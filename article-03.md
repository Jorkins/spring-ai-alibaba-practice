---
title: Spring AI2.0实战03：Prompt 工程实战——模板、变量与系统角色
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第三篇，教你如何用 Prompt 模板让 AI 按照指定角色和格式回答，实现变量动态替换与系统角色设定，让 Java 应用的 AI 交互更可控、更专业。
---

# Spring AI2.0实战03：Prompt 工程实战——模板、变量与系统角色

> 本文是《Spring AI2.0实战》系列的第三篇。承接前两篇，本篇教你如何用 Prompt 模板让 AI 按照指定角色和格式回答，实现变量动态替换与系统角色设定，让 Java 应用的 AI 交互更可控、更专业。

---

## 一、Prompt 工程为什么重要

大模型对输入提示词非常敏感，同样的问题，不同的表述可能得到截然不同的答案。在 Java 应用中，不应在业务代码里硬编码 Prompt 字符串，而应通过 **PromptTemplate** 模板化管理，实现变量动态替换和角色设定。

核心优势：

| 维度 | 说明 |
|------|------|
| **模板化** | Prompt 与业务逻辑分离，易于维护 |
| **变量替换** | 支持 `{name}` 等占位符，运行时动态填充 |
| **系统角色** | 通过 System Message 设定 AI 的身份和行为规范 |

---

## 二、PromptTemplate 基础用法

使用 `PromptTemplate` 可定义带占位符的模板，运行时传入变量 Map 生成最终消息：

```java
PromptTemplate promptTemplate = new PromptTemplate(
    "请用 {count} 句话解释 {topic}，面向 {audience} 读者"
);

Message message = promptTemplate.createMessage(Map.of(
    "count", 3,
    "topic", "Spring AI Alibaba",
    "audience", "Java 后端开发者"
));
```

核心依赖（`pom.xml`）：

```xml
<dependency>
    <groupId>com.alibaba.cloud.ai</groupId>
    <artifactId>spring-ai-alibaba-starter-dashscope</artifactId>
</dependency>
```

---

## 三、系统角色与用户角色

在多轮对话或角色扮演场景中，需要区分 **System**（系统角色）和 **User**（用户角色）：

- **System Message**：设定 AI 的身份、行为规范、输出格式，通常在对话开始时设置一次
- **User Message**：真正的用户输入或业务问题

使用 `ChatClient` 组合两者：

```java
@Bean
CommandLineRunner promptDemo(ChatClient.Builder chatClientBuilder) {
    return args -> {
        String answer = chatClientBuilder.build()
                .system("你是一位资深 Java 架构师，擅长 Spring Boot 和 AI 应用开发。" +
                        "请用专业但易懂的中文回答，每段不超过 3 句话。")
                .user("请用 3 句话解释什么是 Spring AI Alibaba")
                .call()
                .content();
        System.out.println(answer);
    };
}
```

效果示例：

```
Spring AI Alibaba 是阿里云基于 Spring AI 构建的 Java AI 应用开发框架。
它让 Java 开发者无需学习 Python，就能用 Spring Boot 的方式构建 AI 原生应用。
支持多模型、流式输出、Function Calling 等能力，适合企业级 AI 集成。
```

---

## 四、变量动态替换实战

将 Prompt 模板提取到配置文件或数据库，运行时根据业务场景动态填充变量，实现同一模板多场景复用：

```java
String template = "你是一位{role}，请用{tone}的语气回答{user}关于{topic}的问题，不超过{limit}字。";

PromptTemplate promptTemplate = new PromptTemplate(template);
Message message = promptTemplate.createMessage(Map.of(
    "role", "产品经理",
    "tone", "友好",
    "user", "张三",
    "topic", "Spring AI Alibaba",
    "limit", 200
));
```

这样无需修改 Java 代码，只需调整模板变量即可适配不同角色、语气和场景。

---

## 五、本文速记

1. 使用 `PromptTemplate` 实现 Prompt 模板化，与业务逻辑分离
2. 支持 `{variable}` 占位符，运行时动态填充
3. 通过 `ChatClient` 的 `.system()` 和 `.user()` 设定系统角色与用户输入
4. 变量动态替换让同一模板可适配多种场景，无需改代码

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战04：Function Calling——让 Java 方法变成 AI 工具》，教你如何把本地 Java 方法注册为 AI 可调用的工具，实现天气查询、数据库操作等功能。记得关注，不迷路。
