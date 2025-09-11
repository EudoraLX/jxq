<template>
  <el-dialog title="编辑模具明细" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="组件编码" prop="componentCode">
            <el-input v-model="form.componentCode" placeholder="请输入组件编码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="组件名称" prop="componentName">
            <el-input v-model="form.componentName" placeholder="请输入组件名称" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="组件类型" prop="componentType">
            <el-select v-model="form.componentType" placeholder="请选择组件类型" style="width:100%">
              <el-option v-for="dict in dict.type.component_type" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="组件状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择组件状态" style="width:100%">
              <el-option v-for="dict in dict.type.equipment_status" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="规格尺寸" prop="specification">
            <el-input v-model="form.specification" placeholder="请输入规格尺寸" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="材质" prop="material">
            <el-input v-model="form.material" placeholder="请输入材质" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="数量" prop="quantity">
            <el-input-number v-model="form.quantity" :min="1" :precision="0" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="单位" prop="unit">
            <el-input v-model="form.unit" placeholder="请输入单位" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="单价" prop="unitPrice">
            <el-input-number v-model="form.unitPrice" :min="0" :precision="2" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="总价" prop="totalPrice">
            <el-input v-model="totalPriceDisplay" readonly style="width:100%" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="供应商" prop="supplier">
            <el-input v-model="form.supplier" placeholder="请输入供应商" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="采购日期" prop="purchaseDate">
            <el-date-picker
              v-model="form.purchaseDate"
              type="date"
              placeholder="请选择采购日期"
              value-format="yyyy-MM-dd"
              style="width:100%"
            />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注信息" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "MoldDetailEdit",
  dicts: ['component_type', 'equipment_status'],
  data() {
    return {
      visible: false,
      form: {
        componentCode: '',
        componentName: '',
        componentType: '',
        status: '',
        specification: '',
        material: '',
        quantity: 1,
        unit: '',
        unitPrice: 0,
        totalPrice: 0,
        supplier: '',
        purchaseDate: '',
        remark: ''
      },
      rules: {
        componentCode: [
          { required: true, message: '组件编码不能为空', trigger: 'blur' }
        ],
        componentName: [
          { required: true, message: '组件名称不能为空', trigger: 'blur' }
        ],
        componentType: [
          { required: true, message: '请选择组件类型', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择组件状态', trigger: 'change' }
        ],
        quantity: [
          { required: true, message: '数量不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    totalPriceDisplay() {
      return (this.form.quantity * this.form.unitPrice).toFixed(2)
    }
  },
  watch: {
    'form.quantity'(newVal) {
      this.form.totalPrice = newVal * this.form.unitPrice
    },
    'form.unitPrice'(newVal) {
      this.form.totalPrice = this.form.quantity * newVal
    }
  },
  methods: {
    show(detailData = null) {
      this.visible = true
      if (detailData) {
        // 编辑模式，复制数据
        this.form = { ...detailData }
      } else {
        // 新增模式，重置表单
        this.reset()
      }
    },
    reset() {
      this.form = {
        componentCode: '',
        componentName: '',
        componentType: '',
        status: '',
        specification: '',
        material: '',
        quantity: 1,
        unit: '',
        unitPrice: 0,
        totalPrice: 0,
        supplier: '',
        purchaseDate: '',
        remark: ''
      }
      this.$nextTick(() => {
        this.$refs.form && this.$refs.form.clearValidate()
      })
    },
    handleConfirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          // 计算总价
          this.form.totalPrice = this.form.quantity * this.form.unitPrice
          // 触发select事件，返回编辑后的数据
          this.$emit('select', { ...this.form })
          this.visible = false
        }
      })
    }
  }
}
</script>

<style scoped>
.el-form-item {
  margin-bottom: 18px;
}
</style>
