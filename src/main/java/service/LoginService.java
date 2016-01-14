package service;

/**
 * Created by fafa on 2015/2/2.
 *
 * 第三方登陆
 * （第三方账号必须以某种方式导入到账号库里，才允许登陆）
 */
public interface LoginService {

    public boolean tryLogin(String username, String passwrod);
}
