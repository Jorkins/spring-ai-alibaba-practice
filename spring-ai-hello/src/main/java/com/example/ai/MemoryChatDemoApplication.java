package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 第 09 篇：上下文工程与对话记忆
 * 演示如何构建带记忆的对话系统，让 AI 能够记住历史对话。
 */
@SpringBootApplication
public class MemoryChatDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoryChatDemoApplication.class, args);
	}

	@Bean
	public ChatMemory chatMemory() {
		// 最近 10 条消息作为上下文窗口
		return MessageWindowChatMemory.builder()
				.maxMessages(10)
				.build();
	}

	@Bean
	CommandLineRunner memoryDemo(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
		return args -> {
			ChatClient chatClient = chatClientBuilder.build();

			// 第一轮对话
			String answer1 = chatClient.prompt("你好，我叫小明，喜欢编程。")
					.call()
					.content();
			System.out.println("用户：你好，我叫小明，喜欢编程。");
			System.out.println("AI：" + answer1);
			System.out.println();

			// 第二轮对话，AI 应该记住用户名字
			String answer2 = chatClient.prompt("你还记得我叫什么吗？")
					.call()
					.content();
			System.out.println("用户：你还记得我叫什么吗？");
			System.out.println("AI：" + answer2);
			System.out.println();

			// 第三轮对话
			String answer3 = chatClient.prompt("我刚才说我喜欢什么？")
					.call()
					.content();
			System.out.println("用户：我刚才说我喜欢什么？");
			System.out.println("AI：" + answer3);
		};
	}
}
