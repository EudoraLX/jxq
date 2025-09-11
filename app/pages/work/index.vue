<template>
  <view class="work-container">
    <!-- 轮播图 -->
    <uni-swiper-dot class="uni-swiper-dot-box" :info="data" :current="current" field="content">
      <swiper class="swiper-box" :current="swiperDotIndex" @change="changeSwiper">
        <swiper-item v-for="(item, index) in data" :key="index">
          <view class="swiper-item" @click="clickBannerItem(item)">
            <image :src="item.image" mode="aspectFill" :draggable="false" />
          </view>
        </swiper-item>
      </swiper>
    </uni-swiper-dot>

    <!-- 智造管理 -->
    <uni-section title="智造管理" type="line"></uni-section>
    <view class="grid-body">
      <uni-grid :column="4" :showBorder="false" @change="changeGrid">
        <uni-grid-item>
          <view class="grid-item-box" @click="handleDutyStatusClick">
            <image class="duty-status-icon" :src="dutyStatusIcon"></image>
            <text class="text">{{ dutyStatusText }}</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box" @click="handleOperationRecord">
            <uni-icons type="list" size="30"></uni-icons>
            <text class="text">操作记录</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="gear-filled" size="30"></uni-icons>
            <text class="text">参数监控</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="compose" size="30"></uni-icons>
            <text class="text">生产报工</text>
          </view>
        </uni-grid-item>
      </uni-grid>
    </view>

    <!-- 库存管理 -->
    <uni-section title="库存管理" type="line"></uni-section>
    <view class="grid-body">
      <uni-grid :column="4" :showBorder="false" @change="changeGrid">
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="plus-filled" size="30"></uni-icons>
            <text class="text">入库</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="minus-filled" size="30"></uni-icons>
            <text class="text">出库</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="search" size="30"></uni-icons>
            <text class="text">盘点</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="redo" size="30"></uni-icons>
            <text class="text">调拨</text>
          </view>
        </uni-grid-item>
      </uni-grid>
    </view>
  </view>
</template>

<script>
import { mapGetters } from 'vuex'
import { getCurrentUserStatus, getProcessByCode, toggleDutyStatus } from '@/api/erp/operatorDuty'

export default {
  data() {
    return {
      current: 0,
      swiperDotIndex: 0,
      dutyStatus: null,
      data: [{
          image: '/static/images/banner/banner01.jpg'
        },
        {
          image: '/static/images/banner/banner02.jpg'
        },
        {
          image: '/static/images/banner/banner03.jpg'
        }
      ]
    }
  },
  computed: {
    ...mapGetters(['name', 'avatar']),
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
    }
  },
  onLoad() {
    this.getUserDutyStatus()
  },
  methods: {
    clickBannerItem(item) {
      console.info(item)
    },
    changeSwiper(e) {
      this.current = e.detail.current
    },
    changeGrid(e) {
      // 检查是否点击的是在岗状态项目
      if (e.detail.index === 0) {
        // 在岗状态项目有自己的点击处理，这里不处理
        return
      }
      this.$modal.showToast('模块建设中~')
    },
    async getUserDutyStatus() {
      try {
        const response = await getCurrentUserStatus()
        this.dutyStatus = response.data
      } catch (error) {
        console.error('获取上岗状态失败:', error)
        // 如果获取失败，默认为离岗状态
        this.dutyStatus = null
        // 如果是连接错误，提示用户启动后端服务
        if (error.message && error.message.includes('后端接口连接异常')) {
          this.$modal.showToast('请先启动后端服务')
        }
      }
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
        console.log('开始离岗操作，当前状态:', this.dutyStatus)
        
        // 使用当前记录的信息创建离岗记录
        const data = {
          processId: this.dutyStatus.processId,
          processCode: this.dutyStatus.processCode,
          dutyType: '-1'
        }
        
        console.log('离岗数据:', data)
        
        const response = await toggleDutyStatus(data)
        console.log('离岗API响应:', response)
        
        this.$modal.showToast('离岗成功')
        // 延迟刷新状态，确保后端数据已更新
        setTimeout(async () => {
          await this.getUserDutyStatus()
          this.$forceUpdate()
        }, 500)
      } catch (error) {
        console.error('离岗失败:', error)
        this.$modal.showToast('离岗失败: ' + (error.message || error))
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
    },
    handleOperationRecord() {
      // 跳转到操作记录页面
      uni.navigateTo({
        url: '/pages/work/operationRecord/index'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  /* #ifndef APP-NVUE */
  page {
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    background-color: #fff;
    min-height: 100%;
    height: auto;
  }

  view {
    font-size: 14px;
    line-height: inherit;
  }
  /* #endif */

  .text {
    text-align: center;
    font-size: 26rpx;
    margin-top: 10rpx;
  }

  .grid-item-box {
    flex: 1;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 15px 0;
  }

  .duty-status-icon {
    width: 30px;
    height: 30px;
    margin-bottom: 5px;
  }

  .uni-margin-wrap {
    width: 690rpx;
    width: 100%;
    ;
  }

  .swiper {
    height: 300rpx;
  }

  .swiper-box {
    height: 150px;
  }

  .swiper-item {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #fff;
    height: 300rpx;
    line-height: 300rpx;
  }

  @media screen and (min-width: 500px) {
    .uni-swiper-dot-box {
      width: 400px;
      /* #ifndef APP-NVUE */
      margin: 0 auto;
      /* #endif */
      margin-top: 8px;
    }

    .image {
      width: 100%;
    }
  }
</style>
