import request from '@/utils/request'

// 获取用户消息列表
export function getUserMessageList(query) {
  return request({
    url: '/message/message/mobile/my/list',
    method: 'get',
    params: query
  })
}

// 获取消息详情
export function getMessage(messageId) {
  return request({
    url: '/message/message/mobile/my/' + messageId,
    method: 'get'
  })
}

// 标记消息为已读
export function markAsRead(messageId) {
  return request({
    url: '/message/message/mobile/my/read/' + messageId,
    method: 'put'
  })
}

// 批量标记消息为已读
export function markAsReadBatch(messageIds) {
  return request({
    url: '/message/message/mobile/my/read/batch',
    method: 'put',
    data: messageIds
  })
}

// 获取未读消息数量
export function getUnreadCount() {
  return request({
    url: '/message/message/mobile/my/statistics',
    method: 'get'
  })
}