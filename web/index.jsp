<%--
  Created by IntelliJ IDEA.
  User: stan
  Date: 2022/11/14
  Time: 7:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="icon" href="img/108-1.png">
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>石川的博客</title>
  <link href="https://cdn.bootcdn.net/ajax/libs/normalize/8.0.1/normalize.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/@wangeditor/editor@latest/dist/css/style.css" rel="stylesheet">
  <link href="https://unpkg.com/@wangeditor/editor@latest/dist/css/style.css" rel="stylesheet">
  <link href="./css/layout.css" rel="stylesheet">
</head>

  <script type="text/javascript" src="/blog/js/jquery-1.4.2.js"></script>
</head>
<body>

<demo-nav title="Stan blog"></demo-nav>
<textarea  id="blogName" cols="30" rows="2"></textarea>
<div class="page-container">
  <div class="page-left">
    <demo-menu></demo-menu>
  </div>
  <div class="page-right">
    <!-- 编辑器 DOM -->
    <div style="border: 1px solid #ccc;">
      <div id="editor-toolbar" style="border-bottom: 1px solid #ccc;"></div>
      <div id="editor-text-area" style="height: 500px"></div>
    </div>

    <!-- 内容状态 -->
    <p style="background-color: #f1f1f1;">
      Text length: <span id="total-length"></span>；
      Selected text length: <span id="selected-length"></span>；
    </p>
  </div>
</div>

<button class="commit">提交</button>
<h1 id="bacUrl"></h1>
<!-- <script src="https://cdn.jsdelivr.net/npm/@wangeditor/editor@latest/dist/index.min.js"></script> -->
<script src="https://unpkg.com/@wangeditor/editor@latest/dist/index.js"></script>
<script>



    const E = window.wangEditor
    // 切换语言
    const LANG = location.href.indexOf('lang=en') > 0 ? 'en' : 'zh-CN'
    E.i18nChangeLanguage(LANG)

    window.editor = E.createEditor({
        selector: '#editor-text-area',
        html: '<p><br></p>',
        config: {
            placeholder: 'Type here...',
            MENU_CONF: {
                uploadImage: {
                    fieldName: 'your-fileName',
                    base64LimitSize: 10 * 1024 * 1024 // 10M 以下插入 base64
                }
            },
            onChange(editor) {
                console.log(editor.getHtml())

                // 选中文字
                const selectionText = editor.getSelectionText()
                document.getElementById('selected-length').innerHTML = selectionText.length
                // 全部文字
                const text = editor.getText().replace(/\n|\r/mg, '')
                document.getElementById('total-length').innerHTML = text.length
            }
        }
    })

     window.toolbar = E.createToolbar({
        editor,
        selector: '#editor-toolbar',
        config: {}
     })


     $(".commit").click(function () {




         var  blogName = $("#blogName").val();
         var  result = window.editor.getHtml();

         $.ajax({
             contentType: "application/x-www-form-urlencoded; charset=utf-8",
             type: 'post',
             url: "/blog/addBlog",
             data: {
                 blogName:blogName,
                 html: result,
             },
             error: (function () {
                 $(".removeSelf").css({display: 'block'});
             }),
             dataType: 'text',
             success: (function (json) {
                 console.log(json);
                 $("#bacUrl").replaceWith("<a href="+json+">"+json+"</a>");
                 window.open(json,"_blank");
             })

         })

     })

</script>
</body>
</html>
