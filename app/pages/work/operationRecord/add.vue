<template>
  <view class="add-operation-record-container">
    <!-- 页面标题 -->
    <view class="page-header">
      <text class="page-title">操作记录</text>
    </view>

    <!-- 表单内容 -->
    <view class="form-container">
      <uni-forms ref="form" :model="formData" :rules="rules" label-width="160rpx">
        

        <!-- 生产订单 -->
        <uni-forms-item label="生产订单" name="productionOrderId" required>
          <view class="scan-input-container" @click="scanProductionOrder">
            <uni-easyinput 
              v-model="productionOrderDisplay" 
              placeholder="请扫码选择生产订单" 
              :disabled="true"
              class="readonly-input"
            />
            <view class="scan-btn">
              <uni-icons type="scan" size="20" color="#3c96f3"></uni-icons>
            </view>
          </view>
        </uni-forms-item>

        <!-- 工序 -->
        <uni-forms-item label="工序" name="processId" required>
          <view class="scan-input-container" @click="scanProcess">
            <uni-easyinput 
              v-model="processDisplay" 
              placeholder="请扫码选择工序" 
              :disabled="true"
              class="readonly-input"
            />
            <view class="scan-btn">
              <uni-icons type="scan" size="20" color="#3c96f3"></uni-icons>
            </view>
          </view>
        </uni-forms-item>

        <!-- 实际数量 -->
        <uni-forms-item label="实际数量" name="quantity" required>
          <view class="quantity-input-container">
            <uni-easyinput 
              v-model="formData.quantity" 
              type="number" 
              placeholder="请输入实际数量"
              :min="0"
              :step="0.01"
              class="quantity-input"
            />
            <text class="unit-text">{{ formData.unitName || '单位' }}</text>
          </view>
        </uni-forms-item>

        <!-- 分隔线 -->
        <view class="section-divider">
          <text class="divider-text">详细信息</text>
        </view>

        <!-- 操作类型 -->
        <uni-forms-item label="操作类型" name="operationType" required>
          <uni-data-select 
            v-model="formData.operationType" 
            :localdata="getDictOptions('operation_type')"
            placeholder="请选择操作类型"
            :clear="false"
            @change="onOperationTypeChange"
          />
        </uni-forms-item>
        
        

        <!-- 产品信息 -->
        <uni-forms-item label="产品" name="productDisplay">
          <view class="form-item-container">
            <uni-easyinput 
              v-model="productDisplay" 
              placeholder="自动加载" 
              :disabled="true"
              class="readonly-input"
            />
            <view class="field-icon">
              <uni-icons type="info" size="16" color="#999999"></uni-icons>
            </view>
          </view>
        </uni-forms-item>

        <!-- 工作中心 -->
        <uni-forms-item label="工作中心" name="workCenterDisplay">
          <view class="form-item-container">
            <uni-easyinput 
              v-model="workCenterDisplay" 
              placeholder="自动加载" 
              :disabled="true"
              class="readonly-input"
            />
            <view class="field-icon">
              <uni-icons type="home" size="16" color="#999999"></uni-icons>
            </view>
          </view>
        </uni-forms-item>

        <!-- 设备 -->
        <uni-forms-item label="设备" name="equipmentDisplay">
          <view class="form-item-container">
            <uni-easyinput 
              v-model="equipmentDisplay" 
              placeholder="自动加载" 
              :disabled="true"
              class="readonly-input"
            />
            <view class="field-icon">
              <uni-icons type="gear" size="16" color="#999999"></uni-icons>
            </view>
          </view>
        </uni-forms-item>

        <!-- 模具 -->
        <uni-forms-item label="模具" name="moldDisplay">
          <view class="form-item-container">
            <uni-easyinput 
              v-model="moldDisplay" 
              placeholder="自动加载" 
              :disabled="true"
              class="readonly-input"
            />
            <view class="field-icon">
              <uni-icons type="compose" size="16" color="#999999"></uni-icons>
            </view>
          </view>
        </uni-forms-item>

        <!-- 操作员 -->
        <uni-forms-item label="操作员" name="operatorDisplay">
          <view class="form-item-container">
            <uni-easyinput 
              v-model="operatorDisplay" 
              placeholder="当前用户" 
              :disabled="true"
              class="readonly-input"
            />
            <view class="field-icon">
              <uni-icons type="person" size="16" color="#999999"></uni-icons>
            </view>
          </view>
        </uni-forms-item>

        <!-- 操作时间 -->
        <uni-forms-item label="操作时间" name="operationTime" required>
          <view class="form-item-container">
            <uni-easyinput 
              v-model="formData.operationTime" 
              placeholder="自动设置" 
              :disabled="true"
              class="readonly-input"
            />
            <view class="field-icon">
              <uni-icons type="calendar" size="16" color="#999999"></uni-icons>
            </view>
          </view>
        </uni-forms-item>

        <!-- 备注 -->
        <uni-forms-item label="备注" name="remark">
          <uni-easyinput 
            v-model="formData.remark" 
            type="textarea" 
            placeholder="请输入备注信息"
            :maxlength="200"
          />
        </uni-forms-item>

      </uni-forms>
    </view>

    <!-- 底部按钮 -->
    <view class="bottom-buttons">
      <button class="cancel-btn" @click="goBack">取消</button>
      <button class="submit-btn" @click="submitForm" :disabled="submitting">
        {{ submitting ? '提交中...' : '提交' }}
      </button>
    </view>
  </view>
</template>

<script>
import { addProductionOperationRecord, getProcessRouteDetails, getLatestOperationRecord } from '@/api/erp/productionOperationRecord'
import { getProductionOrderByCode } from '@/api/erp/productionOrder'
import { getProcessByCode } from '@/api/erp/operatorDuty'
import { mapGetters } from 'vuex'
import dictMixin from '@/mixins/dict'

export default {
  mixins: [dictMixin],
  data() {
    return {
      // 数据字典类型
      dicts: ['operation_type'],
      formData: {
        operationType: '1', // 默认操作类型为1
        productionOrderId: null,
        processId: null,
        processCode: null,
        processName: null,
        productId: null,
        productCode: null,
        productName: null,
        unitId: null,
        unitName: null,
        workCenterId: null,
        equipmentId: null,
        moldId: null,
        operatorId: null,
        operationTime: null,
        quantity: 1,
        remark: null,
        orderCode: null
      },
      // 操作类型选项（从数据字典获取）
      operationTypeOptions: [],
      // 显示字段
      productionOrderDisplay: '',
      productDisplay: '',
      processDisplay: '',
      workCenterDisplay: '',
      equipmentDisplay: '',
      moldDisplay: '',
      operatorDisplay: '',
      // 表单验证规则
      rules: {
        productionOrderId: [
          { required: true, message: '请选择生产订单', trigger: 'change' }
        ],
        processId: [
          { required: true, message: '请选择工序', trigger: 'change' }
        ],
        quantity: [
          { required: true, message: '请输入实际数量', trigger: 'blur' },
          { pattern: /^\d+(\.\d+)?$/, message: '请输入有效的数量', trigger: 'blur' }
        ],
        operationTime: [
          { required: true, message: '请选择操作时间', trigger: 'change' }
        ]
      },
      submitting: false
    }
  },
  computed: {
    ...mapGetters(['name', 'id'])
  },
  onLoad() {
    this.initForm()
    // 直接初始化数据字典
    this.initDictData()
  },
  methods: {
    // 初始化表单
    initForm() {
      // 设置操作员为当前用户
      this.operatorDisplay = this.name || '当前用户'
      this.formData.operatorId = this.id
      
      // 设置操作时间为当前时间
      const now = new Date()
      this.formData.operationTime = this.formatDateTime(now)
    },

    // 扫码选择生产订单
    async scanProductionOrder() {
      try {
        const orderCode = await this.scanCode('请输入生产订单编码')
        if (!orderCode) return

        // 根据订单编码获取生产订单信息
        const response = await getProductionOrderByCode(orderCode)
        if (response.code === 200 && response.data) {
          const order = response.data
          this.formData.productionOrderId = order.id
          this.formData.orderCode = order.orderCode
          this.formData.productId = order.productId
          this.formData.productCode = order.productCode
          this.formData.productName = order.productName
          this.formData.unitId = order.unitId
          this.formData.unitName = order.unitName
          
          this.productionOrderDisplay = order.orderCode
          this.productDisplay = `${order.productCode} - ${order.productName}`
          
          // 清空工序相关字段
          this.clearProcessFields()
          
          this.$modal.showToast('生产订单选择成功')
        } else {
          this.$modal.showToast('未找到该生产订单')
        }
      } catch (error) {
        console.error('扫码选择生产订单失败:', error)
        this.$modal.showToast('扫码失败: ' + (error.message || error))
      }
    },

    // 扫码选择工序
    async scanProcess() {
      if (!this.formData.productionOrderId) {
        this.$modal.showToast('请先选择生产订单')
        return
      }

      try {
        const processCode = await this.scanCode('请输入工序编码')
        if (!processCode) return

        // 获取生产订单对应的工序列表
        const response = await getProcessRouteDetails(this.formData.productionOrderId)
        if (response.code === 200 && response.data) {
          const processList = response.data
          const process = processList.find(p => p.processCode === processCode)
          
          if (process) {
            this.formData.processId = process.id
            this.formData.processCode = process.processCode
            this.formData.processName = process.processName
            this.formData.workCenterId = process.workCenterId
            this.formData.equipmentId = process.equipmentId
            this.formData.moldId = process.moldId
            
            this.processDisplay = `${process.processCode} - ${process.processName}`
            // 注意：这里需要根据实际返回的数据结构调整字段名
            this.workCenterDisplay = process.workCenterCode ? `${process.workCenterCode} - ${process.workCenterName}` : '未设置'
            this.equipmentDisplay = process.equipmentCode ? `${process.equipmentCode} - ${process.equipmentName}` : '未设置'
            this.moldDisplay = process.moldCode ? `${process.moldCode} - ${process.moldName}` : '未设置'
            
            // 检查最新记录并自动设置操作类型
            await this.checkLatestRecordAndSetOperationType()
            
            this.$modal.showToast('工序选择成功')
          } else {
            this.$modal.showToast('该生产订单中没有找到该工序')
          }
        } else {
          this.$modal.showToast('获取工序列表失败')
        }
      } catch (error) {
        console.error('扫码选择工序失败:', error)
        this.$modal.showToast('扫码失败: ' + (error.message || error))
      }
    },

    // 扫码功能
    scanCode(placeholder) {
      return new Promise((resolve, reject) => {
        // #ifdef H5
        uni.showModal({
          title: '模拟扫码',
          content: placeholder,
          editable: true,
          placeholderText: placeholder,
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

    // 清空工序相关字段
    clearProcessFields() {
      this.formData.processId = null
      this.formData.processCode = null
      this.formData.processName = null
      this.formData.workCenterId = null
      this.formData.equipmentId = null
      this.formData.moldId = null
      
      this.processDisplay = ''
      this.workCenterDisplay = ''
      this.equipmentDisplay = ''
      this.moldDisplay = ''
    },

    // 检查最新记录并自动设置操作类型
    async checkLatestRecordAndSetOperationType() {
      if (!this.formData.productionOrderId || !this.formData.processId) {
        return
      }

      try {
        const response = await getLatestOperationRecord({
          productionOrderId: this.formData.productionOrderId,
          processId: this.formData.processId
        })
        
        if (response.code === 200 && response.data) {
          const latestRecord = response.data
          // 如果最新记录的操作类型是"1"（生产操作），则自动设置为"-1"（结束操作）
          if (latestRecord.operationType === '1' || latestRecord.operationType === '0') {
            this.formData.operationType = '-1'
            this.$modal.showToast('检测到未结束的生产操作，已自动设置为结束操作')
          }
        }
      } catch (error) {
        console.error('检查最新记录失败:', error)
        // 不显示错误提示，静默处理
      }
    },

    // 操作类型变化处理
    onOperationTypeChange(value) {
      console.log('操作类型变化:', value)
      this.formData.operationType = value
    },


    // 提交表单
    async submitForm() {
      try {
        // 表单验证
        const valid = await this.$refs.form.validate()
        if (!valid) return

        this.submitting = true

        // 提交数据
        const response = await addProductionOperationRecord(this.formData)
        if (response.code === 200) {
          this.$modal.showToast('操作记录添加成功')
          setTimeout(() => {
            uni.navigateBack()
          }, 1500)
        } else {
          this.$modal.showToast('添加失败: ' + (response.msg || '未知错误'))
        }
      } catch (error) {
        console.error('提交表单失败:', error)
        this.$modal.showToast('提交失败: ' + (error.message || error))
      } finally {
        this.submitting = false
      }
    },

    // 返回上一页
    goBack() {
      uni.navigateBack()
    },

    // 格式化日期时间
    formatDateTime(date) {
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
.add-operation-record-container {
  min-height: 100vh;
  background-color: #f5f6f7;
  padding-bottom: 120rpx;
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

.form-container {
  padding: 30rpx;
  background-color: #ffffff;
  margin: 20rpx;
  border-radius: 16rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
}

.form-item-container {
  position: relative;
  display: flex;
  align-items: center;
  
  .field-icon {
    position: absolute;
    right: 20rpx;
    top: 50%;
    transform: translateY(-50%);
    z-index: 10;
  }
}

.scan-input-container {
  position: relative;
  display: flex;
  align-items: center;
  
  .scan-btn {
    position: absolute;
    right: 20rpx;
    top: 50%;
    transform: translateY(-50%);
    z-index: 10;
    background-color: rgba(60, 150, 243, 0.1);
    border-radius: 50%;
    width: 60rpx;
    height: 60rpx;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

.quantity-input-container {
  display: flex;
  align-items: center;
  background-color: #f8f9fa;
  border-radius: 12rpx;
  padding: 0 20rpx;
  
  .quantity-input {
    flex: 1;
  }
  
  .unit-text {
    margin-left: 20rpx;
    font-size: 28rpx;
    color: #666666;
    min-width: 80rpx;
    font-weight: 500;
  }
}

.section-divider {
  margin: 40rpx 0 30rpx 0;
  text-align: center;
  position: relative;
  
  &::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    height: 1rpx;
    background: linear-gradient(90deg, transparent, #e0e0e0, transparent);
  }
  
  .divider-text {
    background-color: #ffffff;
    padding: 0 30rpx;
    font-size: 26rpx;
    color: #999999;
    font-weight: 500;
  }
}

.readonly-input {
  background-color: #f8f9fa !important;
  border: 2rpx solid #e9ecef !important;
  border-radius: 12rpx !important;
  color: #6c757d !important;
  font-weight: 500;
}

.bottom-buttons {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
  padding: 30rpx;
  display: flex;
  gap: 30rpx;
  box-shadow: 0 -4rpx 20rpx rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10rpx);
  
  .cancel-btn {
    flex: 1;
    height: 88rpx;
    background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
    color: #6c757d;
    border: 2rpx solid #dee2e6;
    border-radius: 16rpx;
    font-size: 32rpx;
    font-weight: 600;
    box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    
    &:active {
      transform: translateY(2rpx);
      box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
    }
  }
  
  .submit-btn {
    flex: 1;
    height: 88rpx;
    background: linear-gradient(135deg, #3c96f3 0%, #2e7d32 100%);
    color: #ffffff;
    border: none;
    border-radius: 16rpx;
    font-size: 32rpx;
    font-weight: 600;
    box-shadow: 0 4rpx 20rpx rgba(60, 150, 243, 0.4);
    transition: all 0.3s ease;
    
    &:active {
      transform: translateY(2rpx);
      box-shadow: 0 2rpx 12rpx rgba(60, 150, 243, 0.3);
    }
    
    &:disabled {
      background: linear-gradient(135deg, #cccccc 0%, #999999 100%);
      box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
    }
  }
}

// 美化表单标签
:deep(.uni-forms-item__label) {
  font-weight: 600;
  color: #495057;
  font-size: 30rpx;
}

// 美化输入框
:deep(.uni-easyinput__content) {
  border-radius: 12rpx;
  border: 2rpx solid #e9ecef;
  transition: all 0.3s ease;
  
  &:focus-within {
    border-color: #3c96f3;
    box-shadow: 0 0 0 4rpx rgba(60, 150, 243, 0.1);
  }
}

// 美化必填标识
:deep(.uni-forms-item__label--required) {
  &::before {
    color: #dc3545;
    font-weight: bold;
  }
}

// 美化错误提示
:deep(.uni-forms-item__error) {
  color: #dc3545;
  font-size: 24rpx;
  margin-top: 10rpx;
  padding-left: 20rpx;
}
</style>
