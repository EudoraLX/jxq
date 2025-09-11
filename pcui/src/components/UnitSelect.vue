<template>
  <!-- 单位选择弹窗 -->
  <el-dialog title="选择单位" :visible.sync="visible" width="800px" top="5vh" append-to-body>
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
      <el-form-item label="单位符号" prop="unitSymbol">
        <el-input
          v-model="queryParams.unitSymbol"
          placeholder="请输入单位符号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="unitList" @row-click="handleRowClick" highlight-current-row>
      <el-table-column label="单位编码" align="center" prop="unitCode" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="单位符号" align="center" prop="unitSymbol" />
      <el-table-column label="是否基本单位" align="center" prop="isBaseUnit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_yes_no" :value="scope.row.isBaseUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
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
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listUnit } from "@/api/erp/unit"

export default {
  name: "UnitSelect",
  dicts: ['business_yes_no', 'sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 单位列表
      unitList: [],
      // 是否显示弹窗
      visible: false,
      // 选中的单位
      selectedUnit: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitCode: null,
        unitName: null,
        unitSymbol: null,
        status: "0"
      }
    }
  },
  methods: {
    /** 显示弹窗 */
    show() {
      this.visible = true
      this.getList()
    },
         /** 查询单位列表 */
     getList() {
       this.loading = true
       console.log('查询参数:', this.queryParams)
       
       listUnit(this.queryParams).then(response => {
         console.log('API响应:', response)
         console.log('响应数据:', response.data)
         
         // 检查响应数据结构
         if (response.data) {
           this.unitList = response.data.rows || response.data || []
           this.total = response.data.total || 0
         } else if (response.rows) {
           this.unitList = response.rows || []
           this.total = response.total || 0
         } else {
           this.unitList = response || []
           this.total = 0
         }
         
         console.log('设置后的数据:', {
           unitList: this.unitList,
           total: this.total,
           length: this.unitList.length
         })
         
         this.loading = false
       }).catch(error => {
         console.error('获取单位列表失败:', error)
         this.unitList = []
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
      this.selectedUnit = row
    },
    /** 确认选择 */
    handleConfirm() {
      if (!this.selectedUnit) {
        this.$modal.msgWarning("请选择一个单位")
        return
      }
      this.$emit('select', this.selectedUnit)
      this.visible = false
      this.selectedUnit = null
    }
  }
}
</script>
