<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入工序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作员" prop="operatorName">
        <el-input
          v-model="queryParams.operatorName"
          placeholder="请输入操作员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报工日期">
        <el-date-picker
          v-model="daterangeReportDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="审批状态" prop="approvalStatus">
        <el-select v-model="queryParams.approvalStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.approval_status"
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
          v-hasPermi="['erp:productionReport:add']"
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
          v-hasPermi="['erp:productionReport:edit']"
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
          v-hasPermi="['erp:productionReport:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:productionReport:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productionReportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报工单编码" align="center" prop="reportCode" />
      <el-table-column label="生产订单编码" align="center" prop="orderCode" />
      <el-table-column label="产品编码" align="center" prop="productCode" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="工序编码" align="center" prop="processCode" />
      <el-table-column label="工序名称" align="center" prop="processName" />
      <el-table-column label="操作员" align="center" prop="operatorName" />
      <el-table-column label="报工日期" align="center" prop="reportDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reportDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报工数量" align="center" prop="actualQuantity" />
      <el-table-column label="合格数量" align="center" prop="qualifiedQuantity" />
      <el-table-column label="不良数量" align="center" prop="defectiveQuantity" />
      <el-table-column label="报废数量" align="center" prop="scrapQuantity" />
      <el-table-column label="返工数量" align="center" prop="reworkQuantity" />
      <el-table-column label="实际工时" align="center" prop="actualHours" />
      <el-table-column label="合格率" align="center" prop="qualityRate" />
      <el-table-column label="审批状态" align="center" prop="approvalStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.approval_status" :value="scope.row.approvalStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:productionReport:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:productionReport:remove']"
          >删除</el-button>
          <!-- 提交按钮：审批状态为0且有编辑权限时显示 -->
          <el-button
            v-if="scope.row.approvalStatus === '0'"
            size="mini"
            type="text"
            icon="el-icon-upload"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:productionReport:edit']"
          >提交</el-button>
          <!-- 审批按钮：审批状态为1且有审批权限时显示 -->
          <el-button
            v-if="scope.row.approvalStatus === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApproval(scope.row)"
            v-hasPermi="['erp:productionReport:approval']"
          >审批</el-button>
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

    <!-- 添加或修改生产报工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="报工单编码" prop="reportCode">
              <el-input v-model="form.reportCode" placeholder="系统自动生成" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报工日期" prop="reportDate">
              <el-date-picker clearable
                v-model="form.reportDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择报工日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产订单" prop="productionOrderId">
              <el-input v-model="form.orderCode" placeholder="请选择生产订单" readonly @click="selectProductionOrder">
                <el-button slot="append" icon="el-icon-search" @click="selectProductionOrder"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工序" prop="processId">
              <el-input v-model="processDisplayName" placeholder="请选择工序" readonly @click="selectProcess">
                <el-button slot="append" icon="el-icon-search" @click="selectProcess"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产品" prop="productId">
              <el-input v-model="productDisplayName" placeholder="自动带出" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位" prop="unitId">
              <el-input v-model="form.unitName" placeholder="自动带出" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作员" prop="operatorId">
              <el-input v-model="form.operatorName" placeholder="请选择操作员" readonly @click="selectOperator">
                <el-button slot="append" icon="el-icon-search" @click="selectOperator"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实际工时" prop="actualHours">
              <el-input v-model="form.actualHours" placeholder="请输入实际工时" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="合格数量" prop="qualifiedQuantity">
              <el-input v-model="form.qualifiedQuantity" placeholder="请输入合格数量" @input="calculateQuantities" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="不良数量" prop="defectiveQuantity">
              <el-input v-model="form.defectiveQuantity" placeholder="请输入不良数量" @input="calculateQuantities" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="报废数量" prop="scrapQuantity">
              <el-input v-model="form.scrapQuantity" placeholder="请输入报废数量" @input="calculateQuantities" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="返工数量" prop="reworkQuantity">
              <el-input v-model="form.reworkQuantity" placeholder="请输入返工数量" @input="calculateQuantities" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="报工数量" prop="actualQuantity">
              <el-input v-model="form.actualQuantity" placeholder="自动计算" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合格率" prop="qualityRate">
              <el-input v-model="form.qualityRate" placeholder="自动计算" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 生产订单选择对话框 -->
    <el-dialog title="选择生产订单" :visible.sync="productionOrderDialogVisible" width="800px" append-to-body>
      <el-table v-loading="productionOrderLoading" :data="productionOrderList" @row-click="selectProductionOrderRow">
        <el-table-column label="订单编码" align="center" prop="orderCode" />
        <el-table-column label="订单名称" align="center" prop="orderName" />
        <el-table-column label="产品编码" align="center" prop="productCode" />
        <el-table-column label="产品名称" align="center" prop="productName" />
        <el-table-column label="计划数量" align="center" prop="plannedQuantity" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.production_order_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="productionOrderTotal>0"
        :total="productionOrderTotal"
        :page.sync="productionOrderQueryParams.pageNum"
        :limit.sync="productionOrderQueryParams.pageSize"
        @pagination="getProductionOrderList"
      />
    </el-dialog>

    <!-- 工序选择对话框 -->
    <el-dialog title="选择工序" :visible.sync="processDialogVisible" width="600px" append-to-body>
      <el-table v-loading="processLoading" :data="processList" @row-click="selectProcessRow">
        <el-table-column label="工序编码" align="center" prop="process_code" />
        <el-table-column label="工序名称" align="center" prop="process_name" />
        <el-table-column label="工序顺序" align="center" prop="process_order" />
        <el-table-column label="标准工时" align="center" prop="standardTime" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.process_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="processTotal>0"
        :total="processTotal"
        :page.sync="processQueryParams.pageNum"
        :limit.sync="processQueryParams.pageSize"
        @pagination="getProcessList"
      />
    </el-dialog>

    <!-- 操作员选择对话框 -->
    <el-dialog title="选择操作员" :visible.sync="operatorDialogVisible" width="600px" append-to-body>
      <el-table v-loading="operatorLoading" :data="operatorList" @row-click="selectOperatorRow">
        <el-table-column label="用户ID" align="center" prop="userId" />
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="昵称" align="center" prop="nickName" />
        <el-table-column label="部门" align="center" prop="deptName" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="operatorTotal>0"
        :total="operatorTotal"
        :page.sync="operatorQueryParams.pageNum"
        :limit.sync="operatorQueryParams.pageSize"
        @pagination="getOperatorList"
      />
    </el-dialog>
  </div>
</template>

<script>
import { listProductionReport, getProductionReport, delProductionReport, addProductionReport, updateProductionReport } from "@/api/erp/productionReport";
import { listProductionOrder, getProductionOrderProcesses } from "@/api/erp/productionOrder";
import { listProcess } from "@/api/erp/process";
import { listUser } from "@/api/system/user";

export default {
  name: "ProductionReport",
  dicts: ['approval_status', 'production_order_status', 'process_status', 'sys_normal_disable'],
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
      // 生产报工单表格数据
      productionReportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 报工日期范围
      daterangeReportDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reportCode: null,
        productionOrderId: null,
        productId: null,
        processId: null,
        operatorId: null,
        reportDate: null,
        approvalStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productionOrderId: [
          { required: true, message: "生产订单不能为空", trigger: "change" }
        ],
        productId: [
          { required: true, message: "产品不能为空", trigger: "change" }
        ],
        unitId: [
          { required: true, message: "单位不能为空", trigger: "change" }
        ],
        processId: [
          { required: true, message: "工序不能为空", trigger: "change" }
        ],
        operatorId: [
          { required: true, message: "操作员不能为空", trigger: "change" }
        ],
        reportDate: [
          { required: true, message: "报工日期不能为空", trigger: "blur" }
        ],
        actualQuantity: [
          { required: true, message: "报工数量不能为空", trigger: "blur" },
          { 
            validator: (rule, value, callback) => {
              if (value == null || value === '' || parseFloat(value) <= 0) {
                callback(new Error('报工数量必须大于0'));
              } else {
                callback();
              }
            }, 
            trigger: "blur" 
          }
        ]
      },
      // 生产订单选择相关
      productionOrderDialogVisible: false,
      productionOrderLoading: false,
      productionOrderList: [],
      productionOrderTotal: 0,
      productionOrderQueryParams: {
        pageNum: 1,
        pageSize: 10,
        status: '2' // 只显示已下达的订单
      },
      // 工序选择相关
      processDialogVisible: false,
      processLoading: false,
      processList: [],
      processTotal: 0,
      processQueryParams: {
        pageNum: 1,
        pageSize: 10,
        status: '1' // 只显示启用的工序
      },
      // 操作员选择相关
      operatorDialogVisible: false,
      operatorLoading: false,
      operatorList: [],
      operatorTotal: 0,
      operatorQueryParams: {
        pageNum: 1,
        pageSize: 10,
        status: '0' // 只显示正常状态用户
      }
    };
  },
  created() {
    this.getList();
  },
  computed: {
    // 产品显示名称
    productDisplayName() {
      if (this.form.productCode && this.form.productName) {
        return `${this.form.productCode} - ${this.form.productName}`;
      }
      return '';
    },
    // 工序显示名称
    processDisplayName() {
      if (this.form.processCode && this.form.processName) {
        return `${this.form.processCode} - ${this.form.processName}`;
      }
      return '';
    }
  },
  methods: {
    /** 查询生产报工单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeReportDate && '' != this.daterangeReportDate) {
        this.queryParams.params["beginReportDate"] = this.daterangeReportDate[0];
        this.queryParams.params["endReportDate"] = this.daterangeReportDate[1];
      }
      listProductionReport(this.queryParams).then(response => {
        this.productionReportList = response.rows;
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
        reportCode: null,
        productionOrderId: null,
        orderCode: null,
        productId: null,
        productCode: null,
        productName: null,
        unitId: null,
        unitCode: null,
        unitName: null,
        processId: null,
        processCode: null,
        processName: null,
        operatorId: null,
        operatorName: null,
        reportDate: null,
        actualQuantity: null,
        qualifiedQuantity: null,
        defectiveQuantity: null,
        scrapQuantity: null,
        reworkQuantity: null,
        actualHours: null,
        qualityRate: null,
        approvalStatus: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeReportDate = [];
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
      this.form.reportDate = this.parseTime(new Date(), '{y}-{m}-{d}');
      this.open = true;
      this.title = "添加生产报工单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProductionReport(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改生产报工单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProductionReport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductionReport(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除生产报工单编号为"' + ids + '"的数据项？').then(function() {
        return delProductionReport(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/productionReport/export', {
        ...this.queryParams
      }, `productionReport_${new Date().getTime()}.xlsx`)
    },
    // 计算数量
    calculateQuantities() {
      const qualified = parseFloat(this.form.qualifiedQuantity) || 0;
      const defective = parseFloat(this.form.defectiveQuantity) || 0;
      const scrap = parseFloat(this.form.scrapQuantity) || 0;
      const rework = parseFloat(this.form.reworkQuantity) || 0;
      
      const actual = qualified + defective + scrap + rework;
      this.form.actualQuantity = actual;
      
      if (actual > 0) {
        this.form.qualityRate = (qualified / actual).toFixed(4);
      } else {
        this.form.qualityRate = 0;
      }
    },
    // 选择生产订单
    selectProductionOrder() {
      this.productionOrderDialogVisible = true;
      this.getProductionOrderList();
    },
    // 获取生产订单列表
    getProductionOrderList() {
      this.productionOrderLoading = true;
      listProductionOrder(this.productionOrderQueryParams).then(response => {
        this.productionOrderList = response.rows;
        this.productionOrderTotal = response.total;
        this.productionOrderLoading = false;
      });
    },
    // 选择生产订单行
    selectProductionOrderRow(row) {
      this.form.productionOrderId = row.id;
      this.form.orderCode = row.orderCode;
      this.form.productId = row.productId;
      this.form.productCode = row.productCode;
      this.form.productName = row.productName;
      this.form.unitId = row.unitId;
      this.form.unitCode = row.unitCode;
      this.form.unitName = row.unitName;
      this.productionOrderDialogVisible = false;
      
      // 清空工序选择
      this.form.processId = null;
      this.form.processCode = '';
      this.form.processName = '';
      
      // 根据生产订单查询对应的工序
      this.loadOrderProcesses(row.id);
    },
    // 选择工序
    selectProcess() {
      if (!this.form.productionOrderId) {
        this.$modal.msgWarning("请先选择生产订单");
        return;
      }
      this.processDialogVisible = true;
      this.getProcessList();
    },
    // 获取工序列表
    getProcessList() {
      this.processLoading = true;
      // 根据生产订单ID查询对应的工序
      getProductionOrderProcesses(this.form.productionOrderId).then(response => {
        this.processList = response.data || [];
        this.processTotal = this.processList.length;
        this.processLoading = false;
      }).catch(() => {
        this.processLoading = false;
      });
    },
    // 选择工序行
    selectProcessRow(row) {
      this.form.processId = row.id;
      this.form.processCode = row.process_code;
      this.form.processName = row.process_name;
      this.processDialogVisible = false;
    },
    // 加载订单对应的工序
    loadOrderProcesses(orderId) {
      if (!orderId) return;
      getProductionOrderProcesses(orderId).then(response => {
        // 将工序数据存储到processList中，供选择使用
        this.processList = response.data || [];
      });
    },
    // 选择操作员
    selectOperator() {
      this.operatorDialogVisible = true;
      this.getOperatorList();
    },
    // 获取操作员列表
    getOperatorList() {
      this.operatorLoading = true;
      listUser(this.operatorQueryParams).then(response => {
        this.operatorList = response.rows;
        this.operatorTotal = response.total;
        this.operatorLoading = false;
      });
    },
    // 选择操作员行
    selectOperatorRow(row) {
      this.form.operatorId = row.userId;
      this.form.operatorName = row.nickName;
      this.operatorDialogVisible = false;
    },
    // 提交报工单
    handleSubmit(row) {
      this.$modal.confirm('确认提交该报工单吗？').then(() => {
        const data = {
          id: row.id,
          approvalStatus: '1'
        };
        updateProductionReport(data).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.getList();
        });
      });
    },
    // 审批报工单
    handleApproval(row) {
      this.$modal.confirm('确认审批通过该报工单吗？').then(() => {
        const data = {
          id: row.id,
          approvalStatus: '2'
        };
        updateProductionReport(data).then(response => {
          this.$modal.msgSuccess("审批成功");
          this.getList();
        });
      });
    }
  }
};
</script>
