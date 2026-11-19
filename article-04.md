---
title: Spring AI2.0实战04：Function Calling——让 Java 方法变成 AI 工具
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第四篇，教你如何把本地 Java 方法注册为 AI 可调用的工具，实现天气查询、数据库操作等功能，让 AI 不仅能聊天，还能执行实际业务。
coverImage: article-01/cover.png
---

# Spring AI2.0实战04：Function Calling——让 Java 方法变成 AI 工具

> 本文是《Spring AI2.0实战》系列的第四篇。承接前三篇，本篇教你如何把本地 Java 方法注册为 AI 可调用的工具，实现天气查询、数据库操作等功能，让 AI 不仅能聊天，还能执行实际业务。

---

## 一、Function Calling 是什么

**Function Calling**（函数调用）是大模型的核心能力之一。AI 不再只输出文字，而是在需要时调用你注册的 Java 方法，获取真实数据后再组织回答。

核心优势：

| 维度 | 说明 |
|------|------|
| **工具化** | 把任意 Java 方法变成 AI 可调用的工具 |
| **实时数据** | 突破大模型知识截止日期，调用真实 API 或数据库 |
| **类型安全** | 参数自动映射，返回结果自动注入对话 |

---

## 二、核心原理

Function Calling 的工作流程：

1. 用户提出问题（如“杭州今天天气怎么样？”）
2. AI 判断需要调用工具，输出工具名称和参数
3. Spring AI Alibaba 自动匹配并执行对应的 Java 方法
4. 方法返回结果，AI 结合结果生成最终回答

关键依赖（`pom.xml`）：

```xml
<dependency>
    <groupId>com.alibaba.cloud.ai</groupId>
    <artifactId>spring-ai-alibaba-starter-dashscope</artifactId>
</dependency>
```

---

## 三、实战：天气查询助手

定义一个天气查询 Java 方法，注册为 AI 工具：

```java
@Bean
public ToolCallback weatherTool() {
    return FunctionToolCallback.builder("get_weather", new WeatherService())
            .description("查询指定城市的天气信息")
            .inputType(WeatherRequest.class)
            .build();
}
```

请求参数类：

```java
public class WeatherRequest {
    @Description("城市名称，如 杭州、北京")
    private String city;
    // getter/setter
}
```

服务实现类：

```java
public class WeatherService {
    @Description("查询天气")
    public String getWeather(WeatherRequest request) {
        return request.getCity() + "今天晴朗，气温 25°C，适合出行。";
    }
}
```

---

## 四、完整示例代码

主启动类，组合 ChatClient 与 Function Calling 工具：

```java
@SpringBootApplication
public class FunctionCallingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunctionCallingDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner functionDemo(ChatClient.Builder chatClientBuilder) {
        return args -> {
            String answer = chatClientBuilder.build()
                    .system("你是一个天气助手，用户询问天气时请调用 get_weather 工具。")
                    .user("杭州今天天气怎么样？")
                    .call()
                    .content();
            System.out.println(answer);
        };
    }
}
```

运行效果示例：

```
杭州今天天气怎么样？

🌤️ 杭州今天晴朗，气温 25°C，适合出行。
```

AI 自动识别出需要查询天气，调用了 `get_weather` 工具，获取结果后生成自然语言回答。

---

## 五、本文速记

1. 使用 `FunctionToolCallback.builder()` 注册 Java 方法为 AI 工具
2. 通过 `@Description` 注解告诉 AI 参数含义，提高调用准确率
3. AI 自动匹配工具并执行，无需手动解析参数
4. 适合天气查询、数据库操作、外部 API 调用等场景

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战05：多智能体协作——Agent 团队作战》，教你如何构建多个智能体协作完成复杂任务。记得关注，不迷路。
