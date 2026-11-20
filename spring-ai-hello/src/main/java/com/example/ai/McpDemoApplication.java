package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.client.McpClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 第 08 篇：MCP 集成——标准化工具协议
 * 演示如何接入 MCP 服务，让 AI 能够调用本地文件等标准化能力。
 */
@SpringBootApplication
public class McpDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpDemoApplication.class, args);
	}

	@Bean
	public McpClient mcpClient() {
		return McpClient.builder()
				.server("filesystem")
				.command("npx")
				.args("-y", "@modelcontextprotocol/server-filesystem", "/tmp/workspace")
				.build();
	}

	@Bean
	CommandLineRunner mcpDemo(McpClient mcpClient, ChatClient.Builder chatClientBuilder) {
		return args -> {
			// 发现可用工具
			List<ToolCallback> tools = mcpClient.getTools();

			// AI 决定调用文件系统工具读取文件
			String answer = chatClientBuilder.build()
					.system("你是一个文件助手，需要调用文件系统工具读取文件内容。")
					.user("请读取 /tmp/workspace/README.md 文件的内容")
					.tools(tools)
					.call()
					.content();

			System.out.println("\n=== MCP 工具调用示例 ===");
			System.out.println(answer);
			System.out.println("=====================\n");
		};
	}
}
