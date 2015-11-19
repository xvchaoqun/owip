package shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by fafa on 2015/8/18.
 */
public class AuthToken extends UsernamePasswordToken {

    //验证码字符串
    private String captcha;
    //类型
    private String type;

    public AuthToken(String username, char[] password,
                     boolean rememberMe, String host, String captcha, String type) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        this.type = type;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
