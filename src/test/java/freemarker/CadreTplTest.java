package freemarker;

import bean.CadreInfoForm;
import domain.cadre.CadreInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.xmlbeans.XmlException;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.DivToSdt;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import service.cadre.CadreAdformService;
import service.cadre.CadreInfoService;
import sys.constants.CadreConstants;
import sys.utils.DocumentHandler;
import sys.utils.ImageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaomin on 16/9/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CadreTplTest {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private CadreInfoService cadreInfoService;
    @Autowired
    private CadreAdformService cadreAdformService;

    @Test
    public void exportCadre() throws IOException, TemplateException {

        int cadreId = 54;
        FileOutputStream fos = new FileOutputStream("D:/tmp/"+cadreId+".doc");
        OutputStreamWriter oWriter = new OutputStreamWriter(fos, "UTF-8");

        CadreInfoForm adform = cadreAdformService.getCadreAdform(cadreId);
        cadreAdformService.process(adform, fos);

    }

    //导出到word
    public void exportWord(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            /***
             查询数据库获得数据														**/
            //word内容
            String content = "<html>";//拼接注意加上<html>
            for (int i = 0; i < 10; i++) {
                String cx = i + "";
                String title = "TITLE" + i;
                //html拼接出word内容
                content += "<div style=\"text-align: center\"><span style=\"font-size: 24px\"><span style=\"font-family: 黑体\">"
                        + title + "<br /> <br /> </span></span></div>";
                content += "<div style=\"text-align: left\"><span >"
                        + cx + "<br /> <br /> </span></span></div>";
                //插入分页符
                content += "<span lang=EN-US style='font-size:12.0pt;line-height:150%;mso-fareast-font-family:宋体;mso-font-kerning:1.0pt;mso-ansi-language:EN-US;mso-fareast-language:ZH-CN;mso-bidi-language:AR-SA'><br clear=all style='page-break-before:always'></span>";
                content += "<p class=MsoNormal style='line-height:150%'><span lang=EN-US style='font-size:12.0pt;line-height:150%'><o:p> </o:p></span></p>";

            }
            content += "</html>";
            byte b[] = content.getBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);

            //输出文件
            String name = "导出知识";
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment;filename=" +
                            new String((name + ".doc").getBytes(),
                                    "iso-8859-1"));
            response.setContentType("application/msword");
            OutputStream ostream = response.getOutputStream();
            //输出到本地文件的话，new一个文件流
            //FileOutputStream ostream = new FileOutputStream(path+ fileName);
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws IOException, OpenXML4JException, XmlException, Docx4JException {

        int cadreId = 17;

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

        CadreInfo cadreInfo = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_EDU);

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


        String html = cadreInfo.getContent();
        html = html.replace("&nbsp;", " ");
        System.out.println(html);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        ;
        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
        XHTMLImporter.setDivHandler(new DivToSdt());

        wordMLPackage.getMainDocumentPart().getContent().addAll(
                XHTMLImporter.convert(html, null));


        String marshaltoString = XmlUtils.marshaltoString(wordMLPackage
                .getMainDocumentPart().getContents(), true, true);

        System.out.println(marshaltoString);

        /*html = html.replace("'", "&apos;");//替换单引号
        html = html.replaceAll("&", "&amp;");//替换&
        html = html.replace("\"", "&quot;"); // 替换双引号
        html = html.replace("\t", "&nbsp;&nbsp;");// 替换跳格
        html = html.replace(" ", "&nbsp;");// 替换空格
        html = html.replace("<", "&lt;");//替换左尖括号
        html = html.replaceAll(">", "&gt;");//替换右尖括号*/
        dataMap.put("learn", "学习经历:" + marshaltoString);
        dataMap.put("work", "工作经历");
        DocumentHandler mdoc = new DocumentHandler();
        createDoc(dataMap, "干部任免审批表.xml", "/Volumes/work/test/outFile.doc");
    }

    private String getContent(InputStream... ises) throws IOException {
        if (ises != null) {
            StringBuilder result = new StringBuilder();
            BufferedReader br;
            String line;
            for (InputStream is : ises) {
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }
            return result.toString();
        }
        return null;
    }

    public void createDoc(Map<String, Object> dataMap, String tpl, String fileName) throws UnsupportedEncodingException {
        Template t = null;

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        try {
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
