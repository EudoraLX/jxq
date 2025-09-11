<template>
  <view class="operation-record-container">
    <!-- 页面标题 -->
    <view class="page-header">
      <text class="page-title">操作记录</text>
    </view>

    <!-- 记录列表 -->
    <view class="record-list">
      <view 
        v-for="(record, index) in recordList" 
        :key="index" 
        class="record-item"
        @click="viewRecordDetail(record)"
        @longpress="showDeleteConfirm(record, index)"
      >
        <view class="record-header">
          <text class="order-code">{{ record.orderCode }}</text>
          <text class="operation-time">{{ formatDateTime(record.operationTime) }}</text>
        </view>
        
        <view class="record-content">
          <view class="product-info">
            <text class="product-code">{{ record.productCode }}</text>
            <text class="product-name">{{ record.productName }}</text>
          </view>
          
          <view class="process-info">
            <text class="process-name">{{ record.processName }}</text>
          </view>
          
          <view class="quantity-info">
            <text class="quantity">数量：{{ record.quantity }}</text>
            <text class="unit">单位：{{ record.unitName }}</text>
          </view>
          
          <view class="time-info">
            <text class="operation-time">操作时间：{{ formatDateTime(record.operationTime) }}</text>
          </view>
        </view>
      </view>
    </view>

    <!-- 空状态 -->
    <view v-if="recordList.length === 0 && !loading" class="empty-state">
      <image class="empty-icon" src="/static/images/empty.png" mode="aspectFit"></image>
      <text class="empty-text">暂无操作记录</text>
    </view>

    <!-- 加载状态 -->
    <view v-if="loading" class="loading-state">
      <text class="loading-text">加载中...</text>
    </view>

    <!-- 分页加载更多 -->
    <view v-if="hasMore && !loading" class="load-more" @click="loadMore">
      <text class="load-more-text">加载更多</text>
    </view>

    <!-- 浮动添加按钮 -->
    <view class="floating-add-btn" @click="addRecord">
      <uni-icons type="plus" size="24" color="#ffffff"></uni-icons>
    </view>
  </view>
</template>

<script>
import { listProductionOperationRecord, delProductionOperationRecord } from '@/api/erp/productionOperationRecord'
import dictMixin from '@/mixins/dict'

export default {
  mixins: [dictMixin],
  data() {
    return {
      // 数据字典类型
      dicts: ['operation_type'],
      recordList: [],
      loading: false,
      hasMore: true,
      pageNum: 1,
      pageSize: 10,
      total: 0
    }
  },
  onLoad() {
    this.getRecordList()
    // 延迟初始化数据字典
    this.$nextTick(() => {
      this.initDictData()
    })
  },
  onReachBottom() {
    if (this.hasMore && !this.loading) {
      this.loadMore()
    }
  },
  methods: {
    async getRecordList(refresh = false) {
      if (refresh) {
        this.pageNum = 1
        this.recordList = []
        this.hasMore = true
      }
      
      this.loading = true
      try {
        const params = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderBy: 'operation_time desc'
        }
        
        const response = await listProductionOperationRecord(params)
        
        if (refresh) {
          this.recordList = response.rows || []
        } else {
          this.recordList = [...this.recordList, ...(response.rows || [])]
        }
        
        this.total = response.total || 0
        this.hasMore = this.recordList.length < this.total
        
        if (this.pageNum === 1) {
          this.pageNum = 2
        } else {
          this.pageNum++
        }
      } catch (error) {
        console.error('获取操作记录失败:', error)
        this.$modal.showToast('获取操作记录失败')
      } finally {
        this.loading = false
      }
    },
    
    loadMore() {
      if (this.hasMore && !this.loading) {
        this.getRecordList()
      }
    },
    
    viewRecordDetail(record) {
      // 查看记录详情
      console.log('查看记录详情:', record)
      uni.navigateTo({
        url: `/pages/work/operationRecord/detail?id=${record.id}`
      })
    },
    
    addRecord() {
      // 添加新记录
      console.log('添加新记录')
      uni.navigateTo({
        url: '/pages/work/operationRecord/add'
      })
    },
    
    // 长按显示删除确认
    showDeleteConfirm(record, index) {
      uni.showModal({
        title: '删除确认',
        content: `确定要删除订单 ${record.orderCode} 的操作记录吗？`,
        confirmText: '删除',
        confirmColor: '#ff4757',
        success: (res) => {
          if (res.confirm) {
            this.deleteRecord(record.id, index)
          }
        }
      })
    },
    
    // 删除记录
    async deleteRecord(recordId, index) {
      try {
        this.loading = true
        const response = await delProductionOperationRecord(recordId)
        if (response.code === 200) {
          this.$modal.showToast('删除成功')
          // 从列表中移除该记录
          this.recordList.splice(index, 1)
          this.total--
        } else {
          this.$modal.showToast('删除失败: ' + (response.msg || '未知错误'))
        }
      } catch (error) {
        console.error('删除记录失败:', error)
        this.$modal.showToast('删除失败: ' + (error.message || error))
      } finally {
        this.loading = false
      }
    },
    
    formatDateTime(dateTimeStr) {
      if (!dateTimeStr) return ''
      
      const date = new Date(dateTimeStr)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      
      return `${year}-${month}-${day} ${hours}:${minutes}`
    }
  }
}
</script>

<style lang="scss" scoped>
.operation-record-container {
  min-height: 100vh;
  background-color: #f5f6f7;
  padding-bottom: 100rpx;
}

.page-header {
  background-color: #3c96f3;
  padding: 30rpx;
  text-align: center;
  
  .page-title {
    color: #ffffff;
    font-size: 36rpx;
    font-weight: bold;
  }
}

.record-list {
  padding: 20rpx;
}

.record-item {
  background-color: #ffffff;
  border-radius: 16rpx;
  margin-bottom: 20rpx;
  padding: 30rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  position: relative;
  
  &:active {
    transform: scale(0.98);
    box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.15);
  }
  
  &::after {
    content: '长按删除';
    position: absolute;
    top: 10rpx;
    right: 10rpx;
    background-color: rgba(255, 71, 87, 0.1);
    color: #ff4757;
    font-size: 20rpx;
    padding: 4rpx 8rpx;
    border-radius: 8rpx;
    opacity: 0;
    transition: opacity 0.3s ease;
  }
  
  &:hover::after {
    opacity: 1;
  }
}

.record-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
  
  .order-code {
    font-size: 32rpx;
    font-weight: bold;
    color: #333333;
  }
  
  .operation-time {
    font-size: 24rpx;
    color: #999999;
  }
}

.record-content {
  .product-info {
    display: flex;
    align-items: center;
    margin-bottom: 15rpx;
    
    .product-code {
      font-size: 28rpx;
      color: #3c96f3;
      font-weight: bold;
      margin-right: 20rpx;
    }
    
    .product-name {
      font-size: 28rpx;
      color: #666666;
      flex: 1;
    }
  }
  
  .process-info {
    margin-bottom: 15rpx;
    
    .process-name {
      font-size: 26rpx;
      color: #333333;
    }
  }
  
  .quantity-info {
    display: flex;
    align-items: center;
    margin-bottom: 15rpx;
    
    .quantity {
      font-size: 26rpx;
      color: #666666;
      margin-right: 30rpx;
    }
    
    .unit {
      font-size: 26rpx;
      color: #666666;
    }
  }
  
  .time-info {
    .operation-time {
      font-size: 26rpx;
      color: #ff6b35;
      font-weight: bold;
    }
  }
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100rpx 0;
  
  .empty-icon {
    width: 200rpx;
    height: 200rpx;
    margin-bottom: 30rpx;
    opacity: 0.6;
  }
  
  .empty-text {
    font-size: 28rpx;
    color: #999999;
  }
}

.loading-state {
  text-align: center;
  padding: 40rpx 0;
  
  .loading-text {
    font-size: 26rpx;
    color: #999999;
  }
}

.load-more {
  text-align: center;
  padding: 40rpx 0;
  
  .load-more-text {
    font-size: 26rpx;
    color: #3c96f3;
  }
}

.floating-add-btn {
  position: fixed;
  right: 30rpx;
  bottom: 30rpx;
  width: 100rpx;
  height: 100rpx;
  background-color: #3c96f3;
  border-radius: 50rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4rpx 12rpx rgba(60, 150, 243, 0.3);
  z-index: 999;
}
</style>
