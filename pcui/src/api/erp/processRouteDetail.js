import request from '@/utils/request'

// 查询工艺路线明细列表
export function listProcessRouteDetail(query) {
  return request({
    url: '/erp/processRouteDetail/list',
    method: 'get',
    params: query
  })
}

// 根据工艺路线ID查询明细列表
export function listProcessRouteDetailByRouteId(processRouteId) {
  return request({
    url: '/erp/processRouteDetail/listByRouteId/' + processRouteId,
    method: 'get'
  })
}

// 查询工艺路线明细详细
export function getProcessRouteDetail(id) {
  return request({
    url: '/erp/processRouteDetail/' + id,
    method: 'get'
  })
}

// 新增工艺路线明细
export function addProcessRouteDetail(data) {
  return request({
    url: '/erp/processRouteDetail',
    method: 'post',
    data: data
  })
}

// 修改工艺路线明细
export function updateProcessRouteDetail(data) {
  return request({
    url: '/erp/processRouteDetail',
    method: 'put',
    data: data
  })
}

// 删除工艺路线明细
export function delProcessRouteDetail(id) {
  return request({
    url: '/erp/processRouteDetail/' + id,
    method: 'delete'
  })
}
