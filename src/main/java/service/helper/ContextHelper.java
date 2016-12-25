package service.helper;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fafa on 2016/6/14.
 */
public class ContextHelper {

    public static String getRealIp(){

        return IpUtils.getRealIp(getRequest());
    }

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
