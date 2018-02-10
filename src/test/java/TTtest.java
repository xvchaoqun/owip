import org.junit.Test;
import sys.constants.RoleConstants;
import sys.ip.IPSeeker;
import sys.utils.DateUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by fafa on 2016/8/24.
 */
public class TTtest {

    @Test
    public void t(){
        System.out.println(RoleConstants.ROLE_MAP);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //System.out.println(DateUtils.formatDateTimeMillis(1500889489690L, DateUtils.YYYY_MM_DD_HH_MM_SS));

        System.out.println(("sc/scss").replaceAll("\\/", "\\."));

        System.out.println(DateUtils.parseDate("2017-06", DateUtils.YYYY_MM_DD));

        String filename = URLEncoder.encode("[20 17]", "UTF-8");
        filename = filename.replaceAll("\\+", "%20");
        System.out.println(filename);

    }

    @Test
    public void ip(){

        IPSeeker instance = IPSeeker.getInstance();
        String country = instance.getCountry("59.64.48.17");
        String area = instance.getArea("59.64.48.17");
        System.out.println(country + "|" + area);
    }
}
