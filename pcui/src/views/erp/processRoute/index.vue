<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工艺路线编码" prop="processRouteCode">
        <el-input
          v-model="queryParams.processRouteCode"
          placeholder="请输入工艺路线编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工艺路线名称" prop="processRouteName">
        <el-input
          v-model="queryParams.processRouteName"
          placeholder="请输入工艺路线名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.approval_status"
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
          v-hasPermi="['erp:processRoute:add']"
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
          v-hasPermi="['erp:processRoute:edit']"
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
          v-hasPermi="['erp:processRoute:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:processRoute:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processRouteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工艺路线编码" align="center" prop="processRouteCode" />
      <el-table-column label="工艺路线名称" align="center" prop="processRouteName" />

      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="生效日期" align="center" prop="effectiveDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectiveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="失效日期" align="center" prop="expiryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.approval_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="默认版本" align="center" prop="isDefault">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_yes_no" :value="scope.row.isDefault"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:processRoute:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:processRoute:remove']"
          >删除</el-button>
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

    <!-- 添加或修改工艺路线主表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="工艺路线编码" prop="processRouteCode">
                  <el-input v-model="form.processRouteCode" placeholder="请输入工艺路线编码" prefix-icon="el-icon-document" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工艺路线名称" prop="processRouteName">
                  <el-input v-model="form.processRouteName" placeholder="请输入工艺路线名称" prefix-icon="el-icon-edit" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="版本号" prop="version">
                  <el-input v-model="form.version" placeholder="请输入版本号" prefix-icon="el-icon-edit" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="生效日期" prop="effectiveDate">
                  <el-date-picker clearable v-model="form.effectiveDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择生效日期" style="width:100%" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="失效日期" prop="expiryDate">
                  <el-date-picker clearable v-model="form.expiryDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择失效日期" style="width:100%" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="状态" prop="status">
                  <el-select v-model="form.status" placeholder="请选择状态" style="width:100%">
                    <el-option
                      v-for="dict in dict.type.approval_status"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="默认版本" prop="isDefault">
                  <el-select v-model="form.isDefault" placeholder="请选择默认版本" style="width:100%">
                    <el-option
                      v-for="dict in dict.type.business_yes_no"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
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
              <el-col :span="12">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" placeholder="请输入备注信息" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>


        <el-tab-pane label="工艺路线明细" name="details">
          <div style="padding: 20px;">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDetail">添加</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDetail">删除</el-button>
              </el-col>
            </el-row>
            <el-table :data="erpProcessRouteDetailList" :row-class-name="rowDetailIndex" @selection-change="handleDetailSelectionChange" ref="detail">
              <el-table-column type="selection" width="50" align="center" />
              <el-table-column label="序号" align="center" prop="index" width="50"/>
              <el-table-column label="工序顺序" prop="operationSequence" width="100" />
              <el-table-column label="工序" width="150">
                <template slot-scope="scope">
                  <span v-if="scope.row.processCode && scope.row.processName">
                    {{ scope.row.processCode }} - {{ scope.row.processName }}
                  </span>
                  <span v-else-if="scope.row.processId">ID: {{ scope.row.processId }}</span>
                </template>
              </el-table-column>
              <el-table-column label="人工工时(小时)" prop="laborHours" width="120" />
              <el-table-column label="机器工时(小时)" prop="machineHours" width="120" />
              <el-table-column label="关键工序" prop="isCritical" width="100">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.business_yes_no" :value="scope.row.isCritical"/>
                </template>
              </el-table-column>
              <el-table-column label="可选工序" prop="isOptional" width="100">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.business_yes_no" :value="scope.row.isOptional"/>
                </template>
              </el-table-column>
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


    <!-- 工艺路线明细编辑弹窗 -->
    <process-route-detail-edit ref="processRouteDetailEdit" @select="handleDetailEdit" />
  </div>
</template>

<script>
import { listProcessRoute, getProcessRoute, delProcessRoute, addProcessRoute, updateProcessRoute } from "@/api/erp/processRoute"

import ProcessRouteDetailEdit from "@/components/ProcessRouteDetailEdit"

export default {
  name: "ProcessRoute",
  components: {
    ProcessRouteDetailEdit
  },
  dicts: ['approval_status', 'business_yes_no', 'business_active'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDetail: [],
      // 当前编辑的明细索引
      currentEditIndex: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工艺路线主表格数据
      processRouteList: [],
      // 工艺路线明细表格数据
      erpProcessRouteDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的Tab
      activeTab: "basic",

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processRouteCode: null,
        processRouteName: null,

        version: null,
        status: null,
        isActive: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        processRouteCode: [
          { required: true, message: "工艺路线编码不能为空", trigger: "blur" }
        ],
        processRouteName: [
          { required: true, message: "工艺路线名称不能为空", trigger: "blur" }
        ],

        version: [
          { required: true, message: "版本号不能为空", trigger: "blur" }
        ],
        effectiveDate: [
          { required: true, message: "生效日期不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
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
    /** 查询工艺路线主表列表 */
    getList() {
      this.loading = true
      listProcessRoute(this.queryParams).then(response => {
        this.processRouteList = response.rows
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
        processRouteCode: null,
        processRouteName: null,
        version: null,
        effectiveDate: null,
        expiryDate: null,
        status: null,
        isDefault: null,
        isActive: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      }
      this.erpProcessRouteDetailList = []
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
      this.title = "添加工艺路线主表"
      this.activeTab = "basic"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProcessRoute(id).then(response => {
        this.form = response.data
        this.erpProcessRouteDetailList = response.data.erpProcessRouteDetailList || []

        this.open = true
        this.title = "修改工艺路线"
        this.activeTab = "basic"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.erpProcessRouteDetailList = this.erpProcessRouteDetailList
          if (this.form.id != null) {
            updateProcessRoute(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addProcessRoute(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工艺路线主表编号为"' + ids + '"的数据项？').then(function() {
        return delProcessRoute(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
	/** 工艺路线明细序号 */
    rowDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 工艺路线明细添加按钮操作 */
    handleAddDetail() {
      this.$refs.processRouteDetailEdit.show()
    },
    /** 工艺路线明细删除按钮操作 */
    handleDeleteDetail() {
      if (this.checkedDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的工艺路线明细数据")
      } else {
        const erpProcessRouteDetailList = this.erpProcessRouteDetailList
        const checkedDetail = this.checkedDetail
        this.erpProcessRouteDetailList = erpProcessRouteDetailList.filter(function(item) {
          return checkedDetail.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleDetailSelectionChange(selection) {
      this.checkedDetail = selection.map(item => item.index)
    },

    /** 编辑工艺路线明细 */
    handleEditDetail(row, index) {
      this.currentEditIndex = index // 保存当前编辑的索引
      this.$refs.processRouteDetailEdit.show(row)
    },

    /** 处理工艺路线明细编辑结果 */
    handleDetailEdit(detailData) {
      if (this.currentEditIndex !== undefined && this.currentEditIndex >= 0) {
        // 使用Vue.set确保响应式更新
        this.$set(this.erpProcessRouteDetailList, this.currentEditIndex, detailData)
        this.currentEditIndex = undefined // 重置索引
      } else {
        // 添加新行（如果没有索引，说明是新增）
        this.erpProcessRouteDetailList.push(detailData)
      }

      // 重新计算序号
      this.erpProcessRouteDetailList.forEach((item, idx) => {
        this.$set(item, 'index', idx + 1)
      })


      // 使用nextTick确保DOM更新
      this.$nextTick(() => {
        this.$message.success('工艺路线明细更新成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/processRoute/export', {
        ...this.queryParams
      }, `processRoute_${new Date().getTime()}.xlsx`)
    },
    


  }
}
</script>
