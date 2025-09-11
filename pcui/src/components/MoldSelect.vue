<template>
  <el-dialog title="选择模具" :visible.sync="visible" width="900px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item label="模具编码" prop="moldCode">
        <el-input
          v-model="queryParams.moldCode"
          placeholder="请输入模具编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模具名称" prop="moldName">
        <el-input
          v-model="queryParams.moldName"
          placeholder="请输入模具名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模具类型" prop="moldType">
        <el-select v-model="queryParams.moldType" placeholder="请选择模具类型" clearable>
          <el-option
            v-for="dict in dict.type.mold_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="模具分类" prop="moldCategory">
        <el-select v-model="queryParams.moldCategory" placeholder="请选择模具分类" clearable>
          <el-option
            v-for="dict in dict.type.mold_category"
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

    <el-table v-loading="loading" :data="moldList" @row-click="handleRowClick" highlight-current-row>
      <el-table-column type="index" width="50" align="center" />
      <el-table-column label="模具编码" align="center" prop="moldCode" width="120" />
      <el-table-column label="模具名称" align="center" prop="moldName" />
      <el-table-column label="模具类型" align="center" prop="moldType" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mold_type" :value="scope.row.moldType"/>
        </template>
      </el-table-column>
      <el-table-column label="模具分类" align="center" prop="moldCategory" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mold_category" :value="scope.row.moldCategory"/>
        </template>
      </el-table-column>
      <el-table-column label="产品物料" align="center" prop="productMaterialName" width="150" />
      <el-table-column label="模具状态" align="center" prop="moldStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_status" :value="scope.row.moldStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isActive" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
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
import { listMold } from "@/api/erp/mold"

export default {
  name: "MoldSelect",
  dicts: ['mold_type', 'mold_category', 'equipment_status', 'business_active'],
  data() {
    return {
      visible: false,
      loading: false,
      total: 0,
      moldList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        moldCode: null,
        moldName: null,
        moldType: null,
        moldCategory: null,
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
    /** 查询模具列表 */
    getList() {
      this.loading = true
      listMold(this.queryParams).then(response => {
        this.moldList = response.rows
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
        moldCode: null,
        moldName: null,
        moldType: null,
        moldCategory: null,
        isActive: '1'
      }
      this.handleQuery()
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.handleSelect(row)
    },
    /** 选择模具 */
    handleSelect(row) {
      this.$emit('select', row)
      this.visible = false
    }
  }
}
</script>
