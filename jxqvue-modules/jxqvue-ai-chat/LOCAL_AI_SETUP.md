# 本地AI数据库分析功能使用说明

## 功能概述

本功能为您的AI智能客服添加了本地AI数据库分析能力，无需外部API，完全免费且数据安全。

## 安装步骤

### 1. 安装Ollama

**Windows系统：**
```bash
# 方法1：下载安装包
# 访问 https://ollama.ai/download 下载Windows版本

# 方法2：使用winget
winget install Ollama.Ollama

# 方法3：使用PowerShell
Invoke-WebRequest -Uri https://ollama.ai/install.ps1 -OutFile install.ps1
.\install.ps1
```

**Linux/Mac系统：**
```bash
curl -fsSL https://ollama.ai/install.sh | sh
```

### 2. 启动Ollama服务

```bash
ollama serve
```

### 3. 下载AI模型

```bash
# 推荐使用Code Llama模型（擅长代码和SQL生成）
ollama pull codellama:7b

# 如果硬件资源有限，可以使用更小的模型
ollama pull llama2:7b

# 验证模型是否下载成功
ollama list
```

### 4. 启动您的Spring Boot应用

确保数据库连接正常，然后启动应用。

## 功能测试

### 1. 测试Ollama连接

访问：`http://localhost:8080/aichat/localai/test-ollama`

### 2. 测试数据库结构获取

访问：`http://localhost:8080/aichat/localai/test-schema`

### 3. 测试AI数据库分析

访问：`http://localhost:8080/aichat/localai/test-analysis?question=我们有多少客户？`

### 4. 检查系统状态

访问：`http://localhost:8080/aichat/localai/status`

## 使用方法

### 在聊天界面中使用

1. 打开AI聊天界面
2. 输入数据库相关问题，例如：
   - "我们有多少客户？"
   - "库存不足的物料有哪些？"
   - "生产订单完成情况如何？"
   - "设备运行状态统计"
   - "供应商评级情况"

### 支持的问题类型

- **统计类**：多少、数量、统计
- **列表类**：哪些、列表、明细
- **分析类**：情况、状态、趋势
- **对比类**：排名、最多、最少

## 技术架构

```
用户问题 → 意图识别 → Ollama AI → SQL生成 → 数据库查询 → 结果分析 → 智能回答
    ↓         ↓         ↓         ↓         ↓         ↓         ↓
  自然语言   关键词匹配  本地AI模型  自动生成    MySQL     AI分析    格式化输出
```

## 文件说明

- `OllamaClient.java` - Ollama客户端服务
- `DatabaseSchemaService.java` - 数据库结构获取服务
- `LocalAIDatabaseAnalyzer.java` - 本地AI数据库分析器
- `LocalAITestController.java` - 测试控制器
- `AiChatServiceImpl.java` - 集成的AI聊天服务

## 性能优化

1. **缓存机制**：查询结果缓存1小时
2. **SQL限制**：自动添加LIMIT 100限制
3. **错误处理**：完善的异常处理机制
4. **降级策略**：AI分析失败时使用原有OpenRouter API

## 故障排除

### 1. Ollama连接失败

**问题**：Ollama服务未启动
**解决**：
```bash
ollama serve
```

### 2. 模型未找到

**问题**：模型未下载
**解决**：
```bash
ollama pull codellama:7b
```

### 3. 数据库连接失败

**问题**：数据库配置错误
**解决**：检查 `application-druid.yml` 配置

### 4. AI分析结果不准确

**问题**：问题描述不够具体
**解决**：使用更具体的问题，如"我们有多少活跃客户？"

## 成本对比

| 方案 | 成本 | 数据安全 | 响应速度 | 准确性 |
|------|------|----------|----------|--------|
| **本地AI** | **免费** | **高** | 中等 | 高 |
| 外部API | 按调用付费 | 中等 | 快 | 高 |

## 扩展功能

未来可以扩展的功能：
1. 支持更多AI模型
2. 添加图表生成
3. 支持复杂查询
4. 添加查询历史
5. 支持多语言

## 注意事项

1. 首次查询可能较慢（模型加载）
2. 需要足够的硬件资源运行AI模型
3. 建议定期更新AI模型
4. 生产环境建议使用GPU加速

## 联系支持

如有问题，请检查：
1. Ollama服务是否正常运行
2. 数据库连接是否正常
3. 查看应用日志获取详细错误信息
