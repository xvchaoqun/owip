package service.cadre;

import bean.CadreInfoForm;
import domain.base.MetaType;
import domain.cadre.*;
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
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

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
    /*@Autowired
    protected CadreInfoService cadreInfoService;*/
    @Autowired
    protected CadreAdformService cadreAdformService;
    @Autowired
    protected CadrePostService cadrePostService;
    @Autowired
    protected SysConfigService sysConfigService;

    public void export(Integer[] cadreIds, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, TemplateException {

        if (cadreIds == null) return;

        if (cadreIds.length == 1) {

            int cadreId = cadreIds[0];
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            //输出文件
            String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                    + " 干部信息采集表 " + cadre.getUser().getRealname()  + ".doc";
            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
            response.setContentType("application/msword;charset=UTF-8");

            process(cadreId, response.getWriter());
        }else {

            Map<String, File> fileMap = new LinkedHashMap<>();
            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "infoForms";
            FileUtils.mkdirs(tmpdir, false);

            Set<String> filenameSet = new HashSet<>();
            for (int cadreId : cadreIds) {
                CadreView cadre = iCadreMapper.getCadre(cadreId);
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " 干部信息采集表 " + cadre.getRealname() + ".doc";

                // 保证文件名不重复
                if(filenameSet.contains(filename)){
                    filename = cadre.getCode() + " " + filename;
                }
                filenameSet.add(filename);

                String filepath = tmpdir + FILE_SEPARATOR + filename;
                FileOutputStream output = new FileOutputStream(new File(filepath));
                OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

                process(cadreId, osw);

                fileMap.put(filename, new File(filepath));
            }

            String filename = String.format("%s干部信息采集表",
                    CmTag.getSysConfig().getSchoolName());
            DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.zip(fileMap, filename, request, response);
            FileUtils.deleteDir(new File(tmpdir));
        }
    }

    public void export2(Integer[] cadreIds, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, TemplateException {

        if (cadreIds == null) return;

        if (cadreIds.length == 1) {

            int cadreId = cadreIds[0];
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            //输出文件
            String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                    + " 干部信息表 " + cadre.getUser().getRealname()  + ".doc";
            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
            response.setContentType("application/msword;charset=UTF-8");

            process2(cadreId, response.getWriter());
        }else {

            Map<String, File> fileMap = new LinkedHashMap<>();
            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "infoForms";
            FileUtils.mkdirs(tmpdir, false);

            Set<String> filenameSet = new HashSet<>();
            for (int cadreId : cadreIds) {
                CadreView cadre = iCadreMapper.getCadre(cadreId);
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " 干部信息表 " + cadre.getRealname() + ".doc";

                // 保证文件名不重复
                if(filenameSet.contains(filename)){
                    filename = cadre.getCode() + filename;
                }
                filenameSet.add(filename);

                String filepath = tmpdir + FILE_SEPARATOR + filename;
                FileOutputStream output = new FileOutputStream(new File(filepath));
                OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

                process2(cadreId, osw);

                fileMap.put(filename, new File(filepath));
            }

            String filename = String.format("%s干部信息表.xlsx",
                    CmTag.getSysConfig().getSchoolName());
            DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.zip(fileMap, filename, request, response);
            FileUtils.deleteDir(new File(tmpdir));
        }
    }

    public void export3(Integer[] cadreIds, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, TemplateException {

        if (cadreIds == null) return;

        if (cadreIds.length == 1) {

            int cadreId = cadreIds[0];
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            //输出文件
            String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                    + " 信息采集表 " + cadre.getUser().getRealname()  + ".doc";
            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
            response.setContentType("application/msword;charset=UTF-8");

            process3(cadreId, response.getWriter());
        }else {

            Map<String, File> fileMap = new LinkedHashMap<>();
            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "infoForms";
            FileUtils.mkdirs(tmpdir, false);

            Set<String> filenameSet = new HashSet<>();
            for (int cadreId : cadreIds) {
                CadreView cadre = iCadreMapper.getCadre(cadreId);
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " 信息采集表 " + cadre.getRealname() + ".doc";

                // 保证文件名不重复
                if(filenameSet.contains(filename)){
                    filename = cadre.getCode() + filename;
                }
                filenameSet.add(filename);

                String filepath = tmpdir + FILE_SEPARATOR + filename;
                FileOutputStream output = new FileOutputStream(new File(filepath));
                OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

                process3(cadreId, osw);

                fileMap.put(filename, new File(filepath));
            }

            String filename = String.format("%s信息采集表.xlsx",
                    CmTag.getSysConfig().getSchoolName());
            DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.zip(fileMap, filename, request, response);
            FileUtils.deleteDir(new File(tmpdir));
        }
    }

    // 获取干部信息采集表属性值
    public CadreInfoForm getCadreInfoForm(int cadreId) {

        CadreInfoForm bean = cadreAdformService.getCadreAdform(cadreId);

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView uv = cadre.getUser();

        bean.setCode(uv.getCode());

        // 行政级别
        bean.setAdminLevel(cadre.getAdminLevel());
        // 管理岗位等级及分级时间
        bean.setManageLevel(cadre.getManageLevel());
        bean.setManageLevelTime(cadre.getManageLevelTime());
        bean.setIdCard(uv.getIdcard());

        // 联系方式
        bean.setMobile(uv.getMobile());
        bean.setPhone(uv.getPhone());
        bean.setHomePhone(uv.getHomePhone());
        bean.setEmail(uv.getEmail());

        bean.setHousehold(uv.getHousehold());

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

        // 学习经历
        /*CadreInfo edu = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_EDU);
        bean.setLearnDesc((edu == null || StringUtils.isBlank(edu.getContent())) ?
                freemarkerService.freemarker(cadreEduService.list(cadreId, null),
                        "cadreEdus", "/cadre/cadreEdu.ftl") : edu.getContent());*/
        bean.setLearnDesc(freemarkerService.freemarker(cadreEduService.list(cadreId, null),
                "cadreEdus", "/cadre/cadreEdu.ftl"));

        // 工作经历
        /*CadreInfo work = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_WORK);
        bean.setWorkDesc((work == null || StringUtils.isBlank(work.getContent())) ?
                freemarkerService.freemarker(cadreWorkService.list(cadreId),
                        "cadreWorks", "/cadre/cadreWork.ftl") : work.getContent());*/
        bean.setWorkDesc(freemarkerService.freemarker(cadreWorkService.list(cadreId),
                "cadreWorks", "/cadre/cadreWork.ftl"));

        // 社会或学术兼职
        /*CadreInfo parttime = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_PARTTIME);
        String _parttime = null;
        if (parttime == null || StringUtils.isBlank(parttime.getContent())) {
            _parttime = freemarkerService.freemarker(cadreParttimeService.list(cadreId),
                    "cadreParttimes", "/cadre/cadreParttime.ftl");
        } else {
            _parttime = StringUtils.trim(parttime.getContent());
        }*/
        String _parttime = freemarkerService.freemarker(cadreParttimeService.list(cadreId),
                "cadreParttimes", "/cadre/cadreParttime.ftl");
        bean.setParttime(StringUtils.defaultIfBlank(_parttime, "无"));

        // 培训情况
        /*CadreInfo train = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_TRAIN);
        String _train = null;
        if (train == null || StringUtils.isBlank(train.getContent())) {

            _train = freemarkerService.freemarker(cadreTrainService.list(cadreId),
                    "cadreTrains", "/cadre/cadreTrain.ftl");
        } else {
            _train = StringUtils.trim(train.getContent());
        }*/
        String _train = freemarkerService.freemarker(cadreTrainService.list(cadreId),
                "cadreTrains", "/cadre/cadreTrain.ftl");
        bean.setTrainDesc(StringUtils.defaultIfBlank(_train, "无"));

        // 教学情况
        /*CadreInfo teach = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_TEACH);
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
        }*/
        String _teach = null;
        {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("bksCadreCourses", cadreCourseService.list(cadreId, CadreConstants.CADRE_COURSE_TYPE_BKS));
            dataMap.put("ssCadreCourses", cadreCourseService.list(cadreId, CadreConstants.CADRE_COURSE_TYPE_SS));
            dataMap.put("bsCadreCourses", cadreCourseService.list(cadreId, CadreConstants.CADRE_COURSE_TYPE_BS));
            dataMap.put("cadreRewards", cadreRewardService.list(cadreId, CadreConstants.CADRE_REWARD_TYPE_TEACH));

            _teach = freemarkerService.freemarker(dataMap, "/cadre/cadreCourse.ftl");
        }
        bean.setTeachDesc(StringUtils.defaultIfBlank(_teach, "无"));


        // 科研情况
        /*CadreInfo research = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_RESEARCH);
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
        }*/
        String _research = null;
        {

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("cadreResearchDirects", cadreResearchService.list(cadreId, CadreConstants.CADRE_RESEARCH_TYPE_DIRECT));
            dataMap.put("cadreResearchIns", cadreResearchService.list(cadreId, CadreConstants.CADRE_RESEARCH_TYPE_IN));
            dataMap.put("cadreBooks", cadreBookService.list(cadreId));
            dataMap.put("cadrePapers", cadrePaperService.list(cadreId));
            dataMap.put("cadreRewards", cadreRewardService.list(cadreId, CadreConstants.CADRE_REWARD_TYPE_RESEARCH));

            _research = freemarkerService.freemarker(dataMap, "/cadre/cadreResearch.ftl");
        }
        bean.setResearchDesc(StringUtils.defaultIfBlank(_research, "无"));

        // 其他奖励情况
        {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("cadreRewards", cadreRewardService.list(cadreId, CadreConstants.CADRE_REWARD_TYPE_OTHER));
            String _otherReward = freemarkerService.freemarker(dataMap, "/cadre/cadreReward.ftl");
            bean.setOtherRewardDesc(StringUtils.defaultIfBlank(_otherReward, "无"));
        }

        {
            // 企业兼职情况
            CadreCompanyExample example = new CadreCompanyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            List<CadreCompany> cadreCompanies = cadreCompanyMapper
                    .selectByExampleWithRowbounds(example, new RowBounds(0, 3));
            if (cadreCompanies.size() == 0) {
                CadreCompany record = new CadreCompany();
                record.setUnit("无");
                cadreCompanies.add(record);
            }
            bean.setCadreCompanies(cadreCompanies);
        }

        {
            // 家庭成员海外情况
            CadreFamilyAbroadExample example = new CadreFamilyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            List<CadreFamilyAbroad> cadreFamilyAbroads =
                    cadreFamilyAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 2));
            if (cadreFamilyAbroads.size() == 0) {
                CadreFamilyAbroad record = new CadreFamilyAbroad();
                record.setFamilyTitle("无");
                cadreFamilyAbroads.add(record);
            } else {
                for (CadreFamilyAbroad record : cadreFamilyAbroads) {
                    String familyTitle = metaTypeService.getName(record.getCadreFamily().getTitle());
                    record.setFamilyTitle(familyTitle);
                }
            }

            bean.setCadreFamilyAbroads(cadreFamilyAbroads);
        }

        return bean;
    }

    public Map<String, Object> getDataMap(int cadreId) throws IOException, TemplateException {

        CadreInfoForm bean = getCadreInfoForm(cadreId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("code", bean.getCode());
        dataMap.put("name", bean.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM));
        dataMap.put("a", bean.getAge());

        dataMap.put("avatar", bean.getAvatar());
        dataMap.put("avatarWidth", bean.getAvatarWidth());
        dataMap.put("avatarHeight", bean.getAvatarHeight());
        dataMap.put("nation", bean.getNation());
        dataMap.put("np", bean.getNativePlace());
        dataMap.put("hp", bean.getHomeplace());

        dataMap.put("isOw", bean.getIsOw());
        dataMap.put("owGrowTime", DateUtils.formatDate(bean.getOwGrowTime(), DateUtils.YYYYMM));
        if (bean.getDpTypeId() != null && bean.getDpTypeId() > 0) {
            // 第一民主党派
            MetaType metaType = CmTag.getMetaType(bean.getDpTypeId());
            String dpPartyName = StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
            dataMap.put("dpPartyName", dpPartyName);
            dataMap.put("dpGrowTime", DateUtils.formatDate(bean.getDpGrowTime(), DateUtils.YYYYMM));
            // 其他民主党派
            List<CadreParty> dpParties = bean.getDpParties();
            if(dpParties.size()>0) {
                String dpPartyNames = "";
                for (CadreParty dpParty : dpParties) {
                    metaType = CmTag.getMetaType(dpParty.getClassId());
                    dpPartyNames += "；" + StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
                }
                dataMap.put("dpPartyNames", dpPartyNames);
            }
        }

        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM));

        dataMap.put("health", bean.getHealth());
        // 专业技术职务及评定时间
        dataMap.put("proPost", bean.getProPost());
        dataMap.put("proPostTime", DateUtils.formatDate(bean.getProPostTime(), DateUtils.YYYYMM));
        dataMap.put("specialty", bean.getSpecialty());

        dataMap.put("edu", bean.getEdu());
        dataMap.put("degree", StringUtils.trimToNull(bean.getDegree()));
        dataMap.put("inEdu", bean.getInEdu());
        dataMap.put("inDegree", StringUtils.trimToNull(bean.getInDegree()));

        dataMap.put("sameSchool", bean.isSameSchool());
        dataMap.put("schoolDepMajor1", bean.getSchoolDepMajor1());
        dataMap.put("schoolDepMajor2", bean.getSchoolDepMajor2());

        dataMap.put("sameInSchool", bean.isSameInSchool());
        dataMap.put("inSchoolDepMajor1", bean.getInSchoolDepMajor1());
        dataMap.put("inSchoolDepMajor2", bean.getInSchoolDepMajor2());

        dataMap.put("masterTutor", bean.getMasterTutor());
        dataMap.put("doctorTutor", bean.getDoctorTutor());

        dataMap.put("post", bean.getPost());

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        MetaType adminLevel = metaTypeMap.get(bean.getAdminLevel());
        dataMap.put("adminLevel", adminLevel == null ? "" : adminLevel.getName());

        // 管理岗位等级及分级时间
        dataMap.put("manageLevel", bean.getManageLevel());
        dataMap.put("manageLevelTime", DateUtils.formatDate(bean.getManageLevelTime(), DateUtils.YYYYMM));
        /*
        dataMap.put("adminLevel_1", metaTypeMap.get("mt_admin_level_main").getId());
        dataMap.put("adminLevel_2", metaTypeMap.get("mt_admin_level_vice").getId());
        dataMap.put("adminLevel_3", metaTypeMap.get("mt_admin_level_none").getId());*/

        dataMap.put("idCard", bean.getIdCard());
        dataMap.put("household", bean.getHousehold());

        //dataMap.put("depWork", "");

        //dataMap.put("learnDesc", freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 440));
        //dataMap.put("workDesc", freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 440));

        String resumeDesc = StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("学习经历",
                bean.getLearnDesc(), true, 440, "/common/oldTitleEditor.ftl"))
                + StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("工作经历",
                bean.getWorkDesc(), true, 440, "/common/oldTitleEditor.ftl"));
        dataMap.put("resumeDesc", StringUtils.trimToNull(resumeDesc));

        dataMap.put("parttime", freemarkerService.genTitleEditorSegment(bean.getParttime(), true, false, 440));
        dataMap.put("trainDesc", freemarkerService.genTitleEditorSegment(bean.getTrainDesc(), true, false, 440));
        dataMap.put("teachDesc", freemarkerService.genTitleEditorSegment(bean.getTeachDesc(), false, false, 440));
        dataMap.put("researchDesc", freemarkerService.genTitleEditorSegment(bean.getResearchDesc(), false, false, 440));
        dataMap.put("otherRewardDesc", freemarkerService.genTitleEditorSegment(bean.getOtherRewardDesc(), false, false, 440));

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
            String familys = "";
            List<CadreFamily> cadreFamilys = bean.getCadreFamilys();
            int size = cadreFamilys.size();
            for (int i = 0; i < 6; i++) {
                if (size <= i)
                    familys += getFamilySeg(null, "/infoform/family.ftl");
                else
                    familys += getFamilySeg(cadreFamilys.get(i), "/infoform/family.ftl");
            }
            dataMap.put("familys", familys);
        }

        {
            String familyAbroads = "";
            List<CadreFamilyAbroad> cadreFamilyAbroads = bean.getCadreFamilyAbroads();
            int size = cadreFamilyAbroads.size();
            for (int i = 0; i < 2; i++) {
                if (size <= i)
                    familyAbroads += getFamilyAbroadSeg(null, "/infoform/abroad.ftl");
                else {
                    familyAbroads += getFamilyAbroadSeg(cadreFamilyAbroads.get(i), "/infoform/abroad.ftl");
                }
            }
            dataMap.put("familyAbroads", familyAbroads);
        }

        return dataMap;
    }

    public Map<String, Object> getDataMap2(int cadreId) throws IOException, TemplateException {

        CadreInfoForm bean = getCadreInfoForm(cadreId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("code", bean.getCode());
        dataMap.put("name", bean.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM));
        dataMap.put("a", bean.getAge());

        dataMap.put("avatar", bean.getAvatar());
        dataMap.put("avatarWidth", bean.getAvatarWidth());
        dataMap.put("avatarHeight", bean.getAvatarHeight());
        dataMap.put("nation", bean.getNation());
        dataMap.put("np", bean.getNativePlace());
        dataMap.put("hp", bean.getHomeplace());

        dataMap.put("isOw", bean.getIsOw());
        dataMap.put("owGrowTime", DateUtils.formatDate(bean.getOwGrowTime(), DateUtils.YYYYMM));
        if (bean.getDpTypeId() != null && bean.getDpTypeId() > 0) {
            // 民主党派
            MetaType metaType = CmTag.getMetaType(bean.getDpTypeId());
            String dpPartyName = StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
            dataMap.put("dpPartyName", dpPartyName);
            dataMap.put("dpGrowTime", DateUtils.formatDate(bean.getDpGrowTime(), DateUtils.YYYYMM));
        }

        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM));

        dataMap.put("health", bean.getHealth());
        // 专业技术职务及评定时间
        dataMap.put("proPost", bean.getProPost());
        dataMap.put("proPostTime", DateUtils.formatDate(bean.getProPostTime(), DateUtils.YYYYMM));
        dataMap.put("specialty", bean.getSpecialty());

        dataMap.put("edu", bean.getEdu());
        dataMap.put("degree", StringUtils.trimToNull(bean.getDegree()));
        dataMap.put("inEdu", bean.getInEdu());
        dataMap.put("inDegree", StringUtils.trimToNull(bean.getInDegree()));

        dataMap.put("sameSchool", bean.isSameSchool());
        dataMap.put("schoolDepMajor1", bean.getSchoolDepMajor1());
        dataMap.put("schoolDepMajor2", bean.getSchoolDepMajor2());

        dataMap.put("sameInSchool", bean.isSameInSchool());
        dataMap.put("inSchoolDepMajor1", bean.getInSchoolDepMajor1());
        dataMap.put("inSchoolDepMajor2", bean.getInSchoolDepMajor2());

        dataMap.put("masterTutor", bean.getMasterTutor());
        dataMap.put("doctorTutor", bean.getDoctorTutor());

        //dataMap.put("post", bean.getPost());
        dataMap.put("title", bean.getTitle());
        dataMap.put("reward", freemarkerService.genTitleEditorSegment(null, bean.getReward(),
                false, 360, "/common/titleEditor.ftl"));
        dataMap.put("ces", bean.getCes());

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        MetaType adminLevel = metaTypeMap.get(bean.getAdminLevel());
        dataMap.put("adminLevel", adminLevel == null ? "" : adminLevel.getName());

        // 管理岗位等级及分级时间
        dataMap.put("manageLevel", bean.getManageLevel());
        dataMap.put("manageLevelTime", DateUtils.formatDate(bean.getManageLevelTime(), DateUtils.YYYYMM));
        /*
        dataMap.put("adminLevel_1", metaTypeMap.get("mt_admin_level_main").getId());
        dataMap.put("adminLevel_2", metaTypeMap.get("mt_admin_level_vice").getId());
        dataMap.put("adminLevel_3", metaTypeMap.get("mt_admin_level_none").getId());*/

        dataMap.put("idCard", bean.getIdCard());
        dataMap.put("household", bean.getHousehold());

        //dataMap.put("depWork", "");

        //dataMap.put("learnDesc", freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 440));
        //dataMap.put("workDesc", freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 440));

        String resumeDesc = freemarkerService.genTitleEditorSegment(null, bean.getResumeDesc(), true, 360, "/common/titleEditor.ftl");
        /*if(StringUtils.isBlank(resumeDesc)){
            resumeDesc = StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 360))
                    + StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 360));
        }*/
        dataMap.put("resumeDesc", StringUtils.trimToNull(resumeDesc));

        //dataMap.put("parttime", freemarkerService.genTitleEditorSegment(bean.getParttime(), true, false, 440));
        dataMap.put("trainDesc", freemarkerService.genTitleEditorSegment(bean.getTrainDesc(), true, false, 360));
        //dataMap.put("teachDesc", freemarkerService.genTitleEditorSegment(bean.getTeachDesc(), false, false, 440));
        //dataMap.put("researchDesc", freemarkerService.genTitleEditorSegment(bean.getResearchDesc(), false, true, 440));
        //dataMap.put("otherRewardDesc", freemarkerService.genTitleEditorSegment(bean.getOtherRewardDesc(), false, false, 440));

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
            String familys = "";
            List<CadreFamily> cadreFamilys = bean.getCadreFamilys();
            int size = cadreFamilys.size();
            for (int i = 0; i < size; i++) {
                familys += getFamilySeg(cadreFamilys.get(i), "/infoform/family2.ftl");
            }
            for (int i = 1; i < (4-size); i++) { // 保证至少有4行
               familys += getFamilySeg(null, "/infoform/family2.ftl");
            }

            dataMap.put("familys", familys);
        }

        return dataMap;
    }

    // 输出干部信息采集表
    public void process(int cadreId, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMap(cadreId);
        dataMap.put("fillDate", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));

        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        dataMap.put("schoolEmail", CmTag.getStringProperty("zzb_email"));
        freemarkerService.process("/infoform/infoform.ftl", dataMap, out);
    }

    // 输出干部信息表
    public void process2(int cadreId, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMap2(cadreId);

        freemarkerService.process("/infoform/infoform2.ftl", dataMap, out);
    }
    // 输出党委委员信息采集表
    public void process3(int cadreId, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMap2(cadreId);
        dataMap.put("familys", null);
        dataMap.put("trainDesc", null);
        dataMap.put("familys", null);
        dataMap.put("ces", null);
        dataMap.put("reward", null);
        dataMap.put("resumeDesc", null);

        freemarkerService.process("/infoform/infoform3.ftl", dataMap, out);
    }

    private String getCompanySeg(CadreCompany bean, String ftlPath) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("a1", bean == null ? "" : bean.getUnit());
        dataMap.put("b1", bean == null ? "" : DateUtils.formatDate(bean.getStartTime(), DateUtils.YYYYMM));
        dataMap.put("c1", bean == null ? "" : bean.getApprovalUnit());

        return freemarkerService.process(ftlPath, dataMap);
    }

    private String getFamilySeg(CadreFamily bean, String ftlPath) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();

        String ftitle = "";
        if (bean != null) {
            ftitle = metaTypeService.getName(bean.getTitle());
        }
        dataMap.put("a2", StringUtils.trimToNull(ftitle));
        dataMap.put("b2", bean == null ? "" : StringUtils.trimToEmpty(bean.getRealname()));

        dataMap.put("c2", bean == null ? "" : DateUtils.formatDate(bean.getBirthday(), DateUtils.YYYYMM));

        String fps = "";
        if (bean != null && bean.getPoliticalStatus() != null) {
            fps = metaTypeService.getName(bean.getPoliticalStatus());
        }
        dataMap.put("d2", StringUtils.trimToNull(fps));

        dataMap.put("e2", bean == null ? "" : StringUtils.trimToEmpty(bean.getUnit()));

        return freemarkerService.process(ftlPath, dataMap);
    }

    private String getFamilyAbroadSeg(CadreFamilyAbroad bean, String ftlPath) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("a3", bean == null ? "" : bean.getFamilyTitle());
        dataMap.put("b3", (bean == null || bean.getCadreFamily() == null) ? "" : bean.getCadreFamily().getRealname());
        dataMap.put("c3", (bean == null || bean.getCountry() == null) ? "" : bean.getCountry());
        dataMap.put("d3", (bean == null || bean.getAbroadTime() == null) ? "" : DateUtils.formatDate(bean.getAbroadTime(), DateUtils.YYYYMM));
        dataMap.put("e3", (bean == null || bean.getCity() == null) ? "" : bean.getCity());

        dataMap.put("type", (bean == null || bean.getType() == null) ? -1 : bean.getType());// 移居类别
        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        dataMap.put("type_1", metaTypeMap.get("mt_abroad_type_citizen").getId());
        dataMap.put("type_2", metaTypeMap.get("mt_abroad_type_live").getId());
        dataMap.put("type_3", metaTypeMap.get("mt_abroad_type_stay").getId());


        return freemarkerService.process(ftlPath, dataMap);
    }
}
