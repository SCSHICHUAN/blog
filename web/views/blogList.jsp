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
    <title>世界列表</title>
    <link rel="icon" href="img/108-1.png">
    <link rel="stylesheet" href="css/list.css">
</head>
<body>
<script type="text/javascript" src="/blog/js/jquery-1.4.2.js"></script>
<%--<div class="editAlter">--%>
    <%--<label>删除博客</label>--%>
    <%--<label class="deName"></label>--%>
    <%--<input type="text" class="inputName" placeholder="博客名字">--%>
    <%--<button class="cancel">取消</button>--%>
    <%--<button class="confirm">删除</button>--%>
<%--</div>--%>
<div class="headTxt">List of blogs with <span class="allBlogs"></span> posts</div>
<div class="list"></div>
<script>

    function showBlogList() {

        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type: 'post',
            url: "/blog/list.do",
            data: {},
            error: (function (e) {
                $(".removeSelf").css({display: 'block'});
                console.log(e);
            }),
            dataType: 'json',
            success: (function (objs) {
                console.log(objs);
                showList(objs);
            })
        })
    }


    showBlogList();

    function showList(objs) {

        var html = "";
        for(var i =0;i<objs.length;i++){
            var url = "https://stanserver.cn:444/blog/"+objs[i].blogName+".html";
              html +=
            "<div class=\"item\" deleName =\""+objs[i].blogName+"\">"
            +"<table>\n"
            +"<tr>\n"

            +"<th class='t1'>"
               +"<a class='info'>"
               +"<a\ class=\"headName\">"+objs[i].blogName+"</a>"
               +"<br/>"
                  +"<a href='"+url+"'>"+url+"</a>"
               +"</div>"
            +"</th>\n"

            +"<th class='t2'>"
                  +"<div class='time'>创建:"+objs[i].createDate+"</div>"
                  +"<div class='timeEdit'> 修改:"+objs[i].updateDate+"</div>"
            +"</th>"

            +"<th class='t3'>"
               +"<div class='bb'>"
               // +"<button class=\"dele\" deleName =\""+objs[i].blogName+"\">删除</button>"
               +"<button class=\"edit\" blogName =\""+objs[i].blogName+"\">复制</button>"
               +"</div>"
            +"</th>\n"

            +"</tr>\n"
            +"</table>"
            +"</div>";
        }

        $(".allBlogs").html(objs.length);
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
           var delElem = $(e.target).attr('deleName');

            var removeEl = $('[alter="'+delElem+'"]');
            removeEl.remove();


             var ele =  "<div class=\"editAlter\" alter=\""+delElem+"\">\n" +
                 // "    <label class='blogDD'>删除博客:</label>\n" +
                 "    <label class=\"deName\">"+delElem+"</label>\n" +
                 "    <input type=\"text\" class=\"inputName\" placeholder=\"博客名字\">\n" +
                 "    <button class=\"cancel\" ddName=\""+delElem+"\">取消</button>\n" +
                 "    <button class=\"confirm\" ddName=\""+delElem+"\">删除</button>\n" +
                 "</div>";

             var item =  $(e.target).parents('[deleName="'+delElem+'"]');
             item.after(ele);
             console.log(item);
             bundClickDele();

        });

    }

    /**
     * 动态生成的Document 要绑定一下
     * */
    function bundClickDele(){

        $(".cancel").click(function (e) {
            var delName = $(e.target).attr('ddName');
            var editAlter = $(e.target).parents('[alter="'+delName+'"]');
            editAlter.remove();
        });
        $(".confirm").click(function (e) {

            var deleName = $(e.target).attr('ddName');
            var inputName = $(".inputName").val();
            if (deleName != inputName) return;
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                type: 'post',
                url: "/blog/deleBlog.sc",
                data: {
                    blogName:deleName
                },
                error: (function () {
                    alert("你还没有登陆，或者请求失败");
                }),
                dataType: 'json',
                success: (function (json) {
                    console.log(json);
                    if (json.result == 'success'){

                        var  elem = $("[deleName=\'"+deleName+"\']");
                        elem.remove();

                        var editAlter = $("[alter=\'"+deleName+"\']");;
                        editAlter.remove();

                    }else if(json.result == "noself") {
                        alert("不是你的文章不能删除！");
                    }
                })

            })
        })

    }

</script>
</body>
</html>
