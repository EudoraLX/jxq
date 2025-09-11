import request from '@/utils/request'

// 查询生产报工单列表
export function listProductionReport(query) {
  return request({
    url: '/erp/productionReport/list',
    method: 'get',
    params: query
  })
}

// 查询生产报工单详细
export function getProductionReport(id) {
  return request({
    url: '/erp/productionReport/' + id,
    method: 'get'
  })
}

// 新增生产报工单
export function addProductionReport(data) {
  return request({
    url: '/erp/productionReport',
    method: 'post',
    data: data
  })
}

// 修改生产报工单
export function updateProductionReport(data) {
  return request({
    url: '/erp/productionReport',
    method: 'put',
    data: data
  })
}

// 删除生产报工单
export function delProductionReport(id) {
  return request({
    url: '/erp/productionReport/' + id,
    method: 'delete'
  })
}
