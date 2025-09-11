<template>
  <view class="detail-container">
    <!-- 页面标题 -->
    <view class="page-header">
      <view class="header-left" @click="goBack">
        <uni-icons type="left" size="20" color="#ffffff"></uni-icons>
      </view>
      <text class="page-title">操作记录详情</text>
      <view class="header-right" @click="showActionSheet">
        <uni-icons type="more" size="20" color="#ffffff"></uni-icons>
      </view>
    </view>

    <!-- 加载状态 -->
    <view v-if="loading" class="loading-container">
      <text class="loading-text">加载中...</text>
    </view>

    <!-- 详情内容 -->
    <view v-else-if="recordDetail" class="detail-content">
      <!-- 基本信息卡片 -->
      <view class="info-card">
        <view class="card-header">
          <text class="card-title">基本信息</text>
        </view>
        <view class="info-list">
          <view class="info-item">
            <text class="info-label">订单编码</text>
            <text class="info-value">{{ recordDetail.orderCode }}</text>
          </view>
          <view class="info-item">
            <text class="info-label">操作类型</text>
            <text class="info-value">{{ getOperationTypeText(recordDetail.operationType) }}</text>
          </view>
          <view class="info-item">
            <text class="info-label">操作时间</text>
            <text class="info-value">{{ formatDateTime(recordDetail.operationTime) }}</text>
          </view>
          <view class="info-item">
            <text class="info-label">实际数量</text>
            <text class="info-value">{{ recordDetail.quantity }} {{ recordDetail.unitName }}</text>
          </view>
        </view>
      </view>

      <!-- 产品信息卡片 -->
      <view class="info-card">
        <view class="card-header">
          <text class="card-title">产品信息</text>
        </view>
        <view class="info-list">
          <view class="info-item">
            <text class="info-label">产品编码</text>
            <text class="info-value">{{ recordDetail.productCode }}</text>
          </view>
          <view class="info-item">
            <text class="info-label">产品名称</text>
            <text class="info-value">{{ recordDetail.productName }}</text>
          </view>
        </view>
      </view>

      <!-- 工序信息卡片 -->
      <view class="info-card">
        <view class="card-header">
          <text class="card-title">工序信息</text>
        </view>
        <view class="info-list">
          <view class="info-item">
            <text class="info-label">工序编码</text>
            <text class="info-value">{{ recordDetail.processCode }}</text>
          </view>
          <view class="info-item">
            <text class="info-label">工序名称</text>
            <text class="info-value">{{ recordDetail.processName }}</text>
          </view>
          <view class="info-item">
            <text class="info-label">工作中心</text>
            <text class="info-value">{{ recordDetail.workCenterName || '未设置' }}</text>
          </view>
          <view class="info-item">
            <text class="info-label">设备</text>
            <text class="info-value">{{ recordDetail.equipmentName || '未设置' }}</text>
          </view>
          <view class="info-item">
            <text class="info-label">模具</text>
            <text class="info-value">{{ recordDetail.moldName || '未设置' }}</text>
          </view>
        </view>
      </view>

      <!-- 操作员信息卡片 -->
      <view class="info-card">
        <view class="card-header">
          <text class="card-title">操作员信息</text>
        </view>
        <view class="info-list">
          <view class="info-item">
            <text class="info-label">操作员</text>
            <text class="info-value">{{ recordDetail.operatorName || '未知' }}</text>
          </view>
        </view>
      </view>

      <!-- 备注信息卡片 -->
      <view v-if="recordDetail.remark" class="info-card">
        <view class="card-header">
          <text class="card-title">备注信息</text>
        </view>
        <view class="remark-content">
          <text class="remark-text">{{ recordDetail.remark }}</text>
        </view>
      </view>
    </view>

    <!-- 空状态 -->
    <view v-else class="empty-state">
      <image class="empty-icon" src="/static/images/empty.png" mode="aspectFit"></image>
      <text class="empty-text">记录不存在</text>
    </view>

    <!-- 操作菜单 -->
    <uni-popup ref="actionSheet" type="bottom">
      <view class="action-sheet">
        <view class="action-item" @click="editRecord">
          <uni-icons type="compose" size="20" color="#3c96f3"></uni-icons>
          <text class="action-text">编辑记录</text>
        </view>
        <view class="action-item danger" @click="deleteRecord">
          <uni-icons type="trash" size="20" color="#ff4757"></uni-icons>
          <text class="action-text">删除记录</text>
        </view>
        <view class="action-item" @click="closeActionSheet">
          <text class="action-text">取消</text>
        </view>
      </view>
    </uni-popup>
  </view>
</template>

<script>
import { getProductionOperationRecord, delProductionOperationRecord } from '@/api/erp/productionOperationRecord'
import dictMixin from '@/mixins/dict'

export default {
  mixins: [dictMixin],
  data() {
    return {
      // 数据字典类型
      dicts: ['operation_type'],
      recordId: null,
      recordDetail: null,
      loading: false
    }
  },
  onLoad(options) {
    this.recordId = options.id
    if (this.recordId) {
      this.getRecordDetail()
    }
    // 延迟初始化数据字典
    this.$nextTick(() => {
      this.initDictData()
    })
  },
  methods: {
    // 获取记录详情
    async getRecordDetail() {
      try {
        this.loading = true
        const response = await getProductionOperationRecord(this.recordId)
        if (response.code === 200) {
          this.recordDetail = response.data
        } else {
          this.$modal.showToast('获取记录详情失败')
        }
      } catch (error) {
        console.error('获取记录详情失败:', error)
        this.$modal.showToast('获取记录详情失败: ' + (error.message || error))
      } finally {
        this.loading = false
      }
    },

    // 显示操作菜单
    showActionSheet() {
      this.$refs.actionSheet.open()
    },

    // 关闭操作菜单
    closeActionSheet() {
      this.$refs.actionSheet.close()
    },

    // 编辑记录
    editRecord() {
      this.closeActionSheet()
      uni.navigateTo({
        url: `/pages/work/operationRecord/edit?id=${this.recordId}`
      })
    },

    // 删除记录
    deleteRecord() {
      this.closeActionSheet()
      uni.showModal({
        title: '删除确认',
        content: `确定要删除订单 ${this.recordDetail.orderCode} 的操作记录吗？`,
        confirmText: '删除',
        confirmColor: '#ff4757',
        success: async (res) => {
          if (res.confirm) {
            try {
              this.loading = true
              const response = await delProductionOperationRecord(this.recordId)
              if (response.code === 200) {
                this.$modal.showToast('删除成功')
                setTimeout(() => {
                  uni.navigateBack()
                }, 1500)
              } else {
                this.$modal.showToast('删除失败: ' + (response.msg || '未知错误'))
              }
            } catch (error) {
              console.error('删除记录失败:', error)
              this.$modal.showToast('删除失败: ' + (error.message || error))
            } finally {
              this.loading = false
            }
          }
        }
      })
    },

    // 返回上一页
    goBack() {
      uni.navigateBack()
    },

    // 获取操作类型文本
    getOperationTypeText(type) {
      return this.getDictLabel('operation_type', type)
    },

    // 格式化日期时间
    formatDateTime(dateTimeStr) {
      if (!dateTimeStr) return ''
      
      const date = new Date(dateTimeStr)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      const seconds = String(date.getSeconds()).padStart(2, '0')
      
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
    }
  }
}
</script>

<style lang="scss" scoped>
.detail-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding-bottom: 40rpx;
}

.page-header {
  background: linear-gradient(135deg, #3c96f3 0%, #2e7d32 100%);
  padding: 40rpx 30rpx;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 4rpx 20rpx rgba(60, 150, 243, 0.3);
  
  .header-left {
    width: 60rpx;
    height: 60rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(255, 255, 255, 0.2);
    border-radius: 50%;
    backdrop-filter: blur(10rpx);
  }
  
  .page-title {
    color: #ffffff;
    font-size: 38rpx;
    font-weight: bold;
    flex: 1;
    text-align: center;
    text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.1);
  }
  
  .header-right {
    width: 60rpx;
    height: 60rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(255, 255, 255, 0.2);
    border-radius: 50%;
    backdrop-filter: blur(10rpx);
  }
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 400rpx;
  
  .loading-text {
    color: #ffffff;
    font-size: 28rpx;
  }
}

.detail-content {
  padding: 30rpx 20rpx;
}

.info-card {
  background-color: #ffffff;
  border-radius: 20rpx;
  margin-bottom: 30rpx;
  box-shadow: 0 8rpx 32rpx rgba(0, 0, 0, 0.1);
  overflow: hidden;
  
  .card-header {
    background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
    padding: 30rpx;
    border-bottom: 1rpx solid #e9ecef;
    
    .card-title {
      font-size: 32rpx;
      font-weight: bold;
      color: #495057;
    }
  }
  
  .info-list {
    padding: 30rpx;
  }
  
  .info-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20rpx 0;
    border-bottom: 1rpx solid #f8f9fa;
    
    &:last-child {
      border-bottom: none;
    }
    
    .info-label {
      font-size: 28rpx;
      color: #6c757d;
      font-weight: 500;
      min-width: 160rpx;
    }
    
    .info-value {
      font-size: 28rpx;
      color: #495057;
      font-weight: 600;
      text-align: right;
      flex: 1;
      margin-left: 20rpx;
    }
  }
  
  .remark-content {
    padding: 30rpx;
    
    .remark-text {
      font-size: 28rpx;
      color: #495057;
      line-height: 1.6;
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
    color: #ffffff;
  }
}

.action-sheet {
  background-color: #ffffff;
  border-radius: 20rpx 20rpx 0 0;
  padding: 40rpx 0;
  
  .action-item {
    display: flex;
    align-items: center;
    padding: 30rpx 40rpx;
    border-bottom: 1rpx solid #f8f9fa;
    
    &:last-child {
      border-bottom: none;
    }
    
    &.danger {
      .action-text {
        color: #ff4757;
      }
    }
    
    .action-text {
      margin-left: 20rpx;
      font-size: 32rpx;
      color: #495057;
      font-weight: 500;
    }
  }
}
</style>
