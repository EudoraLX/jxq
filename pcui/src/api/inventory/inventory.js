import request from '@/utils/request'

// 查询库存主表列表
export function listInventory(query) {
  return request({
    url: '/inventory/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询库存主表详细
export function getInventory(id) {
  return request({
    url: '/inventory/inventory/' + id,
    method: 'get'
  })
}

// 新增库存主表
export function addInventory(data) {
  return request({
    url: '/inventory/inventory',
    method: 'post',
    data: data
  })
}

// 修改库存主表
export function updateInventory(data) {
  return request({
    url: '/inventory/inventory',
    method: 'put',
    data: data
  })
}

// 删除库存主表
export function delInventory(id) {
  return request({
    url: '/inventory/inventory/' + id,
    method: 'delete'
  })
}

// 根据物料ID和仓库ID查询库存
export function getInventoryByMaterialAndWarehouse(materialId, warehouseId) {
  return request({
    url: '/inventory/inventory/getByMaterialAndWarehouse',
    method: 'get',
    params: {
      materialId: materialId,
      warehouseId: warehouseId
    }
  })
}

// 查询库存预警列表
export function getInventoryAlertList(query) {
  return request({
    url: '/inventory/inventory/alertList',
    method: 'get',
    params: query
  })
}

// 增加库存
export function increaseInventory(data) {
  return request({
    url: '/inventory/inventory/increase',
    method: 'post',
    data: data
  })
}

// 减少库存
export function decreaseInventory(data) {
  return request({
    url: '/inventory/inventory/decrease',
    method: 'post',
    data: data
  })
}

// 分配库存
export function allocateInventory(data) {
  return request({
    url: '/inventory/inventory/allocate',
    method: 'post',
    data: data
  })
}

// 释放库存
export function releaseInventory(data) {
  return request({
    url: '/inventory/inventory/release',
    method: 'post',
    data: data
  })
}

// 冻结库存
export function freezeInventory(data) {
  return request({
    url: '/inventory/inventory/freeze',
    method: 'post',
    data: data
  })
}

// 解冻库存
export function unfreezeInventory(data) {
  return request({
    url: '/inventory/inventory/unfreeze',
    method: 'post',
    data: data
  })
}
