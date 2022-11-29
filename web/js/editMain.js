

var  totalText = 0;
const E = window.wangEditor
// 切换语言
const LANG = location.href.indexOf('lang=en') > 0 ? 'en' : 'zh-CN'
E.i18nChangeLanguage(LANG)

window.editor = E.createEditor({
    selector: '#editor-text-area',
    html: '',
    config: {
        placeholder: 'Type here...',
        scroll:false,
        MENU_CONF: {
            uploadImage: {
                fieldName: 'your-fileName',
                base64LimitSize: 10 * 1024 * 1024 // 10M 以下插入 base64
            }
        },
        onChange(editor) {
            console.log(editor.getHtml());

            // // 选中文字
            // const selectionText = editor.getSelectionText();
            // document.getElementById('selected-length').innerHTML = selectionText.length;
            // 全部文字
            const text = editor.getText().replace(/\n|\r/mg, '');
            document.getElementById('total-length').innerHTML = text.length;
            totalText = text.length;
        }
    }
});



window.toolbar = E.createToolbar({
    editor,
    selector: '#editor-toolbar',
    config: {}
});


















/**
 * save commit
 */
var canEdit = 'no';
var saveEdit = false;
function sendArticleToServer() {

    var  blogName = $("#blogName").val();
    var  result = window.editor.getHtml();


    if (blogName.length <= 0)
        return $("#total-length").html("请输入博客名字...");
    if(result.length <= 0)
        return $("#total-length").html("博客内容为空...");



    $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        type: 'post',
        url: "/blog/addBlog.sc",
        data: {
            blogName:blogName,
            totalText:totalText,
            html: result,
            canEdit:canEdit
        },
        error: (function () {
            alert("你还没有登陆，或者请求失败");
        }),
        dataType: 'json',
        success: (function (json) {
            console.log(json);

            switch (json.type) {
                case "creatBlogSameName":
                    $("#total-length").html("博客名字已经存在");
                    break;
                case "creatBlogSuccess":
                    createBlogSuccess(json);
                    $("#blogName").attr('disabled','disabled');
                    canEdit = 'yes';
                case "creatBlogNOlogin":
                // $("#total-length").html("你还没有登陆");
            }

        })

    })
    
}


function createBlogSuccess(obj) {
    var url = obj.url;
    $("#bacUrl").html("<a class='result' href="+url+">"+url+"</a>");
    if (!saveEdit){
        window.open(url,"_blank");
    }
}

$(".commit").click(function () {
    saveEdit = false;
    sendArticleToServer();

})
$(".save").click(function () {
    saveEdit = true;
    sendArticleToServer();
})






/**
 * 编辑跳转过来
 **/
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
        url: "/blog/edit.do.sc",
        data: {
            blogName:name
        },
        error: (function () {
            alert("你还没有登陆，或者请求失败");
        }),
        dataType: 'json',
        success: (function (json) {
            console.log(json);

            var  str = return2Br(json.blogHtml);
            editor.dangerouslyInsertHtml(json.blogHtml);


            if(json.result == "success"){
                $("#blogName").val(tmpName).attr('disabled','disabled');
                canEdit = 'yes';
            }else if(json.result == "noself"){
                $("#blogName").val("");
                canEdit = 'no';
            }

        })
    })
}


function return2Br(str) {
    return str.replace(/\r?\n/g,"<br />");
}