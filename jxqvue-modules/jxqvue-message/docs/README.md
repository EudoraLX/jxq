# 消息管理模块

## 模块概述

消息管理模块是一个完整的消息系统，支持系统消息、业务消息和通知消息的管理。该模块包含后端API、移动端界面和WebSocket实时推送功能。

## 功能特性

### 后端功能
- **消息管理**：支持消息的增删改查操作
- **消息模板**：支持消息模板的创建和管理
- **消息推送**：基于WebSocket的实时消息推送
- **消息统计**：提供消息相关的统计信息
- **多接收者支持**：支持一条消息发送给多个用户

### 移动端功能
- **消息列表**：显示用户的所有消息，支持分页加载
- **消息详情**：查看消息的详细内容
- **未读消息统计**：显示未读消息数量
- **消息状态管理**：支持标记已读、批量已读等操作
- **实时更新**：支持下拉刷新和上拉加载更多

## 技术架构

### 后端技术栈
- **Spring Boot**：主框架
- **MyBatis**：数据持久层
- **WebSocket**：实时推送
- **MySQL**：数据存储

### 移动端技术栈
- **UniApp**：跨平台开发框架
- **Vue.js**：前端框架
- **SCSS**：样式预处理器

## 数据库设计

### 核心表结构

1. **message** - 消息表
   - 存储消息的基本信息
   - 支持消息类型、级别、状态等属性

2. **message_receiver** - 消息接收者表
   - 存储消息与接收者的关联关系
   - 支持消息状态跟踪

3. **message_template** - 消息模板表
   - 存储消息模板信息
   - 支持模板变量替换

4. **message_template_variable** - 模板变量表
   - 存储模板变量定义
   - 支持变量类型和验证

5. **message_template_category** - 模板分类表
   - 存储模板分类信息
   - 支持分类层级管理

6. **message_statistics** - 消息统计表
   - 存储用户消息统计信息
   - 支持各种维度的统计

## API接口

### 消息管理接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 查询消息列表 | GET | /message/message/list | 获取消息列表 |
| 获取消息详情 | GET | /message/message/{id} | 获取消息详情 |
| 新增消息 | POST | /message/message | 创建新消息 |
| 修改消息 | PUT | /message/message | 更新消息 |
| 删除消息 | DELETE | /message/message/{ids} | 删除消息 |
| 发送消息 | POST | /message/message/send | 发送消息 |
| 推送消息 | PUT | /message/message/push/{id} | 推送消息 |

### 用户消息接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 用户消息列表 | GET | /message/message/user/list | 获取用户消息列表 |
| 用户消息详情 | GET | /message/message/user/detail/{id} | 获取用户消息详情 |
| 未读消息数量 | GET | /message/message/unread/count | 获取未读消息数量 |
| 标记已读 | PUT | /message/message/read/{id} | 标记消息已读 |
| 批量标记已读 | PUT | /message/message/read/batch | 批量标记已读 |

### 消息模板接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 模板列表 | GET | /message/template/list | 获取模板列表 |
| 模板详情 | GET | /message/template/{id} | 获取模板详情 |
| 新增模板 | POST | /message/template | 创建新模板 |
| 修改模板 | PUT | /message/template | 更新模板 |
| 删除模板 | DELETE | /message/template/{ids} | 删除模板 |

### 消息统计接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 统计概览 | GET | /message/statistics/overview | 获取消息统计概览 |
| 未读数量 | GET | /message/statistics/unread/count | 获取未读消息数量 |

## WebSocket推送

### 连接地址
```
ws://localhost:8080/websocket/message?userId={userId}
```

### 推送消息格式

#### 消息推送
```json
{
  "type": "message",
  "messageId": 1,
  "title": "消息标题",
  "content": "消息内容",
  "messageType": "1",
  "messageLevel": "1",
  "senderName": "发送者",
  "createTime": "2024-01-01 12:00:00"
}
```

#### 未读数量更新
```json
{
  "type": "unreadCount",
  "count": 5
}
```

## 移动端页面

### 消息列表页面 (`/pages/message/index.vue`)
- 显示消息统计卡片
- 消息列表展示
- 支持下拉刷新和上拉加载
- 未读消息高亮显示
- 批量标记已读功能

### 消息详情页面 (`/pages/message/detail.vue`)
- 消息详细内容展示
- 消息元信息显示
- 自动标记已读
- 错误处理和重试机制

## 部署说明

### 1. 数据库初始化
```sql
-- 执行表结构创建脚本
source message_tables.sql

-- 执行数据字典配置
source message_dict.sql

-- 执行菜单权限配置
source message_menu.sql
```

### 2. 后端部署
1. 确保MySQL数据库已创建并配置
2. 修改数据库连接配置
3. 启动Spring Boot应用
4. 验证API接口是否正常

### 3. 移动端部署
1. 确保后端API服务正常运行
2. 修改API请求地址配置
3. 编译并部署到移动端平台

## 配置说明

### 数据字典配置
- `message_type`：消息类型（系统消息、业务消息、通知消息）
- `message_level`：消息级别（普通、重要、紧急）
- `message_status`：消息状态（未读、已读、已删除）
- `push_status`：推送状态（未推送、已推送）
- `template_type`：模板类型（系统、业务、审批、通知）
- `variable_type`：变量类型（字符串、数字、日期时间、布尔值）

### 权限配置
- `message:message:*`：消息管理权限
- `message:template:*`：消息模板权限
- `message:statistics:*`：消息统计权限
- `mobile:message:*`：移动端消息权限

## 使用示例

### 发送消息
```javascript
// 创建消息请求
const messageRequest = {
  title: "系统通知",
  content: "系统将于今晚进行维护",
  messageType: "1",
  messageLevel: "2",
  senderId: 1,
  senderName: "系统管理员",
  receivers: [
    { receiverId: 2, receiverName: "用户1" },
    { receiverId: 3, receiverName: "用户2" }
  ]
};

// 发送请求
fetch('/message/message', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(messageRequest)
});
```

### 移动端获取消息列表
```javascript
import { getUserMessageList } from '@/api/message'

// 获取消息列表
const getMessages = async () => {
  try {
    const response = await getUserMessageList({
      pageNum: 1,
      pageSize: 10
    });
    console.log(response.data);
  } catch (error) {
    console.error('获取消息失败:', error);
  }
};
```

## 注意事项

1. **数据库连接**：确保数据库连接配置正确
2. **WebSocket连接**：确保WebSocket服务正常启动
3. **权限配置**：确保相关菜单和按钮权限已正确配置
4. **移动端适配**：确保移动端页面在不同设备上正常显示
5. **性能优化**：大量消息时考虑分页和索引优化

## 更新日志

### v1.0.0 (2024-01-01)
- 初始版本发布
- 实现基础消息管理功能
- 支持消息模板和变量替换
- 实现WebSocket实时推送
- 完成移动端界面开发
