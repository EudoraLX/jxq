import request from '@/utils/request'

// 查询入库单表列表
export function listInbound(query) {
  return request({
    url: '/inventory/inbound/list',
    method: 'get',
    params: query
  })
}

// 查询入库单表详细
export function getInbound(id) {
  return request({
    url: '/inventory/inbound/' + id,
    method: 'get'
  })
}

// 新增入库单表
export function addInbound(data) {
  return request({
    url: '/inventory/inbound',
    method: 'post',
    data: data
  })
}

// 修改入库单表
export function updateInbound(data) {
  return request({
    url: '/inventory/inbound',
    method: 'put',
    data: data
  })
}

// 删除入库单表
export function delInbound(id) {
  return request({
    url: '/inventory/inbound/' + id,
    method: 'delete'
  })
}

// 根据入库单号查询入库单
export function getInboundByInboundNo(inboundNo) {
  return request({
    url: '/inventory/inbound/getByInboundNo',
    method: 'get',
    params: {
      inboundNo: inboundNo
    }
  })
}

// 审核入库单
export function approveInbound(data) {
  return request({
    url: '/inventory/inbound/approve',
    method: 'post',
    data: data
  })
}

// 执行入库单
export function executeInbound(data) {
  return request({
    url: '/inventory/inbound/execute',
    method: 'post',
    data: data
  })
}

// 取消入库单
export function cancelInbound(data) {
  return request({
    url: '/inventory/inbound/cancel',
    method: 'post',
    data: data
  })
}

// 生成入库单号
export function generateInboundNo() {
  return request({
    url: '/inventory/inbound/generateInboundNo',
    method: 'get'
  })
}

// 查询入库单统计信息
export function getInboundStatistics(query) {
  return request({
    url: '/inventory/inbound/statistics',
    method: 'get',
    params: query
  })
}
