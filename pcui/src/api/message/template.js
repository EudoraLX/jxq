import request from '@/utils/request'

// 查询消息模板列表
export function listTemplate(query) {
  return request({
    url: '/message/template/list',
    method: 'get',
    params: query
  })
}

// 查询消息模板详细
export function getTemplate(templateId) {
  return request({
    url: '/message/template/' + templateId,
    method: 'get'
  })
}

// 新增消息模板
export function addTemplate(data) {
  return request({
    url: '/message/template',
    method: 'post',
    data: data
  })
}

// 修改消息模板
export function updateTemplate(data) {
  return request({
    url: '/message/template',
    method: 'put',
    data: data
  })
}

// 删除消息模板
export function delTemplate(templateId) {
  return request({
    url: '/message/template/' + templateId,
    method: 'delete'
  })
}

// 根据模板编码获取模板
export function getTemplateByCode(templateCode) {
  return request({
    url: '/message/template/code/' + templateCode,
    method: 'get'
  })
}

// 预览模板
export function previewTemplate(data) {
  return request({
    url: '/message/template/preview',
    method: 'post',
    data: data
  })
}
