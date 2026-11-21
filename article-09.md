---
title: Spring AI2.0实战09：上下文工程与对话记忆
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第九篇，教你如何构建带记忆的对话系统，让 AI 能够记住历史对话并保持上下文连贯。
coverImage: article-01/cover.png
---

# Spring AI2.0实战09：上下文工程与对话记忆

> 本文是《Spring AI2.0实战》系列的第九篇。承接前八篇，本篇教你如何构建带记忆的对话系统，让 AI 能够记住历史对话并保持上下文连贯。

---

## 一、什么是上下文工程与对话记忆

大模型本身是无状态的，每次调用都是独立的，默认不记得之前的对话。要构建真正可用的 AI 应用，需要通过 **上下文工程（Context Engineering）** 将历史对话注入模型，让 AI 保持对话连贯性。

核心优势：

| 维度 | 说明 |
|------|------|
| **对话连贯** | AI 能够记住用户名字、偏好、之前提到的内容 |
| **多轮理解** | 理解代词（他、它、那个）所指代的内容 |
| **个性化服务** | 基于历史对话提供个性化建议 |
| **状态隔离** | 不同用户、不同会话的记忆互不干扰 |

---

## 二、ChatMemory 核心概念

Spring AI Alibaba 提供 `ChatMemory` 接口来管理对话记忆，常见实现包括：

| 实现类 | 说明 |
|--------|------|
| **MessageWindowChatMemory** | 保留最近 N 条消息作为上下文窗口，最常用 |
| **VectorStoreChatMemory** | 将历史对话向量化，检索相关片段作为上下文，适合长对话 |

---

## 三、实战：带记忆的对话机器人

构建一个能够记住用户信息的对话机器人：

```java
@Bean
public ChatMemory chatMemory() {
    // 最近 10 条消息作为上下文窗口
    return MessageWindowChatMemory.builder()
            .maxMessages(10)
            .build();
}
```

调用示例：

```java
@Bean
CommandLineRunner memoryDemo(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
    return args -> {
        ChatClient chatClient = chatClientBuilder.build();

        // 第一轮对话
        String answer1 = chatClient.prompt("你好，我叫小明，喜欢编程。")
                .call()
                .content();

        // 第二轮对话，AI 应该记住用户名字
        String answer2 = chatClient.prompt("你还记得我叫什么吗？")
                .call()
                .content();

        // 第三轮对话
        String answer3 = chatClient.prompt("我刚才说我喜欢什么？")
                .call()
                .content();
    };
}
```

执行效果示例：

```
用户：你好，我叫小明，喜欢编程。
AI：你好小明！很高兴认识你。编程是一项很有成就感的技能。

用户：你还记得我叫什么吗？
AI：你叫小明。

用户：我刚才说我喜欢什么？
AI：你喜欢编程。
```

---

## 四、会话隔离

在实际应用中，不同用户的对话记忆必须隔离。通过会话 ID（Session ID）或对话 ID 来区分：

```java
// 不同会话使用不同的 ChatMemory 实例，或在 VectorStore 中通过 metadata 区分
ChatMemory userAMemory = MessageWindowChatMemory.builder().maxMessages(10).build();
ChatMemory userBMemory = MessageWindowChatMemory.builder().maxMessages(10).build();
```

这样用户 A 的对话历史不会泄露给用户 B。

---

## 五、本文速记

1. 大模型本身无状态，需要通过 ChatMemory 实现对话记忆
2. `MessageWindowChatMemory` 适合短对话，保留最近 N 条消息
3. `VectorStoreChatMemory` 适合长对话，基于向量检索相关历史
4. 不同会话必须使用独立的记忆实例，确保状态隔离

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战10：生产级部署与可观测》，教你如何使用 Admin 控制台进行链路追踪、提示词评估和生产部署。记得关注，不迷路。
