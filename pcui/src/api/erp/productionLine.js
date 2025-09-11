import request from '@/utils/request'

// 查询生产线列表
export function listProductionLine(query) {
  return request({
    url: '/erp/productionLine/list',
    method: 'get',
    params: query
  })
}

// 查询生产线详细
export function getProductionLine(id) {
  return request({
    url: '/erp/productionLine/' + id,
    method: 'get'
  })
}

// 新增生产线
export function addProductionLine(data) {
  return request({
    url: '/erp/productionLine',
    method: 'post',
    data: data
  })
}

// 修改生产线
export function updateProductionLine(data) {
  return request({
    url: '/erp/productionLine',
    method: 'put',
    data: data
  })
}

// 删除生产线
export function delProductionLine(id) {
  return request({
    url: '/erp/productionLine/' + id,
    method: 'delete'
  })
}

// 获取可用的生产线列表（已启用且状态正常）
export function getAvailableProductionLines() {
  return request({
    url: '/erp/productionLine/available',
    method: 'get'
  })
}
