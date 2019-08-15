package service.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(FreemarkerService.class);
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

            dataMap.put("rewardOnlyYear", CmTag.getBoolProperty("rewardOnlyYear"));

            StringWriter writer = new StringWriter();
            FreeMarkerConfigurer freeMarkerConfigurer = CmTag.getBean(FreeMarkerConfigurer.class);
            Configuration cf = freeMarkerConfigurer.getConfiguration();
            cf.setAPIBuiltinEnabled(true);
            Template tp = cf.getTemplate(ftlPath);
            tp.process(dataMap, writer);
            return StringUtils.trimToNull(writer.toString());
        } catch (IOException e) {
            logger.error("异常", e);
        } catch (TemplateException e) {
            logger.error("异常", e);
        }

        return null;
    }

    public static String freemarker(List records, String key, String ftlPath) {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(key, records);

        return freemarker(dataMap, ftlPath);
    }

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
    // needHanging: 从第二行开始进行缩进
    // 非标题行行首空4个空格
    public String genTitleEditorSegment(String content, boolean needHanging, boolean needWhiteSpace, int line) throws IOException, TemplateException {

        if(StringUtils.isBlank(content)) return null;

        String ftlPath = "/common/titleEditor.ftl";

        String result = "";

        String title = null;
        List rows = new ArrayList();

        //Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(content.replace("<br", "<p")));
        Document doc = Jsoup.parse(content.replace("<br", "<p"));
        Elements ps = doc.getElementsByTag("p");
        int size = ps.size();

        if (size == 0) {
            List cols = new ArrayList();
            cols.add(0);
            cols.add(HtmlUtils.htmlEscapeDecimal(StringUtils.trimToEmpty(doc.text())));
            rows.add(cols);

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("dataList", rows);
            dataMap.put("needHanging", needHanging);
            dataMap.put("needWhiteSpace", needWhiteSpace);
            dataMap.put("line", line);

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
                        dataMap.put("needHanging", needHanging);
                        dataMap.put("needWhiteSpace", needWhiteSpace);
                        dataMap.put("line", line);

                        result += process(ftlPath, dataMap);
                    }
                    title = pStr.substring(0, pStr.length() - 1);
                    rows.clear();
                } else {

                    String style = pElement.attr("style");
                    int type = 0;
                    if (StringUtils.contains(style, "2em"))
                        type = 1;
                    if (StringUtils.contains(style, "0em"))
                        type = 2;
                    //String text = HtmlEscapeUtils.getTextFromHTML(pElement.html());
                    List cols = new ArrayList();
                    cols.add(type);

                    for (String col : pStr.split(" ")) {
                        cols.add(col.trim());
                    }
                    rows.add(cols);
                }
            }

            if (rows.size() > 0) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("title", title);
                dataMap.put("dataList", rows);
                dataMap.put("needHanging", needHanging);
                dataMap.put("needWhiteSpace", needWhiteSpace);
                dataMap.put("line", line);

                result += process(ftlPath, dataMap);
            }
        }

        return result;
    }

    // 指定段落标题
    public String genTitleEditorSegment(String title, String content,
                                        boolean needHanging, int line, String ftlPath) throws IOException, TemplateException {

        if(StringUtils.isBlank(content)) return null;

        /*String conent = "<p>\n" +
                "\t1987.09-1991.07&nbsp;内蒙古大学生物学系植物生态学&nbsp;\n" +
                "</p>\n" +
                "<p>\n" +
                "\t1994.09-1997.07&nbsp;北京师范大学资源与环境学院自然地理学&nbsp;管理学博士\n" +
                "</p>";*/
        //System.out.println(getStringNoBlank(info));
        List rows = new ArrayList();

        //Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(content.replace("<br", "<p")));
        Document doc = Jsoup.parse(content.replace("<br", "<p"));
        Elements pElements = doc.getElementsByTag("p");
        int size = pElements.size();
        for (Element pElement : pElements) {
            String style = pElement.attr("style");
            int type = 0;
            if (StringUtils.contains(style, "2em"))
                type = 1;
            if (StringUtils.contains(style, "0em"))
                type = 2;
            //String text = HtmlEscapeUtils.getTextFromHTML(pElement.html());
            String text = StringUtils.trimToEmpty(pElement.text());

            // 需要换行的其间经历
            String _text = null;
            String[] texts = text.split("） （"); // 中间包含一个空格
            if(texts.length==2){
                text = texts[0] + "）";
                _text = "（" + texts[1];
            }
            List cols = new ArrayList();
            cols.add(type);
            boolean isEmptyRow = true; // 判断当前行是否空行
            String[] textArray = text.trim().split("\\s");

            for (int i=0; i< textArray.length; i++) {

                String col = textArray[i].trim();
                if(StringUtils.isNotBlank(col)){
                    isEmptyRow = false;
                }
                if(i==0 && (col.endsWith("—")||col.endsWith("—至今"))){
                    /**
                     *  简历中结束时间为空，留7个空格（如果是新起一行的其间经历则不空），含"至今"则空3格
                     *  （总共空出9个空格或2个空格，其中2个空格在ftl文件中预留）
                      */
                    col = col + (col.endsWith("—")?(col.startsWith("（")?"":"       "):"   ");
                    cols.add(HtmlUtils.htmlEscapeDecimal(col));
                }else{
                    cols.add(HtmlUtils.htmlEscapeDecimal(col));
                }
            }
            if(!isEmptyRow) {
                rows.add(cols);
            }

            if(_text!=null){

                List _cols = new ArrayList();
                _cols.add(0);
                _cols.add("               "); // 15个空格
                for (String col : _text.trim().split(" ")) {
                    _cols.add(HtmlUtils.htmlEscapeDecimal(col.trim()));
                }
                rows.add(_cols);
            }
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
        dataMap.put("needHanging", needHanging);
        dataMap.put("line", line);

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
