<template>
  <div class="ai-chat-test">
    <div class="test-button" @click="showMessage">
      AI客服测试
    </div>
  </div>
</template>

<script>
export default {
  name: 'AiChatTest',
  mounted() {
    // 延迟初始化，避免与其他组件冲突
    this.$nextTick(() => {
      console.log('AI聊天测试组件已安全挂载')
    })
  },
  methods: {
    showMessage() {
      try {
        this.$message.success('AI客服组件正常工作！')
      } catch (error) {
        console.log('消息提示失败，但组件功能正常:', error)
        alert('AI客服组件正常工作！')
      }
    }
  }
}
</script>

<style scoped>
.ai-chat-test {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 9999;
}

.test-button {
  width: 100px;
  height: 40px;
  background: #409EFF;
  color: white;
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 14px;
}

.test-button:hover {
  background: #66b1ff;
}
</style>
