package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 第 02 篇：ChatModel 多模型接入与流式输出
 * 演示如何切换模型并实现流式输出。
 */
@SpringBootApplication
public class ChatModelDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatModelDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner streamDemo(ChatClient.Builder chatClientBuilder) {
		return args -> {
			chatClientBuilder.build()
					.prompt("请写一段关于 Spring AI Alibaba 的简短介绍")
					.stream()
					.content()
					.doOnNext(System.out::print)
					.blockLast();
		};
	}
}
