@echo off
chcp 65001 >nul
echo 开始部署AI智能客服模块...

REM 1. 检查环境
echo 检查Java环境...
java -version >nul 2>&1
if errorlevel 1 (
    echo 错误: 未找到Java环境，请先安装Java 8或更高版本
    pause
    exit /b 1
)

mvn -version >nul 2>&1
if errorlevel 1 (
    echo 错误: 未找到Maven环境，请先安装Maven
    pause
    exit /b 1
)

REM 2. 编译后端项目
echo 编译后端项目...
call mvn clean compile -DskipTests

if errorlevel 1 (
    echo 错误: 后端编译失败
    pause
    exit /b 1
)

REM 3. 检查配置文件
echo 检查配置文件...
findstr /C:"openrouter:" jxqvue-admin\src\main\resources\application.yml >nul
if errorlevel 1 (
    echo 警告: 未找到OpenRouter配置，请检查application.yml文件
)

REM 4. 检查前端项目
echo 检查前端项目...
cd pcui
if not exist "node_modules" (
    echo 安装前端依赖...
    call npm install
)
cd ..

REM 5. 生成部署包
echo 生成部署包...
call mvn clean package -DskipTests

if errorlevel 1 (
    echo 错误: 打包失败
    pause
    exit /b 1
)

echo 部署完成！
echo.
echo 下一步操作：
echo 1. 配置OpenRouter API密钥到环境变量或application.yml
echo 2. 执行数据库脚本: sql\ai_chat_tables.sql
echo 3. 启动后端服务: java -jar jxqvue-admin\target\jxqvue-admin.jar
echo 4. 启动前端服务: cd pcui ^&^& npm run dev
echo.
echo 配置示例：
echo set OPENROUTER_API_KEY=your-api-key-here
echo.
echo 访问地址：
echo 后端API: http://localhost:8080
echo 前端界面: http://localhost:80
echo.
pause
