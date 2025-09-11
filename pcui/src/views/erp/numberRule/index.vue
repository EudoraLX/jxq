<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="规则编码" prop="ruleCode">
        <el-input
          v-model="queryParams.ruleCode"
          placeholder="请输入规则编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规则名称" prop="ruleName">
        <el-input
          v-model="queryParams.ruleName"
          placeholder="请输入规则名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务类型" prop="businessType">
        <el-select v-model="queryParams.businessType" placeholder="请选择业务类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_business_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="重置类型" prop="resetType">
        <el-select v-model="queryParams.resetType" placeholder="请选择重置类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_reset_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="需要审批" prop="needApproval">
        <el-select v-model="queryParams.needApproval" placeholder="请选择需要审批" clearable>
          <el-option
            v-for="dict in dict.type.business_yes_no"
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
          v-hasPermi="['erp:numberRule:add']"
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
          v-hasPermi="['erp:numberRule:edit']"
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
          v-hasPermi="['erp:numberRule:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:numberRule:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="numberRuleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规则编码" align="center" prop="ruleCode" />
      <el-table-column label="规则名称" align="center" prop="ruleName" />
      <el-table-column label="业务类型" align="center" prop="businessType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_business_type" :value="scope.row.businessType"/>
        </template>
      </el-table-column>
      <el-table-column label="前缀" align="center" prop="prefix" />
      <el-table-column label="日期格式" align="center" prop="dateFormat" />
      <el-table-column label="序列号长度" align="center" prop="sequenceLength" />
      <el-table-column label="当前序列号" align="center" prop="currentSequence" />
      <el-table-column label="重置类型" align="center" prop="resetType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_reset_type" :value="scope.row.resetType"/>
        </template>
      </el-table-column>
      <el-table-column label="最后重置日期" align="center" prop="lastResetDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastResetDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="需要审批" align="center" prop="needApproval">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_yes_no" :value="scope.row.needApproval"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:numberRule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:numberRule:remove']"
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

    <!-- 添加或修改编号规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="规则编码" prop="ruleCode">
              <el-input v-model="form.ruleCode" placeholder="请输入规则编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规则名称" prop="ruleName">
              <el-input v-model="form.ruleName" placeholder="请输入规则名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="业务类型" prop="businessType">
              <el-select v-model="form.businessType" placeholder="请选择业务类型">
                <el-option
                  v-for="dict in dict.type.erp_business_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="前缀" prop="prefix">
              <el-input v-model="form.prefix" placeholder="请输入前缀" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="日期格式" prop="dateFormat">
              <el-input v-model="form.dateFormat" placeholder="请输入日期格式，如：yyyyMMdd" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="序列号长度" prop="sequenceLength">
              <el-input-number v-model="form.sequenceLength" :min="1" :max="10" placeholder="序列号长度" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="当前序列号" prop="currentSequence">
              <el-input-number v-model="form.currentSequence" :min="1" placeholder="当前序列号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="重置类型" prop="resetType">
              <el-select v-model="form.resetType" placeholder="请选择重置类型">
                <el-option
                  v-for="dict in dict.type.erp_reset_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="最后重置日期" prop="lastResetDate">
              <el-date-picker clearable
                v-model="form.lastResetDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择最后重置日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="需要审批" prop="needApproval">
              <el-radio-group v-model="form.needApproval">
                <el-radio
                  v-for="dict in dict.type.business_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listNumberRule, getNumberRule, delNumberRule, addNumberRule, updateNumberRule } from "@/api/erp/numberRule"

export default {
  name: "NumberRule",
  dicts: ['erp_reset_type', 'business_yes_no', 'erp_business_type'],
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
      // 编号规则表格数据
      numberRuleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ruleCode: null,
        ruleName: null,
        businessType: null,
        prefix: null,
        resetType: null,
        needApproval: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ruleCode: [
          { required: true, message: "规则编码不能为空", trigger: "blur" }
        ],
        ruleName: [
          { required: true, message: "规则名称不能为空", trigger: "blur" }
        ],
        businessType: [
          { required: true, message: "业务类型不能为空", trigger: "blur" }
        ],
        sequenceLength: [
          { required: true, message: "序列号长度不能为空", trigger: "blur" }
        ],
        currentSequence: [
          { required: true, message: "当前序列号不能为空", trigger: "blur" }
        ],
        resetType: [
          { required: true, message: "重置类型不能为空", trigger: "change" }
        ],
        needApproval: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询编号规则列表 */
    getList() {
      this.loading = true
      listNumberRule(this.queryParams).then(response => {
        this.numberRuleList = response.rows
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
        ruleId: null,
        ruleCode: null,
        ruleName: null,
        businessType: null,
        prefix: null,
        dateFormat: null,
        sequenceLength: 4,
        currentSequence: 1,
        resetType: "YEAR",
        lastResetDate: null,
        needApproval: "0",
        remark: null
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
      this.ids = selection.map(item => item.ruleId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加编号规则"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const ruleId = row.ruleId || this.ids
      getNumberRule(ruleId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改编号规则"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.ruleId != null) {
            updateNumberRule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addNumberRule(this.form).then(response => {
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
      const ruleIds = row.ruleId || this.ids
      this.$modal.confirm('是否确认删除编号规则编号为"' + ruleIds + '"的数据项？').then(function() {
        return delNumberRule(ruleIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/numberRule/export', {
        ...this.queryParams
      }, `numberRule_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
