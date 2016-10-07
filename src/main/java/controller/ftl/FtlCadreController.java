package controller.ftl;

import controller.BaseController;
import domain.cadre.*;
import domain.member.Member;
import domain.sys.SysUser;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.DocumentHandler;
import sys.utils.FileUtils;
import sys.utils.ImageUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liaomin on 16/9/29.
 */
@Controller
public class FtlCadreController extends BaseController{

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    // 干部任免审批表下载
    @RequestMapping("/cadre/adform")
    public void adform(int cadreId, HttpServletResponse response) throws IOException, TemplateException {

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        SysUser sysUser = cadre.getUser();

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("name", sysUser.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(sysUser.getGender()));
        dataMap.put("birth", DateUtils.formatDate(sysUser.getBirth(), "yyyy年MM月"));
        dataMap.put("age", DateUtils.intervalYearsUntilNow(sysUser.getBirth()));

        File avatar =  new File(springProps.avatarFolder + File.separator
                + sysUser.getId()%100 + File.separator + sysUser.getCode() +".jpg");
        if(!avatar.exists()) avatar = new File(springProps.avatarFolder + springProps.defaultAvatar);
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        dataMap.put("avatar", base64);

        dataMap.put("nation", cadre.getNation());
        dataMap.put("nativePlace", cadre.getNativePlace());
        dataMap.put("birthCountry", "");

        String growTime = "";
        if(cadre.getIsDp()){
            growTime = DateUtils.formatDate(cadre.getDpAddTime(), "yyyy.MM");
        }else{
            Member member = memberService.get(sysUser.getId());
            if(member!=null){
                growTime = DateUtils.formatDate(member.getGrowTime(), "yyyy.MM");
            }
        }
        dataMap.put("growTime", StringUtils.trimToEmpty(growTime));

        dataMap.put("workTime", "");
        dataMap.put("health", "");
        dataMap.put("proPost", "");
        dataMap.put("professinal", "");

        // 最高学历
        CadreEdu highEdu = cadreEduService.getHighEdu(cadreId);
        dataMap.put("degree", highEdu == null ? "" : metaTypeService.getName(highEdu.getEduId()));
        dataMap.put("schoolDepMajor", highEdu == null ? "" :
                StringUtils.trimToEmpty(highEdu.getSchool())+
                        StringUtils.trimToEmpty(highEdu.getDep())
                        +StringUtils.trimToEmpty(highEdu.getMajor()));

        dataMap.put("inDegree", "");
        dataMap.put("inSchoolDepMajor", "");

        CadrePost mainCadrePost = cadrePostService.getCadreMainCadrePost(cadreId);
        // 主职,现任职务
        dataMap.put("post", mainCadrePost==null?"":mainCadrePost.getPost());

        dataMap.put("inPost", "");
        dataMap.put("prePost", "");
        dataMap.put("reward", "");
        dataMap.put("ces", "");
        dataMap.put("reason", "");
        dataMap.put("learnDesc", "");
        dataMap.put("workDesc", "");

/*        dataMap.put("opinion1", "");
        dataMap.put("y1", "");
        dataMap.put("m1", "");
        dataMap.put("d1", "");*/


        CadreInfo edu = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_EDU);
        if(edu!=null)
            dataMap.put("learnDesc", genSegment("学习经历", edu.getContent(), "cadreInfo.ftl"));

        CadreInfo work = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_WORK);
        if(work!=null)
            dataMap.put("workDesc", genSegment("工作经历", work.getContent(), "cadreInfo.ftl"));

        CadreInfo train = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_TRAIN);
        if(train!=null)
            dataMap.put("trainDesc", genSegment(null, train.getContent(), "cadreInfo.ftl"));

        String famliy = "";
        CadreFamliyExample example = new CadreFamliyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        List<CadreFamliy> cadreFamliys = cadreFamliyMapper.selectByExample(example);
        int size = cadreFamliys.size();
        for (int i=0; i<5; i++) {
            if(size<=i)
                famliy += getFamliySeg(null, "famliy.ftl");
            else
                famliy += getFamliySeg(cadreFamliys.get(i), "famliy.ftl");
        }
        dataMap.put("famliy", famliy);

        //输出文件
        String filename = "干部任免审批表";
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((filename + ".doc").getBytes(), "iso-8859-1"));
        response.setContentType("application/msword;charset=UTF-8");

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        Template tp= cf.getTemplate("cadre.ftl");
        tp.process(dataMap, response.getWriter());

        //createDoc(dataMap, "cadre.xml", "/Volumes/work/test/outFile.doc");
    }


    public static String getStringNoBlank(String str) {
        if(str!=null && !"".equals(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            String strNoBlank = m.replaceAll("");
            return strNoBlank;
        }else {
            return str;
        }
    }

    private String getFamliySeg(CadreFamliy cf, String ftlPath) throws IOException, TemplateException {

        Map<String,Object> dataMap = new HashMap<>();

        String ftitle = "";
        if(cf!=null){
            ftitle =SystemConstants.CADRE_FAMLIY_TITLE_MAP.get(cf.getTitle());
        }
        dataMap.put("ftitle", StringUtils.trimToEmpty(ftitle));
        dataMap.put("fname", cf==null?"":StringUtils.trimToEmpty(cf.getRealname()));

        String fage = "";
        if(cf!=null && cf.getBirthday()!=null){
            fage = DateUtils.intervalYearsUntilNow(cf.getBirthday()) + "岁";
        }
        dataMap.put("fage", fage);

        String fps = "";
        if(cf!=null && cf.getPoliticalStatus()!=null){
            fps = metaTypeService.getName(cf.getPoliticalStatus());
        }
        dataMap.put("fps", StringUtils.trimToEmpty(fps));

        dataMap.put("fpost", cf==null?"":StringUtils.trimToEmpty(cf.getUnit()));

        return processFtl(ftlPath, dataMap);
    }

    public String genSegment(String title, String conent, String ftlPath) throws IOException, TemplateException {

        /*String conent = "<p>\n" +
                "\t1987.09-1991.07&nbsp;内蒙古大学生物学系植物生态学&nbsp;\n" +
                "</p>\n" +
                "<p>\n" +
                "\t1994.09-1997.07&nbsp;北京师范大学资源与环境学院自然地理学&nbsp;管理学博士\n" +
                "</p>";*/
        //System.out.println(getStringNoBlank(info));
        List rows = new ArrayList();

        Pattern p = Pattern.compile("<p(.*)>([^/]*)</p>");
        Matcher matcher = p.matcher(conent);
        while(matcher.find()){
            int type = 0;
            if(StringUtils.contains(matcher.group(1), "2em"))
                type=1;
            if(StringUtils.contains(matcher.group(1), "5em"))
                type=2;
            String group = matcher.group(2);
            List cols = new ArrayList();
            cols.add(type);

            for (String col : group.trim().split("&nbsp;")) {
                cols.add(col.trim());
            }
            rows.add(cols);
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("dataList", rows);

        return processFtl(ftlPath, dataMap);
    }


    public String processFtl(String path, Map<String,Object> dataMap) throws IOException, TemplateException {

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        Template tp= cf.getTemplate(path);
        StringWriter writer = new StringWriter();
        tp.process(dataMap, writer);
        return writer.toString();
    }

    public void createDoc(Map<String,Object> dataMap, String tpl, String fileName) throws UnsupportedEncodingException {
        Template t=null;

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        try {
            //test.ftl为要装载的模板
            t = cf.getTemplate(tpl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出文档路径及名称
        File outFile = new File(fileName);
        if(!outFile.getParentFile().exists()){

            outFile.getParentFile().mkdirs();
        }
        Writer out = null;
        FileOutputStream fos=null;
        try {
            fos = new FileOutputStream(outFile);
            OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");
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
