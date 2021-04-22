package service.cadre;

import bean.CadreInfoForm;
import bean.ResumeRow;
import controller.global.OpException;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.*;
import domain.sys.*;
import ext.service.ExtCommonService;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by fafa on 2016/10/28.
 */
@Service
public class CadreAdformService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExtCommonService extCommonService;
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
    protected CadrePunishService cadrePunishService;
    @Autowired
    protected CadreFamilyService cadreFamilyService;
    @Autowired
    protected CadreTrainService cadreTrainService;
    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    protected AvatarService avatarService;
    @Autowired
    protected SysConfigService sysConfigService;

    public void export(Integer[] cadreIds,
                       Integer reserveType, //区分文件名
                       boolean isWord, // 否：中组部格式
                       Byte adFormType,
                       HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, DocumentException {

        if (cadreIds == null) return;
        String preStr = "";
        if (reserveType != null){
            preStr = metaTypeService.getName(reserveType);
        }

        String fileClasspath = null;

        if (adFormType == null) {
            adFormType = CmTag.getByteProperty("adFormType",
                    CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG);
        }
        if (adFormType == CadreConstants.CADRE_ADFORMTYPE_BJ) {
            fileClasspath = "classpath:ftl/adform_docx/adform_bj.docx";
        } else if (adFormType == CadreConstants.CADRE_ADFORMTYPE_ZZB_GB2312) {
            fileClasspath = "classpath:ftl/adform_docx/adform_zzb(gb_2312).docx";
        } else if (adFormType == CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG) {
            fileClasspath = "classpath:ftl/adform_docx/adform_zzb.docx";
        }
        //模板文件
        File docxFile = ResourceUtils.getFile(fileClasspath);
        ZipFile zipFile = new ZipFile(docxFile);

        // 填表人
        String admin = null;
        SysUserView currentUser = ShiroHelper.getCurrentUser();
        if (currentUser != null) {
            admin = currentUser.getRealname();
        }

        if (cadreIds.length == 1) {

            int cadreId = cadreIds[0];
            CadreView cadre = iCadreMapper.getCadre(cadreId);

            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            if (isWord) {
                //输出WORD任免审批表
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " "  + preStr + "干部任免审批表 " + cadre.getUser().getRealname() + ".docx";

                response.setHeader("Content-Disposition",
                        "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
                response.setContentType("application/msword;charset=UTF-8");

                CadreInfoForm adform = getCadreAdform(cadreId);
                /*process(adform, adFormType,response.getWriter());*/

                if (adFormType == null) {
                    adFormType = CmTag.getByteProperty("adFormType",
                            CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG);
                }

                String document = process(adform, adFormType, admin);
                exportDocxUtils(zipFile,document,adform.getAvatar(),response.getOutputStream());

            } else {
                // 输出中组部任免审批表
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd") + " " + preStr + "干部任免审批表 " + cadre.getRealname();

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

            String _preStr = StringUtils.isBlank(preStr)?preStr:(preStr+" ");
            Set<String> filenameSet = new HashSet<>();
            for (int i = 0; i < cadreIds.length; i++) {

                int cadreId = cadreIds[i];
                CadreView cadre = iCadreMapper.getCadre(cadreId);
                String filename = null;
                String filepath = null;
                if (isWord) {
                    filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                            + " " + _preStr +  (i+1) + ".干部任免审批表 " + cadre.getRealname() + ".docx";

                    // 保证文件名不重复
                    if (filenameSet.contains(filename)) {
                        filename = cadre.getCode() + " " + filename;
                    }
                    filenameSet.add(filename);

                    filepath = tmpdir + FILE_SEPARATOR + filename;
                    FileOutputStream fop = new FileOutputStream(new File(filepath));
                    CadreInfoForm adform = getCadreAdform(cadreId);
                    /*OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");
                    process(adform, adFormType,osw);*/

                    String document = process(adform, adFormType, admin);
                    exportDocxUtils(zipFile, document, adform.getAvatar(), fop);
                } else {
                    filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                            + " " + _preStr + (i+1) + ".干部任免审批表 " + cadre.getRealname() + ".lrmx";

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
                    CmTag.getSysConfig().getSchoolName() + preStr);
            DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.zip(fileMap, filename, request, response);
            FileUtils.deleteDir(new File(tmpdir));
        }
    }

    //导出干部任免审批表
    public void export(Integer[] cadreIds,
                       Integer reserveType, //区分文件名
                       boolean isWord, // 否：中组部格式
                       HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, DocumentException {

        export(cadreIds, reserveType, isWord, null, request, response);
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
        bean.setReason(CmTag.getStringProperty("label_adform_reason"));
        bean.setCadreId(cadreId);
        bean.setRealname(CmTag.realnameWithEmpty(uv.getRealname()));
        bean.setGender(uv.getGender());
        bean.setIdCard(uv.getIdcard());
        bean.setBirth(cadre.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(DateUtils.getFirstDayOfMonth(cadre.getBirth())));

        File avatar = new File(springProps.avatarFolder + uv.getAvatar());
        if (!avatar.exists()) avatar = new File(ConfigUtil.defaultHomePath()
                + FILE_SEPARATOR + "img" + FILE_SEPARATOR + "default.png");

        // 头像默认大小
        bean.setAvatarWidth(135);
        bean.setAvatarHeight(180);
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

        bean.setMobile(cadre.getMobile());
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
        bean.setOwPositiveTime(cadre.getOwPositiveTime());

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
            if (!isJxxx(secondHighDegree.getEduId())) { // 把第二个最高学位对应的学历放在第二行（不显示学位）
                _fulltimeDegree = StringUtils.trimToEmpty(secondHighDegree.getDegree());
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
                            + StringUtils.trimToEmpty(fulltimeHighEdu.getDep())
                            +StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                    bean.setSchoolDepMajor2(null);

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
                            + StringUtils.trimToEmpty(fulltimeHighEdu.getDep())
                            +StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                bean.setSchoolDepMajor2(null);

            } else if (fulltimeHighDegree != null) {

                bean.setSameSchool(true);

                bean.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighDegree.getSchool())
                            + StringUtils.trimToEmpty(fulltimeHighDegree.getDep())
                            +StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighDegree.getMajor())));
                bean.setSchoolDepMajor2(null);
            }
        }else{ // 没有学位的情况

            CadreEdu fulltimeHighEdu = cadreEduService.getHighEdu(cadreId, fullltimeType.getId());
            if (fulltimeHighEdu != null && !isJxxx(fulltimeHighEdu.getEduId())) {
                _fulltimeEdu = CmTag.getEduName(fulltimeHighEdu.getEduId());

                bean.setSameSchool(true);

                bean.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                        + StringUtils.trimToEmpty(fulltimeHighEdu.getDep())
                        + StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
                bean.setSchoolDepMajor2(null);
            }

            if(fulltimeHighEdu!=null) {
                MetaType eduType = CmTag.getMetaType(fulltimeHighEdu.getEduId());
                if (eduType == null
                        || StringUtils.startsWith(eduType.getExtraAttr(), "cz")
                        || StringUtils.startsWith(eduType.getExtraAttr(), "gz")) {
                    bean.setSchoolDepMajor1(null); // 全日制教育中，初中、高中的学历不显示“毕业院校系及专业”
                }
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
            if (!isJxxx(secondHighDegree.getEduId())) { // 把第二个最高学位对应学历放在第二行 （不显示学位）
                _onjobDegree = StringUtils.trimToEmpty(secondHighDegree.getDegree());
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
                    /*bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                            + StringUtils.trimToEmpty(onjobHighEdu.getDep()));
                    bean.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));*/
                    bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                            + StringUtils.trimToEmpty(onjobHighEdu.getDep())
                            + StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                    bean.setInSchoolDepMajor2(null);
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
                        + StringUtils.trimToEmpty(onjobHighEdu.getDep())
                        + StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                bean.setInSchoolDepMajor2(null);
            } else if (onjobHighDegree != null) {

                bean.setSameInSchool(true);
                bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighDegree.getSchool())
                        + StringUtils.trimToEmpty(onjobHighDegree.getDep())
                        + StringUtils.trimToEmpty(CadreUtils.major(onjobHighDegree.getMajor())));
                bean.setInSchoolDepMajor2(null);
            }
        }else{ // 没有学位的情况

            CadreEdu onjobHighEdu = cadreEduService.getHighEdu(cadreId, onjobType.getId());
            if (onjobHighEdu != null && !isJxxx(onjobHighEdu.getEduId())) {
                _onjobEdu = CmTag.getEduName(onjobHighEdu.getEduId());

                bean.setSameInSchool(true);
                bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                        + StringUtils.trimToEmpty(onjobHighEdu.getDep())
                        + StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
                bean.setInSchoolDepMajor2(null);
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
        /*CadrePost mainCadrePost = cadrePostService.getFirstMainCadrePost(cadreId);
        bean.setPost(mainCadrePost==null?null:springProps.school + mainCadrePost.getPost());*/
        // 现任职务
        bean.setPost(cadre.getTitle());
        String schoolName = sysConfigService.getSchoolName();
        if (BooleanUtils.isNotTrue(cadre.getIsOutside())
                && !StringUtils.startsWith(cadre.getTitle(), schoolName)) {
            if(StringUtils.isNotBlank(cadre.getTitle())) {
                bean.setPost(schoolName + StringUtils.trimToEmpty(cadre.getTitle()));
            }
        }

        // 学习经历
        bean.setLearnDesc(cadreInfoService.getTrimContent(cadreId, CadreConstants.CADRE_INFO_TYPE_EDU));

        // 奖惩情况
        String _reward = cadreInfoService.getTrimContent(cadreId, CadreConstants.CADRE_INFO_TYPE_REWARD);
        if (StringUtils.isBlank(_reward)) {

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("cadreRewards", cadreRewardService.list(cadreId));
            dataMap.put("cadrePunishes", cadrePunishService.list(cadreId));

            _reward = freemarkerService.freemarker(dataMap, "/cadre/cadreReward.ftl");
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

        bean.setCes(extCommonService.getEvaResult(cadreId));

        // 培训情况
        String trainDesc = cadreInfoService.getTrimContent(cadreId, CadreConstants.CADRE_INFO_TYPE_TRAIN);
        if(StringUtils.isBlank(trainDesc)){
            trainDesc = freemarkerService.freemarker(cadreTrainService.list(cadreId),
                "cadreTrains", "/cadre/cadreTrain.ftl");
        }
        bean.setTrainDesc(trainDesc);

        // 所有的家庭成员
        CadreFamilyExample example = new CadreFamilyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("sort_order asc");
        List<CadreFamily> cadreFamilys = cadreFamilyMapper.selectByExample(example);
        bean.setCadreFamilys(cadreFamilys);

        // 呈报日程默认当天
        //bean.setReportDate(new Date());

        return bean;
    }

    // 任免审批表模板输出为字符串 admin：填表人
    public String process(CadreInfoForm bean, Byte adFormType, String admin) throws IOException, TemplateException {

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
        String _resumeDesc = bean.getResumeDesc();
        if (adFormType == CadreConstants.CADRE_ADFORMTYPE_BJ) {

            _resumeDesc = StringUtils.replace(_resumeDesc, "—", "－");
            maxFamilyCount = 7;
            adFormFtl = "/adform_docx/adform_bj.ftl";
            titleEditorFtl = "/adform_docx/titleEditor_bj.ftl";
            rewardFtl = "/adform_docx/editor_bj.ftl";
            familyFtl = "/adform_docx/family_bj.ftl";
        } else if (adFormType == CadreConstants.CADRE_ADFORMTYPE_ZZB_GB2312) {
            maxFamilyCount = 7;
            adFormFtl = "/adform_docx/adform_zzb(gb_2312).ftl";
            titleEditorFtl = "/common/titleEditor_zzb(gb_2312).ftl";
            rewardFtl = "/common/titleEditor2.ftl";
            familyFtl = "/adform_docx/family_zzb(gb_2312).ftl";
        } else if (adFormType == CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG) {
            maxFamilyCount = 7;
            adFormFtl = "/adform_docx/adform_zzb.ftl";
            titleEditorFtl = "/adform_docx/titleEditor_zzb.ftl";
            rewardFtl = "/common/titleEditor2.ftl";
            familyFtl = "/adform_docx/family_zzb.ftl";
        }

        dataMap.put("reward", freemarkerService.genTitleEditorSegment(null, bean.getReward(),
                false, 360, rewardFtl));
        dataMap.put("ces", bean.getCes());
        dataMap.put("reason", bean.getReason());

        //dataMap.put("learnDesc", freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 360));
        //dataMap.put("workDesc", freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 360));

        String resumeDesc = freemarkerService.genTitleEditorSegment(null, _resumeDesc, true, 360, titleEditorFtl);
        /*if(StringUtils.isBlank(resumeDesc)){
            resumeDesc = StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 360))
                    + StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 360));
        }*/
        dataMap.put("resumeDesc", StringUtils.trimToNull(resumeDesc));
        dataMap.put("trainDesc", freemarkerService.genTitleEditorSegment(null, bean.getTrainDesc(), false, 360, rewardFtl));

        int adForm1_family_birth = CmTag.getIntProperty("adForm1_family_birth", 1);
        dataMap.put("adForm1_family_birth", adForm1_family_birth);
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
        dataMap.put("admin", admin);

        Date reportDate = bean.getReportDate();
        if(reportDate!=null) {
            dataMap.put("y1", DateUtils.getYear(reportDate));
            dataMap.put("m1", DateUtils.getMonth(reportDate));
            dataMap.put("d1", DateUtils.getDay(reportDate));
        }

        return freemarkerService.process(adFormFtl,dataMap);
        //freemarkerService.process(adFormFtl, dataMap, out);
    }

    //输出任免审批表(系统默认格式)
    public void process(CadreInfoForm bean, OutputStream out) throws IOException, TemplateException {

        Byte adFormType = null;
        String fileClasspath = "classpath:ftl/adform_docx/adform_bj.docx";
        File docxFile = ResourceUtils.getFile(fileClasspath);
        ZipFile zipFile = new ZipFile(docxFile);

        String admin = null;
        SysUserView currentUser = ShiroHelper.getCurrentUser();
        if (currentUser != null) {
            admin = currentUser.getRealname();
        }

        String content = process(bean, adFormType, admin);
        exportDocxUtils(zipFile,content,bean.getAvatar(),out);
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

    public String html2Paragraphs(String content, String seg) {

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

            str += process(text) + seg;
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

        String _blankEndDate = "  "; // 默认留2个空格
        String[] textArray = text.trim().split("\\s", 2);
        if (textArray[0].trim().endsWith("—")) {
            _blankEndDate = "         "; // 简历中结束时间为空，留9个空格
        }

        text = text.replaceFirst("[ |\\s]+", _blankEndDate /*+ "  "*/).replaceAll("—", "--");

        return text;
    }

    /**
     * 导入中组部任免审批表
     *
     * @param path
     * @param parseResume 是否解析并导入简历（学习经历和工作经历）
     * @throws IOException
     * @throws DocumentException
     */
    @Transactional
    public void importRm(String path, boolean parseResume) throws IOException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(path);
        Document doc = reader.read(is);

        // 姓名去掉所有的空格
        String realname = ContentUtils.removeAllBlank(XmlUtils.getNodeText(doc, "//Person/XingMing"));
        String idcard = ContentUtils.removeAllBlank(XmlUtils.getNodeText(doc, "//Person/ShenFenZheng"));
        String birth = XmlUtils.getNodeText(doc,"//Person/ChuShengNianYue");

        CadreView cv = null;
        {
            List<Byte> statusList = new ArrayList<>();
            statusList.add(CadreConstants.CADRE_STATUS_CJ);
            statusList.add(CadreConstants.CADRE_STATUS_KJ);
            CadreViewExample example = new CadreViewExample();
            CadreViewExample.Criteria criteria = example.createCriteria().andRealnameEqualTo(realname)
                    .andStatusIn(statusList);
            SysUserViewExample userExample = new SysUserViewExample();
            userExample.createCriteria().andIdcardEqualTo(idcard).andLockedEqualTo(false);
            if (sysUserViewMapper.selectByExample(userExample).size() > 0) {
                if (StringUtils.isNotBlank(idcard)) {
                    criteria.andIdcardEqualTo(idcard);
                }
            }
            List<CadreView> cadreViews = cadreViewMapper.selectByExample(example);

            int size = cadreViews.size();
            if (size == 1) {
                cv = cadreViews.get(0);
            } else if (size > 1) {
                int count = 0;
                for (CadreView cadreView : cadreViews) {
                    String _birth = DateUtils.formatDate(cadreView.getBirth(), "yyyyMM");
                    if (_birth.equals(birth)){
                        cv = cadreView;
                        count++;
                    }
                }
                if (count > 1)
                    throw new OpException("存在{0}个姓名为{1}的干部，无法导入。", count, realname);
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
                throw new OpException("{0}不存在系统账号，请核对姓名和身份证号是否与系统内的一致。", realname);
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
            cadre.setStatus(CadreConstants.CADRE_STATUS_CJ);

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
        if (parseResume) {
            // 导入简历部分
            importResume(cadreId, resume, realname);
            cadreEduService.checkHighEdu(cadreId, true);
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

        MetaClass mcFamilyTitle = CmTag.getMetaClassByCode("mc_family_title");
        for (Node node : nodeList) {
            String _title = XmlUtils.getChildNodeText(node, "ChengWei");
            MetaType familyTitle = CmTag.getMetaTypeByName("mc_family_title", _title);
            // 不存在的称谓，则创建一个新的元数据类型
            if(familyTitle==null && StringUtils.isNotBlank(_title)){
                familyTitle = new MetaType();
                familyTitle.setClassId(mcFamilyTitle.getId());
                familyTitle.setCode(metaTypeService.genCode());
                familyTitle.setName(_title);
                metaTypeService.insertSelective(familyTitle);
            }

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

        //导入奖惩情况
        /*String reward = XmlUtils.getNodeText(doc, "//Person/JiangChengQingKuang");
        if (StringUtils.isNotBlank(reward)){
            //System.out.println(reward);
            importReward(cadreId, reward);
        }*/

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
        CmTag.clearCadreCache(userId);

        is.close();
    }

    /*
        解析中组部任免审批表的奖惩情况
    * */
    @Transactional
    public void importReward(int cadreId, String reward) {

        CadreRewardExample example = new CadreRewardExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        cadreRewardMapper.deleteByExample(example);

        int row = 1;
        String name = null;
        Date date = null;
        String[] lines = reward.split("\n|；");
        for (String line : lines) {
            Boolean flag = false;
            if (StringUtils.isBlank(line)) continue;
            line = line.replaceAll("；|。", "").trim();

            Pattern pattern = Pattern.compile("^([1|2]\\d{3})[^\\d]+(\\.|年)?((0-9){1,2})?(月)?.*");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()){
                line = matcher.group();
                flag = true;
            }else if (flag == false){
                Pattern pattern1 = Pattern.compile(".*([1|2]\\d{3})[^\\d]+(\\.|年)?((0-9){1,2})?(月)?.*");
                Matcher matcher1 = pattern1.matcher(line);
                if (matcher1.find())
                    line = matcher1.group();
            }

            Pattern p = Pattern.compile("(\\d{4})(\\.|年)?((\\d){1,2})?(月)?");
            Matcher m = p.matcher(line);
            if (m.find()) {
                //System.out.println(m.end());
                name = flag ? line.substring(m.end()).trim() : line.trim();
                date = DateUtils.parseStringToDate(m.group());
            }

            name = (name == null || name == "") ? line : name;

            CadreReward record = new CadreReward();
            record.setCadreId(cadreId);
            record.setRewardTime(date);
            record.setRewardLevel(CmTag.getMetaTypeByCode("mc_reward_dtj").getId());
            record.setRewardType(CadreConstants.CADRE_REWARD_TYPE_OTHER);
            if (name.length() >= 200)
                record.setRemark(name) ;
            else
                record.setName(name);
            cadreRewardService.insertSelective(record);
        }
    }

    /**
     * 导入中组部任免审批表的简历部分
     *
     * @param cadreId
     * @param resume  中组部任免审批表简历内容
     */
    private void importResume(int cadreId, String resume, String realname) {

        List<ResumeRow> resumeRows = CadreUtils.parseResume(resume, realname);
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        // <row, 主要工作经历或学习经历的ID> 辅助数组，用于其间工作
        Map<Integer, Integer> fidMap = new HashMap<>();

        for (ResumeRow resumeRow : resumeRows) {

            if (resumeRow.isEdu) {
                CadreEdu byEduTime = cadreEduService.getByEduTime(cadreId, resumeRow.start, resumeRow.end);
                if (byEduTime != null){
                    continue;
                }
                // 学习经历
                CadreEdu cadreEdu = new CadreEdu();
                cadreEdu.setCadreId(cadreId);

                Byte degreeType = null;
                String degree = "";
                Integer eduId = null;
                if (StringUtils.contains(resumeRow.desc, "中专")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_zz").getId();
                } else if (StringUtils.containsAny(resumeRow.desc, "大专", "专科")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_zk").getId();
                } else if (StringUtils.contains(resumeRow.desc, "进修")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_jxxx").getId();
                } else if (StringUtils.contains(resumeRow.desc, "课程班")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_yjskcb").getId();
                } else if (StringUtils.containsAny(resumeRow.desc, "博士", "硕博连读", "本硕博连读")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_doctor").getId();
                    degreeType = SystemConstants.DEGREE_TYPE_BS;
                    degree = "博士学位";
                } else if (StringUtils.contains(resumeRow.desc, "同等")
                        && StringUtils.contains(resumeRow.desc, "硕士")) { // 硕士同等学历、同等学历硕士
                    eduId = CmTag.getMetaTypeByCode("mt_edu_sstd").getId();
                    degreeType = SystemConstants.DEGREE_TYPE_SS;
                    degree = "硕士学位";
                } else if (StringUtils.containsAny(resumeRow.desc, "硕士", "研究生", "直硕", "本硕连读")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_master").getId();
                    degreeType = SystemConstants.DEGREE_TYPE_SS;
                    degree = "硕士学位";
                } else {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_bk").getId();
                    degreeType = SystemConstants.DEGREE_TYPE_XS;
                    degree = "学士学位";
                }

                cadreEdu.setEduId(eduId);
                cadreEdu.setEnrolTime(resumeRow.start);
                cadreEdu.setFinishTime(resumeRow.end);
                cadreEdu.setIsGraduated(resumeRow.end!=null && !StringUtils.contains(resumeRow.desc, "在读"));
                cadreEdu.setIsHighEdu(false);
                cadreEdu.setIsHighDegree(false); // 导入时默认非最高学位

                //处理学校/学院/专业字段
                analysisEdu(cadreEdu, resumeRow.desc);

                byte schoolType = CadreConstants.CADRE_SCHOOL_TYPE_DOMESTIC;
                if (StringUtils.containsAny(resumeRow.desc, "留学", "国外", "日本", "韩国", "美国", "英国", "法国")) {
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

                if (eduId == CmTag.getMetaTypeByCode("mt_edu_zz").getId() || eduId == CmTag.getMetaTypeByCode("mt_edu_zk").getId())
                    cadreEdu.setHasDegree(false);
                else
                    cadreEdu.setHasDegree(cadreEdu.getIsGraduated());
                if (cadreEdu.getHasDegree()) {
                    cadreEdu.setDegree(degree);
                    cadreEdu.setDegreeType(degreeType);
                    if (schoolType != CadreConstants.CADRE_SCHOOL_TYPE_ABROAD) {
                        cadreEdu.setDegreeCountry("中国");
                    }else if (StringUtils.containsAny(resumeRow.desc,  "日本", "韩国", "美国", "英国", "法国")){
                        Pattern pattern = Pattern.compile("日本|韩国|美国|英国|法国|加拿大");
                        Matcher matcher = pattern.matcher(resumeRow.desc);
                        if (matcher.find()) {
                            cadreEdu.setDegreeCountry(matcher.group());
                        }
                    }

                    cadreEdu.setDegreeUnit(StringUtils.trimToEmpty(cadreEdu.getDegreeUnit()));
                    cadreEdu.setDegreeTime(cadreEdu.getFinishTime());
                }

                if (byEduTime == null) {
                    try {
                        cadreEduService.insertSelective(cadreEdu);
                        if (resumeRow.row != null) {
                            // 暂存学习经历ID
                            fidMap.put(resumeRow.row, cadreEdu.getId());
                        }
                    } catch (Exception ex) {
                        //导入出错，会将该条信息录入到工作经历
                        CadreWork cadreWork = new CadreWork();
                        cadreWork.setIsEduWork(false);
                        cadreWork.setCadreId(cadreId);
                        cadreWork.setStartTime(resumeRow.start);
                        cadreWork.setEndTime(resumeRow.end);
                        cadreWork.setDetail(resumeRow.desc);
                        cadreWork.setNote(resumeRow.note);

                        int workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_jg").getId();
                        if (StringUtils.containsAny(resumeRow.desc, "学院", "系", "专业", "教师", "讲师", "助教", "教授")) {
                            workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_xy").getId();
                        } else if (StringUtils.containsAny(resumeRow.desc, "留学", "国外")) {
                            workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_abroad").getId();
                        }
                        cadreWork.setWorkTypes(workType+"");
                        cadreWork.setIsCadre(StringUtils.containsAny(resumeRow.desc, "处长", "院长",
                                "主任", "处级", "部长", "书记"));
                        cadreWorkService.insertSelective(cadreWork);
                        logger.error("任免审批表：{0}学习经历有误：{1}", cadre.getRealname(), ex.getMessage());
                        //throw new OpException("{0}学习经历有误：{1}", cadre.getRealname(), ex.getMessage());
                    }
                }
            } else {
                CadreWork byWorkTime = cadreWorkService.getByWorkTime(cadreId, resumeRow.start, resumeRow.end);
                if (byWorkTime != null){
                    continue;
                }
                // 工作经历
                CadreWork cadreWork = new CadreWork();
                cadreWork.setIsEduWork(false);
                cadreWork.setCadreId(cadreId);
                cadreWork.setStartTime(resumeRow.start);
                cadreWork.setEndTime(resumeRow.end);
                cadreWork.setDetail(resumeRow.desc);
                cadreWork.setNote(resumeRow.note);

                int workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_jg").getId();
                if (StringUtils.containsAny(resumeRow.desc, "学院", "系", "专业", "教师", "讲师", "助教", "教授")) {
                    workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_xy").getId();
                } else if (StringUtils.containsAny(resumeRow.desc, "留学", "国外")) {
                    workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_abroad").getId();
                }
                cadreWork.setWorkTypes(workType+"");
                cadreWork.setIsCadre(StringUtils.containsAny(resumeRow.desc, "处长", "院长",
                        "主任", "处级", "部长", "书记"));

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

                }
            }
        }
    }

    //解析学校院系专业
    public void analysisEdu(CadreEdu cadreEdu, String eduStr){

        if (StringUtils.containsAny(eduStr, "中专生", "大专生", "专科生", "自考[本科]", "函授大专生", "函授本科", "成人教育本科", "博士生联合培养",
                "进修", "学习", "联合培养博士研究生", "直博", "", "", "", "", "")){
            cadreEdu.setNote(PatternUtils.withdraw(".*([大|中]+专生|专科生|自考[本科]|函授大专生|函授本科|[博士生]*联合培养[博士研究生]*" +
                    "|[研究生]*[课程]*进修[班]*|[证书班]*学习|直博).*", eduStr));
        }
        cadreEdu.setResume(eduStr);
        //cadreEdu.setRemark(eduStr); // 学习经历放入备注，用来核对信息

        String otherStr = PatternUtils.withdraw("(^[同等学力]*攻读).*", eduStr);
        String school = "";
        String dep = "";
        String major = "";
        if (StringUtils.isBlank(otherStr)){
            otherStr = PatternUtils.withdraw("^同等学力.*", eduStr);
        }
        if (StringUtils.isNotBlank(otherStr)) {
            eduStr = eduStr.replace(otherStr, "");
        }
        if (StringUtils.contains(eduStr, "大学")){
                    /*Pattern pattern = Pattern.compile("大学");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        school = str.substring(0, matcher.end());
                    }*/
            school = getFirstMatch("大学", eduStr);
        }else if (StringUtils.contains(eduStr, "学校")){
            school = PatternUtils.withdraw("(.*学校)", eduStr);
        }else if (StringUtils.contains(eduStr, "学院")){
                    /*Pattern pattern = Pattern.compile("学院");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        school = str.substring(0, matcher.end());
                    }*/
            school = getFirstMatch("学院", eduStr);
        }else if (StringUtils.contains(eduStr, "师范")){
            school = PatternUtils.withdraw(eduStr, "(.*师范).*");
        }else if (PatternUtils.match("(中科院|.*党校).*", eduStr)){
            //比较特殊，少有
            school = PatternUtils.withdraw("(中科院|.*党校).*", eduStr);
        }
        if (StringUtils.isNotBlank(school)) {
            eduStr = eduStr.replace(school, "");

            if (PatternUtils.match("(.*(?!关)系(?!统)).*", eduStr)) {//关系系统
                dep = getFirstMatch("(?!关)系(?!统)", eduStr);
                //dep = PatternUtils.withdraw("(.*系(?!统)).*", str);
            } else if (StringUtils.contains(eduStr, "学院")) {
                dep = getFirstMatch("学院", eduStr);
            } else if (StringUtils.contains(eduStr, "部") && !StringUtils.contains(eduStr, "干部")) {//干部
                dep = PatternUtils.withdraw("(.*部).*", eduStr);
            } else if (StringUtils.contains(eduStr, "所")) {
                dep = PatternUtils.withdraw("(.*所).*", eduStr);
            } else if (StringUtils.contains(eduStr, "中心")) {
                dep = PatternUtils.withdraw("(.*中心).*", eduStr);
            } else if (StringUtils.contains(eduStr, "研究院")) {
                dep = PatternUtils.withdraw("(.*研究院).*", eduStr);
            } else if (StringUtils.contains(eduStr, "(.*实验室).*")){
                dep = PatternUtils.withdraw("(.*实验室).*",eduStr);
            }
            if (StringUtils.contains(dep, "专业")){
                dep = "";
            }
            if (StringUtils.isNotBlank(dep)) {
                eduStr = eduStr.replace(dep, "");
            }
            if (PatternUtils.match("(.*专业).*", eduStr)) {
                major = getFirstMatch("专业", eduStr);
                //major = PatternUtils.withdraw("(.*专业).*", str);
            } else if (StringUtils.containsAny(eduStr, "本科生", "研究生", "博士生", "进修")) {
                Pattern pattern = Pattern.compile("本科生|研究生|博士生|进修");
                Matcher matcher = pattern.matcher(eduStr);
                if (matcher.find()) {
                    major = eduStr.substring(0, matcher.start());
                }
                //major = str.split("本科生|研究生|博士生|进修")[0];
            }
        }else {
            CadreView cv = cadreService.get(cadreEdu.getCadreId());
            logger.error("任免审批表" + cv.getRealname() + "的学习经历或工作经历需要调整");
            /*throw new OpException(cv.getRealname() + "的学习经历或工作经历需要调整");*/
        }
        if (StringUtils.isNotBlank(school)) {
            cadreEdu.setSchool(school);
        }
        if (StringUtils.isNotBlank(dep)) {
            cadreEdu.setDep(dep);
        }
        if (StringUtils.isNotBlank(major)) {
            cadreEdu.setMajor(major);
        }

    }

    //得到最先匹配到该reg的数据
    public String getFirstMatch(String reg, String data){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return data.substring(0, matcher.end());
        }
        return null;
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

        setNodeText(doc, "JianLi", html2Paragraphs(jianli, "\r\n"));
        setNodeText(doc, "JiangChengQingKuang", html2Paragraphs(bean.getReward(), "\r\n"));
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
         if (cf != null) {
             Date birth = cf.getBirthday();
             String fage = birth == null ? "" : DateUtils.formatDate(birth, DateUtils.YYYYMM);
             dataMap.put("fage", fage);

             String fage2;
             int adForm1_family_birth = CmTag.getIntProperty("adForm1_family_birth", 1);
             if(adForm1_family_birth==1){
                 fage2 = fage;
             }else{
                 fage2 = (birth == null || BooleanUtils.isTrue(cf.getWithGod())) ? "" : DateUtils.yearOffNow(DateUtils.getFirstDayOfMonth(birth))+"";
             }
             dataMap.put("fage2", fage2);
         }

        String fps = "";
        if (cf != null && cf.getPoliticalStatus() != null) {
            fps = metaTypeService.getName(cf.getPoliticalStatus());
        }
        dataMap.put("fps", StringUtils.trimToEmpty(fps));

        dataMap.put("fpost", cf == null ? "" : StringUtils.trimToEmpty(cf.getUnit()));

        return freemarkerService.process(ftlPath, dataMap);
    }

    public void exportDocxUtils(ZipFile zipFile, String document, String avatar, OutputStream outputStream) throws IOException {

        //zip输出流
        ZipOutputStream zipout = new ZipOutputStream(outputStream);
        //填充后的模板内容
        ByteArrayInputStream documentInput = new ByteArrayInputStream(document.getBytes("utf-8"));
        //头像内容
        InputStream imageInput = ImageUtils.decodeBase64ToInputStream(avatar);

        //获取docx模板文件
        Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();

        int len = -1;
        byte[] buffer = new byte[1024];

        //遍历zip文件结构
        while (zipEntrys.hasMoreElements()) {

            ZipEntry next = zipEntrys.nextElement();
            InputStream is = zipFile.getInputStream(next);

            zipout.putNextEntry(new ZipEntry(next.getName()));
            if ("word/document.xml".equals(next.getName())) {//如果是word/document.xml则替换为模板输出
                if (documentInput != null) {
                    while ((len = documentInput.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    documentInput.close();
                }
            }else if ("word/media/image1.png".equals(next.getName())){//如果是word/media/image1.png则替换为图片输出
                if (imageInput != null){
                    while ((len = imageInput.read(buffer)) != -1) {
                        zipout.write(buffer,0,len);
                    }
                }
                imageInput.close();
            }else {
                if (is != null){
                    while ((len = is.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                }
                is.close();
            }
        }
        //关闭zip输出流
        if(zipout!=null){
            zipout.close();
        }
    }

    @Transactional
    public void importDocxRm(String path) throws IOException {

        FileInputStream is = new FileInputStream(path);//载入文档 //如果是office2007  docx格式
        if(path.toLowerCase().endsWith("docx")){
            //word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
            XWPFDocument xwpf = new XWPFDocument(is);//得到word文档的信息
            Iterator<XWPFTable> it = xwpf.getTablesIterator();//得到word中的表格
            Map<String, String> dataMap = new HashMap<>();//如<姓名,李**>

            int page = 0;
            //获得数据
            while(it.hasNext()){

                XWPFTable table = it.next();
                List<XWPFTableRow> rows=table.getRows();
                //读取每一行数据
                for (int i = 0; i < rows.size(); i++) {
                    if ((page == 0 && (i == 4 || i == 5))
                            || (page == 1 && i >= 12)){
                        continue;
                    }
                    //家庭成员表头
                    List<XWPFTableCell> _cells = null;
                    if (page == 1 && (i >= 5 && i <= 11)){
                        _cells = rows.get(4).getTableCells();
                    }
                    XWPFTableRow  row = rows.get(i);
                    //这里行是从1开始
                    //System.out.println(String.format("第%s页第%s行", page + 1, i + 1));
                    //读取每一列数据
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (int j = 0; j < cells.size(); j=j+2) {
                        String key = null;
                        String value = null;
                        if ((page == 0 && i < 4 && j + 1 == cells.size())
                                || (page == 1 && (i == 4 || j + 1 == cells.size()))){
                            continue;
                        }
                        if (page == 1 && (i >= 5 && i <= 11)){
                            key = ContentUtils.removeAllBlank(_cells.get(j + 1).getText()) + (i - 4);
                            value = ContentUtils.removeAllBlank(cells.get(j + 1).getText());
                            j--;
                        }else {
                            key = ContentUtils.removeAllBlank(cells.get(j).getText());
                            value = ContentUtils.removeAllBlank(cells.get(j + 1).getText());

                        }
                        dataMap.put(key, value);
                        //输出当前的单元格的数据
                        //System.out.println(key + ":" + value);
                    }
                }
                page++;
            }

            //存储数据
            String realname= dataMap.get("姓名");
            String birth = null;
            if (StringUtils.isNotBlank(dataMap.get("出生年月（岁）"))) {
                birth = (dataMap.get("出生年月（岁）").split("（"))[0];
            }
            CadreView cv = null;
            {
                List<Byte> statusList = new ArrayList<>();
                statusList.add(CadreConstants.CADRE_STATUS_CJ);
                statusList.add(CadreConstants.CADRE_STATUS_KJ);
                CadreViewExample example = new CadreViewExample();
                example.createCriteria().andRealnameEqualTo(realname).andStatusIn(statusList);
                SysUserViewExample userExample = new SysUserViewExample();
                userExample.createCriteria().andRealnameEqualTo(realname).andLockedEqualTo(false);

                List<CadreView> cadreViews = cadreViewMapper.selectByExample(example);

                int size = cadreViews.size();
                if (size == 1) {
                    cv = cadreViews.get(0);
                } else if (size > 1) {
                    int count = 0;
                    for (CadreView cadreView : cadreViews) {
                        String _birth = DateUtils.formatDate(cadreView.getBirth(), "yyyyMM");
                        if (_birth.equals(birth)){
                            cv = cadreView;
                            count++;
                        }
                    }
                    if (count > 1)
                        throw new OpException("存在{0}个姓名为{1}的干部，无法导入。", count, realname);
                }
            }

            String title = dataMap.get("现任职务");
            if (cv == null) { // 不存在干部时先插入一个处级干部

                SysUserView uv = null;
                SysUserViewExample example = new SysUserViewExample();
                example.createCriteria().andRealnameEqualTo(realname)
                        .andTypeEqualTo(SystemConstants.USER_TYPE_JZG);
                List<SysUserView> uvs = sysUserViewMapper.selectByExample(example);
                if (uvs.size() == 0) {
                    throw new OpException("{0}不存在系统账号，请核对姓名是否与系统内的一致。", realname);
                } else if (uvs.size() > 1) {
                    for (SysUserView _uv : uvs) {
                        String _birth = DateUtils.formatDate(_uv.getBirth(), "yyyyMM");
                        if (_birth.equals(birth)){
                            uv = _uv;
                        }
                    }

                    if(uv == null) {
                        throw new OpException("{0}存在多个系统账号，无法导入。", realname);
                    }
                }

                SysConfig sysConfig = CmTag.getSysConfig();
                int userId = uv.getId();
                Cadre cadre = new Cadre();
                cadre.setUserId(userId);
                cadre.setTitle(title.replaceAll("^" + sysConfig.getSchoolName().replaceAll("\\*", "\\\\*")
                        + "|" + sysConfig.getSchoolShortName().replaceAll("\\*", "\\\\*"), ""));
                cadre.setStatus(CadreConstants.CADRE_STATUS_CJ);

                cadreService.insertSelective(cadre);

                cv = CmTag.getCadreByUserId(userId);
            }

            String nation = dataMap.get("民族");
            String nativePlace = dataMap.get("籍贯");
            String homeplace = dataMap.get("出生地");
            String health = dataMap.get("健康状况");
            String specialty = dataMap.get("熟悉专业有何专长");
            String proPost = dataMap.get("专业技术职务");

            String resume = dataMap.get("简历");

            int userId = cv.getUserId();
            SysUser _sysUser = sysUserMapper.selectByPrimaryKey(userId);
            int cadreId = cv.getId();
            SysUserInfo ui = new SysUserInfo();
            ui.setUserId(userId);
            ui.setNation(nation);
            ui.setNativePlace(nativePlace);
            ui.setHomeplace(homeplace);

            //照片
            List<XWPFPictureData> picList = xwpf.getAllPictures();
            if (picList != null && picList.size() > 0){
                String tmpAvatarFile = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                        DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR
                        + "lrmx" + FILE_SEPARATOR + "avatar" + FILE_SEPARATOR;
                FileUtils.mkdirs(tmpAvatarFile, false);
                for (XWPFPictureData pic : picList) {
                    String tmpFile = tmpAvatarFile + cv.getCode() + ".jpg";
                    byte[] bytev = pic.getData();
                    // 大于300bites的图片我们才弄下来，消除word中莫名的小图片的影响
                    /*if (bytev.length > 300) {
                        FileOutputStream fos = new FileOutputStream(tmpFile);
                        fos.write(bytev);
                    }*/
                    FileOutputStream fos = new FileOutputStream(tmpFile);
                    fos.write(bytev);
                    String avatar = avatarService.copyToAvatar(new File(tmpFile));
                    if (avatar != null) {
                        ui.setAvatar(avatar);
                    }
                    FileUtils.delFile(tmpFile);
                }

            }

            MetaType healthType = CmTag.getMetaTypeByName("mc_health", health);
            if (healthType != null) {
                ui.setHealth(healthType.getId());
            }
            ui.setSpecialty(specialty);
            ui.setResume(resume);
            // 导入简历部分
            importDocxResume(cadreId, resume, realname);
            cadreEduService.checkHighEdu(cadreId, true);

            sysUserInfoMapper.updateByPrimaryKeySelective(ui);

            title = StringUtils.removeStart(title, CmTag.getSysConfig().getSchoolName());
            if (StringUtils.isNotBlank(title)) {
                Cadre c = new Cadre();
                c.setId(cadreId);
                c.setTitle(title);
                cadreMapper.updateByPrimaryKeySelective(c);
            }

            String workTime = dataMap.get("参加工作时间");
            Date _workTime = DateUtils.parseStringToDate(workTime);
            if (_workTime != null) {
                TeacherInfo record = new TeacherInfo();
                record.setUserId(userId);
                record.setWorkTime(_workTime);
                teacherInfoMapper.updateByPrimaryKeySelective(record);
            }

            MetaClass mcFamilyTitle = CmTag.getMetaClassByCode("mc_family_title");
            for (int i = 1; i < 8; i++) {
                String _title = dataMap.get("称谓" + i);
                if(StringUtils.isBlank(_title))
                    continue;
                MetaType familyTitle = CmTag.getMetaTypeByName("mc_family_title", _title);
                if (familyTitle == null && _title.length() > 3){
                   //throw new OpException("{0}的家人成为有问题", realname);
                    logger.error("家庭称谓有误{0}", realname);
                    continue;
                }
                // 不存在的称谓，则创建一个新的元数据类型
                if(familyTitle==null && StringUtils.isNotBlank(_title)){
                    familyTitle = new MetaType();
                    familyTitle.setClassId(mcFamilyTitle.getId());
                    familyTitle.setCode(metaTypeService.genCode());
                    familyTitle.setName(_title);
                    metaTypeService.insertSelective(familyTitle);
                }

                CadreFamily cf = new CadreFamily();
                cf.setCadreId(cadreId);
                if (familyTitle != null) {
                    cf.setTitle(familyTitle.getId());
                }

                String _realname = dataMap.get("姓名" + i);
                String _birthday = null;
                if (StringUtils.isNotBlank(dataMap.get("年龄" + i))){

                    try {
                        int age = Integer.valueOf(dataMap.get("年龄" + i));
                        _birthday = Integer.valueOf(DateUtils.getCurrentDateTime("yyyy")) - age + DateUtils.getCurrentDateTime("MMdd");
                    }catch (Exception e){
                        logger.error(_realname, e);
                    }

                }
                String _politicalStatus = dataMap.get("政治面貌" + i);
                String _unit = dataMap.get("工作单位及职务" + i);

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

            // 导入年度考核结果
            String ces = dataMap.get("核结果年度考");
            if (StringUtils.isNotBlank(ces)) {

                Map<Integer, MetaType> evaTypes = CmTag.getMetaTypes("mc_cadre_eva");
                List<String> evaResultList = new ArrayList<>();
                Map<String, Integer> evaMap = new HashMap<>();//<name, id>
                for (MetaType metaType : evaTypes.values()) {
                    evaResultList.add(metaType.getName());
                    evaMap.put(metaType.getName(), metaType.getId());
                }
                String[] ceses = ces.split("，|,|。");
                for (String cese : ceses) {
                    if (StringUtils.isNotBlank(cese)) {
                        Pattern pattern = Pattern.compile("([1|2]\\d{3})[^\\d]+");
                        Matcher matcher = pattern.matcher(cese);

                        List<Integer> yearList = new ArrayList<>();
                        while (matcher.find()) {
                            String _year = matcher.group(1);
                            //System.out.println("_year = " + _year);

                            yearList.add(Integer.valueOf(_year));
                        }

                        String evaResultsReg = StringUtils.join(evaResultList, "|");
                        if (StringUtils.isNotBlank(evaResultsReg)) {
                            pattern = Pattern.compile(MessageFormat.format("[^{0}]*({0})[^{0}]*", evaResultsReg));
                            matcher = pattern.matcher(cese);

                            String result = null;
                            if (matcher.find()) {
                                result = matcher.group(1);
                            }else {
                                continue;
                            }

                            List<CadreEva> cadreEvaList = new ArrayList<>();
                            for (Integer year : yearList) {
                                CadreEva cadreEva = new CadreEva();
                                cadreEva.setCadreId(cadreId);
                                cadreEva.setYear(year);
                                Integer evaType = evaMap.get(result);
                                cadreEva.setType(evaType);
                                cadreEvaList.add(cadreEva);
                                //System.out.println(String.format("%s年%s", year, result));
                            }
                            cadreEvaService.batchImport(cadreEvaList);
                        }
                    }
                }
            }

            cacheHelper.clearUserCache(_sysUser);
            CmTag.clearCadreCache(userId);

            is.close();
        }

    }

    /**
     * 导入word格式的任免审批表的简历部分
     *
     * @param cadreId
     * @param resume  word格式任免审批表简历内容
     */
    private void importDocxResume(int cadreId, String resume, String realname) {

        List<ResumeRow> resumeRows = CadreUtils.parseDocxResume(resume, realname);
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        // <row, 主要工作经历或学习经历的ID> 辅助数组，用于其间工作
        Map<Integer, Integer> fidMap = new HashMap<>();

        for (ResumeRow resumeRow : resumeRows) {

            if (resumeRow.isEdu) {
                // 学习经历
                CadreEdu byEduTime = cadreEduService.getByEduTime(cadreId, resumeRow.start, resumeRow.end);
                if (byEduTime != null){
                    continue;
                }
                // 学习经历
                CadreEdu cadreEdu = new CadreEdu();
                cadreEdu.setCadreId(cadreId);

                Byte degreeType = null;
                String degree = "";
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
                    degreeType = SystemConstants.DEGREE_TYPE_BS;
                    degree = "博士学位";
                } else if (StringUtils.contains(resumeRow.desc, "同等")
                        && StringUtils.contains(resumeRow.desc, "硕士")) { // 硕士同等学历、同等学历硕士
                    eduId = CmTag.getMetaTypeByCode("mt_edu_sstd").getId();
                    degreeType = SystemConstants.DEGREE_TYPE_SS;
                    degree = "硕士学位";
                } else if (StringUtils.containsAny(resumeRow.desc, "硕士", "研究生")) {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_master").getId();
                    degreeType = SystemConstants.DEGREE_TYPE_SS;
                    degree = "硕士学位";
                } else if (StringUtils.contains(resumeRow.desc, "初中") || StringUtils.contains(resumeRow.desc, "中学")){
                    eduId = CmTag.getMetaTypeByCode("mt_edu_cz").getId();
                } else if (StringUtils.contains(resumeRow.desc, "高中")){
                    eduId = CmTag.getMetaTypeByCode("mt_edu_gz").getId();
                } else {
                    eduId = CmTag.getMetaTypeByCode("mt_edu_bk").getId();
                    degreeType = SystemConstants.DEGREE_TYPE_XS;
                    degree = "学士学位";
                }

                cadreEdu.setEduId(eduId);
                cadreEdu.setEnrolTime(resumeRow.start);
                cadreEdu.setFinishTime(resumeRow.end);
                cadreEdu.setIsGraduated(resumeRow.end!=null && !StringUtils.contains(resumeRow.desc, "在读"));
                cadreEdu.setIsHighEdu(false);
                cadreEdu.setIsHighDegree(false); // 导入时默认非最高学位

                //处理学校/学院/专业字段
                analysisEdu(cadreEdu, resumeRow.desc);

                byte schoolType = CadreConstants.CADRE_SCHOOL_TYPE_DOMESTIC;
                if (StringUtils.containsAny(resumeRow.desc, "留学", "国外", "日本", "韩国", "美国", "英国", "法国")) {
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

                if (eduId == CmTag.getMetaTypeByCode("mt_edu_zz").getId() || eduId == CmTag.getMetaTypeByCode("mt_edu_zk").getId())
                    cadreEdu.setHasDegree(false);
                else
                    cadreEdu.setHasDegree(cadreEdu.getIsGraduated());
                if (cadreEdu.getHasDegree()) {
                    cadreEdu.setDegree(degree);
                    cadreEdu.setDegreeType(degreeType);
                    if (schoolType != CadreConstants.CADRE_SCHOOL_TYPE_ABROAD) {
                        cadreEdu.setDegreeCountry("中国");
                    }else if (StringUtils.containsAny(resumeRow.desc,  "日本", "韩国", "美国", "英国", "法国")) {
                        Pattern pattern = Pattern.compile("日本|韩国|美国|英国|法国|加拿大");
                        Matcher matcher = pattern.matcher(resumeRow.desc);
                        if (matcher.find()) {
                            cadreEdu.setDegreeCountry(matcher.group());
                        }
                    }

                    cadreEdu.setDegreeUnit(StringUtils.trimToEmpty(cadreEdu.getDegreeUnit()));
                    cadreEdu.setDegreeTime(cadreEdu.getFinishTime());
                }

                //cadreEdu.setRemark(resumeRow.desc);
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
                }
            } else {
                CadreWork byWorkTime = cadreWorkService.getByWorkTime(cadreId, resumeRow.start, resumeRow.end);
                if (byWorkTime != null){
                    continue;
                }
                // 工作经历
                CadreWork cadreWork = new CadreWork();
                cadreWork.setIsEduWork(false);
                cadreWork.setCadreId(cadreId);
                cadreWork.setStartTime(resumeRow.start);
                cadreWork.setEndTime(resumeRow.end);
                cadreWork.setDetail(resumeRow.desc);
                cadreWork.setNote(resumeRow.note);

                int workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_jg").getId();
                if (StringUtils.containsAny(resumeRow.desc, "学院", "系", "专业", "教师", "讲师", "助教", "教授")) {
                    workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_xy").getId();
                } else if (StringUtils.containsAny(resumeRow.desc, "留学", "国外")) {
                    workType = CmTag.getMetaTypeByCode("mt_cadre_work_type_abroad").getId();
                }
                cadreWork.setWorkTypes(workType+"");
                cadreWork.setIsCadre(StringUtils.containsAny(resumeRow.desc, "处长", "院长",
                        "主任", "处级", "部长", "书记"));

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

                }
            }
        }

    }
}
