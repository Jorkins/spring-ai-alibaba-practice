$bun = "C:\Users\EDY\AppData\Local\npm-cache\_npx\5c4f1b4a21be27f7\node_modules\bun\bin\bun.exe"
$script = "C:\Users\EDY\.agents\skills\baoyu-post-to-wechat\scripts\wechat-api.ts"
$md = "C:\develop\source\spring-ai-alibaba-practice\article-03.md"
$title = Get-Content "C:\develop\source\spring-ai-alibaba-practice\title-03.txt" -Raw
$summary = Get-Content "C:\develop\source\spring-ai-alibaba-practice\summary-03.txt" -Raw
& $bun $script $md --theme default --color blue --title $title --summary $summary --author Jorkins
