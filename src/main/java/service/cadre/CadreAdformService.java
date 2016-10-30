package service.cadre;

import bean.CadreAdform;
import domain.cadre.*;
import domain.member.Member;
import domain.sys.SysUser;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.common.FreemarkerService;
import service.helper.ShiroSecurityHelper;
import service.party.MemberService;
import service.sys.MetaTypeService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fafa on 2016/10/28.
 */
@Service
public class CadreAdformService extends BaseMapper{

    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected CadreEduService cadreEduService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected CadreInfoService cadreInfoService;
    @Autowired
    protected CadrePostService cadrePostService;

    // 获取任免审批表属性值
    public CadreAdform getCadreAdform(int cadreId){

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        SysUser sysUser = cadre.getUser();

        CadreAdform bean = new CadreAdform();
        bean.setCadreId(cadreId);
        bean.setRealname(sysUser.getRealname());
        bean.setGender(sysUser.getGender());
        bean.setBirth(sysUser.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(sysUser.getBirth()));

        File avatar =  new File(springProps.avatarFolder + File.separator
                + sysUser.getId()%100 + File.separator + sysUser.getCode() +".jpg");
        if(!avatar.exists()) avatar = new File(springProps.avatarFolder + springProps.defaultAvatar);
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        bean.setAvatar(base64);
        bean.setNation(cadre.getNation());
        bean.setNativePlace(cadre.getNativePlace());

        if(cadre.getIsDp()){
            bean.setGrowTime(cadre.getDpAddTime());
        }else{
            Member member = memberService.get(sysUser.getId());
            bean.setGrowTime(member == null ? null :member.getGrowTime());
        }

        // 最高学历
        CadreEdu highEdu = cadreEduService.getHighEdu(cadreId);
        bean.setDegree(highEdu == null ? null : metaTypeService.getName(highEdu.getEduId()));
        bean.setSchoolDepMajor(highEdu == null ? null :
                StringUtils.trimToEmpty(highEdu.getSchool())+
                        StringUtils.trimToEmpty(highEdu.getDep())
                        +StringUtils.trimToEmpty(highEdu.getMajor()));
        // 主职,现任职务
        CadrePost mainCadrePost = cadrePostService.getCadreMainCadrePost(cadreId);
        bean.setPost(mainCadrePost==null?null:mainCadrePost.getPost());

        // 学习经历
        CadreInfo edu = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_EDU);
        bean.setLearnDesc(edu==null?null:edu.getContent());
        // 工作经历
        CadreInfo work = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_WORK);
        bean.setWorkDesc(work==null?null:work.getContent());
        // 培训情况
        CadreInfo train = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_TRAIN);
        bean.setTrainDesc(train==null?null:train.getContent());

        // 社会关系
        CadreFamliyExample example = new CadreFamliyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        List<CadreFamliy> cadreFamliys = cadreFamliyMapper.selectByExample(example);
        bean.setCadreFamliys(cadreFamliys);

        return bean;
    }

    // 输出任免审批表
    public void process(int cadreId, Writer out) throws IOException, TemplateException {

        CadreAdform adform = getCadreAdform(cadreId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("name", adform.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(adform.getGender()));
        dataMap.put("birth", DateUtils.formatDate(adform.getBirth(), "yyyy年MM月"));
        dataMap.put("age", adform.getAge());
        dataMap.put("avatar", adform.getAvatar());
        dataMap.put("nation", adform.getNation());
        dataMap.put("nativePlace", adform.getNativePlace());
        dataMap.put("birthCountry", adform.getHomeplace());
        dataMap.put("growTime", DateUtils.formatDate(adform.getGrowTime(), "yyyy.MM"));
        dataMap.put("workTime", DateUtils.formatDate(adform.getWorkTime(), "yyyy.MM"));

        dataMap.put("health", adform.getHealth());
        dataMap.put("proPost", adform.getProPost());
        dataMap.put("professinal", adform.getProfessinal());

        dataMap.put("degree", adform.getDegree());
        dataMap.put("schoolDepMajor", adform.getSchoolDepMajor());
        dataMap.put("inDegree", adform.getInDegree());
        dataMap.put("inSchoolDepMajor", adform.getInSchoolDepMajor());

        dataMap.put("post", adform.getPost());
        dataMap.put("inPost", adform.getInPost());
        dataMap.put("prePost", adform.getPrePost());
        dataMap.put("reward", adform.getReward());
        dataMap.put("ces", adform.getCes());
        dataMap.put("reason", adform.getReason());

        dataMap.put("learnDesc", "");
        dataMap.put("workDesc", "");

        if(adform.getLearnDesc()!=null)
            dataMap.put("learnDesc", genSegment("学习经历", adform.getLearnDesc(), "cadreInfo.ftl"));
        if(adform.getWorkDesc()!=null)
            dataMap.put("workDesc", genSegment("工作经历", adform.getWorkDesc(), "cadreInfo.ftl"));
        if(adform.getTrainDesc()!=null)
            dataMap.put("trainDesc", genSegment(null, adform.getTrainDesc(), "cadreInfo.ftl"));

        String famliy = "";
        List<CadreFamliy> cadreFamliys = adform.getCadreFamliys();
        int size = cadreFamliys.size();
        for (int i=0; i<5; i++) {
            if(size<=i)
                famliy += getFamliySeg(null, "famliy.ftl");
            else
                famliy += getFamliySeg(cadreFamliys.get(i), "famliy.ftl");
        }
        dataMap.put("famliy", famliy);
        SysUser currentUser = ShiroSecurityHelper.getCurrentUser();
        if(currentUser!=null)
            dataMap.put("admin", currentUser.getRealname());

        freemarkerService.process("cadre.ftl", dataMap, out);
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

        return freemarkerService.process(ftlPath, dataMap);
    }

    private String genSegment(String title, String conent, String ftlPath) throws IOException, TemplateException {

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

        return freemarkerService.process(ftlPath, dataMap);
    }
}
