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
      <el-form-item label="货架" prop="shelfId">
        <el-select v-model="queryParams.shelfId" placeholder="请选择货架" clearable>
          <el-option
            v-for="shelf in shelfList"
            :key="shelf.id"
            :label="shelf.shelfName"
            :value="shelf.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="货位" prop="locationId">
        <el-select v-model="queryParams.locationId" placeholder="请选择货位" clearable>
          <el-option
            v-for="location in locationList"
            :key="location.id"
            :label="location.locationName"
            :value="location.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="批次号" prop="batchNo">
        <el-input
          v-model="queryParams.batchNo"
          placeholder="请输入批次号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryDetailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="materialCode" width="120" />
      <el-table-column label="物料名称" align="center" prop="materialName" width="150" />
      <el-table-column label="物料规格" align="center" prop="materialSpec" width="120" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" width="120" />
      <el-table-column label="货架编码" align="center" prop="shelfCode" width="100" />
      <el-table-column label="货架名称" align="center" prop="shelfName" width="120" />
      <el-table-column label="货位编码" align="center" prop="locationCode" width="100" />
      <el-table-column label="货位名称" align="center" prop="locationName" width="120" />
      <el-table-column label="批次号" align="center" prop="batchNo" width="120" />
      <el-table-column label="批号" align="center" prop="lotNo" width="120" />
      <el-table-column label="序列号" align="center" prop="serialNo" width="120" />
      <el-table-column label="库存数量" align="center" prop="quantity" width="100" />
      <el-table-column label="可用数量" align="center" prop="availableQuantity" width="100" />
      <el-table-column label="已分配数量" align="center" prop="allocatedQuantity" width="100" />
      <el-table-column label="冻结数量" align="center" prop="frozenQuantity" width="100" />
      <el-table-column label="单位成本" align="center" prop="unitCost" width="100" />
      <el-table-column label="总成本" align="center" prop="totalCost" width="100" />
      <el-table-column label="生产日期" align="center" prop="productionDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到期日期" align="center" prop="expiryDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="质量状态" align="center" prop="qualityStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quality_status" :value="scope.row.qualityStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="质量等级" align="center" prop="qualityLevel" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quality_level" :value="scope.row.qualityLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['inventory:inventory:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:inventory:edit']"
          >调整</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh"
            @click="handleTransfer(scope.row)"
            v-hasPermi="['inventory:transfer:add']"
          >调拨</el-button>
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

    <!-- 库存明细详情对话框 -->
    <el-dialog title="库存明细详情" :visible.sync="detailOpen" width="800px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="物料编码">{{ detailForm.materialCode }}</el-descriptions-item>
        <el-descriptions-item label="物料名称">{{ detailForm.materialName }}</el-descriptions-item>
        <el-descriptions-item label="物料规格">{{ detailForm.materialSpec }}</el-descriptions-item>
        <el-descriptions-item label="物料型号">{{ detailForm.materialModel }}</el-descriptions-item>
        <el-descriptions-item label="仓库名称">{{ detailForm.warehouseName }}</el-descriptions-item>
        <el-descriptions-item label="货架名称">{{ detailForm.shelfName }}</el-descriptions-item>
        <el-descriptions-item label="货位名称">{{ detailForm.locationName }}</el-descriptions-item>
        <el-descriptions-item label="批次号">{{ detailForm.batchNo }}</el-descriptions-item>
        <el-descriptions-item label="批号">{{ detailForm.lotNo }}</el-descriptions-item>
        <el-descriptions-item label="序列号">{{ detailForm.serialNo }}</el-descriptions-item>
        <el-descriptions-item label="库存数量">{{ detailForm.quantity }}</el-descriptions-item>
        <el-descriptions-item label="可用数量">{{ detailForm.availableQuantity }}</el-descriptions-item>
        <el-descriptions-item label="已分配数量">{{ detailForm.allocatedQuantity }}</el-descriptions-item>
        <el-descriptions-item label="冻结数量">{{ detailForm.frozenQuantity }}</el-descriptions-item>
        <el-descriptions-item label="单位成本">{{ detailForm.unitCost }}</el-descriptions-item>
        <el-descriptions-item label="总成本">{{ detailForm.totalCost }}</el-descriptions-item>
        <el-descriptions-item label="生产日期">{{ parseTime(detailForm.productionDate, '{y}-{m}-{d}') }}</el-descriptions-item>
        <el-descriptions-item label="到期日期">{{ parseTime(detailForm.expiryDate, '{y}-{m}-{d}') }}</el-descriptions-item>
        <el-descriptions-item label="质量状态">
          <dict-tag :options="dict.type.quality_status" :value="detailForm.qualityStatus"/>
        </el-descriptions-item>
        <el-descriptions-item label="质量等级">
          <dict-tag :options="dict.type.quality_level" :value="detailForm.qualityLevel"/>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import { listInventoryDetail, getInventoryDetail } from "@/api/inventory/inventoryDetail"
import { listWarehouse } from "@/api/erp/warehouse"
import { listShelf } from "@/api/erp/shelf"
import { listLocation } from "@/api/erp/location"

export default {
  name: "InventoryDetail",
  dicts: ['quality_status', 'quality_level'],
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
      // 库存明细表格数据
      inventoryDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      detailOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
        warehouseId: null,
        shelfId: null,
        locationId: null,
        batchNo: null,
        lotNo: null,
        serialNo: null
      },
      // 表单参数
      detailForm: {},
      // 仓库列表
      warehouseList: [],
      // 货架列表
      shelfList: [],
      // 货位列表
      locationList: []
    };
  },
  created() {
    this.getList();
    this.getWarehouseList();
    this.getShelfList();
    this.getLocationList();
  },
  methods: {
    /** 查询库存明细列表 */
    getList() {
      this.loading = true;
      listInventoryDetail(this.queryParams).then(response => {
        this.inventoryDetailList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询仓库列表 */
    getWarehouseList() {
      listWarehouse({}).then(response => {
        this.warehouseList = response.rows
      })
    },
    /** 查询货架列表 */
    getShelfList() {
      listShelf({}).then(response => {
        this.shelfList = response.rows
      })
    },
    /** 查询货位列表 */
    getLocationList() {
      listLocation({}).then(response => {
        this.locationList = response.rows
      })
    },
    // 取消按钮
    cancel() {
      this.detailOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.detailForm = {
        id: null,
        inventoryId: null,
        materialId: null,
        warehouseId: null,
        shelfId: null,
        locationId: null,
        batchNo: null,
        lotNo: null,
        serialNo: null,
        quantity: null,
        availableQuantity: null,
        allocatedQuantity: null,
        frozenQuantity: null,
        unitCost: null,
        totalCost: null,
        productionDate: null,
        expiryDate: null,
        qualityStatus: null,
        qualityLevel: null
      };
      this.resetForm("detailForm");
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
    /** 详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const id = row.id || this.ids
      getInventoryDetail(id).then(response => {
        this.detailForm = response.data;
        this.detailOpen = true;
        this.title = "库存明细详情";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$router.push({
        path: '/inventory/inventory/adjust',
        query: { id: row.id }
      });
    },
    /** 调拨按钮操作 */
    handleTransfer(row) {
      this.$router.push({
        path: '/inventory/transfer/add',
        query: { 
          materialId: row.materialId,
          fromWarehouseId: row.warehouseId,
          fromShelfId: row.shelfId,
          fromLocationId: row.locationId
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('inventory/inventoryDetail/export', {
        ...this.queryParams
      }, `库存明细_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
