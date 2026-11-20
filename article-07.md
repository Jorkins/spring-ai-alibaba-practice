---
title: Spring AI2.0实战07：RAG 实战——私有知识库问答
author: Jorkins
summary: 本文是《Spring AI2.0实战》系列的第七篇，教你如何构建基于向量检索的企业知识库问答系统，让 AI 能够回答基于你私有文档的问题，不再受限于大模型训练数据。
coverImage: article-01/cover.png
---

# Spring AI2.0实战07：RAG 实战——私有知识库问答

> 本文是《Spring AI2.0实战》系列的第七篇。承接前六篇，本篇教你如何构建基于向量检索的企业知识库问答系统，让 AI 能够回答基于你私有文档的问题，不再受限于大模型训练数据。

---

## 一、什么是 RAG

**RAG（Retrieval-Augmented Generation，检索增强生成）** 是目前企业级 AI 应用的主流架构。它的核心思路是：用户提问前，先从私有知识库中检索相关片段，将片段作为上下文喂给大模型，让模型基于真实文档回答，减少幻觉。

核心优势：

| 维度 | 说明 |
|------|------|
| **私有知识** | 基于企业自有文档回答，不泄露训练数据 |
| **减少幻觉** | 有文档依据，回答更准确、可溯源 |
| **知识可更新** | 更新文档即可，无需重新训练模型 |
| **成本可控** | 不需要微调大模型，只需维护向量库 |

---

## 二、RAG 工作流程

RAG 的完整链路分为四步：

| 步骤 | 说明 |
|------|------|
| **1. 文档加载** | 读取 PDF、Word、TXT 等私有文档 |
| **2. 文档分割** | 将长文档切分为适合嵌入的片段（Chunk） |
| **3. 向量化存储** | 将片段转为向量，存入向量数据库 |
| **4. 检索增强** | 用户提问时，检索相关片段，作为 Prompt 上下文喂给大模型 |

---

## 三、实战：企业文档问答

构建一个基于本地文档的问答助手，支持上传文档并提问。

```java
@Bean
public CommandLineRunner ragDemo(VectorStore vectorStore, ChatModel chatModel) {
    return args -> {
        // 1. 加载文档
        Document document = new Document("Spring AI Alibaba 是阿里云基于 Spring AI 构建的 Java AI 框架。");
        
        // 2. 分割文档
        TextSplitter splitter = new RecursiveCharacterTextSplitter();
        List<Document> chunks = splitter.split(List.of(document));
        
        // 3. 向量化存储
        vectorStore.add(chunks);
        
        // 4. 检索
        List<Document> results = vectorStore.similaritySearch("Spring AI Alibaba 是什么？", 3);
        
        // 5. 构建 Prompt 并调用模型
        String context = results.stream().map(Document::getContent).collect(Collectors.joining("\n"));
        String answer = chatModel.call("基于以下文档回答问题：\n" + context + "\n\n问题：Spring AI Alibaba 是什么？");
        
        System.out.println(answer);
    };
}
```

核心依赖（`pom.xml`）：

```xml
<dependency>
    <groupId>com.alibaba.cloud.ai</groupId>
    <artifactId>spring-ai-alibaba-starter-dashscope</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-chroma</artifactId>
</dependency>
```

---

## 四、配置向量数据库

在 `application.yml` 中配置向量存储（以 Chroma 为例）：

```yaml
spring:
  ai:
    vectorstore:
      chroma:
        enabled: true
        host: localhost
        port: 8000
```

启动向量数据库（Docker）：

```bash
docker run -p 8000:8000 chromadb/chroma
```

---

## 五、本文速记

1. RAG 让 AI 基于私有文档回答，减少幻觉，提升准确性
2. 核心流程：加载文档 → 分割 → 向量化存储 → 检索增强
3. 需要向量数据库（如 Chroma、Milvus、Pinecone 等）
4. 适合企业知识库、客服问答、文档摘要等场景

---

GitHub 仓库：https://github.com/Jorkins/spring-ai-alibaba-practice

👇 **下一篇预告**：《Spring AI2.0实战08：MCP 集成——标准化工具协议》，教你如何接入模型上下文协议，让 AI 能够调用本地文件、数据库等标准化能力。记得关注，不迷路。
