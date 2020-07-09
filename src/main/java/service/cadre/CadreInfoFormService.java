package service.cadre;

import bean.CadreInfoForm;
import bean.CadreResume;
import bean.PartyMemberInfoForm;
import domain.base.MetaType;
import domain.cadre.*;
import domain.party.Branch;
import domain.party.BranchMember;
import domain.party.PartyEva;
import domain.party.PartyEvaExample;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.party.*;
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
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected PartyPostService partyPostService;
    @Autowired
    protected PartyRewardService partyRewardService;
    @Autowired
    protected PartyPunishService partyPunishService;

    public void export(Integer[] cadreIds, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, TemplateException {

        if (cadreIds == null) return;

        if (cadreIds.length == 1) {

            int cadreId = cadreIds[0];
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            //输出文件
            String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                    + " 干部信息采集表 " + cadre.getUser().getRealname()  + ".docx";
            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
            response.setContentType("application/msword;charset=UTF-8");

            process(cadreId, response.getOutputStream());
        }else {

            Map<String, File> fileMap = new LinkedHashMap<>();
            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "infoForms";
            FileUtils.mkdirs(tmpdir, false);

            Set<String> filenameSet = new HashSet<>();
            for (int cadreId : cadreIds) {
                CadreView cadre = iCadreMapper.getCadre(cadreId);
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " 干部信息采集表 " + cadre.getRealname() + ".docx";

                // 保证文件名不重复
                if(filenameSet.contains(filename)){
                    filename = cadre.getCode() + " " + filename;
                }
                filenameSet.add(filename);

                String filepath = tmpdir + FILE_SEPARATOR + filename;
                FileOutputStream output = new FileOutputStream(new File(filepath));
                OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

                process(cadreId, output);

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
                    + " 干部信息表 " + cadre.getUser().getRealname()  + ".docx";
            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
            response.setContentType("application/msword;charset=UTF-8");

            process2(cadreId, response.getOutputStream());
        }else {

            Map<String, File> fileMap = new LinkedHashMap<>();
            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "infoForms";
            FileUtils.mkdirs(tmpdir, false);

            Set<String> filenameSet = new HashSet<>();
            for (int cadreId : cadreIds) {
                CadreView cadre = iCadreMapper.getCadre(cadreId);
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " 干部信息表 " + cadre.getRealname() + ".docx";

                // 保证文件名不重复
                if(filenameSet.contains(filename)){
                    filename = cadre.getCode() + " " + filename;
                }
                filenameSet.add(filename);

                String filepath = tmpdir + FILE_SEPARATOR + filename;
                FileOutputStream output = new FileOutputStream(new File(filepath));
                OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

                process2(cadreId, output);

                fileMap.put(filename, new File(filepath));
            }

            String filename = String.format("%s干部信息表",
                    CmTag.getSysConfig().getSchoolName());
            DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.zip(fileMap, filename, request, response);
            FileUtils.deleteDir(new File(tmpdir));
        }
    }

    public void export_simple(Integer[] cadreIds, HttpServletRequest request,
                        HttpServletResponse response) throws IOException, TemplateException {

        if (cadreIds == null) return;

        if (cadreIds.length == 1) {

            int cadreId = cadreIds[0];
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            //输出文件
            String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                    + " 干部信息表(简版) " + cadre.getUser().getRealname()  + ".docx";
            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
            response.setContentType("application/msword;charset=UTF-8");

            processSimple(cadreId, response.getOutputStream());
        }else {

            Map<String, File> fileMap = new LinkedHashMap<>();
            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "infoForms";
            FileUtils.mkdirs(tmpdir, false);

            Set<String> filenameSet = new HashSet<>();
            for (int cadreId : cadreIds) {
                CadreView cadre = iCadreMapper.getCadre(cadreId);
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " 干部信息表(简版) " + cadre.getRealname() + ".docx";

                // 保证文件名不重复
                if(filenameSet.contains(filename)){
                    filename = cadre.getCode() + " " + filename;
                }
                filenameSet.add(filename);

                String filepath = tmpdir + FILE_SEPARATOR + filename;
                FileOutputStream output = new FileOutputStream(new File(filepath));
                //OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

                processSimple(cadreId, output);

                fileMap.put(filename, new File(filepath));
            }

            String filename = String.format("%s干部信息表(简版)",
                    CmTag.getSysConfig().getSchoolName());
            DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.zip(fileMap, filename, request, response);
            FileUtils.deleteDir(new File(tmpdir));
        }
    }

    // 支部书记
    public void exportPartyMember(Integer cadreId, int branchId, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, TemplateException {

        if (cadreId == null) return;
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        //输出文件
        String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                + " 信息采集表 " + cadre.getUser().getRealname()  + ".doc";
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
        response.setContentType("application/msword;charset=UTF-8");

        processPartyMember(cadreId, branchId, response.getWriter());
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

    public PartyMemberInfoForm getPartyMemberInfoForm (int cadreId, int branchId){

        CadreInfoForm cadreInfoForm = getCadreInfoForm(cadreId);
        PartyMemberInfoForm partyMemberInfoForm = new PartyMemberInfoForm();
        try {
            PropertyUtils.copyProperties(partyMemberInfoForm, cadreInfoForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
        int userId = cadre.getUserId();
        Branch branch = branchService.findAll().get(branchId);
        if (branch != null) {
            partyMemberInfoForm.setBranchName(branch.getName());
        }

        MetaType branchSecretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        BranchMember branchSecretary = iPartyMapper.findBranchMember(branchSecretaryType.getId(), branchId, userId);
        // 是否一线教师支部书记
        partyMemberInfoForm.setIsPrefessionalSecretary(BooleanUtils.isTrue(branch.getIsPrefessional())
                && branchSecretary != null);

        if (branchSecretary != null) {
            partyMemberInfoForm.setAssignDate(DateUtils.formatDate(branchSecretary.getAssignDate(), DateUtils.YYYYMM));
        }

        List<CadreResume> resumes = partyPostService.resume(userId);
        String resume = StringUtils.trimToNull(freemarkerService.freemarker(resumes, "cadreResumes",
                "/cadre/cadreResume.ftl"));
        partyMemberInfoForm.setResume(resume);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("partyRewards", partyRewardService.list(userId));
        dataMap.put("partyPunishes", partyPunishService.list(userId));
        String reward = StringUtils.defaultIfBlank(freemarkerService.freemarker(dataMap,
                "/party/partyReward.ftl"), "无");
        partyMemberInfoForm.setReward(reward);

        //年度考核结果
        Integer evaYears = CmTag.getIntProperty("evaYears");
        if (evaYears == null) evaYears = 3;
        Integer currentYear = DateUtils.getCurrentYear();
        List<Integer> years = new ArrayList<>();
        for (Integer i = 0; i < evaYears; i++) {
            years.add(currentYear - evaYears + i);
        }
        String evaResult = StringUtils.join(years, "、") + "年均为合格"; // 默认
        {
            Map<Integer, String> evaMap = new LinkedHashMap<>();
            PartyEvaExample example = new PartyEvaExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andYearBetween(currentYear - evaYears, currentYear);
            example.setOrderByClause("year desc");
            List<PartyEva> partyEvas = partyEvaMapper.selectByExampleWithRowbounds(example, new RowBounds(0, evaYears));
            if (partyEvas.size() > 0) {
                for (PartyEva partyEva : partyEvas) {
                    int year = partyEva.getYear();
                    int type = partyEva.getType();
                    evaMap.put(year, year + "年：" + metaTypeService.getName(type));
                }
                ArrayList<String> evaList = new ArrayList<>(evaMap.values());
                Collections.reverse(evaList);
                evaResult = StringUtils.join(evaList, "；");
            }
        }
        partyMemberInfoForm.setCes(evaResult);

        return partyMemberInfoForm;
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
                bean.getLearnDesc(), true, 440, "/common/oldTitleEditor_docx.ftl"))
                + StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("工作经历",
                bean.getWorkDesc(), true, 440, "/common/oldTitleEditor_docx.ftl"));
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
                    companies += getCompanySeg(null, "/infoform/company_docx.ftl");
                else
                    companies += getCompanySeg(cadreCompanies.get(i), "/infoform/company_docx.ftl");
            }
            dataMap.put("companies", companies);
        }

        {
            String familys = "";
            List<CadreFamily> cadreFamilys = bean.getCadreFamilys();
            int size = cadreFamilys.size();
            for (int i = 0; i < 6; i++) {
                if (size <= i)
                    familys += getFamilySeg(null, "/infoform/family_docx.ftl");
                else
                    familys += getFamilySeg(cadreFamilys.get(i), "/infoform/family_docx.ftl");
            }
            dataMap.put("familys", familys);
        }

        {
            String familyAbroads = "";
            List<CadreFamilyAbroad> cadreFamilyAbroads = bean.getCadreFamilyAbroads();
            int size = cadreFamilyAbroads.size();
            for (int i = 0; i < 2; i++) {
                if (size <= i)
                    familyAbroads += getFamilyAbroadSeg(null, "/infoform/abroad_docx.ftl");
                else {
                    familyAbroads += getFamilyAbroadSeg(cadreFamilyAbroads.get(i), "/infoform/abroad_docx.ftl");
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
                familys += getFamilySeg(cadreFamilys.get(i), "/infoform/family2_docx.ftl");
            }
            for (int i = 1; i < (4-size); i++) { // 保证至少有4行
               familys += getFamilySeg(null, "/infoform/family2_docx.ftl");
            }

            dataMap.put("familys", familys);
        }

        return dataMap;
    }

    public Map<String, Object> getDataMapSimple(int cadreId) throws IOException, TemplateException {

        CadreInfoForm bean = getCadreInfoForm(cadreId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("code", bean.getCode());
        dataMap.put("name", bean.getRealname());//姓名
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));//性别
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM));//出生年月
        dataMap.put("age", bean.getAge());//年龄
        dataMap.put("avatar", bean.getAvatar());//照片

        dataMap.put("nation", bean.getNation());//民族
        dataMap.put("np", bean.getNativePlace());//籍贯
        dataMap.put("proPost", bean.getProPost());//专业技术职务

        String partyName = CmTag.getCadreParty(bean.getIsOw(), bean.getOwGrowTime(),
                bean.getOwPositiveTime(), "中共党员", bean.getDpTypeId(),
                bean.getDpGrowTime(), false).get("partyName");
        dataMap.put("partyName",partyName);//政治面貌
        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM));//参加工作时间
        dataMap.put("specialty", bean.getSpecialty());//熟悉专业有何特长

        dataMap.put("edu", bean.getEdu());//最高学历
        dataMap.put("degree", StringUtils.trimToNull(bean.getDegree()));//最高学位
        dataMap.put("sameSchool", bean.isSameSchool());//全日制教育-学历学位毕业学校是否一致
        dataMap.put("schoolDepMajor1", bean.getSchoolDepMajor1());//全日制教育-学历毕业院校系及专业
        dataMap.put("schoolDepMajor2", bean.getSchoolDepMajor2());//全日制教育-学位毕业院校系及专业

        dataMap.put("inEdu", bean.getInEdu());//在职教育-最高学历
        dataMap.put("inDegree", StringUtils.trimToNull(bean.getInDegree()));//在职教育-最高学位
        dataMap.put("sameInSchool", bean.isSameInSchool());//在职教育-学历学位毕业学校是否一致
        dataMap.put("inSchoolDepMajor1", bean.getInSchoolDepMajor1());//在职教育-学历毕业院校系及专业
        dataMap.put("inSchoolDepMajor2", bean.getInSchoolDepMajor2());//在职教育-学位毕业院校系及专业

        dataMap.put("title",bean.getTitle());//现任职务

        String resumeDesc = freemarkerService.genTitleEditorSegment(null, bean.getResumeDesc(), true, 360, "/common/titleEditor.ftl");
        dataMap.put("resumeDesc", StringUtils.trimToNull(resumeDesc));//学习经历和工作经历

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
            for (int i = 0; i < 7; i++) {
                if (size <= i)
                    familys += getFamilySeg(null, "/infoform/familySimple_docx.ftl");
                else
                    familys += getFamilySeg(cadreFamilys.get(i), "/infoform/familySimple_docx.ftl");
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

    public Map<String, Object> getDataMapOfPartyMember(int cadreId, int branchId) throws IOException, TemplateException {

        PartyMemberInfoForm bean = getPartyMemberInfoForm(cadreId, branchId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("code", bean.getCode());
        dataMap.put("name", bean.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM));
        dataMap.put("age", bean.getAge());

        dataMap.put("nativePlace", bean.getNativePlace());
        dataMap.put("homeplace", bean.getHomeplace());

        dataMap.put("avatar", bean.getAvatar());
        dataMap.put("avatarWidth", bean.getAvatarWidth());
        dataMap.put("avatarHeight", bean.getAvatarHeight());
        dataMap.put("nation", bean.getNation());
        dataMap.put("np", bean.getNativePlace());
        dataMap.put("hp", bean.getHomeplace());

        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM));

        dataMap.put("isOw", bean.getIsOw());
        dataMap.put("owGrowTime", DateUtils.formatDate(bean.getOwGrowTime(), DateUtils.YYYYMM));
        if (bean.getDpTypeId() != null && bean.getDpTypeId() > 0) {
            // 民主党派
            MetaType metaType = CmTag.getMetaType(bean.getDpTypeId());
            String dpPartyName = StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
            dataMap.put("dpPartyName", dpPartyName);
            dataMap.put("dpGrowTime", DateUtils.formatDate(bean.getDpGrowTime(), DateUtils.YYYYMM));
        }

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

        dataMap.put("title", bean.getTitle());

        dataMap.put("isPrefessionalSecretary", bean.getIsPrefessionalSecretary());
        dataMap.put("branchName", bean.getBranchName());
        dataMap.put("assignDate", bean.getAssignDate());
        dataMap.put("post", bean.getPost());

        dataMap.put("resumeDesc", freemarkerService.genTitleEditorSegment(bean.getResume(), true, false, 440));
        dataMap.put("rewardDesc", freemarkerService.genTitleEditorSegment(bean.getReward(), true, false, 440));
        dataMap.put("ces", bean.getCes());

        dataMap.put("mobile", bean.getMobile());

        return dataMap;
    }

    // 输出干部信息采集表
    public void process(int cadreId, OutputStream outputStream/* Writer out*/) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMap(cadreId);
        dataMap.put("fillDate", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));

        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        dataMap.put("schoolEmail", CmTag.getStringProperty("zzb_email"));

        CadreInfoForm adform = cadreAdformService.getCadreAdform(cadreId);
        String content = freemarkerService.process("/infoform/infoform_docx.ftl",dataMap);
        cadreAdformService.exportDocxUtils("classpath:ftl/infoform/infoform.docx",content,adform.getAvatar(),outputStream);

        //OutputStreamWriter osw = new OutputStreamWriter(outputStream, "utf-8");
        //freemarkerService.process("/infoform/infoform.ftl", dataMap,osw);
    }

    // 输出干部信息表
    public void process2(int cadreId, OutputStream outputStream/*Writer out*/) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMap2(cadreId);

        CadreInfoForm adform = cadreAdformService.getCadreAdform(cadreId);
        String content = freemarkerService.process("/infoform/infoform2_docx.ftl",dataMap);
       cadreAdformService.exportDocxUtils("classpath:ftl/infoform/infoform2.docx",content,adform.getAvatar(),outputStream);

        //OutputStreamWriter osw = new OutputStreamWriter(outputStream, "utf-8");
        //freemarkerService.process("/infoform/infoform2.ftl", dataMap, osw);
    }

    //输出干部信息表(简版)
    public void processSimple(int cadreId, OutputStream outputStream) throws IOException, TemplateException {

        Map<String,Object> dataMap = getDataMapSimple(cadreId);

        CadreInfoForm adform = cadreAdformService.getCadreAdform(cadreId);
        String content = freemarkerService.process("/infoform/infoformSimple_docx.ftl",dataMap);
        cadreAdformService.exportDocxUtils("classpath:ftl/infoform/infoformSimple.docx",content,adform.getAvatar(),outputStream);

        //OutputStreamWriter osw = new OutputStreamWriter(outputStream, "utf-8");
        //freemarkerService.process("/infoform/infoformSimple.ftl",dataMap,osw);

    }

    // 输出支部书记信息采集表
    public void processPartyMember(int cadreId, int branchId, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMapOfPartyMember(cadreId, branchId);

        freemarkerService.process("/party/partyMemberInfoForm.ftl", dataMap, out);
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
