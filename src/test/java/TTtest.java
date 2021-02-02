import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.web.util.HtmlUtils;
import sys.ip.IPSeeker;
import sys.utils.ImageUtils;
import sys.utils.PatternUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

/**
 * Created by fafa on 2016/8/24.
 */
public class TTtest {

    @Test
    public void xxxxx() {

        boolean image = ImageUtils.isImage(new File("test"));
        System.out.println("image = " + image);

        /*
        String name = "2010-06-01";
        Date date = DateUtils.parseStringToDate(name);

        System.out.println("date = " + DateUtils.formatDate(DateUtils.parseStringToDate(name),
                    DateUtils.YYYY_MM_DD));*/
    }
    @Test
    public void xxx() {

        String name = "[   sample_xsss]是的发生的.xls";
        String withdraw = PatternUtils.withdraw(".*\\[\\s*(\\S*)\\s*\\].*", name);

        System.out.println("withdraw = " + withdraw);
    }
    @Test
    public void xxx2() {

        String name = "[   sample_xsss]是的发生的.xls";
        String withdraw = PatternUtils.withdraw(".*\\[.*](.*)\\..*", name);
        System.out.println("withdraw = " + withdraw);
    }

    @Test
    public void ttt1() {

        int[] ids = new int[]{1, 23, 3};
        String join = StringUtils.join(ids, ",");
        System.out.println("join = " + join);

        String[] s = new String[]{"Yuan", "Mxy"};//传入String类型的数组，使用"-"号拼接
        String join2 = StringUtils.join(s, "-");
        System.out.println(join2);
    }

    @Test
    public void xx() {

        String str = MessageFormat.format("mysql -u{0} -p\"{1}\" -e\"use {2};{3}\"", 1, 2, 3, 4);
        System.out.println("str = " + str);
    }

    @Test
    public void ttt() {

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
    public void ip() {

        IPSeeker instance = IPSeeker.getInstance();
        String country = instance.getCountry("59.64.48.17");
        String area = instance.getArea("59.64.48.17");
        System.out.println(country + "|" + area);
    }

    @Test
    public void doConvert() {

        String keyword = "userIdName";
        if (keyword.matches("[\\S]*[A-Z][\\S]*")) {
            for (int i = 0; i < keyword.length(); i++) {
                char key = keyword.charAt(i);
                if (Character.isUpperCase(key)) {
                    String keyStr = key + "";
                    keyword = keyword.replace(keyStr, "_" + keyStr.toLowerCase());
                }
            }
        }
        System.out.println("keyword = " + keyword);
    }
}
