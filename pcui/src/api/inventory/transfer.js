import request from '@/utils/request'

// 查询调拨单表列表
export function listTransfer(query) {
  return request({
    url: '/inventory/transfer/list',
    method: 'get',
    params: query
  })
}

// 查询调拨单表详细
export function getTransfer(id) {
  return request({
    url: '/inventory/transfer/' + id,
    method: 'get'
  })
}

// 新增调拨单表
export function addTransfer(data) {
  return request({
    url: '/inventory/transfer',
    method: 'post',
    data: data
  })
}

// 修改调拨单表
export function updateTransfer(data) {
  return request({
    url: '/inventory/transfer',
    method: 'put',
    data: data
  })
}

// 删除调拨单表
export function delTransfer(id) {
  return request({
    url: '/inventory/transfer/' + id,
    method: 'delete'
  })
}

// 审核调拨单
export function approveTransfer(data) {
  return request({
    url: '/inventory/transfer/approve',
    method: 'put',
    data: data
  })
}

// 执行调拨单
export function executeTransfer(data) {
  return request({
    url: '/inventory/transfer/execute',
    method: 'put',
    data: data
  })
}

// 生成调拨单号
export function generateTransferNo() {
  return request({
    url: '/inventory/transfer/generateTransferNo',
    method: 'get'
  })
}
