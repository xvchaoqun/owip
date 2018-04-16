package service.cadre;

import bean.CadreInfoForm;
import domain.base.MetaType;
import domain.cadre.CadreCompany;
import domain.cadre.CadreCompanyExample;
import domain.cadre.CadreEdu;
import domain.cadre.CadreFamliy;
import domain.cadre.CadreFamliyAbroad;
import domain.cadre.CadreFamliyAbroadExample;
import domain.cadre.CadreFamliyExample;
import domain.cadre.CadreInfo;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.party.MemberService;
import service.sys.SysConfigService;
import sys.constants.CadreConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreInfoFormService extends BaseMapper {

    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected CadreEduService cadreEduService;
    @Autowired
    protected CadreWorkService cadreWorkService;
    @Autowired
    protected CadreParttimeService cadreParttimeService;
    @Autowired
    protected CadreTrainService cadreTrainService;
    @Autowired
    protected CadreRewardService cadreRewardService;
    @Autowired
    protected CadreCourseService cadreCourseService;
    @Autowired
    protected CadreResearchService cadreResearchService;
    @Autowired
    protected CadrePaperService cadrePaperService;
    @Autowired
    protected CadreBookService cadreBookService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected CadreInfoService cadreInfoService;
    @Autowired
    protected CadrePostService cadrePostService;
    @Autowired
    protected SysConfigService sysConfigService;

    // 获取干部信息采集表属性值
    public CadreInfoForm getCadreInfoForm(int cadreId) {

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        SysUserView uv = cadre.getUser();

        CadreInfoForm bean = new CadreInfoForm();
        bean.setCadreId(cadreId);
        bean.setCode(uv.getCode());
        bean.setRealname(uv.getRealname());
        bean.setGender(uv.getGender());
        bean.setBirth(cadre.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(cadre.getBirth()));
        // 行政级别
        bean.setAdminLevel(cadre.getTypeId());
        bean.setIdCard(uv.getIdcard());

        // 联系方式
        bean.setMobile(uv.getMobile());
        bean.setPhone(uv.getPhone());
        bean.setHomePhone(uv.getHomePhone());
        bean.setEmail(uv.getEmail());

        File avatar = new File(springProps.avatarFolder + uv.getAvatar());
        if (!avatar.exists()) avatar = new File(springProps.avatarFolder + FILE_SEPARATOR + springProps.defaultAvatar);
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        bean.setAvatar(base64);
        bean.setNation(cadre.getNation());
        bean.setNativePlace(cadre.getNativePlace());
        bean.setHousehold(uv.getHousehold());

        bean.setHomeplace(uv.getHomeplace());
        bean.setWorkTime(cadre.getWorkTime()); // 参加工作时间
        //bean.setWorkTime(cadre.getWorkTime());
        bean.setHealth(metaTypeService.getName(uv.getHealth()));
        bean.setProPost(cadre.getProPost());
        bean.setSpecialty(uv.getSpecialty());

        bean.setGrowTime(cadre.getCadreGrowTime());

        // 最高学历
        /*CadreEdu highEdu = cadreEduService.getHighEdu(cadreId);
        bean.setDegree(highEdu == null ? null : metaTypeService.getName(highEdu.getEduId()));
        bean.setSchoolDepMajor(highEdu == null ? null :
                StringUtils.trimToEmpty(highEdu.getSchool())+
                        StringUtils.trimToEmpty(highEdu.getDep())
                        +StringUtils.trimToEmpty(highEdu.getMajor()));*/
        String _fulltimeEdu = "";
        String _fulltimeDegreee = "";
        String _fulltimeMajor = "";
        String _onjobEdu = "";
        String _onjobDegree = "";
        String _onjobMajor = "";
        CadreEdu[] cadreEdus = cadre.getCadreEdus();
        CadreEdu fulltimeEdu = cadreEdus[0];
        CadreEdu onjobEdu = cadreEdus[1];
        if (fulltimeEdu != null) {
            Integer eduId = fulltimeEdu.getEduId();
            //String degree = fulltimeEdu.getDegree();
            _fulltimeEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;
            _fulltimeMajor = fulltimeEdu.getSchool() + fulltimeEdu.getDep() + fulltimeEdu.getMajor()
                    + (StringUtils.isNotBlank(fulltimeEdu.getMajor()) ? "专业" : "");
            _fulltimeDegreee = fulltimeEdu.getDegree(); // 学位
        }
        if (onjobEdu != null) {
            Integer eduId = onjobEdu.getEduId();
            //String degree = onjobEdu.getDegree();
            _onjobEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;
            _onjobMajor = onjobEdu.getSchool() + onjobEdu.getDep() + onjobEdu.getMajor()
                    + (StringUtils.isNotBlank(onjobEdu.getMajor()) ? "专业" : "");

            _onjobDegree = onjobEdu.getDegree();
        }
        // 全日制最高学历
        bean.setEdu(_fulltimeEdu);
        bean.setDegree(_fulltimeDegreee);
        bean.setSchoolDepMajor(_fulltimeMajor);
        bean.setInEdu(_onjobEdu);
        bean.setInDegree(_onjobDegree);
        bean.setInSchoolDepMajor(_onjobMajor);

        // 硕士导师
        String masterTutor = "";
        MetaType masterType = metaTypeService.codeKeyMap().get("mt_edu_master");
        CadreEdu masterEdu = cadreEduService.getCadreEdu(cadreId, masterType.getId());
        if (masterEdu != null && StringUtils.isNotBlank(masterEdu.getTutorName())) {
            masterTutor = masterEdu.getTutorName() + (StringUtils.isNotBlank(masterEdu.getTutorTitle()) ? "，" : "") + masterEdu.getTutorTitle();
        }

        // 博士导师
        String doctorTutor = "";
        MetaType doctorType = metaTypeService.codeKeyMap().get("mt_edu_doctor");
        CadreEdu doctorEdu = cadreEduService.getCadreEdu(cadreId, doctorType.getId());
        if (doctorEdu != null && StringUtils.isNotBlank(doctorEdu.getTutorName())) {
            doctorTutor = doctorEdu.getTutorName() + (StringUtils.isNotBlank(doctorEdu.getTutorTitle()) ? "，" : "") + doctorEdu.getTutorTitle();
        }

        bean.setMasterTutor(masterTutor);
        bean.setDoctorTutor(doctorTutor);

        // 主职,现任职务
        /*CadrePost mainCadrePost = cadrePostService.getCadreMainCadrePost(cadreId);
        bean.setPost(mainCadrePost==null?null:mainCadrePost.getPost());*/
        // 现任职务
        String schoolName = sysConfigService.getSchoolName();
        if (!StringUtils.startsWith(cadre.getTitle(), schoolName)) {
            bean.setPost(schoolName + cadre.getTitle());
        } else {
            bean.setPost(cadre.getTitle());
        }

        // 学习经历
        CadreInfo edu = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_EDU);
        bean.setLearnDesc((edu == null || StringUtils.isBlank(edu.getContent())) ?
                freemarkerService.freemarker(cadreEduService.list(cadreId),
                        "cadreEdus", "/cadre/cadreEdu.ftl") : edu.getContent());

        // 工作经历
        CadreInfo work = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_WORK);
        bean.setWorkDesc((work == null || StringUtils.isBlank(work.getContent())) ?
                freemarkerService.freemarker(cadreWorkService.list(cadreId),
                        "cadreWorks", "/cadre/cadreWork.ftl") : work.getContent());

        // 社会或学术兼职
        CadreInfo parttime = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_PARTTIME);
        String _parttime = null;
        if (parttime == null || StringUtils.isBlank(parttime.getContent())) {
            _parttime = freemarkerService.freemarker(cadreParttimeService.list(cadreId),
                    "cadreParttimes", "/cadre/cadreParttime.ftl");
        } else {
            _parttime = StringUtils.trim(parttime.getContent());
        }
        bean.setParttime(_parttime == null ? "无" : _parttime);

        // 培训情况
        CadreInfo train = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_TRAIN);
        String _train = null;
        if (train == null || StringUtils.isBlank(train.getContent())) {

            _train = freemarkerService.freemarker(cadreTrainService.list(cadreId),
                    "cadreTrains", "/cadre/cadreTrain.ftl");
        } else {
            _train = StringUtils.trim(train.getContent());
        }
        bean.setTrainDesc(_train == null ? "无" : _train);

        // 教学情况
        CadreInfo teach = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_TEACH);
        String _teach = null;
        if (teach == null || StringUtils.isBlank(teach.getContent())) {

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("bksCadreCourses", cadreCourseService.list(cadreId, CadreConstants.CADRE_COURSE_TYPE_BKS));
            dataMap.put("ssCadreCourses", cadreCourseService.list(cadreId, CadreConstants.CADRE_COURSE_TYPE_SS));
            dataMap.put("bsCadreCourses", cadreCourseService.list(cadreId, CadreConstants.CADRE_COURSE_TYPE_BS));
            dataMap.put("cadreRewards", cadreRewardService.list(cadreId, CadreConstants.CADRE_REWARD_TYPE_TEACH));

            _teach = freemarkerService.freemarker(dataMap, "/cadre/cadreCourse.ftl");
        } else {
            _teach = StringUtils.trim(teach.getContent());
        }
        bean.setTeachDesc(_teach == null ? "无" : _teach);


        // 科研情况
        CadreInfo research = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_RESEARCH);
        String _research = null;
        if (research == null || StringUtils.isBlank(research.getContent())) {

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("cadreResearchDirects", cadreResearchService.list(cadreId, CadreConstants.CADRE_RESEARCH_TYPE_DIRECT));
            dataMap.put("cadreResearchIns", cadreResearchService.list(cadreId, CadreConstants.CADRE_RESEARCH_TYPE_IN));
            dataMap.put("cadreBooks", cadreBookService.list(cadreId));
            dataMap.put("cadrePapers", cadrePaperService.list(cadreId));
            dataMap.put("cadreRewards", cadreRewardService.list(cadreId, CadreConstants.CADRE_REWARD_TYPE_RESEARCH));

            _research = freemarkerService.freemarker(dataMap, "/cadre/cadreResearch.ftl");
        } else {
            _research = StringUtils.trim(research.getContent());
        }
        bean.setResearchDesc(_research == null ? "无" : _research);

        // 其他奖励情况
        CadreInfo otherReward = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_REWARD_OTHER);
        String _otherReward = null;
        if (otherReward == null || StringUtils.isBlank(otherReward.getContent())) {

            _otherReward = freemarkerService.freemarker(cadreRewardService.list(cadreId, CadreConstants.CADRE_REWARD_TYPE_OTHER),
                    "cadreRewards", "/cadre/cadreReward.ftl");
        } else {
            _otherReward = StringUtils.trim(otherReward.getContent());
        }
        bean.setOtherRewardDesc(_otherReward == null ? "无" : _otherReward);

        {
            // 企业兼职情况
            CadreCompanyExample example = new CadreCompanyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            List<CadreCompany> cadreCompanies = cadreCompanyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 3));
            if (cadreCompanies.size() == 0) {
                CadreCompany record = new CadreCompany();
                record.setUnit("无");
                cadreCompanies.add(record);
            }
            bean.setCadreCompanies(cadreCompanies);
        }

        {
            // 社会关系
            CadreFamliyExample example = new CadreFamliyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            List<CadreFamliy> cadreFamliys = cadreFamliyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 6));
            bean.setCadreFamliys(cadreFamliys);
        }

        {
            // 家庭成员海外情况
            CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            List<CadreFamliyAbroad> cadreFamliyAbroads =
                    cadreFamliyAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 2));
            if (cadreFamliyAbroads.size() == 0) {
                CadreFamliyAbroad record = new CadreFamliyAbroad();
                record.setFamliyTitle("无");
                cadreFamliyAbroads.add(record);
            } else {
                for (CadreFamliyAbroad record : cadreFamliyAbroads) {
                    String famliyTitle = CadreConstants.CADRE_FAMLIY_TITLE_MAP.get(record.getCadreFamliy().getTitle());
                    record.setFamliyTitle(famliyTitle);
                }
            }

            bean.setCadreFamliyAbroads(cadreFamliyAbroads);
        }

        return bean;
    }

    public Map<String, Object> getDataMap(int cadreId) throws IOException, TemplateException {

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

        dataMap.put("masterTutor", bean.getMasterTutor());
        dataMap.put("doctorTutor", bean.getDoctorTutor());

        dataMap.put("post", bean.getPost());

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        MetaType adminLevel = metaTypeMap.get(bean.getAdminLevel());
        dataMap.put("adminLevel", adminLevel == null ? "" : adminLevel.getName());
        /*
        dataMap.put("adminLevel_1", metaTypeMap.get("mt_admin_level_main").getId());
        dataMap.put("adminLevel_2", metaTypeMap.get("mt_admin_level_vice").getId());
        dataMap.put("adminLevel_3", metaTypeMap.get("mt_admin_level_none").getId());*/

        dataMap.put("idCard", bean.getIdCard());
        dataMap.put("household", bean.getHousehold());

        //dataMap.put("depWork", "");

        dataMap.put("learnDesc", bean.getLearnDesc() == null ? "" :
                freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc()));
        dataMap.put("workDesc", bean.getWorkDesc() == null ? "" :
                freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc()));
        dataMap.put("parttime",
                freemarkerService.genTitleEditorSegment(bean.getParttime()));
        dataMap.put("trainDesc", bean.getTrainDesc() == null ? "" :
                freemarkerService.genTitleEditorSegment(bean.getTrainDesc()));
        dataMap.put("teachDesc", bean.getTeachDesc() == null ? "" :
                freemarkerService.genTitleEditorSegment(bean.getTeachDesc()));
        dataMap.put("researchDesc", bean.getResearchDesc() == null ? "" :
                freemarkerService.genTitleEditorSegment(bean.getResearchDesc()));
        dataMap.put("otherRewardDesc", bean.getOtherRewardDesc() == null ? "" :
                freemarkerService.genTitleEditorSegment(bean.getOtherRewardDesc()));

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
                else {
                    famliyAbroads += getFamliyAbroadSeg(cadreFamliyAbroads.get(i), "/infoform/abroad.ftl");
                }
            }
            dataMap.put("famliyAbroads", famliyAbroads);
        }

        return dataMap;
    }

    // 输出干部信息采集表
    public void process(int cadreId, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMap(cadreId);
        dataMap.put("fillDate", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));

        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        dataMap.put("schoolEmail", CmTag.getSysConfig().getSchoolEmail());
        freemarkerService.process("/infoform/infoform.ftl", dataMap, out);
    }

    private String getCompanySeg(CadreCompany bean, String ftlPath) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("a1", bean == null ? "" : bean.getUnit());
        dataMap.put("b1", bean == null ? "" : DateUtils.formatDate(bean.getStartTime(), "yyyy.MM"));
        dataMap.put("c1", bean == null ? "" : bean.getReportUnit());

        return freemarkerService.process(ftlPath, dataMap);
    }

    private String getFamliySeg(CadreFamliy bean, String ftlPath) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();

        String ftitle = "";
        if (bean != null) {
            ftitle = CadreConstants.CADRE_FAMLIY_TITLE_MAP.get(bean.getTitle());
        }
        dataMap.put("a2", StringUtils.trimToNull(ftitle));
        dataMap.put("b2", bean == null ? "" : StringUtils.trimToEmpty(bean.getRealname()));

        dataMap.put("c2", bean == null ? "" : DateUtils.formatDate(bean.getBirthday(), "yyyy.MM"));

        String fps = "";
        if (bean != null && bean.getPoliticalStatus() != null) {
            fps = metaTypeService.getName(bean.getPoliticalStatus());
        }
        dataMap.put("d2", StringUtils.trimToNull(fps));

        dataMap.put("e2", bean == null ? "" : StringUtils.trimToEmpty(bean.getUnit()));

        return freemarkerService.process(ftlPath, dataMap);
    }

    private String getFamliyAbroadSeg(CadreFamliyAbroad bean, String ftlPath) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("a3", bean == null ? "" : bean.getFamliyTitle());
        dataMap.put("b3", (bean == null || bean.getCadreFamliy() == null) ? "" : bean.getCadreFamliy().getRealname());
        dataMap.put("c3", (bean == null || bean.getCountry() == null) ? "" : bean.getCountry());
        dataMap.put("d3", (bean == null || bean.getAbroadTime() == null) ? "" : DateUtils.formatDate(bean.getAbroadTime(), "yyyy.MM"));
        dataMap.put("e3", (bean == null || bean.getCity() == null) ? "" : bean.getCity());

        dataMap.put("type", (bean == null || bean.getType() == null) ? -1 : bean.getType());// 移居类别
        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        dataMap.put("type_1", metaTypeMap.get("mt_abroad_type_citizen").getId());
        dataMap.put("type_2", metaTypeMap.get("mt_abroad_type_live").getId());
        dataMap.put("type_3", metaTypeMap.get("mt_abroad_type_stay").getId());


        return freemarkerService.process(ftlPath, dataMap);
    }
}
