<template>
  <div id="app">
    <router-view />
    <theme-picker />
    <ai-chat-fixed />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker"
import AiChatFixed from "@/components/AiChatFixed"

export default {
  name: "App",
  components: { 
    ThemePicker,
    AiChatFixed
  }
}
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
