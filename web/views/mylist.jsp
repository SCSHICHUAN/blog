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
    <title>我的列表</title>
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
            url: "/blog/mylist.do.sc",
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
                "<div class=\"item\"  item = \""+i+"\" deleName =\""+objs[i].blogName+"\">"
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
                +"<button class=\"dele\" delsc = \""+i+"\"  deleName =\""+objs[i].blogName+"\">删除</button>"
                +"<button class=\"edit\" editsc = \""+i+"\"  blogName =\""+objs[i].blogName+"\">编辑</button>"
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




        /**
         * 编辑
         */
        $(".edit").click(function (e) {
            var blogName = $(e.target).attr('blogName');
            var origin = window.location.origin;
            console.log(blogName);
            window.open(origin+"/blog/?name="+blogName,"_blank");
        });

        /**
           删除
         */
        $(".dele").click(function (e) {
            var delElem = $(e.target).attr('deleName');
            var delElemID = $(e.target).attr('delsc');

            var removeEl = $('[scAlter="'+delElemID+'ppc"]');
            removeEl.remove();


            var ele =  "<div class=\"editAlter\" scAlter = \""+delElemID+"ppc\" alter=\""+delElem+"\">\n" +
                "    <label class='blogDD'>删除博客:</label>\n" +
                "    <label class=\"deName\">["+delElem+"]</label>\n" +
                "    <input type=\"text\" class=\""+delElemID+"ppc\" placeholder=\"博客名字\">\n" +
                "    <button class=\"cancel\"  ddsc=\""+delElemID+"ppc\" ddName=\""+delElem+"\">取消</button>\n" +
                "    <button class=\"confirm\"  ddsc=\""+delElemID+"ppc\"  ddName=\""+delElem+"\">删除</button>\n" +
                "</div>";

            var item =  $(e.target).parents('[item="'+delElemID+'"]');
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
            var delElemID = $(e.target).attr('ddsc');
            var editAlter = $(e.target).parents('[scAlter="'+delElemID+'"]');
            editAlter.remove();
        });
        $(".confirm").click(function (e) {

            var deleName = $(e.target).attr('ddName');
            var idsc = $(e.target).attr('ddsc');
            var inputName = $("."+idsc+"").val();
            if (return2Br(deleName) != return2Br(inputName)){
                alert("输入的博客名字错误！");
            }else {
                $.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    type: 'post',
                    url: "/blog/deleBlog.sc",
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

                            var editAlter = $("[alter=\'"+deleName+"\']");;
                            editAlter.remove();

                        }
                    })

                })
            }

        })

    }


    function return2Br(str) {
        var tmpStr = str;
        tmpStr = str.replace(/\s*/g,"");
        tmpStr = tmpStr.replace(/\r?\n/g,"");
        tmpStr = tmpStr.replace(".","");
        return tmpStr;
    }

</script>
</body>
</html>
