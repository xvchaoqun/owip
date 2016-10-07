package servcie;

import domain.cadre.CadreInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.DivToSdt;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Test;
import sys.constants.SystemConstants;
import sys.utils.DocumentHandler;
import sys.utils.ImageUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaomin on 16/9/30.
 */
public class WordmlTest {

    @Test
    public void t() throws UnsupportedEncodingException {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        for (int i = 1; i < 65; i++)
            dataMap.put("test" + i, "&#x0020;&#x0020;干部任");
        List<Map<String, String>> relateList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", "");
            map.put("name", "");
            map.put("birth", "");
            map.put("polity", "");
            map.put("post", "");
            relateList.add(map);
        }
        dataMap.put("relateList", relateList);


        String base64 = ImageUtils.encodeImgageToBase64(new File("/Volumes/work/test/88029.jpeg"));
        //System.out.println(base64);
        dataMap.put("avatar", base64);


        /*String content = "<html><head><style></style></head><body>" + cadreInfo.getContent() + "</body></html>";
        InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
        POIFSFileSystem fs = new POIFSFileSystem();
        //对应于org.apache.poi.hdf.extractor.WordDocument
        DocumentEntry wordDocument = fs.createDocument(is, "WordDocument");

        OutputStream os = new FileOutputStream("/Volumes/work/test/1.doc");
        fs.writeFilesystem(os);
        os.close();
        is.close();*/


        String html = "<w:p wsp:rsidR=\"00E759EF\" wsp:rsidRPr=\"00B62C14\" wsp:rsidRDefault=\"00E759EF\" wsp:rsidP=\"00E759EF\">\n" +
                "<w:pPr>\n" +
                "<w:spacing w:line=\"400\" w:line-rule=\"exact\"/>\n" +
                "<w:ind w:left=\"2041\" w:hanging=\"2041\"/>\n" +
                "<w:rPr>\n" +
                "<w:rFonts w:hint=\"fareast\"/>\n" +
                "<w:sz w:val=\"22\"/>\n" +
                "</w:rPr>\n" +
                "</w:pPr>\n" +
                "<w:r wsp:rsidRPr=\"00B62C14\">\n" +
                "<w:rPr>\n" +
                "<w:rFonts w:hint=\"fareast\"/>\n" +
                "<w:sz w:val=\"22\"/>\n" +
                "</w:rPr>\n" +
                "<w:t>2009.01-2009.03</w:t>\n" +
                "</w:r>\n" +
                "<w:r wsp:rsidRPr=\"00B62C14\">\n" +
                "<w:rPr>\n" +
                "<w:rFonts w:hint=\"fareast\"/>\n" +
                "<wx:font wx:val=\"宋体\"/>\n" +
                "<w:sz w:val=\"22\"/>\n" +
                "</w:rPr>\n" +
                "<w:t>北京师范大学教育学部</w:t>\n" +
                "</w:r>\n" +
                "<w:r wsp:rsidRPr=\"00B62C14\">\n" +
                "<w:rPr>\n" +
                "<w:rFonts w:hint=\"fareast\"/>\n" +
                "<w:sz w:val=\"22\"/>\n" +
                "</w:rPr>\n" +
                "<w:t>  </w:t>\n" +
                "</w:r>\n" +
                "<w:r wsp:rsidRPr=\"00B62C14\">\n" +
                "<w:rPr>\n" +
                "<w:rFonts w:hint=\"fareast\"/>\n" +
                "<wx:font wx:val=\"宋体\"/>\n" +
                "<w:sz w:val=\"22\"/>\n" +
                "</w:rPr>\n" +
                "<w:t>常务副部长</w:t>\n" +
                "</w:r>\n" +
                "</w:p>";

        dataMap.put("learn", "<w:p><w:r><w:t>学习经历:</w:t></w:r></w:p>" + html + html + html + html + html + html + html + html + html + html + html);
        dataMap.put("work", "工作经历");
        DocumentHandler mdoc = new DocumentHandler();
        createDoc(dataMap, "干部任免审批表.xml", "/Volumes/work/test/outFile.doc");
    }

    public void createDoc(Map<String, Object> dataMap, String tpl, String fileName) throws UnsupportedEncodingException {
        Template t = null;

        Configuration cf = new Configuration();
        try {
            cf.setDirectoryForTemplateLoading(new File("/Volumes/work/IdeaProjects/owip/src/test/resources/tpl"));
            //test.ftl为要装载的模板
            t = cf.getTemplate(tpl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出文档路径及名称
        File outFile = new File(fileName);
        if (!outFile.getParentFile().exists()) {

            outFile.getParentFile().mkdirs();
        }
        Writer out = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outFile);
            OutputStreamWriter oWriter = new OutputStreamWriter(fos, "UTF-8");
            //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
            //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            out = new BufferedWriter(oWriter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
            out.close();
            fos.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("---------------------------");
    }
}
