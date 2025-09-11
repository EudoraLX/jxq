<template>
  <el-dialog :title="title" :visible.sync="visible" width="800px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="货架编码" prop="shelfCode">
            <el-input v-model="form.shelfCode" placeholder="请输入货架编码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="isActive">
            <el-radio-group v-model="form.isActive">
              <el-radio
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="货架名称" prop="shelfName">
            <el-input v-model="form.shelfName" placeholder="请输入货架名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="货架类型" prop="shelfType">
            <el-select v-model="form.shelfType" placeholder="请选择货架类型" style="width: 100%">
              <el-option
                v-for="dict in dict.type.shelf_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="区域编码" prop="areaCode">
            <el-input v-model="form.areaCode" placeholder="请输入区域编码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="行数" prop="rowNum">
            <el-input-number v-model="form.rowNum" :min="1" placeholder="请输入行数" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="列数" prop="columnNum">
            <el-input-number v-model="form.columnNum" :min="1" placeholder="请输入列数" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="高度(cm)" prop="height">
            <el-input-number v-model="form.height" :min="0" placeholder="请输入高度" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="宽度(cm)" prop="width">
            <el-input-number v-model="form.width" :min="0" placeholder="请输入宽度" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="深度(cm)" prop="depth">
            <el-input-number v-model="form.depth" :min="0" placeholder="请输入深度" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="最大承重(kg)" prop="maxWeight">
            <el-input-number v-model="form.maxWeight" :min="0" placeholder="请输入最大承重" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" placeholder="请输入备注内容" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addShelf, updateShelf } from "@/api/erp/shelf"

export default {
  name: "ShelfFormDialog",
  dicts: ['shelf_type', 'sys_yes_no'],
  props: {
    value: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ""
    },
    shelfData: {
      type: Object,
      default: () => ({})
    },
    warehouseId: {
      type: [String, Number],
      default: null
    },
    warehouseData: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      form: {},
      rules: {
        shelfCode: [
          { required: true, message: "货架编码不能为空", trigger: "blur" }
        ],
        shelfName: [
          { required: true, message: "货架名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "所属仓库不能为空", trigger: "change" }
        ],
        shelfType: [
          { required: true, message: "货架类型不能为空", trigger: "change" }
        ]
      }
    }
  },
  computed: {
    visible: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  },
  watch: {
    value(val) {
      if (val) {
        this.initForm()
      }
    }
  },
  methods: {
    initForm() {
      this.form = {
        id: this.shelfData.id,
        shelfCode: this.shelfData.shelfCode,
        shelfName: this.shelfData.shelfName,
        warehouseId: this.warehouseId || this.shelfData.warehouseId,
        shelfType: this.shelfData.shelfType,
        areaCode: this.shelfData.areaCode,
        rowNum: this.shelfData.rowNum,
        columnNum: this.shelfData.columnNum,
        height: this.shelfData.height,
        width: this.shelfData.width,
        depth: this.shelfData.depth,
        maxWeight: this.shelfData.maxWeight,
        remark: this.shelfData.remark,
        isActive: this.shelfData.isActive || '1'
      }
      
      console.log('Shelf form initialized:', this.form)
    },
    
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log('Submitting shelf data:', this.form)
          
          if (this.form.id != null) {
            // 修改货架
            updateShelf(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.$emit('success')
            }).catch(error => {
              console.error('Shelf update error:', error)
              this.$modal.msgError("修改失败")
            })
          } else {
            // 新增货架
            addShelf(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.$emit('success')
            }).catch(error => {
              console.error('Shelf add error:', error)
              this.$modal.msgError("新增失败")
            })
          }
        }
      })
    },
    
    cancel() {
      this.visible = false
      this.reset()
    },
    
    reset() {
      this.form = {}
      this.resetForm("form")
      console.log('Shelf form reset completed')
    }
  }
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
