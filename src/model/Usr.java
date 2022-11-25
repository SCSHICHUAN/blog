package model;

public class Usr {

    public String usrID;
    public String usr;
    public String pwd;
    public String mail;

    public Usr(String usrID, String usr, String pwd, String mail) {
        this.usrID = usrID;
        this.usr = usr;
        this.pwd = pwd;
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Usr{" +
                "usrID='" + usrID + '\'' +
                ", usr='" + usr + '\'' +
                ", pwd='" + pwd + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
