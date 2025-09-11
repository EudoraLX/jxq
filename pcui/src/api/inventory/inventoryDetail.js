import request from '@/utils/request'

// 查询库存明细列表
export function listInventoryDetail(query) {
  return request({
    url: '/inventory/inventoryDetail/list',
    method: 'get',
    params: query
  })
}

// 查询库存明细详细
export function getInventoryDetail(id) {
  return request({
    url: '/inventory/inventoryDetail/' + id,
    method: 'get'
  })
}

// 新增库存明细
export function addInventoryDetail(data) {
  return request({
    url: '/inventory/inventoryDetail',
    method: 'post',
    data: data
  })
}

// 修改库存明细
export function updateInventoryDetail(data) {
  return request({
    url: '/inventory/inventoryDetail',
    method: 'put',
    data: data
  })
}

// 删除库存明细
export function delInventoryDetail(id) {
  return request({
    url: '/inventory/inventoryDetail/' + id,
    method: 'delete'
  })
}

// 查询物料在指定位置的库存
export function getInventoryByLocation(warehouseId, shelfId, locationId) {
  return request({
    url: '/inventory/inventoryDetail/location',
    method: 'get',
    params: {
      warehouseId: warehouseId,
      shelfId: shelfId,
      locationId: locationId
    }
  })
}

// 查询物料在指定仓库的所有位置
export function getInventoryByWarehouse(warehouseId, materialId) {
  return request({
    url: '/inventory/inventoryDetail/warehouse',
    method: 'get',
    params: {
      warehouseId: warehouseId,
      materialId: materialId
    }
  })
}

// 查询即将过期的库存
export function getExpiringInventory(days) {
  return request({
    url: '/inventory/inventoryDetail/expiring',
    method: 'get',
    params: {
      days: days
    }
  })
}

// 查询批次库存位置
export function getInventoryByBatch(batchNo) {
  return request({
    url: '/inventory/inventoryDetail/batch',
    method: 'get',
    params: {
      batchNo: batchNo
    }
  })
}
