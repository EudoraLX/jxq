<template>
  <el-dialog :title="`${warehouseData.warehouseName} - 仓库预览`" :visible.sync="visible" width="95%" append-to-body>
    <div class="warehouse-preview">
      <!-- 仓库基本信息 -->
      <div class="warehouse-info">
        <div class="warehouse-details">
          <div class="warehouse-text">
            <h3>{{ warehouseData.warehouseName }}</h3>
            <p>编码：{{ warehouseData.warehouseCode }}</p>
            <p>类型：{{ getDictLabel(dict.type.warehouse_type, warehouseData.warehouseType) }}</p>
            <p>地址：{{ warehouseData.address }}</p>
            <p>负责人：{{ warehouseData.manager }}</p>
            <p>联系电话：{{ warehouseData.phone }}</p>
          </div>
          <div class="warehouse-barcode">
            <barcode-display 
              v-if="warehouseData.warehouseCode" 
              :value="warehouseData.warehouseCode" 
              :width="2" 
              :height="80"
              format="CODE128"
            />
          </div>
        </div>
      </div>

      <!-- 仓库统计信息 -->
      <div class="warehouse-stats">
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="stat-card">
              <div class="stat-icon">📦</div>
              <div class="stat-content">
                <div class="stat-number">{{ shelfList.length }}</div>
                <div class="stat-label">货架总数</div>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card">
              <div class="stat-icon">📍</div>
              <div class="stat-content">
                <div class="stat-number">{{ locationList.length }}</div>
                <div class="stat-label">货位总数</div>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card">
              <div class="stat-icon">✅</div>
              <div class="stat-content">
                <div class="stat-number">{{ getFreeLocationsCount() }}</div>
                <div class="stat-label">空闲货位</div>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card">
              <div class="stat-icon">🚫</div>
              <div class="stat-content">
                <div class="stat-number">{{ getOccupiedLocationsCount() }}</div>
                <div class="stat-label">占用货位</div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>

      <!-- 仓库布局 -->
      <div class="warehouse-layout">
        <div class="layout-header">
          <h3>仓库布局图</h3>
          <div class="layout-controls">
            <el-button size="mini" @click="toggleViewMode">
              {{ viewMode === 'grid' ? '切换为列表视图' : '切换为网格视图' }}
            </el-button>
            <el-button size="mini" @click="refreshLayout">
              <i class="el-icon-refresh"></i> 刷新
            </el-button>
          </div>
        </div>

        <!-- 网格视图 -->
        <div v-if="viewMode === 'grid'" class="shelf-grid-container">
          <div class="shelf-container" v-for="shelf in sortedShelfList" :key="shelf.id">
            <div class="shelf-header">
              <h4>{{ shelf.shelfName }}</h4>
              <span class="shelf-code clickable" @click="handleShelfPreview(shelf)">{{ shelf.shelfCode }}</span>
            </div>
            <div class="shelf-info">
              <span class="shelf-type">{{ getDictLabel(dict.type.shelf_type, shelf.shelfType) }}</span>
              <span class="shelf-status" :class="shelf.isActive === '1' ? 'active' : 'inactive'">
                {{ shelf.isActive === '1' ? '启用' : '禁用' }}
              </span>
            </div>
            <div class="shelf-grid">
              <div 
                v-for="location in getLocationsByShelf(shelf.id)" 
                :key="location.id"
                class="location-cell clickable"
                :class="getLocationStatusClass(location)"
                :title="getLocationTooltip(location)"
                @click="handleLocationPreview(location)"
              >
                <div class="location-code">{{ location.locationCode }}</div>
                <div class="location-info">
                  <span>{{ location.levelNum }}-{{ location.positionNum }}</span>
                </div>
                <div class="location-status-indicator" :class="getLocationStatusClass(location)"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 列表视图 -->
        <div v-else class="shelf-list-container">
          <el-table :data="sortedShelfList" style="width: 100%">
            <el-table-column prop="shelfCode" label="货架编码" width="120" />
            <el-table-column prop="shelfName" label="货架名称" width="150" />
            <el-table-column prop="shelfType" label="货架类型" width="120">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.shelf_type" :value="scope.row.shelfType"/>
              </template>
            </el-table-column>
            <el-table-column prop="isActive" label="状态" width="80">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isActive"/>
              </template>
            </el-table-column>
            <el-table-column label="货位数量" width="100">
              <template slot-scope="scope">
                <span class="location-count">{{ getLocationsByShelf(scope.row.id).length }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="handleShelfPreview(scope.row)">
                  查看货架
                </el-button>
                <el-button size="mini" type="text" @click="handleShelfLocations(scope.row)">
                  查看货位
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>

    <!-- 货架预览弹窗 -->
    <shelf-preview-dialog 
      v-model="shelfPreviewVisible"
      :shelf-data="currentShelf"
    />

    <!-- 货位预览弹窗 -->
    <location-preview-dialog 
      v-model="locationPreviewVisible"
      :location-data="currentLocation"
    />

    <!-- 货架货位弹窗 -->
    <el-dialog title="货架货位详情" :visible.sync="shelfLocationsVisible" width="800px" append-to-body>
      <div class="shelf-locations-detail">
        <h4>{{ currentShelf.shelfName }} ({{ currentShelf.shelfCode }})</h4>
        <el-table :data="getLocationsByShelf(currentShelf.id)" style="width: 100%">
          <el-table-column prop="locationCode" label="货位编码" width="120" />
          <el-table-column prop="locationName" label="货位名称" width="150" />
          <el-table-column prop="locationType" label="货位类型" width="120">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.location_type" :value="scope.row.locationType"/>
            </template>
          </el-table-column>
          <el-table-column prop="levelNum" label="层级" width="80" />
          <el-table-column prop="positionNum" label="位置" width="80" />
          <el-table-column prop="isOccupied" label="占用状态" width="100">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.location_is_occupied" :value="scope.row.isOccupied"/>
            </template>
          </el-table-column>
          <el-table-column prop="isActive" label="状态" width="80">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isActive"/>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
import { getWarehouse } from "@/api/erp/warehouse"
import { getShelvesByWarehouse } from "@/api/erp/shelf"
import { getLocationsByShelf } from "@/api/erp/location"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import ShelfPreviewDialog from "./ShelfPreviewDialog.vue"
import LocationPreviewDialog from "./LocationPreviewDialog.vue"

export default {
  name: "WarehousePreviewDialog",
  components: {
    BarcodeDisplay,
    ShelfPreviewDialog,
    LocationPreviewDialog
  },
  dicts: ['warehouse_type', 'shelf_type', 'sys_yes_no', 'location_type', 'location_is_occupied'],
  props: {
    value: {
      type: Boolean,
      default: false
    },
    warehouseData: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      shelfList: [],
      locationList: [],
      currentShelf: {},
      currentLocation: {},
      shelfPreviewVisible: false,
      locationPreviewVisible: false,
      shelfLocationsVisible: false,
      viewMode: 'grid' // 'grid' 或 'list'
    }
  },
  computed: {
    visible: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    },
    sortedShelfList() {
      return [...this.shelfList].sort((a, b) => a.shelfCode.localeCompare(b.shelfCode))
    }
  },
  watch: {
    value(val) {
      console.log('WarehousePreviewDialog watch triggered:', val)
      console.log('warehouseData:', this.warehouseData)
      if (val && this.warehouseData.id) {
        console.log('Starting to get preview data for warehouse ID:', this.warehouseData.id)
        this.getPreviewData()
      } else {
        console.warn('Cannot get preview data - missing warehouse ID or dialog not visible')
      }
    }
  },
  methods: {
    getPreviewData() {
      console.log('Getting preview data for warehouse:', this.warehouseData)
      console.log('Warehouse ID:', this.warehouseData.id)
      
      // 获取货架列表
      getShelvesByWarehouse(this.warehouseData.id).then(response => {
        console.log('Shelf response:', response)
        this.shelfList = response.data || []
        console.log('Shelf list loaded:', this.shelfList)
        
        if (this.shelfList.length === 0) {
          console.warn('No shelves found for warehouse')
          this.locationList = []
          return
        }
        
        // 获取所有货位信息
        const shelfIds = this.shelfList.map(shelf => shelf.id).filter(id => id)
        console.log('Shelf IDs to fetch locations for:', shelfIds)
        
        if (shelfIds.length === 0) {
          console.warn('No valid shelf IDs found')
          this.locationList = []
          return
        }
        
        Promise.all(shelfIds.map(shelfId => getLocationsByShelf(shelfId)))
          .then(responses => {
            console.log('Location responses:', responses)
            this.locationList = responses.flatMap(response => {
              const locations = response.rows || response.data || []
              console.log('Locations for shelf:', locations)
              return locations
            })
            console.log('Final location list loaded:', this.locationList)
          })
          .catch(error => {
            console.error('加载货位信息失败:', error)
            this.locationList = []
          })
      }).catch(error => {
        console.error('加载货架信息失败:', error)
        this.shelfList = []
        this.locationList = []
      })
    },
    getLocationsByShelf(shelfId) {
      console.log(`Filtering locations for shelf ${shelfId} (type: ${typeof shelfId})`)
      console.log('All locations:', this.locationList)
      
      const locations = this.locationList.filter(location => {
        console.log(`Checking location ${location.locationCode}: shelfId=${location.shelfId} (type: ${typeof location.shelfId}), shelfId === ${shelfId}: ${location.shelfId === shelfId}`)
        return location.shelfId === shelfId
      })
      
      console.log(`Found ${locations.length} locations for shelf ${shelfId}:`, locations)
      return locations
    },
    getLocationStatusClass(location) {
      if (location.isActive === '0') {
        return 'status-disabled' // 禁用
      }
      switch (location.isOccupied) {
        case '0': return 'status-free' // 空闲
        case '1': return 'status-occupied' // 占用
        default: return 'status-free'
      }
    },
    getLocationTooltip(location) {
      return `${location.locationCode} - ${location.locationName}\n类型: ${this.getDictLabel(this.dict.type.location_type, location.locationType)}\n状态: ${location.isOccupied === '1' ? '占用' : '空闲'}`
    },
    getDictLabel(dictOptions, value) {
      const item = dictOptions.find(d => d.value === value)
      return item ? item.label : value
    },
    getFreeLocationsCount() {
      return this.locationList.filter(loc => loc.isOccupied === '0' && loc.isActive === '1').length
    },
    getOccupiedLocationsCount() {
      return this.locationList.filter(loc => loc.isOccupied === '1' && loc.isActive === '1').length
    },
    handleShelfPreview(shelf) {
      this.currentShelf = shelf
      this.shelfPreviewVisible = true
    },
    handleLocationPreview(location) {
      this.currentLocation = location
      this.locationPreviewVisible = true
    },
    handleShelfLocations(shelf) {
      this.currentShelf = shelf
      this.shelfLocationsVisible = true
    },
    toggleViewMode() {
      this.viewMode = this.viewMode === 'grid' ? 'list' : 'grid'
    },
    refreshLayout() {
      this.getPreviewData()
    }
  }
}
</script>

<style scoped>
.warehouse-preview {
  padding: 20px;
}

.warehouse-info {
  background: #f5f7fa;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.warehouse-details {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 20px;
}

.warehouse-text {
  flex: 1;
}

.warehouse-barcode {
  flex-shrink: 0;
  text-align: center;
}

.warehouse-info h3 {
  margin: 0 0 10px 0;
  color: #303133;
}

.warehouse-info p {
  margin: 5px 0;
  color: #606266;
}

/* 统计信息卡片 */
.warehouse-stats {
  margin-bottom: 20px;
}

.stat-card {
  background: #fff;
  border: 1px solid #ebeef5;
  border-radius: 8px;
  padding: 20px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.stat-icon {
  font-size: 24px;
  margin-bottom: 10px;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 5px;
}

.stat-label {
  color: #909399;
  font-size: 14px;
}

/* 布局控制 */
.layout-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.layout-header h3 {
  margin: 0;
  color: #303133;
}

.layout-controls {
  display: flex;
  gap: 10px;
}

/* 网格视图 */
.shelf-grid-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.shelf-container {
  border: 2px solid #dcdfe6;
  border-radius: 8px;
  padding: 15px;
  min-width: 300px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
}

.shelf-container:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.shelf-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.shelf-header h4 {
  margin: 0;
  color: #303133;
}

.shelf-code {
  background: #409eff;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.shelf-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  font-size: 12px;
}

.shelf-type {
  color: #606266;
}

.shelf-status {
  padding: 2px 6px;
  border-radius: 3px;
  font-size: 11px;
}

.shelf-status.active {
  background: #f0f9ff;
  color: #67c23a;
}

.shelf-status.inactive {
  background: #fef0f0;
  color: #f56c6c;
}

.shelf-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
  gap: 8px;
}

.location-cell {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 8px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
  min-height: 60px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
}

.location-cell:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.location-code {
  font-size: 10px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 4px;
}

.location-info {
  font-size: 9px;
  color: #909399;
}

.location-status-indicator {
  position: absolute;
  top: 2px;
  right: 2px;
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

/* 货位状态样式 */
.status-free {
  background: #f0f9ff;
  border-color: #67c23a;
}

.status-occupied {
  background: #fef0f0;
  border-color: #f56c6c;
}

.status-disabled {
  background: #f5f5f5;
  border-color: #c0c4cc;
  opacity: 0.6;
}

.status-free .location-status-indicator {
  background: #67c23a;
}

.status-occupied .location-status-indicator {
  background: #f56c6c;
}

.status-disabled .location-status-indicator {
  background: #c0c4cc;
}

/* 列表视图 */
.shelf-list-container {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.location-count {
  background: #409eff;
  color: white;
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
}

/* 货架货位详情 */
.shelf-locations-detail h4 {
  margin: 0 0 20px 0;
  color: #303133;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

/* 可点击元素样式 */
.clickable {
  cursor: pointer;
  transition: all 0.3s;
}

.clickable:hover {
  color: #409eff;
  text-decoration: underline;
}

.shelf-code.clickable:hover {
  background: #66b1ff;
  transform: scale(1.05);
}

.location-cell.clickable:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
