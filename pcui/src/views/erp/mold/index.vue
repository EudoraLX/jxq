<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模具编码" prop="moldCode">
        <el-input
          v-model="queryParams.moldCode"
          placeholder="请输入模具编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模具名称" prop="moldName">
        <el-input
          v-model="queryParams.moldName"
          placeholder="请输入模具名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模具类型" prop="moldType">
        <el-select v-model="queryParams.moldType" placeholder="请选择模具类型" clearable>
          <el-option
            v-for="dict in dict.type.mold_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="模具分类" prop="moldCategory">
        <el-select v-model="queryParams.moldCategory" placeholder="请选择模具分类" clearable>
          <el-option
            v-for="dict in dict.type.mold_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="模具状态" prop="moldStatus">
        <el-select v-model="queryParams.moldStatus" placeholder="请选择模具状态" clearable>
          <el-option
            v-for="dict in dict.type.equipment_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否启用" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择是否启用" clearable>
          <el-option
            v-for="dict in dict.type.business_active"
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
          v-hasPermi="['erp:mold:add']"
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
          v-hasPermi="['erp:mold:edit']"
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
          v-hasPermi="['erp:mold:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:mold:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="moldList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="模具编码" align="center" prop="moldCode" />
      <el-table-column label="模具名称" align="center" prop="moldName" />
      <el-table-column label="模具类型" align="center" prop="moldType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mold_type" :value="scope.row.moldType"/>
        </template>
      </el-table-column>
      <el-table-column label="模具分类" align="center" prop="moldCategory">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mold_category" :value="scope.row.moldCategory"/>
        </template>
      </el-table-column>
      <el-table-column label="模具重量(kg)" align="center" prop="moldWeight" />
      <el-table-column label="模具尺寸" align="center" prop="moldSize" />
      <el-table-column label="型腔数量" align="center" prop="cavityCount" />
      <el-table-column label="模具状态" align="center" prop="moldStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_status" :value="scope.row.moldStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
      <el-table-column label="条码" align="center" prop="moldCode" width="120">
        <template slot-scope="scope">
          <barcode-display 
            v-if="scope.row.moldCode" 
            :value="scope.row.moldCode" 
            :width="1.5" 
            :height="40"
            format="CODE128"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:mold:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:mold:remove']"
          >删除</el-button>
          <barcode-printer 
            :machine-data="{
              machineCode: scope.row.moldCode,
              machineName: scope.row.moldName,
              machineType: scope.row.moldType,
              workCenterName: scope.row.moldCategory || '未设置分类'
            }"
            :title="'模具标签'"
            :label-title="'模具标签'"
            :info-labels="{
              code: '模具编码',
              name: '模具名称',
              type: '模具类型',
              workCenter: '模具分类'
            }"
            :machine-type-dict="dict.type.mold_type"
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

    <!-- 添加或修改模具对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="模具编码" prop="moldCode">
                  <el-input v-model="form.moldCode" placeholder="请输入模具编码" prefix-icon="el-icon-document" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="模具名称" prop="moldName">
                  <el-input v-model="form.moldName" placeholder="请输入模具名称" prefix-icon="el-icon-edit" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="模具类型" prop="moldType">
                  <el-select v-model="form.moldType" placeholder="请选择模具类型" style="width:100%">
                                         <el-option v-for="dict in dict.type.mold_type" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="模具分类" prop="moldCategory">
                  <el-select v-model="form.moldCategory" placeholder="请选择模具分类" style="width:100%">
                                         <el-option v-for="dict in dict.type.mold_category" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="产品物料" prop="productMaterialId">
                  <div style="display: flex; align-items: center;">
                    <div style="flex: 1; position: relative;" @click="showMaterialSelect">
                      <el-input
                        v-model="materialDisplay"
                        placeholder="请选择产品物料"
                        readonly
                        style="background-color: #f5f7fa; cursor: pointer;"
                        prefix-icon="el-icon-goods"
                      />
                    </div>
                    <el-button
                      type="primary"
                      icon="el-icon-arrow-down"
                      size="mini"
                      @click="showMaterialSelect"
                      style="margin-left: 10px;"
                    >
                      选择
                    </el-button>
                    <el-button
                      v-if="form.productMaterialId"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="clearMaterial"
                      style="margin-left: 5px;"
                    >
                      清除
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="存储位置ID" prop="storageLocationId">
                  <el-input v-model="form.storageLocationId" placeholder="请输入存储位置ID" prefix-icon="el-icon-location" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="模具状态" prop="moldStatus">
                  <el-radio-group v-model="form.moldStatus">
                    <el-radio v-for="dict in dict.type.equipment_status" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否启用" prop="isActive">
                  <el-select v-model="form.isActive" placeholder="请选择是否启用" style="width:100%">
                    <el-option
                      v-for="dict in dict.type.business_active"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="规格参数" name="specs">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="模具重量(kg)" prop="moldWeight">
                  <el-input-number v-model="form.moldWeight" :precision="2" :step="1" placeholder="请输入模具重量" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="模具尺寸" prop="moldSize">
                  <el-input v-model="form.moldSize" placeholder="请输入模具尺寸" prefix-icon="el-icon-scale" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="型腔数量" prop="cavityCount">
                  <el-input-number v-model="form.cavityCount" :precision="0" :step="1" placeholder="请输入型腔数量" style="width: 100%;" :min="1" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="设计寿命(次)" prop="designLife">
                  <el-input-number v-model="form.designLife" :precision="0" :step="1000" placeholder="请输入设计寿命" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="最大注塑重量(g)" prop="maxShotWeight">
                  <el-input-number v-model="form.maxShotWeight" :precision="2" :step="1" placeholder="请输入最大注塑重量" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="最小注塑重量(g)" prop="minShotWeight">
                  <el-input-number v-model="form.minShotWeight" :precision="2" :step="1" placeholder="请输入最小注塑重量" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="模具下模时间(分钟)" prop="moldDownTime">
                  <el-input-number v-model="form.moldDownTime" :precision="2" :step="0.01" placeholder="请输入模具下模时间" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="模具上模时间(分钟)" prop="moldUpTime">
                  <el-input-number v-model="form.moldUpTime" :precision="2" :step="0.01" placeholder="请输入模具上模时间" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="维护信息" name="maintenance">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="当前使用次数" prop="currentUsageCount">
                  <el-input-number v-model="form.currentUsageCount" :precision="0" :step="1" placeholder="请输入当前使用次数" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="维护周期(次)" prop="maintenanceCycle">
                  <el-input-number v-model="form.maintenanceCycle" :precision="0" :step="100" placeholder="请输入维护周期" style="width: 100%;" :min="1" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="上次维护日期" prop="lastMaintenanceDate">
                  <el-date-picker clearable v-model="form.lastMaintenanceDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择上次维护日期" style="width:100%" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="下次维护日期" prop="nextMaintenanceDate">
                  <el-date-picker clearable v-model="form.nextMaintenanceDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择下次维护日期" style="width:100%" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="采购信息" name="purchase">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="供应商" prop="supplierId">
                  <el-input v-model="form.supplierId" placeholder="请输入供应商" prefix-icon="el-icon-user" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="采购日期" prop="purchaseDate">
                  <el-date-picker clearable v-model="form.purchaseDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择采购日期" style="width:100%" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="采购价格" prop="purchasePrice">
                  <el-input-number v-model="form.purchasePrice" :precision="2" :step="1000" placeholder="请输入采购价格" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" placeholder="请输入备注信息" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="模具明细" name="details">
          <div style="padding: 20px;">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddErpMoldDetail">添加</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteErpMoldDetail">删除</el-button>
              </el-col>
            </el-row>
            <el-table :data="erpMoldDetailList" :row-class-name="rowErpMoldDetailIndex" @selection-change="handleErpMoldDetailSelectionChange" ref="erpMoldDetail">
              <el-table-column type="selection" width="50" align="center" />
              <el-table-column label="序号" align="center" prop="index" width="50"/>
              <el-table-column label="组件编码" prop="componentCode" width="120" />
              <el-table-column label="组件名称" prop="componentName" width="120" />
              <el-table-column label="组件类型" prop="componentType" width="100">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.component_type" :value="scope.row.componentType"/>
                </template>
              </el-table-column>
              <el-table-column label="状态" prop="status" width="100">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.equipment_status" :value="scope.row.status"/>
                </template>
              </el-table-column>
              <el-table-column label="规格尺寸" prop="specification" width="120" />
              <el-table-column label="材质" prop="material" width="100" />
              <el-table-column label="数量" prop="quantity" width="80" />
              <el-table-column label="单位" prop="unit" width="80" />
              <el-table-column label="单价" prop="unitPrice" width="100" />
              <el-table-column label="总价" prop="totalPrice" width="100" />
              <el-table-column label="供应商" prop="supplier" width="120" />
              <el-table-column label="采购日期" prop="purchaseDate" width="120" />
              <el-table-column label="操作" align="center" width="120">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleEditDetail(scope.row, scope.$index)"
                  >编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 物料选择弹窗 -->
    <material-select ref="materialSelect" @select="handleMaterialSelect" />

    <!-- 模具明细编辑弹窗 -->
    <mold-detail-edit ref="moldDetailEdit" @select="handleDetailEdit" />
  </div>
</template>

<script>
import { listMold, getMold, delMold, addMold, updateMold } from "@/api/erp/mold"
import MaterialSelect from "@/components/MaterialSelect"
import MoldDetailEdit from "@/components/MoldDetailEdit"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import BarcodePrinter from "@/components/BarcodePrinter"

export default {
  name: "Mold",
  components: {
    MaterialSelect,
    MoldDetailEdit,
    BarcodeDisplay,
    BarcodePrinter
  },
  dicts: ['equipment_status', 'mold_type', 'mold_category', 'component_type', 'business_active'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedErpMoldDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 模具表格数据
      moldList: [],
      // 模具明细表格数据
      erpMoldDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的Tab
      activeTab: "basic",
      // 物料显示值
      materialDisplay: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        moldCode: null,
        moldName: null,
        moldType: null,
        moldCategory: null,
        moldWeight: null,
        moldSize: null,
        cavityCount: null,
        moldStatus: null,
        isActive: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        moldCode: [
          { required: true, message: "模具编码不能为空", trigger: "blur" }
        ],
        moldName: [
          { required: true, message: "模具名称不能为空", trigger: "blur" }
        ],
        moldType: [
          { required: true, message: "模具类型不能为空", trigger: "change" }
        ],
        moldCategory: [
          { required: true, message: "模具分类不能为空", trigger: "change" }
        ],
        productMaterialId: [
          { required: true, message: "产品物料ID不能为空", trigger: "blur" }
        ],
        moldStatus: [
          { required: true, message: "模具状态不能为空", trigger: "change" }
        ],
        isActive: [
          { required: true, message: "是否启用不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询模具列表 */
    getList() {
      this.loading = true
      listMold(this.queryParams).then(response => {
        this.moldList = response.rows
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
        moldCode: null,
        moldName: null,
        moldType: null,
        moldCategory: null,
        productMaterialId: null,
        storageLocationId: null,
        moldWeight: null,
        moldSize: null,
        cavityCount: null,
        maxShotWeight: null,
        minShotWeight: null,
        designLife: null,
        currentUsageCount: null,
        maintenanceCycle: null,
        lastMaintenanceDate: null,
        nextMaintenanceDate: null,
        moldStatus: null,
        supplierId: null,
        purchaseDate: null,
        purchasePrice: null,
        moldDownTime: null,
        moldUpTime: null,
        isActive: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      }
      this.erpMoldDetailList = []
      // 初始化一个空的模具明细对象，包含所有必要字段
      this.erpMoldDetailList.push({
        componentCode: '',
        componentName: '',
        componentType: '',
        status: '',
        specification: '',
        material: '',
        quantity: 1,
        unit: '',
        unitPrice: 0,
        totalPrice: 0,
        supplier: '',
        purchaseDate: '',
        remark: ''
      })
      this.activeTab = "basic"
      this.materialDisplay = ""
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
      this.title = "添加模具"
      this.activeTab = "basic"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getMold(id).then(response => {
        this.form = response.data
        this.erpMoldDetailList = response.data.erpMoldDetailList || []
        // 如果没有模具明细数据，初始化一个空对象
        if (this.erpMoldDetailList.length === 0) {
          this.erpMoldDetailList.push({
            componentCode: '',
            componentName: '',
            componentType: '',
            status: '',
            specification: '',
            material: '',
            quantity: 1,
            unit: '',
            unitPrice: 0,
            totalPrice: 0,
            supplier: '',
            purchaseDate: '',
            remark: ''
          })
        }
        this.setMaterialDisplay()
        this.open = true
        this.title = "修改模具"
        this.activeTab = "basic"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 过滤掉空的模具明细记录
          this.form.erpMoldDetailList = this.erpMoldDetailList.filter(item =>
            item.componentCode && item.componentName
          )
          if (this.form.id != null) {
            updateMold(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMold(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除模具编号为"' + ids + '"的数据项？').then(function() {
        return delMold(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
	/** 模具明细序号 */
    rowErpMoldDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 模具明细添加按钮操作 */
    handleAddErpMoldDetail() {
      this.$refs.moldDetailEdit.show()
    },
    /** 模具明细删除按钮操作 */
    handleDeleteErpMoldDetail() {
      if (this.checkedErpMoldDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的模具明细数据")
      } else {
        const erpMoldDetailList = this.erpMoldDetailList
        const checkedErpMoldDetail = this.checkedErpMoldDetail
        this.erpMoldDetailList = erpMoldDetailList.filter(function(item) {
          return checkedErpMoldDetail.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleErpMoldDetailSelectionChange(selection) {
      this.checkedErpMoldDetail = selection.map(item => item.index)
    },

    /** 编辑模具明细 */
    handleEditDetail(row, index) {
      this.$refs.moldDetailEdit.show(row)
    },

    /** 处理模具明细编辑结果 */
    handleDetailEdit(detailData) {
      // 查找当前编辑的行并更新数据
      const index = this.erpMoldDetailList.findIndex(item =>
        item.componentCode === detailData.componentCode &&
        item.componentName === detailData.componentName
      )

      if (index !== -1) {
        // 更新现有行
        this.erpMoldDetailList.splice(index, 1, detailData)
      } else {
        // 添加新行
        this.erpMoldDetailList.push(detailData)
      }

      // 重新计算序号
      this.erpMoldDetailList.forEach((item, idx) => {
        item.index = idx + 1
      })

      this.$message.success('模具明细更新成功')
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/mold/export', {
        ...this.queryParams
      }, `mold_${new Date().getTime()}.xlsx`)
    },

    /** 显示物料选择弹窗 */
    showMaterialSelect() {
      this.$refs.materialSelect.show()
    },

    /** 清除物料选择 */
    clearMaterial() {
      this.form.productMaterialId = null
      this.materialDisplay = ""
    },

    /** 处理物料选择 */
    handleMaterialSelect(selectedMaterial) {
      this.form.productMaterialId = selectedMaterial.id
      this.materialDisplay = selectedMaterial.materialCode + ' - ' + selectedMaterial.materialName
    },

    /** 设置物料显示值 */
    setMaterialDisplay() {
      if (this.form.productMaterialId) {
        // 从后端返回的数据中获取物料信息
        if (this.form.materialCode && this.form.materialName) {
          this.materialDisplay = this.form.materialCode + ' - ' + this.form.materialName
        } else {
          this.materialDisplay = this.form.productMaterialId
        }
      }
    }
  }
}
</script>
