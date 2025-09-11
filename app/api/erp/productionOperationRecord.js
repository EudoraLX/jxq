import request from '@/utils/request'

// 查询生产操作记录列表
export function listProductionOperationRecord(query) {
  return request({
    url: '/erp/productionOperationRecord/list',
    method: 'get',
    params: query
  })
}

// 查询生产操作记录详细
export function getProductionOperationRecord(id) {
  return request({
    url: '/erp/productionOperationRecord/' + id,
    method: 'get'
  })
}

// 新增生产操作记录
export function addProductionOperationRecord(data) {
  return request({
    url: '/erp/productionOperationRecord',
    method: 'post',
    data: data
  })
}

// 修改生产操作记录
export function updateProductionOperationRecord(data) {
  return request({
    url: '/erp/productionOperationRecord',
    method: 'put',
    data: data
  })
}

// 删除生产操作记录
export function delProductionOperationRecord(id) {
  return request({
    url: '/erp/productionOperationRecord/' + id,
    method: 'delete'
  })
}

// 获取生产订单对应的工序列表
export function getProcessRouteDetails(orderId) {
  return request({
    url: '/erp/productionOrder/' + orderId + '/processes',
    method: 'get'
  })
}

// 获取最新操作记录
export function getLatestOperationRecord(params) {
  return request({
    url: '/erp/productionOperationRecord/latest',
    method: 'get',
    params: params
  })
}
