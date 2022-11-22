<%--
  Created by IntelliJ IDEA.
  User: stan
  Date: 2022/11/21
  Time: 4:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>博客列表</title>
    <link rel="icon" href="img/108-1.png">
    <link rel="stylesheet" href="css/list.css">
</head>
<body>
<script type="text/javascript" src="/blog/js/jquery-1.4.2.js"></script>
<%--<div class="removeSelf" style="background-color: rgb(45,45,45);--%>
<%--border-radius: 5px;box-shadow: 0 0 50px rgba(0,0,0,0.2);--%>
<%--width: 400px;height: 200px;position: fixed;margin: auto;top: 0;--%>
<%--bottom: 0;right: 0;left: 0;color: white;text-align: center;line-height: 200px;--%>
<%--z-index: 999;">请求失败...</div>--%>
<div class="list"></div>

<script>



    function showBlogList() {

        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type: 'post',
            url: "/blog/list.do",
            data: {},
            error: (function () {
                $(".removeSelf").css({display: 'block'});
            }),
            dataType: 'json',
            success: (function (json) {
                console.log(json);
                showList(json.listName);
            })
        })




    }


    showBlogList();

    function showList(names) {

        var html = "";
        for(var i =0;i<names.length;i++){
            var url = "https://stanserver.cn:444/blog/"+names[i]+".html";
              html +=


                 "<div class=\"item\" deleName =\""+names[i]+"\">"
           +"<table>\n"
                  +"    <tr>\n"
            +"        <th>"+"<div class='info'>"
            +"<a class=\"headName\">"+names[i]+"</a>"
            +"<br/>"
            +"<a href="+url+">"+url+"</a>"
            +"</div>"+"</th>\n"
            +"        <th>"+"<div class='bb'>"

            +"<button class=\"dele\" deleName =\""+names[i]+"\">删除</button>"

                  +"<button class=\"edit\" blogName =\""+names[i]+"\">编辑</button>"
            +"</div>"+"</th>\n" +
            "    </tr>\n" +
            "</table>"
                +"</div>";
        }
        $(".list").html(html);
        bindButton();
    }

    function bindButton(){


        $(".edit").click(function (e) {
            var blogName = $(e.target).attr('blogName');
            var origin = window.location.origin;
            console.log(blogName);
            window.open(origin+"/blog/?name="+blogName,"_blank");
        });

        $(".dele").click(function (e) {
            var deleName = $(e.target).attr('deleName');
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                type: 'post',
                url: "/blog/deleBlog",
                data: {
                    blogName:deleName
                },
                error: (function () {
                    $(".removeSelf").css({display: 'block'});
                }),
                dataType: 'json',
                success: (function (json) {
                    console.log(json);
                    if (json.result == 'success'){

                        var  elem = $("[deleName=\'"+deleName+"\']");
                        elem.remove();

                    }
                })

            })

        })


    }
    






</script>
</body>
</html>
