<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料型号" prop="materialModel">
        <el-input
          v-model="queryParams.materialModel"
          placeholder="请输入物料型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-input
          v-model="queryCategoryDisplay"
          placeholder="请选择分类"
          readonly
          clearable
          @click.native="showQueryCategorySelect"
        >
          <el-button slot="append" icon="el-icon-search" @click="showQueryCategorySelect"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="工艺路线" prop="processRouteId">
        <el-input
          v-model="queryProcessRouteDisplay"
          placeholder="请选择工艺路线"
          readonly
          clearable
          @click.native="showQueryProcessRouteSelect"
        >
          <el-button slot="append" icon="el-icon-search" @click="showQueryProcessRouteSelect"></el-button>
        </el-input>
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
          v-hasPermi="['erp:masterMaterial:add']"
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
          v-hasPermi="['erp:masterMaterial:edit']"
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
          v-hasPermi="['erp:masterMaterial:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:masterMaterial:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="masterMaterialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="物料ID" align="center" prop="id" /> -->
      <el-table-column label="物料编码" align="center" prop="materialCode" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="物料规格" align="center" prop="materialSpec" />
      <el-table-column label="物料型号" align="center" prop="materialModel" />
      <el-table-column label="分类" align="center" prop="categoryId">
        <template slot-scope="scope">
          <span v-if="scope.row.categoryId">{{ getCategoryDisplay(scope.row.categoryId) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unitId">
        <template slot-scope="scope">
          <span v-if="scope.row.unitId">{{ getUnitDisplay(scope.row.unitId) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="工艺路线" align="center" prop="processRouteId">
        <template slot-scope="scope">
          <span v-if="scope.row.processRouteCode && scope.row.processRouteName">
            {{ scope.row.processRouteCode }} - {{ scope.row.processRouteName }}
          </span>
          <span v-else-if="scope.row.processRouteId">ID: {{ scope.row.processRouteId }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
      <el-table-column label="条码" align="center" prop="materialCode" width="120">
        <template slot-scope="scope">
          <barcode-display 
            v-if="scope.row.materialCode" 
            :value="scope.row.materialCode" 
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
             icon="el-icon-view"
             @click="handleView(scope.row)"
           >查看</el-button>
           <el-button
             size="mini"
             type="text"
             icon="el-icon-edit"
             @click="handleUpdate(scope.row)"
             v-hasPermi="['erp:masterMaterial:edit']"
           >修改</el-button>
           <el-button
             size="mini"
             type="text"
             icon="el-icon-delete"
             @click="handleDelete(scope.row)"
             v-hasPermi="['erp:masterMaterial:remove']"
           >删除</el-button>
           <barcode-printer 
             :machine-data="{
               machineCode: scope.row.materialCode,
               machineName: scope.row.materialName,
               machineType: scope.row.materialModel,
               workCenterName: scope.row.categoryName || '未设置分类'
             }"
             :title="'物料标签'"
             :label-title="'物料标签'"
             :info-labels="{
               code: '物料编码',
               name: '物料名称',
               type: '物料型号',
               workCenter: '物料分类'
             }"
             :machine-type-dict="[]"
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

    <!-- 添加或修改物料主数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <!-- 基本信息Tab -->
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="物料编码" prop="materialCode">
                  <el-input v-model="form.materialCode" placeholder="请输入物料编码" prefix-icon="el-icon-document" :disabled="isView" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="物料名称" prop="materialName">
                  <el-input v-model="form.materialName" placeholder="请输入物料名称" prefix-icon="el-icon-edit" :disabled="isView" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="物料规格" prop="materialSpec">
                  <el-input v-model="form.materialSpec" type="textarea" placeholder="请输入物料规格" :disabled="isView" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="物料型号" prop="materialModel">
                  <el-input v-model="form.materialModel" placeholder="请输入物料型号" prefix-icon="el-icon-collection" :disabled="isView" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="分类" prop="categoryId">
                  <div style="display: flex; align-items: center;">
                    <div style="flex: 1; position: relative;" @click="showCategorySelect">
                      <el-input
                        v-model="categoryDisplay"
                        placeholder="请选择分类"
                        readonly
                        :disabled="isView"
                        style="background-color: #f5f7fa; cursor: pointer;"
                        prefix-icon="el-icon-folder"
                      />
                    </div>
                    <el-button
                      type="primary"
                      icon="el-icon-arrow-down"
                      size="mini"
                      @click="showCategorySelect"
                      :disabled="isView"
                      style="margin-left: 10px;"
                    >
                      选择
                    </el-button>
                    <el-button
                      v-if="form.categoryId"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="clearCategory"
                      :disabled="isView"
                      style="margin-left: 5px;"
                    >
                      清除
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="单位" prop="unitId">
                  <div style="display: flex; align-items: center;">
                    <div style="flex: 1; position: relative;" @click="showUnitSelect">
                      <el-input
                        v-model="unitDisplay"
                        placeholder="请选择单位"
                        readonly
                        :disabled="isView"
                        style="background-color: #f5f7fa; cursor: pointer;"
                        prefix-icon="el-icon-scale"
                      />
                    </div>
                    <el-button
                      type="primary"
                      icon="el-icon-arrow-down"
                      size="mini"
                      @click="showUnitSelect"
                      :disabled="isView"
                      style="margin-left: 10px;"
                    >
                      选择
                    </el-button>
                    <el-button
                      v-if="form.unitId"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="clearUnit"
                      :disabled="isView"
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
                <el-form-item label="基本单位" prop="unitName">
                  <el-input v-model="form.unitName" placeholder="请输入基本单位" prefix-icon="el-icon-scale" :disabled="isView" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="标准单价" prop="unitPrice">
                  <el-input-number 
                    v-model="form.unitPrice" 
                    :precision="2" 
                    :step="0.01" 
                    placeholder="请输入标准单价" 
                    style="width: 100%;"
                    :min="0"
                    :disabled="isView"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 库存配置Tab -->
        <el-tab-pane label="库存配置" name="inventory">
          <el-form ref="form" :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="最小库存" prop="minStock">
                  <el-input-number 
                    v-model="form.minStock" 
                    :precision="0" 
                    :step="1" 
                    placeholder="请输入最小库存" 
                    style="width: 100%;"
                    :min="0"
                    :disabled="isView"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="最大库存" prop="maxStock">
                  <el-input-number 
                    v-model="form.maxStock" 
                    :precision="0" 
                    :step="1" 
                    placeholder="请输入最大库存" 
                    style="width: 100%;"
                    :min="0"
                    :disabled="isView"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="安全库存" prop="safetyStock">
                  <el-input-number 
                    v-model="form.safetyStock" 
                    :precision="0" 
                    :step="1" 
                    placeholder="请输入安全库存" 
                    style="width: 100%;"
                    :min="0"
                    :disabled="isView"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="提前期(天)" prop="leadTime">
                  <el-input-number 
                    v-model="form.leadTime" 
                    :precision="0" 
                    :step="1" 
                    placeholder="请输入提前期" 
                    style="width: 100%;"
                    :min="0"
                    :disabled="isView"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="保质期(天)" prop="shelfLife">
                  <el-input-number 
                    v-model="form.shelfLife" 
                    :precision="0" 
                    :step="1" 
                    placeholder="请输入保质期" 
                    style="width: 100%;"
                    :min="0"
                    :disabled="isView"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 业务属性Tab -->
        <el-tab-pane label="业务属性" name="business">
          <el-form ref="form" :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="是否采购件" prop="isPurchased">
                  <el-radio-group v-model="form.isPurchased" :disabled="isView">
                    <el-radio
                      v-for="dict in dict.type.business_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否制造件" prop="isManufactured">
                  <el-radio-group v-model="form.isManufactured" :disabled="isView">
                    <el-radio
                      v-for="dict in dict.type.business_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="是否销售件" prop="isSales">
                  <el-radio-group v-model="form.isSales" :disabled="isView">
                    <el-radio
                      v-for="dict in dict.type.business_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否库存件" prop="isInventory">
                  <el-radio-group v-model="form.isInventory" :disabled="isView">
                    <el-radio
                      v-for="dict in dict.type.business_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="是否启用" prop="isActive">
                  <el-radio-group v-model="form.isActive" :disabled="isView">
                    <el-radio
                      v-for="dict in dict.type.business_active"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息" :disabled="isView" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 工艺路线Tab -->
        <el-tab-pane label="工艺路线" name="processRoute">
          <el-form ref="form" :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="工艺路线" prop="processRouteId">
                  <div style="display: flex; align-items: center;">
                    <div style="flex: 1; position: relative;" @click="showProcessRouteSelect">
                      <el-input
                        v-model="processRouteDisplay"
                        placeholder="请选择工艺路线"
                        readonly
                        :disabled="isView"
                        style="background-color: #f5f7fa; cursor: pointer;"
                        prefix-icon="el-icon-s-operation"
                      />
                    </div>
                    <el-button
                      type="primary"
                      icon="el-icon-arrow-down"
                      size="mini"
                      @click="showProcessRouteSelect"
                      :disabled="isView"
                      style="margin-left: 10px;"
                    >
                      选择
                    </el-button>
                    <el-button
                      v-if="form.processRouteId"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="clearProcessRoute"
                      :disabled="isView"
                      style="margin-left: 5px;"
                    >
                      清除
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
      </el-tabs>
         
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="!isView">确 定</el-button>
        <el-button @click="cancel">{{ isView ? '关闭' : '取消' }}</el-button>
      </div>
    </el-dialog>
    
    <!-- 关联表选择组件 -->
    
    <!-- 物料分类选择弹窗 - 搜索用（只显示启用的分类） -->
    <material-category-select ref="categorySelect" :only-active="true" @select="handleCategorySelect" />
    
    <!-- 物料分类选择弹窗 - 表单用（只显示启用的分类） -->
    <material-category-select ref="formCategorySelect" :only-active="true" @select="handleCategorySelect" />
    
    <!-- 单位选择弹窗 -->
    <unit-select ref="unitSelect" @select="handleUnitSelect" />
    
    <!-- 工艺路线选择弹窗 -->
    <process-route-select ref="processRouteSelect" @select="handleProcessRouteSelect" />
  </div>
</template>

<script>
import { listMasterMaterial, getMasterMaterial, delMasterMaterial, addMasterMaterial, updateMasterMaterial } from "@/api/erp/masterMaterial"
import MaterialCategorySelect from "@/components/MaterialCategorySelect"
import UnitSelect from "@/components/UnitSelect"
import ProcessRouteSelect from "@/components/ProcessRouteSelect"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import BarcodePrinter from "@/components/BarcodePrinter"

export default {
  name: "MasterMaterial",
  components: {
    MaterialCategorySelect,
    UnitSelect,
    ProcessRouteSelect,
    BarcodeDisplay,
    BarcodePrinter
  },
  dicts: ['business_yes_no', 'business_active'],
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
      // 物料主数据表格数据
      masterMaterialList: [],
             // 弹出层标题
       title: "",
       // 是否显示弹出层
       open: false,
       // 是否为查看模式
       isView: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialName: null,
        materialSpec: null,
        materialModel: null,
        categoryId: null,
        processRouteId: null,
        isActive: null,
      },
      // 表单参数
      form: {},
      // 分类显示值
      categoryDisplay: "",
      // 单位显示值
      unitDisplay: "",
      // 工艺路线显示值
      processRouteDisplay: "",
      // 搜索分类显示值
      queryCategoryDisplay: "",
      // 搜索工艺路线显示值
      queryProcessRouteDisplay: "",
      // 当前激活的Tab
      activeTab: "basic",
      // 表单校验
      rules: {
        materialCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "分类不能为空", trigger: "blur" }
        ],
        unitName: [
          { required: true, message: "基本单位不能为空", trigger: "blur" }
        ],
        isActive: [
          { required: true, message: "是否启用不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询物料主数据列表 */
    getList() {
      this.loading = true
      listMasterMaterial(this.queryParams).then(response => {
        this.masterMaterialList = response.rows
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
         materialCode: null,
         materialName: null,
         materialSpec: null,
         materialModel: null,
         categoryId: null,
         unitId: null,
         unitName: null,
         unitPrice: null,
         minStock: null,
         maxStock: null,
         safetyStock: null,
         leadTime: null,
         shelfLife: null,
         isPurchased: "0",
         isManufactured: "0",
         isSales: "0",
         isInventory: "0",
         isActive: "1",
         remark: null,
         createBy: null,
         createTime: null,
         updateBy: null,
         updateTime: null
       }
       this.isView = false
       this.categoryDisplay = ""
       this.unitDisplay = ""
       this.queryCategoryDisplay = ""
       this.activeTab = "basic"
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
       this.queryCategoryDisplay = ""
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
       this.title = "添加物料主数据"
       this.isView = false
     },
         /** 查看按钮操作 */
     handleView(row) {
       this.reset()
       const id = row.id
       getMasterMaterial(id).then(response => {
         this.form = response.data
         // 设置分类和单位显示值
         this.setDisplayValues()
         this.open = true
         this.title = "查看物料主数据"
         this.isView = true
       })
     },
     /** 修改按钮操作 */
     handleUpdate(row) {
       this.reset()
       const id = row.id || this.ids
       getMasterMaterial(id).then(response => {
         this.form = response.data
         // 设置分类和单位显示值
         this.setDisplayValues()
         this.open = true
         this.title = "修改物料主数据"
         this.isView = false
       })
     },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMasterMaterial(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMasterMaterial(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除物料主数据编号为"' + ids + '"的数据项？').then(function() {
        return delMasterMaterial(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
         /** 导出按钮操作 */
     handleExport() {
       this.download('erp/masterMaterial/export', {
         ...this.queryParams
       }, `masterMaterial_${new Date().getTime()}.xlsx`)
     },
     
     /** 显示物料分类选择弹窗 */
     showCategorySelect() {
       this.$refs.formCategorySelect.show()
     },
     
     /** 显示搜索分类选择弹窗 */
     showQueryCategorySelect() {
       this.$refs.categorySelect.show()
     },
     
     /** 显示搜索工艺路线选择弹窗 */
     showQueryProcessRouteSelect() {
       this.$refs.processRouteSelect.show()
     },
     
     /** 清除分类选择 */
     clearCategory() {
       this.form.categoryId = null
       this.categoryDisplay = ""
     },
     
     /** 清除单位选择 */
     clearUnit() {
       this.form.unitId = null
       this.unitDisplay = ""
     },
     
     /** 处理物料分类选择 */
     handleCategorySelect(selectedCategory) {
       // 判断是搜索选择还是表单选择
       if (this.open) {
         // 表单选择
         this.form.categoryId = selectedCategory.id
         this.categoryDisplay = selectedCategory.categoryCode + ' - ' + selectedCategory.categoryName
       } else {
         // 搜索选择
         this.queryParams.categoryId = selectedCategory.id
         this.queryCategoryDisplay = selectedCategory.categoryCode + ' - ' + selectedCategory.categoryName
       }
     },
     
     /** 显示单位选择弹窗 */
     showUnitSelect() {
       this.$refs.unitSelect.show()
     },
     
     /** 处理单位选择 */
     handleUnitSelect(selectedUnit) {
       this.form.unitId = selectedUnit.id
       this.unitDisplay = selectedUnit.unitName
     },
     
     /** 显示工艺路线选择弹窗 */
     showProcessRouteSelect() {
       this.$refs.processRouteSelect.show()
     },
     
     /** 清除工艺路线选择 */
     clearProcessRoute() {
       this.form.processRouteId = null
       this.processRouteDisplay = ""
     },
     
     /** 处理工艺路线选择 */
     handleProcessRouteSelect(selectedProcessRoute) {
       // 判断是搜索选择还是表单选择
       if (this.open) {
         // 表单选择
         this.form.processRouteId = selectedProcessRoute.id
         this.processRouteDisplay = selectedProcessRoute.processRouteCode + ' - ' + selectedProcessRoute.processRouteName
       } else {
         // 搜索选择
         this.queryParams.processRouteId = selectedProcessRoute.id
         this.queryProcessRouteDisplay = selectedProcessRoute.processRouteCode + ' - ' + selectedProcessRoute.processRouteName
       }
     },
     
     /** 设置显示值 */
     setDisplayValues() {
       // 设置分类显示值
       if (this.form.categoryId) {
         // 从后端返回的数据中获取分类信息
         if (this.form.categoryCode && this.form.categoryName) {
           this.categoryDisplay = this.form.categoryCode + ' - ' + this.form.categoryName
         } else {
           this.categoryDisplay = this.form.categoryId
         }
       }
       // 设置单位显示值
       if (this.form.unitId) {
         // 从后端返回的数据中获取单位信息
         if (this.form.unitName) {
           this.unitDisplay = this.form.unitName
         } else {
           this.unitDisplay = this.form.unitId
         }
       }
       // 设置工艺路线显示值
       if (this.form.processRouteId) {
         // 从后端返回的数据中获取工艺路线信息
         if (this.form.processRouteCode && this.form.processRouteName) {
           this.processRouteDisplay = this.form.processRouteCode + ' - ' + this.form.processRouteName
         } else {
           this.processRouteDisplay = this.form.processRouteId
         }
       }
     },
     
     /** 根据分类ID获取分类显示值 */
     getCategoryDisplay(categoryId) {
       // 从当前列表数据中查找分类信息
       const material = this.masterMaterialList.find(item => item.categoryId === categoryId)
       if (material && material.categoryCode && material.categoryName) {
         return material.categoryCode + ' - ' + material.categoryName
       }
       return categoryId
     },
     
     /** 根据单位ID获取单位显示值 */
     getUnitDisplay(unitId) {
       // 从当前列表数据中查找单位信息
       const material = this.masterMaterialList.find(item => item.unitId === unitId)
       if (material && material.unitName) {
         return material.unitName
       }
       return unitId
     },
    
    /** 查看关联数据 */
    handleViewRelatedData(tableName, relatedFields, value) {
      if (!value) {
        this.$modal.msgInfo('该字段没有关联数据')
        return
      }
      // 这里可以打开一个弹窗显示关联数据详情
      this.$modal.msgInfo('查看' + tableName + '表中字段' + relatedFields + '的值为' + value + '的数据')
    },
    
         /** 选择关联数据 */
     handleSelectRelatedData(tableName, relatedFields, fieldName, javaField) {
       // 动态导入关联表的select组件
       const componentName = this.getRelatedComponentName(tableName);
       if (componentName) {
         // 使用动态组件显示选择弹窗
         this.$refs[componentName + 'Select'].show((selectedData) => {
           // 设置选中值
           this.form[fieldName] = selectedData.id;
           // 设置显示值
           const displayValue = this.buildDisplayValue(selectedData, relatedFields);
           this.form[javaField + 'Display'] = displayValue;
         });
       } else {
         this.$modal.msgError('未找到关联表的select组件：' + tableName);
       }
     },
     
     /** 获取关联组件的名称 */
     getRelatedComponentName(tableName) {
       // 根据表名生成组件名称，例如：erp_material_category -> MaterialCategory
       const parts = tableName.split('_');
       let componentName = '';
       for (let i = 1; i < parts.length; i++) {
         componentName += parts[i].charAt(0).toUpperCase() + parts[i].slice(1);
       }
       return componentName;
     },
     
     /** 构建显示值 */
     buildDisplayValue(selectedData, relatedFields) {
       if (!relatedFields) return '';
       const fields = relatedFields.split(',');
       const values = [];
       fields.forEach(field => {
         const trimmedField = field.trim();
         if (selectedData[trimmedField]) {
           values.push(selectedData[trimmedField]);
         }
       });
       return values.join(' - ');
     }
  }
}
</script>
