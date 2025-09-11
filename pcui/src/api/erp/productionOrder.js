import request from '@/utils/request'

// 查询生产订单列表
export function listProductionOrder(query) {
  return request({
    url: '/erp/productionOrder/list',
    method: 'get',
    params: query
  })
}

// 查询生产订单详细
export function getProductionOrder(id) {
  return request({
    url: '/erp/productionOrder/' + id,
    method: 'get'
  })
}

// 新增生产订单
export function addProductionOrder(data) {
  return request({
    url: '/erp/productionOrder',
    method: 'post',
    data: data
  })
}

// 修改生产订单
export function updateProductionOrder(data) {
  return request({
    url: '/erp/productionOrder',
    method: 'put',
    data: data
  })
}

// 删除生产订单
export function delProductionOrder(id) {
  return request({
    url: '/erp/productionOrder/' + id,
    method: 'delete'
  })
}

// 更新生产订单状态
export function updateProductionOrderStatus(data) {
  return request({
    url: '/erp/productionOrder/updateStatus',
    method: 'put',
    data: data
  })
}

// 查询生产订单对应的工序列表
export function getProductionOrderProcesses(orderId) {
  return request({
    url: '/erp/productionOrder/' + orderId + '/processes',
    method: 'get'
  })
}