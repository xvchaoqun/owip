package service.cadre;

import bean.CadreAdform;
import domain.base.MetaType;
import domain.cadre.CadreEdu;
import domain.cadre.CadreFamliy;
import domain.cadre.CadreFamliyExample;
import domain.cadre.CadreInfo;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
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
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    protected SysConfigService sysConfigService;

    // 获取任免审批表属性值
    public CadreAdform getCadreAdform(int cadreId){

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();

        CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        SysUserView uv = cadre.getUser();

        CadreAdform bean = new CadreAdform();
        bean.setCadreId(cadreId);
        bean.setRealname(uv.getRealname());
        bean.setRealname(uv.getRealname());
        bean.setGender(uv.getGender());
        bean.setIdCard(uv.getIdcard());
        bean.setBirth(cadre.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(cadre.getBirth()));

        File avatar =  new File(springProps.avatarFolder + uv.getAvatar());
        if(!avatar.exists()) avatar = new File(springProps.avatarFolder + FILE_SEPARATOR + springProps.defaultAvatar);
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        bean.setAvatar(base64);
        bean.setNation(cadre.getNation());
        bean.setNativePlace(cadre.getNativePlace());

        bean.setHomeplace(uv.getHomeplace());
        bean.setWorkTime(cadre.getWorkTime()); // 参加工作时间

        bean.setHealth(metaTypeService.getName(uv.getHealth()));
        bean.setProPost(cadre.getProPost());
        bean.setSpecialty(uv.getSpecialty());

        bean.setGrowTime(cadre.getCadreGrowTime());

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
        if(fulltimeEdu!=null){
            Integer eduId = fulltimeEdu.getEduId();
            //String degree = fulltimeEdu.getDegree();
            _fulltimeEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;

            bean.setSchool(fulltimeEdu.getSchool());
            bean.setDepMajor(fulltimeEdu.getMajor()
                    + (StringUtils.isNotBlank(fulltimeEdu.getMajor())?"专业":""));
            _fulltimeMajor = bean.getSchool() + bean.getDepMajor();

            _fulltimeDegree = fulltimeEdu.getDegree(); // 学位
        }
        if(onjobEdu!=null){
            Integer eduId = onjobEdu.getEduId();
            //String degree = onjobEdu.getDegree();
            _onjobEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;

            bean.setInSchool(onjobEdu.getSchool());
            bean.setInDepMajor(onjobEdu.getMajor()
                    + (StringUtils.isNotBlank(onjobEdu.getMajor())?"专业":""));
            _onjobMajor =  bean.getInSchool() + bean.getInDepMajor();

            _onjobDegree = onjobEdu.getDegree();
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
            bean.setPost(schoolName + cadre.getTitle());
        }else{
            bean.setPost(cadre.getTitle());
        }

        // 学习经历
        CadreInfo edu = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_EDU);
        bean.setLearnDesc(edu==null?null:edu.getContent());
        // 奖惩情况，暂时同步其他奖励
        CadreInfo reward = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_REWARD_OTHER);
        bean.setReward(reward == null ? null : reward.getContent());

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
    public void process(CadreAdform adform, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("name", adform.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(adform.getGender()));
        dataMap.put("birth", DateUtils.formatDate(adform.getBirth(), "yyyy.MM"));
        dataMap.put("age", adform.getAge());
        dataMap.put("avatar", adform.getAvatar());
        dataMap.put("nation", adform.getNation());
        dataMap.put("nativePlace", adform.getNativePlace());
        dataMap.put("homeplace", adform.getHomeplace());
        dataMap.put("growTime", DateUtils.formatDate(adform.getGrowTime(), "yyyy.MM"));
        dataMap.put("workTime", DateUtils.formatDate(adform.getWorkTime(), "yyyy.MM"));

        dataMap.put("health", adform.getHealth());
        dataMap.put("proPost", adform.getProPost());
        dataMap.put("specialty", adform.getSpecialty());

        dataMap.put("edu", adform.getEdu());
        dataMap.put("degree", adform.getDegree());
        dataMap.put("schoolDepMajor", adform.getSchoolDepMajor());
        dataMap.put("inEdu", adform.getInEdu());
        dataMap.put("inDegree", adform.getInDegree());
        dataMap.put("inSchoolDepMajor", adform.getInSchoolDepMajor());

        dataMap.put("post", adform.getPost());
        dataMap.put("inPost", adform.getInPost());
        dataMap.put("prePost", adform.getPrePost());
        if(adform.getReward()!=null)
            dataMap.put("reward", freemarkerService.genTitleEditorSegment(null, adform.getReward()));
        dataMap.put("ces", adform.getCes());
        dataMap.put("reason", adform.getReason());

        dataMap.put("learnDesc", "");
        dataMap.put("workDesc", "");

        if(adform.getLearnDesc()!=null)
            dataMap.put("learnDesc", freemarkerService.genTitleEditorSegment("学习经历", adform.getLearnDesc()));
        if(adform.getWorkDesc()!=null)
            dataMap.put("workDesc", freemarkerService.genTitleEditorSegment("工作经历", adform.getWorkDesc()));
        if(adform.getTrainDesc()!=null)
            dataMap.put("trainDesc", freemarkerService.genTitleEditorSegment(null, adform.getTrainDesc()));

        String famliy = "";
        List<CadreFamliy> cadreFamliys = adform.getCadreFamliys();
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
            String style = pElement.attr("style");
            int type = 0;
            if (StringUtils.contains(style, "2em")
                    || StringUtils.contains(style, "text-indent"))
                type = 1;
            if (StringUtils.contains(style, "5em"))
                type = 2;

            String rowStr = StringUtils.trimToEmpty(pElement.text());
            //System.out.println(rowStr);
            rowStr = rowStr.replaceFirst("[ |\\s]+", "  ").replaceFirst("-", "--");
            str +=  rowStr + "\r\n";
            //System.out.println(rowStr);
        }

        if (size == 0) {
            str += StringUtils.trimToEmpty(doc.text())
                    .replaceFirst("[ |\\s]+", "  ").replaceFirst("-", "--");
        }

        //str = str.replace("-", "--");

        //System.out.println(str);

        return str;
    }

    // 输出中组部任免审批表
    public void zzb(CadreAdform adform, Writer out) throws IOException, DocumentException {

        Document doc = getZZBTemplate();

        setNodeText(doc, "XingMing", adform.getRealname());
        setNodeText(doc, "XingBie", SystemConstants.GENDER_MAP.get(adform.getGender()));
        setNodeText(doc, "ChuShengNianYue", DateUtils.formatDate(adform.getBirth(), "yyyyMM"));
        setNodeText(doc, "MinZu", adform.getNation());
        setNodeText(doc, "JiGuan", adform.getNativePlace());
        setNodeText(doc, "ChuShengDi", adform.getHomeplace());
        setNodeText(doc, "RuDangShiJian", DateUtils.formatDate(adform.getGrowTime(), "yyyyMM"));
        setNodeText(doc, "CanJiaGongZuoShiJian", DateUtils.formatDate(adform.getWorkTime(), "yyyyMM"));
        setNodeText(doc, "JianKangZhuangKuang", adform.getHealth());
        setNodeText(doc, "ZhuanYeJiShuZhiWu", adform.getProPost());
        setNodeText(doc, "ShuXiZhuanYeYouHeZhuanChang", adform.getSpecialty());

        setNodeText(doc, "QuanRiZhiJiaoYu_XueLi", adform.getEdu());
        setNodeText(doc, "QuanRiZhiJiaoYu_XueWei", adform.getDegree());
        setNodeText(doc, "QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi", adform.getSchool());
        setNodeText(doc, "QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi", adform.getDepMajor());
        setNodeText(doc, "ZaiZhiJiaoYu_XueLi", adform.getInEdu());
        setNodeText(doc, "ZaiZhiJiaoYu_XueWei", adform.getInDegree());
        setNodeText(doc, "ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi", adform.getInSchool());
        setNodeText(doc, "ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi", adform.getInDepMajor());

        setNodeText(doc, "XianRenZhiWu", adform.getPost());
        setNodeText(doc, "NiRenZhiWu", adform.getInPost());
        setNodeText(doc, "NiMianZhiWu", adform.getPrePost());

        String jianli = "";
        if(StringUtils.isNotBlank(adform.getLearnDesc())){
            jianli += adform.getLearnDesc();
        }
        if(StringUtils.isNotBlank(adform.getWorkDesc())){
            jianli += adform.getWorkDesc();
        }

        setNodeText(doc, "JianLi", html2Paragraphs(jianli));
        setNodeText(doc, "JiangChengQingKuang", html2Paragraphs(adform.getReward()));
        setNodeText(doc, "NianDuKaoHeJieGuo", adform.getCes());
        setNodeText(doc, "RenMianLiYou", adform.getReason());

        // 家庭成员
        Element famliys = (Element)doc.selectSingleNode("//Person//JiaTingChengYuan");
        List<CadreFamliy> cadreFamliys = adform.getCadreFamliys();
        int size = Math.min(cadreFamliys.size(), 10);
        for (int i=0; i<size; i++) {

            CadreFamliy cf = cadreFamliys.get(i);
            Element item = famliys.addElement("Item");
            item.addElement("ChengWei").setText(StringUtils.trimToEmpty(SystemConstants.CADRE_FAMLIY_TITLE_MAP.get(cf.getTitle())));
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
}
