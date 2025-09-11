<template>
  <el-dialog title="选择货位" :visible.sync="dialogVisible" width="800px" @close="handleClose">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams" class="demo-form-inline">
        <el-form-item label="货位编码">
          <el-input v-model="queryParams.locationCode" placeholder="请输入货位编码" clearable />
        </el-form-item>
        <el-form-item label="货位名称">
          <el-input v-model="queryParams.locationName" placeholder="请输入货位名称" clearable />
        </el-form-item>
        <el-form-item label="所属货架">
          <el-select v-model="queryParams.shelfId" placeholder="请选择货架" clearable>
            <el-option
              v-for="shelf in shelfList"
              :key="shelf.id"
              :label="shelf.shelfName"
              :value="shelf.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <el-table 
      :data="locationList" 
      @row-click="handleRowClick"
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column type="index" label="序号" width="50" />
      <el-table-column prop="locationCode" label="货位编码" width="120" />
      <el-table-column prop="locationName" label="货位名称" width="200" />
      <el-table-column prop="shelfName" label="所属货架" width="150" />
      <el-table-column prop="warehouseName" label="所属仓库" width="150" />
      <el-table-column prop="locationType" label="货位类型" width="100" />
      <el-table-column prop="capacity" label="容量" width="100" />
      <el-table-column prop="status" label="状态" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.location_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
    </el-table>
    
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm" :disabled="!selectedLocation">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listLocation } from "@/api/erp/location"
import { listShelf } from "@/api/erp/shelf"

export default {
  name: "LocationSelectDialog",
  dicts: ['location_status'],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    shelfId: {
      type: [Number, String],
      default: null
    }
  },
  data() {
    return {
      dialogVisible: false,
      locationList: [],
      shelfList: [],
      selectedLocation: null,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        locationCode: null,
        locationName: null,
        shelfId: null
      }
    }
  },
  watch: {
    visible: {
      handler(val) {
        this.dialogVisible = val
        if (val) {
          this.getList()
          this.getShelfList()
        }
      },
      immediate: true
    }
  },
  methods: {
    /** 查询货位列表 */
    getList() {
      // 添加货架ID筛选条件
      const params = { ...this.queryParams }
      if (this.shelfId) {
        params.shelfId = this.shelfId
      }
      
      listLocation(params).then(response => {
        this.locationList = response.rows || []
      }).catch(() => {
        // 如果API不存在，使用模拟数据，并根据货架ID筛选
        let mockData = [
          { id: 1, locationCode: 'A01-01', locationName: 'A01-01货位', shelfId: 1, shelfName: 'A区01号货架', warehouseName: '主仓库', locationType: '1', capacity: 50, status: '1' },
          { id: 2, locationCode: 'A01-02', locationName: 'A01-02货位', shelfId: 1, shelfName: 'A区01号货架', warehouseName: '主仓库', locationType: '1', capacity: 50, status: '1' },
          { id: 3, locationCode: 'A01-03', locationName: 'A01-03货位', shelfId: 1, shelfName: 'A区01号货架', warehouseName: '主仓库', locationType: '1', capacity: 50, status: '1' },
          { id: 4, locationCode: 'A02-01', locationName: 'A02-01货位', shelfId: 2, shelfName: 'A区02号货架', warehouseName: '主仓库', locationType: '1', capacity: 50, status: '1' },
          { id: 5, locationCode: 'A02-02', locationName: 'A02-02货位', shelfId: 2, shelfName: 'A区02号货架', warehouseName: '主仓库', locationType: '1', capacity: 50, status: '1' },
          { id: 6, locationCode: 'B01-01', locationName: 'B01-01货位', shelfId: 3, shelfName: 'B区01号货架', warehouseName: '主仓库', locationType: '2', capacity: 80, status: '1' },
          { id: 7, locationCode: 'B01-02', locationName: 'B01-02货位', shelfId: 3, shelfName: 'B区01号货架', warehouseName: '主仓库', locationType: '2', capacity: 80, status: '1' },
          { id: 8, locationCode: 'C01-01', locationName: 'C01-01货位', shelfId: 5, shelfName: 'C区01号货架', warehouseName: '主仓库', locationType: '2', capacity: 100, status: '1' }
        ]
        
        // 根据货架ID筛选
        if (this.shelfId) {
          this.locationList = mockData.filter(item => item.shelfId == this.shelfId)
        } else {
          this.locationList = mockData
        }
      })
    },
    /** 查询货架列表 */
    getShelfList() {
      listShelf({}).then(response => {
        this.shelfList = response.rows || []
      }).catch(() => {
        // 如果API不存在，使用模拟数据
        this.shelfList = [
          { id: 1, shelfCode: 'A01', shelfName: 'A区01号货架' },
          { id: 2, shelfCode: 'A02', shelfName: 'A区02号货架' },
          { id: 3, shelfCode: 'B01', shelfName: 'B区01号货架' },
          { id: 4, shelfCode: 'B02', shelfName: 'B区02号货架' },
          { id: 5, shelfCode: 'C01', shelfName: 'C区01号货架' }
        ]
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
        locationCode: null,
        locationName: null,
        shelfId: null
      }
      this.getList()
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.selectedLocation = row
    },
    /** 确认选择 */
    handleConfirm() {
      if (this.selectedLocation) {
        this.$emit('confirm', this.selectedLocation)
        this.handleClose()
      }
    },
    /** 关闭弹窗 */
    handleClose() {
      this.dialogVisible = false
      this.selectedLocation = null
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
