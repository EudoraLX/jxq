import { getDicts, getMultipleDicts } from '@/api/system/dict'

export default {
  data() {
    return {
      // 字典数据存储
      dictData: {},
      // 字典加载状态
      dictLoading: false
    }
  },
  
  mounted() {
    // 不在这里自动加载，让组件自己控制时机
  },
  
  methods: {
    // 初始化字典数据
    async initDictData() {
      // 尝试从不同位置获取dicts
      const dictTypes = this.dicts || this.$options.dicts || []
      
      if (!dictTypes || dictTypes.length === 0) {
        return
      }
      
      // 检查是否已经加载过
      if (this.dictData[dictTypes[0]] && this.dictData[dictTypes[0]].length > 0) {
        return
      }
      
      this.dictLoading = true
      
      try {
        const results = await getMultipleDicts(dictTypes)
        
        // 处理返回的字典数据
        results.forEach(result => {
          this.dictData[result.type] = result.data || []
        })
        
        // 触发页面更新
        this.$forceUpdate()
      } catch (error) {
        console.error('字典数据加载失败:', error)
      } finally {
        this.dictLoading = false
      }
    },

    // 获取字典标签
    getDictLabel(dictType, value) {
      if (!this.dictData[dictType]) {
        return value
      }
      
      const item = this.dictData[dictType].find(item => item.dictValue === value)
      return item ? item.dictLabel : value
    },

    // 获取字典选项（用于uni-data-select）
    getDictOptions(dictType) {
      if (!this.dictData[dictType]) {
        return []
      }
      
      return this.dictData[dictType].map(item => ({
        value: item.dictValue,
        text: item.dictLabel
      }))
    },

    // 获取字典选项（用于uni-combox）
    getDictComboxOptions(dictType) {
      if (!this.dictData[dictType]) {
        return []
      }
      
      return this.dictData[dictType].map(item => ({
        value: item.dictValue,
        text: item.dictLabel
      }))
    },

    // 检查字典是否已加载
    isDictLoaded(dictType) {
      return this.dictData[dictType] && this.dictData[dictType].length > 0
    },

    // 重新加载指定字典
    async reloadDict(dictType) {
      try {
        const result = await getDicts(dictType)
        this.dictData[dictType] = result || []
        this.$forceUpdate()
      } catch (error) {
        console.error(`重新加载字典失败: ${dictType}`, error)
      }
    }
  }
}