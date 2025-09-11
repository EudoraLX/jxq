<template>
  <el-dialog title="选择工艺路线" :visible.sync="visible" width="1000px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工艺路线编码" prop="processRouteCode">
        <el-input
          v-model="queryParams.processRouteCode"
          placeholder="请输入工艺路线编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工艺路线名称" prop="processRouteName">
        <el-input
          v-model="queryParams.processRouteName"
          placeholder="请输入工艺路线名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
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

    <el-table v-loading="loading" :data="processRouteList" @row-click="handleRowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工艺路线编码" align="center" prop="processRouteCode" width="150" />
      <el-table-column label="工艺路线名称" align="center" prop="processRouteName" :show-overflow-tooltip="true" />
      <el-table-column label="版本" align="center" prop="version" width="100" />
      <el-table-column label="生效日期" align="center" prop="effectiveDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectiveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="是否默认" align="center" prop="isDefault" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_yes_no" :value="scope.row.isDefault"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isActive" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_yes_no" :value="scope.row.isActive"/>
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
import { listProcessRoute } from "@/api/erp/processRoute"

export default {
  name: "ProcessRouteSelect",
  dicts: ['sys_normal_disable', 'business_yes_no'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工艺路线表格数据
      processRouteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      visible: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processRouteCode: null,
        processRouteName: null,
        status: null
      },
      // 选中的工艺路线
      selectedProcessRoute: null
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工艺路线列表 */
    getList() {
      this.loading = true
      listProcessRoute(this.queryParams).then(response => {
        this.processRouteList = response.rows
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
      this.handleQuery()
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.selectedProcessRoute = row
      // 清除其他行的选中状态
      this.$refs.table && this.$refs.table.clearSelection()
      this.$nextTick(() => {
        this.$refs.table && this.$refs.table.toggleRowSelection(row, true)
      })
    },
    /** 确认选择 */
    handleConfirm() {
      if (!this.selectedProcessRoute) {
        this.$modal.msgWarning("请选择一条工艺路线")
        return
      }
      this.$emit('select', this.selectedProcessRoute)
      this.visible = false
    },
    /** 显示弹窗 */
    show() {
      this.visible = true
      this.selectedProcessRoute = null
      this.getList()
    }
  }
}
</script>
