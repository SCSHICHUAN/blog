package server;

import Utils.MailServer;
import main.*;

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
            case "/addBlog.sc":
                CreateBlog.addBlog(request,response);
            break;
            case "/list":
                BlogList.list(request,response);
                break;
            case "/list.do.sc":
                BlogList.blogList(request,response);
                break;
            case "/edit.do.sc":
                EditBlog.edit(request,response);
                break;
            case "/deleBlog.sc":
                DeleteBlog.deleBlog(request,response);
                break;
            case "/loginBlog":
                LoginBlog loginBlog = new LoginBlog();
                loginBlog.login(request,response);
                break;
            case "/sendMailCodeBlog":
                 RegisterBlog registerBlog = new RegisterBlog();
                 registerBlog.sendMaliCode(request,response);
                break;
            case "/registerBlog":
                RegisterBlog registerBlog2 = new RegisterBlog();
                registerBlog2.registerBlog(request,response);
                break;

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }









}
