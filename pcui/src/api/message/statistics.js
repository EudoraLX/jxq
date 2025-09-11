import request from '@/utils/request'

// 获取消息统计数据
export function getMessageStatistics() {
  return request({
    url: '/message/statistics',
    method: 'get'
  })
}

// 获取消息统计列表
export function getMessageStatisticsList(query) {
  return request({
    url: '/message/statistics/list',
    method: 'get',
    params: query
  })
}

// 获取消息类型统计
export function getMessageTypeStatistics(query) {
  return request({
    url: '/message/statistics/type',
    method: 'get',
    params: query
  })
}

// 获取消息级别统计
export function getMessageLevelStatistics(query) {
  return request({
    url: '/message/statistics/level',
    method: 'get',
    params: query
  })
}

// 获取消息趋势统计
export function getMessageTrendStatistics(query) {
  return request({
    url: '/message/statistics/trend',
    method: 'get',
    params: query
  })
}

// 获取用户消息统计
export function getUserMessageStatistics(userId) {
  return request({
    url: '/message/statistics/user/' + userId,
    method: 'get'
  })
}
