package server;

import main.BlogList;
import main.CreateBlog;
import main.EditBlog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("requst path =" + path);

        switch (path){
            case "/addBlog":
                CreateBlog.addBlog(request,response);
            break;
            case "/list":
                BlogList.list(request,response);
                break;
            case "/list.do":
                BlogList.blogList(request,response);
                break;
//            case "/edit":
//                EditBlog.edit(request,response);
//                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }









}
