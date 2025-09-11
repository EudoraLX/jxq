import request from '@/utils/request'

// 查询工序交接单列表
export function listProcessHandover(query) {
  return request({
    url: '/erp/processHandover/list',
    method: 'get',
    params: query
  })
}

// 查询工序交接单详细
export function getProcessHandover(id) {
  return request({
    url: '/erp/processHandover/' + id,
    method: 'get'
  })
}

// 新增工序交接单
export function addProcessHandover(data) {
  return request({
    url: '/erp/processHandover',
    method: 'post',
    data: data
  })
}

// 修改工序交接单
export function updateProcessHandover(data) {
  return request({
    url: '/erp/processHandover',
    method: 'put',
    data: data
  })
}

// 删除工序交接单
export function delProcessHandover(id) {
  return request({
    url: '/erp/processHandover/' + id,
    method: 'delete'
  })
}
