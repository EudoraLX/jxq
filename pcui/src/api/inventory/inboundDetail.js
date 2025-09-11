import request from '@/utils/request'

// 查询入库单明细列表
export function listInboundOrderDetail(query) {
  return request({
    url: '/inventory/inboundOrderDetail/list',
    method: 'get',
    params: query
  })
}

// 查询入库单明细详细
export function getInboundOrderDetail(id) {
  return request({
    url: '/inventory/inboundOrderDetail/' + id,
    method: 'get'
  })
}

// 新增入库单明细
export function addInboundOrderDetail(data) {
  return request({
    url: '/inventory/inboundOrderDetail',
    method: 'post',
    data: data
  })
}

// 修改入库单明细
export function updateInboundOrderDetail(data) {
  return request({
    url: '/inventory/inboundOrderDetail',
    method: 'put',
    data: data
  })
}

// 删除入库单明细
export function delInboundOrderDetail(id) {
  return request({
    url: '/inventory/inboundOrderDetail/' + id,
    method: 'delete'
  })
}

// 批量删除入库单明细
export function delInboundOrderDetails(ids) {
  return request({
    url: '/inventory/inboundOrderDetail/' + ids,
    method: 'delete'
  })
}

// 根据入库单ID查询明细列表
export function getInboundOrderDetailsByInboundId(inboundId) {
  return request({
    url: '/inventory/inboundOrderDetail/inbound/' + inboundId,
    method: 'get'
  })
}

// 批量新增入库单明细
export function addInboundOrderDetails(data) {
  return request({
    url: '/inventory/inboundOrderDetail/batch',
    method: 'post',
    data: data
  })
}

// 批量更新入库单明细
export function updateInboundOrderDetails(data) {
  return request({
    url: '/inventory/inboundOrderDetail/batch',
    method: 'put',
    data: data
  })
}
