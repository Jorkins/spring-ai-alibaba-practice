package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 第 04 篇：Function Calling——让 Java 方法变成 AI 工具
 * 演示如何把本地 Java 方法注册为 AI 可调用的工具。
 */
@SpringBootApplication
public class FunctionCallingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunctionCallingDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner functionDemo(ChatClient.Builder chatClientBuilder) {
		return args -> {
			String answer = chatClientBuilder.build()
					.system("你是一个天气助手，用户询问天气时请调用 get_weather 工具。")
					.user("杭州今天天气怎么样？")
					.call()
					.content();
			System.out.println("\n=== Function Calling 示例 ===");
			System.out.println(answer);
			System.out.println("============================\n");
		};
	}

	@Bean
	public ToolCallback weatherTool() {
		return FunctionToolCallback.builder("get_weather", new WeatherService())
				.description("查询指定城市的天气信息")
				.inputType(WeatherRequest.class)
				.build();
	}

	public static class WeatherRequest {
		@org.springframework.ai.tool.annotation.Description("城市名称，如 杭州、北京")
		private String city;

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}
	}

	public static class WeatherService {
		@org.springframework.ai.tool.annotation.Description("查询天气")
		public String getWeather(WeatherRequest request) {
			return request.getCity() + "今天晴朗，气温 25°C，适合出行。";
		}
	}
}
