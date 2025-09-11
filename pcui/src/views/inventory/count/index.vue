<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="盘点单号" prop="countNo">
        <el-input
          v-model="queryParams.countNo"
          placeholder="请输入盘点单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点单名称" prop="countName">
        <el-input
          v-model="queryParams.countName"
          placeholder="请输入盘点单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点类型" prop="countType">
        <el-select v-model="queryParams.countType" placeholder="请选择盘点类型" clearable>
          <el-option
            v-for="dict in dict.type.count_type"
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
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择仓库" clearable>
          <el-option
            v-for="warehouse in warehouseList"
            :key="warehouse.id"
            :label="warehouse.warehouseName"
            :value="warehouse.id"
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
          v-hasPermi="['inventory:count:add']"
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
          v-hasPermi="['inventory:count:edit']"
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
          v-hasPermi="['inventory:count:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:count:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="countList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="盘点单号" align="center" prop="countNo" />
      <el-table-column label="盘点单名称" align="center" prop="countName" />
      <el-table-column label="盘点类型" align="center" prop="countType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.count_type" :value="scope.row.countType"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="盘点日期" align="center" prop="countDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.countDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总项目数" align="center" prop="totalItems" />
      <el-table-column label="已盘点项目数" align="center" prop="countedItems" />
      <el-table-column label="差异项目数" align="center" prop="differenceItems" />
      <el-table-column label="总差异金额" align="center" prop="totalDifferenceAmount" />
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
            v-hasPermi="['inventory:count:edit']"
            v-if="scope.row.approvalStatus === '0'"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:count:remove']"
            v-if="scope.row.approvalStatus === '0'"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['inventory:count:approve']"
            v-if="scope.row.approvalStatus === '1'"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload"
            @click="handleExecute(scope.row)"
            v-hasPermi="['inventory:count:execute']"
            v-if="scope.row.approvalStatus === '2'"
          >执行</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-setting"
            @click="handleAdjust(scope.row)"
            v-hasPermi="['inventory:count:adjust']"
            v-if="scope.row.status === '3'"
          >调整</el-button> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['inventory:count:query']"
          >详情</el-button>
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

    <!-- 添加或修改盘点单表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="盘点单号" prop="countNo">
              <el-input v-model="form.countNo" placeholder="请输入盘点单号" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="盘点单名称" prop="countName">
              <el-input v-model="form.countName" placeholder="请输入盘点单名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="盘点类型" prop="countType">
              <el-select v-model="form.countType" placeholder="请选择盘点类型">
                <el-option
                  v-for="dict in dict.type.count_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="仓库" prop="warehouseId">
              <el-select v-model="form.warehouseId" placeholder="请选择仓库" @change="handleWarehouseChange">
                <el-option
                  v-for="warehouse in warehouseList"
                  :key="warehouse.id"
                  :label="warehouse.warehouseName"
                  :value="warehouse.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="盘点日期" prop="countDate">
              <el-date-picker clearable
                v-model="form.countDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择盘点日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-divider content-position="center">盘点明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDetail">添加明细</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDetail">删除明细</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" icon="el-icon-refresh" size="mini" @click="handleGenerateDetails">生成明细</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" icon="el-icon-calculator" size="mini" @click="handleCalculateDifferences">计算差异</el-button>
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
          <el-table-column label="账面数量" prop="bookQuantity" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.bookQuantity" 
                :precision="4" 
                :step="0.1" 
                :min="0" 
                :controls="false"
                @change="handleQuantityChange(scope.row)" 
              />
            </template>
          </el-table-column>
          <el-table-column label="盘点数量" prop="countQuantity" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.countQuantity" 
                :precision="4" 
                :step="0.1" 
                :min="0" 
                :controls="false"
                @change="handleQuantityChange(scope.row)" 
              />
            </template>
          </el-table-column>
          <el-table-column label="差异数量" prop="differenceQuantity" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.differenceQuantity" 
                :precision="4" 
                :step="0.1" 
                :min="0" 
                :controls="false"
                readonly 
              />
            </template>
          </el-table-column>
          <el-table-column label="单价" prop="unitCost" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.unitCost" 
                :precision="4" 
                :step="0.01" 
                :min="0" 
                :controls="false"
                @change="handleQuantityChange(scope.row)" 
              />
            </template>
          </el-table-column>
          <el-table-column label="账面金额" prop="bookAmount" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.bookAmount" 
                :precision="2" 
                :step="0.01" 
                :min="0" 
                :controls="false"
                readonly 
              />
            </template>
          </el-table-column>
          <el-table-column label="盘点金额" prop="countAmount" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.countAmount" 
                :precision="2" 
                :step="0.01" 
                :min="0" 
                :controls="false"
                readonly 
              />
            </template>
          </el-table-column>
          <el-table-column label="差异金额" prop="differenceAmount" width="120">
            <template slot-scope="scope">
              <el-input-number 
                v-model="scope.row.differenceAmount" 
                :precision="2" 
                :step="0.01" 
                :min="0" 
                :controls="false"
                readonly 
              />
            </template>
          </el-table-column>
          <el-table-column label="货架" prop="shelfName" width="120">
            <template slot-scope="scope">
              <el-input 
                v-model="scope.row.shelfName" 
                placeholder="请选择货架" 
                readonly
                @click="openShelfSelect(scope.row)"
                style="cursor: pointer;"
              >
                <el-button slot="append" icon="el-icon-search" @click="openShelfSelect(scope.row)"></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="货位" prop="locationName" width="120">
            <template slot-scope="scope">
              <el-input 
                v-model="scope.row.locationName" 
                placeholder="请选择货位" 
                readonly
                @click="openLocationSelect(scope.row)"
                style="cursor: pointer;"
              >
                <el-button slot="append" icon="el-icon-search" @click="openLocationSelect(scope.row)"></el-button>
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
          <el-table-column label="盘点状态" prop="countStatus" width="120">
            <template slot-scope="scope">
              <el-select v-model="scope.row.countStatus" placeholder="请选择盘点状态">
                <el-option
                  v-for="dict in dict.type.count_status"
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
    
    <!-- 货架选择弹窗 -->
    <ShelfSelectDialog 
      :visible.sync="shelfSelectVisible" 
      :warehouse-id="form.warehouseId"
      @confirm="handleShelfSelect"
    />
    
    <!-- 货位选择弹窗 -->
    <LocationSelectDialog 
      :visible.sync="locationSelectVisible" 
      :shelf-id="currentDetailRow ? currentDetailRow.shelfId : null"
      @confirm="handleLocationSelect"
    />
  </div>
</template>

<script>
import { listCount, getCount, delCount, addCount, updateCount, approveCount, executeCount, adjustCount, generateCountNo, generateDetails, calculateDifferences } from "@/api/inventory/count"
import { listWarehouse } from "@/api/erp/warehouse"
import { listMasterMaterial } from "@/api/erp/masterMaterial"
import MaterialSelectDialog from "@/components/MaterialSelectDialog"
import ShelfSelectDialog from "@/components/ShelfSelectDialog"
import LocationSelectDialog from "@/components/LocationSelectDialog"

export default {
  name: "Count",
  dicts: ['count_type', 'approval_status', 'count_status'],
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
      // 盘点单表表格数据
      countList: [],
      // 仓库列表
      warehouseList: [],
      // 物料列表
      materialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 明细表格数据
      detailList: [],
      // 物料选择弹窗
      materialSelectVisible: false,
      // 货架选择弹窗
      shelfSelectVisible: false,
      // 货位选择弹窗
      locationSelectVisible: false,
      // 当前编辑的明细行
      currentDetailRow: null,
      // 货架列表
      shelfList: [],
      // 货位列表
      locationList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        countNo: null,
        countName: null,
        countType: null,
        status: null,
        warehouseId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        countName: [
          { required: true, message: "盘点单名称不能为空", trigger: "blur" }
        ],
        countType: [
          { required: true, message: "盘点类型不能为空", trigger: "change" }
        ],
        warehouseId: [
          { required: true, message: "仓库不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getWarehouseList()
    this.getMaterialList()
  },
  methods: {
    /** 查询盘点单表列表 */
    getList() {
      this.loading = true
      listCount(this.queryParams).then(response => {
        this.countList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询仓库列表 */
    getWarehouseList() {
      listWarehouse({}).then(response => {
        this.warehouseList = response.rows
      })
    },
    /** 查询物料列表 */
    getMaterialList() {
      listMasterMaterial({}).then(response => {
        this.materialList = response.rows
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
        countNo: null,
        countName: null,
        countType: null,
        warehouseId: null,
        warehouseCode: null,
        warehouseName: null,
        countDate: null,
        status: "0",
        totalItems: 0,
        countedItems: 0,
        differenceItems: 0,
        totalDifferenceAmount: 0,
        approverId: null,
        approverName: null,
        approveTime: null,
        counterId: null,
        counterName: null,
        countTime: null,
        adjusterId: null,
        adjusterName: null,
        adjustTime: null,
        remark: null
      }
      this.detailList = []
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
      this.generateCountNo()
      this.open = true
      this.title = "添加盘点单表"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getCount(id).then(response => {
        this.form = response.data
        this.detailList = response.data.inventoryCountDetailList || []
        this.open = true
        this.title = "修改盘点单表"
      })
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.reset()
      const id = row.id
      getCount(id).then(response => {
        this.form = response.data
        this.detailList = response.data.inventoryCountDetailList || []
        this.open = true
        this.title = "盘点单详情"
      })
    },
    /** 审核按钮操作 */
    handleApprove(row) {
      this.$modal.confirm('是否确认审核盘点单编号为"' + row.countNo + '"的数据项？').then(function() {
        return approveCount(row)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核成功")
      }).catch(() => {})
    },
    /** 执行按钮操作 */
    handleExecute(row) {
      this.$modal.confirm('是否确认执行盘点单编号为"' + row.countNo + '"的数据项？').then(function() {
        return executeCount(row)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("执行成功")
      }).catch(() => {})
    },
    /** 调整按钮操作 */
    handleAdjust(row) {
      this.$modal.confirm('是否确认调整盘点单编号为"' + row.countNo + '"的数据项？').then(function() {
        return adjustCount(row)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("调整成功")
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.inventoryCountDetailList = this.detailList
          if (this.form.id != null) {
            updateCount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addCount(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除盘点单表编号为"' + ids + '"的数据项？').then(function() {
        return delCount(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('inventory/count/export', {
        ...this.queryParams
      }, `count_${new Date().getTime()}.xlsx`)
    },
    /** 生成盘点单号 */
    generateCountNo() {
      generateCountNo().then(response => {
        this.form.countNo = response.data
      })
    },
    /** 仓库变化处理 */
    handleWarehouseChange(warehouseId) {
      const warehouse = this.warehouseList.find(item => item.id === warehouseId)
      if (warehouse) {
        this.form.warehouseCode = warehouse.warehouseCode
        this.form.warehouseName = warehouse.warehouseName
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
        bookQuantity: 0,
        countQuantity: 0,
        differenceQuantity: 0,
        unitCost: 0,
        bookAmount: 0,
        countAmount: 0,
        differenceAmount: 0,
        shelfId: null,
        shelfCode: "",
        shelfName: "",
        locationId: null,
        locationCode: "",
        locationName: "",
        batchNo: "",
        productionDate: null,
        expiryDate: null,
        countStatus: "0"
      }
      this.detailList.push(obj)
    },
    /** 明细删除按钮操作 */
    handleDeleteDetail() {
      if (this.checkedDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的明细数据")
      } else {
        const detailList = this.detailList
        const checkedDetail = this.checkedDetail
        this.detailList = detailList.filter(function(item) {
          return checkedDetail.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleDetailSelectionChange(selection) {
      this.checkedDetail = selection.map(item => item.index)
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
      const material = this.materialList.find(item => item.materialCode === row.materialCode)
      if (material) {
        row.materialId = material.id
        row.materialName = material.materialName
        row.materialSpec = material.materialSpec
        row.unit = material.unit
        row.unitCost = material.unitCost
        row.bookQuantity = material.totalQuantity
        row.bookAmount = material.totalCost
      }
    },
    /** 数量变化处理 */
    handleQuantityChange(row) {
      // 计算差异数量
      row.differenceQuantity = row.countQuantity - row.bookQuantity
      
      // 计算金额
      if (row.bookQuantity && row.unitCost) {
        row.bookAmount = row.bookQuantity * row.unitCost
      }
      if (row.countQuantity && row.unitCost) {
        row.countAmount = row.countQuantity * row.unitCost
      }
      
      // 计算差异金额
      row.differenceAmount = row.countAmount - row.bookAmount
    },
    /** 生成明细按钮操作 */
    handleGenerateDetails() {
      if (!this.form.warehouseId) {
        this.$modal.msgError("请先选择仓库")
        return
      }
      
      this.$modal.confirm('是否确认生成盘点明细？').then(() => {
        generateDetails(this.form).then(response => {
          this.$modal.msgSuccess("生成成功")
          this.getList()
        })
      })
    },
    /** 计算差异按钮操作 */
    handleCalculateDifferences() {
      this.$modal.confirm('是否确认计算盘点差异？').then(() => {
        calculateDifferences(this.form).then(response => {
          this.$modal.msgSuccess("计算完成")
          this.getList()
        })
      })
    },
    /** 打开货架选择弹窗 */
    openShelfSelect(row) {
      this.currentDetailRow = row
      this.shelfSelectVisible = true
      this.getShelfList()
    },
    /** 打开货位选择弹窗 */
    openLocationSelect(row) {
      this.currentDetailRow = row
      this.locationSelectVisible = true
      this.getLocationList()
    },
    /** 处理货架选择确认 */
    handleShelfSelect(shelf) {
      if (this.currentDetailRow) {
        this.currentDetailRow.shelfId = shelf.id
        this.currentDetailRow.shelfCode = shelf.shelfCode
        this.currentDetailRow.shelfName = shelf.shelfName
        this.currentDetailRow = null
      }
    },
    /** 处理货位选择确认 */
    handleLocationSelect(location) {
      if (this.currentDetailRow) {
        this.currentDetailRow.locationId = location.id
        this.currentDetailRow.locationCode = location.locationCode
        this.currentDetailRow.locationName = location.locationName
        this.currentDetailRow = null
      }
    },
    /** 查询货架列表 */
    getShelfList() {
      // 这里需要调用货架API，暂时使用模拟数据
      this.shelfList = [
        { id: 1, shelfCode: 'A01', shelfName: 'A区01号货架' },
        { id: 2, shelfCode: 'A02', shelfName: 'A区02号货架' },
        { id: 3, shelfCode: 'B01', shelfName: 'B区01号货架' }
      ]
    },
    /** 查询货位列表 */
    getLocationList() {
      // 这里需要调用货位API，暂时使用模拟数据
      this.locationList = [
        { id: 1, locationCode: 'A01-01', locationName: 'A01-01货位' },
        { id: 2, locationCode: 'A01-02', locationName: 'A01-02货位' },
        { id: 3, locationCode: 'A02-01', locationName: 'A02-01货位' }
      ]
    }
  }
}
</script>
