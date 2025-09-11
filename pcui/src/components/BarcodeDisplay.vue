<template>
  <div class="barcode-display">
    <div class="barcode-container">
      <svg ref="barcode" class="barcode-svg"></svg>
    </div>
    <div class="barcode-text">{{ value }}</div>
  </div>
</template>

<script>
import JsBarcode from 'jsbarcode';

export default {
  name: "BarcodeDisplay",
  props: {
    value: {
      type: String,
      default: ""
    },
    format: {
      type: String,
      default: "CODE128"
    },
    width: {
      type: Number,
      default: 2
    },
    height: {
      type: Number,
      default: 50
    },
    fontSize: {
      type: Number,
      default: 12
    },
    margin: {
      type: Number,
      default: 10
    }
  },
  watch: {
    value: {
      handler(newVal) {
        if (newVal) {
          this.$nextTick(() => {
            this.generateBarcode();
          });
        }
      },
      immediate: true
    }
  },
  mounted() {
    if (this.value) {
      this.$nextTick(() => {
        this.generateBarcode();
      });
    }
  },
  updated() {
    if (this.value) {
      this.$nextTick(() => {
        this.generateBarcode();
      });
    }
  },
  methods: {
    generateBarcode() {
      if (!this.value || !this.$refs.barcode) {
        return;
      }
      
      try {
        JsBarcode(this.$refs.barcode, this.value, {
          format: this.format,
          width: this.width,
          height: this.height,
          fontSize: this.fontSize,
          margin: this.margin,
          displayValue: false, // 不在条码下方显示文本，我们自定义显示
          background: "#ffffff",
          lineColor: "#000000"
        });
      } catch (error) {
        console.error("生成条码失败:", error);
      }
    }
  }
};
</script>

<style scoped>
.barcode-display {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.barcode-container {
  background: white;
  padding: 12px 16px;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  transition: all 0.3s ease;
}

.barcode-container:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  border-color: #409EFF;
}

.barcode-svg {
  display: block;
  filter: drop-shadow(0 1px 2px rgba(0,0,0,0.1));
}

.barcode-text {
  font-family: 'Courier New', monospace;
  font-size: 11px;
  color: #606266;
  text-align: center;
  background: linear-gradient(135deg, #f8f9fa, #e9ecef);
  padding: 6px 10px;
  border-radius: 6px;
  border: 1px solid #e4e7ed;
  font-weight: 600;
  letter-spacing: 0.5px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.05);
}
</style>
