package Utils;


import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

public class MailServer implements Runnable {
    private HttpServletResponse response;
    private String email;
    private String code; // 激活码

    public MailServer(String email, String code,HttpServletResponse response) {
        this.email = email;
        this.code = code;
        this.response = response;
    }

    public void run() {
        // 配置
        Properties prop=new Properties();
        // 设置邮件服务器主机名，这里是163
        prop.put("mail.host","smtp.163.com" );
        // 发送邮件协议名称
        prop.put("mail.transport.protocol", "smtp");
        // 是否认证
        prop.put("mail.smtp.auth", true);

        try {
            // SSL加密
            MailSSLSocketFactory sf = null;
            sf = new MailSSLSocketFactory();
            // 设置信任所有的主机
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            // 创建会话对象
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                // 认证信息，需要提供"用户账号","授权码"
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("stanserver@163.com","SFUGJJJHZGDTMSGT");
                }
            });
            // 是否打印出debug信息
            session.setDebug(false);

            // 创建邮件
            Message message = new MimeMessage(session);
            // 邮件发送者
            message.setFrom(new InternetAddress("stanserver@163.com"));
            // 邮件接受者
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 邮件主题
            message.setSubject("邮箱验证");
            String content = "<html><head></head><body><h1>你的邮件验证码为："+code+"</h1><h3></h3></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // Transport.send(message);
            // 邮件发送
            Transport transport = session.getTransport();
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            response.getOutputStream().write("success".getBytes("utf8"));
        } catch (Exception e) {
            try {
                response.getOutputStream().write("Failed".getBytes("utf8"));
            }catch (Exception e1){
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
    }
}
