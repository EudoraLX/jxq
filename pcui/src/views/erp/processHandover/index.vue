<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="交接单编码" prop="handoverCode">
        <el-input
          v-model="queryParams.handoverCode"
          placeholder="请输入交接单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交接单名称" prop="handoverName">
        <el-input
          v-model="queryParams.handoverName"
          placeholder="请输入交接单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产订单" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入生产订单"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交接日期" prop="handoverDate">
        <el-date-picker clearable
          v-model="queryParams.handoverDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择交接日期">
        </el-date-picker>
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
          v-hasPermi="['erp:processHandover:add']"
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
          v-hasPermi="['erp:processHandover:edit']"
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
          v-hasPermi="['erp:processHandover:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:processHandover:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processHandoverList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="交接单编码" align="center" prop="handoverCode" />
      <el-table-column label="交接单名称" align="center" prop="handoverName" />
      <el-table-column label="生产订单" align="center" prop="orderCode" />
      <el-table-column label="产品" align="center" prop="productCode" />
      <el-table-column label="源工序" align="center" prop="fromProcessCode" />
      <el-table-column label="目标工序" align="center" prop="toProcessCode" />
      <el-table-column label="交接日期" align="center" prop="handoverDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handoverDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交接数量" align="center" prop="handoverQuantity" />
      <el-table-column label="合格数量" align="center" prop="qualifiedQuantity" />
      <el-table-column label="不良数量" align="center" prop="defectiveQuantity" />
      <el-table-column label="交接人" align="center" prop="fromOperatorName" />
      <el-table-column label="接收人" align="center" prop="toOperatorName" />
      <el-table-column label="交接类型" align="center" prop="handoverType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.handover_type" :value="scope.row.handoverType"/>
        </template>
      </el-table-column>
      <el-table-column label="质量状态" align="center" prop="qualityStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quality_status" :value="scope.row.qualityStatus"/>
        </template>
      </el-table-column>
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
            v-hasPermi="['erp:processHandover:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:processHandover:remove']"
          >删除</el-button>
          <!-- 提交按钮：审批状态为0且有编辑权限时显示 -->
          <el-button
            v-if="scope.row.approvalStatus === '0'"
            size="mini"
            type="text"
            icon="el-icon-upload"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:processHandover:edit']"
          >提交</el-button>
          <!-- 审批按钮：审批状态为1且有审批权限时显示 -->
          <el-button
            v-if="scope.row.approvalStatus === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApproval(scope.row)"
            v-hasPermi="['erp:processHandover:approval']"
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

    <!-- 添加或修改工序交接单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="交接单编码" prop="handoverCode">
              <el-input v-model="form.handoverCode" placeholder="请输入交接单编码" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="交接单名称" prop="handoverName">
              <el-input v-model="form.handoverName" placeholder="请输入交接单名称" />
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
            <el-form-item label="交接日期" prop="handoverDate">
              <el-date-picker clearable
                v-model="form.handoverDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择交接日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="交接时间" prop="handoverTime">
              <el-date-picker clearable
                v-model="form.handoverTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择交接时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品" prop="productId">
              <el-input v-model="productDisplayName" placeholder="产品信息" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位" prop="unitId">
              <el-input v-model="form.unitName" placeholder="单位信息" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="源工序" prop="fromProcessId">
              <el-input v-model="fromProcessDisplayName" placeholder="请选择源工序" readonly @click="selectFromProcess">
                <el-button slot="append" icon="el-icon-search" @click="selectFromProcess"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="目标工序" prop="toProcessId">
              <el-input v-model="toProcessDisplayName" placeholder="请选择目标工序" readonly @click="selectToProcess">
                <el-button slot="append" icon="el-icon-search" @click="selectToProcess"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="交接数量" prop="handoverQuantity">
              <el-input v-model="form.handoverQuantity" placeholder="交接数量" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="合格数量" prop="qualifiedQuantity">
              <el-input v-model="form.qualifiedQuantity" placeholder="请输入合格数量" @input="calculateHandoverQuantity" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="不良数量" prop="defectiveQuantity">
              <el-input v-model="form.defectiveQuantity" placeholder="请输入不良数量" @input="calculateHandoverQuantity" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="批次号" prop="batchNo">
              <el-input v-model="form.batchNo" placeholder="请输入批次号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="批号" prop="lotNo">
              <el-input v-model="form.lotNo" placeholder="请输入批号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="交接人" prop="fromOperatorId">
              <el-input v-model="form.fromOperatorName" placeholder="请选择交接人" readonly @click="selectFromOperator">
                <el-button slot="append" icon="el-icon-search" @click="selectFromOperator"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="接收人" prop="toOperatorId">
              <el-input v-model="form.toOperatorName" placeholder="请选择接收人" readonly @click="selectToOperator">
                <el-button slot="append" icon="el-icon-search" @click="selectToOperator"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="交接类型" prop="handoverType">
              <el-select v-model="form.handoverType" placeholder="请选择交接类型">
                <el-option
                  v-for="dict in dict.type.handover_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="质量状态" prop="qualityStatus">
              <el-select v-model="form.qualityStatus" placeholder="请选择质量状态">
                <el-option
                  v-for="dict in dict.type.quality_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
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
      <el-form :model="productionOrderQueryParams" ref="productionOrderQueryForm" size="small" :inline="true">
        <el-form-item label="订单编码" prop="orderCode">
          <el-input
            v-model="productionOrderQueryParams.orderCode"
            placeholder="请输入订单编码"
            clearable
            @keyup.enter.native="getProductionOrderList"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getProductionOrderList">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetProductionOrderQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="productionOrderLoading" :data="productionOrderList" @row-click="selectProductionOrderRow">
        <el-table-column label="订单编码" align="center" prop="orderCode" />
        <el-table-column label="产品编码" align="center" prop="productCode" />
        <el-table-column label="产品名称" align="center" prop="productName" />
        <el-table-column label="单位编码" align="center" prop="unitCode" />
        <el-table-column label="单位名称" align="center" prop="unitName" />
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
      </el-table>
    </el-dialog>

    <!-- 操作员选择对话框 -->
    <el-dialog title="选择操作员" :visible.sync="operatorDialogVisible" width="600px" append-to-body>
      <el-table v-loading="operatorLoading" :data="operatorList" @row-click="selectOperatorRow">
        <el-table-column label="用户ID" align="center" prop="userId" />
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="昵称" align="center" prop="nickName" />
        <el-table-column label="部门" align="center" prop="deptName" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listProcessHandover, getProcessHandover, delProcessHandover, addProcessHandover, updateProcessHandover } from "@/api/erp/processHandover";
import { listProductionOrder, getProductionOrderProcesses } from "@/api/erp/productionOrder";
import { listProcess } from "@/api/erp/process";
import { listUser } from "@/api/system/user";

export default {
  name: "ProcessHandover",
  dicts: ['handover_type', 'quality_status', 'approval_status', 'production_order_status'],
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
      // 工序交接单表格数据
      processHandoverList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        handoverCode: null,
        handoverName: null,
        orderCode: null,
        handoverDate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        handoverName: [
          { required: true, message: "交接单名称不能为空", trigger: "blur" }
        ],
        productionOrderId: [
          { required: true, message: "生产订单不能为空", trigger: "change" }
        ],
        fromProcessId: [
          { required: true, message: "源工序不能为空", trigger: "change" }
        ],
        toProcessId: [
          { required: true, message: "目标工序不能为空", trigger: "change" }
        ],
        handoverDate: [
          { required: true, message: "交接日期不能为空", trigger: "blur" }
        ],
        handoverTime: [
          { required: true, message: "交接时间不能为空", trigger: "blur" }
        ],
        productId: [
          { required: true, message: "产品不能为空", trigger: "change" }
        ],
        unitId: [
          { required: true, message: "单位不能为空", trigger: "change" }
        ],
        qualifiedQuantity: [
          { required: true, message: "合格数量不能为空", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              if (value == null || value === '' || parseFloat(value) < 0) {
                callback(new Error('合格数量不能小于0'));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        defectiveQuantity: [
          { required: true, message: "不良数量不能为空", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              if (value == null || value === '' || parseFloat(value) < 0) {
                callback(new Error('不良数量不能小于0'));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        fromOperatorId: [
          { required: true, message: "交接人不能为空", trigger: "change" }
        ],
        toOperatorId: [
          { required: true, message: "接收人不能为空", trigger: "change" }
        ]
      },
      // 生产订单选择相关
      productionOrderDialogVisible: false,
      productionOrderList: [],
      productionOrderTotal: 0,
      productionOrderLoading: false,
      productionOrderQueryParams: {
        pageNum: 1,
        pageSize: 10,
        orderCode: null,
        status: '2' // 只显示下达的订单
      },
      // 工序选择相关
      processDialogVisible: false,
      processList: [],
      processLoading: false,
      currentProcessType: '', // 'from' 或 'to'
      // 操作员选择相关
      operatorDialogVisible: false,
      operatorList: [],
      operatorTotal: 0,
      operatorLoading: false,
      operatorQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        nickName: null
      },
      currentOperatorType: '' // 'from' 或 'to'
    };
  },
  computed: {
    productDisplayName() {
      if (this.form.productCode && this.form.productName) {
        return `${this.form.productCode} - ${this.form.productName}`;
      }
      return '';
    },
    fromProcessDisplayName() {
      if (this.form.fromProcessCode && this.form.fromProcessName) {
        return `${this.form.fromProcessCode} - ${this.form.fromProcessName}`;
      }
      return '';
    },
    toProcessDisplayName() {
      if (this.form.toProcessCode && this.form.toProcessName) {
        return `${this.form.toProcessCode} - ${this.form.toProcessName}`;
      }
      return '';
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工序交接单列表 */
    getList() {
      this.loading = true;
      listProcessHandover(this.queryParams).then(response => {
        this.processHandoverList = response.rows;
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
        handoverCode: null,
        handoverName: null,
        productionOrderId: null,
        fromProcessId: null,
        toProcessId: null,
        handoverDate: null,
        handoverTime: null,
        productId: null,
        unitId: null,
        handoverQuantity: null,
        qualifiedQuantity: null,
        defectiveQuantity: null,
        batchNo: null,
        lotNo: null,
        fromOperatorId: null,
        toOperatorId: null,
        handoverType: "1",
        qualityStatus: "1",
        approvalStatus: "0",
        remark: null,
        // 关联字段重置
        orderCode: null,
        productCode: null,
        productName: null,
        unitName: null,
        fromProcessCode: null,
        fromProcessName: null,
        toProcessCode: null,
        toProcessName: null,
        fromOperatorName: null,
        toOperatorName: null
      };
      this.resetForm("form");
      // 重置后计算交接数量
      this.calculateHandoverQuantity();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
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
      this.title = "添加工序交接单";
      // 设置默认值
      this.form.handoverDate = this.parseTime(new Date(), '{y}-{m}-{d}');
      this.form.handoverTime = this.parseTime(new Date(), '{y}-{m}-{d} {h}:{i}:{s}');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProcessHandover(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工序交接单";
        // 加载数据后计算交接数量
        this.calculateHandoverQuantity();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProcessHandover(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcessHandover(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工序交接单编号为"' + ids + '"的数据项？').then(function() {
        return delProcessHandover(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/processHandover/export', {
        ...this.queryParams
      }, `工序交接单_${new Date().getTime()}.xlsx`)
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
    // 重置生产订单查询
    resetProductionOrderQuery() {
      this.resetForm("productionOrderQueryForm");
      this.getProductionOrderList();
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
    },
    // 选择源工序
    selectFromProcess() {
      if (!this.form.productionOrderId) {
        this.$modal.msgWarning("请先选择生产订单");
        return;
      }
      this.currentProcessType = 'from';
      this.processDialogVisible = true;
      this.getOrderProcessList();
    },
    // 选择目标工序
    selectToProcess() {
      if (!this.form.productionOrderId) {
        this.$modal.msgWarning("请先选择生产订单");
        return;
      }
      this.currentProcessType = 'to';
      this.processDialogVisible = true;
      this.getOrderProcessList();
    },
    // 获取工序列表（所有工序）
    getProcessList() {
      this.processLoading = true;
      listProcess({}).then(response => {
        this.processList = response.rows;
        this.processLoading = false;
      });
    },
    // 获取生产订单对应的工序列表
    getOrderProcessList() {
      this.processLoading = true;
      getProductionOrderProcesses(this.form.productionOrderId).then(response => {
        this.processList = response.data;
        this.processLoading = false;
      }).catch(() => {
        this.processLoading = false;
      });
    },
    // 选择工序行
    selectProcessRow(row) {
      if (this.currentProcessType === 'from') {
        this.form.fromProcessId = row.id;
        // 处理不同的数据结构：可能是camelCase或snake_case
        this.form.fromProcessCode = row.processCode || row.process_code;
        this.form.fromProcessName = row.processName || row.process_name;
      } else if (this.currentProcessType === 'to') {
        this.form.toProcessId = row.id;
        // 处理不同的数据结构：可能是camelCase或snake_case
        this.form.toProcessCode = row.processCode || row.process_code;
        this.form.toProcessName = row.processName || row.process_name;
      }
      this.processDialogVisible = false;
    },
    // 选择交接人
    selectFromOperator() {
      this.currentOperatorType = 'from';
      this.operatorDialogVisible = true;
      this.getOperatorList();
    },
    // 选择接收人
    selectToOperator() {
      this.currentOperatorType = 'to';
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
      if (this.currentOperatorType === 'from') {
        this.form.fromOperatorId = row.userId;
        this.form.fromOperatorName = row.nickName;
      } else if (this.currentOperatorType === 'to') {
        this.form.toOperatorId = row.userId;
        this.form.toOperatorName = row.nickName;
      }
      this.operatorDialogVisible = false;
    },
    // 提交交接单
    handleSubmit(row) {
      this.$modal.confirm('确认提交该交接单吗？').then(() => {
        const data = {
          id: row.id,
          approvalStatus: '1'
        };
        updateProcessHandover(data).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.getList();
        });
      });
    },
    // 审批交接单
    handleApproval(row) {
      this.$modal.confirm('确认审批通过该交接单吗？').then(() => {
        const data = {
          id: row.id,
          approvalStatus: '2'
        };
        updateProcessHandover(data).then(response => {
          this.$modal.msgSuccess("审批成功");
          this.getList();
        });
      });
    },
    // 计算交接数量
    calculateHandoverQuantity() {
      const qualifiedQuantity = parseFloat(this.form.qualifiedQuantity) || 0;
      const defectiveQuantity = parseFloat(this.form.defectiveQuantity) || 0;
      const handoverQuantity = qualifiedQuantity + defectiveQuantity;
      this.form.handoverQuantity = handoverQuantity;
    }
  }
};
</script>
