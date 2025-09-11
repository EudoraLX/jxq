<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警类型" prop="alertType">
        <el-select v-model="queryParams.alertType" placeholder="请选择预警类型" clearable>
          <el-option
            v-for="dict in dict.type.inventory_alert_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警级别" prop="alertLevel">
        <el-select v-model="queryParams.alertLevel" placeholder="请选择预警级别" clearable>
          <el-option
            v-for="dict in dict.type.alert_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理状态" prop="isHandled">
        <el-select v-model="queryParams.isHandled" placeholder="请选择处理状态" clearable>
          <el-option label="未处理" value="0" />
          <el-option label="已处理" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择仓库" clearable>
          <el-option
            v-for="warehouse in warehouseList"
            :key="warehouse.id"
            :label="warehouse.warehouseName"
            :value="warehouse.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['inventory:alert:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['inventory:alert:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['inventory:alert:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:alert:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleGenerate"
          v-hasPermi="['inventory:alert:add']"
        >生成预警</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-check"
          size="mini"
          :disabled="multiple"
          @click="handleBatchHandle"
          v-hasPermi="['inventory:alert:handle']"
        >批量处理</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 预警统计卡片 -->
    <el-row :gutter="20" class="mb8">
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>未处理预警</span>
          </div>
          <div class="text item">
            <span class="alert-count">{{ unhandledCount }}</span>
            <span class="alert-unit">条</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>高优先级预警</span>
          </div>
          <div class="text item">
            <span class="alert-count high-priority">{{ highPriorityCount }}</span>
            <span class="alert-unit">条</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>库存不足预警</span>
          </div>
          <div class="text item">
            <span class="alert-count low-stock">{{ lowStockCount }}</span>
            <span class="alert-unit">条</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>即将过期预警</span>
          </div>
          <div class="text item">
            <span class="alert-count expiry">{{ expiryCount }}</span>
            <span class="alert-unit">条</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="alertList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="materialCode" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="预警类型" align="center" prop="alertType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inventory_alert_type" :value="scope.row.alertType"/>
        </template>
      </el-table-column>
      <el-table-column label="当前库存" align="center" prop="currentQuantity" />
      <el-table-column label="阈值数量" align="center" prop="thresholdQuantity" />
      <el-table-column label="预警级别" align="center" prop="alertLevel">
        <template slot-scope="scope">
          <el-tag :type="getAlertLevelType(scope.row.alertLevel)">
            <dict-tag :options="dict.type.alert_level" :value="scope.row.alertLevel"/>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="预警信息" align="center" prop="alertMessage" show-overflow-tooltip />
      <el-table-column label="处理状态" align="center" prop="isHandled">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isHandled === '1' ? 'success' : 'danger'">
            {{ scope.row.isHandled === '1' ? '已处理' : '未处理' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handlerName" />
      <el-table-column label="处理时间" align="center" prop="handleTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handleTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:alert:edit']"
            v-if="scope.row.isHandled === '0'"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:alert:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleHandle(scope.row)"
            v-hasPermi="['inventory:alert:handle']"
            v-if="scope.row.isHandled === '0'"
          >处理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['inventory:alert:query']"
          >详情</el-button>
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

    <!-- 添加或修改库存预警表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料编码" prop="materialCode">
              <el-input v-model="form.materialCode" placeholder="请输入物料编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料名称" prop="materialName">
              <el-input v-model="form.materialName" placeholder="请输入物料名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库" prop="warehouseId">
              <el-select v-model="form.warehouseId" placeholder="请选择仓库">
                <el-option
                  v-for="warehouse in warehouseList"
                  :key="warehouse.id"
                  :label="warehouse.warehouseName"
                  :value="warehouse.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预警类型" prop="alertType">
              <el-select v-model="form.alertType" placeholder="请选择预警类型">
                <el-option
                  v-for="dict in dict.type.inventory_alert_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="当前库存" prop="currentQuantity">
              <el-input-number v-model="form.currentQuantity" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阈值数量" prop="thresholdQuantity">
              <el-input-number v-model="form.thresholdQuantity" :precision="2" :step="1" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="预警级别" prop="alertLevel">
              <el-select v-model="form.alertLevel" placeholder="请选择预警级别">
                <el-option
                  v-for="dict in dict.type.alert_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="处理状态" prop="isHandled">
              <el-select v-model="form.isHandled" placeholder="请选择处理状态">
                <el-option label="未处理" value="0" />
                <el-option label="已处理" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="预警信息" prop="alertMessage">
          <el-input v-model="form.alertMessage" type="textarea" placeholder="请输入预警信息" />
        </el-form-item>
        <el-form-item label="处理备注" prop="handleRemark">
          <el-input v-model="form.handleRemark" type="textarea" placeholder="请输入处理备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 处理预警对话框 -->
    <el-dialog title="处理预警" :visible.sync="handleDialogVisible" width="600px" append-to-body>
      <el-form ref="handleForm" :model="handleForm" :rules="handleRules" label-width="100px">
        <el-form-item label="预警信息" prop="alertMessage">
          <el-input v-model="handleForm.alertMessage" type="textarea" readonly />
        </el-form-item>
        <el-form-item label="处理备注" prop="handleRemark">
          <el-input v-model="handleForm.handleRemark" type="textarea" placeholder="请输入处理备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitHandle">确 定</el-button>
        <el-button @click="handleDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAlert, getAlert, delAlert, addAlert, updateAlert, handleAlert, handleBatchAlert, generateAlerts, getUnhandledCount, getHighPriorityCount } from "@/api/inventory/alert"
import { listWarehouse } from "@/api/erp/warehouse"

export default {
  name: "Alert",
  dicts: ['inventory_alert_type', 'alert_level'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 库存预警表表格数据
      alertList: [],
      // 仓库列表
      warehouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 处理对话框显示状态
      handleDialogVisible: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
        alertType: null,
        alertLevel: null,
        isHandled: null,
        warehouseId: null
      },
      // 表单参数
      form: {},
      // 处理表单参数
      handleForm: {},
      // 预警统计
      unhandledCount: 0,
      highPriorityCount: 0,
      lowStockCount: 0,
      expiryCount: 0,
      // 表单校验
      rules: {
        materialCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "仓库不能为空", trigger: "change" }
        ],
        alertType: [
          { required: true, message: "预警类型不能为空", trigger: "change" }
        ],
        alertLevel: [
          { required: true, message: "预警级别不能为空", trigger: "change" }
        ]
      },
      // 处理表单校验
      handleRules: {
        handleRemark: [
          { required: true, message: "处理备注不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getWarehouseList()
    this.getStatistics()
  },
  methods: {
    /** 查询库存预警表列表 */
    getList() {
      this.loading = true
      listAlert(this.queryParams).then(response => {
        this.alertList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询仓库列表 */
    getWarehouseList() {
      listWarehouse({}).then(response => {
        this.warehouseList = response.rows
      })
    },
    /** 获取预警统计 */
    getStatistics() {
      getUnhandledCount().then(response => {
        this.unhandledCount = response.data
      })
      getHighPriorityCount().then(response => {
        this.highPriorityCount = response.data
      })
      // 计算其他统计
      this.calculateStatistics()
    },
    /** 计算预警统计 */
    calculateStatistics() {
      this.lowStockCount = this.alertList.filter(item => item.alertType === '01' && item.isHandled === '0').length
      this.expiryCount = this.alertList.filter(item => item.alertType === '03' && item.isHandled === '0').length
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        materialId: null,
        materialCode: null,
        materialName: null,
        warehouseId: null,
        warehouseCode: null,
        warehouseName: null,
        alertType: null,
        currentQuantity: null,
        thresholdQuantity: null,
        alertLevel: null,
        alertMessage: null,
        isHandled: "0",
        handlerId: null,
        handlerName: null,
        handleTime: null,
        handleRemark: null
      }
      this.resetForm("form")
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加库存预警表"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAlert(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改库存预警表"
      })
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.reset()
      const id = row.id
      getAlert(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "库存预警详情"
      })
    },
    /** 处理按钮操作 */
    handleHandle(row) {
      this.handleForm = {
        id: row.id,
        alertMessage: row.alertMessage,
        handleRemark: ""
      }
      this.handleDialogVisible = true
    },
    /** 批量处理按钮操作 */
    handleBatchHandle() {
      if (this.ids.length === 0) {
        this.$modal.msgError("请先选择要处理的预警")
        return
      }
      this.$modal.confirm('是否确认处理选中的预警？').then(function() {
        return handleBatchAlert(this.ids)
      }).then(() => {
        this.getList()
        this.getStatistics()
        this.$modal.msgSuccess("处理成功")
      }).catch(() => {})
    },
    /** 生成预警按钮操作 */
    handleGenerate() {
      this.$modal.confirm('是否确认生成库存预警？').then(function() {
        return generateAlerts()
      }).then(response => {
        this.getList()
        this.getStatistics()
        this.$modal.msgSuccess("生成成功，共生成 " + response.data + " 条预警信息")
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAlert(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.getStatistics()
            })
          } else {
            addAlert(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
              this.getStatistics()
            })
          }
        }
      })
    },
    /** 提交处理表单 */
    submitHandle() {
      this.$refs["handleForm"].validate(valid => {
        if (valid) {
          handleAlert(this.handleForm).then(response => {
            this.$modal.msgSuccess("处理成功")
            this.handleDialogVisible = false
            this.getList()
            this.getStatistics()
          })
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除库存预警表编号为"' + ids + '"的数据项？').then(function() {
        return delAlert(ids)
      }).then(() => {
        this.getList()
        this.getStatistics()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('inventory/alert/export', {
        ...this.queryParams
      }, `alert_${new Date().getTime()}.xlsx`)
    },
    /** 获取预警级别样式 */
    getAlertLevelType(level) {
      const levelMap = {
        '1': 'info',
        '2': 'warning',
        '3': 'danger'
      }
      return levelMap[level] || 'info'
    }
  }
}
</script>

<style scoped>
.box-card {
  margin-bottom: 20px;
}

.alert-count {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.alert-count.high-priority {
  color: #F56C6C;
}

.alert-count.low-stock {
  color: #E6A23C;
}

.alert-count.expiry {
  color: #909399;
}

.alert-unit {
  font-size: 14px;
  color: #909399;
  margin-left: 5px;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>
