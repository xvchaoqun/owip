package shiro;

/**
 * Created by liaom on 2015/8/14.
 */
public class SaltPassword {

    private String salt;
    private String password;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
