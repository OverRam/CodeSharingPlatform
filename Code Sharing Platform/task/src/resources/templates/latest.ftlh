<!DOCTYPE html>
<html>

<head>
    <title>Latest</title>
    <meta charset="UTF-8">
    <style>
     <#include "latest.css">

    </style>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>

<body>

<
#list modelList as model>
<span id="load_date">  ${model.getFormattedData()} </span>
<br>
<pre id="code_snippet"> <code>${model.script}</code></pre>
<br>
</#list>
</body>

</html>