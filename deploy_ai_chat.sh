#!/bin/bash

# AI智能客服模块部署脚本
# 使用方法: ./deploy_ai_chat.sh

echo "开始部署AI智能客服模块..."

# 1. 检查环境
echo "检查Java环境..."
if ! command -v java &> /dev/null; then
    echo "错误: 未找到Java环境，请先安装Java 8或更高版本"
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo "错误: 未找到Maven环境，请先安装Maven"
    exit 1
fi

# 2. 编译后端项目
echo "编译后端项目..."
mvn clean compile -DskipTests

if [ $? -ne 0 ]; then
    echo "错误: 后端编译失败"
    exit 1
fi

# 3. 执行数据库脚本
echo "执行数据库脚本..."
echo "请手动执行以下SQL脚本："
echo "mysql -u your_username -p your_database < sql/ai_chat_tables.sql"
echo ""

# 4. 检查配置文件
echo "检查配置文件..."
if ! grep -q "openrouter:" jxqvue-admin/src/main/resources/application.yml; then
    echo "警告: 未找到OpenRouter配置，请检查application.yml文件"
fi

# 5. 编译前端项目
echo "编译前端项目..."
cd pcui

if [ ! -d "node_modules" ]; then
    echo "安装前端依赖..."
    npm install
fi

echo "前端项目准备完成"
cd ..

# 6. 生成部署包
echo "生成部署包..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "错误: 打包失败"
    exit 1
fi

echo "部署完成！"
echo ""
echo "下一步操作："
echo "1. 配置OpenRouter API密钥到环境变量或application.yml"
echo "2. 执行数据库脚本: sql/ai_chat_tables.sql"
echo "3. 启动后端服务: java -jar jxqvue-admin/target/jxqvue-admin.jar"
echo "4. 启动前端服务: cd pcui && npm run dev"
echo ""
echo "配置示例："
echo "export OPENROUTER_API_KEY=your-api-key-here"
echo ""
echo "访问地址："
echo "后端API: http://localhost:8080"
echo "前端界面: http://localhost:80"
