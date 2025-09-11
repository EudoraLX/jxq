import request from '@/utils/request'

// 查询BOM管理列表
export function listBom(query) {
  return request({
    url: '/erp/bom/list',
    method: 'get',
    params: query
  })
}

// 查询BOM管理详细
export function getBom(id) {
  return request({
    url: '/erp/bom/' + id,
    method: 'get'
  })
}

// 新增BOM管理
export function addBom(data) {
  return request({
    url: '/erp/bom',
    method: 'post',
    data: data
  })
}

// 修改BOM管理
export function updateBom(data) {
  return request({
    url: '/erp/bom',
    method: 'put',
    data: data
  })
}

// 删除BOM管理
export function delBom(ids) {
  return request({
    url: '/erp/bom/' + ids,
    method: 'delete'
  })
}

// 查询BOM明细表列表
export function listBomDetail(query) {
  return request({
    url: '/erp/bomDetail/list',
    method: 'get',
    params: query
  })
}

// 查询BOM明细表详细
export function getBomDetail(id) {
  return request({
    url: '/erp/bomDetail/' + id,
    method: 'get'
  })
}

// 新增BOM明细表
export function addBomDetail(data) {
  return request({
    url: '/erp/bomDetail',
    method: 'post',
    data: data
  })
}

// 修改BOM明细表
export function updateBomDetail(data) {
  return request({
    url: '/erp/bomDetail',
    method: 'put',
    data: data
  })
}

// 删除BOM明细表
export function delBomDetail(ids) {
  return request({
    url: '/erp/bomDetail/' + ids,
    method: 'delete'
  })
}

// 查询BOM工艺路线明细表列表
export function listBomRouteDetail(query) {
  return request({
    url: '/erp/bomRouteDetail/list',
    method: 'get',
    params: query
  })
}

// 查询BOM工艺路线明细表详细
export function getBomRouteDetail(id) {
  return request({
    url: '/erp/bomRouteDetail/' + id,
    method: 'get'
  })
}

// 新增BOM工艺路线明细表
export function addBomRouteDetail(data) {
  return request({
    url: '/erp/bomRouteDetail',
    method: 'post',
    data: data
  })
}

// 修改BOM工艺路线明细表
export function updateBomRouteDetail(data) {
  return request({
    url: '/erp/bomRouteDetail',
    method: 'put',
    data: data
  })
}

// 删除BOM工艺路线明细表
export function delBomRouteDetail(ids) {
  return request({
    url: '/erp/bomRouteDetail/' + ids,
    method: 'delete'
  })
}

// 检查BOM冲突
export function checkBomConflict(productId, effectiveDate) {
  return request({
    url: '/erp/bom/checkConflict',
    method: 'get',
    params: {
      productId: productId,
      effectiveDate: effectiveDate
    }
  })
}

// 审批BOM
export function approveBom(data) {
  return request({
    url: '/erp/bom/approve',
    method: 'post',
    data: data
  })
}