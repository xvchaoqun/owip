package service.cadre;

import bean.CadreInfoForm;
import domain.base.MetaType;
import domain.cadre.*;
import domain.sys.SysUserView;
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
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.HtmlUtils;
import service.BaseMapper;
import service.SpringProps;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.party.MemberService;
import service.sys.SysConfigService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ImageUtils;

import javax.imageio.ImageIO;
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
    protected SysConfigService sysConfigService;

    // 获取任免审批表属性值
    public CadreInfoForm getCadreAdform(int cadreId){

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView uv = cadre.getUser();

        CadreInfoForm bean = new CadreInfoForm();
        bean.setCadreId(cadreId);
        bean.setRealname(CmTag.realnameWithEmpty(uv.getRealname()));
        bean.setGender(uv.getGender());
        bean.setIdCard(uv.getIdcard());
        bean.setBirth(cadre.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(cadre.getBirth()));

        File avatar =  new File(springProps.avatarFolder + uv.getAvatar());
        if(!avatar.exists()) avatar = new File(springProps.avatarFolder + FILE_SEPARATOR + springProps.defaultAvatar);

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

        if(fulltimeEdu!=null && fulltimeEdu.getIsGraduated()){
            if(jxxx!=null && fulltimeEdu.getId().intValue()==jxxx.getId()){
                // 进修学习不能进入表格
            }else {
                Integer eduId = fulltimeEdu.getEduId();
                //String degree = fulltimeEdu.getDegree();
                _fulltimeEdu = CmTag.getEduName(eduId) /*+ (degree!=null?degree:"")*/;

                bean.setSchool(StringUtils.trimToEmpty(fulltimeEdu.getSchool()));
                bean.setDep(StringUtils.trimToEmpty(fulltimeEdu.getDep()));
                bean.setDepMajor(StringUtils.trimToEmpty(CadreUtils.major(fulltimeEdu.getMajor())));
                _fulltimeMajor = bean.getSchool() + StringUtils.trimToEmpty(fulltimeEdu.getDep()) + bean.getDepMajor();

                _fulltimeDegree = fulltimeEdu.getDegree(); // 学位
            }
        }
        if(onjobEdu!=null && onjobEdu.getIsGraduated()){
            if(jxxx!=null && onjobEdu.getId().intValue()==jxxx.getId()){
                // 进修学习不能进入表格
            }else {
                Integer eduId = onjobEdu.getEduId();
                //String degree = onjobEdu.getDegree();
                _onjobEdu = CmTag.getEduName(eduId) /*+ (degree!=null?degree:"")*/;

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

        // 主职,现任职务
        /*CadrePost mainCadrePost = cadrePostService.getCadreMainCadrePost(cadreId);
        bean.setPost(mainCadrePost==null?null:springProps.school + mainCadrePost.getPost());*/
        // 现任职务
        String schoolName = sysConfigService.getSchoolName();
        if(!StringUtils.startsWith(cadre.getTitle(), schoolName)){
            bean.setPost(schoolName + StringUtils.trimToEmpty(cadre.getTitle()));
        }else{
            bean.setPost(cadre.getTitle());
        }

        // 学习经历
        CadreInfo edu = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_EDU);
        bean.setLearnDesc(edu==null?null:edu.getContent());

        // 奖惩情况
        CadreInfo reward = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_REWARD);
        String _reward = (reward == null) ? null :reward.getContent();
        if(StringUtils.isBlank(_reward)){
            _reward = freemarkerService.freemarker(cadreRewardService.list(cadreId),
                    "cadreRewards", "/cadre/cadreReward.ftl");
        }
        bean.setReward(StringUtils.trimToNull(_reward));

        // 工作经历
        CadreInfo work = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_WORK);
        bean.setWorkDesc(work==null?null:work.getContent());

        // 简历
        CadreInfo resume = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_RESUME);
        String _resume = (resume == null) ? null :resume.getContent();
        if(StringUtils.isBlank(_resume)){
            _resume = freemarkerService.freemarker(cadreWorkService.resume(bean.getCadreId()),
                    "cadreResumes", "/cadre/cadreResume.ftl");
        }
        bean.setResumeDesc(StringUtils.trimToNull(_resume));

        //年度考核结果
        Integer currentYear = DateUtils.getCurrentYear();
        String evaResult = (currentYear-3) + "、"+ (currentYear-2) + "、"+ (currentYear-1) + "年年度考核均为合格。"; // 默认
        {
            Map<Integer, String> evaMap = new LinkedHashMap<>();
            CadreEvaExample example = new CadreEvaExample();
            example.createCriteria().andCadreIdEqualTo(cadreId)
                    .andYearBetween(currentYear - 3, currentYear);
            example.setOrderByClause("year desc");
            List<CadreEva> cadreEvas = cadreEvaMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 3));
            if(cadreEvas.size()>0) {
                for (CadreEva cadreEva : cadreEvas) {
                    int year = cadreEva.getYear();
                    int type = cadreEva.getType();
                    evaMap.put(year, year + "年度考核" + metaTypeService.getName(type));
                }
                ArrayList<String> evaList = new ArrayList<>(evaMap.values());
                Collections.reverse(evaList);
                evaResult = StringUtils.join(evaList, "，");
            }
        }
        
        bean.setCes(evaResult);

        // 培训情况
        CadreInfo train = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_TRAIN);
        bean.setTrainDesc(train==null?null:train.getContent());

        // 社会关系
        CadreFamilyExample example = new CadreFamilyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        List<CadreFamily> cadreFamilys = cadreFamilyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 6));
        bean.setCadreFamilys(cadreFamilys);

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
        if(bean.getDpTypeId()!=null && bean.getDpTypeId()>0) {
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
        dataMap.put("reward", freemarkerService.genTitleEditorSegment(null, bean.getReward(), false, 360, "/common/titleEditor.ftl"));
        dataMap.put("ces", bean.getCes());
        dataMap.put("reason", bean.getReason());

        //dataMap.put("learnDesc", freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 360));
        //dataMap.put("workDesc", freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 360));

        String resumeDesc = freemarkerService.genTitleEditorSegment(null, bean.getResumeDesc(), true, 360, "/common/titleEditor.ftl");
        /*if(StringUtils.isBlank(resumeDesc)){
            resumeDesc = StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc(), true, 360))
                    + StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc(), true, 360));
        }*/
        dataMap.put("resumeDesc", StringUtils.trimToNull(resumeDesc));
        dataMap.put("trainDesc", freemarkerService.genTitleEditorSegment(null, bean.getTrainDesc(), false, 360, "/common/titleEditor.ftl"));

        String family = "";
        List<CadreFamily> cadreFamilys = bean.getCadreFamilys();
        int size = cadreFamilys.size();
        for (int i=0; i<5; i++) {
            if(size<=i)
                family += getFamilySeg(null, "/adform/family.ftl");
            else
                family += getFamilySeg(cadreFamilys.get(i), "/adform/family.ftl");
        }
        dataMap.put("family", family);
        SysUserView currentUser = ShiroHelper.getCurrentUser();
        if(currentUser!=null)
            dataMap.put("admin", currentUser.getRealname());

        dataMap.put("y1", DateUtils.getCurrentYear());
        dataMap.put("m1", DateUtils.getMonth(new Date()));
        dataMap.put("d1", DateUtils.getDay(new Date()));

        freemarkerService.process("/adform/adform.ftl", dataMap, out);
    }

    private Document getZZBTemplate() throws FileNotFoundException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xml/adform/rm.xml"));
        return reader.read(is);
    }

    private void setNodeText(Document doc, String nodeKey, String value){

        Node node = doc.selectSingleNode("//Person//" + nodeKey);
        node.setText(StringUtils.trimToEmpty(value));
    }

    public String html2Paragraphs(String content){

        if(StringUtils.isBlank(content)) return null;

        org.jsoup.nodes.Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(content));
        Elements pElements = doc.getElementsByTag("p");
        int size = pElements.size();
        String str = "";
        //String lineSeparator = System.getProperty("line.separator", "/n");
        for (org.jsoup.nodes.Element pElement : pElements) {

            String text = StringUtils.trimToEmpty(pElement.text());
            //System.out.println(rowStr);

            str +=  process(text) + "\r\n";
            //System.out.println(rowStr);
        }

        if (size == 0) {
            str += process(StringUtils.trimToEmpty(doc.text()));
        }

        return str;
    }

    private String process(String text){

        // 需要换行的期间经历
        String[] texts = text.split("） （"); // 中间包含一个空格
        if(texts.length==2){
            text = texts[0] + "）\r\n（" + texts[1];
        }

        String _blankEndDate="";
        String[] textArray = text.trim().split(" ");
        if(textArray[0].trim().endsWith("—")){
            _blankEndDate = "       "; // 简历中结束时间为空，留7个空格
        }

        text = text.replaceFirst("[ |\\s]+", _blankEndDate + "  ").replaceAll("—", "--");

        return text;
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
        if(adform.getDpTypeId()!=null && adform.getDpTypeId()>0){
            MetaType metaType = CmTag.getMetaType(adform.getDpTypeId());
            dpPartyName = StringUtils.defaultIfBlank(metaType.getExtraAttr(), metaType.getName());
        }
        String owGrowTime = DateUtils.formatDate(adform.getOwGrowTime(), "yyyyMM");
        if(owGrowTime==null && dpPartyName!=null){
            setNodeText(doc, "RuDangShiJian", dpPartyName);
        }else if(owGrowTime!=null && dpPartyName==null){
            setNodeText(doc, "RuDangShiJian", owGrowTime);
        }else if(owGrowTime!=null && dpPartyName!=null){
            setNodeText(doc, "RuDangShiJian", owGrowTime+"；" + dpPartyName);
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
        if(StringUtils.isNotBlank(resumeDesc)){
            jianli += adform.getResumeDesc();
        }else {
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
        Element familys = (Element)doc.selectSingleNode("//Person//JiaTingChengYuan");
        List<CadreFamily> cadreFamilys = adform.getCadreFamilys();
        int size = Math.min(cadreFamilys.size(), 10);
        for (int i=0; i<size; i++) {

            CadreFamily cf = cadreFamilys.get(i);
            Element item = familys.addElement("Item");
            item.addElement("ChengWei").setText(StringUtils.trimToEmpty(CadreConstants.CADRE_FAMILY_TITLE_MAP.get(cf.getTitle())));
            item.addElement("XingMing").setText(StringUtils.trimToEmpty(cf.getRealname()));
            item.addElement("ChuShengRiQi").setText(StringUtils.trimToEmpty(DateUtils.formatDate(cf.getBirthday(), "yyyyMM")));

            String fps = "";
            if(cf!=null && cf.getPoliticalStatus()!=null){
                fps = metaTypeService.getName(cf.getPoliticalStatus());
            }
            item.addElement("ZhengZhiMianMao").setText(StringUtils.trimToEmpty(fps));
            item.addElement("GongZuoDanWeiJiZhiWu").setText(cf==null?"":StringUtils.trimToEmpty(cf.getUnit()));
        }

        setNodeText(doc, "ChengBaoDanWei", "");
        setNodeText(doc, "JiSuanNianLingShiJian", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        setNodeText(doc, "TianBiaoShiJian", "");
        SysUserView currentUser = ShiroHelper.getCurrentUser();
        setNodeText(doc, "TianBiaoRen", currentUser==null?"": currentUser.getRealname());
        setNodeText(doc, "ShenFenZheng", adform.getIdCard());
        setNodeText(doc, "ZhaoPian", adform.getAvatar());
        setNodeText(doc, "Version", "3.2.1.6");


        // 设置发送的内容
        OutputFormat format = new OutputFormat();
        XMLWriter writer = null;
        format.setEncoding("UTF-8");
        writer = new XMLWriter(out,format);
        writer.write(doc);
        writer.close();

        /*OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(out, format);
        writer.write(doc);
        writer.close();*/
    }

    private String getFamilySeg(CadreFamily cf, String ftlPath) throws IOException, TemplateException {

        Map<String,Object> dataMap = new HashMap<>();

        String ftitle = "";
        if(cf!=null){
            ftitle = CadreConstants.CADRE_FAMILY_TITLE_MAP.get(cf.getTitle());
        }
        dataMap.put("ftitle", StringUtils.trimToEmpty(ftitle));
        dataMap.put("fname", cf==null?"":StringUtils.trimToEmpty(cf.getRealname()));

       /* String fage = "";
        if(cf!=null && cf.getBirthday()!=null){
            fage = DateUtils.calAge(cf.getBirthday());
        }*/
        dataMap.put("fage", cf==null?"":DateUtils.formatDate(cf.getBirthday(), DateUtils.YYYYMM));

        String fps = "";
        if(cf!=null && cf.getPoliticalStatus()!=null){
            fps = metaTypeService.getName(cf.getPoliticalStatus());
        }
        dataMap.put("fps", StringUtils.trimToEmpty(fps));

        dataMap.put("fpost", cf==null?"":StringUtils.trimToEmpty(cf.getUnit()));

        return freemarkerService.process(ftlPath, dataMap);
    }
}
