### **简短版 README.md for Android App (IGoals)**

---

#### **项目名称：IGoals - 一款智能任务管理软件**

IGoals 是一个 Android 应用程序，旨在通过截图、语音、文本等多模态输入，实现智能任务管理。该应用通过 OCR 技术和大模型解析算法，自动提取任务关键信息，进行智能分类和优先级识别，帮助用户更高效地管理日常任务。

---

#### **功能特性：**

1. **一键任务添加：**

   * 支持多种输入方式：截图、语音、文本。
   * 通过悬浮按钮快速触发截图操作。

2. **智能任务解析：**

   * 集成 OCR 识别，将图片转换为文本。
   * 语音转文本识别，自动提取任务内容。
   * 使用大模型（如 GPT 或 BERT）对任务进行语义解析和分类。

3. **任务提醒与管理：**

   * 多模式提醒：通知、铃声、闹钟。
   * 任务优先级标记和分类显示。
   * 自动同步至日历和消息通知。

4. **多场景适用：**

   * 工作任务管理
   * 学业待办跟踪
   * 日常生活规划

---

#### **开发技术：**

* **编程语言：** Java
* **开发平台：** Android Studio
* **主要框架和库：**

  * MediaProjection API：屏幕捕获
  * ImageReader：截图保存
  * Foreground Service：后台悬浮按钮服务
  * OCR 技术：文本提取
  * 大模型 API：任务解析
  * Android权限管理：适配 Android 12 和 13

---

#### **项目结构：**

```
MediaProjectionDemo/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/mtsahakis/mediaprojectiondemo/
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── ScreenCaptureService.java
│   │   │   │   ├── FloatingButtonService.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── floating_button.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   ├── AndroidManifest.xml
├── build.gradle
└── README.md
```

---

#### **安装与运行：**

1. **环境要求：**

   * Android Studio Arctic Fox 或以上
   * Android SDK 34 (Tiramisu)
   * Java 11 或以上

2. **运行步骤：**

   * **克隆项目：**

     ```
     git clone https://github.com/yourusername/MediaProjectionDemo.git
     cd MediaProjectionDemo
     ```
   * **编译与安装：**

     ```
     ./gradlew clean
     ./gradlew assembleDebug
     adb install app/build/outputs/apk/debug/app-debug.apk
     ```
   * **运行应用：**

     * 点击“显示”按钮，悬浮按钮将出现在屏幕上。
     * 点击悬浮按钮，进行截图操作。
     * 点击“停止”按钮，隐藏悬浮按钮并停止录制服务。

---

#### **权限要求：**

* **系统权限：**

  * SYSTEM\_ALERT\_WINDOW：悬浮窗权限
  * FOREGROUND\_SERVICE：前台服务
  * MANAGE\_EXTERNAL\_STORAGE：读写存储权限
  * RECORD\_AUDIO：语音输入
  * POST\_NOTIFICATIONS：消息通知
* **运行时权限：**

  * 在 Android 12 和 13 上，手动允许录屏和悬浮窗权限。

---

#### **已知问题：**

* 在 Android 13 设备上，启动时可能会请求额外的通知权限。
* OCR 模块需要网络支持，大模型 API 调用可能延迟。
* 当前版本仅支持截图操作，语音和文本功能仍在开发中。

---

#### **开发计划：**

* 增加语音输入的任务识别和提醒功能。
* 集成更多大模型进行任务分类和优先级判断。
* 支持多种任务类型和复杂场景下的任务管理。

---

#### **贡献指南：**

欢迎开发者提出建议或提交 PR，协作改进 IGoals。
请在提交前检查代码格式和文档完整性。

---

#### **联系方式：**

* **作者：** Arc Lyn
* **Email：**wangjunjie00007@163.com

---

#### **感谢使用 IGoals！**

如果你有任何疑问或改进建议，请随时联系我。😊
