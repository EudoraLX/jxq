<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="6">
        <el-card class="statistics-card" shadow="hover">
          <div slot="header" class="card-header">
            <span class="header-title">
              <i class="el-icon-data-board"></i>
              消息统计
            </span>
          </div>
          <div class="statistics-container">
            <div class="stat-item clickable" @click="filterMessages('all')" :class="{ 'active': currentFilter === 'all' }">
              <div class="stat-icon total">
                <i class="el-icon-message"></i>
              </div>
              <div class="stat-content">
                <div class="stat-number">{{ statistics.totalMessages }}</div>
                <div class="stat-label">全部消息</div>
              </div>
            </div>
            <div class="stat-item clickable" @click="filterMessages('unread')" :class="{ 'active': currentFilter === 'unread' }">
              <div class="stat-icon unread">
                <i class="el-icon-bell"></i>
              </div>
              <div class="stat-content">
                <div class="stat-number">{{ statistics.unreadMessages }}</div>
                <div class="stat-label">未读消息</div>
              </div>
            </div>
            <div class="stat-item clickable" @click="filterMessages('read')" :class="{ 'active': currentFilter === 'read' }">
              <div class="stat-icon read">
                <i class="el-icon-check"></i>
              </div>
              <div class="stat-content">
                <div class="stat-number">{{ statistics.readMessages }}</div>
                <div class="stat-label">已读消息</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card class="message-card" shadow="hover">
          <div slot="header" class="card-header">
            <span class="header-title">
              <i class="el-icon-chat-dot-round"></i>
              我的消息
            </span>
            <el-button 
              type="primary" 
              @click="markAllAsRead" 
              :disabled="!hasUnreadMessages"
              icon="el-icon-check"
            >
              全部已读
            </el-button>
          </div>
          <el-table 
            v-loading="loading" 
            :data="filteredMessageList" 
            style="width: 100%"
            :row-class-name="getRowClassName"
            stripe
            :default-sort="{ prop: 'readStatus', order: 'ascending' }"
          >
            <el-table-column prop="title" label="标题" min-width="200">
              <template slot-scope="scope">
                <div class="message-title" @click="handlePreview(scope.row)" :class="{ 'clickable': true }">
                  <i v-if="scope.row.readStatus === '0'" class="el-icon-bell unread-icon"></i>
                  <span :class="{ 'unread': scope.row.readStatus === '0' }">{{ scope.row.title }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="content" label="内容" min-width="300" show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="message-content">{{ scope.row.content }}</div>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="时间" width="150">
              <template slot-scope="scope">
                <div class="message-time">{{ parseTime(scope.row.createTime) }}</div>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150" fixed="right">
              <template slot-scope="scope">
                <el-button
                  size="small"
                  type="primary"
                  @click="markAsRead(scope.row)"
                  v-if="scope.row.readStatus === '0'"
                  icon="el-icon-check"
                >
                  标记已读
                </el-button>
                <el-tag v-else type="success" size="small">已读</el-tag>
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
        </el-card>
      </el-col>
    </el-row>

    <!-- 消息预览对话框 -->
    <el-dialog 
      :visible.sync="previewOpen" 
      title="消息详情" 
      width="600px" 
      :close-on-click-modal="false"
      :close-on-press-escape="true"
    >
      <div class="message-preview">
        <div class="preview-header">
          <h3 class="preview-title">{{ previewMessage.title }}</h3>
          <div class="preview-meta">
            <el-tag :type="previewMessage.readStatus === '0' ? 'warning' : 'success'" size="small">
              {{ previewMessage.readStatus === '0' ? '未读' : '已读' }}
            </el-tag>
            <span class="preview-time">{{ parseTime(previewMessage.createTime) }}</span>
          </div>
        </div>
        <el-divider />
        <div class="preview-content">
          <div class="content-text">{{ previewMessage.content }}</div>
        </div>
        <div class="preview-footer">
          <div class="message-info">
            <span class="info-item">
              <strong>发送者：</strong>{{ previewMessage.senderName || '系统' }}
            </span>
            <span class="info-item">
              <strong>消息类型：</strong>
              <el-tag size="small" :type="getMessageTypeTag(previewMessage.messageType)">
                {{ getMessageTypeName(previewMessage.messageType) }}
              </el-tag>
            </span>
            <span class="info-item">
              <strong>消息级别：</strong>
              <el-tag size="small" :type="getMessageLevelTag(previewMessage.messageLevel)">
                {{ getMessageLevelName(previewMessage.messageLevel) }}
              </el-tag>
            </span>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="previewOpen = false">关闭</el-button>
        <el-button 
          v-if="previewMessage.readStatus === '0'" 
          type="primary" 
          @click="markAsReadFromPreview"
        >
          标记已读
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getMyMessageList, markAsRead, markAsReadBatch, getMyMessageStatistics } from "@/api/message/myMessage";

export default {
  name: "MyMessage",
  data() {
    return {
      loading: false,
      messageList: [],
      total: 0,
      statistics: {
        totalMessages: 0,
        unreadMessages: 0,
        readMessages: 0
      },
      // 预览相关
      previewOpen: false,
      previewMessage: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 当前过滤条件
      currentFilter: 'all' // 'all', 'unread', 'read'
    };
  },
  computed: {
    hasUnreadMessages() {
      return this.messageList.some(message => message.readStatus === '0');
    },
    // 过滤后的消息列表
    filteredMessageList() {
      let filtered = [...this.messageList];
      
      // 根据当前过滤条件过滤消息
      if (this.currentFilter === 'unread') {
        filtered = filtered.filter(message => message.readStatus === '0');
      } else if (this.currentFilter === 'read') {
        filtered = filtered.filter(message => message.readStatus === '1');
      }
      
      // 排序：未读在前，已读在后，然后按创建时间倒序
      filtered.sort((a, b) => {
        // 首先按阅读状态排序：未读(0)在前，已读(1)在后
        if (a.readStatus !== b.readStatus) {
          return a.readStatus === '0' ? -1 : 1;
        }
        // 状态相同时，按创建时间倒序排列（最新的在前）
        return new Date(b.createTime) - new Date(a.createTime);
      });
      
      return filtered;
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 获取表格行样式 */
    getRowClassName({ row }) {
      return row.readStatus === '0' ? 'unread-row' : 'read-row';
    },

    /** 处理消息预览 */
    handlePreview(row) {
      this.previewMessage = { ...row };
      this.previewOpen = true;
      
      // 如果是未读消息，自动标记为已读
      if (row.readStatus === '0') {
        this.markAsRead(row);
      }
    },

    /** 从预览对话框标记已读 */
    markAsReadFromPreview() {
      this.markAsRead(this.previewMessage);
      this.previewOpen = false;
    },

    /** 获取消息类型名称 */
    getMessageTypeName(type) {
      const typeMap = {
        '1': '系统消息',
        '2': '业务消息',
        '3': '通知消息'
      };
      return typeMap[type] || '未知类型';
    },

    /** 获取消息类型标签样式 */
    getMessageTypeTag(type) {
      const tagMap = {
        '1': 'danger',
        '2': 'primary',
        '3': 'warning'
      };
      return tagMap[type] || 'info';
    },

    /** 获取消息级别名称 */
    getMessageLevelName(level) {
      const levelMap = {
        '1': '普通',
        '2': '重要',
        '3': '紧急'
      };
      return levelMap[level] || '普通';
    },

    /** 获取消息级别标签样式 */
    getMessageLevelTag(level) {
      const tagMap = {
        '1': 'info',
        '2': 'warning',
        '3': 'danger'
      };
      return tagMap[level] || 'info';
    },

    /** 查询消息列表 */
    getList() {
      this.loading = true;
      getMyMessageList(this.queryParams).then(response => {
        this.messageList = response.rows;
        this.total = response.total;
        this.loading = false;
        // 获取列表后再更新统计
        this.getStatistics();
      }).catch(error => {
        console.error('获取用户消息列表失败:', error);
        this.$modal.msgError('获取消息列表失败');
        this.loading = false;
      });
    },

    /** 获取消息统计 */
    getStatistics() {
      getMyMessageStatistics().then(response => {
        this.statistics = {
          totalMessages: response.data.totalMessages || 0,
          unreadMessages: response.data.unreadMessages || 0,
          readMessages: response.data.readMessages || 0
        };
      }).catch(error => {
        console.error('获取消息统计失败:', error);
        this.statistics = {
          totalMessages: this.total,
          unreadMessages: 0,
          readMessages: this.total
        };
      });
    },

    /** 标记消息为已读 */
    markAsRead(row) {
      markAsRead(row.messageId).then(response => {
        this.$modal.msgSuccess('标记成功');
        row.readStatus = '1'; // 更新本地状态
        this.getStatistics();
      }).catch(error => {
        console.error('标记已读失败:', error);
        this.$modal.msgError('标记失败');
      });
    },

    /** 标记所有消息为已读 */
    markAllAsRead() {
      this.$modal.confirm('确定要标记所有消息为已读吗？').then(() => {
        // 获取所有未读消息的ID
        const unreadMessageIds = this.messageList
          .filter(message => message.readStatus === '0')
          .map(message => message.messageId);
        
        if (unreadMessageIds.length === 0) {
          this.$modal.msgWarning('没有未读消息');
          return;
        }
        
        markAsReadBatch(unreadMessageIds).then(response => {
          this.$modal.msgSuccess('标记成功');
          this.messageList.forEach(message => {
            message.readStatus = '1';
          });
          this.getStatistics();
        }).catch(error => {
          console.error('批量标记已读失败:', error);
          this.$modal.msgError('标记失败');
        });
      });
    },

    /** 过滤消息 */
    filterMessages(filterType) {
      this.currentFilter = filterType;
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
  background: #f5f7fa;
  min-height: calc(100vh - 84px);
}

.statistics-card {
  height: 100%;
  border-radius: 12px;
  border: none;
}

.message-card {
  border-radius: 12px;
  border: none;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
}

.header-title {
  display: flex;
  align-items: center;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.header-title i {
  margin-right: 8px;
  font-size: 18px;
  color: #409eff;
}

.statistics-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.stat-item {
  display: flex;
  align-items: center;
  padding: 16px;
  background: #f8f9fa;
  border-radius: 8px;
  transition: all 0.3s ease;
  cursor: pointer;
  border: 2px solid transparent;
}

.stat-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.stat-item.clickable {
  cursor: pointer;
}

.stat-item.active {
  border-color: #409eff;
  background: #ecf5ff;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  font-size: 24px;
  color: white;
}

.stat-icon.total {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.unread {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-icon.read {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 28px;
  font-weight: 700;
  color: #303133;
  line-height: 1;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  font-weight: 500;
}

.message-title {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.message-title.clickable:hover {
  color: #409eff;
  transform: translateX(2px);
}

.unread-icon {
  color: #f56c6c;
  font-size: 16px;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% { opacity: 1; }
  50% { opacity: 0.5; }
  100% { opacity: 1; }
}

.message-content {
  color: #606266;
  line-height: 1.5;
}

.message-time {
  color: #909399;
  font-size: 13px;
}

.unread {
  font-weight: 600;
  color: #303133;
}

.unread-row {
  background-color: #fdf6ec !important;
}

.unread-row:hover {
  background-color: #fdf6ec !important;
}

.read-row {
  background-color: #ffffff !important;
}

.read-row:hover {
  background-color: #f5f7fa !important;
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

::v-deep .el-table td {
  padding: 16px 0;
}

::v-deep .el-button {
  border-radius: 6px;
  font-weight: 500;
}

::v-deep .el-tag {
  border-radius: 6px;
}

/* 预览对话框样式 */
.message-preview {
  padding: 0;
}

.preview-header {
  margin-bottom: 16px;
}

.preview-title {
  margin: 0 0 12px 0;
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  line-height: 1.4;
}

.preview-meta {
  display: flex;
  align-items: center;
  gap: 12px;
  color: #909399;
  font-size: 14px;
}

.preview-time {
  color: #909399;
}

.preview-content {
  margin: 20px 0;
}

.content-text {
  line-height: 1.6;
  color: #606266;
  font-size: 14px;
  white-space: pre-wrap;
  word-break: break-word;
}

.preview-footer {
  margin-top: 20px;
  padding-top: 16px;
  border-top: 1px solid #ebeef5;
}

.message-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #606266;
}

.info-item strong {
  color: #303133;
  min-width: 80px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
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