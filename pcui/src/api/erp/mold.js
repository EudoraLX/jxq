import request from '@/utils/request'

// 查询模具列表
export function listMold(query) {
  return request({
    url: '/erp/mold/list',
    method: 'get',
    params: query
  })
}

// 查询模具详细
export function getMold(id) {
  return request({
    url: '/erp/mold/' + id,
    method: 'get'
  })
}

// 新增模具
export function addMold(data) {
  return request({
    url: '/erp/mold',
    method: 'post',
    data: data
  })
}

// 修改模具
export function updateMold(data) {
  return request({
    url: '/erp/mold',
    method: 'put',
    data: data
  })
}

// 删除模具
export function delMold(id) {
  return request({
    url: '/erp/mold/' + id,
    method: 'delete'
  })
}
