<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="是否启用" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择是否启用" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
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
          v-hasPermi="['inventory:inventory:add']"
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
          v-hasPermi="['inventory:inventory:edit']"
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
          v-hasPermi="['inventory:inventory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:inventory:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-warning"
          size="mini"
          @click="handleAlert"
          v-hasPermi="['inventory:inventory:list']"
        >库存预警</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="materialCode" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="物料规格" align="center" prop="materialSpec" />
      <el-table-column label="物料型号" align="center" prop="materialModel" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" />
      <el-table-column label="基本单位" align="center" prop="unit" />
      <el-table-column label="总库存数量" align="center" prop="totalQuantity" />
      <el-table-column label="可用库存数量" align="center" prop="availableQuantity" />
      <el-table-column label="已分配数量" align="center" prop="allocatedQuantity" />
      <el-table-column label="冻结数量" align="center" prop="frozenQuantity" />
      <el-table-column label="单位成本" align="center" prop="unitCost" />
      <el-table-column label="总成本" align="center" prop="totalCost" />
      <el-table-column label="最小库存" align="center" prop="minStock" />
      <el-table-column label="最大库存" align="center" prop="maxStock" />
      <el-table-column label="安全库存" align="center" prop="safetyStock" />
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
      <el-table-column label="最后入库日期" align="center" prop="lastInDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastInDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后出库日期" align="center" prop="lastOutDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastOutDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:inventory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:inventory:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['inventory:inventory:query']"
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

    <!-- 添加或修改库存主表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料ID" prop="materialId">
              <el-input v-model="form.materialId" placeholder="请输入物料ID" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料编码" prop="materialCode">
              <el-input v-model="form.materialCode" placeholder="请输入物料编码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料名称" prop="materialName">
              <el-input v-model="form.materialName" placeholder="请输入物料名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料规格" prop="materialSpec">
              <el-input v-model="form.materialSpec" placeholder="请输入物料规格" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料型号" prop="materialModel">
              <el-input v-model="form.materialModel" placeholder="请输入物料型号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库ID" prop="warehouseId">
              <el-input v-model="form.warehouseId" placeholder="请输入仓库ID" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库编码" prop="warehouseCode">
              <el-input v-model="form.warehouseCode" placeholder="请输入仓库编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-input v-model="form.warehouseName" placeholder="请输入仓库名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="基本单位" prop="unit">
              <el-input v-model="form.unit" placeholder="请输入基本单位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总库存数量" prop="totalQuantity">
              <el-input-number v-model="form.totalQuantity" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="可用库存数量" prop="availableQuantity">
              <el-input-number v-model="form.availableQuantity" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="已分配数量" prop="allocatedQuantity">
              <el-input-number v-model="form.allocatedQuantity" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="冻结数量" prop="frozenQuantity">
              <el-input-number v-model="form.frozenQuantity" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="在途数量" prop="onOrderQuantity">
              <el-input-number v-model="form.onOrderQuantity" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位成本" prop="unitCost">
              <el-input-number v-model="form.unitCost" :precision="4" :step="0.01" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总成本" prop="totalCost">
              <el-input-number v-model="form.totalCost" :precision="2" :step="0.01" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="最小库存" prop="minStock">
              <el-input-number v-model="form.minStock" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最大库存" prop="maxStock">
              <el-input-number v-model="form.maxStock" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="安全库存" prop="safetyStock">
              <el-input-number v-model="form.safetyStock" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="保质期(天)" prop="shelfLife">
              <el-input-number v-model="form.shelfLife" :step="1" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否启用" prop="isActive">
              <el-radio-group v-model="form.isActive">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
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

    <!-- 库存预警对话框 -->
    <el-dialog title="库存预警" :visible.sync="alertOpen" width="1200px" append-to-body>
      <el-table v-loading="alertLoading" :data="alertList">
        <el-table-column label="物料编码" align="center" prop="materialCode" />
        <el-table-column label="物料名称" align="center" prop="materialName" />
        <el-table-column label="仓库名称" align="center" prop="warehouseName" />
        <el-table-column label="当前库存" align="center" prop="availableQuantity" />
        <el-table-column label="安全库存" align="center" prop="safetyStock" />
        <el-table-column label="最小库存" align="center" prop="minStock" />
        <el-table-column label="预警状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.availableQuantity <= scope.row.minStock" type="danger">库存不足</el-tag>
            <el-tag v-else-if="scope.row.availableQuantity <= scope.row.safetyStock" type="warning">库存预警</el-tag>
            <el-tag v-else type="success">正常</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listInventory, getInventory, delInventory, addInventory, updateInventory, getInventoryAlertList } from "@/api/inventory/inventory"
import { listWarehouse } from "@/api/erp/warehouse"

export default {
  name: "Inventory",
  dicts: ['sys_normal_disable'],
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
      // 库存主表表格数据
      inventoryList: [],
      // 仓库列表
      warehouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示预警弹出层
      alertOpen: false,
      // 预警加载状态
      alertLoading: false,
      // 预警列表
      alertList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
        warehouseId: null,
        isActive: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialId: [
          { required: true, message: "物料ID不能为空", trigger: "blur" }
        ],
        materialCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "仓库ID不能为空", trigger: "blur" }
        ],
        unit: [
          { required: true, message: "基本单位不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getWarehouseList()
  },
  methods: {
    /** 查询库存主表列表 */
    getList() {
      this.loading = true
      listInventory(this.queryParams).then(response => {
        this.inventoryList = response.rows
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
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        materialId: null,
        materialCode: null,
        materialName: null,
        materialSpec: null,
        materialModel: null,
        categoryId: null,
        categoryCode: null,
        categoryName: null,
        warehouseId: null,
        warehouseCode: null,
        warehouseName: null,
        unit: null,
        totalQuantity: null,
        availableQuantity: null,
        allocatedQuantity: null,
        frozenQuantity: null,
        onOrderQuantity: null,
        unitCost: null,
        totalCost: null,
        lastInDate: null,
        lastOutDate: null,
        lastCountDate: null,
        minStock: null,
        maxStock: null,
        safetyStock: null,
        shelfLife: null,
        expiryDate: null,
        isActive: "1",
        remark: null
      }
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
      this.title = "添加库存主表"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getInventory(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改库存主表"
      })
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.reset()
      const id = row.id
      getInventory(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "库存详情"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInventory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addInventory(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除库存主表编号为"' + ids + '"的数据项？').then(function() {
        return delInventory(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 库存预警按钮操作 */
    handleAlert() {
      this.alertOpen = true
      this.alertLoading = true
      getInventoryAlertList({}).then(response => {
        this.alertList = response.rows
        this.alertLoading = false
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('inventory/inventory/export', {
        ...this.queryParams
      }, `inventory_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
