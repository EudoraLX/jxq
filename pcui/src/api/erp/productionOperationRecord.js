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
export function delProductionOperationRecord(ids) {
  return request({
    url: '/erp/productionOperationRecord/' + ids,
    method: 'delete'
  })
}

// 根据操作员ID获取最新的工序ID
export function getLatestProcessId(operatorId) {
  return request({
    url: '/erp/productionOperationRecord/getLatestProcessId/' + operatorId,
    method: 'get'
  })
}

// 根据生产订单ID和工序ID获取工作中心、设备、模具信息
export function getWorkCenterInfo(productionOrderId, processId) {
  return request({
    url: '/erp/productionOperationRecord/getWorkCenterInfo/' + productionOrderId + '/' + processId,
    method: 'get'
  })
}

// 根据生产订单ID获取工艺路线明细信息
export function getProcessRouteDetails(productionOrderId) {
  return request({
    url: '/erp/productionOperationRecord/getProcessRouteDetails/' + productionOrderId,
    method: 'get'
  })
}
