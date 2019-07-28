package xml;

import bean.ResumeRow;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import service.cadre.CadreUtils;
import sys.utils.DateUtils;
import sys.utils.PatternUtils;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lm on 2018/4/15.
 */
public class XmlTest {

    @Test
    public void test2() {

        String content = "2016、2017、2018年均为合格";
        //String content = "2015年，合格；2016年，合格。";

        Pattern pattern = Pattern.compile("([1|2]\\d{3})[^\\d]+");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()){
            String _year = matcher.group(1);
            System.out.println("_year = " + _year);
            int start = matcher.start(1);
            System.out.println("start = " + start);
        }

        pattern = Pattern.compile("[^优秀|合格|不合格|良好]*(优秀|合格|不合格|良好)[^优秀|合格|不合格|良好]*");
        matcher = pattern.matcher(content);
        while (matcher.find()){
            String result = matcher.group(1);
            System.out.println("result = " + result);
            int start = matcher.start(1);
            System.out.println("start = " + start);
        }
    }
    @Test
    public void test() {

        String content = "(1993.09-1997.07  北京邮电大学机电系机电控制及自动化专业 同等学力攻读在职硕士研究生)";
        String desc = PatternUtils.withdraw("[（|\\(]{0,1}[0-9]{4}\\.[0-9]{2}-{1,2}[0-9]{4}\\.[0-9]{2}\\s*([^(\\)|）)]*)[）|\\)]{0,1}", content);

        System.out.println("parseResumeRow = " + desc);
    }

    public String getNodeText(Document doc, String nodePath) {
        Node node = doc.selectSingleNode(nodePath);
        return (node != null) ? node.getText() : null;
    }

    public String getChildNodeText(Node node, String childNodePath) {
        Node childNode = node.selectSingleNode(childNodePath);
        return (childNode != null) ? childNode.getText() : null;
    }

    @Test
    public void readRmJL() throws IOException, DocumentException {

        SAXReader reader = new SAXReader();
        //InputStream is = new FileInputStream("D:\\tmp\\孟祥海.lrmx");
        //InputStream is = new FileInputStream("D:\\tmp\\张宝军.lrmx");
        //InputStream is = new FileInputStream("D:\\tmp\\杜育红.lrmx");
        InputStream is = new FileInputStream("D:\\tmp\\陈丽媛.lrmx");
        Document doc = reader.read(is);

        String resume = getNodeText(doc, "//Person/JianLi");

        List<ResumeRow> resumeRows = CadreUtils.parseResume(resume);
        for (ResumeRow resumeRow : resumeRows) {
            System.out.println(resumeRow);
        }
    }

    @Test
    public void readRm() throws IOException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream("D:\\tmp\\陈丽媛.lrmx");
        Document doc = reader.read(is);

        String realname = getNodeText(doc, "//Person/XingMing");
        System.out.println("realname = " + realname);

        String nativePlace = getNodeText(doc, "//Person/JiGuan");
        String homeplace = getNodeText(doc, "//Person/ChuShengDi");
        String health = getNodeText(doc, "//Person/JianKangZhuangKuang");
        String specialty = getNodeText(doc, "//Person/ShuXiZhuanYeYouHeZhuanChang");
        String workTime = getNodeText(doc, "//Person/CanJiaGongZuoShiJian");

        Date _workTime = DateUtils.parseStringToDate(workTime);
        System.out.println("_workTime = " + _workTime);

        String title = getNodeText(doc, "//Person/XianRenZhiWu");
        String resume = getNodeText(doc, "//Person/JianLi");

        List<Node> nodeList = doc.selectNodes("//Person/JiaTingChengYuan/Item");

        for (Node node : nodeList) {
            String _title = getChildNodeText(node, "ChengWei");
            System.out.println("_title = " + _title);
            String _realname = getChildNodeText(node, "XingMing");
            System.out.println("_realname = " + _realname);
            String _birthday = getChildNodeText(node, "ChuShengRiQi");
            String _politicalStatus = getChildNodeText(node, "ZhengZhiMianMao");
            String _unit = getChildNodeText(node, "GongZuoDanWeiJiZhiWu");

            boolean withGod = StringUtils.contains(_unit, "去世");
        }

        is.close();
    }

    @Test
    public void rm() throws IOException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xml/adform/rm.xml"));
        Document doc = reader.read(is);

        String realname = "刘冬雪";
        Node node = doc.selectSingleNode("//Person//XingMing");
        node.setText(realname);

        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileOutputStream("D://tmp/" + realname + ".lrmx"), format);
        writer.write(doc);
        writer.close();
    }

    @Test
    public void dom4j() throws FileNotFoundException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xml/pmd/main_post.xml"));
        Document document = reader.read(is);

        List<Node> nodeList = document.selectNodes("//main-post-list/main-post");
        for (Node node : nodeList) {

            String mainPost = node.valueOf("@name");
            System.out.println(mainPost + ":" + node.getText());
        }
    }
}
