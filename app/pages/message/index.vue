<template>
  <view class="message-page">
    <!-- 自定义导航栏 -->
    <view class="custom-navbar">
      <view class="navbar-content">
        <text class="navbar-title">我的消息</text>
        <view class="navbar-actions">
          <view class="action-btn" @click="markAllRead">
            <text class="action-text">全部已读</text>
          </view>
        </view>
      </view>
    </view>

    <!-- 消息统计卡片 -->
    <view class="statistics-section">
      <view class="stat-card clickable" :class="{ 'active': currentFilter === 'unread' }" @click="filterMessages('unread')">
        <view class="stat-number">{{ unreadCount }}</view>
        <view class="stat-label">未读消息</view>
      </view>
      <view class="stat-card clickable" :class="{ 'active': currentFilter === 'read' }" @click="filterMessages('read')">
        <view class="stat-number">{{ readCount }}</view>
        <view class="stat-label">已读消息</view>
      </view>
      <view class="stat-card clickable" :class="{ 'active': currentFilter === 'all' }" @click="filterMessages('all')">
        <view class="stat-number">{{ totalCount }}</view>
        <view class="stat-label">全部消息</view>
      </view>
    </view>

    <!-- 消息列表 -->
    <view class="message-list">
      <view 
        v-for="(item, index) in filteredMessageList" 
        :key="item.messageId"
        class="message-item"
        :class="{ 'unread': item.readStatus === '0' }"
        @click="viewMessage(item)"
      >
        <view class="message-header">
          <view class="message-title">{{ item.title }}</view>
          <view class="message-time">{{ formatTime(item.createTime) }}</view>
        </view>
        <view class="message-content">{{ item.content }}</view>
        <view class="message-footer">
          <view class="message-sender">发送者：{{ item.senderName }}</view>
          <view class="message-status">
            <text v-if="item.readStatus === '0'" class="status-unread">未读</text>
            <text v-else class="status-read">已读</text>
          </view>
        </view>
      </view>
    </view>

    <!-- 空状态 -->
    <view v-if="filteredMessageList.length === 0" class="empty-state">
      <image src="/static/images/empty-message.png" class="empty-image"></image>
      <text class="empty-text">{{ getEmptyText() }}</text>
    </view>

    <!-- 加载更多 -->
    <view v-if="hasMore" class="load-more" @click="loadMore">
      <text class="load-more-text">加载更多</text>
    </view>
  </view>
</template>

<script>
import { getUserMessageList, markAsRead, getUnreadCount, markAsReadBatch } from '@/api/message'

export default {
  data() {
    return {
      messageList: [],
      unreadCount: 0,
      readCount: 0,
      totalCount: 0,
      loading: false,
      hasMore: true,
      pageNum: 1,
      pageSize: 10,
      currentFilter: 'all' // 当前过滤条件：all, unread, read
    }
  },
  
  computed: {
    // 过滤后的消息列表
    filteredMessageList() {
      let filtered = [...this.messageList]
      if (this.currentFilter === 'unread') {
        filtered = filtered.filter(message => message.readStatus === '0')
      } else if (this.currentFilter === 'read') {
        filtered = filtered.filter(message => message.readStatus === '1')
      }
      // 排序：未读在前，已读在后，然后按时间倒序
      filtered.sort((a, b) => {
        if (a.readStatus !== b.readStatus) {
          return a.readStatus === '0' ? -1 : 1
        }
        return new Date(b.createTime) - new Date(a.createTime)
      })
      return filtered
    }
  },
  
  onLoad() {
    this.getUnreadCount()
    this.getMessageList()
  },
  
  onShow() {
    // 页面显示时刷新数据
    this.getUnreadCount()
    this.getMessageList()
    // 更新全局未读消息数量
    this.updateGlobalUnreadCount()
  },
  
  onPullDownRefresh() {
    // 下拉刷新
    this.pageNum = 1
    this.getMessageList().then(() => {
      uni.stopPullDownRefresh()
    })
  },
  
  onReachBottom() {
    // 上拉加载更多
    if (this.hasMore && !this.loading) {
      this.loadMore()
    }
  },
  
  methods: {
    // 获取未读消息数量
    async getUnreadCount() {
      try {
        const response = await getUnreadCount()
        if (response.data) {
          this.unreadCount = response.data.unreadMessages || 0
          this.readCount = response.data.readMessages || 0
          this.totalCount = response.data.totalMessages || 0
        } else {
          this.unreadCount = 0
          this.readCount = 0
          this.totalCount = 0
        }
      } catch (error) {
        console.error('获取未读消息数量失败:', error)
        this.unreadCount = 0
        this.readCount = 0
        this.totalCount = 0
      }
    },
    
    // 获取消息列表
    async getMessageList() {
      if (this.loading) return
      
      this.loading = true
      try {
        const params = {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
        const response = await getUserMessageList(params)
        
        let newMessages = response.rows || []
        
        if (this.pageNum === 1) {
          this.messageList = newMessages
        } else {
          this.messageList = [...this.messageList, ...newMessages]
        }
        
        // 对消息列表进行排序：未读消息在前，已读消息在后
        this.sortMessageList()
        
        this.totalCount = response.total || 0
        this.hasMore = this.messageList.length < this.totalCount
      } catch (error) {
        console.error('获取消息列表失败:', error)
        uni.showToast({
          title: '获取消息列表失败',
          icon: 'none'
        })
      } finally {
        this.loading = false
      }
    },
    
    // 加载更多
    loadMore() {
      if (this.hasMore && !this.loading) {
        this.pageNum++
        this.getMessageList()
      }
    },
    
    // 查看消息详情
    viewMessage(item) {
      // 如果是未读消息，标记为已读
      if (item.readStatus === '0') {
        this.markAsRead(item.messageId)
      }
      
      // 跳转到消息详情页
      uni.navigateTo({
        url: `/pages/message/detail?id=${item.messageId}`
      })
    },
    
    // 标记已读
    async markAsRead(messageId) {
      try {
        await markAsRead(messageId)
        // 更新本地消息状态
        const message = this.messageList.find(item => item.messageId === messageId)
        if (message) {
          message.readStatus = '1'
        }
        // 重新排序消息列表
        this.sortMessageList()
        // 更新未读数量
        this.getUnreadCount()
        // 更新全局未读消息数量
        this.updateGlobalUnreadCount()
      } catch (error) {
        console.error('标记已读失败:', error)
      }
    },
    
    // 全部标记已读
    async markAllRead() {
      const unreadMessages = this.messageList.filter(item => item.readStatus === '0')
      if (unreadMessages.length === 0) {
        uni.showToast({
          title: '没有未读消息',
          icon: 'none'
        })
        return
      }
      
      try {
        const messageIds = unreadMessages.map(item => item.messageId)
        await markAsReadBatch(messageIds)
        
        // 更新本地消息状态
        this.messageList.forEach(item => {
          if (item.status === '0') {
            item.status = '1'
          }
        })
        
        // 重新排序消息列表
        this.sortMessageList()
        
        // 更新未读数量
        this.getUnreadCount()
        // 更新全局未读消息数量
        this.updateGlobalUnreadCount()
        
        uni.showToast({
          title: '全部标记成功',
          icon: 'success'
        })
      } catch (error) {
        console.error('全部标记已读失败:', error)
        uni.showToast({
          title: '标记失败',
          icon: 'none'
        })
      }
    },
    
    // 排序消息列表：未读消息在前，已读消息在后
    sortMessageList() {
      this.messageList.sort((a, b) => {
        // 首先按状态排序：未读(0)在前，已读(1)在后
        if (a.readStatus !== b.readStatus) {
          return a.readStatus === '0' ? -1 : 1
        }
        // 状态相同时，按创建时间倒序排列（最新的在前）
        return new Date(b.createTime) - new Date(a.createTime)
      })
    },
    
    // 格式化时间
    formatTime(time) {
      if (!time) return ''
      
      const date = new Date(time)
      const now = new Date()
      const diff = now - date
      
      // 小于1分钟
      if (diff < 60000) {
        return '刚刚'
      }
      // 小于1小时
      if (diff < 3600000) {
        return Math.floor(diff / 60000) + '分钟前'
      }
      // 小于24小时
      if (diff < 86400000) {
        return Math.floor(diff / 3600000) + '小时前'
      }
      // 小于7天
      if (diff < 604800000) {
        return Math.floor(diff / 86400000) + '天前'
      }
      
      // 超过7天显示具体日期
      return date.getFullYear() + '-' + 
             String(date.getMonth() + 1).padStart(2, '0') + '-' + 
             String(date.getDate()).padStart(2, '0')
    },
    
    // 更新全局未读消息数量
    updateGlobalUnreadCount() {
      const app = getApp()
      if (app && app.updateUnreadCount) {
        app.updateUnreadCount()
      }
    },
    
    // 过滤消息
    filterMessages(filterType) {
      this.currentFilter = filterType
    },
    
    // 获取空状态文本
    getEmptyText() {
      switch (this.currentFilter) {
        case 'unread':
          return '暂无未读消息'
        case 'read':
          return '暂无已读消息'
        default:
          return '暂无消息'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.message-page {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding-bottom: 120rpx;
}

.custom-navbar {
  background-color: #ffffff;
  padding-top: var(--status-bar-height);
  padding-bottom: 20rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
}

.navbar-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30rpx;
}

.navbar-title {
  font-size: 36rpx;
  font-weight: bold;
  color: #333333;
}

.navbar-actions {
  display: flex;
  align-items: center;
}

.action-btn {
  padding: 10rpx 20rpx;
  background-color: #409eff;
  border-radius: 20rpx;
}

.action-text {
  font-size: 24rpx;
  color: #ffffff;
}

.statistics-section {
  display: flex;
  padding: 30rpx;
  gap: 20rpx;
}

.stat-card {
  flex: 1;
  background-color: #ffffff;
  border-radius: 16rpx;
  padding: 30rpx;
  text-align: center;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.stat-card.clickable {
  cursor: pointer;
}

.stat-card.clickable:active {
  transform: scale(0.95);
}

.stat-card.active {
  background-color: #409EFF;
  color: #ffffff;
}

.stat-card.active .stat-number {
  color: #ffffff;
}

.stat-card.active .stat-label {
  color: #ffffff;
}

.stat-number {
  font-size: 48rpx;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 10rpx;
}

.stat-label {
  font-size: 24rpx;
  color: #666666;
}

.message-list {
  padding: 0 30rpx;
}

.message-item {
  background-color: #ffffff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.message-item.unread {
  border-left: 8rpx solid #409eff;
  background-color: #f8f9ff;
}

.message-item:active {
  transform: scale(0.98);
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
}

.message-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333333;
  flex: 1;
}

.message-time {
  font-size: 24rpx;
  color: #999999;
}

.message-content {
  font-size: 28rpx;
  color: #666666;
  line-height: 1.6;
  margin-bottom: 20rpx;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}

.message-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.message-sender {
  font-size: 24rpx;
  color: #999999;
}

.message-status {
  font-size: 24rpx;
}

.status-unread {
  color: #f56c6c;
  font-weight: bold;
}

.status-read {
  color: #67c23a;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100rpx 30rpx;
}

.empty-image {
  width: 200rpx;
  height: 200rpx;
  margin-bottom: 30rpx;
  opacity: 0.5;
}

.empty-text {
  font-size: 28rpx;
  color: #999999;
}

.load-more {
  text-align: center;
  padding: 30rpx;
}

.load-more-text {
  font-size: 28rpx;
  color: #409eff;
}
</style>
