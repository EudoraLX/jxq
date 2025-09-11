<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="调拨单号" prop="transferNo">
        <el-input
          v-model="queryParams.transferNo"
          placeholder="请输入调拨单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="调拨单名称" prop="transferName">
        <el-input
          v-model="queryParams.transferName"
          placeholder="请输入调拨单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="调拨类型" prop="transferType">
        <el-select v-model="queryParams.transferType" placeholder="请选择调拨类型" clearable>
          <el-option
            v-for="dict in dict.type.transfer_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['inventory:transfer:add']"
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
          v-hasPermi="['inventory:transfer:edit']"
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
          v-hasPermi="['inventory:transfer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:transfer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transferList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="调拨单号" align="center" prop="transferNo" />
      <el-table-column label="调拨单名称" align="center" prop="transferName" />
      <el-table-column label="调拨类型" align="center" prop="transferType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.transfer_type" :value="scope.row.transferType"/>
        </template>
      </el-table-column>
      <el-table-column label="源仓库" align="center" prop="fromWarehouseName" />
      <el-table-column label="目标仓库" align="center" prop="toWarehouseName" />
      <el-table-column label="计划调拨日期" align="center" prop="plannedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.plannedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际调拨日期" align="center" prop="actualDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actualDate, '{y}-{m}-{d}') }}</span>
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
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['inventory:transfer:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:transfer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['inventory:transfer:approve']"
            v-if="scope.row.approvalStatus == '1'"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-right"
            @click="handleExecute(scope.row)"
            v-hasPermi="['inventory:transfer:execute']"
            v-if="scope.row.approvalStatus == '2'"
          >执行</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:transfer:remove']"
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

    <!-- 添加或修改调拨单表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="调拨单号" prop="transferNo">
              <el-input v-model="form.transferNo" placeholder="请输入调拨单号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="调拨单名称" prop="transferName">
              <el-input v-model="form.transferName" placeholder="请输入调拨单名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="调拨类型" prop="transferType">
              <el-select v-model="form.transferType" placeholder="请选择调拨类型">
                <el-option
                  v-for="dict in dict.type.transfer_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="源仓库" prop="fromWarehouseId">
              <el-select v-model="form.fromWarehouseId" placeholder="请选择源仓库" @change="handleFromWarehouseChange">
                <el-option
                  v-for="warehouse in warehouseList"
                  :key="warehouse.id"
                  :label="warehouse.warehouseName"
                  :value="warehouse.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="目标仓库" prop="toWarehouseId">
              <el-select v-model="form.toWarehouseId" placeholder="请选择目标仓库" @change="handleToWarehouseChange">
                <el-option
                  v-for="warehouse in warehouseList"
                  :key="warehouse.id"
                  :label="warehouse.warehouseName"
                  :value="warehouse.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划调拨日期" prop="plannedDate">
              <el-date-picker clearable v-model="form.plannedDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择计划调拨日期" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-divider content-position="left">调拨明细</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDetail">添加明细</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDetail">删除明细</el-button>
          </el-col>
        </el-row>
        <el-table :data="detailList" :row-class-name="rowDetailIndex" @selection-change="handleDetailSelectionChange" ref="detail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="物料编码" prop="materialCode" width="150">
            <template slot-scope="scope">
              <el-input 
                v-model="scope.row.materialCode" 
                placeholder="请选择物料" 
                readonly
                @click="openMaterialSelect(scope.row)"
                style="cursor: pointer;"
              >
                <el-button slot="append" icon="el-icon-search" @click="openMaterialSelect(scope.row)"></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="物料名称" prop="materialName" width="200">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialName" placeholder="请输入物料名称" />
            </template>
          </el-table-column>
          <el-table-column label="物料规格" prop="materialSpec" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialSpec" placeholder="请输入物料规格" />
            </template>
          </el-table-column>
          <el-table-column label="基本单位" prop="unitName" width="100">
            <template slot-scope="scope">
              <el-input v-model="scope.row.unitName" placeholder="基本单位" readonly />
            </template>
          </el-table-column>
          <el-table-column label="计划数量" prop="plannedQuantity" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.plannedQuantity" 
                :precision="4" 
                :step="0.1" 
                :min="0" 
                :controls="false"
                @change="handleQuantityChange(scope.row)" 
              />
            </template>
          </el-table-column>
          <el-table-column label="实际数量" prop="actualQuantity" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.actualQuantity" 
                :precision="4" 
                :step="0.1" 
                :min="0" 
                :controls="false"
                @change="handleQuantityChange(scope.row)" 
              />
            </template>
          </el-table-column>
          <el-table-column label="单价" prop="unitPrice" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.unitPrice" 
                :precision="4" 
                :step="0.01" 
                :min="0" 
                :controls="false"
                @change="handleQuantityChange(scope.row)" 
              />
            </template>
          </el-table-column>
          <el-table-column label="总金额" prop="totalAmount" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.totalAmount" 
                :precision="2" 
                :step="0.01" 
                :min="0" 
                :controls="false"
                readonly 
              />
            </template>
          </el-table-column>
          <el-table-column label="源货架" prop="fromShelfName" width="120">
            <template slot-scope="scope">
              <el-input 
                v-model="scope.row.fromShelfName" 
                placeholder="请选择源货架" 
                readonly
                @click="openFromShelfSelect(scope.row)"
                style="cursor: pointer;"
              >
                <el-button slot="append" icon="el-icon-search" @click="openFromShelfSelect(scope.row)"></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="源货位" prop="fromLocationName" width="120">
            <template slot-scope="scope">
              <el-input 
                v-model="scope.row.fromLocationName" 
                placeholder="请选择源货位" 
                readonly
                @click="openFromLocationSelect(scope.row)"
                style="cursor: pointer;"
              >
                <el-button slot="append" icon="el-icon-search" @click="openFromLocationSelect(scope.row)"></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="目标货架" prop="toShelfName" width="120">
            <template slot-scope="scope">
              <el-input 
                v-model="scope.row.toShelfName" 
                placeholder="请选择目标货架" 
                readonly
                @click="openToShelfSelect(scope.row)"
                style="cursor: pointer;"
              >
                <el-button slot="append" icon="el-icon-search" @click="openToShelfSelect(scope.row)"></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="目标货位" prop="toLocationName" width="120">
            <template slot-scope="scope">
              <el-input 
                v-model="scope.row.toLocationName" 
                placeholder="请选择目标货位" 
                readonly
                @click="openToLocationSelect(scope.row)"
                style="cursor: pointer;"
              >
                <el-button slot="append" icon="el-icon-search" @click="openToLocationSelect(scope.row)"></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="批次号" prop="batchNo" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.batchNo" placeholder="请输入批次号" />
            </template>
          </el-table-column>
          <el-table-column label="生产日期" prop="productionDate" width="150">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.productionDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择生产日期" />
            </template>
          </el-table-column>
          <el-table-column label="到期日期" prop="expiryDate" width="150">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.expiryDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择到期日期" />
            </template>
          </el-table-column>
          <el-table-column label="质量状态" prop="qualityStatus" width="120">
            <template slot-scope="scope">
              <el-select v-model="scope.row.qualityStatus" placeholder="请选择质量状态">
                <el-option
                  v-for="dict in dict.type.quality_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </template>
          </el-table-column>
        </el-table>
        
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 物料选择弹窗 -->
    <MaterialSelectDialog 
      :visible.sync="materialSelectVisible" 
      @confirm="handleMaterialSelect"
    />
    
    <!-- 源货架选择弹窗 -->
    <ShelfSelectDialog 
      :visible.sync="fromShelfSelectVisible" 
      :warehouse-id="form.fromWarehouseId"
      @confirm="handleFromShelfSelect"
    />
    
    <!-- 源货位选择弹窗 -->
    <LocationSelectDialog 
      :visible.sync="fromLocationSelectVisible" 
      :shelf-id="currentDetailRow ? currentDetailRow.fromShelfId : null"
      @confirm="handleFromLocationSelect"
    />
    
    <!-- 目标货架选择弹窗 -->
    <ShelfSelectDialog 
      :visible.sync="toShelfSelectVisible" 
      :warehouse-id="form.toWarehouseId"
      @confirm="handleToShelfSelect"
    />
    
    <!-- 目标货位选择弹窗 -->
    <LocationSelectDialog 
      :visible.sync="toLocationSelectVisible" 
      :shelf-id="currentDetailRow ? currentDetailRow.toShelfId : null"
      @confirm="handleToLocationSelect"
    />
  </div>
</template>

<script>
import { listTransfer, getTransfer, delTransfer, addTransfer, updateTransfer, approveTransfer, executeTransfer, generateTransferNo } from "@/api/inventory/transfer";
import { listWarehouse } from "@/api/erp/warehouse";
import MaterialSelectDialog from "@/components/MaterialSelectDialog";
import ShelfSelectDialog from "@/components/ShelfSelectDialog";
import LocationSelectDialog from "@/components/LocationSelectDialog";

export default {
  name: "Transfer",
  dicts: ['transfer_type', 'approval_status', 'quality_status'],
  components: {
    MaterialSelectDialog,
    ShelfSelectDialog,
    LocationSelectDialog
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调拨单表表格数据
      transferList: [],
      // 仓库列表
      warehouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 明细表格数据
      detailList: [],
      // 物料选择弹窗
      materialSelectVisible: false,
      // 货架选择弹窗
      fromShelfSelectVisible: false,
      toShelfSelectVisible: false,
      // 货位选择弹窗
      fromLocationSelectVisible: false,
      toLocationSelectVisible: false,
      // 当前编辑的明细行
      currentDetailRow: null,
      // 货架列表
      fromShelfList: [],
      toShelfList: [],
      // 货位列表
      fromLocationList: [],
      toLocationList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        transferNo: null,
        transferName: null,
        transferType: null,
        approvalStatus: null,
        fromWarehouseId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        transferNo: [
          { required: true, message: "调拨单号不能为空", trigger: "blur" }
        ],
        transferName: [
          { required: true, message: "调拨单名称不能为空", trigger: "blur" }
        ],
        transferType: [
          { required: true, message: "调拨类型不能为空", trigger: "change" }
        ],
        fromWarehouseId: [
          { required: true, message: "源仓库不能为空", trigger: "change" }
        ],
        toWarehouseId: [
          { required: true, message: "目标仓库不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getWarehouseList();
  },
  methods: {
    /** 查询调拨单表列表 */
    getList() {
      this.loading = true;
      listTransfer(this.queryParams).then(response => {
        this.transferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询仓库列表 */
    getWarehouseList() {
      listWarehouse().then(response => {
        this.warehouseList = response.data;
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
        transferNo: null,
        transferName: null,
        transferType: null,
        fromWarehouseId: null,
        fromWarehouseCode: null,
        fromWarehouseName: null,
        toWarehouseId: null,
        toWarehouseCode: null,
        toWarehouseName: null,
        plannedDate: null,
        actualDate: null,
        totalQuantity: null,
        status: "0",
        remark: null
      };
      this.detailList = [];
      this.resetForm("form");
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
    // 子表多选框选中数据
    handleDetailSelectionChange(selection) {
      this.checkedDetail = selection
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.generateTransferNo();
      this.open = true;
      this.title = "添加调拨单表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTransfer(id).then(response => {
        this.form = response.data
        this.detailList = response.data.transferOrderDetailList || []
        this.open = true
        this.title = "修改调拨单表"
      })
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.reset()
      const id = row.id
      getTransfer(id).then(response => {
        this.form = response.data
        this.detailList = response.data.transferOrderDetailList || []
        this.open = true
        this.title = "调拨单详情"
      })
    },
    /** 审核按钮操作 */
    handleApprove(row) {
      this.$modal.confirm('是否确认审核调拨单编号为"' + row.transferNo + '"的数据项？').then(function() {
        return approveTransfer(row)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核成功")
      }).catch(() => {})
    },
    /** 执行按钮操作 */
    handleExecute(row) {
      this.$modal.confirm('是否确认执行调拨单编号为"' + row.transferNo + '"的数据项？').then(function() {
        return executeTransfer(row)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("执行成功")
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.transferOrderDetailList = this.detailList;
          if (this.form.id != null) {
            updateTransfer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTransfer(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除调拨单表编号为"' + ids + '"的数据项？').then(function() {
        return delTransfer(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 生成调拨单号 */
    generateTransferNo() {
      generateTransferNo().then(response => {
        this.form.transferNo = response.data
      })
    },
    /** 源仓库变化处理 */
    handleFromWarehouseChange(warehouseId) {
      const warehouse = this.warehouseList.find(item => item.id === warehouseId)
      if (warehouse) {
        this.form.fromWarehouseCode = warehouse.warehouseCode
        this.form.fromWarehouseName = warehouse.warehouseName
      }
    },
    /** 目标仓库变化处理 */
    handleToWarehouseChange(warehouseId) {
      const warehouse = this.warehouseList.find(item => item.id === warehouseId)
      if (warehouse) {
        this.form.toWarehouseCode = warehouse.warehouseCode
        this.form.toWarehouseName = warehouse.warehouseName
      }
    },
    /** 明细序号 */
    rowDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 明细添加按钮操作 */
    handleAddDetail() {
      let obj = {
        materialId: null,
        materialCode: "",
        materialName: "",
        materialSpec: "",
        unitId: null,
        unitCode: "",
        unitName: "",
        plannedQuantity: 0,
        actualQuantity: 0,
        unitPrice: 0,
        totalAmount: 0,
        fromShelfId: null,
        fromShelfCode: "",
        fromShelfName: "",
        fromLocationId: null,
        fromLocationCode: "",
        fromLocationName: "",
        toShelfId: null,
        toShelfCode: "",
        toShelfName: "",
        toLocationId: null,
        toLocationCode: "",
        toLocationName: "",
        batchNo: "",
        productionDate: null,
        expiryDate: null,
        qualityStatus: "1"
      }
      this.detailList.push(obj)
    },
    /** 明细删除按钮操作 */
    handleDeleteDetail() {
      if (this.checkedDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的明细数据")
      } else {
        this.$modal.confirm("是否确认删除选中的明细数据项？").then(() => {
          this.checkedDetail.forEach(item => {
            const index = this.detailList.indexOf(item)
            if (index > -1) {
              this.detailList.splice(index, 1)
            }
          })
          this.checkedDetail = []
        })
      }
    },
    /** 打开物料选择弹窗 */
    openMaterialSelect(row) {
      this.currentDetailRow = row
      this.materialSelectVisible = true
    },
    /** 处理物料选择确认 */
    handleMaterialSelect(material) {
      if (this.currentDetailRow) {
        // 更新当前行的物料信息
        this.currentDetailRow.materialId = material.materialId
        this.currentDetailRow.materialCode = material.materialCode
        this.currentDetailRow.materialName = material.materialName
        this.currentDetailRow.materialSpec = material.materialSpec
        this.currentDetailRow.unitId = material.unitId
        this.currentDetailRow.unitCode = material.unitCode
        this.currentDetailRow.unitName = material.unitName
        this.currentDetailRow = null
      }
    },
    /** 物料变化处理 */
    handleMaterialChange(row) {
      // 这里可以添加物料查询逻辑
    },
    /** 数量变化处理 */
    handleQuantityChange(row) {
      if (row.actualQuantity && row.unitPrice) {
        row.totalAmount = row.actualQuantity * row.unitPrice
      }
    },
    /** 打开源货架选择弹窗 */
    openFromShelfSelect(row) {
      this.currentDetailRow = row
      this.fromShelfSelectVisible = true
      this.getFromShelfList()
    },
    /** 打开源货位选择弹窗 */
    openFromLocationSelect(row) {
      this.currentDetailRow = row
      this.fromLocationSelectVisible = true
      this.getFromLocationList()
    },
    /** 打开目标货架选择弹窗 */
    openToShelfSelect(row) {
      this.currentDetailRow = row
      this.toShelfSelectVisible = true
      this.getToShelfList()
    },
    /** 打开目标货位选择弹窗 */
    openToLocationSelect(row) {
      this.currentDetailRow = row
      this.toLocationSelectVisible = true
      this.getToLocationList()
    },
    /** 处理源货架选择确认 */
    handleFromShelfSelect(shelf) {
      if (this.currentDetailRow) {
        this.currentDetailRow.fromShelfId = shelf.id
        this.currentDetailRow.fromShelfCode = shelf.shelfCode
        this.currentDetailRow.fromShelfName = shelf.shelfName
        this.currentDetailRow = null
      }
    },
    /** 处理源货位选择确认 */
    handleFromLocationSelect(location) {
      if (this.currentDetailRow) {
        this.currentDetailRow.fromLocationId = location.id
        this.currentDetailRow.fromLocationCode = location.locationCode
        this.currentDetailRow.fromLocationName = location.locationName
        this.currentDetailRow = null
      }
    },
    /** 处理目标货架选择确认 */
    handleToShelfSelect(shelf) {
      if (this.currentDetailRow) {
        this.currentDetailRow.toShelfId = shelf.id
        this.currentDetailRow.toShelfCode = shelf.shelfCode
        this.currentDetailRow.toShelfName = shelf.shelfName
        this.currentDetailRow = null
      }
    },
    /** 处理目标货位选择确认 */
    handleToLocationSelect(location) {
      if (this.currentDetailRow) {
        this.currentDetailRow.toLocationId = location.id
        this.currentDetailRow.toLocationCode = location.locationCode
        this.currentDetailRow.toLocationName = location.locationName
        this.currentDetailRow = null
      }
    },
    /** 查询源货架列表 */
    getFromShelfList() {
      // 这里需要调用货架API，暂时使用模拟数据
      this.fromShelfList = [
        { id: 1, shelfCode: 'A01', shelfName: 'A区01号货架' },
        { id: 2, shelfCode: 'A02', shelfName: 'A区02号货架' },
        { id: 3, shelfCode: 'B01', shelfName: 'B区01号货架' }
      ]
    },
    /** 查询源货位列表 */
    getFromLocationList() {
      // 这里需要调用货位API，暂时使用模拟数据
      this.fromLocationList = [
        { id: 1, locationCode: 'A01-01', locationName: 'A01-01货位' },
        { id: 2, locationCode: 'A01-02', locationName: 'A01-02货位' },
        { id: 3, locationCode: 'A02-01', locationName: 'A02-01货位' }
      ]
    },
    /** 查询目标货架列表 */
    getToShelfList() {
      // 这里需要调用货架API，暂时使用模拟数据
      this.toShelfList = [
        { id: 1, shelfCode: 'C01', shelfName: 'C区01号货架' },
        { id: 2, shelfCode: 'C02', shelfName: 'C区02号货架' },
        { id: 3, shelfCode: 'D01', shelfName: 'D区01号货架' }
      ]
    },
    /** 查询目标货位列表 */
    getToLocationList() {
      // 这里需要调用货位API，暂时使用模拟数据
      this.toLocationList = [
        { id: 1, locationCode: 'C01-01', locationName: 'C01-01货位' },
        { id: 2, locationCode: 'C01-02', locationName: 'C01-02货位' },
        { id: 3, locationCode: 'C02-01', locationName: 'C02-01货位' }
      ]
    }
  }
};
</script>
