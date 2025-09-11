import request from '@/utils/request'

// 获取当前用户最新上岗状态
export function getCurrentUserStatus() {
  return request({
    url: '/erp/operatorDuty/currentUserStatus',
    method: 'get'
  })
}

// 根据工序编号查询工序信息
export function getProcessByCode(processCode) {
  return request({
    url: `/erp/operatorDuty/getProcessByCode/${processCode}`,
    method: 'get'
  })
}

// 切换上岗状态
export function toggleDutyStatus(data) {
  return request({
    url: '/erp/operatorDuty/toggleDutyStatus',
    method: 'post',
    data: data
  })
}
