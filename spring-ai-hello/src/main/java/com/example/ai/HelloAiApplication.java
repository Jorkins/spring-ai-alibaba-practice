package com.example.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
