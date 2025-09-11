import request from '@/utils/request'

// 查询编号规则列表
export function listNumberRule(query) {
  return request({
    url: '/erp/numberRule/list',
    method: 'get',
    params: query
  })
}

// 查询编号规则详细
export function getNumberRule(ruleId) {
  return request({
    url: '/erp/numberRule/' + ruleId,
    method: 'get'
  })
}

// 新增编号规则
export function addNumberRule(data) {
  return request({
    url: '/erp/numberRule',
    method: 'post',
    data: data
  })
}

// 修改编号规则
export function updateNumberRule(data) {
  return request({
    url: '/erp/numberRule',
    method: 'put',
    data: data
  })
}

// 删除编号规则
export function delNumberRule(ruleId) {
  return request({
    url: '/erp/numberRule/' + ruleId,
    method: 'delete'
  })
}
