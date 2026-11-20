package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * 第 06 篇：Graph 工作流——可编排的长期运行智能体
 * 演示如何构建有状态、可编排的 AI 工作流。
 */
@SpringBootApplication
public class GraphDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner graphDemo(ChatClient.Builder chatClientBuilder) {
		return args -> {
			// 节点 1：接单
			String receive = chatClientBuilder.build()
					.system("你是接单专员，负责记录订单信息，输出订单摘要。")
					.user("订单号：ORD-20261120，商品：笔记本电脑，数量：1")
					.call()
					.content();

			// 节点 2：审核（基于上一步结果）
			String audit = chatClientBuilder.build()
					.system("你是审核专员，检查订单是否符合规则，输出审核结论。")
					.user("以下是订单信息：" + receive)
					.call()
					.content();

			// 节点 3：出库（基于审核结果）
			String ship = chatClientBuilder.build()
					.system("你是出库专员，审核通过后安排发货，输出出库单。")
					.user("以下是审核结果：" + audit)
					.call()
					.content();

			System.out.println("\n=== Graph 工作流执行结果 ===");
			System.out.println(ship);
			System.out.println("==========================\n");
		};
	}
}
