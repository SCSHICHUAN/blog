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
    <title>STAN博客</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/normalize/8.0.1/normalize.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/@wangeditor/editor@latest/dist/css/style.css" rel="stylesheet">
    <script src="https://unpkg.com/@wangeditor/editor@latest/dist/index.js"></script>
    <%--<link href="/blog/wangEdit/normalize.css" rel="stylesheet">--%>
    <%--<link href="/blog/wangEdit/style.css" rel="stylesheet">--%>
    <%--<script src="/blog/wangEdit/edit.js"></script>--%>

    <link href="css/index.css" rel="stylesheet">
    <script type="text/javascript" src="/blog/js/jquery-1.4.2.js"></script>
</head>



<div class="wel3">
  <button class="commit">提交</button>
  <label class="wel4"><span id="total-length"></span><label id="bacUrl"></label></label>
    <div class="longin">
        <button class="save">save</button>
        <button class="world">world</button>
        <button class="blogs">my blos</button>
        <button class="quit">quit</button>
        <label class="longinTips"></label>
        <button class="showLogin">未登陆</button>
    </div>
</div>


<div class="wel1">Welcome to my blog Create Space</div>
<div class="wel2">
    <input id="categoryName" placeholder="分组名字默认 Others">
</div>
<div class="wel2">
   <input id="blogName" placeholder="请输入博客名不能重复">
</div>

<div id="editor-toolbar" style="border-bottom: 1px solid #ccc;"></div>
<div id="editor-text-area"></div>

<script type="text/javascript" src="/blog/js/editMain.js"></script>
<div style="text-align:center;padding-bottom: 10px;padding-top: 20px"><code>stanserver.cn stanserver@163.com</code></div></body>
<script>


    function creatLoginView(){

        return "<div class=\"longin1\">\n" +
            "    <button class=\"mailBtn\">忘记密码未注册</button>\n" +
            "    <input id = \"mail\" placeholder=\"mail\">\n" +
            "    <input id = \"mailCode\" placeholder=\"邮箱验证码\">\n" +
            "    <input id = \"usrName\" placeholder=\"user\">\n" +
            "    <input id = \"pwd\" placeholder=\"password\">\n" +
            "    <input id = \"repwd\" placeholder=\"确定密码\">\n" +
            "</div>"

    }

    $(".showLogin").click(function (e) {
        var ele = $(".showLogin");
        var cotent = ele.text();
        if (cotent == "未登陆"){

            $(".longin1").remove();
            $(".wel3").after(creatLoginView());
            blinMailBtn();

            ele.text('登陆');

            var ele2 = $(".longin1").animate({height:'40px'});
            $("#usrName").animate({top:'40px'});
            $("#pwd").animate({top:'40px'});
            $(".mailBtn").animate({top:'40px'});
            $("#mail").animate({top:'40px'});
            $("#mailCode").animate({top:'40px'});

        }else if(cotent == '登陆'){
            loginAction();
        }else if (cotent == "注册或找回密码"){
            registerAction();
        }

    })

    function blinMailBtn(){

        $(".mailBtn").click(function (e) {

            if ($(e.target).text() == '获取邮件验证码'){

                var  mail = $("#mail").val();

                if(mail.length <= 0){
                    $(".longinTips").text('请输入你的邮件！');
                    alert("请输入你的邮件！");
                }else {

                    $.ajax({
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        type: 'post',
                        url: "/blog/sendMailCodeBlog",
                        data: {
                            mail:mail
                        },
                        error: (function () {
                            $(".longinTips").text('请求错误');
                        }),
                        dataType: 'text',
                        success: (function (json) {
                            console.log(json);
                            if(json == "success"){
                                $(".longinTips").text('邮件已经发送成功！');
                                alert("邮件已经发送成功！")
                            }else {
                                $(".longinTips").text('邮件发送失败！');
                                alert("邮件发送失败");
                            }
                        })
                    })
                }

            }else {

                $(".showLogin").text('注册或找回密码');
                $(e.target).text("获取邮件验证码");
                $("#mail").animate({opacity:'1'});
                $("#mailCode").animate({opacity:'1'});

                $("#usrName").animate({right:'330px'});
                $("#pwd").animate({right:'170px'});
                $("#repwd").animate({top:'40px',right:'10px',width:'150px',opacity:1});

            }
        })

    }




     function loginAction() {
        var usr = $("#usrName").val();
        var pwd = $("#pwd").val();


        if(usr.length <= 0 || pwd.length <= 0){
            $(".longinTips").text('请输入用户名或密码');
        }else {
            $(".longinTips").text('');

            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                type: 'post',
                url: "/blog/loginBlog",
                data: {
                    usr:usr,
                    pwd:pwd,
                },
                error: (function () {
                    $(".longinTips").text('请求错误');
                }),
                dataType: 'text',
                success: (function (json) {
                    console.log(json);
                    if(json == "success"){
                        $(".longinTips").text('');
                        $(".showLogin").text('已登陆');
                        var str = "Welcome to the "+usr+" Blog Creation Space";
                        $(".wel1").text(str);
                        $(".longin1").remove();
                    }else {
                        $(".longinTips").text('账号或密码错误');
                        $(".showLogin").text('未登陆');
                    }

                })
            })
        }
     }

     function registerAction() {

         var  mail = $("#mail").val();
         var  mailCode = $("#mailCode").val();
         var  usrName = $("#usrName").val();
         var  pwd = $("#pwd").val();
         var  repwd = $("#repwd").val();

         if(mail.length <= 0
             || mailCode.length <= 0
             || usrName.length <= 0
             || pwd.length <= 0
             || repwd.length <= 0){

             $(".longinTips").text('请把所有注册信息添写完！');
             alert('请把所有注册信息添写完！');
         }else {

             if (pwd != repwd){
                 $(".longinTips").text('俩次输入密码不一！');
                 alert('俩次输入密码不一！');
             } else {

                 $.ajax({
                     contentType: "application/x-www-form-urlencoded; charset=utf-8",
                     type: 'post',
                     url: "/blog/registerBlog",
                     data: {
                         mail:mail,
                         mailCode:mailCode,
                         usrName:usrName,
                         pwd:pwd
                     },
                     error: (function () {
                         $(".longinTips").text('请求错误');
                     }),
                     dataType: 'text',
                     success: (function (json) {
                         console.log(json);
                         if(json == "success"){
                             $(".longinTips").text('');
                             $(".showLogin").text('已登陆');
                             var str = "Welcome to the "+usrName+" Blog Creation Space";
                             $(".wel1").text(str);
                             $(".longin1").remove();
                         }else {
                             $(".longinTips").text('邮件验证码错误！');
                         }

                     })

                 })
             }

         }

     }


    /**
     * 查下用户登陆信息
     * @param objname
     * @returns {string}
     */
    function getcookie(objname){//获取指定名称的cookie的值
        var arrstr = document.cookie.split("; ");
        for(var i = 0;i < arrstr.length;i ++){
            var temp = arrstr[i].split("=");
            if(temp[0] == objname) return unescape(temp[1]);
        }
    }

    //检查用户是否已经登陆
    function cookieLogin() {

        $(".wel3").after(creatLoginView());
        blinMailBtn();

        var usr = getcookie("blogCookNameUsr");
        var pwd = getcookie("blogCookNamePwd");

        $("#usrName").val(usr);
        $("#pwd").val(pwd);


        if (typeof(usr) == 'undefined') return;

        if(usr.length>0){
            loginAction();
        }
    }
    cookieLogin();



    var host =  document.domain;
    if (host == "localhost") {
        host = "http://localhost:8080";
    }else {
        host = "https://stanserver.cn";
    }
    

    $(".world").click(function () {
        window.open(host+"/blog/list","_blank");
    })
    $(".blogs").click(function () {

        if($(".showLogin").text() == '已登陆'){
            window.open(host+"/blog/mylist","_blank");
        }else {
            alert("未登陆")
        }
    })

    $(".quit").click(function () {
        $(".showLogin").text("未登陆");
        document.cookie = "blogCookNameUsr =";
        document.cookie = "blogCookNamePwd =";
    })


</script>
</body>
</html>
