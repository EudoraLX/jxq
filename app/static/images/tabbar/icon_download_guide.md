# 消息图标下载指南

## 推荐图标资源

### 1. Feather Icons (推荐)
- 网址：https://feathericons.com/
- 搜索：bell, message-circle, mail
- 下载SVG格式，然后转换为PNG

### 2. Material Design Icons
- 网址：https://materialdesignicons.com/
- 搜索：bell, message, notification
- 下载PNG格式

### 3. Iconfont (阿里巴巴图标库)
- 网址：https://www.iconfont.cn/
- 搜索：消息、铃铛、通知
- 下载PNG格式

## 图标要求

### 尺寸规格
- **宽度**：81像素
- **高度**：81像素
- **格式**：PNG
- **背景**：透明

### 颜色规范
- **未选中状态**：灰色 (#999999)
- **选中状态**：蓝色 (#409EFF)

## 推荐图标样式

### 1. 铃铛图标 (Bell)
- 适合表示通知和消息
- 简洁明了，易于识别

### 2. 消息气泡图标 (Message Circle)
- 直接表示消息功能
- 现代感强

### 3. 信封图标 (Mail)
- 传统消息图标
- 用户熟悉度高

## 快速解决方案

如果急需使用，可以：

1. **临时使用文字图标**：
   - 在pages.json中移除iconPath
   - 只显示"消息"文字

2. **使用系统默认图标**：
   - 某些平台支持系统默认图标
   - 无需自定义图片

3. **复制现有图标并修改**：
   - 复制mine.png
   - 使用图片编辑软件修改颜色
   - 保存为message.png和message_.png

## 文件保存位置

```
D:\Develop\Jxq2509_1\app\static\images\tabbar\
├── message.png      (未选中状态)
└── message_.png     (选中状态)
```

## 测试方法

1. 保存图标文件到指定位置
2. 重新编译移动端应用
3. 检查tabbar中消息图标是否正确显示
4. 测试选中和未选中状态的切换
