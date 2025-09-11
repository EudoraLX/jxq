# AI智能客服模块使用说明

## 概述

本模块实现了基于OpenRouter.ai的全局智能客服功能，提供悬浮式聊天按钮，支持PC端使用。

## 功能特性

- 🎯 **全局悬浮按钮** - 固定在页面右下角，支持拖拽
- 💬 **实时聊天** - 支持与AI进行实时对话
- 📱 **会话管理** - 支持多会话管理，历史记录保存
- 🔔 **未读提醒** - 显示未读消息数量，支持消息已读标记
- 🎨 **美观界面** - 现代化UI设计，响应式布局
- ⚡ **快速响应** - 基于OpenRouter免费模型，响应迅速

## 技术架构

### 后端技术栈
- **Spring Boot 2.5.15** - 主框架
- **MyBatis** - 数据持久层
- **WebFlux** - 异步HTTP客户端
- **OpenRouter API** - AI模型接口

### 前端技术栈
- **Vue 2.x** - 前端框架
- **Element UI** - UI组件库
- **Axios** - HTTP客户端

## 安装配置

### 1. 数据库配置

执行SQL脚本创建相关表结构：

```sql
-- 执行 sql/ai_chat_tables.sql 文件
source sql/ai_chat_tables.sql
```

### 2. 环境配置

在 `application.yml` 中配置OpenRouter API密钥：

```yaml
openrouter:
  # 替换为您的实际API密钥
  api-key: your-actual-api-key-here
  base-url: https://openrouter.ai/api/v1
  model: deepseek/deepseek-r1:free:online
  max-tokens: 1000
  temperature: 0.7
  timeout: 30000
```

### 3. 获取OpenRouter API密钥

1. 访问 [OpenRouter官网](https://openrouter.ai/)
2. 注册并登录账户
3. 在API设置页面生成API密钥
4. 将密钥配置到环境变量或配置文件中

### 4. 启动应用

```bash
# 编译项目
mvn clean compile

# 启动后端服务
mvn spring-boot:run

# 启动前端服务
cd pcui
npm install
npm run dev
```

## 使用说明

### 1. 基本使用

1. 登录系统后，在页面右下角会看到AI客服悬浮按钮
2. 点击按钮打开聊天窗口
3. 输入问题并发送，AI会实时回复
4. 支持多轮对话，历史记录自动保存

### 2. 会话管理

- **新建会话**：点击聊天窗口中的"+"按钮
- **切换会话**：在会话列表中点击对应会话
- **查看历史**：所有对话历史都会自动保存

### 3. 消息功能

- **实时发送**：输入消息后按回车或点击发送按钮
- **消息状态**：显示发送时间、已读状态
- **未读提醒**：悬浮按钮显示未读消息数量

## API接口

### 主要接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/aichat/send` | POST | 发送聊天消息 |
| `/aichat/user/sessions` | GET | 获取用户会话列表 |
| `/aichat/session/{id}/messages` | GET | 获取会话消息列表 |
| `/aichat/session/new` | POST | 创建新会话 |
| `/aichat/message/read/{id}` | PUT | 标记消息已读 |

### 请求示例

```javascript
// 发送消息
const response = await this.$http.post('/aichat/send', {
  sessionId: 123,
  message: '你好，请介绍一下系统功能',
  messageType: 'text',
  createNewSession: false
})
```

## 配置说明

### OpenRouter模型配置

当前使用免费模型：`deepseek/deepseek-r1:free:online`

- **请求限制**：每分钟20次，每天200次
- **响应速度**：通常1-3秒
- **支持语言**：中文、英文等

### 自定义配置

可以在 `OpenRouterConfig.java` 中修改以下参数：

```java
@ConfigurationProperties(prefix = "openrouter")
public class OpenRouterConfig {
    private String model = "deepseek/deepseek-r1:free:online";
    private Integer maxTokens = 1000;
    private Double temperature = 0.7;
    private Integer timeout = 30000;
}
```

## 故障排除

### 常见问题

1. **API调用失败**
   - 检查API密钥是否正确
   - 确认网络连接正常
   - 查看控制台错误日志

2. **消息发送失败**
   - 检查用户登录状态
   - 确认数据库连接正常
   - 查看后端日志

3. **界面显示异常**
   - 检查浏览器控制台错误
   - 确认前端资源加载正常
   - 清除浏览器缓存

### 日志查看

```bash
# 查看应用日志
tail -f logs/jxqvue-admin.log

# 查看AI聊天相关日志
grep "OpenRouter\|AiChat" logs/jxqvue-admin.log
```

## 扩展开发

### 添加新功能

1. **自定义AI模型**：修改 `OpenRouterConfig` 配置
2. **消息类型扩展**：在 `ChatMessage` 中添加新字段
3. **界面主题**：修改 `AiChatButton.vue` 样式
4. **权限控制**：在控制器方法上添加权限注解

### 集成其他AI服务

可以扩展 `OpenRouterClient` 或创建新的AI客户端：

```java
@Component
public class CustomAiClient {
    // 实现自定义AI服务接口
}
```

## 注意事项

1. **API密钥安全**：不要将API密钥提交到代码仓库
2. **请求频率**：注意免费模型的请求限制
3. **数据隐私**：聊天内容会保存到数据库，注意数据安全
4. **性能优化**：大量用户使用时考虑缓存和限流

## 更新日志

### v1.0.0 (2024-01-01)
- 初始版本发布
- 支持基本的AI聊天功能
- 集成OpenRouter免费模型
- 实现PC端悬浮聊天按钮

## 技术支持

如有问题，请联系开发团队或查看项目文档。

---

**注意**：使用前请确保已正确配置OpenRouter API密钥，否则AI聊天功能将无法正常工作。
