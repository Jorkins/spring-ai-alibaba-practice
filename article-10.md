---
title: Spring AI2.0实战10：生产级部署与可观测
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第十篇，也是本系列的完结篇，教你如何使用 Admin 控制台进行链路追踪、提示词评估和生产部署，让 AI 应用从开发环境走向生产环境。
coverImage: article-01/cover.png
---

# Spring AI2.0实战10：生产级部署与可观测

> 本文是《Spring AI2.0实战》系列的第十篇，也是本系列的完结篇。承接前九篇，本篇教你如何使用 Admin 控制台进行链路追踪、提示词评估和生产部署，让 AI 应用从开发环境走向生产环境。

---

## 一、为什么需要生产级可观测

开发环境下的 AI 应用运行正常，不代表在生产环境也稳定。AI 应用有其特殊性：模型响应延迟高、Token 费用不可控、Prompt 效果难以量化。生产级可观测能帮你解决：

| 问题 | 说明 |
|------|------|
| **调用追踪** | 不知道 AI 调用了多少次、耗时多久、成功率如何 |
| **成本管控** | Token 消耗不透明，超预算后才发现 |
| **Prompt 调优** | 不知哪个 Prompt 版本效果更好，缺乏评估数据 |
| **故障排查** | 链路复杂，错误发生时难以定位根因 |

---

## 二、Spring AI Alibaba Admin 控制台

Spring AI Alibaba Admin 是一站式 Agent 平台，提供可视化的 Agent 开发、可观测、评估和 MCP 管理能力。

核心功能：

| 功能 | 说明 |
|------|------|
| **链路追踪** | 查看每次 AI 调用的完整链路、耗时、Token 消耗 |
| **提示词评估** | 对比不同 Prompt 版本的输出效果，选择最优方案 |
| **Agent 管理** | 可视化管理已部署的智能体，查看运行状态 |
| **MCP 管理** | 集中管理 MCP 服务器和工具配置 |

---

## 三、链路追踪集成

在生产环境中，需要给每次 AI 调用增加追踪标识，方便问题定位。

```yaml
spring:
  ai:
    alibaba:
      observability:
        enabled: true
        trace-id-header: X-Trace-Id
```

```java
@Bean
CommandLineRunner tracingDemo(ChatClient.Builder chatClientBuilder) {
    return args -> {
        String answer = chatClientBuilder.build()
                .prompt("请介绍 Spring AI Alibaba 的生产部署要点")
                .call()
                .content();
        System.out.println(answer);
    };
}
```

Admin 控制台会自动采集调用链路，展示调用频次、平均耗时、错误率等指标。

---

## 四、提示词评估与版本管理

生产环境下，Prompt 的微小改动可能导致输出质量显著变化。Admin 提供提示词评估功能：

1. **创建评估集**：准备一组标准问题和期望答案
2. **运行对比**：让不同 Prompt 版本分别回答同一组问题
3. **打分比较**：自动或人工打分，选择效果最佳的版本
4. **灰度发布**：将优化后的 Prompt 逐步推送到生产环境

---

## 五、生产部署建议

| 维度 | 建议 |
|------|------|
| **模型选择** | 日常对话用 qwen-turbo，复杂推理用 qwen-plus/qwen-max |
| **流式输出** | 前端使用 SSE 接收流式内容，提升用户体验 |
| **熔断降级** | 模型调用失败时，提供降级回答或缓存结果 |
| **Token 限流** | 对单用户、单 IP 设置调用频次和 Token 上限 |
| **日志脱敏** | 日志中不记录用户敏感信息和 API Key |

---

## 六、本文速记

1. Spring AI Alibaba Admin 提供链路追踪、提示词评估、Agent 管理等生产能力
2. 生产环境需要开启可观测，追踪调用链路和 Token 消耗
3. 提示词评估帮助选择最优 Prompt 版本，避免盲目改动
4. 部署时注意模型选型、流式输出、熔断降级和 Token 限流

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

---

《Spring AI2.0实战》系列到此完结。从第 01 篇的环境搭建，到第 10 篇的生产部署，我们共同走完了从零到一构建 Java AI 应用的全过程。希望这个系列能帮助你快速掌握 Spring AI Alibaba，构建出属于自己的 AI Native 应用。
