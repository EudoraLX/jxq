<template>
  <!-- 基本单位选择弹窗 -->
  <el-dialog title="选择基本单位" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="单位编码" prop="unitCode">
        <el-input
          v-model="queryParams.unitCode"
          placeholder="请输入单位编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位名称" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    
    <el-table 
      @row-click="clickRow" 
      ref="table" 
      :data="unitList" 
      height="400px"
      highlight-current-row
    >
      <el-table-column label="单位编码" prop="unitCode" width="120" />
      <el-table-column label="单位名称" prop="unitName" width="150" />
      <el-table-column label="单位符号" prop="unitSymbol" width="100" />
      <el-table-column label="状态" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
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
      <el-button type="primary" @click="handleSelectUnit">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listUnit } from "@/api/erp/unit"

export default {
  name: "BaseUnitSelect",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中的单位
      selectedUnit: null,
      // 总条数
      total: 0,
      // 单位列表
      unitList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitCode: null,
        unitName: null,
        isBaseUnit: 1, // 只查询基本单位
        status: 0 // 只查询正常状态
      }
    }
  },
  methods: {
    // 显示弹框
    show() {
      this.visible = true
      this.getList()
    },
    
    // 点击行选择
    clickRow(row) {
      this.selectedUnit = row
      // 高亮选中的行
      this.$refs.table.setCurrentRow(row)
    },
    
    // 查询基本单位列表
    getList() {
      listUnit(this.queryParams).then(response => {
        this.unitList = response.rows
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
      this.queryParams.isBaseUnit = 1
      this.queryParams.status = 0
      this.handleQuery()
    },
    
    /** 选择单位操作 */
    handleSelectUnit() {
      if (!this.selectedUnit) {
        this.$modal.msgError("请选择要关联的基本单位")
        return
      }
      this.$emit("select", this.selectedUnit)
      this.visible = false
    },
    
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
