<template>
  <el-dialog title="选择工序" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item label="工序编码" prop="processCode">
        <el-input
          v-model="queryParams.processCode"
          placeholder="请输入工序编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入工序名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序类型" prop="processType">
        <el-input
          v-model="queryParams.processType"
          placeholder="请输入工序类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="processList" @row-click="handleRowClick" highlight-current-row>
      <el-table-column type="index" width="50" align="center" />
      <el-table-column label="工序编码" align="center" prop="processCode" width="120" />
      <el-table-column label="工序名称" align="center" prop="processName" />
      <el-table-column label="工序类型" align="center" prop="processType" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.process_type" :value="scope.row.processType"/>
        </template>
      </el-table-column>
      <el-table-column label="标准时间(小时)" align="center" prop="standardTime" width="120" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
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
import { listProcess } from "@/api/erp/process"

export default {
  name: "ProcessSelect",
  dicts: ['process_type', 'sys_normal_disable'],
  data() {
    return {
      visible: false,
      loading: false,
      total: 0,
      processList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processCode: null,
        processName: null,
        processType: null,
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
    /** 查询工序列表 */
    getList() {
      this.loading = true
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows
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
        processCode: null,
        processName: null,
        processType: null,
        isActive: '1'
      }
      this.handleQuery()
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.handleSelect(row)
    },
    /** 选择工序 */
    handleSelect(row) {
      this.$emit('select', row)
      this.visible = false
    }
  }
}
</script>
