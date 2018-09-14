import org.junit.Test;
import org.springframework.web.util.HtmlUtils;
import sys.constants.RoleConstants;
import sys.ip.IPSeeker;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

/**
 * Created by fafa on 2016/8/24.
 */
public class TTtest {

    @Test
    public void t(){
        System.out.println(RoleConstants.ROLE_MAP);
    }

    @Test
    public void  xx(){

        String str = MessageFormat.format("mysql -u{0} -p\"{1}\" -e\"use {2};{3}\"",1,2,3,4);
        System.out.println("str = " + str);
    }
    @Test
    public void ttt(){

        String str = "1999.09-2003.06   辽宁大学广播影视学院电子科学与技术专业 工学学士学位";
        System.out.println(str);
        str = str.replaceFirst("[ |\\s]+", "  ");
        System.out.println(str);
        str = str.replaceFirst("-", "--");
        System.out.println(str);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //System.out.println(DateUtils.formatDateTimeMillis(1500889489690L, DateUtils.YYYY_MM_DD_HH_MM_SS));

        /*System.out.println(("sc/scss").replaceAll("\\/", "\\."));

        System.out.println(DateUtils.parseDate("2017-06", DateUtils.YYYY_MM_DD));

        String filename = URLEncoder.encode("[20 17]", "UTF-8");
        filename = filename.replaceAll("\\+", "%20");
        System.out.println(filename);*/


        String str = "贯彻落实党的十<九>大精神，加快推进学校“双一流”建设";
        str = HtmlUtils.htmlEscape(str);
        System.out.println(str);
        System.out.println(HtmlUtils.htmlUnescape(str));
        str = HtmlUtils.htmlUnescape(str);
        System.out.println(HtmlUtils.htmlUnescape(str));
        str = HtmlUtils.htmlUnescape(str);
        System.out.println(HtmlUtils.htmlUnescape(str));

    }

    @Test
    public void ip(){

        IPSeeker instance = IPSeeker.getInstance();
        String country = instance.getCountry("59.64.48.17");
        String area = instance.getArea("59.64.48.17");
        System.out.println(country + "|" + area);
    }
}
