package service.cadre;

import bean.CadreAdform;
import domain.cadre.*;
import domain.member.Member;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.common.FreemarkerService;
import shiro.ShiroHelper;
import service.party.MemberService;
import service.base.MetaTypeService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
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
        SysUserView uv = cadre.getUser();

        CadreAdform bean = new CadreAdform();
        bean.setCadreId(cadreId);
        bean.setRealname(uv.getRealname());
        bean.setGender(uv.getGender());
        bean.setBirth(uv.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(uv.getBirth()));

        File avatar =  new File(springProps.avatarFolder + uv.getAvatar());
        if(!avatar.exists()) avatar = new File(springProps.avatarFolder + FILE_SEPARATOR + springProps.defaultAvatar);
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        bean.setAvatar(base64);
        bean.setNation(cadre.getNation());
        bean.setNativePlace(cadre.getNativePlace());

        bean.setHomeplace(uv.getHomeplace());
        //bean.setWorkTime();
        bean.setHealth(uv.getHealth());
        bean.setProPost(cadre.getProPost());
        bean.setSpecialty(uv.getSpecialty());

        if(cadre.getIsDp()){
            bean.setGrowTime(cadre.getDpAddTime());
        }else{
            Member member = memberService.get(uv.getId());
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

        //年度考核结果
        Integer currentYear = DateUtils.getCurrentYear();
        bean.setCes((currentYear-3) + "、"+ (currentYear-2) + "、"+ (currentYear-1) + "年年度考核均为合格。");

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

        CadreAdform bean = getCadreAdform(cadreId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("name", bean.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), "yyyy年MM月"));
        dataMap.put("age", bean.getAge());
        dataMap.put("avatar", bean.getAvatar());
        dataMap.put("nation", bean.getNation());
        dataMap.put("nativePlace", bean.getNativePlace());
        dataMap.put("homeplace", bean.getHomeplace());
        dataMap.put("growTime", DateUtils.formatDate(bean.getGrowTime(), "yyyy.MM"));
        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), "yyyy.MM"));

        dataMap.put("health", bean.getHealth());
        dataMap.put("proPost", bean.getProPost());
        dataMap.put("specialty", bean.getSpecialty());

        dataMap.put("degree", bean.getDegree());
        dataMap.put("schoolDepMajor", bean.getSchoolDepMajor());
        dataMap.put("inDegree", bean.getInDegree());
        dataMap.put("inSchoolDepMajor", bean.getInSchoolDepMajor());

        dataMap.put("post", bean.getPost());
        dataMap.put("inPost", bean.getInPost());
        dataMap.put("prePost", bean.getPrePost());
        dataMap.put("reward", bean.getReward());
        dataMap.put("ces", bean.getCes());
        dataMap.put("reason", bean.getReason());

        dataMap.put("learnDesc", "");
        dataMap.put("workDesc", "");

        if(bean.getLearnDesc()!=null)
            dataMap.put("learnDesc", genSegment("学习经历", bean.getLearnDesc(), "/common/cadreInfo.ftl"));
        if(bean.getWorkDesc()!=null)
            dataMap.put("workDesc", genSegment("工作经历", bean.getWorkDesc(), "/common/cadreInfo.ftl"));
        if(bean.getTrainDesc()!=null)
            dataMap.put("trainDesc", genSegment(null, bean.getTrainDesc(), "/common/cadreInfo.ftl"));

        String famliy = "";
        List<CadreFamliy> cadreFamliys = bean.getCadreFamliys();
        int size = cadreFamliys.size();
        for (int i=0; i<5; i++) {
            if(size<=i)
                famliy += getFamliySeg(null, "/adform/famliy.ftl");
            else
                famliy += getFamliySeg(cadreFamliys.get(i), "/adform/famliy.ftl");
        }
        dataMap.put("famliy", famliy);
        SysUserView currentUser = ShiroHelper.getCurrentUser();
        if(currentUser!=null)
            dataMap.put("admin", currentUser.getRealname());

        dataMap.put("y1", DateUtils.getCurrentYear());
        dataMap.put("m1", DateUtils.getMonth(new Date()));
        dataMap.put("d1", DateUtils.getDay(new Date()));

        freemarkerService.process("/adform/cadre.ftl", dataMap, out);
    }

    private String getFamliySeg(CadreFamliy cf, String ftlPath) throws IOException, TemplateException {

        Map<String,Object> dataMap = new HashMap<>();

        String ftitle = "";
        if(cf!=null){
            ftitle =SystemConstants.CADRE_FAMLIY_TITLE_MAP.get(cf.getTitle());
        }
        dataMap.put("ftitle", StringUtils.trimToEmpty(ftitle));
        dataMap.put("fname", cf==null?"":StringUtils.trimToEmpty(cf.getRealname()));

       /* String fage = "";
        if(cf!=null && cf.getBirthday()!=null){
            fage = DateUtils.calAge(cf.getBirthday());
        }*/
        dataMap.put("fage", cf==null?"":DateUtils.formatDate(cf.getBirthday(), "yyyy.MM"));

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
