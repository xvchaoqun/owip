package service.member;

import bean.CadreResume;
import bean.MemberInfoForm;
import domain.base.MetaType;
import domain.cadre.CadreEdu;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.party.*;
import domain.sys.SysUser;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.*;
import service.common.FreemarkerService;
import service.global.CacheHelper;
import service.party.*;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@Controller
public class MemberInfoFormService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected CacheHelper cacheHelper;
    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected PartyPostService partyPostService;
    @Autowired
    protected PartyRewardService partyRewardService;
    @Autowired
    protected PartyPunishService partyPunishService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected CadreAdformService cadreAdformService;
    @Autowired
    protected CadreEduService cadreEduService;
    @Autowired
    protected CadreWorkService cadreWorkService;
    @Autowired
    protected CadreParttimeService cadreParttimeService;
    @Autowired
    protected CadreTrainService cadreTrainService;
    @Autowired
    protected CadreCourseService cadreCourseService;
    @Autowired
    protected CadreRewardService cadreRewardService;
    @Autowired
    protected CadreBookService cadreBookService;
    @Autowired
    protected CadreResearchService cadreResearchService;
    @Autowired
    protected CadrePaperService cadrePaperService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected CadreInfoService cadreInfoService;

    //党员信息采集表
    public MemberInfoForm getMemberInfoForm(Integer cadreId, Integer userId){

        MemberInfoForm memberInfoForm = new MemberInfoForm();
        CadreView cadre = new CadreView();
        if (cadreId != null){
            cadre = iCadreMapper.getCadre(cadreId);
            userId = cadre.getUserId();
            memberInfoForm.setCadre(cadreId != null);
        }
        SysUserView uv = CmTag.getUserById(userId);
        Member member = memberService.get(userId);
        Branch branch = branchService.findAll().get(member.getBranchId());
        Party party = partyMapper.selectByPrimaryKey(member.getPartyId());

        String _resume = null;

        if (uv.isTeacher()){
            TeacherInfo teacherInfo = teacherInfoService.get(userId);
            memberInfoForm.setWorkTime(teacherInfo.getWorkTime());
            memberInfoForm.setProPost(teacherInfo.getProPost());

        }
        memberInfoForm.setRealname(CmTag.realnameWithEmpty(uv.getRealname()));
        memberInfoForm.setGender(uv.getGender());
        memberInfoForm.setBirth(uv.getBirth());
        memberInfoForm.setAge(DateUtils.intervalYearsUntilNow(uv.getBirth()));
        memberInfoForm.setNation(uv.getNation());
        memberInfoForm.setNativePlace(uv.getNativePlace());
        memberInfoForm.setCode(uv.getCode());
        memberInfoForm.setGrowTime(member.getGrowTime());

        MetaType branchSecretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
        BranchMember branchSecretary = iPartyMapper.findBranchMember(branchSecretaryType.getId(), branch.getId(), userId);
        memberInfoForm.setBranchSecretary(branchSecretary != null);
        // 是否一线教师支部书记
        memberInfoForm.setPrefessionalSecretary(BooleanUtils.isTrue(branch.getIsPrefessional())
                && branchSecretary != null);
        File avatar = new File(springProps.avatarFolder + uv.getAvatar());
        if (!avatar.exists()) avatar = new File(ConfigUtil.defaultHomePath()
                + FILE_SEPARATOR + "img" + FILE_SEPARATOR + "default.png");

        // 头像默认大小
        memberInfoForm.setAvatarWidth(143);
        memberInfoForm.setAvatarHeight(198);
        try {
            BufferedImage _avatar = ImageIO.read(avatar);
            memberInfoForm.setAvatarWidth(_avatar.getWidth());
            memberInfoForm.setAvatarHeight(_avatar.getHeight());
        } catch (IOException e) {
            logger.error("异常", e);
        }
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        memberInfoForm.setAvatar(base64);

        List<String> types = iPartyMapper.findIsMember(userId);
        memberInfoForm.setMember(types.size() > 0);
        if (types.size() >= 0){
            PartyMemberExample partyMemberExample = new PartyMemberExample();
            partyMemberExample.createCriteria().andUserIdEqualTo(userId);
            List<PartyMember> partyMembers = partyMemberMapper.selectByExample(partyMemberExample);
            BranchMemberExample branchMemberExample = new BranchMemberExample();
            branchMemberExample.createCriteria().andUserIdEqualTo(userId);
            List<BranchMember> branchMembers = branchMemberMapper.selectByExample(branchMemberExample);

            String pmAssignDate = "";
            String bmAssignDate = "";

            if (partyMembers.size() + branchMembers.size() == 0){
                memberInfoForm.setPmAssignDate("");
                memberInfoForm.setBmAssignDate("无");
            }else if (partyMembers.size() + branchMembers.size() == 1){
                if (partyMembers.size() == 1){
                   /* MetaType metaType = CmTag.getMetaType(partyMembers.get(0).getPostId());*/
                    pmAssignDate = DateUtils.formatDate(partyMembers.get(0).getAssignDate(), DateUtils.YYYYMM);
                    memberInfoForm.setPmAssignDate(pmAssignDate);
                }else {
                   /* MetaType metaType = CmTag.getMetaType(branchMembers.get(0).getTypeId());*/
                    bmAssignDate = DateUtils.formatDate(branchMembers.get(0).getAssignDate(), DateUtils.YYYYMM);
                    memberInfoForm.setBmAssignDate(bmAssignDate);
                }
            } else if (partyMembers.size() + branchMembers.size() > 1) {
                for (PartyMember partyMember : partyMembers) {
                    MetaType metaType = CmTag.getMetaType(partyMember.getPostId());
                    pmAssignDate += String.format("%s(%s)", metaType.getName(), DateUtils.formatDate(partyMember.getAssignDate(), DateUtils.YYYYMM) == null ? "--" : DateUtils.formatDate(partyMember.getAssignDate(), DateUtils.YYYYMM));
                }
                memberInfoForm.setPmAssignDate(StringUtils.trimToNull(pmAssignDate));

                for (BranchMember branchMember : branchMembers) {
                    Set<Integer> typeIds = NumberUtils.toIntSet(branchMember.getTypes(), ",");
                    List<String> typeNames = new ArrayList<>();
                    for (Integer typeId : typeIds) {
                        typeNames.add(CmTag.getMetaTypeName(Integer.valueOf(typeId)));
                    }

                    bmAssignDate += String.format("%s(%s)", StringUtils.join(typeNames,","), DateUtils.formatDate(branchMember.getAssignDate(), DateUtils.YYYYMM) == null ? "--" : DateUtils.formatDate(branchMember.getAssignDate(), DateUtils.YYYYMM));
                }
                memberInfoForm.setBmAssignDate(StringUtils.trimToNull(bmAssignDate));
            }

        }

        memberInfoForm.setPartyName(String.format("%s-%s", party.getName(), branch.getShortName()!=null?branch.getName():branch.getShortName()));

        memberInfoForm.setMobile(uv.getMobile());
        memberInfoForm.setPost(uv.getPost());

        if (resume(userId).size() > 0){
            _resume = StringUtils.trimToNull(freemarkerService.freemarker(resume(userId),
                    "cadreResumes", "/cadre/cadreResume.ftl"));
        }
        memberInfoForm.setResumeDesc(_resume);

        //党内奖惩
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("partyRewards", partyRewardService.list(userId));
        dataMap.put("partyPunishes", partyPunishService.list(userId));
        String reward = StringUtils.defaultIfBlank(freemarkerService.freemarker(dataMap,
                "/party/partyReward.ftl"), "无");
        memberInfoForm.setReward(reward);

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
        memberInfoForm.setCes(evaResult);

        String _fulltimeEdu = ""; // 全日制最高学历
        String _fulltimeDegree = ""; // 全日制最高学位
        String _onjobEdu = ""; // 在职最高学历
        String _onjobDegree = ""; // 在职最高学位

        if (cadreId != null) {
            MetaType fullltimeType = CmTag.getMetaTypeByCode("mt_fulltime");
            List<CadreEdu> fulltimeHighDegrees = cadreEduService.getHighDegrees(cadreId, fullltimeType.getId());

            if (fulltimeHighDegrees.size() > 1) { // 有多个学位的情况（只处理前两个学位）

                CadreEdu firstHighDegree = fulltimeHighDegrees.get(0);
                CadreEdu secondHighDegree = fulltimeHighDegrees.get(1);

                if (!isJxxx(firstHighDegree.getEduId())) { // 把第一个最高学位对应的学位和学历放在第一行
                    _fulltimeEdu = StringUtils.trimToEmpty(CmTag.getEduName(firstHighDegree.getEduId()))
                            + StringUtils.trimToEmpty(firstHighDegree.getDegree());
                }
                if (!isJxxx(secondHighDegree.getEduId())) { // 把第二个最高学位对应的学位和学历放在第二行
                    _fulltimeDegree = StringUtils.trimToEmpty(CmTag.getEduName(secondHighDegree.getEduId()))
                            + StringUtils.trimToEmpty(secondHighDegree.getDegree());
                }

                memberInfoForm.setSameSchool(false);
                memberInfoForm.setSchoolDepMajor1(StringUtils.trimToEmpty(firstHighDegree.getSchool())
                        + StringUtils.trimToEmpty(firstHighDegree.getDep())
                        + StringUtils.trimToEmpty(CadreUtils.major(firstHighDegree.getMajor())));
                memberInfoForm.setSchoolDepMajor2(StringUtils.trimToEmpty(secondHighDegree.getSchool())
                        + StringUtils.trimToEmpty(secondHighDegree.getDep())
                        + StringUtils.trimToEmpty(CadreUtils.major(secondHighDegree.getMajor())));

            } else if (fulltimeHighDegrees.size() == 1) { // 只有一个学位的情况

                CadreEdu fulltimeHighDegree = fulltimeHighDegrees.get(0);
                CadreEdu fulltimeHighEdu = cadreEduService.getHighEdu(cadreId, fullltimeType.getId());
                if (fulltimeHighEdu != null && !isJxxx(fulltimeHighEdu.getEduId())) {
                    _fulltimeEdu = CmTag.getEduName(fulltimeHighEdu.getEduId());
                }

                if (fulltimeHighDegree != null && !isJxxx(fulltimeHighDegree.getEduId())) {
                    _fulltimeDegree = fulltimeHighDegree.getDegree();
                }
                // 全日制 - 根据学历、学位对应的毕业院系是否相同，读取 “毕业院系及专业”
                if (fulltimeHighEdu != null && fulltimeHighDegree != null) {
                    if (fulltimeHighEdu.getId().intValue() == fulltimeHighDegree.getId()) {
                        // 最高学历和学位毕业学校及专业相同
                        memberInfoForm.setSameSchool(true);
                        memberInfoForm.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                                + StringUtils.trimToEmpty(fulltimeHighEdu.getDep()));
                        memberInfoForm.setSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                    } else {
                        memberInfoForm.setSameSchool(false);
                        memberInfoForm.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                                + StringUtils.trimToEmpty(fulltimeHighEdu.getDep())
                                + StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                        memberInfoForm.setSchoolDepMajor2(StringUtils.trimToEmpty(fulltimeHighDegree.getSchool())
                                + StringUtils.trimToEmpty(fulltimeHighDegree.getDep())
                                + StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighDegree.getMajor())));
                    }
                } else if (fulltimeHighEdu != null) {

                    memberInfoForm.setSameSchool(true);
                    memberInfoForm.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                            + StringUtils.trimToEmpty(fulltimeHighEdu.getDep()));
                    memberInfoForm.setSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                } else if (fulltimeHighDegree != null) {

                    memberInfoForm.setSameSchool(true);
                    memberInfoForm.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighDegree.getSchool())
                            + StringUtils.trimToEmpty(fulltimeHighDegree.getDep()));
                    memberInfoForm.setSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighDegree.getMajor())));
                }
            } else { // 没有学位的情况

                CadreEdu fulltimeHighEdu = cadreEduService.getHighEdu(cadreId, fullltimeType.getId());
                if (fulltimeHighEdu != null && !isJxxx(fulltimeHighEdu.getEduId())) {
                    _fulltimeEdu = CmTag.getEduName(fulltimeHighEdu.getEduId());

                    memberInfoForm.setSameSchool(true);
                    memberInfoForm.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                            + StringUtils.trimToEmpty(fulltimeHighEdu.getDep()));
                    memberInfoForm.setSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                }
            }

            MetaType onjobType = CmTag.getMetaTypeByCode("mt_onjob");
            List<CadreEdu> onjobHighDegrees = cadreEduService.getHighDegrees(cadreId, onjobType.getId());

            if (onjobHighDegrees.size() > 1) { // 有多个学位的情况（只处理前两个学位）

                CadreEdu firstHighDegree = onjobHighDegrees.get(0);
                CadreEdu secondHighDegree = onjobHighDegrees.get(1);

                if (!isJxxx(firstHighDegree.getEduId())) { // 把第一个最高学位对应的学位和学历放在第一行
                    _onjobEdu = StringUtils.trimToEmpty(CmTag.getEduName(firstHighDegree.getEduId()))
                            + StringUtils.trimToEmpty(firstHighDegree.getDegree());
                }
                if (!isJxxx(secondHighDegree.getEduId())) { // 把第二个最高学位对应的学位和学历放在第二行
                    _onjobDegree = StringUtils.trimToEmpty(CmTag.getEduName(secondHighDegree.getEduId()))
                            + StringUtils.trimToEmpty(secondHighDegree.getDegree());
                }

                memberInfoForm.setSameInSchool(false);
                memberInfoForm.setInSchoolDepMajor1(StringUtils.trimToEmpty(firstHighDegree.getSchool())
                        + StringUtils.trimToEmpty(firstHighDegree.getDep())
                        + StringUtils.trimToEmpty(CadreUtils.major(firstHighDegree.getMajor())));
                memberInfoForm.setInSchoolDepMajor2(StringUtils.trimToEmpty(secondHighDegree.getSchool())
                        + StringUtils.trimToEmpty(secondHighDegree.getDep())
                        + StringUtils.trimToEmpty(CadreUtils.major(secondHighDegree.getMajor())));

            } else if (onjobHighDegrees.size() == 1) { // 只有一个学位的情况

                CadreEdu onjobHighDegree = onjobHighDegrees.get(0);
                CadreEdu onjobHighEdu = cadreEduService.getHighEdu(cadreId, onjobType.getId());
                if (onjobHighEdu != null && !isJxxx(onjobHighEdu.getEduId())) {
                    _onjobEdu = CmTag.getEduName(onjobHighEdu.getEduId());
                }

                if (onjobHighDegree != null && !isJxxx(onjobHighDegree.getEduId())) {
                    _onjobDegree = onjobHighDegree.getDegree();
                }
                // 在职 - 根据学历、学位对应的毕业院系是否相同，读取 “毕业院系及专业”
                if (onjobHighEdu != null && onjobHighDegree != null) {
                    if (onjobHighEdu.getId().intValue() == onjobHighDegree.getId()) {
                        // 最高学历和学位毕业学校及专业相同
                        memberInfoForm.setSameInSchool(true);
                        memberInfoForm.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                                + StringUtils.trimToEmpty(onjobHighEdu.getDep()));
                        memberInfoForm.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                    } else {
                        memberInfoForm.setSameInSchool(false);
                        memberInfoForm.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                                + StringUtils.trimToEmpty(onjobHighEdu.getDep())
                                + StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                        memberInfoForm.setInSchoolDepMajor2(StringUtils.trimToEmpty(onjobHighDegree.getSchool())
                                + StringUtils.trimToEmpty(onjobHighDegree.getDep())
                                + StringUtils.trimToEmpty(CadreUtils.major(onjobHighDegree.getMajor())));
                    }
                } else if (onjobHighEdu != null) {

                    memberInfoForm.setSameInSchool(true);
                    memberInfoForm.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                            + StringUtils.trimToEmpty(onjobHighEdu.getDep()));
                    memberInfoForm.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                } else if (onjobHighDegree != null) {

                    memberInfoForm.setSameInSchool(true);
                    memberInfoForm.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighDegree.getSchool())
                            + StringUtils.trimToEmpty(onjobHighDegree.getDep()));
                    memberInfoForm.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighDegree.getMajor())));
                }
            } else { // 没有学位的情况

                CadreEdu onjobHighEdu = cadreEduService.getHighEdu(cadreId, onjobType.getId());
                if (onjobHighEdu != null && !isJxxx(onjobHighEdu.getEduId())) {
                    _onjobEdu = CmTag.getEduName(onjobHighEdu.getEduId());

                    memberInfoForm.setSameInSchool(true);
                    memberInfoForm.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                            + StringUtils.trimToEmpty(onjobHighEdu.getDep()));
                    memberInfoForm.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                }
            }
        }

        // 全日制最高学历
        memberInfoForm.setEdu(_fulltimeEdu);
        memberInfoForm.setDegree(_fulltimeDegree);

        // 在职最高学历
        memberInfoForm.setInEdu(_onjobEdu);
        memberInfoForm.setInDegree(_onjobDegree);

        return memberInfoForm;
    }

    // 判断是否是进修学习，进修学习不能进入任免审批表
    public boolean isJxxx(Integer eduId) {

        MetaType jxxx = CmTag.getMetaTypeByCode("mt_edu_jxxx");
        return (jxxx != null && eduId != null && jxxx.getId() == eduId);
    }

    //简历
    public List<CadreResume> resume(Integer userId){

        List<CadreResume> resumes = new ArrayList<>();
        List<PartyPost> partyPosts = null;

        PartyPostExample example = new PartyPostExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("start_date asc");
        partyPosts = partyPostMapper.selectByExample(example);

        for (PartyPost partyPost : partyPosts){
            CadreResume resume = new CadreResume();
            resume.setIsWork(partyPost.getEndDate() == null);
            resume.setStartDate(partyPost.getStartDate());
            resume.setEndDate(partyPost.getEndDate());
            resume.setDetail(String.format("%s(%s)",partyPost.getDetail(),partyPost.getRemark()));

            resumes.add(resume);
        }

        return resumes;
    }

    // 党员信息采集表
    public void exportPartyMember(Integer cadreId, Integer userId, HttpServletRequest request,
                                  HttpServletResponse response) throws IOException, TemplateException {

        SysUserView uv = CmTag.getUserById(userId);
        //输出文件
        String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                + " 信息采集表 " + uv.getRealname()  + ".doc";
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
        response.setContentType("application/msword;charset=UTF-8");

        processPartyMember(cadreId, userId, response.getWriter());
    }

    // 输出党员信息采集表
    public void processPartyMember(Integer cadreId, Integer userId, Writer out) throws IOException, TemplateException {


        Map<String, Object> dataMap = getDataMapOfPartyMember(cadreId, userId);

        freemarkerService.process("/party/memberInfoForm.ftl", dataMap, out);
    }

    public Map<String, Object> getDataMapOfPartyMember(Integer cadreId, Integer userId) throws IOException, TemplateException {

        SysUserView uv = CmTag.getUserById(userId);
        int cls = uv.isTeacher()?1:2;

        MemberInfoForm bean = getMemberInfoForm(cadreId, userId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("code", bean.getCode());
        dataMap.put("name", bean.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM));
        dataMap.put("age", bean.getAge());

        dataMap.put("nativePlace", bean.getNativePlace());

        dataMap.put("avatar", bean.getAvatar());
        dataMap.put("avatarWidth", bean.getAvatarWidth());
        dataMap.put("avatarHeight", bean.getAvatarHeight());
        dataMap.put("nation", bean.getNation());

        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM));

        // 专业技术职务
        dataMap.put("proPost", bean.getProPost());

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

        dataMap.put("post", bean.getPost());
        dataMap.put("mobile", bean.getMobile());

        dataMap.put("isPrefessionalSecretary", bean.getPrefessionalSecretary());
        dataMap.put("isBranchSecretary", bean.getBranchSecretary());
        dataMap.put("isMember", bean.getMember());

        dataMap.put("resumeDesc", freemarkerService.genTitleEditorSegment(bean.getResumeDesc(), true, false, 440));
        dataMap.put("reward", freemarkerService.genTitleEditorSegment(bean.getReward(), true, false, 440));
        dataMap.put("ces", bean.getCes());

        dataMap.put("partyName", bean.getPartyName());
        dataMap.put("pmAssignDate", bean.getPmAssignDate());
        dataMap.put("bmAssignDate", bean.getBmAssignDate());
        dataMap.put("growTime", DateUtils.formatDate(bean.getGrowTime(), DateUtils.YYYYMM));
        dataMap.put("cls", cls);

        return dataMap;
    }

    @Transactional
    public void update(Integer userId, String mobile, String post){
        SysUserInfo uv = sysUserInfoMapper.selectByPrimaryKey(userId);
        uv.setMobile(mobile);
        uv.setPost(post);

        sysUserInfoMapper.updateByPrimaryKeySelective(uv);

        SysUser _sysUser = sysUserService.dbFindById(userId);
        cacheHelper.clearUserCache(_sysUser);
    }

}
