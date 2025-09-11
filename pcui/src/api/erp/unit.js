import request from '@/utils/request'

// 查询计量单位列表
export function listUnit(query) {
  return request({
    url: '/erp/unit/list',
    method: 'get',
    params: query
  })
}

// 查询计量单位详细
export function getUnit(id) {
  return request({
    url: '/erp/unit/' + id,
    method: 'get'
  })
}

// 新增计量单位
export function addUnit(data) {
  return request({
    url: '/erp/unit',
    method: 'post',
    data: data
  })
}

// 修改计量单位
export function updateUnit(data) {
  return request({
    url: '/erp/unit',
    method: 'put',
    data: data
  })
}

// 删除计量单位
export function delUnit(id) {
  return request({
    url: '/erp/unit/' + id,
    method: 'delete'
  })
}

// 计量单位状态修改
export function changeUnitStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/erp/unit/changeStatus',
    method: 'put',
    data: data
  })
}
