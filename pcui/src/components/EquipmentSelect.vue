<template>
  <el-dialog title="选择设备" :visible.sync="visible" width="900px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item label="设备编码" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入设备编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="equipmentType">
        <el-select v-model="queryParams.equipmentType" placeholder="请选择设备类型" clearable>
          <el-option
            v-for="dict in dict.type.equipment_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择设备状态" clearable>
          <el-option
            v-for="dict in dict.type.equipment_status"
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

    <el-table v-loading="loading" :data="equipmentList" @row-click="handleRowClick" highlight-current-row>
      <el-table-column type="index" width="50" align="center" />
      <el-table-column label="设备编码" align="center" prop="equipmentCode" width="120" />
      <el-table-column label="设备名称" align="center" prop="equipmentName" />
      <el-table-column label="设备类型" align="center" prop="equipmentType" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_type" :value="scope.row.equipmentType"/>
        </template>
      </el-table-column>
      <el-table-column label="设备状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="工作中心" align="center" prop="workCenterName" width="120" />
      <el-table-column label="供应商" align="center" prop="supplierName" width="120" />
      <el-table-column label="操作" align="center" width="80">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleSelect(scope.row)"
          >选择</el-button>
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

    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listEquipment } from "@/api/erp/equipment"

export default {
  name: "EquipmentSelect",
  dicts: ['equipment_type', 'equipment_status'],
  data() {
    return {
      visible: false,
      loading: false,
      total: 0,
      equipmentList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentCode: null,
        equipmentName: null,
        equipmentType: null,
        status: null,
        isActive: '1'
      }
    }
  },
  methods: {
    show() {
      this.visible = true
      this.resetQuery()
      this.getList()
    },
    /** 查询设备列表 */
    getList() {
      this.loading = true
      listEquipment(this.queryParams).then(response => {
        this.equipmentList = response.rows
        this.total = response.total
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
      this.resetForm("queryForm")
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        equipmentCode: null,
        equipmentName: null,
        equipmentType: null,
        status: null,
        isActive: '1'
      }
      this.handleQuery()
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.handleSelect(row)
    },
    /** 选择设备 */
    handleSelect(row) {
      this.$emit('select', row)
      this.visible = false
    }
  }
}
</script>
