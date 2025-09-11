<template>
  <!-- 工作中心选择弹窗 -->
  <el-dialog title="选择工作中心" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="中心编码" prop="workCenterCode">
        <el-input
          v-model="queryParams.workCenterCode"
          placeholder="请输入中心编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中心名称" prop="workCenterName">
        <el-input
          v-model="queryParams.workCenterName"
          placeholder="请输入中心名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中心类型" prop="workCenterType">
        <el-select v-model="queryParams.workCenterType" placeholder="请选择中心类型" clearable>
          <el-option
            v-for="dict in dict.type.work_center_type"
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

    <el-table v-loading="loading" :data="workCenterList" @row-click="handleRowClick" highlight-current-row>
      <el-table-column type="index" width="50" align="center" />
      <el-table-column label="中心编码" align="center" prop="workCenterCode" width="120" />
      <el-table-column label="中心名称" align="center" prop="workCenterName" />
      <el-table-column label="中心类型" align="center" prop="workCenterType" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.work_center_type" :value="scope.row.workCenterType"/>
        </template>
      </el-table-column>
      <el-table-column label="位置" align="center" prop="location" width="120" />
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
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listWorkCenter } from "@/api/erp/workCenter"

export default {
  name: "WorkCenterSelect",
  dicts: ['work_center_type', 'business_active'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 工作中心列表
      workCenterList: [],
      // 是否显示弹窗
      visible: false,
      // 选中的工作中心
      selectedWorkCenter: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workCenterCode: null,
        workCenterName: null,
        workCenterType: null,
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
    /** 查询工作中心列表 */
    getList() {
      this.loading = true
      listWorkCenter(this.queryParams).then(response => {
        this.workCenterList = response.rows || []
        this.total = response.total || 0
        this.loading = false
      }).catch(error => {
        console.error('获取工作中心列表失败:', error)
        this.workCenterList = []
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
      this.handleSelect(row)
    },
    /** 选择工作中心 */
    handleSelect(row) {
      this.$emit('select', row)
      this.visible = false
    },
    /** 确认选择 */
    handleConfirm() {
      if (!this.selectedWorkCenter) {
        this.$modal.msgWarning("请选择一个工作中心")
        return
      }
      this.$emit('select', this.selectedWorkCenter)
      this.visible = false
      this.selectedWorkCenter = null
    }
  }
}
</script>
