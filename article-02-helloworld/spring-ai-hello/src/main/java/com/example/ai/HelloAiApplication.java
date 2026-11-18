/*
 * Copyright 2024-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 第 02 篇：5 分钟搭建第一个 Spring AI Alibaba 应用
 *
 * 启动后会自动调用通义大模型，并在控制台打印 AI 回答。
 */
@SpringBootApplication
public class HelloAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloAiApplication.class, args);
	}

	@Bean
	CommandLineRunner hello(ChatClient.Builder chatClientBuilder) {
		return args -> {
			String answer = chatClientBuilder.build()
					.prompt("你好，请用一句话介绍 Spring AI Alibaba")
					.call()
					.content();
			System.out.println("\n=== AI 回答 ===");
			System.out.println(answer);
			System.out.println("==============\n");
		};
	}
}
