# 宝贝日历 (BabyCalendar)

一款专为准妈妈设计的孕期日历应用，帮助记录怀孕进度、跟踪产检节点与关键里程碑，让整个孕期一目了然。

## 功能特性

### 孕期进度跟踪
- 基于「末次生理期开始时间」计算孕期进度
- 实时显示当前 **孕周 + 余天数** 及总孕天数
- 以 280 天为标准孕期周期，展示距预产期的倒计时
- 圆形波浪进度条（`CircularWavyProgressIndicator`）直观呈现整体进度

### 产检节点提醒
内置完整的标准产检时间表，覆盖整个孕期关键检查：
- 第 1 次产检（6~13 周）：B 超确认宫内孕、NT 扫查
- 第 2 次产检（14~19 周）：唐氏筛查、无创 DNA
- 第 3 次产检（20~24 周）：系统胎儿畸形筛查（大排畸）
- 第 4 次产检（24~28 周）：糖耐量试验（OGTT）
- 第 5 次产检（30~32 周）：小排畸 B 超、胎儿发育评估
- 第 6 次产检（33~35 周）：常规产检、胎监、胎位检查
- 第 7 次产检（36 周）：胎心监护（NST）、评估分娩方式
- 预产期前三周 / 前两周 / 前一周：胎心监护、常规检查
- 预产期：预计分娩时间

### 关键里程碑
自动标记宝宝发育的关键节点：
- 第一次心跳（第 7 周）
- 孕早期结束（第 84 天，进入胎儿期）
- 第一次听见声音（第 17 周）
- 第一次胎动（第 18~22 周）
- 入院待产（第 196 天）
- 足月（第 259 天）

### 事件状态管理
每个事件卡片具有四种状态，并以不同颜色与图标区分：
- **未开始**（`NotStarted`）：尚未到达时间窗口
- **进行中**（`InProgress`）：当前处于时间窗口内
- **已过期**（`Expired`）：超过时间窗口且未标记完成
- **已完成**（`Completed`）：用户已手动标记完成

支持左右滑动卡片进行状态切换：
- 从左向右滑：标记完成
- 从右向左滑：撤回完成状态

### 主题与体验
- 基于 Material 3 设计规范
- 支持 Material You 动态取色（Android 12+）
- 支持深色模式自动跟随系统
- Edge-to-edge 沉浸式全屏布局
- 自适应横竖屏内边距

## 技术栈

| 项目 | 版本 / 说明 |
| --- | --- |
| 语言 | Kotlin 2.4.0 |
| UI 框架 | Jetpack Compose（BOM 2026.06.00） |
| 设计规范 | Material 3 1.5.0-alpha22 |
| 构建工具 | Android Gradle Plugin 9.3.0-rc01 |
| compileSdk | 37 |
| minSdk | 26（Android 8.0） |
| targetSdk | 36 |
| Java 兼容 | 11 |
| 版本目录 | Gradle Version Catalog（`libs.versions.toml`） |
| 构建优化 | Gradle Configuration Cache |
| 数据持久化 | SharedPreferences（轻量本地存储，无数据库依赖） |

## 项目结构

```
app/src/main/java/com/lollipop/babycalendar/
├── LApplication.kt              # Application 入口，初始化 CalendarHelper
├── MainActivity.kt              # 主 Activity，根据状态切换页面
├── data/                        # 数据与业务逻辑层
│   ├── CalendarHelper.kt        # 日期计算、状态计算、本地持久化
│   ├── CalendarFlag.kt          # 孕期事件静态定义（产检、里程碑）
│   ├── CalendarItem.kt          # 事件 UI 模型（图标、颜色、滑动规则）
│   ├── CalendarItemState.kt     # 事件状态枚举
│   └── CalendarState.kt         # Compose 可观察全局状态
├── page/                        # 页面层
│   ├── StartDatePage.kt         # 孕期起始时间选择页
│   └── ContentPage.kt           # 主内容页（进度 + 事件列表）
└── ui/theme/                    # 主题层
    ├── Color.kt                 # 颜色定义
    ├── Theme.kt                 # 主题配置（动态色 / 深色模式）
    └── Type.kt                  # 排版定义
```

## 工作原理

1. **首次启动**：进入 `StartDatePage`，用户通过 `DatePicker` 选择末次生理期开始时间。
2. **时间确认后**：`CalendarHelper` 计算当前孕周、孕天数、倒计时，并生成所有事件卡片及其状态，持久化到 `SharedPreferences`。
3. **主页面**：`ContentPage` 展示波浪进度条与按状态排序的事件列表，用户可滑动卡片标记完成 / 撤回。
4. **重置**：点击右上角刷新按钮可清空起始时间，重新进入选择页。

## 构建与运行

### 环境要求
- Android Studio（支持 AGP 9.3 的版本）
- JDK 11+
- Android SDK 37

### 命令行构建
```bash
# Debug 构建
./gradlew assembleDebug

# 安装到已连接设备
./gradlew installDebug
```

## 应用信息

- **应用 ID**：`com.lollipop.babycalendar`
- **应用名称**：宝贝日历
- **版本**：1.0.0（versionCode: 1_00_00）
