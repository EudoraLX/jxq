<template>
  <div class="ai-chat-fixed" :class="{ 'is-open': isOpen }">
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
            v-for="(message, index) in messages" 
            :key="`msg-${index}-${message.timestamp}`"
            :class="message.role"
          >
            <div class="message-avatar">
              <i :class="message.role === 'user' ? 'el-icon-user' : 'el-icon-chat-dot-round'"></i>
            </div>
            <div class="message-content">
              <div class="message-text" v-html="formatMessage(message.content || '(空内容)')"></div>
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
  name: 'AiChatFixed',
  data() {
    return {
      isOpen: false,
      inputMessage: '',
      messages: [],
      isLoading: false,
      unreadCount: 0,
      sessionId: null,
      apiAvailable: false,
      messageIdCounter: 0
    }
  },
  mounted() {
    // 延迟初始化
    setTimeout(() => {
      this.initializeChat()
    }, 1000)
  },
  beforeDestroy() {
    // 清理定时器
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer)
    }
  },
  methods: {
    // 初始化聊天
    initializeChat() {
      try {
        this.apiAvailable = typeof apiSendMessage === 'function'
        console.log('API可用性:', this.apiAvailable)
        this.loadChatHistory()
        if (this.apiAvailable) {
          this.loadUnreadCount()
        }
      } catch (error) {
        console.warn('AI聊天初始化失败:', error)
        this.apiAvailable = false
      }
    },

    // 生成唯一消息ID
    generateMessageId() {
      this.messageIdCounter++
      return `msg_${Date.now()}_${this.messageIdCounter}_${Math.random().toString(36).substr(2, 6)}`
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
      const welcomeMessage = {
        id: this.generateMessageId(),
        role: 'assistant',
        content: '您好！我是AI智能客服，有什么可以帮助您的吗？',
        createTime: new Date(),
        messageType: 'text',
        timestamp: Date.now()
      }
      this.messages.push(welcomeMessage)
      this.saveChatHistory()
    },

    // 创建新对话
    createNewChat() {
      this.$confirm('确定要开始新的对话吗？当前对话历史将被清空。', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        customClass: 'ai-chat-confirm-dialog',
        appendToBody: true
      }).then(() => {
        this.messages = []
        this.sessionId = null
        this.messageIdCounter = 0
        this.addWelcomeMessage()
        this.saveChatHistory()
        this.$message.success('已开始新对话')
      }).catch(() => {
        // 用户取消
      })
    },

    // 刷新聊天
    refreshChat() {
      this.createNewChat()
    },

    // 发送消息
    async sendMessage() {
      if (!this.inputMessage.trim() || this.isLoading) return

      const userMessage = this.inputMessage.trim()
      this.inputMessage = ''

      // 添加用户消息
      const userMsg = {
        id: this.generateMessageId(),
        role: 'user',
        content: userMessage,
        createTime: new Date(),
        messageType: 'text',
        timestamp: Date.now()
      }
      this.messages.push(userMsg)
      this.scrollToBottom()
      this.saveChatHistory()

      this.isLoading = true

      try {
        if (this.apiAvailable) {
          // 调用真实API
          console.log('发送到API:', userMessage)
          try {
            const response = await apiSendMessage({
              sessionId: this.sessionId,
              message: userMessage,
              messageType: 'text',
              createNewSession: !this.sessionId
            })

            if (response.code === 200) {
              this.sessionId = response.data.sessionId
              const aiResponse = response.data.message || ''
              console.log('AI回复内容:', aiResponse)
              console.log('AI回复长度:', aiResponse.length)
              
              // 检查AI回复是否为空
              if (aiResponse && aiResponse.trim() !== '') {
                const aiMessage = {
                  id: response.data.messageId || this.generateMessageId(),
                  role: 'assistant',
                  content: aiResponse,
                  createTime: new Date(),
                  messageType: 'text',
                  timestamp: Date.now()
                }
                console.log('添加AI消息到界面:', aiMessage)
                this.messages.push(aiMessage)
              } else {
                console.log('AI回复为空，降级到模拟模式')
                // AI回复为空时，降级到模拟模式
                setTimeout(() => {
                  this.addSimulatedReply(userMessage)
                }, 1000)
              }
            } else {
              console.log('API返回错误:', response)
              this.addErrorMessage(response.msg || '发送失败')
            }
          } catch (apiError) {
            console.error('API调用异常，降级到模拟模式:', apiError)
            // API调用异常时，降级到模拟模式
            setTimeout(() => {
              this.addSimulatedReply(userMessage)
            }, 1000)
          }
        } else {
          // 模拟AI回复（API不可用时的降级方案）
          console.log('API不可用，使用模拟模式，用户消息:', userMessage)
          setTimeout(() => {
            this.addSimulatedReply(userMessage)
          }, 1000)
        }
      } catch (error) {
        console.error('发送消息异常，降级到模拟模式:', error)
        // 发送消息异常时，降级到模拟模式
        setTimeout(() => {
          this.addSimulatedReply(userMessage)
        }, 1000)
      } finally {
        this.isLoading = false
        this.scrollToBottom()
        this.saveChatHistory()
      }
    },

    // 添加模拟回复
    addSimulatedReply(userMessage) {
      let reply = ''
      
      // 根据用户消息生成智能回复
      if (userMessage.includes('你好') || userMessage.includes('您好')) {
        reply = '您好！我是AI智能客服，很高兴为您服务！有什么可以帮助您的吗？'
      } else if (userMessage.includes('谢谢') || userMessage.includes('感谢')) {
        reply = '不客气！很高兴能帮助到您。如果还有其他问题，随时可以问我。'
      } else if (userMessage.includes('再见') || userMessage.includes('拜拜')) {
        reply = '再见！感谢您的使用，祝您工作愉快！'
      } else if (userMessage.includes('ERP') || userMessage.includes('erp')) {
        reply = '关于ERP系统，我可以帮您解答生产订单、物料管理、库存控制等相关问题。您具体想了解哪个方面？'
      } else if (userMessage.includes('生产订单')) {
        reply = '生产订单通常包含以下字段：订单编号、产品名称、数量、计划开始时间、计划完成时间、状态、负责人等。您需要了解具体哪个字段的详细信息？'
      } else if (userMessage.includes('物料')) {
        reply = '物料管理包括物料编码、名称、规格、单位、库存数量、安全库存等信息。您想了解物料管理的哪个方面？'
      } else {
        // 通用回复，包含用户的具体问题
        const replies = [
          `关于"${userMessage}"这个问题，我来为您详细解答。`,
          `您提到的"${userMessage}"确实是个很好的问题，让我来分析一下。`,
          `感谢您的提问"${userMessage}"，我来帮您分析一下。`,
          `您的问题"${userMessage}"很重要，让我为您详细说明。`
        ]
        reply = replies[Math.floor(Math.random() * replies.length)]
      }
      
      const aiMessage = {
        id: this.generateMessageId(),
        role: 'assistant',
        content: reply,
        createTime: new Date(),
        messageType: 'text',
        timestamp: Date.now()
      }
      console.log('模拟AI回复:', aiMessage)
      this.messages.push(aiMessage)
    },

    // 添加错误消息
    addErrorMessage(errorMsg) {
      const errorMessage = {
        id: this.generateMessageId(),
        role: 'assistant',
        content: `抱歉，${errorMsg}`,
        createTime: new Date(),
        messageType: 'text',
        timestamp: Date.now()
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

    // 保存聊天历史
    saveChatHistory() {
      try {
        const chatData = {
          messages: this.messages,
          sessionId: this.sessionId,
          messageIdCounter: this.messageIdCounter,
          timestamp: Date.now()
        }
        localStorage.setItem('ai_chat_fixed_history', JSON.stringify(chatData))
      } catch (error) {
        console.warn('保存聊天历史失败:', error)
      }
    },

    // 加载聊天历史
    loadChatHistory() {
      try {
        const chatData = localStorage.getItem('ai_chat_fixed_history')
        if (chatData) {
          const parsed = JSON.parse(chatData)
          // 检查数据是否过期（24小时）
          if (Date.now() - parsed.timestamp < 24 * 60 * 60 * 1000) {
            this.messages = parsed.messages || []
            this.sessionId = parsed.sessionId || null
            this.messageIdCounter = parsed.messageIdCounter || 0
          } else {
            // 数据过期，清空
            localStorage.removeItem('ai_chat_fixed_history')
            this.addWelcomeMessage()
          }
        } else {
          this.addWelcomeMessage()
        }
      } catch (error) {
        console.warn('加载聊天历史失败:', error)
        this.addWelcomeMessage()
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
.ai-chat-fixed {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 9998;
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
  z-index: 9998;
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

/* 弹框层级修复 */
.ai-chat-confirm-dialog {
  z-index: 10001 !important;
}

/* 确保Element UI弹框在最上层 */
.el-message-box__wrapper {
  z-index: 10000 !important;
}

/* 确保聊天窗口不会遮挡弹框 */
.ai-chat-fixed .chat-window {
  z-index: 9998;
}
</style>
