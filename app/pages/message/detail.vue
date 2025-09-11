<template>
  <view class="message-detail">
    <!-- 消息内容 -->
    <view v-if="messageDetail" class="message-content">
      <view class="message-header">
        <view class="message-title">{{ messageDetail.title }}</view>
        <view class="message-meta">
          <view class="message-time">{{ formatTime(messageDetail.createTime) }}</view>
          <view class="message-sender">发送者：{{ messageDetail.senderName }}</view>
        </view>
      </view>

      <view class="message-info">
        <view class="info-item">
          <text class="info-label">消息类型：</text>
          <text class="info-value">{{ getMessageTypeText(messageDetail.messageType) }}</text>
        </view>
        <view class="info-item">
          <text class="info-label">消息级别：</text>
          <text class="info-value">{{ getMessageLevelText(messageDetail.messageLevel) }}</text>
        </view>
        <view class="info-item">
          <text class="info-label">消息状态：</text>
          <text class="info-value" :class="messageDetail.readStatus === '0' ? 'status-unread' : 'status-read'">
            {{ messageDetail.readStatus === '0' ? '未读' : '已读' }}
          </text>
        </view>
      </view>

      <view class="message-body">
        <view class="body-title">消息内容</view>
        <view class="body-content">{{ messageDetail.content }}</view>
      </view>

      <view v-if="messageDetail.remark" class="message-remark">
        <view class="remark-title">备注</view>
        <view class="remark-content">{{ messageDetail.remark }}</view>
      </view>
    </view>

    <!-- 加载状态 -->
    <view v-if="loading" class="loading-state">
      <text class="loading-text">加载中...</text>
    </view>

    <!-- 错误状态 -->
    <view v-if="error" class="error-state">
      <text class="error-text">{{ error }}</text>
      <view class="error-action" @click="retryLoad">
        <text class="retry-text">重试</text>
      </view>
    </view>
  </view>
</template>

<script>
import { getMessage, markAsRead } from '@/api/message'

export default {
  data() {
    return {
      messageId: '',
      messageDetail: null,
      loading: true,
      error: ''
    }
  },
  
  onLoad(options) {
    this.messageId = options.id
    this.loadMessageDetail()
  },
  
  methods: {
    // 加载消息详情
    async loadMessageDetail() {
      if (!this.messageId) {
        this.error = '消息ID不存在'
        this.loading = false
        return
      }
      
      this.loading = true
      this.error = ''
      
      try {
        const response = await getMessage(this.messageId)
        this.messageDetail = response.data
        
        // 如果是未读消息，标记为已读
        if (this.messageDetail && this.messageDetail.readStatus === '0') {
          await this.markAsRead()
        }
      } catch (error) {
        console.error('获取消息详情失败:', error)
        this.error = '获取消息详情失败'
      } finally {
        this.loading = false
      }
    },
    
    // 标记已读
    async markAsRead() {
      try {
        await markAsRead(this.messageId)
        if (this.messageDetail) {
          this.messageDetail.readStatus = '1'
        }
        // 更新全局未读消息数量
        const app = getApp()
        if (app && app.updateUnreadCount) {
          app.updateUnreadCount()
        }
      } catch (error) {
        console.error('标记已读失败:', error)
      }
    },
    
    // 重试加载
    retryLoad() {
      this.loadMessageDetail()
    },
    
    // 格式化时间
    formatTime(time) {
      if (!time) return ''
      
      const date = new Date(time)
      return date.getFullYear() + '-' + 
             String(date.getMonth() + 1).padStart(2, '0') + '-' + 
             String(date.getDate()).padStart(2, '0') + ' ' +
             String(date.getHours()).padStart(2, '0') + ':' +
             String(date.getMinutes()).padStart(2, '0')
    },
    
    // 获取消息类型文本
    getMessageTypeText(type) {
      const typeMap = {
        '1': '系统消息',
        '2': '业务消息',
        '3': '通知消息'
      }
      return typeMap[type] || '未知类型'
    },
    
    // 获取消息级别文本
    getMessageLevelText(level) {
      const levelMap = {
        '1': '普通',
        '2': '重要',
        '3': '紧急'
      }
      return levelMap[level] || '普通'
    }
  }
}
</script>

<style lang="scss" scoped>
.message-detail {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.message-content {
  margin: 30rpx;
  background-color: #ffffff;
  border-radius: 16rpx;
  padding: 40rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
}

.message-header {
  margin-bottom: 40rpx;
  padding-bottom: 30rpx;
  border-bottom: 2rpx solid #f0f0f0;
}

.message-title {
  font-size: 40rpx;
  font-weight: bold;
  color: #333333;
  line-height: 1.4;
  margin-bottom: 20rpx;
}

.message-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.message-time {
  font-size: 24rpx;
  color: #999999;
}

.message-sender {
  font-size: 24rpx;
  color: #666666;
}

.message-info {
  margin-bottom: 40rpx;
  padding-bottom: 30rpx;
  border-bottom: 2rpx solid #f0f0f0;
}

.info-item {
  display: flex;
  align-items: center;
  margin-bottom: 20rpx;
}

.info-item:last-child {
  margin-bottom: 0;
}

.info-label {
  font-size: 28rpx;
  color: #666666;
  width: 160rpx;
}

.info-value {
  font-size: 28rpx;
  color: #333333;
  flex: 1;
}

.status-unread {
  color: #f56c6c;
  font-weight: bold;
}

.status-read {
  color: #67c23a;
}

.message-body {
  margin-bottom: 40rpx;
}

.body-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333333;
  margin-bottom: 20rpx;
}

.body-content {
  font-size: 28rpx;
  color: #666666;
  line-height: 1.6;
  white-space: pre-wrap;
  word-break: break-word;
}

.message-remark {
  padding-top: 30rpx;
  border-top: 2rpx solid #f0f0f0;
}

.remark-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333333;
  margin-bottom: 20rpx;
}

.remark-content {
  font-size: 28rpx;
  color: #666666;
  line-height: 1.6;
  white-space: pre-wrap;
  word-break: break-word;
}

.loading-state {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 100rpx 30rpx;
}

.loading-text {
  font-size: 28rpx;
  color: #999999;
}

.error-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100rpx 30rpx;
}

.error-text {
  font-size: 28rpx;
  color: #f56c6c;
  margin-bottom: 30rpx;
}

.error-action {
  padding: 20rpx 40rpx;
  background-color: #409eff;
  border-radius: 20rpx;
}

.retry-text {
  font-size: 28rpx;
  color: #ffffff;
}
</style>
