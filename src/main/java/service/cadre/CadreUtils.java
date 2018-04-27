package service.cadre;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by lm on 2018/4/27.
 */
public class CadreUtils {

    // 处理专业
    public static String major(String major){

        if(StringUtils.isNotBlank(major)) {
            if(!major.endsWith("专业")){
                major = StringUtils.trim(major) + "专业";
            }
            return major;
        }

        return null;
    }
}
