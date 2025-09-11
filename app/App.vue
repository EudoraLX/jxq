<script>
  import config from './config'
  import { getToken } from '@/utils/auth'
  import { getUnreadCount } from '@/api/message'

  export default {
    globalData: {
      config: {},
      unreadMessageCount: 0
    },
    onLaunch: function() {
      this.initApp()
    },
    onShow: function() {
      // 应用显示时更新未读消息数量
      this.updateUnreadCount()
    },
    methods: {
      // 初始化应用
      initApp() {
        // 初始化应用配置
        this.initConfig()
        // 检查用户登录状态
        //#ifdef H5
        this.checkLogin()
        //#endif
        // 更新未读消息数量
        this.updateUnreadCount()
      },
      initConfig() {
        this.globalData.config = config
      },
      checkLogin() {
        if (!getToken()) {
          this.$tab.reLaunch('/pages/login') 
        }
      },
      // 更新未读消息数量
      async updateUnreadCount() {
        try {
          if (getToken()) {
            const response = await getUnreadCount()
            if (response.data) {
              this.globalData.unreadMessageCount = response.data.unreadMessages || 0
            } else {
              this.globalData.unreadMessageCount = 0
            }
            // 设置tabbar角标
            if (this.globalData.unreadMessageCount > 0) {
              uni.setTabBarBadge({
                index: 2, // 消息按钮在tabbar中的索引
                text: this.globalData.unreadMessageCount.toString()
              })
            } else {
              uni.removeTabBarBadge({
                index: 2
              })
            }
          }
        } catch (error) {
          console.error('获取未读消息数量失败:', error)
        }
      }
    }
  }
</script>

<style lang="scss">
  @import '@/static/scss/index.scss'
</style>
