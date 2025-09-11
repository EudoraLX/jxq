import request from '@/utils/request'

// 查询库存预警表列表
export function listAlert(query) {
  return request({
    url: '/inventory/alert/list',
    method: 'get',
    params: query
  })
}

// 查询库存预警表详细
export function getAlert(id) {
  return request({
    url: '/inventory/alert/' + id,
    method: 'get'
  })
}

// 新增库存预警表
export function addAlert(data) {
  return request({
    url: '/inventory/alert',
    method: 'post',
    data: data
  })
}

// 修改库存预警表
export function updateAlert(data) {
  return request({
    url: '/inventory/alert',
    method: 'put',
    data: data
  })
}

// 删除库存预警表
export function delAlert(id) {
  return request({
    url: '/inventory/alert/' + id,
    method: 'delete'
  })
}

// 处理库存预警
export function handleAlert(data) {
  return request({
    url: '/inventory/alert/handle',
    method: 'post',
    data: data
  })
}

// 批量处理库存预警
export function handleBatchAlert(ids) {
  return request({
    url: '/inventory/alert/handleBatch',
    method: 'post',
    data: ids
  })
}

// 查询库存预警表统计信息
export function getAlertStatistics(query) {
  return request({
    url: '/inventory/alert/statistics',
    method: 'get',
    params: query
  })
}

// 生成库存预警
export function generateAlerts() {
  return request({
    url: '/inventory/alert/generate',
    method: 'post'
  })
}

// 查询未处理的预警数量
export function getUnhandledCount() {
  return request({
    url: '/inventory/alert/unhandledCount',
    method: 'get'
  })
}

// 查询高优先级预警数量
export function getHighPriorityCount() {
  return request({
    url: '/inventory/alert/highPriorityCount',
    method: 'get'
  })
}

// 检查库存不足预警
export function checkLowStockAlert(materialId, warehouseId) {
  return request({
    url: '/inventory/alert/checkLowStock',
    method: 'get',
    params: {
      materialId: materialId,
      warehouseId: warehouseId
    }
  })
}

// 检查库存过多预警
export function checkHighStockAlert(materialId, warehouseId) {
  return request({
    url: '/inventory/alert/checkHighStock',
    method: 'get',
    params: {
      materialId: materialId,
      warehouseId: warehouseId
    }
  })
}

// 检查即将过期预警
export function checkExpiryAlert(materialId, warehouseId, days) {
  return request({
    url: '/inventory/alert/checkExpiry',
    method: 'get',
    params: {
      materialId: materialId,
      warehouseId: warehouseId,
      days: days
    }
  })
}

// 检查已过期预警
export function checkExpiredAlert(materialId, warehouseId) {
  return request({
    url: '/inventory/alert/checkExpired',
    method: 'get',
    params: {
      materialId: materialId,
      warehouseId: warehouseId
    }
  })
}
