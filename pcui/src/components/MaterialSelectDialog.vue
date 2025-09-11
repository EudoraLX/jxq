<template>
  <el-dialog
    title="选择物料"
    :visible.sync="visible"
    width="1200px"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    @close="handleClose"
  >
    <div class="material-select-container">
      <!-- 搜索条件 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
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
        <el-form-item label="物料分类" prop="categoryId">
          <el-input 
            v-model="selectedCategoryName" 
            placeholder="请选择分类" 
            readonly
            @click="openCategorySelect"
            style="cursor: pointer;"
          >
            <el-button slot="append" icon="el-icon-search" @click="openCategorySelect"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 物料列表 -->
      <el-table
        v-loading="loading"
        :data="materialList"
        @selection-change="handleSelectionChange"
        @row-click="handleRowClick"
        height="400"
        ref="materialTable"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="物料编码" align="center" prop="materialCode" width="120" />
        <el-table-column label="物料名称" align="center" prop="materialName" width="200" />
        <el-table-column label="物料规格" align="center" prop="materialSpec" width="150" />
        <el-table-column label="物料型号" align="center" prop="materialModel" width="150" />
        <el-table-column label="物料分类" align="center" prop="categoryName" width="120" />
        <el-table-column label="单位" align="center" prop="unitName" width="80" />
        <el-table-column label="单位价格" align="center" prop="unitPrice" width="100">
          <template slot-scope="scope">
            <span>{{ parseFloat(scope.row.unitPrice || 0).toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="安全库存" align="center" prop="safetyStock" width="100" />
        <el-table-column label="是否启用" align="center" prop="isActive" width="100">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm" :disabled="selectedMaterial == null">确 定</el-button>
    </div>
    
    <!-- 物料分类选择弹窗 -->
    <MaterialCategorySelect 
      ref="categorySelect"
      @select="handleCategorySelect"
    />
  </el-dialog>
</template>

<script>
import { listMasterMaterial } from "@/api/erp/masterMaterial"
import { listMaterialCategory } from "@/api/erp/materialCategory"
import MaterialCategorySelect from "@/components/MaterialCategorySelect"

export default {
  name: "MaterialSelectDialog",
  dicts: ['business_active'],
  components: {
    MaterialCategorySelect
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      loading: false,
      materialList: [],
      categoryList: [],
      selectedMaterial: null,
      selectedCategoryName: '',
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
        categoryId: null,
        isActive: "1"
      }
    }
  },
  watch: {
    visible(val) {
      if (val) {
        this.getList()
        this.getCategoryList()
      }
    }
  },
  methods: {
    /** 查询物料列表 */
    getList() {
      this.loading = true
      listMasterMaterial(this.queryParams).then(response => {
        this.materialList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询物料分类列表 */
    getCategoryList() {
      listMaterialCategory({}).then(response => {
        console.log('物料分类API响应:', response)
        this.categoryList = response.rows || response.data || []
        if (this.categoryList.length === 0) {
          console.warn('物料分类数据为空，使用模拟数据')
          this.loadMockCategories()
        }
      }).catch(error => {
        console.error('物料分类API调用失败:', error)
        // 如果API不存在或权限不足，使用模拟数据
        this.loadMockCategories()
      })
    },
    /** 加载模拟物料分类数据 */
    loadMockCategories() {
      this.categoryList = [
        { id: 1, categoryCode: 'INSULATOR_INJECTION', categoryName: '注塑类绝缘子' },
        { id: 2, categoryCode: 'INSULATOR_INJECTION_PIN', categoryName: '针式注塑绝缘子' },
        { id: 3, categoryCode: 'INSULATOR_INJECTION_SUSPENSION', categoryName: '悬式注塑绝缘子' },
        { id: 4, categoryCode: 'INSULATOR_INJECTION_POST', categoryName: '支柱注塑绝缘子' },
        { id: 5, categoryCode: 'RAW_MATERIAL', categoryName: '原材料' }
      ]
    },
    /** 打开分类选择弹窗 */
    openCategorySelect() {
      this.$refs.categorySelect.show()
    },
    /** 处理分类选择确认 */
    handleCategorySelect(category) {
      this.queryParams.categoryId = category.id
      this.selectedCategoryName = category.categoryName
      // 重新查询物料列表
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.queryParams.categoryId = null
      this.selectedCategoryName = ''
      this.handleQuery()
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      if (selection.length > 0) {
        this.selectedMaterial = selection[0]
        // 清除其他选中项
        this.$refs.materialTable.clearSelection()
        this.$refs.materialTable.toggleRowSelection(selection[0])
      }
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.selectedMaterial = row
      this.$refs.materialTable.clearSelection()
      this.$refs.materialTable.toggleRowSelection(row)
    },
    /** 确认选择 */
    handleConfirm() {
      if (this.selectedMaterial) {
        this.$emit('confirm', {
          materialId: this.selectedMaterial.id,
          materialCode: this.selectedMaterial.materialCode,
          materialName: this.selectedMaterial.materialName,
          materialSpec: this.selectedMaterial.materialSpec,
          materialModel: this.selectedMaterial.materialModel,
          unitId: this.selectedMaterial.unitId,
          unitCode: this.selectedMaterial.unitCode,
          unitName: this.selectedMaterial.unitName,
          unitPrice: this.selectedMaterial.unitPrice
        })
        this.handleClose()
      }
    },
    /** 关闭对话框 */
    handleClose() {
      this.selectedMaterial = null
      this.selectedCategoryName = ''
      this.queryParams.categoryId = null
      this.$refs.materialTable.clearSelection()
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style scoped>
.material-select-container {
  padding: 10px;
}
</style>
