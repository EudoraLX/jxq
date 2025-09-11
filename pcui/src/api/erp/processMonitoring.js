import request from '@/utils/request'

// 查询工艺参数监控列表
export function listProcessMonitoring(query) {
  return request({
    url: '/erp/monitoring/list',
    method: 'get',
    params: query
  })
}

// 查询工艺参数监控详细
export function getProcessMonitoring(id) {
  return request({
    url: '/erp/monitoring/' + id,
    method: 'get'
  })
}

// 新增工艺参数监控
export function addProcessMonitoring(data) {
  return request({
    url: '/erp/monitoring',
    method: 'post',
    data: data
  })
}

// 修改工艺参数监控
export function updateProcessMonitoring(data) {
  return request({
    url: '/erp/monitoring',
    method: 'put',
    data: data
  })
}

// 删除工艺参数监控
export function delProcessMonitoring(ids) {
  return request({
    url: '/erp/monitoring/' + ids,
    method: 'delete'
  })
}

// 根据工艺ID获取工艺参数列表
export function getProcessParameters(processId) {
  return request({
    url: '/erp/monitoring/parameters/' + processId,
    method: 'get'
  })
}

// 根据生产订单ID和工艺ID获取工作中心信息
export function getWorkCenterInfo(productionOrderId, processId) {
  return request({
    url: '/erp/monitoring/workcenter/' + productionOrderId + '/' + processId,
    method: 'get'
  })
}

// 计算偏差和偏差率
export function calculateDeviation(data) {
  return request({
    url: '/erp/monitoring/calculate',
    method: 'post',
    data: data
  })
}

// 根据生产订单ID获取工艺路线明细信息
export function getProcessRouteDetails(productionOrderId) {
  return request({
    url: '/erp/monitoring/getProcessRouteDetails/' + productionOrderId,
    method: 'get'
  })
}
