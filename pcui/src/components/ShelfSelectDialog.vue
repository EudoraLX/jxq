<template>
  <el-dialog title="选择货架" :visible.sync="dialogVisible" width="800px" @close="handleClose">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams" class="demo-form-inline">
        <el-form-item label="货架编码">
          <el-input v-model="queryParams.shelfCode" placeholder="请输入货架编码" clearable />
        </el-form-item>
        <el-form-item label="货架名称">
          <el-input v-model="queryParams.shelfName" placeholder="请输入货架名称" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <el-table 
      :data="shelfList" 
      @row-click="handleRowClick"
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column type="index" label="序号" width="50" />
      <el-table-column prop="shelfCode" label="货架编码" width="120" />
      <el-table-column prop="shelfName" label="货架名称" width="200" />
      <el-table-column prop="warehouseName" label="所属仓库" width="150" />
      <el-table-column prop="shelfType" label="货架类型" width="100" />
      <el-table-column prop="capacity" label="容量" width="100" />
      <el-table-column prop="status" label="状态" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.shelf_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
    </el-table>
    
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm" :disabled="!selectedShelf">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listShelf } from "@/api/erp/shelf"

export default {
  name: "ShelfSelectDialog",
  dicts: ['shelf_status'],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    warehouseId: {
      type: [Number, String],
      default: null
    }
  },
  data() {
    return {
      dialogVisible: false,
      shelfList: [],
      selectedShelf: null,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shelfCode: null,
        shelfName: null,
        warehouseId: null
      }
    }
  },
  watch: {
    visible: {
      handler(val) {
        this.dialogVisible = val
        if (val) {
          this.getList()
        }
      },
      immediate: true
    }
  },
  methods: {
    /** 查询货架列表 */
    getList() {
      // 添加仓库ID筛选条件
      const params = { ...this.queryParams }
      if (this.warehouseId) {
        params.warehouseId = this.warehouseId
      }
      
      listShelf(params).then(response => {
        this.shelfList = response.rows || []
      }).catch(() => {
        // 如果API不存在，使用模拟数据，并根据仓库ID筛选
        let mockData = [
          { id: 1, shelfCode: 'A01', shelfName: 'A区01号货架', warehouseId: 1, warehouseName: '主仓库', shelfType: '1', capacity: 100, status: '1' },
          { id: 2, shelfCode: 'A02', shelfName: 'A区02号货架', warehouseId: 1, warehouseName: '主仓库', shelfType: '1', capacity: 100, status: '1' },
          { id: 3, shelfCode: 'B01', shelfName: 'B区01号货架', warehouseId: 1, warehouseName: '主仓库', shelfType: '1', capacity: 100, status: '1' },
          { id: 4, shelfCode: 'B02', shelfName: 'B区02号货架', warehouseId: 1, warehouseName: '主仓库', shelfType: '1', capacity: 100, status: '1' },
          { id: 5, shelfCode: 'C01', shelfName: 'C区01号货架', warehouseId: 1, warehouseName: '主仓库', shelfType: '2', capacity: 150, status: '1' },
          { id: 6, shelfCode: 'D01', shelfName: 'D区01号货架', warehouseId: 2, warehouseName: '辅仓库', shelfType: '1', capacity: 100, status: '1' },
          { id: 7, shelfCode: 'D02', shelfName: 'D区02号货架', warehouseId: 2, warehouseName: '辅仓库', shelfType: '1', capacity: 100, status: '1' }
        ]
        
        // 根据仓库ID筛选
        if (this.warehouseId) {
          this.shelfList = mockData.filter(item => item.warehouseId == this.warehouseId)
        } else {
          this.shelfList = mockData
        }
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        shelfCode: null,
        shelfName: null,
        warehouseId: null
      }
      this.getList()
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.selectedShelf = row
    },
    /** 确认选择 */
    handleConfirm() {
      if (this.selectedShelf) {
        this.$emit('confirm', this.selectedShelf)
        this.handleClose()
      }
    },
    /** 关闭弹窗 */
    handleClose() {
      this.dialogVisible = false
      this.selectedShelf = null
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style scoped>
.search-form {
  margin-bottom: 20px;
}
</style>
