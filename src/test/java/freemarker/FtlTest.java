package freemarker;

import domain.cadre.CadreFamliy;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liaomin on 16/10/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-freemarker.xml"})
public class FtlTest {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @Test
    public void ftl2() throws IOException, TemplateException {

        CadreFamliy cf = null;

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("ftitle", cf==null?"":cf.getTitle());
        dataMap.put("fname", cf==null?"":cf.getRealname());

        String fage = "";
        if(cf!=null && cf.getBirthday()!=null){
            fage = DateUtils.intervalYearsUntilNow(cf.getBirthday()) + "岁";
        }
        dataMap.put("fage", fage);

        String fps = "";

        dataMap.put("fps", fps);

        dataMap.put("fpost", cf == null ? "" : cf.getUnit());

        System.out.println(processFtl("famliy.ftl", dataMap));
    }

    @Test
    public void ftl() throws IOException, TemplateException {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("title", "test");
        dataMap.put("dataList", new ArrayList());


        String result = processFtl("cadreInfo.ftl", dataMap);

        System.out.println(result);
    }

    @Test
    public void resFolder() throws IOException, TemplateException {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("resFolder", "test");
        String result = processFtl("test/test.ftl", dataMap);

        System.out.println(result);
    }

    public String processFtl(String path, Map<String,Object> dataMap) throws IOException, TemplateException {

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        Template tp= cf.getTemplate(path);
        StringWriter writer = new StringWriter();
        tp.process(dataMap, writer);
        return writer.toString();
    }

    @Test
    public void stringTemplate() throws IOException, TemplateException {

        //.replace('/', '.')

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String templateContent="欢迎：${folder?replace('/', '.')}";
        stringLoader.putTemplate("myTemplate",templateContent);

        Configuration cfg = freeMarkerConfigurer.getConfiguration();
        cfg.setTemplateLoader(stringLoader);

        Template template = cfg.getTemplate("myTemplate","utf-8");
        Map root = new HashMap();
        root.put("folder", "sc/scMatter");
        StringWriter writer = new StringWriter();
        template.process(root, writer);

        System.out.println(writer.toString());
    }
    @Test
    public void nullstringTemplate2() throws IOException, TemplateException {

        //.replace('/', '.')

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        //String templateContent="欢迎：<#if resFolder?? && resFolder?trim!=''>@RequestMapping(\"/${resFolder?trim}\")</#if>";
        String templateContent="欢迎：@RequestMapping(\"/${(resFolder!'')?trim}\")";
        stringLoader.putTemplate("myTemplate",templateContent);

        Configuration cfg = freeMarkerConfigurer.getConfiguration();
        cfg.setTemplateLoader(stringLoader);

        Template template = cfg.getTemplate("myTemplate","utf-8");
        Map root = new HashMap();
        //root.put("resFolder", " 1 ");
        StringWriter writer = new StringWriter();
        template.process(root, writer);

        System.out.println(writer.toString());
    }
}
