// 全局错误处理器
export function setupErrorHandler() {
  // 捕获未处理的Promise拒绝
  window.addEventListener('unhandledrejection', function(event) {
    // 检查是否是已知的第三方库错误
    if (event.reason && event.reason.message && 
        (event.reason.message.includes('Cannot read properties of null') ||
         event.reason.message.includes('reading \'get\''))) {
      console.warn('已忽略已知的第三方库错误:', event.reason.message)
      event.preventDefault()
      return
    }
    
    // 其他错误正常处理
    console.error('未处理的Promise拒绝:', event.reason)
  })

  // 捕获全局JavaScript错误
  window.addEventListener('error', function(event) {
    // 检查是否是已知的第三方库错误
    if (event.error && event.error.message && 
        (event.error.message.includes('Cannot read properties of null') ||
         event.error.message.includes('reading \'get\''))) {
      console.warn('已忽略已知的第三方库错误:', event.error.message)
      event.preventDefault()
      return
    }
    
    // 其他错误正常处理
    console.error('全局JavaScript错误:', event.error)
  })
}

// 安全的API调用包装器
export function safeApiCall(apiFunction, ...args) {
  return new Promise((resolve, reject) => {
    try {
      const result = apiFunction(...args)
      if (result && typeof result.then === 'function') {
        result.then(resolve).catch(reject)
      } else {
        resolve(result)
      }
    } catch (error) {
      console.warn('API调用失败:', error)
      reject(error)
    }
  })
}

// 安全的组件挂载
export function safeMount(component, callback) {
  if (component && typeof callback === 'function') {
    try {
      component.$nextTick(callback)
    } catch (error) {
      console.warn('组件挂载失败:', error)
      // 延迟重试
      setTimeout(() => {
        try {
          callback()
        } catch (retryError) {
          console.warn('组件挂载重试失败:', retryError)
        }
      }, 100)
    }
  }
}
