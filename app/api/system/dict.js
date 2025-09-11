import request from '@/utils/request'

// 获取单个字典类型的数据
export function getDicts(dictType) {
  return request({
    url: `/system/dict/data/type/${dictType}`,
    method: 'get'
  }).then(response => {
    return response.data
  })
}

// 获取单个字典数据
export function getDict(dictType, dictValue) {
  return request({
    url: `/system/dict/data/type/${dictType}`,
    method: 'get'
  }).then(response => {
    const data = response.data || []
    return data.find(item => item.dictValue === dictValue)
  })
}

// 获取多个字典类型的数据
export function getMultipleDicts(dictTypes) {
  const promises = dictTypes.map(dictType => 
    getDicts(dictType).then(data => ({
      type: dictType,
      data: data
    }))
  )
  
  return Promise.all(promises)
}