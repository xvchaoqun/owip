package shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.sys.SysLoginLogService;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.HttpRequestDeviceUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;
    @Autowired
    private SysLoginLogService sysLoginLogService;
    private static final Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);

    private int maxRetryCount = 10;
    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("PasswordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if(retryCount.incrementAndGet() > maxRetryCount) {
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }else{
            HttpServletRequest request = ContextHelper.getRequest();
            byte logType = HttpRequestDeviceUtils.isMobileDevice(request)
                    ?SystemConstants.LOGIN_TYPE_MOBILE:SystemConstants.LOGIN_TYPE_NET;

            logger.info(sysLoginLogService.log(null, username,
                    logType, false, String.format( "登录失败，密码错误（第%s次登录）", retryCount.get())));
        }
        return matches;
    }
}
