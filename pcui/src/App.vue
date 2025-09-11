<template>
  <div id="app">
    <router-view />
    <theme-picker />
    <ai-chat-light />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker"
import AiChatLight from "@/components/AiChatLight"

export default {
  name: "App",
  components: { 
    ThemePicker,
    AiChatLight
  }
}
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
