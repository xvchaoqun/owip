package shiro;

/**
 * Created by fafa on 2015/8/18.
 */
public class ShiroUser {

    private Integer id;
    private String username;
    private String type;

    public ShiroUser(Integer id, String username, String type) {
        this.id = id;
        this.username = username;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
