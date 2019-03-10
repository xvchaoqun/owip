package service.auth;

import jixiantech.api.sso.SSOLogin;
import service.LoginService;

public class SSOLoginService implements LoginService {

    @Override
    public boolean tryLogin(String username, String passwd) {

        return SSOLogin.tryLogin(username, passwd);
    }
}
