<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="组件编码" prop="componentCode">
        <el-input
          v-model="queryParams.componentCode"
          placeholder="请输入组件编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="组件名称" prop="componentName">
        <el-input
          v-model="queryParams.componentName"
          placeholder="请输入组件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="组件类型" prop="componentType">
        <el-select v-model="queryParams.componentType" placeholder="请选择组件类型" clearable>
          <el-option
            v-for="dict in dict.type.component_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['erp:moldDetail:add']"
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
          v-hasPermi="['erp:moldDetail:edit']"
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
          v-hasPermi="['erp:moldDetail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:moldDetail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="moldDetailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="组件编码" align="center" prop="componentCode" />
      <el-table-column label="组件名称" align="center" prop="componentName" />
      <el-table-column label="组件类型" align="center" prop="componentType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.component_type" :value="scope.row.componentType"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_status" :value="scope.row.status"/>
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
            v-hasPermi="['erp:moldDetail:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:moldDetail:remove']"
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

    <!-- 添加或修改模具明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模具ID" prop="moldId">
          <el-input v-model="form.moldId" placeholder="请输入模具ID" />
        </el-form-item>
        <el-form-item label="组件编码" prop="componentCode">
          <el-input v-model="form.componentCode" placeholder="请输入组件编码" />
        </el-form-item>
        <el-form-item label="组件名称" prop="componentName">
          <el-input v-model="form.componentName" placeholder="请输入组件名称" />
        </el-form-item>
        <el-form-item label="组件类型" prop="componentType">
          <el-select v-model="form.componentType" placeholder="请选择组件类型">
            <el-option
              v-for="dict in dict.type.component_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="材料" prop="material">
          <el-input v-model="form.material" placeholder="请输入材料" />
        </el-form-item>
        <el-form-item label="硬度" prop="hardness">
          <el-input v-model="form.hardness" placeholder="请输入硬度" />
        </el-form-item>
        <el-form-item label="表面处理" prop="surfaceTreatment">
          <el-input v-model="form.surfaceTreatment" placeholder="请输入表面处理" />
        </el-form-item>
        <el-form-item label="尺寸规格" prop="dimensions">
          <el-input v-model="form.dimensions" placeholder="请输入尺寸规格" />
        </el-form-item>
        <el-form-item label="重量(kg)" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量(kg)" />
        </el-form-item>
        <el-form-item label="使用寿命(次)" prop="lifeCycle">
          <el-input v-model="form.lifeCycle" placeholder="请输入使用寿命(次)" />
        </el-form-item>
        <el-form-item label="当前使用次数" prop="currentUsageCount">
          <el-input v-model="form.currentUsageCount" placeholder="请输入当前使用次数" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.equipment_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="删除标志(0:正常 1:删除)" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志(0:正常 1:删除)" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMoldDetail, getMoldDetail, delMoldDetail, addMoldDetail, updateMoldDetail } from "@/api/erp/moldDetail"

export default {
  name: "MoldDetail",
  dicts: ['equipment_status', 'component_type'],
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
      // 模具明细表格数据
      moldDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        componentCode: null,
        componentName: null,
        componentType: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        moldId: [
          { required: true, message: "模具ID不能为空", trigger: "blur" }
        ],
        componentCode: [
          { required: true, message: "组件编码不能为空", trigger: "blur" }
        ],
        componentName: [
          { required: true, message: "组件名称不能为空", trigger: "blur" }
        ],
        componentType: [
          { required: true, message: "组件类型不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询模具明细列表 */
    getList() {
      this.loading = true
      listMoldDetail(this.queryParams).then(response => {
        this.moldDetailList = response.rows
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
        moldId: null,
        componentCode: null,
        componentName: null,
        componentType: null,
        material: null,
        hardness: null,
        surfaceTreatment: null,
        dimensions: null,
        weight: null,
        lifeCycle: null,
        currentUsageCount: null,
        status: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
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
      this.title = "添加模具明细"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getMoldDetail(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改模具明细"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMoldDetail(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMoldDetail(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除模具明细编号为"' + ids + '"的数据项？').then(function() {
        return delMoldDetail(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/moldDetail/export', {
        ...this.queryParams
      }, `moldDetail_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
