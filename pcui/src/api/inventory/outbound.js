import request from '@/utils/request'

// 查询出库单表列表
export function listOutbound(query) {
  return request({
    url: '/inventory/outbound/list',
    method: 'get',
    params: query
  })
}

// 查询出库单表详细
export function getOutbound(id) {
  return request({
    url: '/inventory/outbound/' + id,
    method: 'get'
  })
}

// 新增出库单表
export function addOutbound(data) {
  return request({
    url: '/inventory/outbound',
    method: 'post',
    data: data
  })
}

// 修改出库单表
export function updateOutbound(data) {
  return request({
    url: '/inventory/outbound',
    method: 'put',
    data: data
  })
}

// 删除出库单表
export function delOutbound(id) {
  return request({
    url: '/inventory/outbound/' + id,
    method: 'delete'
  })
}

// 根据出库单号查询出库单
export function getOutboundByOutboundNo(outboundNo) {
  return request({
    url: '/inventory/outbound/getByOutboundNo',
    method: 'get',
    params: {
      outboundNo: outboundNo
    }
  })
}

// 审核出库单
export function approveOutbound(data) {
  return request({
    url: '/inventory/outbound/approve',
    method: 'post',
    data: data
  })
}

// 执行出库单
export function executeOutbound(data) {
  return request({
    url: '/inventory/outbound/execute',
    method: 'post',
    data: data
  })
}

// 取消出库单
export function cancelOutbound(data) {
  return request({
    url: '/inventory/outbound/cancel',
    method: 'post',
    data: data
  })
}

// 生成出库单号
export function generateOutboundNo() {
  return request({
    url: '/inventory/outbound/generateOutboundNo',
    method: 'get'
  })
}

// 查询出库单统计信息
export function getOutboundStatistics(query) {
  return request({
    url: '/inventory/outbound/statistics',
    method: 'get',
    params: query
  })
}

// 检查库存是否充足
export function checkInventory(materialId, warehouseId, quantity) {
  return request({
    url: '/inventory/outbound/checkInventory',
    method: 'get',
    params: {
      materialId: materialId,
      warehouseId: warehouseId,
      quantity: quantity
    }
  })
}

// 按FIFO策略分配库存
export function allocateInventoryByFIFO(materialId, warehouseId, quantity) {
  return request({
    url: '/inventory/outbound/allocateInventory',
    method: 'get',
    params: {
      materialId: materialId,
      warehouseId: warehouseId,
      quantity: quantity
    }
  })
}
