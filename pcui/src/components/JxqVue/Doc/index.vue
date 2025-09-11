<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'JxqVueDoc',
  data() {
    return {
      url: 'http://doc.jxqvue.vip/jxqvue-vue'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
