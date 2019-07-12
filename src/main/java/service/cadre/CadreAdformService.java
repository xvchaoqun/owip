package service.cadre;

import bean.CadreInfoForm;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.*;
import domain.sys.SysUser;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
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
import java.util.*;

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
                process(adform, response.getWriter());
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
                if(isWord) {
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
                    process(adform, osw);
                }else{
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
        bean.setIsOw(cadre.getIsOw());
        bean.setOwGrowTime(cadre.getOwGrowTime());

        /*// 最高学历
        CadreEdu highEdu = cadreEduService.getHighEdu(cadreId);
        bean.setDegree(highEdu == null ? null : metaTypeService.getName(highEdu.getEduId()));
        bean.setSchoolDepMajor(highEdu == null ? null :
                StringUtils.trimToEmpty(highEdu.getSchool())+
                        StringUtils.trimToEmpty(highEdu.getDep())
                        +StringUtils.trimToEmpty(highEdu.getMajor()));*/
        String _fulltimeEdu = "";
        String _fulltimeDegree = "";
        String _fulltimeMajor = "";
        String _onjobEdu = "";
        String _onjobDegree = "";
        String _onjobMajor = "";
        CadreEdu[] cadreEdus = cadre.getCadreEdus();
        CadreEdu fulltimeEdu = cadreEdus[0];
        CadreEdu onjobEdu = cadreEdus[1];

        Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();
        MetaType jxxx = codeKeyMap.get("mt_edu_jxxx");

        if (fulltimeEdu != null && fulltimeEdu.getIsGraduated()) {
            if (jxxx != null && fulltimeEdu.getId().intValue() == jxxx.getId()) {
                // 进修学习不能进入表格
            } else {
                Integer eduId = fulltimeEdu.getEduId();
                if(eduId!=null) {
                    _fulltimeEdu = CmTag.getEduName(eduId);
                }

                bean.setSchool(StringUtils.trimToEmpty(fulltimeEdu.getSchool()));
                bean.setDep(StringUtils.trimToEmpty(fulltimeEdu.getDep()));
                bean.setDepMajor(StringUtils.trimToEmpty(CadreUtils.major(fulltimeEdu.getMajor())));
                _fulltimeMajor = bean.getSchool() + StringUtils.trimToEmpty(fulltimeEdu.getDep()) + bean.getDepMajor();

                _fulltimeDegree = fulltimeEdu.getDegree(); // 学位
            }
        }
        if (onjobEdu != null && onjobEdu.getIsGraduated()) {
            if (jxxx != null && onjobEdu.getId().intValue() == jxxx.getId()) {
                // 进修学习不能进入表格
            } else {
                Integer eduId = onjobEdu.getEduId();
                if(eduId!=null) {
                    _onjobEdu = CmTag.getEduName(eduId);
                }

                bean.setInSchool(StringUtils.trimToEmpty(onjobEdu.getSchool()));
                bean.setInDep(StringUtils.trimToEmpty(onjobEdu.getDep()));
                bean.setInDepMajor(StringUtils.trimToEmpty(CadreUtils.major(onjobEdu.getMajor())));

                _onjobMajor = bean.getInSchool() + StringUtils.trimToEmpty(onjobEdu.getDep()) + bean.getInDepMajor();

                _onjobDegree = onjobEdu.getDegree();
            }
        }
        // 全日制最高学历
        bean.setEdu(_fulltimeEdu);
        bean.setDegree(_fulltimeDegree);
        bean.setSchoolDepMajor(_fulltimeMajor);
        // 在职最高学历
        bean.setInEdu(_onjobEdu);
        bean.setInDegree(_onjobDegree);
        bean.setInSchoolDepMajor(_onjobMajor);

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
        CadreInfo edu = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_EDU);
        bean.setLearnDesc(edu == null ? null : edu.getContent());

        // 奖惩情况
        CadreInfo reward = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_REWARD);
        String _reward = (reward == null) ? null : reward.getContent();
        if (StringUtils.isBlank(_reward)) {
            _reward = freemarkerService.freemarker(cadreRewardService.list(cadreId),
                    "cadreRewards", "/cadre/cadreReward.ftl");
        }
        bean.setReward(StringUtils.trimToNull(_reward));

        // 工作经历
        CadreInfo work = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_WORK);
        bean.setWorkDesc(work == null ? null : work.getContent());

        // 简历
        CadreInfo resume = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_RESUME);
        String _resume = (resume == null) ? null : resume.getContent();
        if (StringUtils.isBlank(_resume)) {
            _resume = freemarkerService.freemarker(cadreWorkService.resume(bean.getCadreId()),
                    "cadreResumes", "/cadre/cadreResume.ftl");
        }
        bean.setResumeDesc(StringUtils.trimToNull(_resume));

        //年度考核结果
        Integer evaYears = CmTag.getIntProperty("evaYears");
        if(evaYears==null) evaYears = 3;
        Integer currentYear = DateUtils.getCurrentYear();
        List<Integer> years = new ArrayList<>();
        for (Integer i = 0; i < evaYears; i++) {
            years.add(currentYear-evaYears + i);
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
        CadreInfo train = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_TRAIN);
        bean.setTrainDesc(train == null ? null : train.getContent());

        // 社会关系
        CadreFamilyExample example = new CadreFamilyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("sort_order asc");

        int maxFamilyCount = 6;
        Integer adFormType = CmTag.getIntProperty("adFormType");
        if (adFormType != null && adFormType == 2) {
            maxFamilyCount = 7;
        }
        List<CadreFamily> cadreFamilys = cadreFamilyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, maxFamilyCount));
        bean.setCadreFamilys(cadreFamilys);

        // 呈报日程默认当天
        bean.setReportDate(new Date());

        return bean;
    }

    // 输出任免审批表
    public void process(CadreInfoForm bean, Writer out) throws IOException, TemplateException {

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
            // 民主党派
            MetaType metaType = CmTag.getMetaType(bean.getDpTypeId());
            String dpPartyName = StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
            dataMap.put("dpPartyName", dpPartyName);
            dataMap.put("dpGrowTime", DateUtils.formatDate(bean.getDpGrowTime(), DateUtils.YYYYMM));
        }

        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM));

        dataMap.put("health", bean.getHealth());
        dataMap.put("proPost", bean.getProPost());
        dataMap.put("specialty", bean.getSpecialty());

        dataMap.put("edu", bean.getEdu());
        dataMap.put("degree", bean.getDegree());
        dataMap.put("schoolDepMajor", bean.getSchoolDepMajor());
        dataMap.put("inEdu", bean.getInEdu());
        dataMap.put("inDegree", bean.getInDegree());
        dataMap.put("inSchoolDepMajor", bean.getInSchoolDepMajor());

        dataMap.put("post", bean.getPost());
        dataMap.put("inPost", bean.getInPost());
        dataMap.put("prePost", bean.getPrePost());

        int maxFamilyCount = 5;
        String adformFtl = "/adform/adform.ftl";
        String titleEditorFtl = "/common/titleEditor.ftl";
        String familyFtl = "/adform/family.ftl";
        Integer adFormType = CmTag.getIntProperty("adFormType");
        if (adFormType != null && adFormType == 2) {
            maxFamilyCount = 7;
            adformFtl = "/adform/adform2.ftl";
            titleEditorFtl = "/common/titleEditor2.ftl";
            familyFtl = "/adform/family2.ftl";
        }

        dataMap.put("reward", freemarkerService.genTitleEditorSegment(null, bean.getReward(), false, 360, titleEditorFtl));
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

        freemarkerService.process(adformFtl, dataMap, out);
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
        String[] textArray = text.trim().split(" ");
        if (textArray[0].trim().endsWith("—")) {
            _blankEndDate = "       "; // 简历中结束时间为空，留7个空格
        }

        text = text.replaceFirst("[ |\\s]+", _blankEndDate + "  ").replaceAll("—", "--");

        return text;
    }

    // 导入中组部任免审批表
    @Transactional
    public void importRm(String path) throws IOException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(path);
        Document doc = reader.read(is);

        String realname = XmlUtils.getNodeText(doc, "//Person/XingMing");
        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andRealnameEqualTo(realname);
        List<CadreView> cadreViews = cadreViewMapper.selectByExample(example);
        CadreView cv = null;
        int size = cadreViews.size();
        if (size == 1) {
            cv = cadreViews.get(0);
        } else if (size > 1) {
            throw new OpException("存在{0}个姓名为{1}的干部，无法导入。", size, realname);
        }

        if (cv == null) {
            throw new OpException("{0}不在干部库中，无法导入。", realname);
        }

        String nativePlace = XmlUtils.getNodeText(doc, "//Person/JiGuan");
        String homeplace = XmlUtils.getNodeText(doc, "//Person/ChuShengDi");
        String health = XmlUtils.getNodeText(doc, "//Person/JianKangZhuangKuang");
        String specialty = XmlUtils.getNodeText(doc, "//Person/ShuXiZhuanYeYouHeZhuanChang");
        String workTime = XmlUtils.getNodeText(doc, "//Person/CanJiaGongZuoShiJian");
        String title = XmlUtils.getNodeText(doc, "//Person/XianRenZhiWu");
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
                cadreFamilyMapper.insertSelective(cf);
            } else {
                cf.setId(cadreFamily.getId());
                cadreFamilyMapper.updateByPrimaryKeySelective(cf);
            }
        }

        cacheHelper.clearUserCache(_sysUser);
        cacheHelper.clearCadreCache();

        is.close();
    }

    // 输出中组部任免审批表
    public void zzb(CadreInfoForm adform, Writer out) throws IOException, DocumentException {

        Document doc = getZZBTemplate();

        setNodeText(doc, "XingMing", adform.getRealname());
        setNodeText(doc, "XingBie", SystemConstants.GENDER_MAP.get(adform.getGender()));
        setNodeText(doc, "ChuShengNianYue", DateUtils.formatDate(adform.getBirth(), "yyyyMM"));
        setNodeText(doc, "MinZu", adform.getNation());
        setNodeText(doc, "JiGuan", adform.getNativePlace());
        setNodeText(doc, "ChuShengDi", adform.getHomeplace());

        String dpPartyName = null;
        if (adform.getDpTypeId() != null && adform.getDpTypeId() > 0) {
            MetaType metaType = CmTag.getMetaType(adform.getDpTypeId());
            dpPartyName = StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
        }
        String owGrowTime = DateUtils.formatDate(adform.getOwGrowTime(), "yyyyMM");
        if (owGrowTime == null && dpPartyName != null) {
            setNodeText(doc, "RuDangShiJian", dpPartyName);
        } else if (owGrowTime != null && dpPartyName == null) {
            setNodeText(doc, "RuDangShiJian", owGrowTime);
        } else if (owGrowTime != null && dpPartyName != null) {
            setNodeText(doc, "RuDangShiJian", owGrowTime + "；" + dpPartyName);
        }

        setNodeText(doc, "CanJiaGongZuoShiJian", DateUtils.formatDate(adform.getWorkTime(), "yyyyMM"));
        setNodeText(doc, "JianKangZhuangKuang", adform.getHealth());
        setNodeText(doc, "ZhuanYeJiShuZhiWu", adform.getProPost());
        setNodeText(doc, "ShuXiZhuanYeYouHeZhuanChang", adform.getSpecialty());

        setNodeText(doc, "QuanRiZhiJiaoYu_XueLi", adform.getEdu());
        setNodeText(doc, "QuanRiZhiJiaoYu_XueWei", adform.getDegree());
        setNodeText(doc, "QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi", StringUtils.trimToEmpty(adform.getSchool())
                + StringUtils.trimToEmpty(adform.getDep()));
        setNodeText(doc, "QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi", adform.getDepMajor());
        setNodeText(doc, "ZaiZhiJiaoYu_XueLi", adform.getInEdu());
        setNodeText(doc, "ZaiZhiJiaoYu_XueWei", adform.getInDegree());
        setNodeText(doc, "ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi", StringUtils.trimToEmpty(adform.getInSchool()) +
                StringUtils.trimToEmpty(adform.getInDep()));
        setNodeText(doc, "ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi", adform.getInDepMajor());

        setNodeText(doc, "XianRenZhiWu", adform.getPost());
        setNodeText(doc, "NiRenZhiWu", adform.getInPost());
        setNodeText(doc, "NiMianZhiWu", adform.getPrePost());

        String jianli = "";
        String resumeDesc = adform.getResumeDesc();
        if (StringUtils.isNotBlank(resumeDesc)) {
            jianli += adform.getResumeDesc();
        } else {
            if (StringUtils.isNotBlank(adform.getLearnDesc())) {
                jianli += adform.getLearnDesc();
            }
            if (StringUtils.isNotBlank(adform.getWorkDesc())) {
                jianli += adform.getWorkDesc();
            }
        }

        setNodeText(doc, "JianLi", html2Paragraphs(jianli));
        setNodeText(doc, "JiangChengQingKuang", html2Paragraphs(adform.getReward()));
        setNodeText(doc, "NianDuKaoHeJieGuo", adform.getCes());
        setNodeText(doc, "RenMianLiYou", adform.getReason());

        // 家庭成员
        Element familys = (Element) doc.selectSingleNode("//Person//JiaTingChengYuan");
        List<CadreFamily> cadreFamilys = adform.getCadreFamilys();
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
        setNodeText(doc, "ShenFenZheng", adform.getIdCard());
        setNodeText(doc, "ZhaoPian", adform.getAvatar());
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
