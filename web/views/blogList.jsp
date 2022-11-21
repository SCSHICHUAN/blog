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
    <title>Title</title>
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

    // $(".abc").click(function () {
    //     showBlogList();
    // })


    // setTimeout(function(){
         showBlogList();
    // },1000);



    $.ajaxSetup({complete:function(xhr,status){
            //若HEADER中含有REDIRECT说明后端想重定向
            if("REDIRECT" == xhr.getResponseHeader("REDIRECT")){
                //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求
                window.location.href = xhr.getResponseHeader("CONTENTPATH");
            }
        }});



    function showList(names) {

        var html = "";
        for(var i =0;i<names.length;i++){
            var url = "https://stanserver.cn:444/blog/"+names[i]+".html";
            html += "<div class=\"item\">"+"<a href="+url+">"+url+"</a>"
                +"<button class=\"edit\" blogName =\""+names[i]+"\">编辑</button>"+"</div>"
        }
        $(".list").html(html);
        bindButton();
    }

    function bindButton(){
        $(".edit").click(function (e) {
            var blogName = $(e.target).attr('blogName');
            console.log(blogName);

            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                type: 'post',
                url: "/blog/edit",
                data: {
                    blogName:blogName
                },
                error: (function () {
                    $(".removeSelf").css({display: 'block'});
                }),
                dataType: 'json',
                success: (function (json) {
                    console.log(json);
                    showList(json.listName);
                })
            })




        });
    }
    






</script>
</body>
</html>
