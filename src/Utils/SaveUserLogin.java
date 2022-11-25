package Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class SaveUserLogin {

    public void save(HttpServletResponse response,String usr,String pwd){
        // 设置格式
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        // 创建Cookie
        Cookie cookie = new Cookie("blogCookNameUsr", usr);
        // 有效期,秒为单位
        cookie.setMaxAge(3600);
        // 设置cookie
        response.addCookie(cookie);

        Cookie cookie1 = new Cookie("blogCookNamePwd", pwd);
        cookie1.setMaxAge(3600);
        response.addCookie(cookie1);
        try {
//          response.getWriter().print("cookie创建成功");
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
