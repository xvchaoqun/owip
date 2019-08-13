package service.cadre;

import bean.CadreInfoForm;
import bean.ResumeRow;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.*;
import domain.sys.*;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.HtmlUtils;
import service.BaseMapper;
import service.SpringProps;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.global.CacheHelper;
import service.party.MemberService;
import service.sys.AvatarService;
import service.sys.SysConfigService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fafa on 2016/10/28.
 */
@Service
public class CadreAdformService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

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
    protected CadreService cadreService;
    @Autowired
    protected CadrePartyService cadrePartyService;
    @Autowired
    protected CadreEvaService cadreEvaService;
    @Autowired
    protected CadreInfoService cadreInfoService;
    @Autowired
    protected CadrePostService cadrePostService;
    @Autowired
    protected CadreWorkService cadreWorkService;
    @Autowired
    protected CadreRewardService cadreRewardService;
    @Autowired
    protected CadreFamilyService cadreFamilyService;
    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    protected AvatarService avatarService;
    @Autowired
    protected SysConfigService sysConfigService;

    public void export(Integer[] cadreIds,
                       boolean isWord, // 否：中组部格式
                       Byte adFormType,
                       HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, DocumentException {

        if (cadreIds == null) return;

        if (cadreIds.length == 1) {

            int cadreId = cadreIds[0];
            CadreView cadre = iCadreMapper.getCadre(cadreId);

            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            if (isWord) {
                //输出WORD任免审批表
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " 干部任免审批表 " + cadre.getUser().getRealname() + ".doc";

                response.setHeader("Content-Disposition",
                        "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
                response.setContentType("application/msword;charset=UTF-8");

                CadreInfoForm adform = getCadreAdform(cadreId);
                process(adform, adFormType, response.getWriter());
            } else {
                // 输出中组部任免审批表
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd") + " 干部任免审批表 " + cadre.getRealname();

                response.setHeader("Content-Disposition",
                        "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".lrmx"));
                response.setContentType("text/xml;charset=UTF-8");

                CadreInfoForm adform = getCadreAdform(cadreId);
                zzb(adform, response.getWriter());
            }
        } else {

            Map<String, File> fileMap = new LinkedHashMap<>();
            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "adforms";
            FileUtils.mkdirs(tmpdir, false);

            Set<String> filenameSet = new HashSet<>();
            for (int cadreId : cadreIds) {
                CadreView cadre = iCadreMapper.getCadre(cadreId);
                String filename = null;
                String filepath = null;
                if (isWord) {
                    filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                            + " 干部任免审批表 " + cadre.getRealname() + ".doc";

                    // 保证文件名不重复
                    if (filenameSet.contains(filename)) {
                        filename = cadre.getCode() + " " + filename;
                    }
                    filenameSet.add(filename);

                    filepath = tmpdir + FILE_SEPARATOR + filename;
                    FileOutputStream output = new FileOutputStream(new File(filepath));
                    OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

                    CadreInfoForm adform = getCadreAdform(cadreId);
                    process(adform, adFormType, osw);
                } else {
                    filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                            + " 干部任免审批表 " + cadre.getRealname() + ".lrmx";

                    // 保证文件名不重复
                    if (filenameSet.contains(filename)) {
                        filename = cadre.getCode() + " " + filename;
                    }
                    filenameSet.add(filename);

                    filepath = tmpdir + FILE_SEPARATOR + filename;
                    FileOutputStream output = new FileOutputStream(new File(filepath));
                    OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");
                    CadreInfoForm adform = getCadreAdform(cadreId);

                    zzb(adform, osw);
                }

                fileMap.put(filename, new File(filepath));
            }

            String filename = String.format("%s干部任免审批表",
                    CmTag.getSysConfig().getSchoolName());
            DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.zip(fileMap, filename, request, response);
            FileUtils.deleteDir(new File(tmpdir));
        }
    }

    public void export(Integer[] cadreIds,
                       boolean isWord, // 否：中组部格式
                       HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, DocumentException {

        export(cadreIds, isWord, null, request, response);
    }

    // 判断是否是进修学习，进修学习不能进入任免审批表
    public boolean isJxxx(Integer eduId) {

        MetaType jxxx = CmTag.getMetaTypeByCode("mt_edu_jxxx");
        return (jxxx != null && eduId != null && jxxx.getId() == eduId);
    }

    // 获取任免审批表属性值
    public CadreInfoForm getCadreAdform(int cadreId) {

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView uv = cadre.getUser();

        CadreInfoForm bean = new CadreInfoForm();
        bean.setCadreId(cadreId);
        bean.setRealname(CmTag.realnameWithEmpty(uv.getRealname()));
        bean.setGender(uv.getGender());
        bean.setIdCard(uv.getIdcard());
        bean.setBirth(cadre.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(cadre.getBirth()));

        File avatar = new File(springProps.avatarFolder + uv.getAvatar());
        if (!avatar.exists()) avatar = new File(ConfigUtil.defaultHomePath()
                + FILE_SEPARATOR + "img" + FILE_SEPARATOR + "default.png");

        // 头像默认大小
        bean.setAvatarWidth(143);
        bean.setAvatarHeight(198);
        try {
            BufferedImage _avatar = ImageIO.read(avatar);
            bean.setAvatarWidth(_avatar.getWidth());
            bean.setAvatarHeight(_avatar.getHeight());
        } catch (IOException e) {
            logger.error("异常", e);
        }
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        bean.setAvatar(base64);
        bean.setNation(cadre.getNation());
        bean.setNativePlace(cadre.getNativePlace());

        bean.setHomeplace(uv.getHomeplace());
        bean.setWorkTime(cadre.getWorkTime()); // 参加工作时间

        bean.setHealth(metaTypeService.getName(uv.getHealth()));
        // 专业技术职务及评定时间
        bean.setProPost(cadre.getProPost());
        bean.setProPostTime(cadre.getProPostTime());
        bean.setSpecialty(uv.getSpecialty());

        bean.setDpTypeId(cadre.getDpTypeId());
        bean.setDpGrowTime(cadre.getDpGrowTime());
        bean.setDpParties(cadrePartyService.getDpParties(cadre.getUserId()));

        bean.setIsOw(cadre.getIsOw());
        bean.setOwGrowTime(cadre.getOwGrowTime());

        String _fulltimeEdu = ""; // 全日制最高学历
        String _fulltimeDegree = ""; // 全日制最高学位
        String _onjobEdu = ""; // 在职最高学历
        String _onjobDegree = ""; // 在职最高学位

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

            bean.setSameSchool(false);
            bean.setSchoolDepMajor1(StringUtils.trimToEmpty(firstHighDegree.getSchool())
                    + StringUtils.trimToEmpty(firstHighDegree.getDep())
                    + StringUtils.trimToEmpty(CadreUtils.major(firstHighDegree.getMajor())));
            bean.setSchoolDepMajor2(StringUtils.trimToEmpty(secondHighDegree.getSchool())
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
                    bean.setSameSchool(true);
                    bean.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                            + StringUtils.trimToEmpty(fulltimeHighEdu.getDep()));
                    bean.setSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                } else {
                    bean.setSameSchool(false);
                    bean.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                            + StringUtils.trimToEmpty(fulltimeHighEdu.getDep())
                            + StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                    bean.setSchoolDepMajor2(StringUtils.trimToEmpty(fulltimeHighDegree.getSchool())
                            + StringUtils.trimToEmpty(fulltimeHighDegree.getDep())
                            + StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighDegree.getMajor())));
                }
            } else if (fulltimeHighEdu != null) {

                bean.setSameSchool(true);
                bean.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                        + StringUtils.trimToEmpty(fulltimeHighEdu.getDep()));
                bean.setSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
            } else if (fulltimeHighDegree != null) {

                bean.setSameSchool(true);
                bean.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighDegree.getSchool())
                        + StringUtils.trimToEmpty(fulltimeHighDegree.getDep()));
                bean.setSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighDegree.getMajor())));
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

            bean.setSameInSchool(false);
            bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(firstHighDegree.getSchool())
                    + StringUtils.trimToEmpty(firstHighDegree.getDep())
                    + StringUtils.trimToEmpty(CadreUtils.major(firstHighDegree.getMajor())));
            bean.setInSchoolDepMajor2(StringUtils.trimToEmpty(secondHighDegree.getSchool())
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
                    bean.setSameInSchool(true);
                    bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                            + StringUtils.trimToEmpty(onjobHighEdu.getDep()));
                    bean.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                } else {
                    bean.setSameInSchool(false);
                    bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                            + StringUtils.trimToEmpty(onjobHighEdu.getDep())
                            + StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                    bean.setInSchoolDepMajor2(StringUtils.trimToEmpty(onjobHighDegree.getSchool())
                            + StringUtils.trimToEmpty(onjobHighDegree.getDep())
                            + StringUtils.trimToEmpty(CadreUtils.major(onjobHighDegree.getMajor())));
                }
            } else if (onjobHighEdu != null) {

                bean.setSameInSchool(true);
                bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                        + StringUtils.trimToEmpty(onjobHighEdu.getDep()));
                bean.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
            } else if (onjobHighDegree != null) {

                bean.setSameInSchool(true);
                bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighDegree.getSchool())
                        + StringUtils.trimToEmpty(onjobHighDegree.getDep()));
                bean.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighDegree.getMajor())));
            }
        }

        // 全日制最高学历
        bean.setEdu(_fulltimeEdu);
        bean.setDegree(_fulltimeDegree);

        // 在职最高学历
        bean.setInEdu(_onjobEdu);
        bean.setInDegree(_onjobDegree);

        bean.setTitle(cadre.getTitle());
        // 主职,现任职务
        /*CadrePost mainCadrePost = cadrePostService.getCadreMainCadrePost(cadreId);
        bean.setPost(mainCadrePost==null?null:springProps.school + mainCadrePost.getPost());*/
        // 现任职务
        String schoolName = sysConfigService.getSchoolName();
        if (!StringUtils.startsWith(cadre.getTitle(), schoolName)) {
            bean.setPost(schoolName + StringUtils.trimToEmpty(cadre.getTitle()));
        } else {
            bean.setPost(cadre.getTitle());
        }

        // 学习经历
        bean.setLearnDesc(cadreInfoService.getTrimContent(cadreId, CadreConstants.CADRE_INFO_TYPE_EDU));

        // 奖惩情况
        String _reward = cadreInfoService.getTrimContent(cadreId, CadreConstants.CADRE_INFO_TYPE_REWARD);
        if (StringUtils.isBlank(_reward)) {
            _reward = freemarkerService.freemarker(cadreRewardService.list(cadreId),
                    "cadreRewards", "/cadre/cadreReward.ftl");
        }
        bean.setReward(StringUtils.defaultIfBlank(_reward, "无"));

        // 工作经历
        bean.setWorkDesc(cadreInfoService.getTrimContent(cadreId, CadreConstants.CADRE_INFO_TYPE_WORK));

        // 简历
        String _resume = cadreInfoService.getTrimContent(cadreId, CadreConstants.CADRE_INFO_TYPE_RESUME);
        if (StringUtils.isBlank(_resume)) {
            _resume = freemarkerService.freemarker(cadreWorkService.resume(bean.getCadreId()),
                    "cadreResumes", "/cadre/cadreResume.ftl");
        }
        bean.setResumeDesc(StringUtils.trimToNull(_resume));

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
            CadreEvaExample example = new CadreEvaExample();
            example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andYearBetween(currentYear - evaYears, currentYear);
            example.setOrderByClause("year desc");
            List<CadreEva> cadreEvas = cadreEvaMapper.selectByExampleWithRowbounds(example, new RowBounds(0, evaYears));
            if (cadreEvas.size() > 0) {
                for (CadreEva cadreEva : cadreEvas) {
                    int year = cadreEva.getYear();
                    int type = cadreEva.getType();
                    evaMap.put(year, year + "年：" + metaTypeService.getName(type));
                }
                ArrayList<String> evaList = new ArrayList<>(evaMap.values());
                Collections.reverse(evaList);
                evaResult = StringUtils.join(evaList, "；");
            }
        }

        bean.setCes(evaResult);

        // 培训情况
        bean.setTrainDesc(cadreInfoService.getTrimContent(cadreId, CadreConstants.CADRE_INFO_TYPE_TRAIN));

        // 社会关系
        CadreFamilyExample example = new CadreFamilyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("sort_order asc");

        int maxFamilyCount = 0;
        byte adFormType = CmTag.getByteProperty("adFormType",
                CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG);

        if(adFormType == CadreConstants.CADRE_ADFORMTYPE_BJ){
            maxFamilyCount = 5; // 6?
        }else if (adFormType == CadreConstants.CADRE_ADFORMTYPE_ZZB_GB2312
                || adFormType == CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG) {
            maxFamilyCount = 7;
        }
        List<CadreFamily> cadreFamilys = cadreFamilyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, maxFamilyCount));
        bean.setCadreFamilys(cadreFamilys);

        // 呈报日程默认当天
        bean.setReportDate(new Date());

        return bean;
    }

    // 输出任免审批表
    public void process(CadreInfoForm bean, Byte adFormType, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("name", bean.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), DateUtils.YYYYMM));
        dataMap.put("age", bean.getAge());
        dataMap.put("avatar", bean.getAvatar());
        dataMap.put("avatarWidth", bean.getAvatarWidth());
        dataMap.put("avatarHeight", bean.getAvatarHeight());
        dataMap.put("nation", bean.getNation());
        dataMap.put("nativePlace", bean.getNativePlace());
        dataMap.put("homeplace", bean.getHomeplace());

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
            if (dpParties.size() > 0) {
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
        dataMap.put("proPost", bean.getProPost());
        dataMap.put("specialty", bean.getSpecialty());

        dataMap.put("edu", bean.getEdu());
        dataMap.put("degree", bean.getDegree());
        dataMap.put("inEdu", bean.getInEdu());
        dataMap.put("inDegree", bean.getInDegree());

        dataMap.put("sameSchool", bean.isSameSchool());
        dataMap.put("schoolDepMajor1", bean.getSchoolDepMajor1());
        dataMap.put("schoolDepMajor2", bean.getSchoolDepMajor2());

        dataMap.put("sameInSchool", bean.isSameInSchool());
        dataMap.put("inSchoolDepMajor1", bean.getInSchoolDepMajor1());
        dataMap.put("inSchoolDepMajor2", bean.getInSchoolDepMajor2());


        dataMap.put("post", bean.getPost());
        dataMap.put("inPost", bean.getInPost());
        dataMap.put("prePost", bean.getPrePost());

        int maxFamilyCount = 0;
        String adFormFtl = null;
        String titleEditorFtl = null;
        String rewardFtl = null;
        String familyFtl = null;
        if (adFormType == null) {
            adFormType = CmTag.getByteProperty("adFormType",
                    CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG);
        }
        if (adFormType == CadreConstants.CADRE_ADFORMTYPE_BJ) {

            maxFamilyCount = 5;
            adFormFtl = "/adform/adform.ftl";
            titleEditorFtl = "/common/titleEditor.ftl";
            rewardFtl = "/common/titleEditor.ftl";
            familyFtl = "/adform/family.ftl";
        } else if (adFormType == CadreConstants.CADRE_ADFORMTYPE_ZZB_GB2312) {
            maxFamilyCount = 7;
            adFormFtl = "/adform/adform2.ftl";
            titleEditorFtl = "/common/titleEditor2.ftl";
            rewardFtl = "/common/titleEditor.ftl";
            familyFtl = "/adform/family2.ftl";
        } else if (adFormType == CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG) {
            maxFamilyCount = 7;
            adFormFtl = "/adform/adform3.ftl";
            titleEditorFtl = "/common/titleEditor3.ftl";
            rewardFtl = "/common/titleEditor.ftl";
            familyFtl = "/adform/family3.ftl";
        }

        dataMap.put("reward", freemarkerService.genTitleEditorSegment(null, bean.getReward(),
                false, 360, rewardFtl));
        dataMap.put("ces", bean.getCes());
        dataMap.put("reason", bean.getReason());

        //dataMap.put("learnDesc", freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 360));
        //dataMap.put("workDesc", freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 360));

        String resumeDesc = freemarkerService.genTitleEditorSegment(null, bean.getResumeDesc(), true, 360, titleEditorFtl);
        /*if(StringUtils.isBlank(resumeDesc)){
            resumeDesc = StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 360))
                    + StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 360));
        }*/
        dataMap.put("resumeDesc", StringUtils.trimToNull(resumeDesc));
        dataMap.put("trainDesc", freemarkerService.genTitleEditorSegment(null, bean.getTrainDesc(), false, 360, titleEditorFtl));

        String family = "";
        List<CadreFamily> cadreFamilys = bean.getCadreFamilys();
        int size = cadreFamilys.size();

        for (int i = 0; i < maxFamilyCount; i++) {
            if (size <= i)
                family += getFamilySeg(null, familyFtl);
            else
                family += getFamilySeg(cadreFamilys.get(i), familyFtl);
        }
        dataMap.put("family", family);
        SysUserView currentUser = ShiroHelper.getCurrentUser();
        if (currentUser != null)
            dataMap.put("admin", currentUser.getRealname());

        Date reportDate = bean.getReportDate();
        dataMap.put("y1", DateUtils.getYear(reportDate));
        dataMap.put("m1", DateUtils.getMonth(reportDate));
        dataMap.put("d1", DateUtils.getDay(reportDate));

        freemarkerService.process(adFormFtl, dataMap, out);
    }

    public void process(CadreInfoForm bean, Writer out) throws IOException, TemplateException {

        process(bean, null, out);
    }

    private Document getZZBTemplate() throws FileNotFoundException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xml/adform/rm.xml"));
        return reader.read(is);
    }

    private void setNodeText(Document doc, String nodeKey, String value) {

        Node node = doc.selectSingleNode("//Person//" + nodeKey);
        node.setText(StringUtils.trimToEmpty(value));
    }

    public String html2Paragraphs(String content) {

        if (StringUtils.isBlank(content)) return null;

        org.jsoup.nodes.Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(content.replace("<br", "<p")));
        Elements pElements = doc.getElementsByTag("p");
        int size = pElements.size();
        String str = "";
        //String lineSeparator = System.getProperty("line.separator", "/n");
        for (org.jsoup.nodes.Element pElement : pElements) {

            if (StringUtils.isBlank(pElement.text())) continue;

            String text = StringUtils.trimToEmpty(pElement.text());
            //System.out.println(rowStr);

            str += process(text) + "\r\n";
            //System.out.println(rowStr);
        }

        if (size == 0) {
            str += process(StringUtils.trimToEmpty(doc.text()));
        }

        return str;
    }

    private String process(String text) {

        // 需要换行的其间经历
        String[] texts = text.split("） （"); // 中间包含一个空格
        if (texts.length == 2) {
            text = texts[0] + "）\r\n（" + texts[1];
        }

        String _blankEndDate = "";
        String[] textArray = text.trim().split("\\s", 2);
        if (textArray[0].trim().endsWith("—")) {
            _blankEndDate = "       "; // 简历中结束时间为空，留7个空格
        }

        text = text.replaceFirst("[ |\\s]+", _blankEndDate + "  ").replaceAll("—", "--");

        return text;
    }

    /**
     * 导入中组部任免审批表
     *
     * @param path
     * @param importResume 是否解析并导入简历（学习经历和工作经历）
     * @throws IOException
     * @throws DocumentException
     */
    @Transactional
    public void importRm(String path, boolean importResume) throws IOException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(path);
        Document doc = reader.read(is);

        // 姓名去掉所有的空格
        String realname = StringUtil.removeAllBlank(XmlUtils.getNodeText(doc, "//Person/XingMing"));
        String idcard = XmlUtils.getNodeText(doc, "//Person/ShenFenZheng");

        CadreView cv = null;
        {
            CadreViewExample example = new CadreViewExample();
            CadreViewExample.Criteria criteria = example.createCriteria().andRealnameEqualTo(realname);
            if (StringUtils.isNotBlank(idcard)) {
                criteria.andIdcardEqualTo(idcard);
            }
            List<CadreView> cadreViews = cadreViewMapper.selectByExample(example);

            int size = cadreViews.size();
            if (size == 1) {
                cv = cadreViews.get(0);
            } else if (size > 1) {
                throw new OpException("存在{0}个姓名为{1}的干部，无法导入。", size, realname);
            }
        }

        String title = XmlUtils.getNodeText(doc, "//Person/XianRenZhiWu");
        if (cv == null) { // 不存在干部时先插入一个处级干部

            SysUserViewExample example = new SysUserViewExample();
            SysUserViewExample.Criteria criteria = example.createCriteria().andRealnameEqualTo(realname)
                    .andTypeEqualTo(SystemConstants.USER_TYPE_JZG);
            if (StringUtils.isNotBlank(idcard)) {
                criteria.andIdcardEqualTo(idcard);
            } else {
                throw new OpException("{0}身份证号为空，无法导入。", realname);
            }
            List<SysUserView> uvs = sysUserViewMapper.selectByExample(example);
            if (uvs.size() == 0) {
                throw new OpException("{0}不存在系统账号，无法导入。", realname);
            } else if (uvs.size() > 1) {
                throw new OpException("{0}存在多个系统账号，无法导入。", realname);
            }

            SysConfig sysConfig = CmTag.getSysConfig();
            SysUserView uv = uvs.get(0);
            int userId = uv.getId();
            Cadre cadre = new Cadre();
            cadre.setUserId(userId);
            cadre.setTitle(title.replaceAll("^" + sysConfig.getSchoolName().replaceAll("\\*", "\\\\*")
                    + "|" + sysConfig.getSchoolShortName().replaceAll("\\*", "\\\\*"), ""));
            cadre.setStatus(CadreConstants.CADRE_STATUS_MIDDLE);
            cadre.setType(CadreConstants.CADRE_TYPE_CJ);

            cadreService.insertSelective(cadre);

            cv = CmTag.getCadreByUserId(userId);
        }

        String nativePlace = XmlUtils.getNodeText(doc, "//Person/JiGuan");
        String homeplace = XmlUtils.getNodeText(doc, "//Person/ChuShengDi");
        String health = XmlUtils.getNodeText(doc, "//Person/JianKangZhuangKuang");
        String specialty = XmlUtils.getNodeText(doc, "//Person/ShuXiZhuanYeYouHeZhuanChang");
        String workTime = XmlUtils.getNodeText(doc, "//Person/CanJiaGongZuoShiJian");

        String resume = XmlUtils.getNodeText(doc, "//Person/JianLi");
        String avatarBase64 = XmlUtils.getNodeText(doc, "//Person/ZhaoPian");

        int userId = cv.getUserId();
        SysUser _sysUser = sysUserMapper.selectByPrimaryKey(userId);
        int cadreId = cv.getId();
        SysUserInfo ui = new SysUserInfo();
        ui.setUserId(userId);
        ui.setNativePlace(nativePlace);
        ui.setHomeplace(homeplace);

        if (StringUtils.isNotBlank(avatarBase64)) {
            String tmpAvatarFile = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR
                    + "lrmx" + FILE_SEPARATOR + "avatar" + FILE_SEPARATOR;
            FileUtils.mkdirs(tmpAvatarFile, false);
            ImageUtils.decodeBase64ToImage(avatarBase64, tmpAvatarFile, cv.getCode() + ".jpg");
            String tmpFile = tmpAvatarFile + cv.getCode() + ".jpg";
            String avatar = avatarService.copyToAvatar(new File(tmpFile));
            if (avatar != null) {
                ui.setAvatar(avatar);
            }
            FileUtils.delFile(tmpFile);
        }

        MetaType healthType = CmTag.getMetaTypeByName("mc_health", health);
        if (healthType != null) {
            ui.setHealth(healthType.getId());
        }
        ui.setSpecialty(specialty);
        ui.setResume(resume);
        if (importResume) {
            // 导入简历部分
            importResume(cadreId, resume);
        }

        sysUserInfoMapper.updateByPrimaryKeySelective(ui);

        title = StringUtils.removeStart(title, CmTag.getSysConfig().getSchoolName());
        if (StringUtils.isNotBlank(title)) {
            Cadre c = new Cadre();
            c.setId(cadreId);
            c.setTitle(title);
            cadreMapper.updateByPrimaryKeySelective(c);
        }

        Date _workTime = DateUtils.parseStringToDate(workTime);
        if (_workTime != null) {
            TeacherInfo record = new TeacherInfo();
            record.setUserId(userId);
            record.setWorkTime(_workTime);
            teacherInfoMapper.updateByPrimaryKeySelective(record);
        }

        List<Node> nodeList = doc.selectNodes("//Person/JiaTingChengYuan/Item");

        for (Node node : nodeList) {
            String _title = XmlUtils.getChildNodeText(node, "ChengWei");
            MetaType familyTitle = CmTag.getMetaTypeByName("mc_family_title", _title);

            CadreFamily cf = new CadreFamily();
            cf.setCadreId(cadreId);
            if (familyTitle != null) {
                cf.setTitle(familyTitle.getId());
            }

            String _realname = StringUtils.trimToNull(XmlUtils.getChildNodeText(node, "XingMing"));
            String _birthday = XmlUtils.getChildNodeText(node, "ChuShengRiQi");
            String _politicalStatus = XmlUtils.getChildNodeText(node, "ZhengZhiMianMao");
            String _unit = XmlUtils.getChildNodeText(node, "GongZuoDanWeiJiZhiWu");

            boolean withGod = StringUtils.contains(_unit, "去世");

            cf.setRealname(_realname);
            cf.setBirthday(DateUtils.parseStringToDate(_birthday));
            MetaType politicalStatus = CmTag.getMetaTypeByName("mc_political_status", _politicalStatus);
            if (politicalStatus != null) {
                cf.setPoliticalStatus(politicalStatus.getId());
            }
            cf.setUnit(_unit);
            cf.setWithGod(withGod);
            cf.setStatus(SystemConstants.RECORD_STATUS_FORMAL);

            CadreFamily cadreFamily = cadreFamilyService.get(cadreId, _realname);
            if (cadreFamily == null) {
                cadreFamilyService.insertSelective(cf);
            } else {
                cf.setId(cadreFamily.getId());
                cadreFamilyMapper.updateByPrimaryKeySelective(cf);
            }
        }

        // 导入年度绩效考核结果
        String ces = XmlUtils.getNodeText(doc, "//Person/NianDuKaoHeJieGuo");
        if (StringUtils.isNotBlank(ces)) {

            Pattern pattern = Pattern.compile("([1|2]\\d{3})[^\\d]+");
            Matcher matcher = pattern.matcher(ces);

            Map<Integer, Integer> yearPosMap = new HashMap<>();
            while (matcher.find()) {
                String _year = matcher.group(1);
                //System.out.println("_year = " + _year);
                int start = matcher.start(1);
                //System.out.println("start = " + start);

                yearPosMap.put(Integer.valueOf(_year), start);
            }

            Map<Integer, MetaType> evaTypes = CmTag.getMetaTypes("mc_cadre_eva");
            List<String> evaResults = new ArrayList<>();
            Map<String, Integer> evaMap = new HashMap<>();
            for (MetaType metaType : evaTypes.values()) {
                evaResults.add(metaType.getName());
                evaMap.put(metaType.getName(), metaType.getId());
            }
            String evaResultsReg = StringUtils.join(evaResults, "|");
            if (StringUtils.isNotBlank(evaResultsReg)) {
                pattern = Pattern.compile(MessageFormat.format("[^{0}]*({0})[^{0}]*", evaResultsReg));
                matcher = pattern.matcher(ces);

                Map<Integer, String> posResultMap = new LinkedHashMap<>();
                while (matcher.find()) {
                    String result = matcher.group(1);
                    //System.out.println("result = " + result);
                    int start = matcher.start(1);
                    //System.out.println("start = " + start);

                    posResultMap.put(start, result);
                }

                List<CadreEva> cadreEvas = new ArrayList<>();
                for (Map.Entry<Integer, Integer> entry : yearPosMap.entrySet()) {
                    int year = entry.getKey();
                    int yearPos = entry.getValue();
                    CadreEva record = new CadreEva();
                    record.setCadreId(cadreId);
                    record.setYear(year);
                    for (Map.Entry<Integer, String> resultEntry : posResultMap.entrySet()) {

                        int resultPos = resultEntry.getKey();
                        if (resultPos > yearPos) {
                            Integer evaType = evaMap.get(resultEntry.getValue());
                            record.setType(evaType);
                            cadreEvas.add(record);
                            break;
                        }
                    }
                }

                cadreEvaService.batchImport(cadreEvas);
            }
        }


        cacheHelper.clearUserCache(_sysUser);
        cacheHelper.clearCadreCache();

        is.close();
    }

    /**
     * 导入中组部任免审批表的简历部分
     *
     * @param cadreId
     * @param resume  中组部任免审批表简历内容
     */
    private void importResume(int cadreId, String resume) {

        List<ResumeRow> resumeRows = CadreUtils.parseResume(resume);
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        // <row, 主要工作经历或学习经历的ID> 辅助数组，用于其间工作
        Map<Integer, Integer> fidMap = new HashMap<>();

        for (ResumeRow resumeRow : resumeRows) {

            if (resumeRow.isEdu) {
                // 学习经历
                CadreEdu cadreEdu = new CadreEdu();
                cadreEdu.setCadreId(cadreId);

                String degree = null;
                Integer eduId = null;
                if (StringUtils.contains(resumeRow.desc, "中专")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_zz").getId();
                } else if (StringUtils.containsAny(resumeRow.desc, "大专", "专科")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_zk").getId();
                } else if (StringUtils.contains(resumeRow.desc, "进修")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_jxxx").getId();
                } else if (StringUtils.contains(resumeRow.desc, "课程班")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_yjskcb").getId();
                } else if (StringUtils.contains(resumeRow.desc, "博士")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_doctor").getId();
                    degree = "博士学位";
                } else if (StringUtils.contains(resumeRow.desc, "同等")
                        && StringUtils.contains(resumeRow.desc, "硕士")) { // 硕士同等学历、同等学历硕士
                    eduId = CmTag.getMetaTypeByCode("mt_edu_sstd").getId();
                    degree = "硕士学位";
                } else if (StringUtils.containsAny(resumeRow.desc, "硕士", "研究生")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_master").getId();
                    degree = "硕士学位";
                } else {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_bk").getId();
                    degree = "学士学位";
                }

                cadreEdu.setEduId(eduId);
                cadreEdu.setEnrolTime(resumeRow.start);
                cadreEdu.setFinishTime(resumeRow.end);
                cadreEdu.setIsGraduated(!StringUtils.contains(resumeRow.desc, "在读"));
                cadreEdu.setIsHighEdu(false);
                cadreEdu.setIsHighDegree(false); // 导入时默认非最高学位

                if (resumeRow.fRow == null) {
                    cadreEdu.setSchool(resumeRow.desc); // 全日制描述放入学校字段，需要手动编辑
                } else {
                    cadreEdu.setRemark(resumeRow.desc); // 在职描述放入备注字段，需要手动编辑
                }

                byte schoolType = CadreConstants.CADRE_SCHOOL_TYPE_DOMESTIC;
                if (StringUtils.containsAny(resumeRow.desc, "留学", "国外")) {
                    schoolType = CadreConstants.CADRE_SCHOOL_TYPE_ABROAD;
                } else if (StringUtils.containsAny(resumeRow.desc,
                        CmTag.getSysConfig().getSchoolName(),
                        CmTag.getSysConfig().getSchoolShortName())) {
                    schoolType = CadreConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL;

                    cadreEdu.setDegreeUnit(CmTag.getSysConfig().getSchoolName());
                }
                cadreEdu.setSchoolType(schoolType);

                int learnStyle = CmTag.getMetaTypeByCode("mt_fulltime").getId();
                if (resumeRow.fRow != null) {
                    learnStyle = CmTag.getMetaTypeByCode("mt_onjob").getId();
                }
                cadreEdu.setLearnStyle(learnStyle);

                cadreEdu.setHasDegree(cadreEdu.getIsGraduated());
                if (cadreEdu.getHasDegree()) {
                    cadreEdu.setDegree(degree);
                    if (schoolType != CadreConstants.CADRE_SCHOOL_TYPE_ABROAD) {
                        cadreEdu.setDegreeCountry("中国");
                    }

                    cadreEdu.setDegreeUnit(StringUtils.trimToEmpty(cadreEdu.getDegreeUnit()));
                    cadreEdu.setDegreeTime(cadreEdu.getFinishTime());
                }

                //cadreEdu.setRemark(resumeRow.desc);

                CadreEdu byEduTime = cadreEduService.getByEduTime(cadreId, cadreEdu.getEnrolTime(), cadreEdu.getFinishTime());
                if (byEduTime == null) {
                    try {
                        cadreEduService.insertSelective(cadreEdu);
                        if (resumeRow.row != null) {
                            // 暂存学习经历ID
                            fidMap.put(resumeRow.row, cadreEdu.getId());
                        }
                    } catch (Exception ex) {
                        throw new OpException("{0}学习经历有误：{1}", cadre.getRealname(), ex.getMessage());
                    }
                } else {

                    if (resumeRow.row != null) {
                        fidMap.put(resumeRow.row, byEduTime.getId());
                    }
                    cadreEdu.setId(byEduTime.getId());
                    cadreEduMapper.updateByPrimaryKeySelective(cadreEdu);
                }
            } else {
                // 工作经历
                CadreWork cadreWork = new CadreWork();
                cadreWork.setIsEduWork(false);
                cadreWork.setCadreId(cadreId);
                cadreWork.setStartTime(resumeRow.start);
                cadreWork.setEndTime(resumeRow.end);
                cadreWork.setDetail(resumeRow.desc);

                int workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_jg").getId();
                if (StringUtils.containsAny(resumeRow.desc, "学院", "系", "专业", "教师", "讲师", "助教", "教授")) {
                    workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_xy").getId();
                } else if (StringUtils.containsAny(resumeRow.desc, "留学", "国外")) {
                    workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_abroad").getId();
                }
                cadreWork.setWorkType(workType);
                cadreWork.setIsCadre(StringUtils.containsAny(resumeRow.desc, "处长", "院长",
                        "主任", "处级", "部长", "书记"));

                CadreWork byWorkTime = cadreWorkService.getByWorkTime(cadreId, cadreWork.getStartTime(), cadreWork.getEndTime());
                if (byWorkTime == null) {
                    if (resumeRow.fRow == null) {
                        // 保存主要工作经历
                        cadreWorkService.insertSelective(cadreWork);
                        if (resumeRow.row != null) {
                            // 暂存主要工作经历ID
                            fidMap.put(resumeRow.row, cadreWork.getId());
                        }
                    } else {
                        // 保存其间工作经历
                        cadreWork.setFid(fidMap.get(resumeRow.fRow)); // 读取主要工作经历ID
                        cadreWork.setIsEduWork(resumeRow.isEduWork);
                        cadreWorkService.insertSelective(cadreWork);
                    }

                } else {
                    if (resumeRow.row != null) {
                        fidMap.put(resumeRow.row, byWorkTime.getId());
                    }
                    cadreWork.setId(byWorkTime.getId());
                    cadreWorkService.updateByPrimaryKeySelective(cadreWork);
                }
            }
        }
    }

    // 输出中组部任免审批表
    public void zzb(CadreInfoForm bean, Writer out) throws IOException, DocumentException {

        Document doc = getZZBTemplate();

        setNodeText(doc, "XingMing", bean.getRealname());
        setNodeText(doc, "XingBie", SystemConstants.GENDER_MAP.get(bean.getGender()));
        setNodeText(doc, "ChuShengNianYue", DateUtils.formatDate(bean.getBirth(), "yyyyMM"));
        setNodeText(doc, "MinZu", bean.getNation());
        setNodeText(doc, "JiGuan", bean.getNativePlace());
        setNodeText(doc, "ChuShengDi", bean.getHomeplace());

        String dpPartyName = null;
        if (bean.getDpTypeId() != null && bean.getDpTypeId() > 0) {
            // 第一民主党派
            MetaType metaType = CmTag.getMetaType(bean.getDpTypeId());
            dpPartyName = StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
            // 其他民主党派
            List<CadreParty> dpParties = bean.getDpParties();
            if (dpParties.size() > 0) {
                for (CadreParty dpParty : dpParties) {
                    metaType = CmTag.getMetaType(dpParty.getClassId());
                    dpPartyName += "；" + StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
                }
            }
        }
        String owGrowTime = DateUtils.formatDate(bean.getOwGrowTime(), "yyyyMM");
        if (owGrowTime == null && dpPartyName != null) {
            setNodeText(doc, "RuDangShiJian", dpPartyName);
        } else if (owGrowTime != null && dpPartyName == null) {
            setNodeText(doc, "RuDangShiJian", owGrowTime);
        } else if (owGrowTime != null && dpPartyName != null) {
            setNodeText(doc, "RuDangShiJian", owGrowTime + "；" + dpPartyName);
        }

        setNodeText(doc, "CanJiaGongZuoShiJian", DateUtils.formatDate(bean.getWorkTime(), "yyyyMM"));
        setNodeText(doc, "JianKangZhuangKuang", bean.getHealth());
        setNodeText(doc, "ZhuanYeJiShuZhiWu", bean.getProPost());
        setNodeText(doc, "ShuXiZhuanYeYouHeZhuanChang", bean.getSpecialty());

        setNodeText(doc, "QuanRiZhiJiaoYu_XueLi", bean.getEdu());
        setNodeText(doc, "QuanRiZhiJiaoYu_XueWei", bean.getDegree());
        setNodeText(doc, "QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi", bean.getSchoolDepMajor1());
        setNodeText(doc, "QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi", bean.getSchoolDepMajor2());
        setNodeText(doc, "ZaiZhiJiaoYu_XueLi", bean.getInEdu());
        setNodeText(doc, "ZaiZhiJiaoYu_XueWei", bean.getInDegree());
        setNodeText(doc, "ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi", bean.getInSchoolDepMajor1());
        setNodeText(doc, "ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi", bean.getInSchoolDepMajor2());

        setNodeText(doc, "XianRenZhiWu", bean.getPost());
        setNodeText(doc, "NiRenZhiWu", bean.getInPost());
        setNodeText(doc, "NiMianZhiWu", bean.getPrePost());

        String jianli = "";
        String resumeDesc = bean.getResumeDesc();
        if (StringUtils.isNotBlank(resumeDesc)) {
            jianli += bean.getResumeDesc();
        } else {
            if (StringUtils.isNotBlank(bean.getLearnDesc())) {
                jianli += bean.getLearnDesc();
            }
            if (StringUtils.isNotBlank(bean.getWorkDesc())) {
                jianli += bean.getWorkDesc();
            }
        }

        setNodeText(doc, "JianLi", html2Paragraphs(jianli));
        setNodeText(doc, "JiangChengQingKuang", html2Paragraphs(bean.getReward()));
        setNodeText(doc, "NianDuKaoHeJieGuo", bean.getCes());
        setNodeText(doc, "RenMianLiYou", bean.getReason());

        // 家庭成员
        Element familys = (Element) doc.selectSingleNode("//Person//JiaTingChengYuan");
        List<CadreFamily> cadreFamilys = bean.getCadreFamilys();
        int size = Math.min(cadreFamilys.size(), 10);
        for (int i = 0; i < size; i++) {

            CadreFamily cf = cadreFamilys.get(i);
            Element item = familys.addElement("Item");
            item.addElement("ChengWei").setText(StringUtils.trimToEmpty(metaTypeService.getName(cf.getTitle())));
            item.addElement("XingMing").setText(StringUtils.trimToEmpty(cf.getRealname()));
            item.addElement("ChuShengRiQi").setText(StringUtils.trimToEmpty(DateUtils.formatDate(cf.getBirthday(), "yyyyMM")));

            String fps = "";
            if (cf != null && cf.getPoliticalStatus() != null) {
                fps = metaTypeService.getName(cf.getPoliticalStatus());
            }
            item.addElement("ZhengZhiMianMao").setText(StringUtils.trimToEmpty(fps));
            item.addElement("GongZuoDanWeiJiZhiWu").setText(cf == null ? "" : StringUtils.trimToEmpty(cf.getUnit()));
        }

        setNodeText(doc, "ChengBaoDanWei", "");
        setNodeText(doc, "JiSuanNianLingShiJian", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        setNodeText(doc, "TianBiaoShiJian", "");
        SysUserView currentUser = ShiroHelper.getCurrentUser();
        setNodeText(doc, "TianBiaoRen", currentUser == null ? "" : currentUser.getRealname());
        setNodeText(doc, "ShenFenZheng", bean.getIdCard());
        setNodeText(doc, "ZhaoPian", bean.getAvatar());
        setNodeText(doc, "Version", "3.2.1.6");


        // 设置发送的内容
        OutputFormat format = new OutputFormat();
        XMLWriter writer = null;
        format.setEncoding("UTF-8");
        writer = new XMLWriter(out, format);
        writer.write(doc);
        writer.close();

        /*OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(out, format);
        writer.write(doc);
        writer.close();*/
    }

    private String getFamilySeg(CadreFamily cf, String ftlPath) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();

        String ftitle = "";
        if (cf != null) {
            ftitle = metaTypeService.getName(cf.getTitle());
        }
        dataMap.put("ftitle", StringUtils.trimToEmpty(ftitle));
        dataMap.put("fname", cf == null ? "" : StringUtils.trimToEmpty(cf.getRealname()));

       /* String fage = "";
        if(cf!=null && cf.getBirthday()!=null){
            fage = DateUtils.calAge(cf.getBirthday());
        }*/
        dataMap.put("fage", cf == null ? "" : DateUtils.formatDate(cf.getBirthday(), DateUtils.YYYYMM));

        String fps = "";
        if (cf != null && cf.getPoliticalStatus() != null) {
            fps = metaTypeService.getName(cf.getPoliticalStatus());
        }
        dataMap.put("fps", StringUtils.trimToEmpty(fps));

        dataMap.put("fpost", cf == null ? "" : StringUtils.trimToEmpty(cf.getUnit()));

        return freemarkerService.process(ftlPath, dataMap);
    }
}
