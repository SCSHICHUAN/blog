package main;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

public class EditBlog {
    public static void edit(HttpServletRequest request, HttpServletResponse response){



        String blogName = (String) request.getParameter("blogName");


        String filePath = "/root/webRTC/public/blog/"+blogName+".html";
        //读取.html文件为字符串
        String htmlStr = toHtmlString(new File(filePath));
        //解析字符串为Document对象
        Document doc = Jsoup.parse(htmlStr);
        //获取body元素，获取class="fc"的table元素
        System.out.print(doc.body());


       try {
           JSONObject json = new JSONObject();
           json.put("blogHtml",doc.body());

           try {
               response.getOutputStream().write(json.toString().getBytes("utf8"));
           }catch (Exception e){
               e.printStackTrace();
           }

       }catch (Exception e){
           e.printStackTrace();
       }





    }

    /**
     *  读取本地html文件里的html代码
     * @return
     */
    public static String toHtmlString(File file) {
        // 获取HTML文件流
        StringBuffer htmlSb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "utf8"));
            while (br.ready()) {
                htmlSb.append(br.readLine()+"\n");
            }
            br.close();
            // 删除临时文件
            //file.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // HTML文件字符串
        String htmlStr = htmlSb.toString();
        // 返回经过清洁的html文本
        return htmlStr;
    }




}
