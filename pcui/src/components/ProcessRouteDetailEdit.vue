<template>
  <el-dialog title="编辑工艺路线明细" :visible.sync="visible" width="1000px" top="5vh" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="工序顺序" prop="operationSequence">
            <el-input-number v-model="form.operationSequence" :min="1" :max="999" placeholder="请输入工序顺序" style="width: 100%;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工序" prop="processId">
            <div style="display: flex; align-items: center;">
              <div style="flex: 1; position: relative;" @click="showProcessSelect">
                <el-input
                  v-model="processDisplay"
                  placeholder="请选择工序"
                  readonly
                  style="background-color: #f5f7fa; cursor: pointer;"
                  prefix-icon="el-icon-s-operation"
                />
              </div>
              <el-button
                type="primary"
                icon="el-icon-arrow-down"
                size="mini"
                @click="showProcessSelect"
                style="margin-left: 10px;"
              >
                选择
              </el-button>
              <el-button
                v-if="form.processId"
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="clearProcess"
                style="margin-left: 5px;"
              >
                清除
              </el-button>
            </div>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注信息" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="人工工时(小时)" prop="laborHours">
            <el-input-number v-model="form.laborHours" :precision="2" :step="0.1" placeholder="请输入人工工时" style="width: 100%;" :min="0" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="机器工时(小时)" prop="machineHours">
            <el-input-number v-model="form.machineHours" :precision="2" :step="0.1" placeholder="请输入机器工时" style="width: 100%;" :min="0" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider content-position="left">工序属性</el-divider>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="是否关键工序" prop="isCritical">
            <el-radio-group v-model="form.isCritical">
              <el-radio
                v-for="dict in dict.type.business_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否可选工序" prop="isOptional">
            <el-radio-group v-model="form.isOptional">
              <el-radio
                v-for="dict in dict.type.business_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </div>

    <!-- 弹窗选择组件 -->
    <process-select ref="processSelect" @select="handleProcessSelect" />
  </el-dialog>
</template>

<script>
import ProcessSelect from '@/components/ProcessSelect'

export default {
  name: "ProcessRouteDetailEdit",
  components: {
    ProcessSelect
  },
  dicts: ['business_yes_no'],
  data() {
    return {
      visible: false,
      form: {
        id: null,
        processRouteId: null,
        processId: null,
        operationSequence: 1,
        laborHours: 0,
        machineHours: 0,
        isCritical: '0',
        isOptional: '0',
        remark: null
      },
      rules: {
        operationSequence: [
          { required: true, message: "工序顺序不能为空", trigger: "blur" }
        ],
        processId: [
          { required: true, message: "工序不能为空", trigger: "blur" }
        ]
      },
      processDisplay: ""
    }
  },
  watch: {
    visible(val) {
      if (!val) {
        this.reset()
      }
    },
  },
  methods: {
    show(detailData = null) {
      this.visible = true
      if (detailData) {
        this.form = { ...detailData }
        this.setDisplays()
      } else {
        this.reset()
      }
    },
    reset() {
      this.form = {
        id: null,
        processRouteId: null,
        processId: null,
        operationSequence: 1,
        laborHours: 0,
        machineHours: 0,
        isCritical: '0',
        isOptional: '0',
        remark: null
      }
      this.processDisplay = ""
      this.resetForm("form")
    },
    handleConfirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('select', { ...this.form })
          this.visible = false
        }
      })
    },
    /** 显示工序选择弹窗 */
    showProcessSelect() {
      this.$refs.processSelect.show()
    },
    /** 清空工序选择 */
    clearProcess() {
      this.form.processId = null
      this.form.processCode = null
      this.form.processName = null
      this.processDisplay = ""
    },
    /** 处理工序选择 */
    handleProcessSelect(selectedProcess) {
      this.form.processId = selectedProcess.id
      this.form.processCode = selectedProcess.processCode
      this.form.processName = selectedProcess.processName
      this.processDisplay = selectedProcess.processCode + ' - ' + selectedProcess.processName
    },
    setDisplays() {
      // 设置显示值，使用编码-名称格式
      if (this.form.processCode && this.form.processName) {
        this.processDisplay = this.form.processCode + ' - ' + this.form.processName
      } else if (this.form.processId) {
        this.processDisplay = "ID: " + this.form.processId
      } else {
        this.processDisplay = ""
      }
    }
  }
}
</script>
