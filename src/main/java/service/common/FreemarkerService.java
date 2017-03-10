package service.common;

import freemarker.core.ParseException;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import sys.utils.HtmlEscapeUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fafa on 2016/10/29.
 */
@Service
public class FreemarkerService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public String process(String path, Map<String,Object> dataMap) throws IOException, TemplateException {

        StringWriter writer = new StringWriter();
        process(path, dataMap, writer);

        return writer.toString();
    }

    public void process(String path, Map<String,Object> dataMap, Writer out) throws IOException, TemplateException {

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        Template tp= cf.getTemplate(path);
        tp.process(dataMap, out);
    }

    public String genSegment(String title, String content, String ftlPath) throws IOException, TemplateException {

        /*String conent = "<p>\n" +
                "\t1987.09-1991.07&nbsp;内蒙古大学生物学系植物生态学&nbsp;\n" +
                "</p>\n" +
                "<p>\n" +
                "\t1994.09-1997.07&nbsp;北京师范大学资源与环境学院自然地理学&nbsp;管理学博士\n" +
                "</p>";*/
        //System.out.println(getStringNoBlank(info));
        List rows = new ArrayList();

        //Pattern p = Pattern.compile("<p(.*)>([^/]*)</p>");
        Pattern p = Pattern.compile("<p(.*)>(.*)</p>");
        Matcher matcher = p.matcher(content);

        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            int type = 0;
            String props = matcher.group(1);
            if (StringUtils.contains(props, "2em")
                    ||StringUtils.contains(props, "text-indent"))
                type = 1;
            if (StringUtils.contains(props, "5em"))
                type = 2;
            String text = HtmlEscapeUtils.getTextFromHTML(matcher.group(2));
            List cols = new ArrayList();
            cols.add(type);

            for (String col : text.trim().split(" ")) {
                cols.add(col.trim());
            }
            rows.add(cols);
        }
        if(matchCount==0){
            List cols = new ArrayList();
            cols.add(0);
            cols.add(HtmlEscapeUtils.getTextFromHTML(content));
            rows.add(cols);
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("dataList", rows);

        return process(ftlPath, dataMap);
    }

    public String genSegment2(String content, String ftlPath) throws IOException, TemplateException {

        List<String> rows = new ArrayList();

        Document doc = Jsoup.parse(content);
        Elements ps = doc.getElementsByTag("p");
        int size = ps.size();
        for (int i = 0; i < size; i++) {
            rows.add(StringUtils.trimToEmpty(ps.get(i).text()));
        }
        if(size==0){
            rows.add(StringUtils.trimToEmpty(doc.text()));
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("dataList", rows);

        return process(ftlPath, dataMap);
    }

    public static void main(String[] args) {

        String content = "<p class=\"MsoNormal\" align=\"left\" style=\"font-size:18.6667px;text-indent:28.8pt;\">\n" +
                "\t该同志政治立场坚定，思想觉悟较高。工作积极投入、认真负责。业务水平高，具有较强的协调能力，对外联络能力强，有创新精神。为人热情开朗，待人诚恳、乐于助人、团结同事，群众基础较好。\n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" align=\"left\" style=\"font-size:18.6667px;text-indent:28.8pt;\">\n" +
                "\t<b>不足或希望：</b>希望工作中多思考，多研究，不断完善制度建设，使共建工作的管理更加顺畅。\n" +
                "</p>";
        Document doc = Jsoup.parse(content);
        Elements p1 = doc.getElementsByTag("p");

        System.out.println(p1.get(0));
        System.out.println(p1.get(1));

        Element element = p1.get(1);
        Elements b = element.getElementsByTag("b");
        System.out.println(b.get(0));
    }
}
