import request from '@/utils/request'

// 查询货位信息列表
export function listLocation(query) {
  return request({
    url: '/erp/location/list',
    method: 'get',
    params: query
  })
}

// 查询货位信息详细
export function getLocation(id) {
  return request({
    url: '/erp/location/' + id,
    method: 'get'
  })
}

// 新增货位信息
export function addLocation(data) {
  return request({
    url: '/erp/location',
    method: 'post',
    data: data
  })
}

// 修改货位信息
export function updateLocation(data) {
  return request({
    url: '/erp/location',
    method: 'put',
    data: data
  })
}

// 删除货位信息
export function delLocation(id) {
  return request({
    url: '/erp/location/' + id,
    method: 'delete'
  })
}

// 根据货架ID获取货位列表
export function getLocationsByShelf(shelfId) {
  return request({
    url: '/erp/location/shelf/' + shelfId,
    method: 'get'
  })
}

// 根据仓库ID获取货位列表
export function getLocationsByWarehouse(warehouseId) {
  return request({
    url: '/erp/location/warehouse/' + warehouseId,
    method: 'get'
  })
}
