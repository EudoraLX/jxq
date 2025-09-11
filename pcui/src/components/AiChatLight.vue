<template>
  <div class="ai-chat-light" :class="{ 'is-open': isOpen }">
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
            @click="refreshChat"
            size="mini"
          ></el-button>
          <el-button 
            type="text" 
            icon="el-icon-plus" 
            @click="createNewChat"
            size="mini"
          ></el-button>
        </div>
      </div>

      <div class="chat-content">
        <!-- 消息列表 -->
        <div class="message-list" ref="messageList">
          <div 
            class="message-item" 
            v-for="message in messages" 
            :key="message.messageId"
            :class="message.role"
          >
            <div class="message-avatar">
              <i :class="message.role === 'user' ? 'el-icon-user' : 'el-icon-chat-dot-round'"></i>
            </div>
            <div class="message-content">
              <div class="message-text" v-html="formatMessage(message.content)"></div>
              <div class="message-time">{{ formatTime(message.createTime) }}</div>
            </div>
          </div>
          
          <!-- 加载状态 -->
          <div v-if="isLoading" class="message-item assistant">
            <div class="message-avatar">
              <i class="el-icon-chat-dot-round"></i>
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

        <!-- 输入区域 -->
        <div class="input-area">
          <el-input
            v-model="inputMessage"
            placeholder="请输入您的问题..."
            @keyup.enter.native="sendMessage"
            :disabled="isLoading"
            class="message-input"
          ></el-input>
          <el-button 
            type="primary" 
            @click="sendMessage"
            :loading="isLoading"
            :disabled="!inputMessage.trim()"
            class="send-button"
          >
            发送
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { sendMessage as apiSendMessage } from '@/api/aichat'

export default {
  name: 'AiChatLight',
  data() {
    return {
      isOpen: false,
      inputMessage: '',
      messages: [],
      isLoading: false,
      unreadCount: 0,
      sessionId: null,
      apiAvailable: false
    }
  },
  mounted() {
    // 延迟检查API可用性，避免阻塞页面加载
    setTimeout(() => {
      this.checkApiAvailability()
    }, 1000)
  },
  beforeDestroy() {
    // 清理定时器
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer)
    }
  },
  methods: {
    // 检查API可用性
    checkApiAvailability() {
      try {
        this.apiAvailable = typeof apiSendMessage === 'function'
        if (this.apiAvailable) {
          this.loadUnreadCount()
        }
      } catch (error) {
        console.warn('AI聊天API不可用，使用模拟模式')
        this.apiAvailable = false
      }
    },

    // 切换聊天窗口
    toggleChat() {
      this.isOpen = !this.isOpen
      if (this.isOpen && this.messages.length === 0) {
        this.addWelcomeMessage()
      }
    },

    // 添加欢迎消息
    addWelcomeMessage() {
      this.messages = [
        {
          messageId: Date.now(),
          role: 'assistant',
          content: '您好！我是AI智能客服，有什么可以帮助您的吗？',
          createTime: new Date(),
          messageType: 'text'
        }
      ]
    },

    // 创建新对话
    createNewChat() {
      this.messages = []
      this.sessionId = null
      this.addWelcomeMessage()
    },

    // 刷新聊天
    refreshChat() {
      this.createNewChat()
    },

    // 发送消息
    async sendMessage() {
      if (!this.inputMessage.trim() || this.isLoading) return

      const message = this.inputMessage.trim()
      this.inputMessage = ''

      // 添加用户消息
      const userMessage = {
        messageId: Date.now(),
        role: 'user',
        content: message,
        createTime: new Date(),
        messageType: 'text'
      }
      this.messages.push(userMessage)
      this.scrollToBottom()

      this.isLoading = true

      try {
        if (this.apiAvailable) {
          // 调用API
          const response = await apiSendMessage({
            sessionId: this.sessionId,
            message: message,
            messageType: 'text',
            createNewSession: !this.sessionId
          })

          if (response.code === 200) {
            this.sessionId = response.data.sessionId
            const aiMessage = {
              messageId: response.data.messageId,
              role: 'assistant',
              content: response.data.message,
              createTime: new Date(),
              messageType: 'text'
            }
            this.messages.push(aiMessage)
          } else {
            this.addErrorMessage(response.msg || '发送失败')
          }
        } else {
          // 模拟AI回复
          setTimeout(() => {
            this.addSimulatedReply(message)
          }, 1000)
        }
      } catch (error) {
        console.error('发送消息失败:', error)
        this.addErrorMessage('发送失败：' + error.message)
      } finally {
        this.isLoading = false
        this.scrollToBottom()
      }
    },

    // 添加模拟回复
    addSimulatedReply(userMessage) {
      const replies = [
        '我理解您的问题，让我为您详细解答。',
        '这是一个很好的问题，根据我的了解...',
        '感谢您的提问，我来帮您分析一下。',
        '您提到的这个问题确实很重要，让我为您说明。'
      ]
      
      const randomReply = replies[Math.floor(Math.random() * replies.length)]
      const aiMessage = {
        messageId: Date.now() + 1,
        role: 'assistant',
        content: randomReply,
        createTime: new Date(),
        messageType: 'text'
      }
      this.messages.push(aiMessage)
    },

    // 添加错误消息
    addErrorMessage(errorMsg) {
      const errorMessage = {
        messageId: Date.now() + 1,
        role: 'assistant',
        content: `抱歉，${errorMsg}`,
        createTime: new Date(),
        messageType: 'text'
      }
      this.messages.push(errorMessage)
    },

    // 加载未读消息数量
    async loadUnreadCount() {
      if (!this.apiAvailable) return
      
      try {
        // 这里可以调用API获取未读数量
        this.unreadCount = 0
      } catch (error) {
        console.warn('加载未读消息数量失败:', error)
      }
    },

    // 格式化消息内容
    formatMessage(content) {
      if (!content) return ''
      return content.replace(/\n/g, '<br>')
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

    // 滚动到底部
    scrollToBottom() {
      this.$nextTick(() => {
        const messageList = this.$refs.messageList
        if (messageList) {
          messageList.scrollTop = messageList.scrollHeight
        }
      })
    }
  }
}
</script>

<style scoped>
.ai-chat-light {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 9999;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.chat-button {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.4);
  transition: all 0.3s ease;
  position: relative;
  color: white;
  font-size: 24px;
}

.chat-button:hover {
  transform: scale(1.1);
  box-shadow: 0 6px 25px rgba(102, 126, 234, 0.6);
}

.chat-button.has-unread {
  animation: pulse 2s infinite;
}

.unread-badge {
  position: absolute;
  top: -5px;
  right: -5px;
  background: #ff4757;
  color: white;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: bold;
}

.chat-window {
  position: absolute;
  bottom: 80px;
  right: 0;
  width: 400px;
  height: 500px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.chat-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.chat-title {
  display: flex;
  align-items: center;
  font-weight: 600;
  font-size: 16px;
}

.chat-title i {
  margin-right: 8px;
  font-size: 18px;
}

.chat-actions {
  display: flex;
  gap: 8px;
}

.chat-actions .el-button {
  color: white;
  padding: 4px 8px;
}

.chat-actions .el-button:hover {
  background: rgba(255, 255, 255, 0.2);
}

.chat-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.message-list {
  flex: 1;
  padding: 16px;
  overflow-y: auto;
  background: #f8f9fa;
}

.message-item {
  display: flex;
  margin-bottom: 16px;
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
  margin: 0 8px;
  flex-shrink: 0;
}

.message-item.user .message-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.message-item.assistant .message-avatar {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  color: white;
}

.message-content {
  max-width: 70%;
  background: white;
  padding: 12px 16px;
  border-radius: 18px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: relative;
}

.message-item.user .message-content {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.message-text {
  line-height: 1.5;
  word-wrap: break-word;
}

.message-time {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
  text-align: right;
}

.message-item.user .message-time {
  color: rgba(255, 255, 255, 0.8);
}

.typing {
  display: flex;
  align-items: center;
  gap: 4px;
}

.typing span {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #999;
  animation: typing 1.4s infinite ease-in-out;
}

.typing span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing span:nth-child(3) {
  animation-delay: 0.4s;
}

.input-area {
  padding: 16px;
  background: white;
  border-top: 1px solid #e9ecef;
  display: flex;
  gap: 12px;
  align-items: center;
}

.message-input {
  flex: 1;
}

.message-input .el-input__inner {
  border-radius: 20px;
  border: 1px solid #e9ecef;
  padding: 8px 16px;
}

.send-button {
  border-radius: 20px;
  padding: 8px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
}

.send-button:hover {
  background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
}

/* 动画 */
@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.05); }
  100% { transform: scale(1); }
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

@keyframes typing {
  0%, 60%, 100% {
    transform: translateY(0);
    opacity: 0.5;
  }
  30% {
    transform: translateY(-10px);
    opacity: 1;
  }
}

/* 滚动条样式 */
.message-list::-webkit-scrollbar {
  width: 6px;
}

.message-list::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.message-list::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.message-list::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>
