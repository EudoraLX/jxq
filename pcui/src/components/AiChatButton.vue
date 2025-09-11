<template>
  <div class="ai-chat-button" :class="{ 'is-open': isOpen }">
    <!-- 悬浮按钮 -->
    <div 
      class="chat-button" 
      @click="toggleChat"
      :class="{ 'has-unread': unreadCount > 0 }"
    >
      <i class="el-icon-chat-dot-round" v-if="!isOpen"></i>
      <i class="el-icon-close" v-else></i>
      <span v-if="unreadCount > 0" class="unread-badge">{{ unreadCount > 99 ? '99+' : unreadCount }}</span>
    </div>

    <!-- 聊天窗口 -->
    <div class="chat-window" v-show="isOpen">
      <div class="chat-header">
        <div class="chat-title">
          <i class="el-icon-chat-dot-round"></i>
          AI智能客服
        </div>
        <div class="chat-actions">
          <el-button 
            type="text" 
            icon="el-icon-refresh" 
            @click="refreshSessions"
            size="mini"
          ></el-button>
          <el-button 
            type="text" 
            icon="el-icon-plus" 
            @click="createNewSession"
            size="mini"
          ></el-button>
        </div>
      </div>

      <div class="chat-content">
        <!-- 会话列表 -->
        <div class="session-list" v-if="!currentSessionId">
          <div 
            class="session-item" 
            v-for="session in sessions" 
            :key="session.sessionId"
            @click="selectSession(session)"
            :class="{ 'active': currentSessionId === session.sessionId }"
          >
            <div class="session-info">
              <div class="session-name">{{ session.sessionName }}</div>
              <div class="session-time">{{ formatTime(session.lastMessageTime) }}</div>
            </div>
            <div class="session-unread" v-if="session.unreadCount > 0">
              {{ session.unreadCount }}
            </div>
          </div>
        </div>

        <!-- 聊天界面 -->
        <div class="chat-interface" v-else>
          <!-- 消息列表 -->
          <div class="message-list" ref="messageList">
            <div 
              class="message-item" 
              v-for="message in messages" 
              :key="message.messageId"
              :class="message.role"
            >
              <div class="message-avatar">
                <i class="el-icon-user" v-if="message.role === 'user'"></i>
                <i class="el-icon-robot" v-else></i>
              </div>
              <div class="message-content">
                <div class="message-text" v-html="formatMessage(message.content)"></div>
                <div class="message-time">{{ formatTime(message.createTime) }}</div>
              </div>
            </div>
            
            <!-- 加载中 -->
            <div class="message-item assistant" v-if="isLoading">
              <div class="message-avatar">
                <i class="el-icon-robot"></i>
              </div>
              <div class="message-content">
                <div class="message-text typing">
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
              </div>
            </div>
          </div>

          <!-- 输入框 -->
          <div class="message-input">
            <el-input
              v-model="inputMessage"
              type="textarea"
              :rows="2"
              placeholder="请输入您的问题..."
              @keyup.enter.native="sendMessage"
              :disabled="isLoading"
            ></el-input>
            <el-button 
              type="primary" 
              @click="sendMessage"
              :loading="isLoading"
              :disabled="!inputMessage.trim()"
            >
              发送
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { 
  getUserSessions, 
  getSessionMessages, 
  sendMessage, 
  createNewSession,
  markMessagesAsRead,
  getUserUnreadCount
} from '@/api/aichat'

export default {
  name: 'AiChatButton',
  data() {
    return {
      isOpen: false,
      sessions: [],
      currentSessionId: null,
      messages: [],
      inputMessage: '',
      isLoading: false,
      unreadCount: 0,
      refreshTimer: null
    }
  },
  mounted() {
    // 延迟加载，避免在组件初始化时出现问题
    this.$nextTick(() => {
      this.loadSessions()
      this.loadUnreadCount()
      // 每30秒刷新一次未读消息数量
      this.refreshTimer = setInterval(() => {
        this.loadUnreadCount()
      }, 30000)
    })
  },
  beforeDestroy() {
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer)
    }
  },
  methods: {
    // 切换聊天窗口
    toggleChat() {
      this.isOpen = !this.isOpen
      if (this.isOpen) {
        this.loadSessions()
      }
    },

    // 加载会话列表
    async loadSessions() {
      try {
        const response = await getUserSessions()
        this.sessions = response.data || []
        // 加载每个会话的未读消息数量
        for (let session of this.sessions) {
          try {
            const unreadResponse = await getUnreadCount(session.sessionId)
            session.unreadCount = unreadResponse.data || 0
          } catch (error) {
            session.unreadCount = 0
          }
        }
      } catch (error) {
        console.error('加载会话列表失败:', error)
      }
    },

    // 加载未读消息数量
    async loadUnreadCount() {
      try {
        const response = await getUserUnreadCount()
        this.unreadCount = response.data || 0
      } catch (error) {
        console.error('加载未读消息数量失败:', error)
        this.unreadCount = 0
      }
    },

    // 选择会话
    async selectSession(session) {
      this.currentSessionId = session.sessionId
      await this.loadMessages()
      // 标记消息为已读
      if (session.unreadCount > 0) {
        this.markSessionAsRead()
      }
    },

    // 加载消息列表
    async loadMessages() {
      if (!this.currentSessionId) return
      
      try {
        const response = await getSessionMessages(this.currentSessionId)
        this.messages = response.data || []
        this.$nextTick(() => {
          this.scrollToBottom()
        })
      } catch (error) {
        console.error('加载消息列表失败:', error)
      }
    },

    // 创建新会话
    async createNewSession() {
      try {
        const response = await createNewSession({ sessionName: '新对话' })
        this.currentSessionId = response.data
        this.messages = []
        this.loadSessions()
      } catch (error) {
        console.error('创建新会话失败:', error)
      }
    },

    // 发送消息
    async sendMessage() {
      if (!this.inputMessage.trim() || this.isLoading) return

      const message = this.inputMessage.trim()
      this.inputMessage = ''
      this.isLoading = true

      try {
        // 添加用户消息到界面
        const userMessage = {
          messageId: Date.now(),
          role: 'user',
          content: message,
          createTime: new Date(),
          messageType: 'text'
        }
        this.messages.push(userMessage)
        this.scrollToBottom()

        // 发送到后端
        const response = await sendMessage({
          sessionId: this.currentSessionId,
          message: message,
          messageType: 'text',
          createNewSession: !this.currentSessionId
        })

        if (response.code === 200) {
          // 更新会话ID
          if (response.data.isNewSession) {
            this.currentSessionId = response.data.sessionId
            this.loadSessions()
          }

          // 添加AI回复到界面
          const aiMessage = {
            messageId: response.data.messageId,
            role: 'assistant',
            content: response.data.message,
            createTime: new Date(),
            messageType: 'text'
          }
          this.messages.push(aiMessage)
          this.scrollToBottom()

          // 更新会话列表
          this.loadSessions()
        } else {
          this.$message.error(response.msg || '发送失败')
        }
      } catch (error) {
        console.error('发送消息失败:', error)
        this.$message.error('发送失败，请重试')
      } finally {
        this.isLoading = false
      }
    },

    // 标记会话为已读
    async markSessionAsRead() {
      if (!this.currentSessionId) return
      
      try {
        const unreadMessages = this.messages.filter(msg => msg.role === 'assistant' && !msg.isRead)
        if (unreadMessages.length > 0) {
          const messageIds = unreadMessages.map(msg => msg.messageId)
          await markMessagesAsRead(messageIds)
          this.loadUnreadCount()
        }
      } catch (error) {
        console.error('标记消息已读失败:', error)
      }
    },

    // 刷新会话列表
    refreshSessions() {
      this.loadSessions()
    },

    // 滚动到底部
    scrollToBottom() {
      this.$nextTick(() => {
        const messageList = this.$refs.messageList
        if (messageList) {
          messageList.scrollTop = messageList.scrollHeight
        }
      })
    },

    // 格式化时间
    formatTime(time) {
      if (!time) return ''
      const date = new Date(time)
      const now = new Date()
      const diff = now - date
      
      if (diff < 60000) { // 1分钟内
        return '刚刚'
      } else if (diff < 3600000) { // 1小时内
        return Math.floor(diff / 60000) + '分钟前'
      } else if (diff < 86400000) { // 1天内
        return Math.floor(diff / 3600000) + '小时前'
      } else {
        return date.toLocaleDateString()
      }
    },

    // 格式化消息内容
    formatMessage(content) {
      if (!content) return ''
      // 简单的换行处理
      return content.replace(/\n/g, '<br>')
    }
  }
}
</script>

<style scoped>
.ai-chat-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 9999;
}

.chat-button {
  width: 60px;
  height: 60px;
  background: #409EFF;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  transition: all 0.3s ease;
  position: relative;
}

.chat-button:hover {
  background: #66b1ff;
  transform: scale(1.1);
}

.chat-button.has-unread {
  background: #F56C6C;
  animation: pulse 2s infinite;
}

.chat-button i {
  color: white;
  font-size: 24px;
}

.unread-badge {
  position: absolute;
  top: -5px;
  right: -5px;
  background: #F56C6C;
  color: white;
  border-radius: 10px;
  padding: 2px 6px;
  font-size: 12px;
  min-width: 18px;
  text-align: center;
}

.chat-window {
  position: absolute;
  bottom: 80px;
  right: 0;
  width: 400px;
  height: 600px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.chat-header {
  background: #409EFF;
  color: white;
  padding: 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.chat-title {
  display: flex;
  align-items: center;
  font-weight: 500;
}

.chat-title i {
  margin-right: 8px;
  font-size: 18px;
}

.chat-actions {
  display: flex;
  gap: 8px;
}

.chat-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.session-list {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
}

.session-item {
  padding: 12px;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.2s;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}

.session-item:hover {
  background: #f5f7fa;
}

.session-item.active {
  background: #e6f7ff;
}

.session-info {
  flex: 1;
}

.session-name {
  font-weight: 500;
  margin-bottom: 4px;
}

.session-time {
  font-size: 12px;
  color: #909399;
}

.session-unread {
  background: #F56C6C;
  color: white;
  border-radius: 10px;
  padding: 2px 8px;
  font-size: 12px;
  min-width: 18px;
  text-align: center;
}

.chat-interface {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
}

.message-item {
  display: flex;
  margin-bottom: 16px;
  align-items: flex-start;
}

.message-item.user {
  flex-direction: row-reverse;
}

.message-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 8px;
}

.message-item.user .message-avatar {
  background: #409EFF;
  color: white;
}

.message-item.assistant .message-avatar {
  background: #67C23A;
  color: white;
}

.message-content {
  max-width: 70%;
  background: #f5f7fa;
  padding: 12px;
  border-radius: 12px;
  position: relative;
}

.message-item.user .message-content {
  background: #409EFF;
  color: white;
}

.message-text {
  line-height: 1.5;
  word-wrap: break-word;
}

.message-time {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}

.message-item.user .message-time {
  color: rgba(255, 255, 255, 0.7);
}

.typing {
  display: flex;
  align-items: center;
  gap: 4px;
}

.typing span {
  width: 6px;
  height: 6px;
  background: #909399;
  border-radius: 50%;
  animation: typing 1.4s infinite;
}

.typing span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing span:nth-child(3) {
  animation-delay: 0.4s;
}

.message-input {
  padding: 16px;
  border-top: 1px solid #e4e7ed;
  display: flex;
  gap: 12px;
  align-items: flex-end;
}

.message-input .el-textarea {
  flex: 1;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(245, 108, 108, 0.7);
  }
  70% {
    box-shadow: 0 0 0 10px rgba(245, 108, 108, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(245, 108, 108, 0);
  }
}

@keyframes typing {
  0%, 60%, 100% {
    transform: translateY(0);
  }
  30% {
    transform: translateY(-10px);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .chat-window {
    width: 320px;
    height: 500px;
  }
  
  .chat-button {
    width: 50px;
    height: 50px;
  }
  
  .chat-button i {
    font-size: 20px;
  }
}
</style>
