<template>
  <!-- 物料分类选择弹窗 -->
  <el-dialog title="选择物料分类" :visible.sync="visible" width="900px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="分类编码" prop="categoryCode">
        <el-input
          v-model="queryParams.categoryCode"
          placeholder="请输入分类编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 树状结构显示 -->
    <div class="category-tree-container">
             <el-tree
         ref="categoryTree"
         :data="categoryTreeData"
         :props="defaultProps"
         :expand-on-click-node="false"
         :highlight-current="true"
         :default-expand-all="true"
         node-key="id"
         @node-click="handleNodeClick"
         class="category-tree"
       >
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span class="node-content">
            <i class="el-icon-folder" v-if="data.children && data.children.length > 0"></i>
            <i class="el-icon-document" v-else></i>
            <span class="node-label">{{ data.categoryName }}</span>
            <span class="node-code" v-if="data.categoryCode">({{ data.categoryCode }})</span>
            <el-tag 
              size="mini" 
              :type="data.isActive === '1' ? 'success' : 'info'"
              class="status-tag"
            >
              {{ data.isActive === '1' ? '启用' : '禁用' }}
            </el-tag>
          </span>
        </span>
      </el-tree>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listMaterialCategory } from "@/api/erp/materialCategory"

export default {
  name: "MaterialCategorySelect",
  dicts: ['business_active'],
  props: {
    // 是否只显示启用的分类，默认为true
    onlyActive: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 分类列表（平铺结构，用于搜索）
      categoryList: [],
      // 分类树数据（树状结构，用于显示）
      categoryTreeData: [],
      // 是否显示弹窗
      visible: false,
      // 选中的分类
      selectedCategory: null,
      // 树组件配置
      defaultProps: {
        children: 'children',
        label: 'categoryName'
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryCode: null,
        categoryName: null,
        isActive: null // 根据props动态设置
      }
    }
  },
     watch: {
     // 监听onlyActive属性变化，动态设置查询条件
     onlyActive: {
       handler(newVal) {
         // 临时注释掉过滤条件，显示所有数据
         // this.queryParams.isActive = newVal ? "1" : null
         this.queryParams.isActive = null
         console.log('设置查询条件 isActive:', this.queryParams.isActive)
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
         /** 查询分类列表 */
     getList() {
       this.loading = true
       console.log('查询参数:', this.queryParams)
       
       listMaterialCategory(this.queryParams).then(response => {
         console.log('API响应:', response)
         console.log('响应数据:', response.data)
         
         // 检查响应数据结构
         let rawData = []
         if (response.data) {
           rawData = response.data.rows || response.data || []
           this.total = response.data.total || 0
         } else if (response.rows) {
           rawData = response.rows || []
           this.total = response.total || 0
         } else {
           rawData = response || []
           this.total = 0
         }
         
         // 设置平铺数据（用于搜索）
         this.categoryList = rawData
         
         // 构建树状数据
         this.buildTreeData(rawData)
         
         console.log('设置后的数据:', {
           categoryList: this.categoryList,
           categoryTreeData: this.categoryTreeData,
           total: this.total,
           length: this.categoryList.length
         })
         
         this.loading = false
       }).catch(error => {
         console.error('获取分类列表失败:', error)
         this.categoryList = []
         this.categoryTreeData = []
         this.total = 0
         this.loading = false
       })
     },
     
     /** 构建树状数据 */
     buildTreeData(rawData) {
       if (!rawData || rawData.length === 0) {
         this.categoryTreeData = []
         return
       }
       
       console.log('构建树状数据，原始数据:', rawData)
       
       // 使用handleTree方法构建树状结构
       this.categoryTreeData = this.handleTree(rawData, "id", "parentId")
       
       console.log('构建后的树状数据:', this.categoryTreeData)
       
       // 如果没有顶级节点，创建一个虚拟的顶级节点
       if (this.categoryTreeData.length === 0) {
         this.categoryTreeData = rawData
         console.log('使用原始数据作为树状数据')
       }
     },
         /** 搜索按钮操作 */
     handleQuery() {
       this.queryParams.pageNum = 1
       this.getList()
     },
     
     /** 树节点点击事件 */
     handleNodeClick(data, node) {
       console.log('点击节点:', data, node)
       this.selectedCategory = data
       
       // 高亮选中的节点
       this.$refs.categoryTree.setCurrentKey(data.id)
     },
         /** 重置按钮操作 */
     resetQuery() {
       this.resetForm("queryForm")
       this.queryParams.categoryCode = null
       this.queryParams.categoryName = null
       this.handleQuery()
     },
    /** 行点击事件 */
    handleRowClick(row) {
      this.selectedCategory = row
    },
    /** 确认选择 */
    handleConfirm() {
      if (!this.selectedCategory) {
        this.$modal.msgWarning("请选择一个分类")
        return
      }
      this.$emit('select', this.selectedCategory)
      this.visible = false
      this.selectedCategory = null
    }
     }
 }
 </script>
 
 <style scoped>
 .category-tree-container {
   max-height: 400px;
   overflow-y: auto;
   border: 1px solid #e4e7ed;
   border-radius: 4px;
   padding: 10px;
   background-color: #fafafa;
 }
 
 .category-tree {
   background: transparent;
 }
 
 .custom-tree-node {
   flex: 1;
   display: flex;
   align-items: center;
   justify-content: space-between;
   font-size: 14px;
   padding-right: 8px;
 }
 
 .node-content {
   display: flex;
   align-items: center;
   gap: 8px;
 }
 
 .node-label {
   font-weight: 500;
   color: #303133;
 }
 
 .node-code {
   color: #909399;
   font-size: 12px;
 }
 
 .status-tag {
   margin-left: auto;
 }
 
 .el-tree-node__content {
   height: 40px;
   line-height: 40px;
 }
 
 .el-tree-node__content:hover {
   background-color: #f5f7fa;
 }
 
 .el-tree-node.is-current > .el-tree-node__content {
   background-color: #ecf5ff;
   color: #409eff;
 }
 </style>
