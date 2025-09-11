<template>
  <el-dialog :title="`${shelfData.shelfName} - 货架预览`" :visible.sync="visible" width="800px" append-to-body>
    <div class="shelf-preview">
      <!-- 货架基本信息 -->
      <div class="shelf-info">
        <div class="shelf-details">
          <div class="shelf-text">
            <h3>{{ shelfData.shelfName }}</h3>
            <p>编码：{{ shelfData.shelfCode }}</p>
            <p>类型：{{ getDictLabel(dict.type.shelf_type, shelfData.shelfType) }}</p>
            <p>区域：{{ shelfData.areaCode }}</p>
            <p>尺寸：{{ shelfData.width }}×{{ shelfData.depth }}×{{ shelfData.height }}cm</p>
            <p>承重：{{ shelfData.maxWeight }}kg</p>
          </div>
          <div class="shelf-barcode">
            <barcode-display 
              v-if="shelfData.shelfCode" 
              :value="shelfData.shelfCode" 
              :width="2" 
              :height="80"
              format="CODE128"
            />
          </div>
        </div>
      </div>

      <!-- 货位信息 -->
      <div class="location-section">
        <h3>货位信息</h3>
        <el-table :data="locationList" style="width: 100%">
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
    </div>
  </el-dialog>
</template>

<script>
import { getLocationsByShelf } from "@/api/erp/location"
import BarcodeDisplay from "@/components/BarcodeDisplay"

export default {
  name: "ShelfPreviewDialog",
  components: {
    BarcodeDisplay
  },
  dicts: ['shelf_type', 'location_type', 'location_is_occupied', 'sys_yes_no'],
  props: {
    value: {
      type: Boolean,
      default: false
    },
    shelfData: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      locationList: []
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
    }
  },
  watch: {
    value(val) {
      if (val && this.shelfData.id) {
        this.loadLocationList()
      }
    }
  },
  methods: {
    loadLocationList() {
      getLocationsByShelf(this.shelfData.id).then(response => {
        this.locationList = response.data || []
      }).catch(error => {
        console.error('Error loading location list:', error)
        this.locationList = []
      })
    },
    getDictLabel(dictOptions, value) {
      const item = dictOptions.find(d => d.value === value)
      return item ? item.label : value
    }
  }
}
</script>

<style scoped>
.shelf-preview {
  padding: 20px;
}

.shelf-info {
  background: #f5f7fa;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.shelf-details {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 20px;
}

.shelf-text {
  flex: 1;
}

.shelf-barcode {
  flex-shrink: 0;
  text-align: center;
}

.shelf-info h3 {
  margin: 0 0 10px 0;
  color: #303133;
}

.shelf-info p {
  margin: 5px 0;
  color: #606266;
}

.location-section {
  margin-top: 20px;
}

.location-section h3 {
  margin-bottom: 15px;
  color: #303133;
  font-size: 16px;
  font-weight: 600;
}
</style>
