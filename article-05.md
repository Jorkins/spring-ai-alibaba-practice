---
title: Spring AI2.0实战05：多智能体协作——Agent 团队作战
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第五篇，教你如何构建多个智能体协作完成复杂任务，让 AI 不再是单兵作战，而是像团队一样分工协作。
coverImage: article-01/cover.png
---

# Spring AI2.0实战05：多智能体协作——Agent 团队作战

> 本文是《Spring AI2.0实战》系列的第五篇。承接前四篇，本篇教你如何构建多个智能体协作完成复杂任务，让 AI 不再是单兵作战，而是像团队一样分工协作。

---

## 一、什么是多智能体协作

单个 AI Agent 虽然能力强，但在面对复杂任务时容易出现遗漏或幻觉。多智能体协作（Multi-Agent Collaboration）的核心思路是：将复杂任务拆解，交给不同角色的 Agent 分别负责，再汇总结果。

常见的协作模式：

| 模式 | 说明 |
|------|------|
| **顺序编排** | Agent A 完成后，结果传给 Agent B 继续处理 |
| **并行执行** | 多个 Agent 同时处理不同子任务，最后合并 |
| **路由分发** | 根据用户输入，将任务分发给最合适的 Agent |
| **循环优化** | Agent A 输出 → Agent B 审查 → 反馈给 A 修改 → 重复直到达标 |

---

## 二、实战：代码评审团队

构建一个两人团队：**代码审查 Agent** 负责发现问题，**代码修复 Agent** 负责修复问题，两者顺序协作。

角色分工：

| Agent | 角色 | 职责 |
|-------|------|------|
| 审查 Agent | Senior Reviewer | 阅读代码，找出 Bug 和不合规之处 |
| 修复 Agent | Fixer | 根据审查意见，给出修复后的代码 |

---

## 三、顺序编排示例

使用 `SequentialAgent` 实现顺序编排，审查完成后自动传给修复 Agent：

```java
@Bean
public SequentialAgent codeReviewTeam() {
    Agent reviewer = ReactAgent.builder()
            .name("reviewer")
            .model(chatModel)
            .instruction("你是一位资深代码审查专家，擅长发现 Java 代码中的 Bug 和安全隐患。")
            .build();

    Agent fixer = ReactAgent.builder()
            .name("fixer")
            .model(chatModel)
            .instruction("你是一位代码修复专家，根据审查意见给出修复后的完整代码。")
            .build();

    return SequentialAgent.builder()
            .name("code-review-team")
            .agents(reviewer, fixer)
            .build();
}
```

调用示例：

```java
String result = codeReviewTeam()
        .run("请审查并修复以下代码：public class UserService { ... }");
System.out.println(result);
```

执行流程：

1. 审查 Agent 接收代码，输出审查意见
2. 修复 Agent 接收审查意见，输出修复后的代码
3. 最终返回修复后的完整结果

---

## 四、本文速记

1. 多智能体协作将复杂任务拆解给不同角色的 Agent
2. 常见模式包括顺序编排、并行执行、路由分发、循环优化
3. 使用 `SequentialAgent` 可快速构建顺序协作的 Agent 团队
4. 每个 Agent 通过 `instruction` 设定独立角色和职责

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战06：Graph 工作流——可编排的长期运行智能体》，教你如何构建有状态、可持久化的长期运行 AI 工作流。记得关注，不迷路。
