package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 第 05 篇：多智能体协作——Agent 团队作战
 * 演示如何构建多个智能体顺序协作完成复杂任务。
 */
@SpringBootApplication
public class MultiAgentDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiAgentDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner multiAgentDemo(ChatClient.Builder chatClientBuilder) {
		return args -> {
			// 审查 Agent：发现代码问题
			String review = chatClientBuilder.build()
					.system("你是一位资深代码审查专家，擅长发现 Java 代码中的 Bug 和安全隐患。" +
							"请列出发现的问题，每点不超过一行。")
					.user("请审查以下代码：public class UserService { public void save() { System.out.println(\"saved\"); } }")
					.call()
					.content();

			// 修复 Agent：根据审查意见修复代码
			String fix = chatClientBuilder.build()
					.system("你是一位代码修复专家，根据审查意见给出修复后的完整代码。")
					.user("以下是审查意见：" + review + "\n请给出修复后的完整代码。")
					.call()
					.content();

			System.out.println("\n=== 多智能体协作结果 ===");
			System.out.println(fix);
			System.out.println("=====================\n");
		};
	}
}
