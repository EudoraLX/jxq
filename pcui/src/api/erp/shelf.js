import request from '@/utils/request'

// 查询货架信息列表
export function listShelf(query) {
  return request({
    url: '/erp/shelf/list',
    method: 'get',
    params: query
  })
}

// 查询货架信息详细
export function getShelf(id) {
  return request({
    url: '/erp/shelf/' + id,
    method: 'get'
  })
}

// 新增货架信息
export function addShelf(data) {
  return request({
    url: '/erp/shelf',
    method: 'post',
    data: data
  })
}

// 修改货架信息
export function updateShelf(data) {
  return request({
    url: '/erp/shelf',
    method: 'put',
    data: data
  })
}

// 删除货架信息
export function delShelf(id) {
  return request({
    url: '/erp/shelf/' + id,
    method: 'delete'
  })
}

// 根据仓库ID获取货架列表
export function getShelvesByWarehouse(warehouseId) {
  return request({
    url: '/erp/shelf/warehouse/' + warehouseId,
    method: 'get'
  })
}
