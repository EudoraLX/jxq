<template>
  <!-- 关联表选择弹窗 -->
  <el-dialog title="选择关联表" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="表名称" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入表名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="请输入表描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="handleRowClick" ref="table" :data="tableList" height="260px">
        <el-table-column prop="tableName" label="表名称" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="tableComment" label="表描述" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" :show-overflow-tooltip="true"></el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectTable">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listAllDbTable } from "@/api/tool/gen"

export default {
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中的表
      selectedTable: null,
      // 总条数
      total: 0,
      // 表数据
      tableList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      }
    }
  },
  methods: {
    // 显示弹框
    show() {
      this.visible = true
      this.getList()
    },
    // 行点击事件
    handleRowClick(row) {
      this.selectedTable = row
      // 高亮选中的行
      this.$refs.table.setCurrentRow(row)
    },
    // 查询表数据
    getList() {
      listAllDbTable(this.queryParams).then(response => {
        this.tableList = response.rows
        this.total = response.total
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
    // 选择表
    handleSelectTable() {
      if (!this.selectedTable) {
        this.$modal.msgError("请先选择关联表")
        return
      }
      this.$emit('select', this.selectedTable)
      this.visible = false
      this.selectedTable = null
    }
  }
}
</script>
