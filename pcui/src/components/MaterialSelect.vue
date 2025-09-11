<template>
  <!-- 物料选择弹窗 -->
  <el-dialog title="选择物料" :visible.sync="visible" width="1000px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
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
      <el-form-item label="物料规格" prop="materialSpec">
        <el-input
          v-model="queryParams.materialSpec"
          placeholder="请输入物料规格"
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

    <el-table v-loading="loading" :data="materialList" @row-click="handleRowClick" highlight-current-row @selection-change="handleSelectionChange" ref="materialTable">
      <el-table-column v-if="multiple" type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="materialCode" width="120" />
      <el-table-column label="物料名称" align="center" prop="materialName" width="150" />
      <el-table-column label="物料规格" align="center" prop="materialSpec" width="120" />
      <el-table-column label="物料型号" align="center" prop="materialModel" width="120" />
      <el-table-column label="分类" align="center" prop="categoryId" width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.categoryCode && scope.row.categoryName">
            {{ scope.row.categoryCode }} - {{ scope.row.categoryName }}
          </span>
          <span v-else-if="scope.row.categoryId">ID: {{ scope.row.categoryId }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unitName" width="80" />
      <el-table-column label="标准单价" align="center" prop="unitPrice" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.unitPrice">¥{{ scope.row.unitPrice }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isActive" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
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

    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listMasterMaterial } from "@/api/erp/masterMaterial"

export default {
  name: "MaterialSelect",
  dicts: ['business_active'],
  props: {
    // 是否只显示启用的物料，默认为true
    onlyActive: {
      type: Boolean,
      default: true
    },
    // 是否支持多选，默认为false（单选模式）
    multiple: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 物料列表
      materialList: [],
      // 是否显示弹窗
      visible: false,
      // 选中的物料
      selectedMaterial: null,
      // 多选选中的物料列表
      selectedMaterials: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialCode: null,
        materialName: null,
        materialSpec: null,
        materialModel: null,
        isActive: null // 根据props动态设置
      }
    }
  },
  watch: {
    // 监听onlyActive属性变化，动态设置查询条件
    onlyActive: {
      handler(newVal) {
        this.queryParams.isActive = newVal ? "1" : null
      },
      immediate: true
    }
  },
  methods: {
    /** 显示弹窗 */
    show() {
      this.visible = true
      this.getList()
    },
    /** 查询物料列表 */
    getList() {
      this.loading = true
      console.log('查询参数:', this.queryParams)
      
      listMasterMaterial(this.queryParams).then(response => {
        console.log('API响应:', response)
        
        // 检查响应数据结构
        if (response.data) {
          this.materialList = response.data.rows || response.data || []
          this.total = response.data.total || 0
        } else if (response.rows) {
          this.materialList = response.rows || []
          this.total = response.total || 0
        } else {
          this.materialList = response || []
          this.total = 0
        }
        
        console.log('设置后的数据:', {
          materialList: this.materialList,
          total: this.total,
          length: this.materialList.length
        })
        
        this.loading = false
      }).catch(error => {
        console.error('获取物料列表失败:', error)
        this.materialList = []
        this.total = 0
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.queryParams.materialCode = null
      this.queryParams.materialName = null
      this.queryParams.materialSpec = null
      this.queryParams.materialModel = null
      this.handleQuery()
    },
    /** 行点击事件 */
    handleRowClick(row) {
      this.selectedMaterial = row
    },
    /** 多选变化事件 */
    handleSelectionChange(selection) {
      this.selectedMaterials = selection
    },
    /** 确认选择 */
    handleConfirm() {
      if (this.multiple) {
        // 多选模式
        if (this.selectedMaterials.length > 0) {
          this.$emit('select', this.selectedMaterials)
        } else {
          this.$modal.msgWarning("请选择至少一个物料")
          return
        }
      } else {
        // 单选模式
        if (this.selectedMaterial) {
          this.$emit('select', this.selectedMaterial)
        } else {
          this.$modal.msgWarning("请选择一个物料")
          return
        }
      }
      this.visible = false
      this.selectedMaterial = null
      this.selectedMaterials = []
      // 清空表格选择
      if (this.$refs.materialTable) {
        this.$refs.materialTable.clearSelection()
      }
    }
  }
}
</script>

<style scoped>
.el-table {
  margin-top: 10px;
}

.el-table .el-table__row {
  cursor: pointer;
}

.el-table .el-table__row:hover {
  background-color: #f5f7fa;
}

.el-table .current-row {
  background-color: #ecf5ff;
}
</style>