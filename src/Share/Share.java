package Share;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class Share {

    static public String fileHome(HttpServletRequest request){

        String host = (String)request.getServerName(); //得到域名 localhost

        if (Objects.equals(host,"localhost")){
            return "/Users/stan/Desktop/blogFile/";
        }else {
            return  "/root/webRTC/public/blog/";
        }
    }




}
