<template>
  <div id="app">
    <router-view />
    <theme-picker />
    <ai-chat-stable />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker"
import AiChatStable from "@/components/AiChatStable"

export default {
  name: "App",
  components: { 
    ThemePicker,
    AiChatStable
  }
}
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
