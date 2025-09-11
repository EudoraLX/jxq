<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工序编码" prop="processCode">
        <el-input
          v-model="queryParams.processCode"
          placeholder="请输入工序编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入工序名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序类型" prop="processType">
        <el-select v-model="queryParams.processType" placeholder="请选择工序类型" clearable>
          <el-option
            v-for="dict in dict.type.process_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.business_active"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['erp:process:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:process:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:process:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:process:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-printer"
          size="mini"
          :disabled="multiple"
          @click="handlePrintParameters"
        >打印参数明细</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工序编码" align="center" prop="processCode" />
      <el-table-column label="工序名称" align="center" prop="processName" />
      <el-table-column label="工序类型" align="center" prop="processType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.process_type" :value="scope.row.processType"/>
        </template>
      </el-table-column>
      <el-table-column label="标准时间(分钟)" align="center" prop="standardTime" />
      <el-table-column label="准备时间(分钟)" align="center" prop="setupTime" />
      <el-table-column label="收尾时间(分钟)" align="center" prop="teardownTime" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="条码" align="center" width="120">
        <template slot-scope="scope">
          <barcode-display 
            :value="scope.row.processCode" 
            :width="1.5" 
            :height="40"
            format="CODE128"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:process:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:process:remove']"
          >删除</el-button>
          <barcode-printer 
            :machine-data="getProcessBarcodeData(scope.row)"
            title="工序标签"
            label-title="工序标签"
            :info-labels="processBarcodeLabels"
            :machine-type-dict="[]"
          />
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改工序对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <!-- 基本信息Tab -->
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="工序编码" prop="processCode">
                  <el-input v-model="form.processCode" placeholder="请输入工序编码" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工序名称" prop="processName">
                  <el-input v-model="form.processName" placeholder="请输入工序名称" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="工序类型" prop="processType">
                  <el-select v-model="form.processType" placeholder="请选择工序类型">
                    <el-option
                      v-for="dict in dict.type.process_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态" prop="status">
                  <el-radio-group v-model="form.status">
                    <el-radio
                      v-for="dict in dict.type.business_active"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="标准时间(分钟)" prop="standardTime">
                  <el-input-number v-model="form.standardTime" :precision="2" :step="0.1" placeholder="请输入标准工时" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="准备时间(分钟)" prop="setupTime">
                  <el-input-number v-model="form.setupTime" :precision="2" :step="0.1" placeholder="请输入准备时间" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="收尾时间(分钟)" prop="teardownTime">
                  <el-input-number v-model="form.teardownTime" :precision="2" :step="0.1" placeholder="请输入收尾时间" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 工艺参数明细Tab -->
        <el-tab-pane label="工艺参数明细" name="processPara">
          <el-row :gutter="10" class="mb8" style="padding: 20px 20px 0 20px;">
            <el-col :span="1.5">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddProcessPara">添加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteProcessPara">删除</el-button>
            </el-col>
          </el-row>
          <el-table :data="processParaList" :row-class-name="rowProcessParaIndex" @selection-change="handleProcessParaSelectionChange" ref="processPara" style="margin: 0 20px;" border>
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="序号" align="center" prop="index" width="60"/>
            <el-table-column label="参数类型" prop="parameterType" min-width="120" resizable>
              <template slot-scope="scope">
                <el-select v-model="scope.row.parameterType" placeholder="请选择参数类型">
                  <el-option
                    v-for="dict in dict.type.parameter_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="参数名称" prop="parameterName" min-width="150" resizable>
              <template slot-scope="scope">
                <el-input v-model="scope.row.parameterName" placeholder="请输入参数名称" />
              </template>
            </el-table-column>
            <el-table-column label="参数编码" prop="parameterCode" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input 
                  v-model="scope.row.parameterCode" 
                  placeholder="请输入参数编码" 
                  maxlength="4"
                  :class="{ 'is-error': !isValidParameterCode(scope.row.parameterCode) || isDuplicateParameterCode(scope.row) }"
                  @input="validateParameterCode(scope.row)"
                />
                <div v-if="!isValidParameterCode(scope.row.parameterCode) && scope.row.parameterCode" class="error-message">
                  参数编码只能包含字母和数字，最多4个字符
                </div>
                <div v-if="isDuplicateParameterCode(scope.row) && scope.row.parameterCode" class="error-message">
                  参数编码不能重复
                </div>
              </template>
            </el-table-column>
            <el-table-column label="参数单位" prop="parameterUnit" min-width="100" resizable>
              <template slot-scope="scope">
                <el-input v-model="scope.row.parameterUnit" placeholder="请输入单位" />
              </template>
            </el-table-column>
            <el-table-column label="标准值" prop="standardValue" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.standardValue" :precision="2" :step="0.01" placeholder="标准值" style="width: 100%;" />
              </template>
            </el-table-column>
            <el-table-column label="最小值" prop="minValue" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.minValue" :precision="2" :step="0.01" placeholder="最小值" style="width: 100%;" />
              </template>
            </el-table-column>
            <el-table-column label="最大值" prop="maxValue" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.maxValue" :precision="2" :step="0.01" placeholder="最大值" style="width: 100%;" />
              </template>
            </el-table-column>
            <el-table-column label="公差范围" prop="tolerance" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.tolerance" :precision="2" :step="0.01" placeholder="公差范围" style="width: 100%;" />
              </template>
            </el-table-column>
            <el-table-column label="控制方法" prop="controlMethod" min-width="150" resizable>
              <template slot-scope="scope">
                <el-input v-model="scope.row.controlMethod" placeholder="请输入控制方法" />
              </template>
            </el-table-column>
            <el-table-column label="监控点" prop="monitoringPoint" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input v-model="scope.row.monitoringPoint" placeholder="请输入监控点" />
              </template>
            </el-table-column>
            <el-table-column label="报警上限" prop="alarmUpper" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.alarmUpper" :precision="2" :step="0.01" placeholder="报警上限" style="width: 100%;" />
              </template>
            </el-table-column>
            <el-table-column label="报警下限" prop="alarmLower" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.alarmLower" :precision="2" :step="0.01" placeholder="报警下限" style="width: 100%;" />
              </template>
            </el-table-column>
            <el-table-column label="是否关键参数" prop="isCritical" min-width="120" resizable>
              <template slot-scope="scope">
                <el-select v-model="scope.row.isCritical" placeholder="请选择">
                  <el-option
                    v-for="dict in dict.type.business_yes_no"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="是否监控" prop="isMonitored" min-width="100" resizable>
              <template slot-scope="scope">
                <el-select v-model="scope.row.isMonitored" placeholder="请选择">
                  <el-option
                    v-for="dict in dict.type.business_yes_no"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="排序顺序" prop="sortOrder" min-width="100" resizable>
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.sortOrder" :min="0" placeholder="排序" style="width: 100%;" />
              </template>
            </el-table-column>
            <el-table-column label="监控间隔(分)" prop="monitoringInterval" min-width="120" resizable>
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.monitoringInterval" :min="0" placeholder="监控间隔" style="width: 100%;" />
              </template>
            </el-table-column>
            <el-table-column label="条码" align="center" width="120">
              <template slot-scope="scope">
                <barcode-display 
                  v-if="scope.row.parameterCode && form.processCode" 
                  :value="form.processCode + '-' + scope.row.parameterCode" 
                  :width="1.5" 
                  :height="40"
                  format="CODE128"
                />
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="100">
              <template slot-scope="scope">
                <barcode-printer 
                  v-if="scope.row.parameterCode && form.processCode"
                  :machine-data="getParameterBarcodeData(scope.row)"
                  title="工艺参数标签"
                  label-title="工艺参数标签"
                  :info-labels="parameterBarcodeLabels"
                  :machine-type-dict="[]"
                />
              </template>
            </el-table-column>
            <el-table-column label="备注" prop="remark" min-width="150" resizable>
              <template slot-scope="scope">
                <el-input v-model="scope.row.remark" placeholder="请输入备注" />
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listProcess, getProcess, delProcess, addProcess, updateProcess } from "@/api/erp/process"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import BarcodePrinter from "@/components/BarcodePrinter"

export default {
  name: "Process",
  components: {
    BarcodeDisplay,
    BarcodePrinter
  },
  dicts: ['process_type', 'business_active', 'parameter_type', 'business_yes_no'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedProcessPara: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工序表格数据
      processList: [],
      // 工艺参数明细表格数据
      processParaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的Tab
      activeTab: 'basic',
      // 工艺参数条码标签配置
      parameterBarcodeLabels: {
        code: '条码内容',
        name: '参数名称',
        type: '参数类型',
        workCenter: '工序编码'
      },
      // 工序条码标签配置
      processBarcodeLabels: {
        code: '工序编码',
        name: '工序名称',
        type: '工序类型',
        workCenter: '工作中心'
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processCode: null,
        processName: null,
        processType: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        processCode: [
          { required: true, message: "工序编码不能为空", trigger: "blur" },
          { max: 8, message: "工序编码长度不能超过8个字符", trigger: "blur" },
          { pattern: /^[A-Za-z0-9]+$/, message: "工序编码只能包含字母和数字", trigger: "blur" }
        ],
        processName: [
          { required: true, message: "工序名称不能为空", trigger: "blur" }
        ],
        processType: [
          { required: true, message: "工序类型不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 验证参数编码格式 */
    isValidParameterCode(code) {
      if (!code) return true; // 空值不显示错误
      return /^[A-Za-z0-9]{1,4}$/.test(code);
    },
    /** 检查参数编码是否重复 */
    isDuplicateParameterCode(row) {
      if (!row.parameterCode) return false;
      const currentIndex = this.processParaList.findIndex(item => item === row);
      return this.processParaList.some((item, index) => 
        index !== currentIndex && 
        item.parameterCode && 
        item.parameterCode === row.parameterCode
      );
    },
    /** 验证参数编码输入 */
    validateParameterCode(row) {
      if (row.parameterCode && !this.isValidParameterCode(row.parameterCode)) {
        this.$message.warning('参数编码只能包含字母和数字，最多4个字符');
      } else if (row.parameterCode && this.isDuplicateParameterCode(row)) {
        this.$message.warning('参数编码不能重复');
      }
    },
    /** 检查参数编码重复 */
    checkParameterCodeDuplicates() {
      const codes = [];
      for (let i = 0; i < this.processParaList.length; i++) {
        const row = this.processParaList[i];
        if (row.parameterCode && row.parameterCode.trim()) {
          if (codes.includes(row.parameterCode)) {
            return {
              isValid: false,
              duplicateIndex: i,
              duplicateCode: row.parameterCode
            };
          }
          codes.push(row.parameterCode);
        }
      }
      return { isValid: true };
    },
    /** 获取工艺参数条码数据 */
    getParameterBarcodeData(row) {
      const barcodeData = {
        machineCode: this.form.processCode + '-' + row.parameterCode,
        machineName: row.parameterName,
        machineType: row.parameterType,
        workCenterName: this.form.processCode
      };
      console.log('条码数据:', barcodeData);
      return barcodeData;
    },
    /** 获取工序条码数据 */
    getProcessBarcodeData(row) {
      const barcodeData = {
        machineCode: row.processCode,
        machineName: row.processName,
        machineType: this.getDictLabel(this.dict.type.process_type, row.processType),
        workCenterName: row.workCenterName || '未设置'
      };
      console.log('工序条码数据:', barcodeData);
      return barcodeData;
    },
    /** 获取字典标签 */
    getDictLabel(dictOptions, value) {
      const item = dictOptions.find(item => item.value === value);
      return item ? item.label : value;
    },
    /** 查询工序列表 */
    getList() {
      this.loading = true
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        processCode: null,
        processName: null,
        processType: null,
        standardTime: null,
        setupTime: null,
        teardownTime: null,
        status: "0",
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      }
      this.processParaList = []
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加工序"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProcess(id).then(response => {
        this.form = response.data
        this.processParaList = response.data.erpProcessParaList || []
        this.open = true
        this.title = "修改工序"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 检查参数编码是否有重复
          const duplicateCheck = this.checkParameterCodeDuplicates();
          if (!duplicateCheck.isValid) {
            this.$modal.msgError(`第${duplicateCheck.duplicateIndex + 1}行的参数编码"${duplicateCheck.duplicateCode}"与其他行重复，请修改后重试`);
            return;
          }

          this.form.erpProcessParaList = this.processParaList

          if (this.form.id != null) {
            updateProcess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addProcess(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除工序编号为"' + ids + '"的数据项？').then(function() {
        return delProcess(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/process/export', {
        ...this.queryParams
      }, `process_${new Date().getTime()}.xlsx`)
    },
    /** 打印参数明细按钮操作 */
    handlePrintParameters() {
      if (this.ids.length === 0) {
        this.$modal.msgError("请先选择要打印的工序");
        return;
      }
      
      // 跳转到打印页面
      const ids = this.ids.join(',');
      this.$router.push({
        path: '/erp/process/printParameters',
        query: { ids: ids }
      });
    },
    /** 工艺参数明细序号 */
    rowProcessParaIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 工艺参数明细添加按钮操作 */
    handleAddProcessPara() {
      let obj = {
        id: null,
        processId: null,
        parameterType: null,
        parameterName: null,
        parameterCode: null,
        parameterUnit: null,
        standardValue: null,
        minValue: null,
        maxValue: null,
        tolerance: null,
        controlMethod: null,
        monitoringPoint: null,
        alarmUpper: null,
        alarmLower: null,
        isCritical: "0",
        isMonitored: "1",
        sortOrder: 0,
        monitoringInterval: null,
        remark: null
      }
      this.processParaList.push(obj)
    },
    /** 工艺参数明细删除按钮操作 */
    handleDeleteProcessPara() {
      if (this.checkedProcessPara.length == 0) {
        this.$modal.msgError("请先选择要删除的工艺参数明细数据")
      } else {
        const processParaList = this.processParaList
        const checkedProcessPara = this.checkedProcessPara
        this.processParaList = processParaList.filter(function(item) {
          return checkedProcessPara.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleProcessParaSelectionChange(selection) {
      this.checkedProcessPara = selection.map(item => item.index)
    }
  }
}
</script>

<style scoped>
.is-error {
  border-color: #f56c6c;
}

.error-message {
  color: #f56c6c;
  font-size: 12px;
  margin-top: 4px;
}

</style>
