<template>
  <el-dialog :title="`${locationData.locationName} - 货位预览`" :visible.sync="visible" width="600px" append-to-body>
    <div class="location-preview">
      <!-- 货位基本信息 -->
      <div class="location-info">
        <div class="location-details">
          <div class="location-text">
            <h3>{{ locationData.locationName }}</h3>
            <p>编码：{{ locationData.locationCode }}</p>
            <p>类型：{{ getDictLabel(dict.type.location_type, locationData.locationType) }}</p>
            <p>层级：{{ locationData.levelNum }}</p>
            <p>位置：{{ locationData.positionNum }}</p>
            <p>容量：{{ locationData.capacity }}</p>
            <p>承重：{{ locationData.maxWeight }}kg</p>
            <p>占用状态：{{ getDictLabel(dict.type.location_is_occupied, locationData.isOccupied) }}</p>
          </div>
          <div class="location-barcode">
            <barcode-display 
              v-if="locationData.locationCode" 
              :value="locationData.locationCode" 
              :width="2" 
              :height="80"
              format="CODE128"
            />
          </div>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import BarcodeDisplay from "@/components/BarcodeDisplay"

export default {
  name: "LocationPreviewDialog",
  components: {
    BarcodeDisplay
  },
  dicts: ['location_type', 'location_is_occupied'],
  props: {
    value: {
      type: Boolean,
      default: false
    },
    locationData: {
      type: Object,
      default: () => ({})
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
  methods: {
    getDictLabel(dictOptions, value) {
      const item = dictOptions.find(d => d.value === value)
      return item ? item.label : value
    }
  }
}
</script>

<style scoped>
.location-preview {
  padding: 20px;
}

.location-info {
  background: #f5f7fa;
  padding: 15px;
  border-radius: 8px;
}

.location-details {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 20px;
}

.location-text {
  flex: 1;
}

.location-barcode {
  flex-shrink: 0;
  text-align: center;
}

.location-info h3 {
  margin: 0 0 10px 0;
  color: #303133;
}

.location-info p {
  margin: 5px 0;
  color: #606266;
}
</style>
