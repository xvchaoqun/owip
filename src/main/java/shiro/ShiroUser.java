package shiro;

/**
 * Created by fafa on 2015/8/18.
 */
public class ShiroUser {

    private Integer id;
    private String username;
    private String realname;
    private Byte type;

    public ShiroUser(Integer id, String username, String realname, Byte type) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
