import request from '@/utils/request'

// 查询仓库信息列表
export function listWarehouse(query) {
  return request({
    url: '/erp/warehouse/list',
    method: 'get',
    params: query
  })
}

// 查询仓库信息详细
export function getWarehouse(id) {
  return request({
    url: '/erp/warehouse/' + id,
    method: 'get'
  })
}

// 新增仓库信息
export function addWarehouse(data) {
  return request({
    url: '/erp/warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库信息
export function updateWarehouse(data) {
  return request({
    url: '/erp/warehouse',
    method: 'put',
    data: data
  })
}

// 删除仓库信息
export function delWarehouse(id) {
  return request({
    url: '/erp/warehouse/' + id,
    method: 'delete'
  })
}

// 获取可用仓库列表
export function getAvailableWarehouses() {
  return request({
    url: '/erp/warehouse/available',
    method: 'get'
  })
}
