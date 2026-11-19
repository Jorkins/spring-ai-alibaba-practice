$bun = "C:\Users\EDY\AppData\Local\npm-cache\_npx\5c4f1b4a21be27f7\node_modules\bun\bin\bun.exe"
$script = "C:\Users\EDY\.agents\skills\baoyu-post-to-wechat\scripts\wechat-api.ts"
$md = "C:\develop\source\spring-ai-alibaba-practice\test-simple.md"
$title = Get-Content "C:\develop\source\spring-ai-alibaba-practice\title-test.txt" -Raw
$summary = Get-Content "C:\develop\source\spring-ai-alibaba-practice\summary-test.txt" -Raw
& $bun $script $md --theme default --color blue --title $title --summary $summary --author Jorkins
