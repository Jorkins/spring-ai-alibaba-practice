---
title: Spring AI2.0实战06：Graph 工作流——可编排的长期运行智能体
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第六篇，教你如何构建有状态、可持久化的长期运行 AI 工作流，让智能体不仅能一次性回答，还能跨会话记忆与断点续执行。
coverImage: article-01/cover.png
---

# Spring AI2.0实战06：Graph 工作流——可编排的长期运行智能体

> 本文是《Spring AI2.0实战》系列的第六篇。承接前五篇，本篇教你如何构建有状态、可持久化的长期运行 AI 工作流，让智能体不仅能一次性回答，还能跨会话记忆与断点续执行。

---

## 一、什么是 Graph 工作流

Agent 适合处理单次对话，但复杂业务往往需要多步骤、跨会话、可暂停恢复的工作流。Spring AI Alibaba Graph 提供了底层编排运行时，支持条件路由、嵌套图、并行执行和状态管理。

核心优势：

| 维度 | 说明 |
|------|------|
| **有状态** | 工作流状态可持久化，支持断点续执行 |
| **可编排** | 节点、边、条件路由灵活组合 |
| **长期运行** | 适合跨天、跨会话的业务流程 |
| **可视化** | 支持导出 PlantUML / Mermaid 图表 |

---

## 二、核心概念

| 概念 | 说明 |
|------|------|
| **Node（节点）** | 工作流的基本执行单元，可封装 Agent、工具调用或普通逻辑 |
| **Edge（边）** | 节点之间的流转关系，支持条件分支 |
| **State（状态）** | 贯穿整个工作流的上下文数据，节点间共享 |
| **Checkpoint（检查点）** | 工作流状态快照，支持中断后从上次位置恢复 |

---

## 三、实战：订单处理工作流

构建一个简单的订单处理流程：**接单 → 审核 → 出库 → 完成**，每个节点是一个独立 Agent。

```java
@Bean
public Graph orderProcessingGraph() {
    return Graph.builder()
            .node("receive", agent("receive", "你是接单专员，负责记录订单信息。"))
            .node("audit", agent("audit", "你是审核专员，负责检查订单是否符合规则。"))
            .node("ship", agent("ship", "你是出库专员，负责安排发货。"))
            .edge("receive", "audit")
            .edge("audit", "ship")
            .edge("ship", END)
            .build();
}
```

执行示例：

```java
String result = orderProcessingGraph()
        .run("订单号：ORD-20261120，商品：笔记本电脑，数量：1");
System.out.println(result);
```

执行流程：

1. receive 节点：记录订单信息，更新状态
2. audit 节点：审核订单，若不通过可条件路由回 receive
3. ship 节点：审核通过后安排出库
4. END：工作流结束，返回最终结果

---

## 四、状态管理与断点续执行

Graph 工作流支持状态持久化，工作流中断后可从上次检查点恢复：

```java
MemorySaver checkpointer = new MemorySaver();

Graph graphWithMemory = Graph.builder()
        .node("step1", agent("step1", "第一步处理。"))
        .node("step2", agent("step2", "第二步处理。"))
        .edge("step1", "step2")
        .checkpointer(checkpointer)
        .build();

// 第一次运行
graphWithMemory.run("初始数据");

// 后续可从断点继续
graphWithMemory.run("继续处理", threadId: "order-001");
```

---

## 五、本文速记

1. Graph 工作流适合多步骤、跨会话、可暂停恢复的业务流程
2. 核心概念：Node 节点、Edge 边、State 状态、Checkpoint 检查点
3. 支持条件路由和嵌套图，构建复杂业务编排
4. 状态持久化让长期运行的工作流可靠恢复

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战07：RAG 实战——私有知识库问答》，教你如何构建基于向量检索的企业知识库问答系统。记得关注，不迷路。
