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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.member.MemberService;
import service.sys.SysConfigService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
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
        String _fulltimeDegreee = "";
        String _fulltimeMajor = "";
        String _onjobEdu = "";
        String _onjobMajor = "";
        CadreEdu[] cadreEdus = cadre.getCadreEdus();
        CadreEdu fulltimeEdu = cadreEdus[0];
        CadreEdu onjobEdu = cadreEdus[1];
        if(fulltimeEdu!=null){
            Integer eduId = fulltimeEdu.getEduId();
            //String degree = fulltimeEdu.getDegree();
            _fulltimeEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;
            _fulltimeMajor = fulltimeEdu.getSchool() + fulltimeEdu.getDep() + fulltimeEdu.getMajor()
                    + (StringUtils.isNotBlank(fulltimeEdu.getMajor())?"专业":"");

            _fulltimeDegreee = fulltimeEdu.getDegree(); // 学位
        }
        if(onjobEdu!=null){
            Integer eduId = onjobEdu.getEduId();
            //String degree = onjobEdu.getDegree();
            _onjobEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;
            _onjobMajor = onjobEdu.getSchool() + onjobEdu.getDep() + onjobEdu.getMajor()
                    + (StringUtils.isNotBlank(onjobEdu.getMajor())?"专业":"");
        }
        // 全日制最高学历
        bean.setEdu(_fulltimeEdu);
        bean.setDegree(_fulltimeDegreee);
        bean.setSchoolDepMajor(_fulltimeMajor);
        bean.setInDegree(_onjobEdu);
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
    public void process(int cadreId, Writer out) throws IOException, TemplateException {

        CadreAdform bean = getCadreAdform(cadreId);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("name", bean.getRealname());
        dataMap.put("gender", SystemConstants.GENDER_MAP.get(bean.getGender()));
        dataMap.put("birth", DateUtils.formatDate(bean.getBirth(), "yyyy.MM"));
        dataMap.put("age", bean.getAge());
        dataMap.put("avatar", bean.getAvatar());
        dataMap.put("nation", bean.getNation());
        dataMap.put("nativePlace", bean.getNativePlace());
        dataMap.put("homeplace", bean.getHomeplace());
        dataMap.put("growTime", DateUtils.formatDate(bean.getGrowTime(), "yyyy.MM"));
        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), "yyyy.MM"));

        dataMap.put("health", bean.getHealth());
        dataMap.put("proPost", bean.getProPost());
        dataMap.put("specialty", bean.getSpecialty());

        dataMap.put("edu", bean.getEdu());
        dataMap.put("degree", bean.getDegree());
        dataMap.put("schoolDepMajor", bean.getSchoolDepMajor());
        dataMap.put("inDegree", bean.getInDegree());
        dataMap.put("inSchoolDepMajor", bean.getInSchoolDepMajor());

        dataMap.put("post", bean.getPost());
        dataMap.put("inPost", bean.getInPost());
        dataMap.put("prePost", bean.getPrePost());
        if(bean.getReward()!=null)
            dataMap.put("reward", freemarkerService.genTitleEditorSegment(null, bean.getReward()));
        dataMap.put("ces", bean.getCes());
        dataMap.put("reason", bean.getReason());

        dataMap.put("learnDesc", "");
        dataMap.put("workDesc", "");

        if(bean.getLearnDesc()!=null)
            dataMap.put("learnDesc", freemarkerService.genTitleEditorSegment("学习经历", bean.getLearnDesc()));
        if(bean.getWorkDesc()!=null)
            dataMap.put("workDesc", freemarkerService.genTitleEditorSegment("工作经历", bean.getWorkDesc()));
        if(bean.getTrainDesc()!=null)
            dataMap.put("trainDesc", freemarkerService.genTitleEditorSegment(null, bean.getTrainDesc()));

        String famliy = "";
        List<CadreFamliy> cadreFamliys = bean.getCadreFamliys();
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
