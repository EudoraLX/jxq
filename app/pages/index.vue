<template>
  <view class="content">
    <!-- 顶部欢迎信息 -->
    <view class="welcome-section">
      <view class="user-info">
        <view class="user-details">
          <text class="welcome-text">欢迎{{ userInfo.name || '用户' }}</text>
          <text class="duty-time-text">{{ dutyTimeLabel }}：{{ dutyTimeText }}</text>
        </view>
        <view class="duty-status" @click="handleDutyStatusClick">
          <image class="duty-icon" :src="dutyStatusIcon"></image>
          <text class="duty-text">{{ dutyStatusText }}</text>
        </view>
      </view>
    </view>
    
    <image class="logo" src="@/static/logo.png"></image>
    <view class="text-area">
      <text class="title">Hello jxqvue</text>
    </view>
  </view>
</template>

<script>
import { mapGetters } from 'vuex'
import { getCurrentUserStatus, getProcessByCode, toggleDutyStatus } from '@/api/erp/operatorDuty'

export default {
  data() {
    return {
      dutyStatus: null
    }
  },
  computed: {
    ...mapGetters(['name', 'avatar']),
    userInfo() {
      return {
        name: this.name,
        avatar: this.avatar
      }
    },
    dutyStatusIcon() {
      if (this.dutyStatus && this.dutyStatus.dutyType === '1') {
        return '/static/images/onduty.jpg'
      }
      return '/static/images/offduty.jpg'
    },
    dutyStatusText() {
      if (this.dutyStatus && this.dutyStatus.dutyType === '1') {
        return '在岗'
      }
      return '离岗'
    },
    dutyTimeText() {
      if (this.dutyStatus && this.dutyStatus.registerTime) {
        const timeStr = this.formatDateTime(this.dutyStatus.registerTime)
        // console.log('时间显示:', timeStr, '原始时间:', this.dutyStatus.registerTime)
        return timeStr
      }
      return '暂无记录'
    },
    dutyTimeLabel() {
      if (this.dutyStatus && this.dutyStatus.dutyType === '1') {
        return '上岗时间'
      }
      return '离岗时间'
    }
  },
  onLoad() {
    this.getUserDutyStatus()
  },
  methods: {
    async getUserDutyStatus() {
      try {
        const response = await getCurrentUserStatus()
        this.dutyStatus = response.data
        // console.log('刷新上岗状态:', this.dutyStatus)
      } catch (error) {
        // console.error('获取上岗状态失败:', error)
        // 如果获取失败，默认为离岗状态
        this.dutyStatus = null
      }
    },
    formatDateTime(dateTimeStr) {
      if (!dateTimeStr) return '暂无记录'
      
      const date = new Date(dateTimeStr)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      const seconds = String(date.getSeconds()).padStart(2, '0')
      
      return `${year}年${month}月${day}日 ${hours}:${minutes}:${seconds}`
    },
    async handleDutyStatusClick() {
      const isOnDuty = this.dutyStatus && this.dutyStatus.dutyType === '1'
      const actionText = isOnDuty ? '是否离岗' : '是否上岗'
      
      try {
        const result = await this.$modal.confirm(actionText, '确认操作')
        
        if (result) {
          if (isOnDuty) {
            // 离岗操作
            await this.handleOffDuty()
          } else {
            // 上岗操作
            await this.handleOnDuty()
          }
        }
      } catch (error) {
        console.error('操作失败:', error)
      }
    },
    async handleOffDuty() {
      try {
        // 使用当前记录的信息创建离岗记录
        const data = {
          processId: this.dutyStatus.processId,
          processCode: this.dutyStatus.processCode,
          dutyType: '-1'
        }
        
        await toggleDutyStatus(data)
        this.$modal.showToast('离岗成功')
        // 延迟刷新状态，确保后端数据已更新
        setTimeout(async () => {
          await this.getUserDutyStatus()
          this.$forceUpdate()
        }, 500)
      } catch (error) {
        console.error('离岗失败:', error)
        this.$modal.showToast('离岗失败')
      }
    },
    async handleOnDuty() {
      try {
        // 弹出扫码窗口
        const scanResult = await this.scanCode()
        if (!scanResult) return
        
        // 查询工序信息
        const processResponse = await getProcessByCode(scanResult)
        if (!processResponse.data) {
          this.$modal.showToast('请扫描正确的条码上岗')
          return
        }
        
        // 创建上岗记录
        const data = {
          processId: processResponse.data.processId,
          processCode: processResponse.data.processCode,
          dutyType: '1'
        }
        
        await toggleDutyStatus(data)
        this.$modal.showToast('上岗成功')
        // 延迟刷新状态，确保后端数据已更新
        setTimeout(async () => {
          await this.getUserDutyStatus()
          this.$forceUpdate()
        }, 500)
      } catch (error) {
        console.error('上岗失败:', error)
        this.$modal.showToast('上岗失败')
      }
    },
    scanCode() {
      return new Promise((resolve, reject) => {
        // 检查是否在浏览器环境中
        // #ifdef H5
        // 在浏览器环境中，使用模态框模拟扫码
        uni.showModal({
          title: '模拟扫码',
          content: '请输入工序编号（模拟扫码功能）',
          editable: true,
          placeholderText: '请输入工序编号',
          success: (res) => {
            if (res.confirm && res.content) {
              resolve(res.content)
            } else {
              reject(new Error('用户取消输入'))
            }
          },
          fail: (error) => {
            reject(error)
          }
        })
        // #endif
        
        // #ifndef H5
        // 在移动设备中使用真实的扫码功能
        uni.scanCode({
          success: (res) => {
            resolve(res.result)
          },
          fail: (error) => {
            reject(error)
          }
        })
        // #endif
      })
    }
  }
}
</script>

<style scoped>
  .content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .welcome-section {
    width: 100%;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 40rpx 30rpx;
    margin-bottom: 40rpx;
  }

  .user-info {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .user-details {
    flex: 1;
  }

  .welcome-text {
    display: block;
    font-size: 36rpx;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 15rpx;
  }

  .duty-time-text {
    display: block;
    font-size: 24rpx;
    color: rgba(255, 255, 255, 0.8);
    line-height: 1.4;
  }

  .duty-status {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background: rgba(255, 255, 255, 0.15);
    border-radius: 20rpx;
    padding: 20rpx;
    min-width: 120rpx;
  }

  .duty-icon {
    width: 50rpx;
    height: 50rpx;
    margin-bottom: 10rpx;
  }

  .duty-text {
    font-size: 24rpx;
    color: #ffffff;
    font-weight: 500;
  }

  .logo {
    height: 200rpx;
    width: 200rpx;
    margin-top: 200rpx;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 50rpx;
  }

  .text-area {
    display: flex;
    justify-content: center;
  }

  .title {
    font-size: 36rpx;
    color: #8f8f94;
  }
</style>
