import request from '@/utils/request'

// 查询物料分类列表
export function listMaterialCategory(query) {
  return request({
    url: '/erp/materialCategory/list',
    method: 'get',
    params: query
  })
}

// 查询物料分类详细
export function getMaterialCategory(id) {
  return request({
    url: '/erp/materialCategory/' + id,
    method: 'get'
  })
}

// 新增物料分类
export function addMaterialCategory(data) {
  return request({
    url: '/erp/materialCategory',
    method: 'post',
    data: data
  })
}

// 修改物料分类
export function updateMaterialCategory(data) {
  return request({
    url: '/erp/materialCategory',
    method: 'put',
    data: data
  })
}

// 删除物料分类
export function delMaterialCategory(id) {
  return request({
    url: '/erp/materialCategory/' + id,
    method: 'delete'
  })
}

// 导出物料分类
export function exportMaterialCategory(query) {
  return request({
    url: '/erp/materialCategory/export',
    method: 'get',
    params: query
  })
}
