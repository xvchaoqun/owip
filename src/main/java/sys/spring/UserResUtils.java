package sys.spring;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shiro.ShiroHelper;
import sys.HttpResponseMethod;
import sys.security.Base64Utils;
import sys.security.DES3Utils;
import sys.utils.MD5Util;

import java.lang.reflect.Method;

// 用户资源签名保护
public class UserResUtils {

    private static Logger logger = LoggerFactory.getLogger(UserResUtils.class);

    private static String DES3_KEY = "3desmust24chars*&^%12@12123";

    /**
     * 资源签名校验
     *
     * @param signRes
     * @return
     */
    public static UserRes verify(String signRes) {

        if (StringUtils.isBlank(signRes)) {

            return UserRes.EMPTY;
        }

        UserRes userRes = UserResUtils.decode(signRes);
        // 验签
        verifyBean(userRes);

        // 成功后返回
        return userRes;
    }

    public static void verifyBean(UserRes userRes) {

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if (userRes == null || currentUserId == null
                || userRes.getSignUserId() == null
                || userRes.getSignUserId().intValue() != currentUserId) {

            // 非同一个签名用户，不允许访问
            throw new IllegalUserResException();
        }

        String permissions = userRes.getPermissions();

        if (StringUtils.isNotBlank(permissions)
                && !ShiroHelper.isPermittedAny(permissions.split(","))) {

            throw new IllegalUserResException(); // 无资源使用权限
        }

        if (userRes.getAuthUserId() != null
                && userRes.getAuthUserId().intValue() != currentUserId) {

            throw new IllegalUserResException(); // 非资源使用人
        }

        // 资源权限方法判断
        if (StringUtils.isNotBlank(userRes.getMethod())) {

            try {
                Method method = UserResMethod.class.getDeclaredMethod(StringUtils.trim(userRes.getMethod()), String.class);

                if (BooleanUtils.isNotTrue((Boolean) method.invoke(null, StringUtils.trim(userRes.getParams())))) {

                    throw new IllegalUserResException(); // 资源权限方法校验未通过
                }

            } catch (Exception e) {

                logger.error(HttpResponseMethod.accessLog("check failed."), e);
                throw new IllegalUserResException();
            }
        }
    }

    // 无权限
    public static String sign(String res) {

        return sign(res, null, null, null, null);
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

        if (StringUtils.isBlank(res)) {

            return null;
        }

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if (currentUserId == null) {

            logger.error(HttpResponseMethod.accessLog("no login error：" + res));
            return null;
        }

        if ((permissions != null && permissions.indexOf("\\|") > 0)
                || (method != null && method.indexOf("\\|") > 0)
                || (params != null && params.indexOf("\\|") > 0)) {

            logger.error(HttpResponseMethod.accessLog("参数不能包含|字符：" + permissions + " " + method + " " + params));

            return null;
        }

        String base64Res = null;
        try {
            base64Res = Base64Utils.encode(StringUtils.trim(res));
        } catch (Exception e) {

            logger.error(HttpResponseMethod.accessLog("base64 error：" + res));
            return null;
        }

        return encode(currentUserId,StringUtils.trimToEmpty(base64Res) + "|" + currentUserId
                + "|" + (userId == null ? "" : userId)
                + "|" + StringUtils.trimToEmpty(permissions)
                + "|" + StringUtils.trimToEmpty(method)
                + "|" + StringUtils.trimToEmpty(params));
    }

    public static String encode(int currentUserId, String res) {

        try {

            String key = MD5Util.md5Hex(currentUserId + DES3_KEY, "utf-8");
            logger.debug("key = " + key);

            String encode = DES3Utils.encodeURLSafe(res, key);
            logger.debug("UserRes encode=" + encode);
            return encode;
        } catch (Exception e) {

            logger.error(HttpResponseMethod.accessLog("encode failed."), e);
        }

        return null;
    }

    public static UserRes decode(String signRes) {

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if (currentUserId == null) {

            throw new IllegalUserResException("未登录");
        }

        String plainText = null;
        try {

            String key = MD5Util.md5Hex(currentUserId + DES3_KEY, "utf-8");
            logger.debug("key = " + key);

            plainText = DES3Utils.decode(signRes, key);
            logger.debug("plainText = " + plainText);

            String[] strs = plainText.split("\\|");

            int len = strs.length;
            if (len == 0) {
                throw new IllegalUserResException("签名校验失败");
            }

            UserRes bean = new UserRes();

            if (len > 0) {
                String base64Res = StringUtils.trimToNull(strs[0]);
                String res = Base64Utils.decodeStr(base64Res);
                bean.setRes(res);

                logger.debug("UserRes decode=" + res);
            }

            if (len > 1) {
                bean.setSignUserId(StringUtils.isBlank(strs[1]) ? null : Integer.valueOf(strs[1]));
            }

            if (len > 2) {
                bean.setAuthUserId(StringUtils.isBlank(strs[2]) ? null : Integer.valueOf(strs[2]));
            }
            if (len > 3) {
                bean.setPermissions(StringUtils.trimToNull(strs[3]));
            }
            if (len > 4) {
                bean.setMethod(StringUtils.trimToNull(strs[4]));
            }
            if (len > 5) {
                bean.setParams(StringUtils.trimToNull(strs[5]));
            }

            return bean;

        } catch (Exception e) {

            logger.error(HttpResponseMethod.accessLog("decode failed:" + signRes), e);
            throw new IllegalUserResException("签名校验失败");
        }
    }
}
