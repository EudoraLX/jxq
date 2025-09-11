import request from '@/utils/request'

// 查询生产日历列表
export function listProductionCalendar(query) {
  return request({
    url: '/erp/productionCalendar/list',
    method: 'get',
    params: query
  })
}

// 查询生产日历详细
export function getProductionCalendar(id) {
  return request({
    url: '/erp/productionCalendar/' + id,
    method: 'get'
  })
}

// 新增生产日历
export function addProductionCalendar(data) {
  return request({
    url: '/erp/productionCalendar',
    method: 'post',
    data: data
  })
}

// 修改生产日历
export function updateProductionCalendar(data) {
  return request({
    url: '/erp/productionCalendar',
    method: 'put',
    data: data
  })
}

// 删除生产日历
export function delProductionCalendar(ids) {
  return request({
    url: '/erp/productionCalendar/' + ids,
    method: 'delete'
  })
}

// 根据生产线ID查询生产日历
export function getProductionCalendarByLineId(productionLineId) {
  return request({
    url: '/erp/productionCalendar/line/' + productionLineId,
    method: 'get'
  })
}

// 根据日期范围查询生产日历
export function getProductionCalendarByDateRange(startDate, endDate) {
  return request({
    url: '/erp/productionCalendar/range',
    method: 'get',
    params: {
      startDate: startDate,
      endDate: endDate
    }
  })
}

// 生成生产日历
export function generateProductionCalendar(data) {
  return request({
    url: '/erp/productionCalendar/generate',
    method: 'post',
    data: data
  })
}

// 复制生产日历
export function copyProductionCalendar(sourceCalendarId, data) {
  return request({
    url: '/erp/productionCalendar/copy/' + sourceCalendarId,
    method: 'post',
    data: data
  })
}
