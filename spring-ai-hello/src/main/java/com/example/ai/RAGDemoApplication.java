package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 第 07 篇：RAG 实战——私有知识库问答
 * 演示如何构建基于向量检索的企业知识库问答系统。
 */
@SpringBootApplication
public class RAGDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RAGDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner ragDemo(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
		return args -> {
			// 1. 加载文档
			Document document = new Document(
				"Spring AI Alibaba 是阿里云基于 Spring AI 构建的 Java AI 应用开发框架，" +
				"深度集成通义大模型，支持多模型、流式输出、Function Calling、Agent、Graph 工作流、RAG、MCP 等能力。"
			);

			// 2. 向量化存储
			vectorStore.add(List.of(document));

			// 3. 检索相关片段
			List<Document> results = vectorStore.similaritySearch("Spring AI Alibaba 是什么？", 3);

			// 4. 构建上下文并调用模型
			String context = results.stream()
					.map(Document::getContent)
					.collect(Collectors.joining("\n"));

			String answer = chatClientBuilder.build()
					.prompt("基于以下文档回答问题：\n" + context + "\n\n问题：Spring AI Alibaba 是什么？")
					.call()
					.content();

			System.out.println("\n=== RAG 问答结果 ===");
			System.out.println(answer);
			System.out.println("==================\n");
		};
	}
}
