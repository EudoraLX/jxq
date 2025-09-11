<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="生产订单" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入生产订单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序编码" prop="processCode">
        <el-input
          v-model="queryParams.processCode"
          placeholder="请输入工序编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
             <el-form-item label="操作员" prop="operatorName">
         <el-input
           v-model="queryParams.operatorName"
           placeholder="请输入操作员姓名"
           clearable
           @keyup.enter.native="handleQuery"
         />
       </el-form-item>
       <el-form-item label="操作类型" prop="operationType">
         <el-select v-model="queryParams.operationType" placeholder="请选择操作类型" clearable>
           <el-option
             v-for="dict in dict.type.operation_type"
             :key="dict.value"
             :label="dict.label"
             :value="dict.value"
           />
         </el-select>
       </el-form-item>
      <el-form-item label="操作时间">
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
          v-hasPermi="['erp:productionOperationRecord:add']"
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
          v-hasPermi="['erp:productionOperationRecord:edit']"
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
          v-hasPermi="['erp:productionOperationRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:productionOperationRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

         <el-table v-loading="loading" :data="productionOperationRecordList" @selection-change="handleSelectionChange">
       <el-table-column type="selection" width="55" align="center" />
       <el-table-column label="操作类型" align="center" prop="operationType" width="100">
         <template slot-scope="scope">
           <dict-tag :options="dict.type.operation_type" :value="scope.row.operationType"/>
         </template>
       </el-table-column>
       <el-table-column label="生产订单" align="center" prop="orderCode" width="120" />
      <el-table-column label="产品" align="center" prop="productCode" width="200">
        <template slot-scope="scope">
          {{ scope.row.productCode }} - {{ scope.row.productName }}
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unitName" width="100" />
      <el-table-column label="工序" align="center" prop="processCode" width="120">
        <template slot-scope="scope">
          {{ scope.row.processCode }} - {{ scope.row.processName }}
        </template>
      </el-table-column>
      <el-table-column label="工作中心" align="center" prop="workCenterCode" width="120">
        <template slot-scope="scope">
          {{ scope.row.workCenterCode }} - {{ scope.row.workCenterName }}
        </template>
      </el-table-column>
      <el-table-column label="设备" align="center" prop="equipmentCode" width="120">
        <template slot-scope="scope">
          {{ scope.row.equipmentCode }} - {{ scope.row.equipmentName }}
        </template>
      </el-table-column>
      <el-table-column label="模具" align="center" prop="moldCode" width="120">
        <template slot-scope="scope">
          {{ scope.row.moldCode }} - {{ scope.row.moldName }}
        </template>
      </el-table-column>
      <el-table-column label="操作员" align="center" prop="operatorName" width="100" />
      <el-table-column label="操作时间" align="center" prop="operationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operationTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际数量" align="center" prop="quantity" width="100" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:productionOperationRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:productionOperationRecord:remove']"
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

    <!-- 添加或修改生产操作记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
                 <el-row :gutter="20">
           <el-col :span="12">
             <el-form-item label="操作类型" prop="operationType">
               <el-select v-model="form.operationType" placeholder="请选择操作类型" style="width:100%">
                 <el-option
                   v-for="dict in dict.type.operation_type"
                   :key="dict.value"
                   :label="dict.label"
                   :value="dict.value"
                 />
               </el-select>
             </el-form-item>
           </el-col>
           <el-col :span="12">
             <el-form-item label="生产订单" prop="productionOrderId">
               <el-input v-model="form.orderCode" placeholder="请选择生产订单" readonly @click="selectProductionOrder">
                 <el-button slot="append" icon="el-icon-search" @click="selectProductionOrder"></el-button>
               </el-input>
             </el-form-item>
           </el-col>
         </el-row>

         <el-row :gutter="20">
           <el-col :span="12">
             <el-form-item label="产品" prop="productId">
               <el-input v-model="productDisplayName" placeholder="自动加载" readonly style="background-color: #f5f7fa;" />
             </el-form-item>
           </el-col>
           <el-col :span="12">
             <el-form-item label="实际数量" prop="quantity">
               <el-input
                 v-model="form.quantity"
                 type="number"
                 placeholder="请输入实际数量"
                 style="width: 100%;"
                 :min="0"
                 :step="0.01">
                 <template slot="append">{{ form.unitName || '单位' }}</template>
               </el-input>
             </el-form-item>
           </el-col>
         </el-row>

         <el-row :gutter="20">
           <el-col :span="12">
             <el-form-item label="工序" prop="processId">
               <el-input v-model="processDisplayName" placeholder="请选择工序" readonly @click="selectProcess" :disabled="!form.productionOrderId">
                 <el-button slot="append" icon="el-icon-search" @click="selectProcess" :disabled="!form.productionOrderId"></el-button>
               </el-input>
             </el-form-item>
           </el-col>
           <el-col :span="12">
             <el-form-item label="工作中心" prop="workCenterId">
               <el-input v-model="workCenterDisplay" placeholder="自动加载" readonly style="background-color: #f5f7fa;" />
             </el-form-item>
           </el-col>
         </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="设备" prop="equipmentId">
              <el-input v-model="equipmentDisplay" placeholder="自动加载" readonly style="background-color: #f5f7fa;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="模具" prop="moldId">
              <el-input v-model="moldDisplay" placeholder="自动加载" readonly style="background-color: #f5f7fa;" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="操作员" prop="operatorId">
              <el-input v-model="operatorDisplay" placeholder="当前用户" readonly style="background-color: #f5f7fa;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作时间" prop="operationTime">
              <el-date-picker
                v-model="form.operationTime"
                type="datetime"
                placeholder="选择操作时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%;"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
             </div>
     </el-dialog>

     <!-- 生产订单选择对话框 -->
     <el-dialog title="选择生产订单" :visible.sync="productionOrderDialogVisible" width="1000px" append-to-body>
       <el-table v-loading="productionOrderLoading" :data="productionOrderList" @row-click="selectProductionOrderRow">
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
import { listProductionOperationRecord, getProductionOperationRecord, delProductionOperationRecord, addProductionOperationRecord, updateProductionOperationRecord, getLatestProcessId, getWorkCenterInfo, getProcessRouteDetails } from "@/api/erp/productionOperationRecord";
import { listProductionOrder } from "@/api/erp/productionOrder";

export default {
  name: "ProductionOperationRecord",
  dicts: ['operation_type', 'production_order_status'],
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
      // 生产操作记录表格数据
      productionOperationRecordList: [],
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
         orderCode: null,
         processCode: null,
         operatorName: null,
         operationType: null,
       },
      // 表单参数
      form: {},
             // 表单校验
       rules: {
         operationType: [
           { required: true, message: "操作类型不能为空", trigger: "change" }
         ],
         productionOrderId: [
           { required: true, message: "生产订单不能为空", trigger: "change" }
         ],
         processId: [
           { required: true, message: "工序不能为空", trigger: "change" }
         ],
         operationTime: [
           { required: true, message: "操作时间不能为空", trigger: "change" }
         ],
         quantity: [
           { required: true, message: "实际数量不能为空", trigger: "blur" }
         ]
       },
             // 生产订单选择相关
       productionOrderDialogVisible: false,
       productionOrderList: [],
       productionOrderLoading: false,
       // 工序选择相关
       processDialogVisible: false,
       processList: [],
       processLoading: false,
       // 显示字段
       workCenterDisplay: "",
       equipmentDisplay: "",
       moldDisplay: "",
       operatorDisplay: ""
    };
  },
  computed: {
    processDisplayName() {
      if (this.form.processCode && this.form.processName) {
        return `${this.form.processCode} - ${this.form.processName}`;
      }
      return '';
    },
    productDisplayName() {
      if (this.form.productCode && this.form.productName) {
        return `${this.form.productCode} - ${this.form.productName}`;
      }
      return '';
    }
  },
  created() {
    this.getList();
    this.operatorDisplay = this.$store.state.user.name;
  },
  methods: {
    /** 查询生产操作记录列表 */
    getList() {
      this.loading = true;
      listProductionOperationRecord(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.productionOperationRecordList = response.rows;
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
         operationType: null,
         productionOrderId: null,
         processRouteId: null,
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
      this.title = "添加生产操作记录";
      this.form.operationTime = this.parseTime(new Date(), '{y}-{m}-{d} {h}:{i}:{s}');
    },
         /** 修改按钮操作 */
     handleUpdate(row) {
       this.reset();
       const id = row.id || this.ids
       getProductionOperationRecord(id).then(response => {
         this.form = response.data;
         this.workCenterDisplay = this.form.workCenterCode + " - " + this.form.workCenterName;
         this.equipmentDisplay = this.form.equipmentCode + " - " + this.form.equipmentName;
         this.moldDisplay = this.form.moldCode + " - " + this.form.moldName;
         this.operatorDisplay = this.form.operatorName;
         this.open = true;
         this.title = "修改生产操作记录";
       });
     },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProductionOperationRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductionOperationRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除生产操作记录编号为"' + ids + '"的数据项？').then(function() {
        return delProductionOperationRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/productionOperationRecord/export', {
        ...this.queryParams
      }, `productionOperationRecord_${new Date().getTime()}.xlsx`)
    },
         /** 选择生产订单 */
     selectProductionOrder() {
       this.productionOrderDialogVisible = true;
       this.getProductionOrderList();
     },
     /** 获取生产订单列表 */
     getProductionOrderList() {
       this.productionOrderLoading = true;
       listProductionOrder({ status: '2' }).then(response => {
         this.productionOrderList = response.rows;
         this.productionOrderLoading = false;
       });
     },
     /** 选择生产订单行 */
     selectProductionOrderRow(row) {
       this.form.productionOrderId = row.id;
       this.form.orderCode = row.orderCode;
       this.form.productId = row.productId;
       this.form.productCode = row.productCode;
       this.form.productName = row.productName;
       this.form.unitId = row.unitId;
       this.form.unitName = row.unitName;
       this.productionOrderDialogVisible = false;
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
     }
  }
};
</script>
