package service.common;

import freemarker.core.ParseException;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

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

        Pattern p = Pattern.compile("<p(.*)>([^/]*)</p>");
        Matcher matcher = p.matcher(content);

        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            int type = 0;
            if (StringUtils.contains(matcher.group(1), "2em"))
                type = 1;
            if (StringUtils.contains(matcher.group(1), "5em"))
                type = 2;
            String group = matcher.group(2);
            List cols = new ArrayList();
            cols.add(type);

            for (String col : group.trim().split("&nbsp;")) {
                cols.add(col.trim());
            }
            rows.add(cols);
        }
        if(matchCount==0 && !StringUtils.contains(content, "<p")){
            List cols = new ArrayList();
            cols.add(0);
            cols.add(content);
            rows.add(cols);
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("dataList", rows);

        return process(ftlPath, dataMap);
    }
}
