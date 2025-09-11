# 消息图标选择指南

## 当前可用的图标选项

根据您的 `static/icons` 目录，以下图标可能适合作为消息图标：

### 推荐选项：

1. **list.png** - 列表图标
   - 适合表示消息列表
   - 直观易懂

2. **notice-bar.png** - 通知栏图标
   - 专门用于通知和消息
   - 语义明确

3. **popup.png** - 弹窗图标
   - 表示消息弹窗
   - 现代感强

4. **badge.png** - 徽章图标
   - 表示通知徽章
   - 与角标功能呼应

5. **nav-bar.png** - 导航栏图标
   - 可能包含消息相关元素

## 当前设置

我已经将以下图标设置为消息图标：
- **未选中状态**: `list.png`
- **选中状态**: `list.png`

## 如何更换图标

如果您想使用其他图标，请执行以下命令：

```bash
# 使用通知栏图标
copy "D:\Develop\Jxq2509_1\app\static\icons\notice-bar.png" "D:\Develop\Jxq2509_1\app\static\images\tabbar\message.png"
copy "D:\Develop\Jxq2509_1\app\static\icons\notice-bar.png" "D:\Develop\Jxq2509_1\app\static\images\tabbar\message_.png"

# 使用弹窗图标
copy "D:\Develop\Jxq2509_1\app\static\icons\popup.png" "D:\Develop\Jxq2509_1\app\static\images\tabbar\message.png"
copy "D:\Develop\Jxq2509_1\app\static\icons\popup.png" "D:\Develop\Jxq2509_1\app\static\images\tabbar\message_.png"

# 使用徽章图标
copy "D:\Develop\Jxq2509_1\app\static\icons\badge.png" "D:\Develop\Jxq2509_1\app\static\images\tabbar\message.png"
copy "D:\Develop\Jxq2509_1\app\static\icons\badge.png" "D:\Develop\Jxq2509_1\app\static\images\tabbar\message_.png"
```

## 图标要求

- **尺寸**: 81x81 像素
- **格式**: PNG
- **背景**: 透明
- **颜色**: 
  - 未选中状态：灰色 (#999999)
  - 选中状态：蓝色 (#409EFF)

## 测试方法

1. 替换图标文件
2. 重新编译移动端应用
3. 检查tabbar中消息图标是否正确显示
4. 测试选中和未选中状态的切换

## 其他可能的消息相关图标

如果以上图标都不满意，您还可以考虑：

- **grid.png** - 网格图标（如果设计合适）
- **card.png** - 卡片图标（如果表示消息卡片）
- **tag.png** - 标签图标（如果表示消息标签）

请告诉我您希望使用哪个图标，我可以帮您设置！
