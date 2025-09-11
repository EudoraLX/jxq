<template>
  <div class="app-container">
    <!-- 视图切换 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="24">
        <el-radio-group v-model="viewMode" @change="handleViewModeChange">
          <el-radio-button label="list">列表视图</el-radio-button>
          <el-radio-button label="calendar">日历视图</el-radio-button>
        </el-radio-group>
      </el-col>
    </el-row>

    <!-- 列表视图 -->
    <div v-show="viewMode === 'list'">
      <!-- 搜索表单 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
        <el-form-item label="日历编码" prop="calendarCode">
          <el-input v-model="queryParams.calendarCode" placeholder="请输入日历编码" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="日历名称" prop="calendarName">
          <el-input v-model="queryParams.calendarName" placeholder="请输入日历名称" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="日历类型" prop="calendarType">
          <el-select v-model="queryParams.calendarType" placeholder="请选择日历类型" clearable>
            <el-option v-for="dict in dict.type.production_calendar_type" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option v-for="dict in dict.type.production_calendar_status" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否启用" prop="isActive">
          <el-select v-model="queryParams.isActive" placeholder="请选择是否启用" clearable>
            <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['erp:productionCalendar:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:productionCalendar:edit']">修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:productionCalendar:remove']">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="info" plain icon="el-icon-copy-document" size="mini" :disabled="single" @click="handleCopy" v-hasPermi="['erp:productionCalendar:add']">复制</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['erp:productionCalendar:export']">导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <!-- 数据表格 -->
      <el-table v-loading="loading" :data="productionCalendarList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="日历编码" align="center" prop="calendarCode" />
        <el-table-column label="日历名称" align="center" prop="calendarName" />
        <el-table-column label="日历类型" align="center" prop="calendarType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.production_calendar_type" :value="scope.row.calendarType"/>
          </template>
        </el-table-column>
        <el-table-column label="年份" align="center" prop="year" />
        <el-table-column label="月份" align="center" prop="month" />
        <el-table-column label="周次" align="center" prop="week" />
        <el-table-column label="生效开始日期" align="center" prop="effectiveStartDate" width="120">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.effectiveStartDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="生效结束日期" align="center" prop="effectiveEndDate" width="120">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.effectiveEndDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="总工作日数" align="center" prop="totalWorkDays" />
        <el-table-column label="总工作小时数" align="center" prop="totalWorkHours" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.production_calendar_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="是否启用" align="center" prop="isActive">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.isActive" active-value="1" inactive-value="0" active-color="#13ce66" inactive-color="#ff4949" @change="handleStatusChange(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['erp:productionCalendar:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['erp:productionCalendar:remove']">删除</el-button>
            <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)" v-hasPermi="['erp:productionCalendar:query']">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
    </div>

    <!-- 日历视图 -->
    <div v-show="viewMode === 'calendar'" class="calendar-view">
      <!-- 日历导航 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="24">
          <div class="calendar-navigation">
            <el-button-group>
              <el-button icon="el-icon-arrow-left" @click="previousMonth"></el-button>
              <el-button icon="el-icon-arrow-right" @click="nextMonth"></el-button>
            </el-button-group>
            <span class="current-month">{{ currentYear }}年{{ currentMonth }}月</span>
            <el-button @click="goToToday">今天</el-button>
          </div>
        </el-col>
      </el-row>

      <!-- 日历主体 -->
      <div class="calendar-container">
        <!-- 星期标题 -->
        <div class="calendar-header">
          <div class="calendar-cell header-cell" v-for="day in weekDays" :key="day">{{ day }}</div>
        </div>
        
        <!-- 日历网格 -->
        <div class="calendar-grid">
          <div v-for="date in calendarDates" :key="date.key" class="calendar-cell date-cell" :class="{'other-month': !date.isCurrentMonth, 'today': date.isToday, 'has-calendar': date.hasCalendar, 'work-day': date.isWorkDay}" @click="handleDateClick(date)">
            <div class="date-number">{{ date.day }}</div>
            <div v-if="date.hasCalendar" class="calendar-indicator">
              <el-tag size="mini" type="success">{{ date.calendarCount }}个日历</el-tag>
            </div>
            <div v-if="date.isWorkDay" class="work-day-indicator">
              <i class="el-icon-time"></i>
            </div>
          </div>
        </div>
      </div>

      <!-- 日期详情弹窗 -->
      <el-dialog title="日期详情" :visible.sync="dateDetailVisible" width="600px" append-to-body>
        <div v-if="selectedDate">
          <h4>{{ selectedDate.fullDate }}</h4>
          <div v-if="selectedDate.calendars && selectedDate.calendars.length > 0">
            <el-card v-for="calendar in selectedDate.calendars" :key="calendar.id" class="mb8">
              <div slot="header">
                <span>{{ calendar.calendarName }}</span>
                <el-tag size="mini" :type="getStatusType(calendar.status)" style="margin-left: 8px;">{{ getStatusLabel(calendar.status) }}</el-tag>
              </div>
              <div class="calendar-detail">
                <p><strong>日历类型：</strong>{{ getCalendarTypeLabel(calendar.calendarType) }}</p>
                <p><strong>状态：</strong>{{ getStatusLabel(calendar.status) }}</p>
                <p><strong>是否启用：</strong>{{ calendar.isActive === '1' ? '启用' : '停用' }}</p>
              </div>
            </el-card>
          </div>
          <div v-else>
            <el-empty description="该日期暂无生产日历"></el-empty>
          </div>
        </div>
      </el-dialog>
    </div>

    <!-- 添加或修改生产日历对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-tabs v-model="activeTab" type="border-card" class="production-calendar-tabs">
          <!-- 基本信息Tab -->
          <el-tab-pane label="基本信息" name="basic">
            <div class="tab-content">
              <el-row :gutter="16">
                <el-col :span="12">
                  <el-form-item label="日历编码" prop="calendarCode">
                    <el-input v-model="form.calendarCode" placeholder="请输入日历编码" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="日历名称" prop="calendarName">
                    <el-input v-model="form.calendarName" placeholder="请输入日历名称" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="16">
                <el-col :span="12">
                  <el-form-item label="日历类型" prop="calendarType">
                    <el-select v-model="form.calendarType" placeholder="请选择日历类型" style="width: 100%">
                      <el-option v-for="dict in dict.type.production_calendar_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="16">
                <el-col :span="12">
                  <el-form-item label="年份" prop="year">
                    <el-input-number v-model="form.year" :min="2020" :max="2030" style="width: 100%" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="月份" prop="month">
                    <el-input-number v-model="form.month" :min="1" :max="12" style="width: 100%" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="16">
                <el-col :span="12">
                  <el-form-item label="周次" prop="week">
                    <el-input-number v-model="form.week" :min="1" :max="53" style="width: 100%" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="状态" prop="status">
                    <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                      <el-option v-for="dict in dict.type.production_calendar_status" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="16">
                <el-col :span="12">
                  <el-form-item label="生效开始日期" prop="effectiveStartDate">
                    <el-date-picker v-model="form.effectiveStartDate" type="date" placeholder="选择生效开始日期" style="width: 100%" value-format="yyyy-MM-dd" @change="calculateWorkDays" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="生效结束日期" prop="effectiveEndDate">
                    <el-date-picker v-model="form.effectiveEndDate" type="date" placeholder="选择生效结束日期" style="width: 100%" value-format="yyyy-MM-dd" @change="calculateWorkDays" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="16">
                <el-col :span="12">
                  <el-form-item label="是否启用" prop="isActive">
                    <el-switch v-model="form.isActive" active-value="1" inactive-value="0" active-color="#13ce66" inactive-color="#ff4949" active-text="启用" inactive-text="停用"></el-switch>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="总工作日数" prop="totalWorkDays">
                    <el-input v-model="form.totalWorkDays" readonly />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="16">
                <el-col :span="12">
                  <el-form-item label="总工作小时数" prop="totalWorkHours">
                    <el-input v-model="form.totalWorkHours" readonly />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注信息" />
              </el-form-item>
            </div>
          </el-tab-pane>

          <!-- 班次配置Tab -->
          <el-tab-pane label="班次配置" name="shift">
            <div class="tab-content">
              <el-row :gutter="16">
                <el-col :span="24">
                  <el-button type="primary" @click="addShiftConfig">添加班次</el-button>
                </el-col>
              </el-row>
              <el-table :data="shiftConfigs" style="margin-top: 16px;">
                <el-table-column label="班次类型" prop="shiftType" width="120">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.shiftType" placeholder="请选择班次类型">
                      <el-option label="早班" value="1"></el-option>
                      <el-option label="中班" value="2"></el-option>
                      <el-option label="晚班" value="3"></el-option>
                      <el-option label="全天" value="4"></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column label="开始时间" prop="shiftStartTime" width="150">
                  <template slot-scope="scope">
                    <el-time-picker v-model="scope.row.shiftStartTime" placeholder="选择开始时间" value-format="HH:mm:ss" style="width: 100%" />
                  </template>
                </el-table-column>
                <el-table-column label="结束时间" prop="shiftEndTime" width="150">
                  <template slot-scope="scope">
                    <el-time-picker v-model="scope.row.shiftEndTime" placeholder="选择结束时间" value-format="HH:mm:ss" style="width: 100%" />
                  </template>
                </el-table-column>
                <el-table-column label="工作小时数" prop="workHours" width="120">
                  <template slot-scope="scope">
                    <el-input-number v-model="scope.row.workHours" :min="0" :max="24" :precision="2" @change="calculateWorkHours" />
                  </template>
                </el-table-column>
                <el-table-column label="休息小时数" prop="breakHours" width="120">
                  <template slot-scope="scope">
                    <el-input-number v-model="scope.row.breakHours" :min="0" :max="24" :precision="2" />
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="100">
                  <template slot-scope="scope">
                    <el-button type="text" @click="removeShiftConfig(scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>

          <!-- 工作日设置Tab -->
          <el-tab-pane label="工作日设置" name="workday">
            <div class="tab-content">
              <el-row :gutter="16">
                <el-col :span="24">
                  <el-checkbox-group v-model="workDays">
                    <el-checkbox label="1">周一</el-checkbox>
                    <el-checkbox label="2">周二</el-checkbox>
                    <el-checkbox label="3">周三</el-checkbox>
                    <el-checkbox label="4">周四</el-checkbox>
                    <el-checkbox label="5">周五</el-checkbox>
                    <el-checkbox label="6">周六</el-checkbox>
                    <el-checkbox label="7">周日</el-checkbox>
                  </el-checkbox-group>
                </el-col>
              </el-row>
              <el-row :gutter="16" style="margin-top: 16px;">
                <el-col :span="24">
                  <el-alert title="工作日设置说明" type="info" :closable="false" show-icon>
                    <div>1. 选择的工作日将自动计算工作日数和工时</div>
                    <div>2. 非工作日将不计入工作日数和工时统计</div>
                    <div>3. 班次配置将应用到所有工作日</div>
                  </el-alert>
                </el-col>
              </el-row>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listProductionCalendar, getProductionCalendar, delProductionCalendar, addProductionCalendar, updateProductionCalendar, generateProductionCalendar, copyProductionCalendar } from "@/api/erp/productionCalendar";

export default {
  name: "ProductionCalendar",
  dicts: ['production_calendar_type', 'production_calendar_status', 'sys_yes_no'],
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
      // 生产日历表格数据
      productionCalendarList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        calendarCode: null,
        calendarName: null,
        calendarType: null,
        status: null,
        isActive: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        calendarCode: [{ required: true, message: "日历编码不能为空", trigger: "blur" }],
        calendarName: [{ required: true, message: "日历名称不能为空", trigger: "blur" }],
        calendarType: [{ required: true, message: "日历类型不能为空", trigger: "change" }],
        effectiveStartDate: [{ required: true, message: "生效开始日期不能为空", trigger: "change" }],
        effectiveEndDate: [{ required: true, message: "生效结束日期不能为空", trigger: "change" }]
      },
      // 当前激活的Tab
      activeTab: "basic",
      // 班次配置
      shiftConfigs: [],
      // 工作日设置
      workDays: ["1", "2", "3", "4", "5"],
      // 视图模式
      viewMode: "list",
      // 当前年份
      currentYear: new Date().getFullYear(),
      // 当前月份
      currentMonth: new Date().getMonth() + 1,
      // 星期标题
      weekDays: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
      // 日历数据
      calendarDates: [],
      // 日期详情弹窗
      dateDetailVisible: false,
      // 选中的日期
      selectedDate: null
    };
  },
  created() {
    this.getList();
    this.generateCalendar();
  },
  methods: {
    /** 查询生产日历列表 */
    getList() {
      this.loading = true;
      listProductionCalendar(this.queryParams).then(response => {
        this.productionCalendarList = response.rows;
        this.total = response.total;
        this.loading = false;
        if (this.viewMode === 'calendar') {
          this.generateCalendar();
        }
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
        calendarCode: null,
        calendarName: null,
        calendarType: null,
        year: new Date().getFullYear(),
        month: new Date().getMonth() + 1,
        week: null,
        effectiveStartDate: null,
        effectiveEndDate: null,
        totalWorkDays: 0,
        totalWorkHours: 0,
        status: "1",
        isActive: "1",
        remark: null
      };
      this.shiftConfigs = [];
      this.workDays = ["1", "2", "3", "4", "5"];
      this.activeTab = "basic";
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
      this.title = "添加生产日历";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProductionCalendar(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改生产日历";
      });
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset();
      const id = row.id || this.ids
      getProductionCalendar(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看生产日历";
        this.$nextTick(() => {
          this.$refs.form.disabled = true;
        });
      });
    },
    /** 复制按钮操作 */
    handleCopy(row) {
      this.reset();
      const id = row.id || this.ids
      getProductionCalendar(id).then(response => {
        this.form = response.data;
        this.form.id = null;
        this.form.calendarCode = this.form.calendarCode + "_copy";
        this.form.calendarName = this.form.calendarName + "_复制";
        this.form.status = "1";
        this.open = true;
        this.title = "复制生产日历";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProductionCalendar(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductionCalendar(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除生产日历编号为"' + ids + '"的数据项？').then(function() {
        return delProductionCalendar(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/productionCalendar/export', {
        ...this.queryParams
      }, `production_calendar_${new Date().getTime()}.xlsx`)
    },
    /** 状态修改 */
    handleStatusChange(row) {
      let text = row.isActive === "1" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.calendarName + '"吗？').then(function() {
        return updateProductionCalendar(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.isActive = row.isActive === "0" ? "1" : "0";
      });
    },
    /** 添加班次配置 */
    addShiftConfig() {
      this.shiftConfigs.push({
        shiftType: "1",
        shiftStartTime: null,
        shiftEndTime: null,
        workHours: 8,
        breakHours: 1
      });
      this.calculateWorkHours();
    },
    /** 删除班次配置 */
    removeShiftConfig(index) {
      this.shiftConfigs.splice(index, 1);
      this.calculateWorkHours();
    },
    /** 计算总工作日数 */
    calculateWorkDays() {
      if (!this.form.effectiveStartDate || !this.form.effectiveEndDate) {
        this.form.totalWorkDays = 0;
        this.form.totalWorkHours = 0;
        return;
      }
      
      const startDate = new Date(this.form.effectiveStartDate);
      const endDate = new Date(this.form.effectiveEndDate);
      
      if (startDate > endDate) {
        this.$message.warning('生效开始日期不能大于生效结束日期');
        this.form.totalWorkDays = 0;
        this.form.totalWorkHours = 0;
        return;
      }
      
      let workDays = 0;
      const currentDate = new Date(startDate);
      
      while (currentDate <= endDate) {
        const dayOfWeek = currentDate.getDay(); // 0=周日, 1=周一, ..., 6=周六
        // 检查是否为工作日（周一到周五）
        if (this.workDays.includes(dayOfWeek.toString())) {
          workDays++;
        }
        currentDate.setDate(currentDate.getDate() + 1);
      }
      
      this.form.totalWorkDays = workDays;
      this.calculateWorkHours();
    },
    /** 计算总工作小时数 */
    calculateWorkHours() {
      if (!this.form.totalWorkDays || this.shiftConfigs.length === 0) {
        this.form.totalWorkHours = 0;
        return;
      }
      
      let totalWorkHours = 0;
      this.shiftConfigs.forEach(shift => {
        if (shift.workHours) {
          totalWorkHours += parseFloat(shift.workHours) || 0;
        }
      });
      
      // 总工作小时数 = 总工作日数 × 每日工作小时数
      this.form.totalWorkHours = Math.round(this.form.totalWorkDays * totalWorkHours * 100) / 100;
    },
    /** 视图模式切换 */
    handleViewModeChange(mode) {
      this.viewMode = mode;
      if (mode === 'calendar') {
        this.generateCalendar();
      }
    },
    /** 上个月 */
    previousMonth() {
      if (this.currentMonth === 1) {
        this.currentMonth = 12;
        this.currentYear--;
      } else {
        this.currentMonth--;
      }
      this.generateCalendar();
    },
    /** 下个月 */
    nextMonth() {
      if (this.currentMonth === 12) {
        this.currentMonth = 1;
        this.currentYear++;
      } else {
        this.currentMonth++;
      }
      this.generateCalendar();
    },
    /** 跳转到今天 */
    goToToday() {
      const today = new Date();
      this.currentYear = today.getFullYear();
      this.currentMonth = today.getMonth() + 1;
      this.generateCalendar();
    },
    /** 生成日历数据 */
    generateCalendar() {
      const firstDay = new Date(this.currentYear, this.currentMonth - 1, 1);
      const lastDay = new Date(this.currentYear, this.currentMonth, 0);
      const startDate = new Date(firstDay);
      startDate.setDate(startDate.getDate() - firstDay.getDay());
      
      const endDate = new Date(lastDay);
      endDate.setDate(endDate.getDate() + (6 - lastDay.getDay()));
      
      const dates = [];
      const currentDate = new Date(startDate);
      
      while (currentDate <= endDate) {
        const dateKey = this.formatDate(currentDate);
        const isCurrentMonth = currentDate.getMonth() === this.currentMonth - 1;
        const isToday = this.isToday(currentDate);
        const dayOfWeek = currentDate.getDay();
        const isWorkDay = dayOfWeek >= 1 && dayOfWeek <= 5; // 周一到周五为工作日
        
        // 查找该日期的生产日历
        const calendars = this.findCalendarsForDate(dateKey);
        
        dates.push({
          key: dateKey,
          day: currentDate.getDate(),
          fullDate: dateKey,
          isCurrentMonth,
          isToday,
          dayOfWeek,
          isWorkDay,
          hasCalendar: calendars.length > 0,
          calendarCount: calendars.length,
          calendars
        });
        
        currentDate.setDate(currentDate.getDate() + 1);
      }
      
      this.calendarDates = dates;
    },
    /** 格式化日期 */
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },
    /** 判断是否为今天 */
    isToday(date) {
      const today = new Date();
      return date.getDate() === today.getDate() &&
             date.getMonth() === today.getMonth() &&
             date.getFullYear() === today.getFullYear();
    },
    /** 查找指定日期的生产日历 */
    findCalendarsForDate(dateStr) {
      return this.productionCalendarList.filter(calendar => {
        if (calendar.effectiveStartDate && calendar.effectiveEndDate) {
          const startDate = this.formatDate(new Date(calendar.effectiveStartDate));
          const endDate = this.formatDate(new Date(calendar.effectiveEndDate));
          return dateStr >= startDate && dateStr <= endDate;
        }
        return false;
      });
    },
    /** 日期点击事件 */
    handleDateClick(date) {
      this.selectedDate = date;
      this.dateDetailVisible = true;
    },
    /** 获取状态类型 */
    getStatusType(status) {
      const statusMap = {
        '1': 'info',
        '2': 'success',
        '3': 'danger'
      };
      return statusMap[status] || 'info';
    },
    /** 获取状态标签 */
    getStatusLabel(status) {
      const statusMap = {
        '1': '草稿',
        '2': '生效',
        '3': '过期'
      };
      return statusMap[status] || '未知';
    },
    /** 获取日历类型标签 */
    getCalendarTypeLabel(type) {
      const typeMap = {
        '1': '年度',
        '2': '季度',
        '3': '月度',
        '4': '周度',
        '5': '日度'
      };
      return typeMap[type] || '未知';
    }
  }
};
</script>

<style scoped>
.production-calendar-tabs .el-tabs__header {
  margin-bottom: 10px;
}

.production-calendar-tabs .el-tabs__item {
  height: 32px;
  line-height: 32px;
}

.tab-content {
  padding: 10px 0;
}

.tab-content .el-row {
  margin-bottom: 8px;
}

.tab-content .el-col {
  margin-bottom: 4px;
}

.el-dialog .el-form-item {
  margin-bottom: 8px;
}

.dialog-footer {
  padding-top: 10px;
}

.el-row {
  margin-bottom: 10px;
}

.el-col {
  margin-bottom: 5px;
}

/* 日历视图样式 */
.calendar-view {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.calendar-navigation {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  gap: 20px;
}

.current-month {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
  min-width: 120px;
  text-align: center;
}

.calendar-container {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  overflow: hidden;
}

.calendar-header {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  background: #f5f7fa;
  border-bottom: 1px solid #e4e7ed;
}

.header-cell {
  padding: 12px;
  text-align: center;
  font-weight: bold;
  color: #606266;
  border-right: 1px solid #e4e7ed;
}

.header-cell:last-child {
  border-right: none;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
}

.calendar-cell {
  min-height: 80px;
  border-right: 1px solid #e4e7ed;
  border-bottom: 1px solid #e4e7ed;
  padding: 8px;
  position: relative;
  cursor: pointer;
  transition: all 0.3s;
}

.calendar-cell:hover {
  background: #f5f7fa;
}

.calendar-cell:nth-child(7n) {
  border-right: none;
}

.date-cell {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.date-number {
  font-size: 14px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 4px;
}

.other-month {
  background: #fafafa;
  color: #c0c4cc;
}

.other-month .date-number {
  color: #c0c4cc;
}

.today {
  background: #e6f7ff;
  border: 2px solid #1890ff;
}

.today .date-number {
  color: #1890ff;
}

.has-calendar {
  background: #f6ffed;
}

.work-day {
  background: #fff7e6;
}

.calendar-indicator {
  margin-top: auto;
  width: 100%;
}

.work-day-indicator {
  position: absolute;
  top: 4px;
  right: 4px;
  color: #faad14;
}

.calendar-detail p {
  margin: 4px 0;
  font-size: 14px;
}

.calendar-detail strong {
  color: #303133;
}

.mb8 {
  margin-bottom: 8px;
}
</style>
