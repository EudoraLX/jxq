<template>
  <!-- 关联字段选择弹窗 -->
  <el-dialog title="选择关联字段" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="字段名称" prop="columnName">
        <el-input
          v-model="queryParams.columnName"
          placeholder="请输入字段名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="字段描述" prop="columnComment">
        <el-input
          v-model="queryParams.columnComment"
          placeholder="请输入字段描述"
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
      <el-table @selection-change="handleSelectionChange" ref="table" :data="columnList" height="260px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="columnName" label="字段名称" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="columnComment" label="字段描述" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="columnType" label="字段类型" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="javaField" label="Java属性" :show-overflow-tooltip="true"></el-table-column>
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
      <el-button type="primary" @click="handleSelectFields">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getDbTableColumns } from "@/api/tool/gen"

export default {
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中的字段
      selectedFields: [],
      // 总条数
      total: 0,
      // 字段数据
      columnList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        columnName: undefined,
        columnComment: undefined
      },
      // 当前关联表
      currentTable: null
    }
  },
  methods: {
    // 显示弹框
    show(tableName) {
      if (!tableName) {
        this.$modal.msgError("请先选择关联表")
        return
      }
      this.currentTable = tableName
      this.getList()
      this.visible = true
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectedFields = selection
    },
    // 查询字段数据
    getList() {
      if (!this.currentTable) {
        return
      }
      // 获取指定表的字段信息
      getDbTableColumns(this.currentTable).then(response => {
        if (response.code === 200) {
          this.columnList = response.data
          this.total = this.columnList.length
        }
      }).catch(() => {
        this.columnList = []
        this.total = 0
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
    // 选择字段
    handleSelectFields() {
      if (this.selectedFields.length === 0) {
        this.$modal.msgError("请先选择关联字段")
        return
      }
      this.$emit('select', this.selectedFields)
      this.visible = false
      this.selectedFields = []
    }
  }
}
</script>
