<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    width="800px"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    @close="handleClose"
  >
    <div class="user-select-container">
      <!-- 搜索区域 -->
      <el-form :inline="true" class="search-form">
        <el-form-item label="用户名">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入用户名"
            clearable
            style="width: 200px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input
            v-model="queryParams.nickName"
            placeholder="请输入昵称"
            clearable
            style="width: 200px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 用户列表 -->
      <el-table
        ref="userTable"
        v-loading="loading"
        :data="userList"
        @selection-change="handleSelectionChange"
        height="400"
        stripe
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="用户名" prop="userName" width="120" />
        <el-table-column label="昵称" prop="nickName" width="120" />
        <el-table-column label="部门" prop="deptName" width="150" />
        <el-table-column label="手机号" prop="phonenumber" width="120" />
        <el-table-column label="邮箱" prop="email" :show-overflow-tooltip="true" />
      </el-table>

      <!-- 分页 -->
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <!-- 已选择的用户 -->
      <div v-if="selectedUsers.length > 0" class="selected-users">
        <div class="selected-title">已选择的用户 ({{ selectedUsers.length }})</div>
        <div class="selected-tags">
          <el-tag
            v-for="user in selectedUsers"
            :key="user.userId"
            closable
            @close="removeUser(user)"
            style="margin-right: 8px; margin-bottom: 8px;"
          >
            {{ user.nickName || user.userName }}
          </el-tag>
        </div>
      </div>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listUser } from "@/api/system/user";

export default {
  name: "UserMultiSelect",
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: "选择用户"
    },
    selectedUserIds: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: [],
      // 选中的用户
      selectedUsers: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        nickName: null,
        status: '0' // 只查询正常状态的用户
      }
    };
  },
  computed: {
    dialogVisible: {
      get() {
        return this.visible;
      },
      set(val) {
        this.$emit('update:visible', val);
      }
    }
  },
  watch: {
    visible(val) {
      if (val) {
        this.reset();
        this.getList();
      }
    },
    selectedUserIds: {
      handler(val) {
        this.selectedUsers = [...val];
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.queryParams).then(response => {
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
        
        // 设置已选中的用户
        this.$nextTick(() => {
          this.setSelectedRows();
        });
      });
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
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      // 合并已选择的用户和新选择的用户
      const newSelectedUsers = [...this.selectedUsers];
      
      selection.forEach(user => {
        const exists = newSelectedUsers.find(u => u.userId === user.userId);
        if (!exists) {
          newSelectedUsers.push(user);
        }
      });
      
      this.selectedUsers = newSelectedUsers;
    },
    /** 设置已选中的行 */
    setSelectedRows() {
      if (this.selectedUsers.length > 0) {
        this.userList.forEach(user => {
          const isSelected = this.selectedUsers.some(selected => selected.userId === user.userId);
          if (isSelected) {
            this.$refs.userTable.toggleRowSelection(user, true);
          }
        });
      }
    },
    /** 移除用户 */
    removeUser(user) {
      const index = this.selectedUsers.findIndex(u => u.userId === user.userId);
      if (index > -1) {
        this.selectedUsers.splice(index, 1);
      }
      
      // 取消表格中的选中状态
      const tableUser = this.userList.find(u => u.userId === user.userId);
      if (tableUser) {
        this.$refs.userTable.toggleRowSelection(tableUser, false);
      }
    },
    /** 确认选择 */
    handleConfirm() {
      this.$emit('confirm', this.selectedUsers);
      this.handleClose();
    },
    /** 关闭对话框 */
    handleClose() {
      this.dialogVisible = false;
    },
    /** 重置表单 */
    reset() {
      this.selectedUsers = [...this.selectedUserIds];
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        nickName: null,
        status: '0'
      };
    }
  }
};
</script>

<style scoped>
.user-select-container {
  padding: 0;
}

.search-form {
  margin-bottom: 20px;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
}

.selected-users {
  margin-top: 20px;
  padding: 16px;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

.selected-title {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 12px;
}

.selected-tags {
  min-height: 32px;
}

::v-deep .el-table {
  border-radius: 8px;
  overflow: hidden;
}

::v-deep .el-table th {
  background-color: #fafafa;
  color: #606266;
  font-weight: 600;
}

::v-deep .el-button {
  border-radius: 6px;
  font-weight: 500;
}

::v-deep .el-tag {
  border-radius: 6px;
}

::v-deep .el-dialog {
  border-radius: 12px;
}

::v-deep .el-dialog__header {
  border-bottom: 1px solid #ebeef5;
  padding: 20px 24px 16px;
}

::v-deep .el-dialog__body {
  padding: 24px;
}

::v-deep .el-dialog__footer {
  border-top: 1px solid #ebeef5;
  padding: 16px 24px 20px;
}
</style>
