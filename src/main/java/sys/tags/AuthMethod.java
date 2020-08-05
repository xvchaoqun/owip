package sys.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 权限方法定义后，需要重启应用（jrebel热加载无效）
 *
 * 生成签名方法：AuthTag.sign(资源, 方法名, 方法参数)
 *
 * eg. AuthTag.sign(filepath, "cadre_file", "cadre_edu&12")
 */
public class AuthMethod {

    private static Logger logger = LoggerFactory.getLogger(AuthMethod.class);

    // 方法参数必须为String，返回类型必须为boolean
    public static boolean cadre_file(String params){

        logger.debug("params = " + params);

        // TODO 判断权限，如果有权限则返回true

        return false;
    }
}
