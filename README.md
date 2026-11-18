# Spring AI Alibaba 实战配套代码

本仓库配套公众号系列文章《Java AI Agent 实战：Spring AI Alibaba 从入门到生产》。

适合零基础接触 AI 应用开发的 Java 后端开发者，不需要 Python 基础，只需要你熟悉 Spring Boot。

## 文章与分支对应

| 文章 | 分支 | 说明 |
|------|------|------|
| 第01篇 背景与选型 | article-01-concept | 概念笔记，无代码 |
| 第02篇 5 分钟搭建 Hello World | article-02-helloworld | 调用通义大模型返回文字 |
| 第03篇 多模型与流式输出 | article-03-chatmodel | 流式聊天机器人 |
| 第04篇 Prompt 工程实战 | article-04-prompt | 角色扮演助手 |
| 第05篇 Function Calling | article-05-function-calling | 天气查询助手（调用本地 Java 方法） |
| 第06篇 ReactAgent | article-06-react-agent | 可联网查询的 Agent |
| 第07篇 多智能体协作 | article-07-multi-agent | 代码评审团队（审查 Agent + 修复 Agent） |
| 第08篇 Graph 工作流 | article-08-graph | 订单处理工作流 |
| 第09篇 对话记忆 | article-09-memory | 带记忆的客服机器人 |
| 第10篇 RAG 知识库问答 | article-10-rag | 企业文档问答助手 |
| 第11篇 MCP 集成 | article-11-mcp | 接入本地文件系统 MCP 服务 |
| 第12篇 生产级部署与可观测 | article-12-admin | 可视化调试后的生产应用 |

分支将随文章发布陆续创建。

## 环境要求

- JDK 17+
- Spring Boot 3.x
- 阿里云 DashScope API Key（https://help.aliyun.com/zh/dashscope/）

## 快速开始

```bash
# 克隆仓库
git clone https://github.com/Jorkins/spring-ai-alibaba-practice.git

# 进入对应文章分支（以第02篇为例）
cd spring-ai-alibaba-practice
git checkout article-02-helloworld

# 配置 application.yml 中的 api-key 后运行
./mvnw spring-boot:run
```

## 许可证

Apache License 2.0
