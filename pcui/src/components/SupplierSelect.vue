<template>
  <!-- 供应商选择弹窗 -->
  <el-dialog title="选择供应商" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="供应商编码" prop="supplierCode">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商类型" prop="supplierType">
        <el-select v-model="queryParams.supplierType" placeholder="请选择供应商类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_supplier_type"
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

    <el-table v-loading="loading" :data="supplierList" @row-click="handleRowClick" highlight-current-row>
      <el-table-column label="供应商编码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="供应商简称" align="center" prop="supplierShortName" />
      <el-table-column label="供应商类型" align="center" prop="supplierType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_supplier_type" :value="scope.row.supplierType"/>
        </template>
      </el-table-column>
      <el-table-column label="信用等级" align="center" prop="creditRating">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_credit_rating" :value="scope.row.creditRating"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
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
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listSupplier } from "@/api/erp/supplier"

export default {
  name: "SupplierSelect",
  dicts: ['erp_supplier_type', 'erp_credit_rating', 'business_active'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 供应商列表
      supplierList: [],
      // 是否显示弹窗
      visible: false,
      // 选中的供应商
      selectedSupplier: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierCode: null,
        supplierName: null,
        supplierType: null,
        isActive: "1"
      }
    }
  },
  methods: {
    /** 显示弹窗 */
    show() {
      this.visible = true
      this.getList()
    },
    /** 查询供应商列表 */
    getList() {
      this.loading = true
      listSupplier(this.queryParams).then(response => {
        this.supplierList = response.rows || []
        this.total = response.total || 0
        this.loading = false
      }).catch(error => {
        console.error('获取供应商列表失败:', error)
        this.supplierList = []
        this.total = 0
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
      this.handleQuery()
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.selectedSupplier = row
    },
    /** 确认选择 */
    handleConfirm() {
      if (!this.selectedSupplier) {
        this.$modal.msgWarning("请选择一个供应商")
        return
      }
      this.$emit('select', this.selectedSupplier)
      this.visible = false
      this.selectedSupplier = null
    }
  }
}
</script>
