---
title: Spring AI2.0实战08：MCP 集成——标准化工具协议
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第八篇，教你如何接入模型上下文协议（MCP），让 AI 能够调用本地文件、数据库等标准化能力，实现工具能力的互操作。
coverImage: article-01/cover.png
---

# Spring AI2.0实战08：MCP 集成——标准化工具协议

> 本文是《Spring AI2.0实战》系列的第八篇。承接前七篇，本篇教你如何接入模型上下文协议（MCP），让 AI 能够调用本地文件、数据库等标准化能力，实现工具能力的互操作。

---

## 一、什么是 MCP

**MCP（Model Context Protocol，模型上下文协议）** 是 Anthropic 推出的开放标准协议，旨在解决 AI 模型与外部工具、数据源之间的连接问题。它就像 USB 接口一样，为 AI 提供统一的工具调用标准。

核心优势：

| 维度 | 说明 |
|------|------|
| **标准化** | 一套协议，多种工具互通，避免重复开发 |
| **互操作性** | 不同 AI 模型可复用同一套 MCP 服务 |
| **本地优先** | 支持本地文件、数据库等私有资源的安全访问 |
| **生态丰富** | 已有文件系统、Git、PostgreSQL 等官方 MCP 服务 |

---

## 二、MCP 核心架构

MCP 采用客户端 - 服务器架构：

| 组件 | 说明 |
|------|------|
| **MCP Client** | 嵌入在 AI 应用中，负责连接 MCP Server 并发现可用工具 |
| **MCP Server** | 独立进程，封装具体能力（如文件读取、数据库查询） |
| **工具发现** | AI 通过 Client 向 Server 查询可用工具列表 |
| **工具调用** | AI 决定调用哪个工具，Client 转发请求并返回结果 |

---

## 三、实战：接入本地文件系统 MCP

通过 Spring AI Alibaba 的 MCP 支持，让 AI 能够读取本地文件。

```java
@Bean
public McpClient mcpClient() {
    return McpClient.builder()
            .server("filesystem")
            .command("npx")
            .args("-y", "@modelcontextprotocol/server-filesystem", "/tmp/workspace")
            .build();
}
```

调用示例：

```java
@Bean
CommandLineRunner mcpDemo(McpClient mcpClient, ChatClient.Builder chatClientBuilder) {
    return args -> {
        // 发现可用工具
        List<ToolCallback> tools = mcpClient.getTools();
        
        // AI 决定调用文件读取工具
        String answer = chatClientBuilder.build()
                .system("你是一个文件助手，需要调用文件系统工具读取文件内容。")
                .user("请读取 /tmp/workspace/README.md 文件的内容")
                .tools(tools)
                .call()
                .content();
        
        System.out.println(answer);
    };
}
```

核心依赖（`pom.xml`）：

```xml
<dependency>
    <groupId>com.alibaba.cloud.ai</groupId>
    <artifactId>spring-ai-alibaba-starter-mcp</artifactId>
</dependency>
```

---

## 四、常见 MCP 服务

| 服务 | 说明 | 适用场景 |
|------|------|----------|
| **文件系统** | 读取本地文件、目录操作 | 文档分析、配置读取 |
| **PostgreSQL** | 数据库查询、表结构探测 | 业务数据问答 |
| **Git** | 仓库操作、提交历史 | 代码库分析 |
| **Fetch** | 网页内容抓取 | 互联网信息检索 |

---

## 五、本文速记

1. MCP 是 AI 工具调用的标准化协议，避免重复造轮子
2. MCP Client 嵌入应用，MCP Server 独立部署，两者通过 stdio 或 HTTP 通信
3. Spring AI Alibaba 提供 MCP Starter，快速接入标准化能力
4. 适合本地文件、数据库等私有资源的安全调用

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战09：上下文工程与对话记忆》，教你如何构建带记忆的对话系统，让 AI 能够记住历史对话并保持上下文连贯。记得关注，不迷路。
