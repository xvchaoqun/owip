package service.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.util.HtmlUtils;
import sys.tags.CmTag;
import sys.utils.HtmlEscapeUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/10/29.
 */
@Service
public class FreemarkerService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    // 模板导出
    public void process(String path, Map<String, Object> dataMap, Writer out) throws IOException, TemplateException {

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        Template tp = cf.getTemplate(path);
        tp.process(dataMap, out);
    }

    // 模板输出为字符串
    public String process(String path, Map<String, Object> dataMap) throws IOException, TemplateException {

        StringWriter writer = new StringWriter();
        process(path, dataMap, writer);

        return writer.toString();
    }

    // 模板标签，（主要用于干部初始数据片段内容（学习经历、工作经历等），模板路径：ftl/cadre/*.ftl）
    public static String freemarker(Map<String, Object> dataMap, String ftlPath) {

        try {

            StringWriter writer = new StringWriter();
            FreeMarkerConfigurer freeMarkerConfigurer = CmTag.getBean(FreeMarkerConfigurer.class);
            Configuration cf = freeMarkerConfigurer.getConfiguration();
            Template tp = cf.getTemplate(ftlPath);
            tp.process(dataMap, writer);
            return StringUtils.trimToNull(writer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String freemarker(List records, String key, String ftlPath) {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(key, records);

        return freemarker(dataMap, ftlPath);
    }

    // 按段落读取kindEditor中的内容（段首缩进）
    /*public String genEditorSegment(String content) throws IOException, TemplateException {

        List<String> rows = new ArrayList();

        Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(content));
        Elements ps = doc.getElementsByTag("p");
        int size = ps.size();
        for (int i = 0; i < size; i++) {
            String plainText = StringUtils.trimToEmpty(ps.get(i).text());
            rows.add(HtmlUtils.htmlEscapeDecimal(plainText));
        }
        if (size == 0) {
            String plainText = StringUtils.trimToEmpty(doc.text());
            rows.add(HtmlUtils.htmlEscapeDecimal(plainText));
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("dataList", rows);

        return process("/common/editor.ftl", dataMap);
    }*/

    // 按段落读取textarea（段首缩进）
    public String genTextareaSegment(String content, String tpl) throws IOException, TemplateException {

        if (StringUtils.isBlank(content)) return null;

        List<String> rows = new ArrayList();

        String[] strings = content.split("\n");
        for (String str : strings) {
            String plainText = HtmlEscapeUtils.getTextFromHTML(str);
            plainText = HtmlUtils.htmlEscapeDecimal(plainText);
            if(StringUtils.isBlank(plainText)) continue;

            rows.add(plainText);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("dataList", rows);

        return process(tpl, dataMap);
    }

    // 一行末尾带冒号，则认为是标题行。
    public String genTitleEditorSegment(String content) throws IOException, TemplateException {

        String ftlPath = "/common/titleEditor.ftl";

        String result = "";

        String title = null;
        List rows = new ArrayList();

        Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(content));
        Elements ps = doc.getElementsByTag("p");
        int size = ps.size();

        if (size == 0) {
            List cols = new ArrayList();
            cols.add(0);
            cols.add(HtmlUtils.htmlEscapeDecimal(StringUtils.trimToEmpty(doc.text())));
            rows.add(cols);

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("dataList", rows);

            result = process(ftlPath, dataMap);
        } else {
            for (int i = 0; i < size; i++) {

                Element pElement = ps.get(i);
                String pStr = HtmlUtils.htmlEscapeDecimal(StringUtils.trimToEmpty(pElement.text()));
                if (pStr.endsWith(":") || pStr.endsWith("：")) {
                    if (rows.size() > 0) {
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put("title", title);
                        dataMap.put("dataList", rows);

                        result += process(ftlPath, dataMap);
                    }
                    title = pStr.substring(0, pStr.length() - 1);
                    rows.clear();
                } else {

                    String style = pElement.attr("style");
                    int type = 0;
                    if (StringUtils.contains(style, "2em")
                            || StringUtils.contains(style, "text-indent"))
                        type = 1;
                    if (StringUtils.contains(style, "5em"))
                        type = 2;
                    //String text = HtmlEscapeUtils.getTextFromHTML(pElement.html());
                    List cols = new ArrayList();
                    cols.add(type);

                    for (String col : pStr.split(" ")) {
                        cols.add(col.trim());
                    }
                    rows.add(cols);
                }
            }

            if (rows.size() > 0) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("title", title);
                dataMap.put("dataList", rows);

                result += process(ftlPath, dataMap);
            }
        }

        return result;
    }

    // 指定段落标题
    public String genTitleEditorSegment(String title, String content) throws IOException, TemplateException {

        /*String conent = "<p>\n" +
                "\t1987.09-1991.07&nbsp;内蒙古大学生物学系植物生态学&nbsp;\n" +
                "</p>\n" +
                "<p>\n" +
                "\t1994.09-1997.07&nbsp;北京师范大学资源与环境学院自然地理学&nbsp;管理学博士\n" +
                "</p>";*/
        //System.out.println(getStringNoBlank(info));
        List rows = new ArrayList();

        Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(content));
        Elements pElements = doc.getElementsByTag("p");
        int size = pElements.size();
        for (Element pElement : pElements) {
            String style = pElement.attr("style");
            int type = 0;
            if (StringUtils.contains(style, "2em")
                    || StringUtils.contains(style, "text-indent"))
                type = 1;
            if (StringUtils.contains(style, "5em"))
                type = 2;
            //String text = HtmlEscapeUtils.getTextFromHTML(pElement.html());
            String text = HtmlUtils.htmlEscapeDecimal(StringUtils.trimToEmpty(pElement.text()));
            List cols = new ArrayList();
            cols.add(type);

            for (String col : text.trim().split(" ")) {
                cols.add(col.trim());
            }
            rows.add(cols);

        }

        if (size == 0) {
            List cols = new ArrayList();
            cols.add(0);
            cols.add(HtmlUtils.htmlEscapeDecimal(StringUtils.trimToEmpty(doc.text())));
            rows.add(cols);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("dataList", rows);

        String ftlPath = "/common/titleEditor.ftl";
        return process(ftlPath, dataMap);
    }

    public static void main(String[] args) {

        /*String content = "<p class=\"MsoNormal\" align=\"left\" style=\"font-size:18.6667px;text-indent:28.8pt;\">\n" +
                "\t该同志政治立场<b>坚定</b>，思想觉悟较高。工作积极投入、认真负责。业务水平高，具有较强的协调能力，对外联络能力强，有创新精神。为人热情开朗，待人诚恳、乐于助人、团结同事，群众基础较好。\n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" align=\"left\" style=\"font-size:18.6667px;text-indent:28.8pt;\">\n" +
                "\t<b>不足或希望：</b>希望工作中多思考，多研究，不断完善制度建设，使共建工作的管理更加顺畅。\n" +
                "</p>";*/
        String content = "<p class=\"MsoNormal\" align=\"left\" style=\"font-size:18.6667px;text-indent:28.8pt;\">\n" +
                "\t该同志政治立场<b>坚定</b>，思想觉悟较高。工作积极投入、认真负责。业务水平高，具有较强的协调能力，对外联络能力强，有创新精神。为人热情开朗，待人诚恳、乐于助人、团结同事，群众基础较好。\n" +
                "</p>\n" +
                "\t<b>不足或希望：</b>希望工作中多思考，多研究，不断完善制度建设，使共建工作的管理更加顺畅。\n";
        Document doc = Jsoup.parse(content);
        Elements p1 = doc.getElementsByTag("p");

        System.out.println(p1.get(0).html());
        System.out.println(p1.get(1));

        Element element = p1.get(1);
        Elements b = element.getElementsByTag("b");
        System.out.println(b.get(0));
    }
}
