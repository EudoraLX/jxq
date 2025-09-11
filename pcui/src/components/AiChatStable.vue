<template>
  <div class="ai-chat-stable" :class="{ 'is-open': isOpen }">
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
          <!-- 如果没有会话，显示默认内容 -->
          <div v-if="sessions.length === 0" class="empty-sessions">
            <div class="empty-message">
              <i class="el-icon-chat-dot-round"></i>
              <p>欢迎使用AI智能客服</p>
              <p>点击右上角"+"按钮开始新对话</p>
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
                <div class="typing-text">AI正在思考中...</div>
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
// 安全导入API函数
let getUserSessions, getSessionMessages, sendMessage, createNewSession, markMessagesAsRead, getUserUnreadCount

try {
  const aichatApi = require('@/api/aichat')
  getUserSessions = aichatApi.getUserSessions
  getSessionMessages = aichatApi.getSessionMessages
  sendMessage = aichatApi.sendMessage
  createNewSession = aichatApi.createNewSession
  markMessagesAsRead = aichatApi.markMessagesAsRead
  getUserUnreadCount = aichatApi.getUserUnreadCount
} catch (error) {
  console.warn('AI聊天API导入失败，将使用模拟数据:', error)
}

export default {
  name: 'AiChatStable',
  data() {
    return {
      isOpen: false,
      sessions: [],
      currentSessionId: null,
      messages: [],
      inputMessage: '',
      isLoading: false,
      unreadCount: 0,
      refreshTimer: null,
      apiAvailable: false
    }
  },
  mounted() {
    // 延迟初始化，避免与其他组件冲突
    this.$nextTick(() => {
      this.initializeComponent()
    })
  },
  beforeDestroy() {
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer)
    }
  },
  methods: {
    // 初始化组件
    async initializeComponent() {
      try {
        // 检查API是否可用
        this.apiAvailable = !!(getUserSessions && sendMessage)
        
        if (this.apiAvailable) {
          await this.loadSessions()
          await this.loadUnreadCount()
          // 每30秒刷新一次未读消息数量
          this.refreshTimer = setInterval(() => {
            this.loadUnreadCount()
          }, 30000)
        } else {
          // 使用模拟数据 - 先设置会话列表
          this.sessions = [
            {
              sessionId: 1,
              sessionName: 'AI客服对话',
              lastMessageTime: new Date(),
              unreadCount: 0
            }
          ]
          // 不自动创建会话，让用户看到欢迎界面
        }
      } catch (error) {
        console.error('AI聊天组件初始化失败:', error)
        // 即使出错也要显示基本界面
        this.sessions = []
      }
    },

    // 切换聊天窗口
    toggleChat() {
      this.isOpen = !this.isOpen
      if (this.isOpen) {
        if (this.apiAvailable) {
          this.loadSessions()
        } else {
          // 确保有默认会话和消息
          if (!this.currentSessionId) {
            this.currentSessionId = 1
            this.messages = [
              {
                messageId: 1,
                role: 'assistant',
                content: '您好！我是AI智能客服，有什么可以帮助您的吗？',
                createTime: new Date(),
                messageType: 'text'
              }
            ]
          }
        }
      }
    },

    // 加载会话列表
    async loadSessions() {
      if (!this.apiAvailable) return
      
      try {
        const response = await getUserSessions()
        this.sessions = response.data || []
        // 加载每个会话的未读消息数量
        for (let session of this.sessions) {
          try {
            const unreadResponse = await getUserUnreadCount()
            session.unreadCount = unreadResponse.data || 0
          } catch (error) {
            session.unreadCount = 0
          }
        }
      } catch (error) {
        console.error('加载会话列表失败:', error)
        this.showError('加载会话列表失败')
      }
    },

    // 加载未读消息数量
    async loadUnreadCount() {
      if (!this.apiAvailable) return
      
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
      
      if (this.apiAvailable) {
        await this.loadMessages()
        // 标记消息为已读
        if (session.unreadCount > 0) {
          this.markSessionAsRead()
        }
      } else {
        // 模拟模式：显示默认消息
        this.messages = [
          {
            messageId: Date.now(),
            role: 'assistant',
            content: '您好！我是AI智能客服，有什么可以帮助您的吗？',
            createTime: new Date(),
            messageType: 'text'
          }
        ]
      }
    },

    // 加载消息列表
    async loadMessages() {
      if (!this.currentSessionId || !this.apiAvailable) return
      
      try {
        const response = await getSessionMessages(this.currentSessionId)
        this.messages = response.data || []
        this.$nextTick(() => {
          this.scrollToBottom()
        })
      } catch (error) {
        console.error('加载消息列表失败:', error)
        this.showError('加载消息列表失败')
      }
    },

    // 创建新会话
    async createNewSession() {
      if (!this.apiAvailable) {
        // 模拟模式：创建新会话
        this.currentSessionId = Date.now()
        this.messages = [
          {
            messageId: Date.now(),
            role: 'assistant',
            content: '您好！我是AI智能客服，有什么可以帮助您的吗？',
            createTime: new Date(),
            messageType: 'text'
          }
        ]
        // 添加到会话列表
        this.sessions.unshift({
          sessionId: this.currentSessionId,
          sessionName: '新对话',
          lastMessageTime: new Date(),
          unreadCount: 0
        })
        return
      }
      
      try {
        const response = await createNewSession({ sessionName: '新对话' })
        this.currentSessionId = response.data
        this.messages = []
        this.loadSessions()
      } catch (error) {
        console.error('创建新会话失败:', error)
        this.showError('创建新会话失败')
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
        
        // 添加发送动画效果
        this.$nextTick(() => {
          const messageElements = document.querySelectorAll('.message-item')
          if (messageElements.length > 0) {
            const lastMessage = messageElements[messageElements.length - 1]
            lastMessage.style.animation = 'slideIn 0.3s ease'
          }
        })

        if (this.apiAvailable) {
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
            this.showError(response.msg || '发送失败')
          }
        } else {
          // 模拟AI回复
          setTimeout(() => {
            const aiMessage = {
              messageId: Date.now() + 1,
              role: 'assistant',
              content: '这是一个模拟回复。请配置API密钥以使用真实的AI功能。',
              createTime: new Date(),
              messageType: 'text'
            }
            this.messages.push(aiMessage)
            this.scrollToBottom()
            this.isLoading = false
          }, 1000)
        }
      } catch (error) {
        console.error('发送消息失败:', error)
        this.showError('发送失败，请重试')
      } finally {
        if (this.apiAvailable) {
          this.isLoading = false
        }
      }
    },

    // 标记会话为已读
    async markSessionAsRead() {
      if (!this.currentSessionId || !this.apiAvailable) return
      
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
    },

    // 显示错误信息
    showError(message) {
      try {
        this.$message.error(message)
      } catch (error) {
        console.error('显示错误信息失败:', error)
        alert(message)
      }
    }
  }
}
</script>

<style scoped>
.ai-chat-stable {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 9999;
}

.chat-button {
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  border: 3px solid rgba(255, 255, 255, 0.2);
}

.chat-button:hover {
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
  transform: scale(1.15) translateY(-2px);
  box-shadow: 0 12px 35px rgba(102, 126, 234, 0.6);
}

.chat-button.has-unread {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  animation: pulse 2s infinite;
  box-shadow: 0 8px 25px rgba(255, 107, 107, 0.5);
}

.chat-button i {
  color: white;
  font-size: 26px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.unread-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  color: white;
  border-radius: 12px;
  padding: 4px 8px;
  font-size: 11px;
  font-weight: 600;
  min-width: 20px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(255, 107, 107, 0.4);
  border: 2px solid white;
  animation: bounce 1s infinite;
}

.chat-window {
  position: absolute;
  bottom: 90px;
  right: 0;
  width: 420px;
  height: 650px;
  background: linear-gradient(145deg, #ffffff 0%, #f8f9fa 100%);
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15), 0 0 0 1px rgba(255, 255, 255, 0.8);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.chat-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 20px 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 20px 20px 0 0;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.3);
}

.chat-title {
  display: flex;
  align-items: center;
  font-weight: 600;
  font-size: 16px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.chat-title i {
  margin-right: 12px;
  font-size: 20px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.chat-actions {
  display: flex;
  gap: 12px;
}

.chat-actions .el-button {
  background: rgba(255, 255, 255, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: white;
  border-radius: 8px;
  padding: 8px 12px;
  transition: all 0.3s ease;
}

.chat-actions .el-button:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-1px);
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
  padding: 16px 20px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
  background: rgba(255, 255, 255, 0.6);
  border: 1px solid rgba(0, 0, 0, 0.05);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.session-item:hover {
  background: rgba(102, 126, 234, 0.1);
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.15);
}

.session-item.active {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.15) 0%, rgba(118, 75, 162, 0.15) 100%);
  border-color: rgba(102, 126, 234, 0.3);
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.2);
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

.empty-sessions {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  padding: 60px 40px;
  background: linear-gradient(145deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
  border-radius: 20px;
  margin: 20px;
}

.empty-message {
  text-align: center;
  color: #6c757d;
  animation: fadeInUp 0.6s ease;
}

.empty-message i {
  font-size: 64px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 24px;
  display: block;
  text-shadow: 0 4px 8px rgba(102, 126, 234, 0.2);
}

.empty-message p {
  margin: 12px 0;
  font-size: 16px;
  font-weight: 500;
  color: #495057;
}

.empty-message p:first-of-type {
  font-size: 18px;
  font-weight: 600;
  color: #343a40;
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
  margin-bottom: 20px;
  align-items: flex-start;
  animation: fadeInUp 0.3s ease;
}

.message-item.user {
  flex-direction: row-reverse;
}

.message-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  border: 2px solid rgba(255, 255, 255, 0.8);
}

.message-item.user .message-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.message-item.assistant .message-avatar {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  color: white;
}

.message-content {
  max-width: 70%;
  background: linear-gradient(145deg, #f8f9fa 0%, #e9ecef 100%);
  padding: 16px 20px;
  border-radius: 18px;
  position: relative;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.5);
}

.message-item.user .message-content {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
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

.typing-text {
  font-size: 12px;
  color: #6c757d;
  margin-top: 8px;
  font-style: italic;
  animation: fadeInUp 0.3s ease;
}

.message-input {
  padding: 20px 24px;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
  display: flex;
  gap: 16px;
  align-items: flex-end;
  background: linear-gradient(145deg, #ffffff 0%, #f8f9fa 100%);
  border-radius: 0 0 20px 20px;
}

.message-input .el-textarea {
  flex: 1;
}

.message-input .el-textarea .el-textarea__inner {
  border-radius: 20px;
  border: 2px solid rgba(102, 126, 234, 0.2);
  background: rgba(255, 255, 255, 0.8);
  padding: 12px 16px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.message-input .el-textarea .el-textarea__inner:focus {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.message-input .el-button {
  border-radius: 20px;
  padding: 12px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  font-weight: 600;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
  transition: all 0.3s ease;
}

.message-input .el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
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

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes bounce {
  0%, 20%, 50%, 80%, 100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-6px);
  }
  60% {
    transform: translateY(-3px);
  }
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
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
