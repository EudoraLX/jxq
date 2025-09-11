<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="消息标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入消息标题"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消息类型" prop="messageType">
        <el-select v-model="queryParams.messageType" placeholder="消息类型" clearable style="width: 240px">
          <el-option
            v-for="dict in dict.type.message_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="消息状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="消息状态" clearable style="width: 240px">
          <el-option
            v-for="dict in dict.type.message_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="阅读状态" prop="readStatus">
        <el-select v-model="queryParams.readStatus" placeholder="阅读状态" clearable style="width: 240px">
          <el-option label="未读" value="0" />
          <el-option label="已读" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          @click="handleAdd"
          v-hasPermi="['message:message:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['message:message:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-s-promotion"
          :disabled="single"
          @click="handleSend"
          v-hasPermi="['message:message:send']"
        >发送</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-bell"
          :disabled="single"
          @click="handlePush"
          v-hasPermi="['message:message:push']"
        >推送</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['message:message:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          @click="handleExport"
          v-hasPermi="['message:message:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="messageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="消息标题" align="center" prop="title" :show-overflow-tooltip="true" />
      <el-table-column label="消息类型" align="center" prop="messageType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.message_type" :value="scope.row.messageType"/>
        </template>
      </el-table-column>
      <el-table-column label="消息状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.message_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="阅读状态" align="center" prop="readStatus">
        <template slot-scope="scope">
          <el-tag :type="scope.row.readStatus === '1' ? 'success' : 'warning'">
            {{ scope.row.readStatus === '1' ? '已读' : '未读' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="推送状态" align="center" prop="pushStatus">
        <template slot-scope="scope">
          <el-tag :type="scope.row.pushStatus === '1' ? 'success' : 'info'">
            {{ scope.row.pushStatus === '1' ? '已推送' : '未推送' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发送者" align="center" prop="senderName" />
      <el-table-column label="接收者" align="center" prop="receiverNames" :show-overflow-tooltip="true" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['message:message:query']"
          >查看</el-button>
          <el-button
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['message:message:edit']"
          >修改</el-button>
          <el-button
            type="text"
            icon="el-icon-s-promotion"
            @click="handleSend(scope.row)"
            v-hasPermi="['message:message:send']"
            v-if="scope.row.status === '0'"
          >发送</el-button>
          <el-button
            type="text"
            icon="el-icon-bell"
            @click="handlePush(scope.row)"
            v-hasPermi="['message:message:push']"
            v-if="scope.row.status === '1'"
          >推送</el-button>
          <el-button
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['message:message:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改消息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="消息标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入消息标题" />
        </el-form-item>
        <el-form-item label="消息内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入消息内容" />
        </el-form-item>
        <el-form-item label="消息类型" prop="messageType">
          <el-select v-model="form.messageType" placeholder="请选择消息类型">
            <el-option
              v-for="dict in dict.type.message_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="消息级别" prop="messageLevel">
          <el-select v-model="form.messageLevel" placeholder="请选择消息级别">
            <el-option
              v-for="dict in dict.type.message_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="消息状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择消息状态">
            <el-option
              v-for="dict in dict.type.message_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="接收者" prop="receivers">
          <div style="display: flex; align-items: center; gap: 10px;">
            <el-input 
              v-model="receiverDisplayText" 
              placeholder="请选择接收者" 
              readonly 
              style="flex: 1;"
            />
            <el-button type="primary" @click="showUserSelect = true">选择用户</el-button>
          </div>
          <!-- 已选择的用户标签 -->
          <div v-if="form.receivers && form.receivers.length > 0" style="margin-top: 10px;">
            <el-tag
              v-for="user in form.receivers"
              :key="user.userId"
              closable
              @close="removeReceiver(user)"
              style="margin-right: 8px; margin-bottom: 8px;"
            >
              {{ user.nickName || user.userName }}
            </el-tag>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户选择对话框 -->
    <UserMultiSelect
      :visible.sync="showUserSelect"
      title="选择消息接收者"
      :selectedUserIds="form.receivers"
      @confirm="handleUserSelect"
    />

    <!-- 查看消息对话框 -->
    <el-dialog title="查看消息" :visible.sync="viewOpen" width="600px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="消息标题">{{ viewForm.title }}</el-descriptions-item>
        <el-descriptions-item label="消息类型">
          <dict-tag :options="dict.type.message_type" :value="viewForm.messageType"/>
        </el-descriptions-item>
        <el-descriptions-item label="消息状态">
          <dict-tag :options="dict.type.message_status" :value="viewForm.status"/>
        </el-descriptions-item>
        <el-descriptions-item label="阅读状态">
          <el-tag :type="viewForm.readStatus === '1' ? 'success' : 'warning'">
            {{ viewForm.readStatus === '1' ? '已读' : '未读' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="推送状态">
          <el-tag :type="viewForm.pushStatus === '1' ? 'success' : 'info'">
            {{ viewForm.pushStatus === '1' ? '已推送' : '未推送' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="发送者">{{ viewForm.senderName }}</el-descriptions-item>
        <el-descriptions-item label="接收者" :span="2">{{ viewForm.receiverNames }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">
          {{ parseTime(viewForm.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}
        </el-descriptions-item>
        <el-descriptions-item label="消息内容" :span="2">
          <div style="white-space: pre-wrap;">{{ viewForm.content }}</div>
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="viewOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMessage, getMessage, delMessage, addMessage, updateMessage, sendMessage, pushMessage } from "@/api/message/message";
import UserMultiSelect from "@/components/UserMultiSelect/index.vue";

export default {
  name: "Message",
  dicts: ['message_type', 'message_level', 'message_status'],
  components: {
    UserMultiSelect
  },
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
      // 消息表格数据
      messageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示查看弹出层
      viewOpen: false,
      // 是否显示用户选择弹出层
      showUserSelect: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        messageType: null,
        status: null,
        readStatus: null
      },
      // 表单参数
      form: {},
      // 查看表单参数
      viewForm: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "消息标题不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "消息内容不能为空", trigger: "blur" }
        ],
        messageType: [
          { required: true, message: "消息类型不能为空", trigger: "change" }
        ],
        messageLevel: [
          { required: true, message: "消息级别不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        receivers: [
          { required: true, message: "请选择接收者", trigger: "change" }
        ]
      }
    };
  },
  computed: {
    // 接收者显示文本
    receiverDisplayText() {
      if (!this.form.receivers || this.form.receivers.length === 0) {
        return '';
      }
      return `已选择 ${this.form.receivers.length} 个用户`;
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询消息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.dateRange && '' != this.dateRange) {
        this.queryParams.params["beginCreateTime"] = this.dateRange[0];
        this.queryParams.params["endCreateTime"] = this.dateRange[1];
      }
      listMessage(this.queryParams).then(response => {
        this.messageList = response.rows;
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
        messageId: null,
        title: null,
        content: null,
        messageType: null,
        messageLevel: null,
        status: "0",
        receivers: []
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
      this.dateRange = [];
      this.resetForm("queryRef");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.messageId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加消息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const messageId = row.messageId || this.ids
      getMessage(messageId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改消息";
      });
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.viewForm = { ...row };
      this.viewOpen = true;
    },
    /** 发送按钮操作 */
    handleSend(row) {
      const messageId = row.messageId || this.ids[0];
      this.$modal.confirm('是否确认发送该消息？').then(function() {
        return sendMessage(messageId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("发送成功");
      }).catch(() => {});
    },
    /** 推送按钮操作 */
    handlePush(row) {
      const messageId = row.messageId || this.ids[0];
      this.$modal.confirm('是否确认推送该消息？').then(function() {
        return pushMessage(messageId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("推送成功");
      }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 转换接收者数据格式
          const formData = { ...this.form };
          if (formData.receivers && formData.receivers.length > 0) {
            formData.receivers = formData.receivers.map(user => ({
              receiverId: user.userId,
              receiverName: user.nickName || user.userName
            }));
          }
          
          if (this.form.messageId != null) {
            updateMessage(formData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMessage(formData).then(response => {
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
      const messageIds = row.messageId || this.ids;
      this.$modal.confirm('是否确认删除消息编号为"' + messageIds + '"的数据项？').then(function() {
        return delMessage(messageIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('message/message/export', {
        ...this.queryParams
      }, `message_${new Date().getTime()}.xlsx`)
    },
    /** 用户选择确认 */
    handleUserSelect(selectedUsers) {
      this.form.receivers = selectedUsers;
      this.showUserSelect = false;
    },
    /** 移除接收者 */
    removeReceiver(user) {
      const index = this.form.receivers.findIndex(item => item.userId === user.userId);
      if (index > -1) {
        this.form.receivers.splice(index, 1);
      }
    }
  }
};
</script>