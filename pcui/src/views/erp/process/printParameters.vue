<template>
  <div class="print-parameters-page">
    <div class="page-header">
      <h2>工序参数明细打印</h2>
      <div class="header-actions">
        <el-button type="primary" icon="el-icon-printer" @click="printAll">打印全部</el-button>
        <el-button type="success" icon="el-icon-document" @click="printBarcodes">打印条码</el-button>
        <el-button icon="el-icon-back" @click="goBack">返回</el-button>
      </div>
    </div>

    <div class="print-content">
      <div class="print-header">
        <h3>工序参数明细表</h3>
        <p>打印时间：{{ printTime }}</p>
      </div>
      
      <div class="process-list">
        <div v-for="process in processData" :key="process.id" class="process-item">
          <div class="process-title">
            {{ process.processCode }} - {{ process.processName }}
          </div>
          <div class="parameter-list">
            <div 
              v-for="param in process.parameters" 
              :key="param.id" 
              class="parameter-item"
            >
              <span class="parameter-name">{{ param.parameterCode }} - {{ param.parameterName }}</span>
              <span class="parameter-barcode">{{ process.processCode }}-{{ param.parameterCode }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 条码打印预览弹窗 -->
    <el-dialog
      title="条码打印预览"
      :visible.sync="barcodeDialogVisible"
      width="90%"
      append-to-body
      :close-on-click-modal="false"
    >
      <div class="barcode-preview">
        <div class="barcode-header">
          <h4>工序参数条码</h4>
          <p>共 {{ totalBarcodes }} 个条码</p>
        </div>
        
        <div class="barcode-grid">
          <div 
            v-for="(barcode, index) in barcodeList" 
            :key="index" 
            class="barcode-item"
          >
            <div class="barcode-info">
              <div class="process-info">{{ barcode.processCode }} - {{ barcode.processName }}</div>
              <div class="parameter-info">{{ barcode.parameterCode }} - {{ barcode.parameterName }}</div>
            </div>
            <div class="barcode-display">
              <svg 
                :id="'barcode-' + index" 
                class="barcode-svg" 
                width="200" 
                height="60"
              ></svg>
              <div class="barcode-text">{{ barcode.barcodeValue }}</div>
            </div>
          </div>
        </div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="barcodeDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="printBarcodePage">开始打印条码</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getProcess } from "@/api/erp/process"

export default {
  name: "PrintParameters",
  data() {
    return {
      processData: [],
      printTime: '',
      barcodeDialogVisible: false,
      barcodeList: [],
      totalBarcodes: 0
    }
  },
  created() {
    this.printTime = new Date().toLocaleString()
    this.loadProcessData()
  },
  methods: {
    /** 加载工序数据 */
    loadProcessData() {
      const processIds = this.$route.query.ids
      if (!processIds) {
        this.$message.error('未选择工序')
        this.goBack()
        return
      }

      const ids = processIds.split(',').map(id => parseInt(id))
      const promises = ids.map(id => {
        return getProcess(id).then(response => {
          const processData = response.data
          const parameters = processData.erpProcessParaList || []
          
          return {
            id: processData.id,
            processCode: processData.processCode,
            processName: processData.processName,
            parameters: parameters.filter(param => param.parameterCode && param.parameterName)
          }
        })
      })

      Promise.all(promises).then(results => {
        this.processData = results.filter(result => result.parameters.length > 0)
        if (this.processData.length === 0) {
          this.$message.error('选中的工序都没有工艺参数明细数据')
          this.goBack()
        }
      }).catch(error => {
        this.$message.error('获取工序数据失败：' + error.message)
        this.goBack()
      })
    },

    /** 打印全部内容 */
    printAll() {
      const printContent = this.generatePrintContent()
      this.openPrintWindow(printContent, '工序参数明细表')
    },

    /** 打印条码 */
    printBarcodes() {
      this.generateBarcodeList()
      this.barcodeDialogVisible = true
      
      // 等待DOM更新后生成条码
      this.$nextTick(() => {
        this.generateBarcodes()
      })
    },

    /** 生成条码列表 */
    generateBarcodeList() {
      this.barcodeList = []
      this.processData.forEach(process => {
        process.parameters.forEach(param => {
          this.barcodeList.push({
            processCode: process.processCode,
            processName: process.processName,
            parameterCode: param.parameterCode,
            parameterName: param.parameterName,
            barcodeValue: process.processCode + '-' + param.parameterCode
          })
        })
      })
      this.totalBarcodes = this.barcodeList.length
    },

    /** 生成条码 */
    generateBarcodes() {
      // 动态加载jsbarcode库
      if (typeof JsBarcode === 'undefined') {
        const script = document.createElement('script')
        script.src = 'https://cdn.jsdelivr.net/npm/jsbarcode@3.11.5/dist/JsBarcode.all.min.js'
        script.onload = () => {
          this.createBarcodes()
        }
        document.head.appendChild(script)
      } else {
        this.createBarcodes()
      }
    },

    /** 创建条码 */
    createBarcodes() {
      this.barcodeList.forEach((barcode, index) => {
        try {
          JsBarcode(`#barcode-${index}`, barcode.barcodeValue, {
            format: "CODE128",
            width: 2,
            height: 50,
            displayValue: false,
            background: "#ffffff",
            lineColor: "#000000"
          })
        } catch (e) {
          console.error('生成条码失败:', e)
        }
      })
    },

    /** 打印条码页面 */
    printBarcodePage() {
      const printContent = this.generateBarcodePrintContent()
      this.openPrintWindow(printContent, '工序参数条码')
    },

    /** 生成打印内容 */
    generatePrintContent() {
      let content = `
        <div class="print-container">
          <div class="print-header">
            <h3>工序参数明细表</h3>
            <p>打印时间：${this.printTime}</p>
          </div>
      `
      
      this.processData.forEach(process => {
        content += `
          <div class="process-item">
            <div class="process-title">
              ${process.processCode} - ${process.processName}
            </div>
            <div class="parameter-list">
        `
        
        process.parameters.forEach(param => {
          content += `
            <div class="parameter-item">
              <span class="parameter-name">${param.parameterCode} - ${param.parameterName}</span>
              <span class="parameter-barcode">${process.processCode}-${param.parameterCode}</span>
            </div>
          `
        })
        
        content += `
            </div>
          </div>
        `
      })
      
      content += `
        </div>
      `
      
      return content
    },

    /** 生成条码打印内容 */
    generateBarcodePrintContent() {
      let content = `
        <div class="print-container">
          <div class="print-header">
            <h3>工序参数条码</h3>
            <p>打印时间：${this.printTime}</p>
          </div>
          <div class="barcode-grid">
      `
      
      this.barcodeList.forEach((barcode, index) => {
        content += `
          <div class="barcode-item">
            <div class="barcode-info">
              <div class="process-info">${barcode.processCode} - ${barcode.processName}</div>
              <div class="parameter-info">${barcode.parameterCode} - ${barcode.parameterName}</div>
            </div>
            <div class="barcode-display">
              <svg id="print-barcode-${index}" class="barcode-svg" width="200" height="60"></svg>
              <div class="barcode-text">${barcode.barcodeValue}</div>
            </div>
          </div>
        `
      })
      
      content += `
          </div>
        </div>
      `
      
      return content
    },

    /** 打开打印窗口 */
    openPrintWindow(content, title) {
      const printWindow = window.open('', '_blank')
      const printDocument = printWindow.document
      
      printDocument.write(`
        <!DOCTYPE html>
        <html>
          <head>
            <title>${title}</title>
            <style>
              body {
                margin: 0;
                padding: 20px;
                font-family: Arial, sans-serif;
                font-size: 14px;
                background: #f5f7fa;
              }
              .print-container {
                max-width: 800px;
                margin: 0 auto;
                background: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
              }
              .print-header {
                text-align: center;
                margin-bottom: 30px;
                border-bottom: 2px solid #409EFF;
                padding-bottom: 20px;
              }
              .print-header h3 {
                margin: 0 0 10px 0;
                color: #303133;
                font-size: 24px;
              }
              .print-header p {
                margin: 0;
                color: #606266;
                font-size: 14px;
              }
              .process-item {
                margin-bottom: 25px;
                border: 1px solid #dcdfe6;
                border-radius: 6px;
                overflow: hidden;
              }
              .process-title {
                background: #f5f7fa;
                padding: 15px 20px;
                font-weight: bold;
                font-size: 16px;
                color: #303133;
                border-bottom: 1px solid #dcdfe6;
              }
              .parameter-list {
                padding: 0;
              }
              .parameter-item {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 12px 20px;
                border-bottom: 1px solid #f0f0f0;
              }
              .parameter-item:last-child {
                border-bottom: none;
              }
              .parameter-name {
                flex: 1;
                color: #303133;
                font-size: 14px;
              }
              .parameter-barcode {
                color: #409EFF;
                font-weight: bold;
                font-family: 'Courier New', monospace;
                font-size: 14px;
                background: #f0f9ff;
                padding: 4px 8px;
                border-radius: 4px;
                border: 1px solid #b3d8ff;
              }
              .barcode-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                gap: 20px;
                margin-top: 20px;
              }
              .barcode-item {
                border: 1px solid #dcdfe6;
                border-radius: 6px;
                padding: 15px;
                text-align: center;
                background: white;
              }
              .barcode-info {
                margin-bottom: 10px;
              }
              .process-info {
                font-weight: bold;
                color: #303133;
                font-size: 14px;
                margin-bottom: 5px;
              }
              .parameter-info {
                color: #606266;
                font-size: 12px;
              }
              .barcode-display {
                margin: 10px 0;
              }
              .barcode-svg {
                border: 1px solid #dcdfe6;
                border-radius: 4px;
                padding: 5px;
                background: white;
              }
              .barcode-text {
                font-size: 12px;
                color: #606266;
                font-family: 'Courier New', monospace;
                margin-top: 5px;
              }
              @media print {
                body { margin: 0; background: white; }
                .print-container { box-shadow: none; }
              }
            </style>
          </head>
          <body>
            ${content}
          </body>
        </html>
      `)
      
      printDocument.close()
      
      // 等待内容加载完成后打印
      printWindow.onload = () => {
        if (title === '工序参数条码') {
          // 为条码打印生成条码
          this.generatePrintBarcodes(printWindow)
        } else {
          printWindow.print()
          printWindow.close()
          this.$message.success('打印完成')
        }
      }
    },

    /** 为打印页面生成条码 */
    generatePrintBarcodes(printWindow) {
      if (typeof printWindow.JsBarcode !== 'undefined') {
        this.barcodeList.forEach((barcode, index) => {
          try {
            printWindow.JsBarcode(`#print-barcode-${index}`, barcode.barcodeValue, {
              format: "CODE128",
              width: 2,
              height: 50,
              displayValue: false,
              background: "#ffffff",
              lineColor: "#000000"
            })
          } catch (e) {
            console.error('生成条码失败:', e)
          }
        })
      }
      
      setTimeout(() => {
        printWindow.print()
        printWindow.close()
        this.$message.success('条码打印完成')
      }, 1000)
    },

    /** 返回上一页 */
    goBack() {
      this.$router.go(-1)
    }
  }
}
</script>

<style scoped>
.print-parameters-page {
  padding: 20px;
  background: #f5f7fa;
  min-height: 100vh;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.page-header h2 {
  margin: 0;
  color: #303133;
  font-size: 24px;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.print-content {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  overflow: hidden;
}

.print-header {
  text-align: center;
  margin-bottom: 30px;
  padding: 30px;
  background: #f5f7fa;
  border-bottom: 2px solid #409EFF;
}

.print-header h3 {
  margin: 0 0 10px 0;
  color: #303133;
  font-size: 24px;
}

.print-header p {
  margin: 0;
  color: #606266;
  font-size: 14px;
}

.process-list {
  padding: 0;
}

.process-item {
  margin-bottom: 20px;
  border-bottom: 1px solid #f0f0f0;
}

.process-item:last-child {
  border-bottom: none;
}

.process-title {
  background: #f5f7fa;
  padding: 15px 20px;
  font-weight: bold;
  font-size: 16px;
  color: #303133;
  border-bottom: 1px solid #dcdfe6;
}

.parameter-list {
  padding: 0;
}

.parameter-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 20px;
  border-bottom: 1px solid #f0f0f0;
}

.parameter-item:last-child {
  border-bottom: none;
}

.parameter-name {
  flex: 1;
  color: #303133;
  font-size: 14px;
}

.parameter-barcode {
  color: #409EFF;
  font-weight: bold;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  background: #f0f9ff;
  padding: 4px 8px;
  border-radius: 4px;
  border: 1px solid #b3d8ff;
}

/* 条码预览样式 */
.barcode-preview {
  padding: 20px;
}

.barcode-header {
  text-align: center;
  margin-bottom: 20px;
  padding: 15px;
  background: #f5f7fa;
  border-radius: 6px;
}

.barcode-header h4 {
  margin: 0 0 10px 0;
  color: #303133;
  font-size: 18px;
}

.barcode-header p {
  margin: 0;
  color: #606266;
  font-size: 14px;
}

.barcode-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.barcode-item {
  border: 1px solid #dcdfe6;
  border-radius: 6px;
  padding: 15px;
  text-align: center;
  background: white;
}

.barcode-info {
  margin-bottom: 10px;
}

.process-info {
  font-weight: bold;
  color: #303133;
  font-size: 14px;
  margin-bottom: 5px;
}

.parameter-info {
  color: #606266;
  font-size: 12px;
}

.barcode-display {
  margin: 10px 0;
}

.barcode-svg {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 5px;
  background: white;
}

.barcode-text {
  font-size: 12px;
  color: #606266;
  font-family: 'Courier New', monospace;
  margin-top: 5px;
}
</style>
