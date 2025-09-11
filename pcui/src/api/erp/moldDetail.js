import request from '@/utils/request'

// 查询模具明细列表
export function listMoldDetail(query) {
  return request({
    url: '/erp/moldDetail/list',
    method: 'get',
    params: query
  })
}

// 查询模具明细详细
export function getMoldDetail(id) {
  return request({
    url: '/erp/moldDetail/' + id,
    method: 'get'
  })
}

// 新增模具明细
export function addMoldDetail(data) {
  return request({
    url: '/erp/moldDetail',
    method: 'post',
    data: data
  })
}

// 修改模具明细
export function updateMoldDetail(data) {
  return request({
    url: '/erp/moldDetail',
    method: 'put',
    data: data
  })
}

// 删除模具明细
export function delMoldDetail(id) {
  return request({
    url: '/erp/moldDetail/' + id,
    method: 'delete'
  })
}
