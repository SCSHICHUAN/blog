package Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class SaveUserLogin {

    public void save(HttpServletResponse response,String usr,String pwd,String usrID){
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


        Cookie cookie1 = new Cookie("blogCookNamePwd", pwd);
        cookie1.setMaxAge(3600);


        Cookie cookie2 = new Cookie("blogCookNameID", usrID);
        cookie2.setMaxAge(3600);

        // 设置cookie
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
    }


    public String loginCookis(HttpServletRequest request, String key){

        String result = "";
        try {
            // 获取客户端cookie
            request.setCharacterEncoding("utf-8");
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (Objects.equals(c.getName(),key)){
                        result = c.getValue();
                    }
                    System.out.println(c.getName() + "--->" + c.getValue());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
