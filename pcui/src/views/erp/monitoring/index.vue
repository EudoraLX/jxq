<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="生产订单" prop="productionOrderId">
        <el-input
          v-model="queryParams.productionOrderCode"
          placeholder="请输入生产订单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工艺" prop="processId">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入工艺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作中心" prop="workCenterId">
        <el-input
          v-model="queryParams.workCenterName"
          placeholder="请输入工作中心名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监控时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="是否正常" prop="isNormal">
        <el-select v-model="queryParams.isNormal" placeholder="请选择是否正常" clearable>
          <el-option label="正常" value="1" />
          <el-option label="异常" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="报警等级" prop="alarmLevel">
        <el-select v-model="queryParams.alarmLevel" placeholder="请选择报警等级" clearable>
          <el-option label="无报警" value="0" />
          <el-option label="轻微报警" value="1" />
          <el-option label="严重报警" value="2" />
          <el-option label="紧急报警" value="3" />
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
          v-hasPermi="['erp:monitoring:add']"
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
          v-hasPermi="['erp:monitoring:edit']"
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
          v-hasPermi="['erp:monitoring:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:monitoring:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processMonitoringList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生产订单编码" align="center" prop="productionOrderCode" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="工艺名称" align="center" prop="processName" />
      <el-table-column label="工作中心" align="center" prop="workCenterName" />
      <el-table-column label="设备" align="center" prop="equipmentName" />
      <el-table-column label="模具" align="center" prop="moldName" />
      <el-table-column label="监控时间" align="center" prop="monitoringTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.monitoringTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="监控人" align="center" prop="inspectorName" />
      <el-table-column label="参数名称" align="center" prop="parameterName" />
      <el-table-column label="标准值" align="center" prop="standardValue" />
      <el-table-column label="监控值" align="center" prop="actualValue" />
      <el-table-column label="偏差" align="center" prop="deviation" />
      <el-table-column label="偏差率" align="center" prop="deviationRate">
        <template slot-scope="scope">
          <span v-if="scope.row.deviationRate">{{ (scope.row.deviationRate * 100).toFixed(2) }}%</span>
        </template>
      </el-table-column>
      <el-table-column label="是否正常" align="center" prop="isNormal">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isNormal === '1' ? 'success' : 'danger'">
            {{ scope.row.isNormal === '1' ? '正常' : '异常' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="报警等级" align="center" prop="alarmLevel">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.alarmLevel === '0'" type="info">无报警</el-tag>
          <el-tag v-else-if="scope.row.alarmLevel === '1'" type="warning">轻微报警</el-tag>
          <el-tag v-else-if="scope.row.alarmLevel === '2'" type="danger">严重报警</el-tag>
          <el-tag v-else-if="scope.row.alarmLevel === '3'" type="danger">紧急报警</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:monitoring:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:monitoring:remove']"
          >删除</el-button>
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

    <!-- 添加或修改工艺参数监控对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产订单" prop="productionOrderId">
              <el-input
                v-model="form.productionOrderCode"
                placeholder="请选择生产订单"
                readonly
                @click.native="handleSelectOrder"
              >
                <el-button slot="append" icon="el-icon-search" @click="handleSelectOrder"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工序" prop="processId">
              <el-input
                v-model="processDisplayName"
                placeholder="请选择工序"
                readonly
                @click="selectProcess"
                :disabled="!form.productionOrderId"
              >
                <el-button slot="append" icon="el-icon-search" @click="selectProcess" :disabled="!form.productionOrderId"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工作中心" prop="workCenterId">
              <el-input v-model="workCenterDisplay" placeholder="自动加载" readonly style="background-color: #f5f7fa;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备" prop="equipmentId">
              <el-input v-model="equipmentDisplay" placeholder="自动加载" readonly style="background-color: #f5f7fa;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="模具" prop="moldId">
              <el-input v-model="moldDisplay" placeholder="自动加载" readonly style="background-color: #f5f7fa;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="监控时间" prop="monitoringTime">
              <el-date-picker
                v-model="form.monitoringTime"
                type="datetime"
                placeholder="选择监控时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                :disabled="true"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="监控人" prop="inspectorId">
              <el-input v-model="form.inspectorName" placeholder="监控人" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工艺参数" prop="parameterId">
              <el-select v-model="form.parameterId" placeholder="请选择工艺参数" @change="handleParameterChange">
                <el-option
                  v-for="item in parameterOptions"
                  :key="item.parameterId"
                  :label="item.parameterCode + ' - ' + item.parameterName"
                  :value="item.parameterId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="标准值" prop="standardValue">
              <el-input v-model="form.standardValue" placeholder="标准值" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="允许最小值" prop="minValue">
              <el-input v-model="form.minValue" placeholder="允许最小值" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="允许最大值" prop="maxValue">
              <el-input v-model="form.maxValue" placeholder="允许最大值" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="监控值" prop="actualValue">
              <el-input v-model="form.actualValue" placeholder="请输入监控值" @input="handleActualValueChange" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参数单位" prop="parameterUnit">
              <el-input v-model="form.parameterUnit" placeholder="参数单位" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="实际偏差" prop="deviation">
              <el-input v-model="form.deviation" placeholder="实际偏差" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="偏差率" prop="deviationRate">
              <el-input v-model="form.deviationRate" placeholder="偏差率" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否正常" prop="isNormal">
              <el-tag :type="form.isNormal === '1' ? 'success' : 'danger'">
                {{ form.isNormal === '1' ? '正常' : '异常' }}
              </el-tag>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报警等级" prop="alarmLevel">
              <el-tag v-if="form.alarmLevel === '0'" type="info">无报警</el-tag>
              <el-tag v-else-if="form.alarmLevel === '1'" type="warning">轻微报警</el-tag>
              <el-tag v-else-if="form.alarmLevel === '2'" type="danger">严重报警</el-tag>
              <el-tag v-else-if="form.alarmLevel === '3'" type="danger">紧急报警</el-tag>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="报警信息" prop="alarmMessage">
          <el-input v-model="form.alarmMessage" type="textarea" placeholder="报警信息" readonly />
        </el-form-item>
        <el-form-item label="控制方法" prop="controlMethod">
          <el-input v-model="form.controlMethod" type="textarea" placeholder="请输入控制方法" />
        </el-form-item>
        <el-form-item label="应对行动" prop="adjustmentAction">
          <el-input v-model="form.adjustmentAction" type="textarea" placeholder="请输入应对行动" />
        </el-form-item>
        <el-form-item label="下次监控" prop="nextMonitoringTime">
          <el-date-picker
            v-model="form.nextMonitoringTime"
            type="datetime"
            placeholder="选择下次监控时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 生产订单选择对话框 -->
    <el-dialog title="选择生产订单" :visible.sync="orderDialogVisible" width="1000px" append-to-body>
      <el-table v-loading="orderLoading" :data="orderList" @row-click="handleOrderSelect">
        <el-table-column label="订单编码" align="center" prop="orderCode" width="150" />
        <el-table-column label="产品" align="center" prop="productCode" width="200">
          <template slot-scope="scope">
            {{ scope.row.productCode }} - {{ scope.row.productName }}
          </template>
        </el-table-column>
        <el-table-column label="单位" align="center" prop="unitName" width="100" />
        <el-table-column label="计划数量" align="center" prop="plannedQuantity" width="100" />
        <el-table-column label="状态" align="center" prop="status" width="100">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.production_order_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="orderTotal>0"
        :total="orderTotal"
        :page.sync="orderQueryParams.pageNum"
        :limit.sync="orderQueryParams.pageSize"
        @pagination="getOrderList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button @click="orderDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 工序选择对话框 -->
    <el-dialog title="选择工序" :visible.sync="processDialogVisible" width="1000px" append-to-body>
      <el-table v-loading="processLoading" :data="processList" @row-click="selectProcessRow">
        <el-table-column label="工序编码" align="center" prop="processCode" />
        <el-table-column label="工序名称" align="center" prop="processName" />
        <el-table-column label="工作中心" align="center" prop="workCenterCode">
          <template slot-scope="scope">
            {{ scope.row.workCenterCode }} - {{ scope.row.workCenterName }}
          </template>
        </el-table-column>
        <el-table-column label="设备" align="center" prop="equipmentCode">
          <template slot-scope="scope">
            {{ scope.row.equipmentCode }} - {{ scope.row.equipmentName }}
          </template>
        </el-table-column>
        <el-table-column label="模具" align="center" prop="moldCode">
          <template slot-scope="scope">
            {{ scope.row.moldCode }} - {{ scope.row.moldName }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listProcessMonitoring, getProcessMonitoring, delProcessMonitoring, addProcessMonitoring, updateProcessMonitoring, getProcessParameters, getWorkCenterInfo, calculateDeviation, getProcessRouteDetails } from "@/api/erp/processMonitoring";
import { listProductionOrder } from "@/api/erp/productionOrder";

export default {
  name: "ProcessMonitoring",
  dicts: ['production_order_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工艺参数监控表格数据
      processMonitoringList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productionOrderId: null,
        processId: null,
        workCenterId: null,
        equipmentId: null,
        moldId: null,
        monitoringTime: null,
        inspectorId: null,
        parameterId: null,
        isNormal: null,
        alarmLevel: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productionOrderId: [
          { required: true, message: "生产订单不能为空", trigger: "change" }
        ],
        processId: [
          { required: true, message: "工艺不能为空", trigger: "change" }
        ],
        workCenterId: [
          { required: true, message: "工作中心不能为空", trigger: "change" }
        ],
        actualValue: [
          { required: true, message: "监控值不能为空", trigger: "blur" }
        ]
      },
      // 工艺参数选项
      parameterOptions: [],
      // 生产订单选择对话框
      orderDialogVisible: false,
      orderLoading: false,
      orderList: [],
      orderTotal: 0,
      orderQueryParams: {
        pageNum: 1,
        pageSize: 10,
        status: '2'
      },
      // 工艺选择对话框
      processDialogVisible: false,
      processLoading: false,
      processList: [],
      // 显示字段
      workCenterDisplay: "",
      equipmentDisplay: "",
      moldDisplay: ""
    };
  },
  computed: {
    processDisplayName() {
      if (this.form.processCode && this.form.processName) {
        return `${this.form.processCode} - ${this.form.processName}`;
      }
      return '';
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工艺参数监控列表 */
    getList() {
      this.loading = true;
      listProcessMonitoring(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.processMonitoringList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        productionOrderId: null,
        productionOrderCode: null,
        productId: null,
        productCode: null,
        productName: null,
        processId: null,
        processCode: null,
        processName: null,
        workCenterId: null,
        workCenterCode: null,
        workCenterName: null,
        equipmentId: null,
        equipmentCode: null,
        equipmentName: null,
        moldId: null,
        moldCode: null,
        moldName: null,
        monitoringTime: null,
        inspectorId: null,
        inspectorName: null,
        parameterId: null,
        parameterCode: null,
        parameterName: null,
        parameterUnit: null,
        standardValue: null,
        actualValue: null,
        minValue: null,
        maxValue: null,
        deviation: null,
        deviationRate: null,
        isNormal: "1",
        alarmLevel: "0",
        alarmMessage: null,
        controlMethod: null,
        adjustmentAction: null,
        nextMonitoringTime: null,
        remark: null
      };
      this.workCenterDisplay = "";
      this.equipmentDisplay = "";
      this.moldDisplay = "";
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工艺参数监控";
      // 设置默认值
      this.form.monitoringTime = this.formatDateTime(new Date());
      this.form.inspectorId = this.$store.state.user.id;
      this.form.inspectorName = this.$store.state.user.nickName;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProcessMonitoring(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工艺参数监控";
        // 加载工艺参数选项
        if (this.form.processId) {
          this.loadProcessParameters(this.form.processId);
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProcessMonitoring(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcessMonitoring(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除工艺参数监控编号为"' + ids + '"的数据项？').then(function() {
        return delProcessMonitoring(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/monitoring/export', {
        ...this.queryParams
      }, `工艺参数监控_${new Date().getTime()}.xlsx`)
    },
    /** 选择生产订单 */
    handleSelectOrder() {
      this.orderDialogVisible = true;
      this.getOrderList();
    },
    /** 获取生产订单列表 */
    getOrderList() {
      this.orderLoading = true;
      listProductionOrder(this.orderQueryParams).then(response => {
        this.orderList = response.rows;
        this.orderTotal = response.total;
        this.orderLoading = false;
      });
    },
    /** 选择生产订单行 */
    handleOrderSelect(row) {
      this.form.productionOrderId = row.id;
      this.form.productionOrderCode = row.orderCode;
      this.form.productId = row.productId;
      this.form.productCode = row.productCode;
      this.form.productName = row.productName;
      this.form.unitId = row.unitId;
      this.form.unitName = row.unitName;
      this.orderDialogVisible = false;
    },
    /** 选择工序 */
    selectProcess() {
      if (!this.form.productionOrderId) {
        this.$modal.msgWarning("请先选择生产订单");
        return;
      }
      this.processDialogVisible = true;
      this.getOrderProcessList();
    },
    /** 获取生产订单对应的工序列表 */
    getOrderProcessList() {
      this.processLoading = true;
      getProcessRouteDetails(this.form.productionOrderId).then(response => {
        this.processList = response.data;
        this.processLoading = false;
      }).catch(() => {
        this.processLoading = false;
      });
    },
    /** 选择工序行 */
    selectProcessRow(row) {
      this.form.processId = row.processId;
      this.form.processCode = row.processCode;
      this.form.processName = row.processName;
      this.form.workCenterId = row.workCenterId;
      this.form.equipmentId = row.equipmentId;
      this.form.moldId = row.moldId;
      this.workCenterDisplay = row.workCenterCode + " - " + row.workCenterName;
      this.equipmentDisplay = row.equipmentCode + " - " + row.equipmentName;
      this.moldDisplay = row.moldCode + " - " + row.moldName;
      this.processDialogVisible = false;
      // 加载工艺参数选项
      this.loadProcessParameters(row.processId);
    },
    /** 加载工艺参数选项 */
    loadProcessParameters(processId) {
      getProcessParameters(processId).then(response => {
        this.parameterOptions = response.data;
      });
    },
    /** 工艺参数变化 */
    handleParameterChange(parameterId) {
      const parameter = this.parameterOptions.find(item => item.parameterId === parameterId);
      if (parameter) {
        this.form.parameterCode = parameter.parameterCode;
        this.form.parameterName = parameter.parameterName;
        this.form.parameterUnit = parameter.parameterUnit;
        this.form.standardValue = parameter.standardValue;
        this.form.minValue = parameter.minValue;
        this.form.maxValue = parameter.maxValue;
        this.form.controlMethod = parameter.controlMethod;
      }
    },
    /** 监控值变化 */
    handleActualValueChange() {
      if (this.form.actualValue && this.form.standardValue) {
        calculateDeviation(this.form).then(response => {
          this.form = response.data;
        });
      }
    },
    /** 格式化日期时间 */
    formatDateTime(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
  }
};
</script>
