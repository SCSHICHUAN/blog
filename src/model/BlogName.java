package model;

public class BlogName {
    public String id;
    public String usrID;
    public String usrName;
    public String blogName;
    public String createDate;
    public String updateDate;

    //可以运用编辑器快速生成
    public BlogName() {
        this.id = id;
        this.usrID = usrID;
        this.usrName = usrName;
        this.blogName = blogName;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public BlogName(String id, String usrID, String usrName, String blogName, String createDate, String updateDate) {
        this.id = id;
        this.usrID = usrID;
        this.usrName = usrName;
        this.blogName = blogName;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "BlogName{" +
                "id='" + id + '\'' +
                ", usrID='" + usrID + '\'' +
                ", usrName='" + usrName + '\'' +
                ", blogName='" + blogName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
