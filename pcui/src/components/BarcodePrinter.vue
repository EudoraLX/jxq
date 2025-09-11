<template>
  <div class="barcode-printer">
    <el-button
      size="mini"
      type="text"
      icon="el-icon-printer"
      @click="printBarcode"
      :loading="printing"
      style="margin-left: 0;"
    >
      打印条码
    </el-button>

    <!-- 打印预览对话框 -->
    <el-dialog
      title="条码标签打印预览"
      :visible.sync="previewVisible"
      width="600px"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="print-preview">
        <div class="label-container" ref="printArea">
          <!-- 标签内容 -->
          <div class="label-header">
            <h3>{{ labelTitle }}</h3>
          </div>
          
          <div class="label-content">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">{{ infoLabels.code }}</div>
                <div class="info-value">{{ machineData.machineCode }}</div>
              </div>
              
              <div class="info-item">
                <div class="info-label">{{ infoLabels.name }}</div>
                <div class="info-value">{{ machineData.machineName }}</div>
              </div>
              
              <div class="info-item">
                <div class="info-label">{{ infoLabels.type }}</div>
                <div class="info-value">{{ getMachineTypeLabel(machineData.machineType) }}</div>
              </div>
              
              <div class="info-item">
                <div class="info-label">{{ infoLabels.workCenter }}</div>
                <div class="info-value">{{ machineData.workCenterName || '-' }}</div>
              </div>
            </div>
            
            <!-- 条码区域 -->
            <div class="barcode-section">
              <div class="barcode-container">
                <barcode-display 
                  :value="machineData.machineCode" 
                  :width="2" 
                  :height="60"
                  format="CODE128"
                />
              </div>
            </div>
            
            <div class="label-footer">
              <span class="print-time">打印时间: {{ printTime }}</span>
            </div>
          </div>
        </div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="previewVisible = false">取 消</el-button>
        <el-button type="primary" @click="doPrint" :loading="printing">打 印</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import BarcodeDisplay from './BarcodeDisplay';

export default {
  name: "BarcodePrinter",
  components: {
    BarcodeDisplay
  },
  props: {
    machineData: {
      type: Object,
      required: true
    },
    machineTypeDict: {
      type: Array,
      default: () => []
    },
    labelTitle: {
      type: String,
      default: '设备标签'
    },
    infoLabels: {
      type: Object,
      default: () => ({
        code: '设备编码',
        name: '设备名称',
        type: '设备类型',
        workCenter: '工作中心'
      })
    }
  },
  data() {
    return {
      previewVisible: false,
      printing: false,
      printTime: ''
    };
  },
  methods: {
    printBarcode() {
      this.printTime = new Date().toLocaleString();
      this.previewVisible = true;
    },

    getMachineTypeLabel(type) {
      if (!type || !this.machineTypeDict.length) return type;
      const dict = this.machineTypeDict.find(item => item.value === type);
      return dict ? dict.label : type;
    },

    async doPrint() {
      this.printing = true;

      try {
        // 等待DOM更新完成
        await this.$nextTick();

        // 获取打印区域
        const printElement = this.$refs.printArea;
        if (!printElement) {
          this.$message.error('打印区域未找到');
          return;
        }

        // 创建打印窗口
        const printWindow = window.open('', '_blank');
        const printDocument = printWindow.document;

        // 设置打印样式
        printDocument.write(`
          <!DOCTYPE html>
          <html>
            <head>
              <title>${this.labelTitle}打印</title>
              <style>
                body {
                  margin: 0;
                  padding: 20px;
                  font-family: Arial, sans-serif;
                  font-size: 12px;
                  background: #f5f7fa;
                }
                .label-container {
                  width: 450px;
                  background: white;
                  border-radius: 8px;
                  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                  padding: 20px;
                  text-align: center;
                  margin: 0 auto;
                  border: 1px solid #dcdfe6;
                }
                .label-header {
                  margin-bottom: 20px;
                }
                .label-header h3 {
                  margin: 0;
                  font-size: 18px;
                  color: #303133;
                  font-weight: 600;
                }
                .info-grid {
                  display: grid;
                  grid-template-columns: 1fr 1fr;
                  gap: 12px;
                  margin-bottom: 20px;
                }
                .info-item {
                  text-align: left;
                  padding: 8px 12px;
                  background: #f5f7fa;
                  border-radius: 4px;
                  border-left: 3px solid #409EFF;
                }
                .info-label {
                  font-size: 11px;
                  color: #909399;
                  margin-bottom: 4px;
                  font-weight: 500;
                }
                .info-value {
                  font-size: 12px;
                  color: #303133;
                  font-weight: 600;
                }
                .barcode-section {
                  margin: 20px 0;
                  text-align: center;
                }
                .barcode-container {
                  background: white;
                  padding: 15px;
                  border-radius: 6px;
                  border: 1px solid #e4e7ed;
                  display: inline-block;
                }
                .label-footer {
                  margin-top: 20px;
                }
                .print-time {
                  font-size: 10px;
                  color: #909399;
                }
                @media print {
                  body {
                    margin: 0;
                    background: white;
                  }
                  .label-container {
                    box-shadow: none;
                    border: 1px solid #dcdfe6;
                    page-break-inside: avoid;
                  }
                }
              </style>
            </head>
            <body>
              ${printElement.outerHTML}
            </body>
          </html>
        `);

        printDocument.close();

        // 等待内容加载完成后打印
        printWindow.onload = () => {
          printWindow.print();
          printWindow.close();
          this.$message.success('打印成功');
          this.previewVisible = false;
        };

      } catch (error) {
        console.error('打印失败:', error);
        this.$message.error('打印失败: ' + error.message);
      } finally {
        this.printing = false;
      }
    }
  }
};
</script>

<style scoped>
.barcode-printer {
  display: inline-block;
  margin-left: 8px;
}

.print-preview {
  text-align: center;
  background: #f5f7fa;
  padding: 20px;
}

.label-container {
  width: 450px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  padding: 20px;
  margin: 0 auto;
  border: 1px solid #dcdfe6;
}

.label-header {
  margin-bottom: 20px;
  text-align: center;
}

.label-header h3 {
  margin: 0;
  font-size: 18px;
  color: #303133;
  font-weight: 600;
}

.label-content {
  text-align: left;
}

.info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
  margin-bottom: 20px;
}

.info-item {
  padding: 8px 12px;
  background: #f5f7fa;
  border-radius: 4px;
  border-left: 3px solid #409EFF;
}

.info-label {
  font-size: 11px;
  color: #909399;
  margin-bottom: 4px;
  font-weight: 500;
}

.info-value {
  font-size: 12px;
  color: #303133;
  font-weight: 600;
}

.barcode-section {
  margin: 20px 0;
  text-align: center;
}

.barcode-container {
  background: white;
  padding: 15px;
  border-radius: 6px;
  border: 1px solid #e4e7ed;
  display: inline-block;
}

.label-footer {
  margin-top: 20px;
  text-align: center;
}

.print-time {
  font-size: 10px;
  color: #909399;
}

.dialog-footer {
  text-align: center;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .label-container {
    width: 380px;
    padding: 16px;
  }
  
  .info-grid {
    grid-template-columns: 1fr;
    gap: 8px;
  }
}
</style>
