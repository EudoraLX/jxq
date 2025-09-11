<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="仓库编码" prop="warehouseCode">
        <el-input
          v-model="queryParams.warehouseCode"
          placeholder="请输入仓库编码"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库类型" prop="warehouseType">
        <el-select v-model="queryParams.warehouseType" placeholder="请选择仓库类型" clearable style="width: 200px">
          <el-option
            v-for="dict in dict.type.warehouse_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择状态" clearable style="width: 200px">
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          @click="handleAdd"
          v-hasPermi="['erp:warehouse:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:warehouse:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:warehouse:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          @click="handleExport"
          v-hasPermi="['erp:warehouse:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 仓库列表表格 -->
    <el-table v-loading="loading" :data="warehouseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="仓库编码" align="center" prop="warehouseCode">
        <template slot-scope="scope">
          <div>
            <div>{{ scope.row.warehouseCode }}</div>
            <barcode-display 
              v-if="scope.row.warehouseCode" 
              :value="scope.row.warehouseCode" 
              :width="1.5" 
              :height="30"
              format="CODE128"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="仓库名称" align="center" prop="warehouseName" />
      <el-table-column label="仓库类型" align="center" prop="warehouseType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.warehouse_type" :value="scope.row.warehouseType"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库地址" align="center" prop="address" :show-overflow-tooltip="true" />
      <el-table-column label="负责人" align="center" prop="manager" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isActive"
            :active-value="'1'"
            :inactive-value="'0'"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['erp:warehouse:edit']"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="280">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handlePreview(scope.row)"
            v-hasPermi="['erp:warehouse:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:warehouse:remove']"
          >删除</el-button>
          <barcode-printer 
            :machine-data="{
              machineCode: scope.row.warehouseCode,
              machineName: scope.row.warehouseName,
              machineType: scope.row.warehouseType,
              workCenterName: scope.row.address || '未设置地址'
            }"
            :title="'仓库标签'"
            :label-title="'仓库标签'"
            :info-labels="{
              code: '仓库编码',
              name: '仓库名称',
              type: '仓库类型',
              workCenter: '仓库地址'
            }"
            :machine-type-dict="dict.type.warehouse_type"
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

    <!-- 仓库表单弹窗 -->
    <warehouse-form-dialog 
      :value="warehouseFormVisible"
      @input="warehouseFormVisible = $event"
      :title="warehouseFormTitle"
      :warehouse-data="currentWarehouse"
      @success="handleWarehouseSuccess"
    />

    <!-- 仓库预览弹窗 -->
    <warehouse-preview-dialog 
      :value="previewVisible"
      @input="previewVisible = $event"
      :warehouse-data="previewWarehouse"
    />


  </div>
</template>

<script>
import { listWarehouse, getWarehouse, delWarehouse, updateWarehouse } from "@/api/erp/warehouse"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import BarcodePrinter from "@/components/BarcodePrinter"
import WarehouseFormDialog from "./components/WarehouseFormDialog.vue"
import WarehousePreviewDialog from "./components/WarehousePreviewDialog.vue"

export default {
  name: "Warehouse",
  components: {
    BarcodeDisplay,
    BarcodePrinter,
    WarehouseFormDialog,
    WarehousePreviewDialog,
  },
  dicts: ['warehouse_type', 'sys_yes_no'],
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
      // 仓库表格数据
      warehouseList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warehouseCode: undefined,
        warehouseName: undefined,
        warehouseType: undefined,
        isActive: undefined
      },
      // 弹窗控制
      warehouseFormVisible: false,
      warehouseFormTitle: "",
      currentWarehouse: {},
      previewVisible: false,
      previewWarehouse: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询仓库列表 */
    getList() {
      this.loading = true
      listWarehouse(this.queryParams).then(response => {
        console.log('Warehouse list response:', response)
        this.warehouseList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(error => {
        console.error('Error loading warehouse list:', error)
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryRef")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.currentWarehouse = {}
      this.warehouseFormTitle = "添加仓库"
      this.warehouseFormVisible = true
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      console.log('handleUpdate called with row:', row)
      const id = row.id || this.ids[0]
      console.log('Using ID:', id)
      
      if (!id) {
        this.$modal.msgError("无法获取仓库ID")
        return
      }
      
      getWarehouse(id).then(response => {
        console.log('Warehouse data received:', response)
        this.currentWarehouse = response.data
        this.warehouseFormTitle = "修改仓库"
        this.warehouseFormVisible = true
      }).catch(error => {
        console.error('Error fetching warehouse:', error)
        this.$modal.msgError("获取仓库信息失败")
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除仓库编号为"' + ids + '"的数据项？').then(function() {
        return delWarehouse(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/warehouse/export', {
        ...this.queryParams
      }, `warehouse_${new Date().getTime()}.xlsx`)
    },
    /** 预览按钮操作 */
    handlePreview(row) {
      console.log('Preview warehouse row:', row)
      // 确保有完整的仓库信息
      if (row.id) {
        this.previewWarehouse = row
        this.previewVisible = true
      } else {
        this.$modal.msgError("无法获取仓库ID")
      }
    },


    /** 仓库表单成功回调 */
    handleWarehouseSuccess() {
      this.warehouseFormVisible = false
      this.getList()
    },

    /** 处理仓库状态变化 */
    handleStatusChange(row) {
      const text = row.isActive === '1' ? '启用' : '禁用';
      this.$modal.confirm('确认要' + text + '仓库"' + row.warehouseName + '"吗？').then(() => {
        return updateWarehouse(row);
      }).then(() => {
        this.$modal.msgSuccess(text + '成功');
        this.getList();
      }).catch(() => {
        // 如果操作失败，恢复原状态
        row.isActive = row.isActive === '1' ? '0' : '1';
      });
    }
  }
}
</script>

<style scoped>
.el-table .barcode-display {
  margin-top: 5px;
}
</style>
