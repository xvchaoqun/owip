package sys;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class ShortMsgPropertyUtils {

    private static Logger logger = LoggerFactory.getLogger(ShortMsgPropertyUtils.class);
    private static Properties props =null;
    private static long lastModified = 0;
    public final static String msg(String key){

        if(StringUtils.isBlank(key)) return null;
        Resource resource = new ClassPathResource("/short_msg.properties");
        try {
            long _lastModified = resource.lastModified();
            if(lastModified!=0 && lastModified != _lastModified){
                props = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(props == null){
            try {
                logger.debug("==============reload error.properties==================");
                lastModified = resource.lastModified();
                props = PropertiesLoaderUtils.loadProperties(resource);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(null != props){
            String msg = props.getProperty("msg." + key);
            if(msg != null) return msg;
            if(key != null) return key;
        }
        return null;
    }
}
