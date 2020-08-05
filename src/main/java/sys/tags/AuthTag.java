package sys.tags;

import controller.global.NoAuthException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shiro.ShiroHelper;
import sys.security.DES3Utils;
import sys.utils.MD5Util;

import java.lang.reflect.Method;

public class AuthTag {

    private static Logger logger = LoggerFactory.getLogger(AuthTag.class);

    private static String DES3_KEY = "3desmust24chars*&^%12@12123";

    // 资源校验
    public static void check(String res, String sign) {

        if (StringUtils.isBlank(sign)) {

            throw new NoAuthException();
        }

        AuthBean authBean = AuthTag.decode(res, sign);

        check(authBean);
    }

    public static void check(AuthBean authBean) {

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if (authBean == null || currentUserId == null
                || authBean.getSignUserId() == null
                || authBean.getSignUserId().intValue() != currentUserId) {

            // 非同一个签名用户，不允许访问
            throw new NoAuthException();
        }

        String permissions = authBean.getPermissions();

        if (StringUtils.isNotBlank(permissions)
                && ShiroHelper.isPermittedAny(permissions.split(","))) {

            return ; // 拥有资源使用权限
        }

        if (authBean.getAuthUserId() != null
                && authBean.getAuthUserId().intValue() == currentUserId) {

            return ; // 资源使用人
        }

        // 资源权限方法判断
        if(StringUtils.isNotBlank(authBean.getMethod())){

            try {
                Method method = AuthMethod.class.getDeclaredMethod(StringUtils.trim(authBean.getMethod()), String.class);

                if((boolean)method.invoke(null, StringUtils.trim(authBean.getParams()))){

                    return;
                }

            } catch (Exception e) {

                logger.error("check failed.", e);
                throw new NoAuthException();
            }
        }

        throw new NoAuthException();
    }

    // 资源权限
    public static String sign(String res, String permissions) {

        return sign(res, null, permissions, null, null);
    }

    // 资源使用人
    public static String sign(String res, Integer userId) {

        return sign(res, userId, null, null, null);
    }

    // 资源使用人 & 资源权限
    public static String sign(String res, Integer userId, String permissions) {

        return sign(res, userId, permissions, null, null);
    }

    // 资源使用人 & 资源权限判断方法
    public static String sign(String res, String method, String params) {

        return sign(res, null, null, method, params);
    }

    // 资源使用人 & 资源权限 & 资源权限判断方法
    public static String sign(String res, Integer userId, String permissions, String method, String params) {

        if ((permissions != null && permissions.indexOf("\\|") > 0)
                || (method != null && method.indexOf("\\|") > 0)
                || (params != null && params.indexOf("\\|") > 0)) {

            logger.error("参数不能包含|字符：" + permissions + " " + method + " " + params);

            throw new NoAuthException("签名参数有误");
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if(currentUserId==null){

            throw new NoAuthException("未登录");
        }

        return encode(res, currentUserId
                + "|" + (userId == null ? "" : userId)
                + "|" + StringUtils.trimToEmpty(permissions)
                + "|" + StringUtils.trimToEmpty(method)
                + "|" + StringUtils.trimToEmpty(params));
    }

    public static String encode(String res, String plainText) {

        try {

            String key = MD5Util.md5Hex(res + DES3_KEY, "utf-8");
            logger.debug("key = " + key);

            return DES3Utils.encode(plainText, key);
        } catch (Exception e) {

            logger.error("encode failed.", e);

            throw new NoAuthException("签名失败");
        }
    }

    public static AuthBean decode(String res, String encryptText) {

        String plainText = null;
        try {

            String key = MD5Util.md5Hex(res + DES3_KEY, "utf-8");
            logger.debug("key = " + key);

            plainText = DES3Utils.decode(encryptText, key);
            logger.debug("plainText = " + plainText);

            String[] strs = plainText.split("\\|");

            int len = strs.length;
            if (len == 0){
                throw new NoAuthException("签名校验失败");
            }

            AuthBean bean = new AuthBean();

            if (len > 0) {
                bean.setSignUserId(StringUtils.isBlank(strs[0]) ? null : Integer.valueOf(strs[0]));
            }

            if (len > 1) {
                bean.setAuthUserId(StringUtils.isBlank(strs[1]) ? null : Integer.valueOf(strs[1]));
            }
            if (len > 2) {
                bean.setPermissions(StringUtils.trimToNull(strs[2]));
            }
            if (len > 3) {
                bean.setMethod(StringUtils.trimToNull(strs[3]));
            }
            if (len > 4) {
                bean.setParams(StringUtils.trimToNull(strs[4]));
            }

            return bean;

        } catch (Exception e) {

            logger.error("decode failed.", e);
            throw new NoAuthException("签名校验失败");
        }
    }
}
