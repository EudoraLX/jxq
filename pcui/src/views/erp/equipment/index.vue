<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="equipmentModel">
        <el-input
          v-model="queryParams.equipmentModel"
          placeholder="请输入设备型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="equipmentType">
        <el-select v-model="queryParams.equipmentType" placeholder="请选择设备类型" clearable>
          <el-option
            v-for="dict in dict.type.equipment_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <div style="display: flex; align-items: center;">
          <div style="flex: 1; position: relative;" @click="showQuerySupplierSelect">
            <el-input
              v-model="querySupplierDisplay"
              placeholder="请选择供应商"
              readonly
              style="background-color: #f5f7fa; cursor: pointer;"
              @keyup.enter.native="handleQuery"
            />
          </div>
          <el-button
            type="primary"
            icon="el-icon-arrow-down"
            size="mini"
            @click="showQuerySupplierSelect"
            style="margin-left: 10px;"
          >
            选择
          </el-button>
          <el-button
            v-if="queryParams.supplierId"
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="clearQuerySupplier"
            style="margin-left: 5px;"
          >
            清除
          </el-button>
        </div>
      </el-form-item>
      <el-form-item label="工作中心" prop="workCenterId">
        <div style="display: flex; align-items: center;">
          <div style="flex: 1; position: relative;" @click="showQueryWorkCenterSelect">
            <el-input
              v-model="queryWorkCenterDisplay"
              placeholder="请选择工作中心"
              readonly
              style="background-color: #f5f7fa; cursor: pointer;"
              @keyup.enter.native="handleQuery"
            />
          </div>
          <el-button
            type="primary"
            icon="el-icon-arrow-down"
            size="mini"
            @click="showQueryWorkCenterSelect"
            style="margin-left: 10px;"
          >
            选择
          </el-button>
          <el-button
            v-if="queryParams.workCenterId"
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="clearQueryWorkCenter"
            style="margin-left: 5px;"
          >
            清除
          </el-button>
        </div>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.equipment_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          v-hasPermi="['erp:equipment:add']"
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
          v-hasPermi="['erp:equipment:edit']"
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
          v-hasPermi="['erp:equipment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:equipment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="设备ID" align="center" prop="id" /> -->
      <el-table-column label="设备编码" align="center" prop="equipmentCode" />
      <el-table-column label="设备名称" align="center" prop="equipmentName" />
      <el-table-column label="设备型号" align="center" prop="equipmentModel" />
      <el-table-column label="设备类型" align="center" prop="equipmentType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_type" :value="scope.row.equipmentType"/>
        </template>
      </el-table-column>
      <el-table-column label="制造商" align="center" prop="manufacturer" />
      <el-table-column label="供应商" align="center" prop="supplierName" />
      <el-table-column label="工作中心" align="center" prop="workCenterName" />
      <el-table-column label="购买日期" align="center" prop="purchaseDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="保修到期日期" align="center" prop="warrantyExpiryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warrantyExpiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="折旧方法" align="center" prop="depreciationMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.depreciation_method" :value="scope.row.depreciationMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="使用年限" align="center" prop="usefulLife" />
      <el-table-column label="原值" align="center" prop="originalValue" />
      <el-table-column label="现值" align="center" prop="currentValue" />
      <el-table-column label="产能" align="center" prop="capacity" />
      <el-table-column label="产能单位" align="center" prop="capacityUnit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.capacity_unit" :value="scope.row.capacityUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="条码" align="center" prop="equipmentCode" width="120">
        <template slot-scope="scope">
          <barcode-display 
            v-if="scope.row.equipmentCode" 
            :value="scope.row.equipmentCode" 
            :width="1.5" 
            :height="40"
            format="CODE128"
          />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:equipment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:equipment:remove']"
          >删除</el-button>
          <barcode-printer 
            :machine-data="{
              machineCode: scope.row.equipmentCode,
              machineName: scope.row.equipmentName,
              machineType: scope.row.equipmentType,
              workCenterName: scope.row.workCenterName || '未设置工作中心'
            }"
            :title="'设备标签'"
            :label-title="'设备标签'"
            :info-labels="{
              code: '设备编码',
              name: '设备名称',
              type: '设备类型',
              workCenter: '工作中心'
            }"
            :machine-type-dict="dict.type.equipment_type"
          />
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

    <!-- 添加或修改设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="设备编码" prop="equipmentCode">
                  <el-input v-model="form.equipmentCode" placeholder="请输入设备编码" prefix-icon="el-icon-document" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="设备名称" prop="equipmentName">
                  <el-input v-model="form.equipmentName" placeholder="请输入设备名称" prefix-icon="el-icon-edit" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="设备型号" prop="equipmentModel">
                  <el-input v-model="form.equipmentModel" placeholder="请输入设备型号" prefix-icon="el-icon-collection" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="设备类型" prop="equipmentType">
                  <el-select v-model="form.equipmentType" placeholder="请选择设备类型" style="width:100%">
                    <el-option v-for="dict in dict.type.equipment_type" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="制造商" prop="manufacturer">
                  <el-input v-model="form.manufacturer" placeholder="请输入制造商" prefix-icon="el-icon-office-building" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="供应商" prop="supplierId">
                  <div style="display: flex; align-items: center;">
                    <div style="flex: 1; position: relative;" @click="showSupplierSelect">
                      <el-input
                        v-model="supplierDisplay"
                        placeholder="请选择供应商"
                        readonly
                        style="background-color: #f5f7fa; cursor: pointer;"
                        prefix-icon="el-icon-user"
                      />
                    </div>
                    <el-button
                      type="primary"
                      icon="el-icon-arrow-down"
                      size="mini"
                      @click="showSupplierSelect"
                      style="margin-left: 10px;"
                    >
                      选择
                    </el-button>
                    <el-button
                      v-if="form.supplierId"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="clearSupplier"
                      style="margin-left: 5px;"
                    >
                      清除
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="工作中心" prop="workCenterId">
                  <div style="display: flex; align-items: center;">
                    <div style="flex: 1; position: relative;" @click="showWorkCenterSelect">
                      <el-input
                        v-model="workCenterDisplay"
                        placeholder="请选择工作中心"
                        readonly
                        style="background-color: #f5f7fa; cursor: pointer;"
                        prefix-icon="el-icon-location"
                      />
                    </div>
                    <el-button
                      type="primary"
                      icon="el-icon-arrow-down"
                      size="mini"
                      @click="showWorkCenterSelect"
                      style="margin-left: 10px;"
                    >
                      选择
                    </el-button>
                    <el-button
                      v-if="form.workCenterId"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="clearWorkCenter"
                      style="margin-left: 5px;"
                    >
                      清除
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态" prop="status">
                  <el-select v-model="form.status" placeholder="请选择状态" style="width:100%">
                    <el-option v-for="dict in dict.type.equipment_status" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="产能信息" name="capacity">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="产能" prop="capacity">
                  <el-input-number v-model="form.capacity" :precision="2" :step="1" placeholder="请输入产能" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="产能单位" prop="capacityUnit">
                  <el-select v-model="form.capacityUnit" placeholder="请选择产能单位" style="width:100%">
                    <el-option v-for="dict in dict.type.capacity_unit" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="财务信息" name="finance">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="购买日期" prop="purchaseDate">
                  <el-date-picker clearable v-model="form.purchaseDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择购买日期" style="width:100%" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="保修到期日期" prop="warrantyExpiryDate">
                  <el-date-picker clearable v-model="form.warrantyExpiryDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择保修到期日期" style="width:100%" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="折旧方法" prop="depreciationMethod">
                  <el-select v-model="form.depreciationMethod" placeholder="请选择折旧方法" style="width:100%">
                    <el-option v-for="dict in dict.type.depreciation_method" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="使用年限" prop="usefulLife">
                  <el-input-number v-model="form.usefulLife" :precision="0" :step="1" placeholder="请输入使用年限" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="原值" prop="originalValue">
                  <el-input-number v-model="form.originalValue" :precision="2" :step="1000" placeholder="请输入原值" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="现值" prop="currentValue">
                  <el-input-number v-model="form.currentValue" :precision="2" :step="1000" placeholder="请输入现值" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="其他" name="other">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 工作中心选择弹窗 -->
    <work-center-select ref="workCenterSelect" @select="handleWorkCenterSelect" />
    
    <!-- 供应商选择弹窗 -->
    <supplier-select ref="supplierSelect" @select="handleSupplierSelect" />

    <!-- 搜索用工作中心选择弹窗 -->
    <work-center-select ref="queryWorkCenterSelect" @select="handleQueryWorkCenterSelect" />
    
    <!-- 搜索用供应商选择弹窗 -->
    <supplier-select ref="querySupplierSelect" @select="handleQuerySupplierSelect" />
  </div>
</template>

<script>
import { listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment } from "@/api/erp/equipment"
import WorkCenterSelect from "@/components/WorkCenterSelect"
import SupplierSelect from "@/components/SupplierSelect"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import BarcodePrinter from "@/components/BarcodePrinter"

export default {
  name: "Equipment",
  components: {
    WorkCenterSelect,
    SupplierSelect,
    BarcodeDisplay,
    BarcodePrinter
  },
  dicts: ['equipment_status', 'depreciation_method', 'equipment_type', 'capacity_unit'],
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
      // 设备表格数据
      equipmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的Tab
      activeTab: "basic",
      // 工作中心显示值
      workCenterDisplay: "",
      // 供应商显示值
      supplierDisplay: "",
      // 搜索用工作中心显示值
      queryWorkCenterDisplay: "",
      // 搜索用供应商显示值
      querySupplierDisplay: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentCode: null,
        equipmentName: null,
        equipmentModel: null,
        equipmentType: null,
        manufacturer: null,
        supplierId: null,
        workCenterId: null,
        purchaseDate: null,
        warrantyExpiryDate: null,
        depreciationMethod: null,
        usefulLife: null,
        originalValue: null,
        currentValue: null,
        capacity: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentCode: [
          { required: true, message: "设备编码不能为空", trigger: "blur" }
        ],
        equipmentName: [
          { required: true, message: "设备名称不能为空", trigger: "blur" }
        ],
        delFlag: [
          { required: true, message: "删除标志不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询设备列表 */
    getList() {
      this.loading = true
      listEquipment(this.queryParams).then(response => {
        this.equipmentList = response.rows
        this.total = response.total
        this.loading = false
      })
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
        equipmentCode: null,
        equipmentName: null,
        equipmentModel: null,
        equipmentType: null,
        manufacturer: null,
        supplierId: null,
        workCenterId: null,
        purchaseDate: null,
        warrantyExpiryDate: null,
        depreciationMethod: null,
        usefulLife: null,
        originalValue: null,
        currentValue: null,
        capacity: null,
        capacityUnit: 'm',
        status: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      }
      this.activeTab = "basic"
      this.workCenterDisplay = ""
      this.supplierDisplay = ""
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryWorkCenterDisplay = ""
      this.querySupplierDisplay = ""
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
      this.title = "添加设备"
      this.activeTab = "basic"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getEquipment(id).then(response => {
        this.form = response.data
        this.setDisplayValues()
        this.open = true
        this.title = "修改设备"
        this.activeTab = "basic"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEquipment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addEquipment(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除设备编号为"' + ids + '"的数据项？').then(function() {
        return delEquipment(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/equipment/export', {
        ...this.queryParams
      }, `equipment_${new Date().getTime()}.xlsx`)
    },

    /** 显示工作中心选择弹窗 */
    showWorkCenterSelect() {
      this.$refs.workCenterSelect.show()
    },

    /** 显示供应商选择弹窗 */
    showSupplierSelect() {
      this.$refs.supplierSelect.show()
    },

    /** 清除工作中心选择 */
    clearWorkCenter() {
      this.form.workCenterId = null
      this.workCenterDisplay = ""
    },

    /** 清除供应商选择 */
    clearSupplier() {
      this.form.supplierId = null
      this.supplierDisplay = ""
    },

    /** 处理工作中心选择 */
    handleWorkCenterSelect(selectedWorkCenter) {
      this.form.workCenterId = selectedWorkCenter.id
      this.workCenterDisplay = selectedWorkCenter.workCenterCode + ' - ' + selectedWorkCenter.workCenterName
    },

    /** 处理供应商选择 */
    handleSupplierSelect(selectedSupplier) {
      this.form.supplierId = selectedSupplier.id
      this.supplierDisplay = selectedSupplier.supplierCode + ' - ' + selectedSupplier.supplierName
    },

    /** 设置显示值 */
    setDisplayValues() {
      // 设置工作中心显示值
      if (this.form.workCenterId) {
        // 从后端返回的数据中获取工作中心信息
        if (this.form.workCenterCode && this.form.workCenterName) {
          this.workCenterDisplay = this.form.workCenterCode + ' - ' + this.form.workCenterName
        } else {
          this.workCenterDisplay = this.form.workCenterId
        }
      }
      // 设置供应商显示值
      if (this.form.supplierId) {
        // 从后端返回的数据中获取供应商信息
        if (this.form.supplierCode && this.form.supplierName) {
          this.supplierDisplay = this.form.supplierCode + ' - ' + this.form.supplierName
        } else {
          this.supplierDisplay = this.form.supplierId
        }
      }
    },

    /** 显示搜索用工作中心选择弹窗 */
    showQueryWorkCenterSelect() {
      this.$refs.queryWorkCenterSelect.show()
    },

    /** 显示搜索用供应商选择弹窗 */
    showQuerySupplierSelect() {
      this.$refs.querySupplierSelect.show()
    },

    /** 清除搜索用工作中心选择 */
    clearQueryWorkCenter() {
      this.queryParams.workCenterId = null
      this.queryWorkCenterDisplay = ""
    },

    /** 清除搜索用供应商选择 */
    clearQuerySupplier() {
      this.queryParams.supplierId = null
      this.querySupplierDisplay = ""
    },

    /** 处理搜索用工作中心选择 */
    handleQueryWorkCenterSelect(selectedWorkCenter) {
      this.queryParams.workCenterId = selectedWorkCenter.id
      this.queryWorkCenterDisplay = selectedWorkCenter.workCenterCode + ' - ' + selectedWorkCenter.workCenterName
    },

    /** 处理搜索用供应商选择 */
    handleQuerySupplierSelect(selectedSupplier) {
      this.queryParams.supplierId = selectedSupplier.id
      this.querySupplierDisplay = selectedSupplier.supplierCode + ' - ' + selectedSupplier.supplierName
    }
  }
}
</script>
