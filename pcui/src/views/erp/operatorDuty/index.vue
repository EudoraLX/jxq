<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工序编号" prop="processCode">
        <el-input
          v-model="queryParams.processCode"
          placeholder="请输入工序编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作员" prop="operatorId">
        <el-input
          v-model="queryParams.operatorName"
          placeholder="请输入操作员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="记录类型" prop="dutyType">
        <el-select v-model="queryParams.dutyType" placeholder="请选择记录类型" clearable>
          <el-option
            v-for="dict in dict.type.operator_duty_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="记录时间" prop="registerTime">
        <el-date-picker clearable
          v-model="queryParams.registerTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择记录时间">
        </el-date-picker>
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
          v-hasPermi="['erp:operatorDuty:add']"
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
          v-hasPermi="['erp:operatorDuty:edit']"
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
          v-hasPermi="['erp:operatorDuty:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:operatorDuty:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="operatorDutyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工序编号" align="center" prop="processCode" />
      <el-table-column label="工序名称" align="center" prop="processName" />
      <el-table-column label="操作员" align="center" prop="operatorName" />
      <el-table-column label="记录类型" align="center" prop="dutyType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.operator_duty_type" :value="scope.row.dutyType"/>
        </template>
      </el-table-column>
      <el-table-column label="记录时间" align="center" prop="registerTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.registerTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:operatorDuty:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:operatorDuty:remove']"
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

    <!-- 添加或修改在岗离岗记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="工序" prop="processId">
          <el-input v-model="processDisplayName" placeholder="请选择工序" readonly @click="selectProcess">
            <el-button slot="append" icon="el-icon-search" @click="selectProcess"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="操作员" prop="operatorId">
          <el-input v-model="form.operatorName" placeholder="操作员信息" readonly />
        </el-form-item>
        <el-form-item label="记录类型" prop="dutyType">
          <el-select v-model="form.dutyType" placeholder="请选择记录类型">
            <el-option
              v-for="dict in dict.type.operator_duty_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="记录时间" prop="registerTime">
          <el-date-picker clearable
            v-model="form.registerTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择记录时间"
            readonly>
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 工序选择对话框 -->
    <el-dialog title="选择工序" :visible.sync="processDialogVisible" width="600px" append-to-body>
      <el-table v-loading="processLoading" :data="processList" @row-click="selectProcessRow">
        <el-table-column label="工序编码" align="center" prop="processCode" />
        <el-table-column label="工序名称" align="center" prop="processName" />
        <el-table-column label="工序描述" align="center" prop="remark" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listOperatorDuty, getOperatorDuty, delOperatorDuty, addOperatorDuty, updateOperatorDuty } from "@/api/erp/operatorDuty";
import { listProcess } from "@/api/erp/process";

export default {
  name: "OperatorDuty",
  dicts: ['operator_duty_type'],
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
      // 在岗离岗记录表格数据
      operatorDutyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processCode: null,
        operatorName: null,
        dutyType: null,
        registerTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        processId: [
          { required: true, message: "工序不能为空", trigger: "change" }
        ],
        operatorId: [
          { required: true, message: "操作员不能为空", trigger: "change" }
        ],
        dutyType: [
          { required: true, message: "记录类型不能为空", trigger: "change" }
        ],
        registerTime: [
          { required: true, message: "记录时间不能为空", trigger: "blur" }
        ]
      },
      // 工序选择相关
      processDialogVisible: false,
      processList: [],
      processLoading: false
    };
  },
  computed: {
    processDisplayName() {
      if (this.form.processCode && this.form.processName) {
        return `${this.form.processCode} - ${this.form.processName}`;
      }
      return '';
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询在岗离岗记录列表 */
    getList() {
      this.loading = true;
      listOperatorDuty(this.queryParams).then(response => {
        this.operatorDutyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        processId: null,
        processCode: null,
        operatorId: null,
        dutyType: null,
        registerTime: null,
        processName: null,
        operatorName: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加在岗离岗记录";
      // 设置默认值
      this.form.registerTime = this.parseTime(new Date(), '{y}-{m}-{d} {h}:{i}:{s}');
      // 设置当前用户为操作员
      this.form.operatorId = this.$store.state.user.id;
      this.form.operatorName = this.$store.state.user.name;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOperatorDuty(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改在岗离岗记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOperatorDuty(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOperatorDuty(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除在岗离岗记录编号为"' + ids + '"的数据项？').then(function() {
        return delOperatorDuty(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/operatorDuty/export', {
        ...this.queryParams
      }, `在岗离岗记录_${new Date().getTime()}.xlsx`)
    },
    // 选择工序
    selectProcess() {
      this.processDialogVisible = true;
      this.getProcessList();
    },
    // 获取工序列表
    getProcessList() {
      this.processLoading = true;
      listProcess({}).then(response => {
        this.processList = response.rows;
        this.processLoading = false;
      });
    },
    // 选择工序行
    selectProcessRow(row) {
      this.form.processId = row.id;
      this.form.processCode = row.processCode;
      this.form.processName = row.processName;
      this.processDialogVisible = false;
    }
  }
};
</script>
