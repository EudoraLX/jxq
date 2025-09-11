import request from '@/utils/request'

// 查询AI聊天会话列表
export function listSession(query) {
  return request({
    url: '/aichat/session/list',
    method: 'get',
    params: query
  })
}

// 查询AI聊天会话详细
export function getSession(sessionId) {
  return request({
    url: '/aichat/session/' + sessionId,
    method: 'get'
  })
}

// 新增AI聊天会话
export function addSession(data) {
  return request({
    url: '/aichat/session',
    method: 'post',
    data: data
  })
}

// 修改AI聊天会话
export function updateSession(data) {
  return request({
    url: '/aichat/session',
    method: 'put',
    data: data
  })
}

// 删除AI聊天会话
export function delSession(sessionId) {
  return request({
    url: '/aichat/session/' + sessionId,
    method: 'delete'
  })
}

// 查询AI聊天消息列表
export function listMessage(query) {
  return request({
    url: '/aichat/message/list',
    method: 'get',
    params: query
  })
}

// 查询AI聊天消息详细
export function getMessage(messageId) {
  return request({
    url: '/aichat/message/' + messageId,
    method: 'get'
  })
}

// 新增AI聊天消息
export function addMessage(data) {
  return request({
    url: '/aichat/message',
    method: 'post',
    data: data
  })
}

// 修改AI聊天消息
export function updateMessage(data) {
  return request({
    url: '/aichat/message',
    method: 'put',
    data: data
  })
}

// 删除AI聊天消息
export function delMessage(messageId) {
  return request({
    url: '/aichat/message/' + messageId,
    method: 'delete'
  })
}

// 发送聊天消息
export function sendMessage(data) {
  return request({
    url: '/aichat/send',
    method: 'post',
    data: data
  })
}

// 获取用户会话列表
export function getUserSessions() {
  return request({
    url: '/aichat/user/sessions',
    method: 'get'
  })
}

// 获取会话消息列表
export function getSessionMessages(sessionId) {
  return request({
    url: '/aichat/session/' + sessionId + '/messages',
    method: 'get'
  })
}

// 创建新会话
export function createNewSession(data) {
  return request({
    url: '/aichat/session/new',
    method: 'post',
    data: data
  })
}

// 标记消息为已读
export function markMessageAsRead(messageId) {
  return request({
    url: '/aichat/message/read/' + messageId,
    method: 'put'
  })
}

// 批量标记消息为已读
export function markMessagesAsRead(messageIds) {
  return request({
    url: '/aichat/message/read/batch',
    method: 'put',
    data: messageIds
  })
}

// 获取会话未读消息数量
export function getUnreadCount(sessionId) {
  return request({
    url: '/aichat/session/' + sessionId + '/unread',
    method: 'get'
  })
}

// 获取用户所有未读消息数量
export function getUserUnreadCount() {
  return request({
    url: '/aichat/user/unread',
    method: 'get'
  })
}
