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
    <%--<textarea id="blogName" cols="50" rows="2" placeholder="请输入博客名不能重复"></textarea>--%>
</div>

<div class="hea"></div>
<demo-nav title="Stan blog"></demo-nav>
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
    <span id="selected-length"></span>

  </div>
</div>







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


     var canEdit = 'no';
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
                 canEdit:canEdit
             },
             error: (function () {
                 $(".removeSelf").css({display: 'block'});
             }),
             dataType: 'json',
             success: (function (json) {
                 console.log(json);

                 switch (json.type) {
                     case "creatBlogSameName":
                         $("#bacUrl").html("博客名字已经存在");
                         break;
                     case "creatBlogSuccess":
                         createBlogSuccess(json);
                         $("#blogName").attr('disabled','disabled');
                         canEdit = 'yes';
                 }

             })

         })


         function createBlogSuccess(obj) {
             var url = obj.url;
             $("#bacUrl").html("<a href="+url+">"+url+"</a>");
             window.open(url,"_blank");
         }

     })



    var search = window.location.search;//获取参数；
    var name = getSearchString('name', search); //结果：18
    //key(需要检索的键） url（传入的需要分割的url地址，例：?id=2&age=18）
    function getSearchString(key, Url) {
        var str = Url;
        str = str.substring(1, str.length); // 获取URL中?之后的字符（去掉第一位的问号）
        // 以&分隔字符串，获得类似name=xiaoli这样的元素数组
        var arr = str.split("&");
        var obj = new Object();
        // 将每一个数组元素以=分隔并赋给obj对象
        for (var i = 0; i < arr.length; i++) {
            var tmp_arr = arr[i].split("=");
            obj[decodeURIComponent(tmp_arr[0])] = decodeURIComponent(tmp_arr[1]);
        }
        return obj[key];
    }
    console.log(name);

    var tmpName = name.toString();

    if (!(tmpName == 'undefined')){
        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type: 'post',
            url: "/blog/edit.do",
            data: {
                blogName:name
            },
            error: (function () {
                $(".removeSelf").css({display: 'block'});
            }),
            dataType: 'json',
            success: (function (json) {
                console.log(json);
                var  str = return2Br(json.blogHtml);
                editor.dangerouslyInsertHtml(json.blogHtml);
                $("#blogName").val(tmpName).attr('disabled','disabled');
                canEdit = 'yes';
            })
        })
    }


    function return2Br(str) {
        return str.replace(/\r?\n/g,"<br />");
    }




</script>
</body>
</html>
