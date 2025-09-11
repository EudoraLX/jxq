<template>
  <el-dialog title="打印条码" :visible.sync="visible" width="500px" append-to-body>
    <div class="print-preview">
      <div class="barcode-container">
        <h3>{{ name }}</h3>
        <div class="barcode-display">
          <barcode-display 
            :value="code" 
            :width="2" 
            :height="80"
            format="CODE128"
          />
        </div>
        <p class="barcode-code">{{ code }}</p>
      </div>
      
      <div class="print-options">
        <el-form :model="printOptions" label-width="100px">
          <el-form-item label="打印份数">
            <el-input-number v-model="printOptions.copies" :min="1" :max="10" />
          </el-form-item>
          <el-form-item label="纸张大小">
            <el-select v-model="printOptions.paperSize" placeholder="请选择纸张大小">
              <el-option label="A4" value="A4" />
              <el-option label="A5" value="A5" />
              <el-option label="标签纸" value="label" />
            </el-select>
          </el-form-item>
          <el-form-item label="打印方向">
            <el-radio-group v-model="printOptions.orientation">
              <el-radio label="portrait">纵向</el-radio>
              <el-radio label="landscape">横向</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
    </div>
    
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handlePrint">打 印</el-button>
    </div>
  </el-dialog>
</template>

<script>
import BarcodeDisplay from "./BarcodeDisplay.vue"

export default {
  name: "BarcodePrintDialog",
  components: {
    BarcodeDisplay
  },
  props: {
    value: {
      type: Boolean,
      default: false
    },
    code: {
      type: String,
      default: ""
    },
    name: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      printOptions: {
        copies: 1,
        paperSize: 'A4',
        orientation: 'portrait'
      }
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
    handlePrint() {
      // 这里可以实现实际的打印逻辑
      // 目前只是模拟打印
      this.$modal.msgSuccess(`正在打印 ${this.printOptions.copies} 份条码标签`)
      this.visible = false
    }
  }
}
</script>

<style scoped>
.print-preview {
  padding: 20px;
}

.barcode-container {
  text-align: center;
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background: #f9f9f9;
}

.barcode-container h3 {
  margin: 0 0 15px 0;
  color: #303133;
}

.barcode-display {
  margin: 15px 0;
}

.barcode-code {
  margin: 10px 0 0 0;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  font-weight: bold;
  color: #303133;
}

.print-options {
  background: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
}

.dialog-footer {
  text-align: right;
}
</style>
