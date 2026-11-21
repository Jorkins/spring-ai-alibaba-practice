package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 第 10 篇：生产级部署与可观测
 * 演示生产环境下的 AI 调用与可观测集成示例。
 */
@SpringBootApplication
public class ProductionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductionDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner productionDemo(ChatClient.Builder chatClientBuilder) {
		return args -> {
			String answer = chatClientBuilder.build()
					.prompt("请介绍 Spring AI Alibaba 的生产部署要点")
					.call()
					.content();
			System.out.println("\n=== 生产部署示例 ===");
			System.out.println(answer);
			System.out.println("==================\n");
		};
	}
}
