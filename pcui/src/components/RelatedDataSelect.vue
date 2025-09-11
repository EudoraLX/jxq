<template>
  <!-- 关联表数据选择弹窗 -->
  <el-dialog :title="dialogTitle" :visible.sync="visible" width="1000px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="关键词" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          placeholder="请输入关键词"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="dataList" height="400px">
        <el-table-column 
          v-for="field in displayFields" 
          :key="field" 
          :prop="field" 
          :label="getFieldLabel(field)"
          :show-overflow-tooltip="true"
        ></el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectData">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "RelatedDataSelect",
  props: {
    // 关联表名称
    tableName: {
      type: String,
      required: true
    },
    // 关联字段列表，逗号分隔
    relatedFields: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中的数据
      selectedData: null,
      // 总条数
      total: 0,
      // 数据列表
      dataList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: undefined
      },
      // 显示字段列表
      displayFields: []
    }
  },
  computed: {
    dialogTitle() {
      return `选择${this.tableName}数据`
    }
  },
  methods: {
    // 显示弹框
    show() {
      this.visible = true
      this.initDisplayFields()
      this.getList()
    },
    // 初始化显示字段
    initDisplayFields() {
      if (this.relatedFields) {
        this.displayFields = this.relatedFields.split(',').map(field => field.trim())
      } else {
        // 如果没有配置关联字段，显示所有字段
        this.displayFields = ['id', 'name', 'code']
      }
    },
    // 获取字段标签
    getFieldLabel(field) {
      const fieldLabels = {
        'id': 'ID',
        'name': '名称',
        'code': '编码',
        'category_id': '分类ID',
        'category_name': '分类名称',
        'category_code': '分类编码',
        'material_code': '物料编码',
        'material_name': '物料名称'
      }
      return fieldLabels[field] || field
    },
    clickRow(row) {
      this.selectedData = row
      // 高亮选中的行
      this.$refs.table.setCurrentRow(row)
    },
    // 查询数据
    getList() {
      // 这里需要根据tableName调用对应的API
      // 暂时使用模拟数据
      this.dataList = this.getMockData()
      this.total = this.dataList.length
    },
    // 获取模拟数据
    getMockData() {
      const mockData = {
        'erp_material_category': [
          { id: 1, category_code: 'CAT001', category_name: '原材料' },
          { id: 2, category_code: 'CAT002', category_name: '半成品' },
          { id: 3, category_code: 'CAT003', category_name: '成品' }
        ],
        'erp_material_master': [
          { id: 1, material_code: 'MAT001', material_name: '钢材A', category_id: 1, category_name: '原材料' },
          { id: 2, material_code: 'MAT002', material_name: '钢材B', category_id: 1, category_name: '原材料' }
        ]
      }
      return mockData[this.tableName] || []
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
    /** 选择数据操作 */
    handleSelectData() {
      if (!this.selectedData) {
        this.$modal.msgError("请选择要关联的数据")
        return
      }
      this.$emit("select", this.selectedData)
      this.visible = false
    }
  }
}
</script>
