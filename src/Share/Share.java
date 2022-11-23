package Share;

public class Share {

    final public static boolean releaseFile = true;
    static public String fileHome(){
        if (releaseFile){
            return  "/root/webRTC/public/blog/";
        }else {
            return "/Users/stan/Desktop/blog/";
        }

    }


}
