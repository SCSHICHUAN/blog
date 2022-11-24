<%--
  Created by IntelliJ IDEA.
  User: stan
  Date: 2022/11/14
  Time: 7:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html><%--这里让我找了一天，编辑器外框不会根着内容一起放大，这行默认是<html>--%>
<head>
    <link rel="icon" href="img/108-1.png">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>石川的博客</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/normalize/8.0.1/normalize.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/@wangeditor/editor@latest/dist/css/style.css" rel="stylesheet">
    <script src="https://unpkg.com/@wangeditor/editor@latest/dist/index.js"></script>
    <link href="./css/layout.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <script type="text/javascript" src="/blog/js/jquery-1.4.2.js"></script>
</head>


<body>
<div class="wel3">
  <button class="commit">提交</button>
  <label class="wel4"><span id="total-length"></span><label id="bacUrl"></label></label>
</div>
<div class="wel1">Welcome to my blog Create Space</div>
<div class="wel2">
   <input id="blogName" placeholder="请输入博客名不能重复">
</div>

      <div id="editor-toolbar" style="border-bottom: 1px solid #ccc;"></div>
      <div id="editor-text-area"></div>

<script type="text/javascript" src="/blog/js/editMain.js"></script>
<div style="text-align:center;padding-bottom: 10px;padding-top: 20px"><code>stanserver.cn stanserver@163.com</code></div></body>
</body>
</html>
