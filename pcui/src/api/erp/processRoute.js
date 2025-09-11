import request from '@/utils/request'

// 查询工艺路线主表列表
export function listProcessRoute(query) {
  return request({
    url: '/erp/processRoute/list',
    method: 'get',
    params: query
  })
}

// 查询工艺路线主表详细
export function getProcessRoute(id) {
  return request({
    url: '/erp/processRoute/' + id,
    method: 'get'
  })
}

// 新增工艺路线主表
export function addProcessRoute(data) {
  return request({
    url: '/erp/processRoute',
    method: 'post',
    data: data
  })
}

// 修改工艺路线主表
export function updateProcessRoute(data) {
  return request({
    url: '/erp/processRoute',
    method: 'put',
    data: data
  })
}

// 删除工艺路线主表
export function delProcessRoute(id) {
  return request({
    url: '/erp/processRoute/' + id,
    method: 'delete'
  })
}
