---
title: Spring AI2.0实战02：ChatModel 多模型接入与流式输出
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第二篇，教你如何接入多个通义大模型，并实现类似对话框的流式打字输出效果，让 Java 应用拥有更流畅的 AI 交互体验。
coverImage: article-01/cover.png
---

# Spring AI2.0实战02：ChatModel 多模型接入与流式输出

> 本文是《Spring AI2.0实战》系列的第二篇。承接第 01 篇，本篇教你如何接入多个通义大模型，并实现类似对话框的流式打字输出效果，让 Java 应用拥有更流畅的 AI 交互体验。

---

## 一、ChatModel 是什么

在 Spring AI Alibaba 中，**ChatModel** 是对话模型的统一抽象。通过 `spring-ai-alibaba-starter-dashscope` 启动器，Spring Boot 会自动配置基于 DashScope 的 `ChatModel` Bean，你只需注入即可调用，无需关心底层 HTTP 细节。

核心优势：

| 维度 | 说明 |
|------|------|
| **统一接口** | 同一套 Java 代码可切换不同通义模型 |
| **流式输出** | 原生支持 SSE 流式，适合对话框打字效果 |
| **零配置切换** | 仅需修改配置文件中的模型名称 |

---

## 二、多模型接入

通义大模型提供多个模型，适用于不同场景：

| 模型 | 适用场景 |
|------|----------|
| `qwen-turbo` | 日常对话，响应快，成本低 |
| `qwen-plus` | 复杂推理，能力更强 |
| `qwen-max` | 最强模型，适合专业任务 |

在配置文件中切换模型，只需修改一行：

```yaml
spring:
  ai:
    dashscope:
      api-key: ${AI_DASHSCOPE_API_KEY:}
      chat:
        options:
          model: qwen-plus
```

启动前设置环境变量：

```bash
# Linux / Mac
export AI_DASHSCOPE_API_KEY=你的-api-key

# Windows PowerShell
$env:AI_DASHSCOPE_API_KEY="你的-api-key"
```

核心依赖：

```xml
<dependency>
    <groupId>com.alibaba.cloud.ai</groupId>
    <artifactId>spring-ai-alibaba-starter-dashscope</artifactId>
</dependency>
```

---

## 三、流式输出实现

流式输出是 AI 对话的标准体验，让文字像打字机一样逐字显示，而不是等待整段生成完毕再一次性返回。

使用 `ChatClient` 的 `stream()` 方法即可实现：

```java
@Bean
CommandLineRunner streamDemo(ChatClient.Builder chatClientBuilder) {
    return args -> {
        chatClientBuilder.build()
                .prompt("请写一段关于 Spring AI Alibaba 的简短介绍")
                .stream()
                .content()
                .doOnNext(System.out::print)
                .blockLast();
    };
}
```

关键点：

- `.stream()`：开启流式模式，服务器通过 SSE 持续推送 token
- `.content()`：获取流式内容流
- `.doOnNext(System.out::print)`：每收到一段就打印，实现打字效果
- `.blockLast()`：阻塞等待流结束，防止主线程提前退出

> 流式输出适合聊天机器人、代码生成器等需要实时反馈的场景，显著提升用户体验。

---

## 四、本文速记

1. 通过 `spring-ai-alibaba-starter-dashscope` 接入通义大模型
2. 修改配置文件中的 `model` 字段即可切换 `qwen-turbo`、`qwen-plus`、`qwen-max`
3. 使用 `ChatClient.builder().stream().content()` 实现流式打字输出
4. API Key 建议通过环境变量注入，不要硬编码

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战03：Prompt 工程实战——模板、变量与系统角色》，教你如何用 Prompt 模板让 AI 按照指定角色和格式回答。记得关注，不迷路。
