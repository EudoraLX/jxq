import request from '@/utils/request'

// 查询在岗离岗记录列表
export function listOperatorDuty(query) {
  return request({
    url: '/erp/operatorDuty/list',
    method: 'get',
    params: query
  })
}

// 查询在岗离岗记录详细
export function getOperatorDuty(id) {
  return request({
    url: '/erp/operatorDuty/' + id,
    method: 'get'
  })
}

// 新增在岗离岗记录
export function addOperatorDuty(data) {
  return request({
    url: '/erp/operatorDuty',
    method: 'post',
    data: data
  })
}

// 修改在岗离岗记录
export function updateOperatorDuty(data) {
  return request({
    url: '/erp/operatorDuty',
    method: 'put',
    data: data
  })
}

// 删除在岗离岗记录
export function delOperatorDuty(id) {
  return request({
    url: '/erp/operatorDuty/' + id,
    method: 'delete'
  })
}
