import request from '@/utils/request'

// 查询盘点单表列表
export function listCount(query) {
  return request({
    url: '/inventory/count/list',
    method: 'get',
    params: query
  })
}

// 查询盘点单表详细
export function getCount(id) {
  return request({
    url: '/inventory/count/' + id,
    method: 'get'
  })
}

// 新增盘点单表
export function addCount(data) {
  return request({
    url: '/inventory/count',
    method: 'post',
    data: data
  })
}

// 修改盘点单表
export function updateCount(data) {
  return request({
    url: '/inventory/count',
    method: 'put',
    data: data
  })
}

// 删除盘点单表
export function delCount(id) {
  return request({
    url: '/inventory/count/' + id,
    method: 'delete'
  })
}

// 根据盘点单号查询盘点单
export function getCountByCountNo(countNo) {
  return request({
    url: '/inventory/count/getByCountNo',
    method: 'get',
    params: {
      countNo: countNo
    }
  })
}

// 审核盘点单
export function approveCount(data) {
  return request({
    url: '/inventory/count/approve',
    method: 'post',
    data: data
  })
}

// 执行盘点单
export function executeCount(data) {
  return request({
    url: '/inventory/count/execute',
    method: 'post',
    data: data
  })
}

// 调整盘点单
export function adjustCount(data) {
  return request({
    url: '/inventory/count/adjust',
    method: 'post',
    data: data
  })
}

// 取消盘点单
export function cancelCount(data) {
  return request({
    url: '/inventory/count/cancel',
    method: 'post',
    data: data
  })
}

// 生成盘点单号
export function generateCountNo() {
  return request({
    url: '/inventory/count/generateCountNo',
    method: 'get'
  })
}

// 查询盘点单统计信息
export function getCountStatistics(query) {
  return request({
    url: '/inventory/count/statistics',
    method: 'get',
    params: query
  })
}

// 生成盘点明细
export function generateDetails(data) {
  return request({
    url: '/inventory/count/generateDetails',
    method: 'post',
    data: data
  })
}

// 计算盘点差异
export function calculateDifferences(data) {
  return request({
    url: '/inventory/count/calculateDifferences',
    method: 'post',
    data: data
  })
}

// 查询盘点差异列表
export function getCountDifferences(query) {
  return request({
    url: '/inventory/count/differences',
    method: 'get',
    params: query
  })
}
