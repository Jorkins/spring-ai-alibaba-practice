@echo off
cd /d "C:\Users\EDY\.agents\skills\baoyu-post-to-wechat"
npx -y bun scripts/wechat-api.ts "C:\develop\source\spring-ai-alibaba-practice\article-03.md" --theme default --color blue --title "Spring AI2.0实战03：Prompt 工程实战——模板、变量与系统角色" --summary "本文是《Spring AI2.0实战》系列的第三篇，教你如何用 Prompt 模板让 AI 按照指定角色和格式回答，实现变量动态替换与系统角色设定，让 Java 应用的 AI 交互更可控、更专业。" --author Jorkins --cover "C:\develop\source\spring-ai-alibaba-practice\article-01\cover.png"
