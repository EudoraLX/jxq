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

// 根据订单编码查询生产订单
export function getProductionOrderByCode(orderCode) {
  return request({
    url: '/erp/productionOrder/code/' + orderCode,
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
    url: '/erp/productionOrder/status',
    method: 'put',
    data: data
  })
}
