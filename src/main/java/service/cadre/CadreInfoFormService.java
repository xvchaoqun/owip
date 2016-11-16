package service.cadre;

import bean.CadreInfoForm;
import domain.cadre.*;
import domain.member.Member;
import domain.sys.MetaType;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.common.FreemarkerService;
import service.party.MemberService;
import service.sys.MetaTypeService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CadreInfoFormService extends BaseMapper{

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

    // 获取干部信息采集表属性值
    public CadreInfoForm getCadreInfoForm(int cadreId){

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        SysUserView uv = cadre.getUser();

        CadreInfoForm bean = new CadreInfoForm();
        bean.setCadreId(cadreId);
        bean.setCode(uv.getCode());
        bean.setRealname(uv.getRealname());
        bean.setGender(uv.getGender());
        bean.setBirth(uv.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(uv.getBirth()));
        // 行政级别
        bean.setAdminLevel(cadre.getTypeId());
        bean.setIdCard(uv.getIdcard());

        // 联系方式
        bean.setMobile(uv.getMobile());
        bean.setPhone(uv.getPhone());
        bean.setHomePhone(uv.getHomePhone());
        bean.setEmail(uv.getEmail());

        File avatar =  new File(springProps.avatarFolder + File.separator
                + uv.getId()%100 + File.separator + uv.getCode() +".jpg");
        if(!avatar.exists()) avatar = new File(springProps.avatarFolder + springProps.defaultAvatar);
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        bean.setAvatar(base64);
        bean.setNation(cadre.getNation());
        bean.setNativePlace(cadre.getNativePlace());
        //bean.setHousehold();

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
        // 培训情况
        CadreInfo train = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_TRAIN);
        bean.setTrainDesc(train==null?null:train.getContent());

        // 教学情况
        CadreInfo teach = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_TEACH);
        bean.setTeachDesc(teach == null ? null : teach.getContent());

        // 科研情况
        CadreInfo research = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_RESEARCH);
        bean.setResearchDesc(research == null ? null : research.getContent());

        // 其他奖励情况
        CadreInfo otherReward = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_REWARD_OTHER);
        bean.setOtherRewardDesc(otherReward == null ? null : otherReward.getContent());

        {
            // 企业兼职情况
            CadreCompanyExample example = new CadreCompanyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            List<CadreCompany> cadreCompanies = cadreCompanyMapper.selectByExampleWithRowbounds(example, new RowBounds(0,3));
            bean.setCadreCompanies(cadreCompanies);
        }

        {
            // 社会关系
            CadreFamliyExample example = new CadreFamliyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            List<CadreFamliy> cadreFamliys = cadreFamliyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 6));
            bean.setCadreFamliys(cadreFamliys);
        }

        {
            // 家庭成员海外情况
            CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            List<CadreFamliyAbroad> cadreFamliyAbroads =
                    cadreFamliyAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 2));
            bean.setCadreFamliyAbroads(cadreFamliyAbroads);
        }

        return bean;
    }

    // 输出干部信息采集表
    public void process(int cadreId, Writer out) throws IOException, TemplateException {

        CadreInfoForm bean = getCadreInfoForm(cadreId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("code", bean.getCode());
        dataMap.put("name", bean.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), "yyyy.MM"));
        dataMap.put("a", bean.getAge());

        dataMap.put("avatar", bean.getAvatar());
        dataMap.put("nation", bean.getNation());
        dataMap.put("np", bean.getNativePlace());
        dataMap.put("hp", bean.getHomeplace());
        dataMap.put("growTime", DateUtils.formatDate(bean.getGrowTime(), "yyyy.MM"));
        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), "yyyy.MM"));

        dataMap.put("health", bean.getHealth());
        dataMap.put("proPost", bean.getProPost());
        dataMap.put("specialty", bean.getSpecialty());

        dataMap.put("degree", bean.getDegree());
        dataMap.put("schoolDepMajor", bean.getSchoolDepMajor());
        dataMap.put("inDegree", bean.getInDegree());
        dataMap.put("inSchoolDepMajor", bean.getInSchoolDepMajor());

        dataMap.put("masterTutor", "");
        dataMap.put("doctorTutor", "");

        dataMap.put("post", bean.getPost());

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        MetaType adminLevel = metaTypeMap.get(bean.getAdminLevel());
        dataMap.put("adminLevel", adminLevel==null?"":adminLevel.getName());
        /*
        dataMap.put("adminLevel_1", metaTypeMap.get("mt_admin_level_main").getId());
        dataMap.put("adminLevel_2", metaTypeMap.get("mt_admin_level_vice").getId());
        dataMap.put("adminLevel_3", metaTypeMap.get("mt_admin_level_none").getId());*/

        dataMap.put("idCard", bean.getIdCard());
        dataMap.put("household", "");

        dataMap.put("depWork", "");
        dataMap.put("parttime", "");

        dataMap.put("learnDesc", bean.getLearnDesc()==null?"":
                    genSegment("学习经历", bean.getLearnDesc(), "/common/cadreInfo.ftl"));
        dataMap.put("workDesc", bean.getWorkDesc()==null?"":
                genSegment("工作经历", bean.getWorkDesc(), "/common/cadreInfo.ftl"));
        dataMap.put("trainDesc", bean.getTrainDesc()==null?"":
                    genSegment(null, bean.getTrainDesc(), "/common/cadreInfo.ftl"));
        dataMap.put("teachDesc", bean.getTeachDesc()==null?"":
                genSegment(null, bean.getTeachDesc(), "/common/cadreInfo.ftl"));
        dataMap.put("researchDesc", bean.getResearchDesc()==null?"":
                genSegment(null, bean.getResearchDesc(), "/common/cadreInfo.ftl"));
        dataMap.put("otherRewardDesc", bean.getOtherRewardDesc()==null?"":
                genSegment(null, bean.getOtherRewardDesc(), "/common/cadreInfo.ftl"));

        dataMap.put("mobile", bean.getMobile());
        dataMap.put("phone", bean.getPhone());
        dataMap.put("homePhone", bean.getHomePhone());
        dataMap.put("email", bean.getEmail());

        {
            String companies = "";
            List<CadreCompany> cadreCompanies = bean.getCadreCompanies();
            int size = cadreCompanies.size();
            for (int i = 0; i < 3; i++) {
                if (size <= i)
                    companies += getCompanySeg(null, "/infoform/company.ftl");
                else
                    companies += getCompanySeg(cadreCompanies.get(i), "/infoform/company.ftl");
            }
            dataMap.put("companies", companies);
        }

        {
            String famliys = "";
            List<CadreFamliy> cadreFamliys = bean.getCadreFamliys();
            int size = cadreFamliys.size();
            for (int i = 0; i < 6; i++) {
                if (size <= i)
                    famliys += getFamliySeg(null, "/infoform/famliy.ftl");
                else
                    famliys += getFamliySeg(cadreFamliys.get(i), "/infoform/famliy.ftl");
            }
            dataMap.put("famliys", famliys);
        }

        {
            String famliyAbroads = "";
            List<CadreFamliyAbroad> cadreFamliyAbroads = bean.getCadreFamliyAbroads();
            int size = cadreFamliyAbroads.size();
            for (int i = 0; i < 2; i++) {
                if (size <= i)
                    famliyAbroads += getFamliyAbroadSeg(null, "/infoform/abroad.ftl");
                else
                    famliyAbroads += getFamliyAbroadSeg(cadreFamliyAbroads.get(i), "/infoform/abroad.ftl");
            }
            dataMap.put("famliyAbroads", famliyAbroads);
        }

         dataMap.put("fillDate", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));

        freemarkerService.process("/infoform/form.ftl", dataMap, out);
    }

    private String getCompanySeg(CadreCompany bean, String ftlPath) throws IOException, TemplateException {

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("a1", bean==null?"":bean.getUnit());
        dataMap.put("b1", bean==null?"":DateUtils.formatDate(bean.getStartTime(), "yyyy.MM"));
        dataMap.put("c1", bean==null?"":bean.getReportUnit());

        return freemarkerService.process(ftlPath, dataMap);
    }

    private String getFamliySeg(CadreFamliy bean, String ftlPath) throws IOException, TemplateException {

        Map<String,Object> dataMap = new HashMap<>();

        String ftitle = "";
        if(bean!=null){
            ftitle =SystemConstants.CADRE_FAMLIY_TITLE_MAP.get(bean.getTitle());
        }
        dataMap.put("a2", StringUtils.trimToNull(ftitle));
        dataMap.put("b2", bean==null?"":StringUtils.trimToEmpty(bean.getRealname()));

        dataMap.put("c2", bean==null?"":DateUtils.formatDate(bean.getBirthday(), "yyyy.MM"));

        String fps = "";
        if(bean!=null && bean.getPoliticalStatus()!=null){
            fps = metaTypeService.getName(bean.getPoliticalStatus());
        }
        dataMap.put("d2", StringUtils.trimToNull(fps));

        dataMap.put("e2", bean==null?"":StringUtils.trimToEmpty(bean.getUnit()));

        return freemarkerService.process(ftlPath, dataMap);
    }

    private String getFamliyAbroadSeg(CadreFamliyAbroad bean, String ftlPath) throws IOException, TemplateException {

        Map<String,Object> dataMap = new HashMap<>();

        String ftitle = "";
        if(bean!=null){
            ftitle =SystemConstants.CADRE_FAMLIY_TITLE_MAP.get(bean.getCadreFamliy().getTitle());
        }
        dataMap.put("a3", StringUtils.trimToNull(ftitle));
        dataMap.put("b3", bean==null?"":bean.getCadreFamliy().getRealname());
        dataMap.put("c3", bean==null?"":bean.getCountry());
        dataMap.put("d3", bean==null?"":DateUtils.formatDate(bean.getAbroadTime(), "yyyy.MM"));
        dataMap.put("e3", bean==null?"":bean.getCity());

        dataMap.put("type", bean==null?-1:bean.getType());// 移居类别
        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        dataMap.put("type_1", metaTypeMap.get("mt_abroad_type_citizen").getId());
        dataMap.put("type_2", metaTypeMap.get("mt_abroad_type_live").getId());
        dataMap.put("type_3", metaTypeMap.get("mt_abroad_type_stay").getId());


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
