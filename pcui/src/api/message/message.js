import request from '@/utils/request'

// 查询消息列表
export function listMessage(query) {
  return request({
    url: '/message/message/list',
    method: 'get',
    params: query
  })
}

// 查询消息详细
export function getMessage(messageId) {
  return request({
    url: '/message/message/' + messageId,
    method: 'get'
  })
}

// 查询消息详细（包含接收者信息）
export function getMessageDetail(messageId) {
  return request({
    url: '/message/message/detail/' + messageId,
    method: 'get'
  })
}

// 新增消息
export function addMessage(data) {
  return request({
    url: '/message/message',
    method: 'post',
    data: data
  })
}

// 修改消息
export function updateMessage(data) {
  return request({
    url: '/message/message',
    method: 'put',
    data: data
  })
}

// 删除消息
export function delMessage(messageId) {
  return request({
    url: '/message/message/' + messageId,
    method: 'delete'
  })
}

// 发送消息
export function sendMessage(data) {
  return request({
    url: '/message/message/send',
    method: 'post',
    data: data
  })
}

// 推送消息
export function pushMessage(messageId) {
  return request({
    url: '/message/message/push/' + messageId,
    method: 'post'
  })
}

// 标记消息为已读
export function markAsRead(messageId) {
  return request({
    url: '/message/message/read/' + messageId,
    method: 'post'
  })
}

// 批量标记消息为已读
export function markAsReadBatch(messageIds) {
  return request({
    url: '/message/message/read/batch',
    method: 'post',
    data: messageIds
  })
}

// 获取用户未读消息数量
export function getUnreadCount() {
  return request({
    url: '/message/message/unread/count',
    method: 'get'
  })
}

// 获取用户消息列表
export function getUserMessageList(query) {
  return request({
    url: '/message/message/user/list',
    method: 'get',
    params: query
  })
}
