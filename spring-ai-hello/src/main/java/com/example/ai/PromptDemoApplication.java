package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * 第 03 篇：Prompt 工程实战——模板、变量与系统角色
 * 演示 PromptTemplate 变量替换与系统角色设定。
 */
@SpringBootApplication
public class PromptDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromptDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner promptDemo(ChatClient.Builder chatClientBuilder) {
		return args -> {
			// 系统角色 + 用户角色组合
			String answer = chatClientBuilder.build()
					.system("你是一位资深 Java 架构师，擅长 Spring Boot 和 AI 应用开发。" +
							"请用专业但易懂的中文回答，每段不超过 3 句话。")
					.user("请用 3 句话解释什么是 Spring AI Alibaba")
					.call()
					.content();
			System.out.println("\n=== 系统角色示例 ===");
			System.out.println(answer);
			System.out.println("====================\n");

			// PromptTemplate 变量替换
			PromptTemplate promptTemplate = new PromptTemplate(
					"请用 {count} 句话解释 {topic}，面向 {audience} 读者"
			);
			Message message = promptTemplate.createMessage(Map.of(
					"count", 3,
					"topic", "Spring AI Alibaba",
					"audience", "Java 后端开发者"
			));
			String templateAnswer = chatClientBuilder.build()
					.prompt(message)
					.call()
					.content();
			System.out.println("\n=== PromptTemplate 变量替换 ===");
			System.out.println(templateAnswer);
			System.out.println("==============================\n");
		};
	}
}
