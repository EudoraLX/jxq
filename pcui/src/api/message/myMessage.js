import request from '@/utils/request'

// 查询我的消息列表
export function getMyMessageList(query) {
  return request({
    url: '/message/message/my/list',
    method: 'get',
    params: query
  })
}

// 查询我的消息详情
export function getMyMessageDetail(messageId) {
  return request({
    url: '/message/message/my/' + messageId,
    method: 'get'
  })
}

// 标记消息为已读
export function markAsRead(messageId) {
  return request({
    url: '/message/message/my/read/' + messageId,
    method: 'put'
  })
}

// 批量标记消息为已读
export function markAsReadBatch(messageIds) {
  return request({
    url: '/message/message/my/read/batch',
    method: 'put',
    data: messageIds
  })
}

// 标记消息为未读
export function markAsUnread(messageId) {
  return request({
    url: '/message/message/my/unread/' + messageId,
    method: 'put'
  })
}

// 删除我的消息
export function deleteMyMessage(messageId) {
  return request({
    url: '/message/message/my/' + messageId,
    method: 'delete'
  })
}

// 批量删除我的消息
export function deleteMyMessageBatch(messageIds) {
  return request({
    url: '/message/message/my/batch',
    method: 'delete',
    data: messageIds
  })
}

// 获取我的消息统计
export function getMyMessageStatistics() {
  return request({
    url: '/message/message/my/statistics',
    method: 'get'
  })
}