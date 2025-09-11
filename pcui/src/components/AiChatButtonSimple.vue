<template>
  <div class="ai-chat-button-simple">
    <!-- 悬浮按钮 -->
    <div 
      class="chat-button" 
      @click="toggleChat"
    >
      <i class="el-icon-chat-dot-round" v-if="!isOpen"></i>
      <i class="el-icon-close" v-else></i>
    </div>

    <!-- 聊天窗口 -->
    <div class="chat-window" v-show="isOpen">
      <div class="chat-header">
        <div class="chat-title">
          <i class="el-icon-chat-dot-round"></i>
          AI智能客服
        </div>
      </div>

      <div class="chat-content">
        <div class="message-list">
          <div class="message-item assistant">
            <div class="message-avatar">
              <i class="el-icon-robot"></i>
            </div>
            <div class="message-content">
              <div class="message-text">您好！我是AI智能客服，有什么可以帮助您的吗？</div>
            </div>
          </div>
        </div>

        <div class="message-input">
          <el-input
            v-model="inputMessage"
            type="textarea"
            :rows="2"
            placeholder="请输入您的问题..."
            @keyup.enter.native="sendMessage"
          ></el-input>
          <el-button 
            type="primary" 
            @click="sendMessage"
            :disabled="!inputMessage.trim()"
          >
            发送
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AiChatButtonSimple',
  data() {
    return {
      isOpen: false,
      inputMessage: ''
    }
  },
  methods: {
    // 切换聊天窗口
    toggleChat() {
      this.isOpen = !this.isOpen
    },

    // 发送消息
    sendMessage() {
      if (!this.inputMessage.trim()) return
      
      console.log('发送消息:', this.inputMessage)
      this.$message.success('消息发送成功！')
      this.inputMessage = ''
    }
  }
}
</script>

<style scoped>
.ai-chat-button-simple {
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
}

.chat-button:hover {
  background: #66b1ff;
  transform: scale(1.1);
}

.chat-button i {
  color: white;
  font-size: 24px;
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

.chat-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
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

.message-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 8px;
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

.message-text {
  line-height: 1.5;
  word-wrap: break-word;
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
