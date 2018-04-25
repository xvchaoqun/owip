package service.auth;

import com.bnu.sso.BnuSSOLogin;
import service.LoginService;

public class SSOLoginService implements LoginService {

    @Override
    public boolean tryLogin(String username, String passwd) {

        return BnuSSOLogin.tryLogin(username, passwd);
    }
}
