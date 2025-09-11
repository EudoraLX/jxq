import request from '@/utils/request'

// 查询物料主数据列表
export function listMasterMaterial(query) {
  return request({
    url: '/erp/masterMaterial/list',
    method: 'get',
    params: query
  })
}

// 查询物料主数据详细
export function getMasterMaterial(id) {
  return request({
    url: '/erp/masterMaterial/' + id,
    method: 'get'
  })
}

// 新增物料主数据
export function addMasterMaterial(data) {
  return request({
    url: '/erp/masterMaterial',
    method: 'post',
    data: data
  })
}

// 修改物料主数据
export function updateMasterMaterial(data) {
  return request({
    url: '/erp/masterMaterial',
    method: 'put',
    data: data
  })
}

// 删除物料主数据
export function delMasterMaterial(id) {
  return request({
    url: '/erp/masterMaterial/' + id,
    method: 'delete'
  })
}
