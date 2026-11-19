---
title: Spring AI2.0实战01：5分钟搭建第一个基于通义大模型的 Java AI 应用
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第一篇，面向 Java 后端开发者，不需要 Python 基础，只需要你熟悉 Spring Boot。本文将手把手带你搭建第一个基于阿里云通义大模型的 Java AI 应用，全程不超过 5 分钟。
coverImage: article-01/cover.png
---

# Spring AI2.0实战01：5分钟搭建第一个基于通义大模型的 Java AI 应用

> 本文是《Spring AI2.0实战》系列的第一篇，面向 Java 后端开发者，不需要 Python 基础，只需要你熟悉 Spring Boot。本文将手把手带你搭建第一个基于阿里云通义大模型的 Java AI 应用，全程不超过 5 分钟。

---

## 一、为什么选 Spring AI Alibaba？

最近大模型应用开发非常火，但绝大多数教程都是 Python + LangChain/LlamaIndex。如果你是 Java 后端开发者，不想为了 AI 去学 Python，**Spring AI Alibaba** 是最佳选择。

它是阿里云基于 Spring AI 构建的开源 AI 框架，深度集成通义大模型（DashScope / Bailian），让你用写 Spring Boot 的方式就能开发 AI 原生应用。

核心优势：

| 维度 | 说明 |
|------|------|
| **零门槛** | 不需要 Python，不需要离开 Spring 生态 |
| **零痛点集成** | 引入 Starter，用 Java 注解和配置直接开发 |
| **生产级可用** | 支持多模型、流式输出、Function Calling、Agent、Graph 工作流、RAG、MCP 等 |

---

## 二、环境准备

在开始前，确保你已准备好以下内容：

| 项 | 要求 |
|----|------|
| **JDK** | 17+ |
| **Spring Boot** | 3.x |
| **构建工具** | Maven（或使用项目自带的 `mvnw`） |
| **API Key** | 阿里云 DashScope API Key（[获取地址](https://help.aliyun.com/zh/dashscope/)） |

> 登录 [阿里云 Bailian 控制台](https://bailian.console.aliyun.com/?apiKey=1&tab=api#/api) 即可创建 API Key。

---

## 三、快速开始

### 1. 创建 Spring Boot 项目

通过 [Spring Initializr](https://start.spring.io/) 创建一个标准 Spring Boot 3.x 项目，或者直接从 GitHub 克隆配套代码。

### 2. 添加依赖

在 `pom.xml` 中引入 Spring AI Alibaba 的 DashScope Starter，并导入两个 BOM 管理版本：

- `org.springframework.ai:spring-ai-bom`
- `com.alibaba.cloud.ai:spring-ai-alibaba-bom`

核心依赖：

```xml
<dependency>
    <groupId>com.alibaba.cloud.ai</groupId>
    <artifactId>spring-ai-alibaba-starter-dashscope</artifactId>
</dependency>
```

### 3. 配置 application.yml

在 `src/main/resources/application.yml` 中配置通义大模型信息：

```yaml
spring:
  ai:
    dashscope:
      api-key: ${AI_DASHSCOPE_API_KEY:}
      chat:
        options:
          model: qwen-turbo
```

启动前设置环境变量：

```bash
# Linux / Mac
export AI_DASHSCOPE_API_KEY=你的-api-key

# Windows PowerShell
$env:AI_DASHSCOPE_API_KEY="你的-api-key"
```

### 4. 编写代码

创建主启动类，使用 `ChatClient.Builder` 调用通义大模型：

```java
@Bean
CommandLineRunner hello(ChatClient.Builder chatClientBuilder) {
    return args -> {
        String answer = chatClientBuilder.build()
                .prompt("你好，请用一句话介绍 Spring AI Alibaba")
                .call()
                .content();
        System.out.println(answer);
    };
}
```

项目启动时会自动执行，控制台打印 AI 返回的内容。

---

## 四、运行与验证

在项目根目录执行：

```bash
./mvnw spring-boot:run
```

启动成功后，控制台会输出 AI 回答，说明你的第一个 Java AI 应用已经跑通了。

---

## 五、本文速记

1. 引入 `spring-ai-alibaba-starter-dashscope` 即可接入通义大模型
2. API Key 建议通过环境变量注入，不要硬编码
3. 使用 `ChatClient.Builder` 发送 Prompt，一行代码完成模型调用
4. 完整源码请前往 GitHub 仓库获取

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战02：ChatModel 多模型接入与流式输出》，教你如何切换不同通义模型，并实现类似对话框的流式打字效果。记得关注，不迷路。
