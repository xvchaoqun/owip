package service.dp;

import bean.DpInfoForm;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.dp.*;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.cadre.CadreUtils;
import service.common.FreemarkerService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Service
public class DpInfoFormService extends DpBaseMapper{

    @Autowired
    protected DpWorkService dpWorkService;
    @Autowired
    protected FreemarkerService freemarkerService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected DpRewardService dpRewardService;
    @Autowired
    protected DpEduService dpEduService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected DpMemberService dpMemberService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    //导出基本情况登记表
    public void export(Integer[] userIds, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, TemplateException {

        if (userIds == null) return;

        if (userIds.length == 1) {

            int userId = userIds[0];
            SysUserView uv = CmTag.getUserById(userId);
            //输出文件
            String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                    + " 基本情况登记表 " + uv.getRealname()  + ".doc";
            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
            response.setContentType("application/msword;charset=UTF-8");

            process(userId, response.getWriter());
        }else {

            Map<String, File> fileMap = new LinkedHashMap<>();
            String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                    DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "infoForms";
            FileUtils.mkdirs(tmpdir, false);

            Set<String> filenameSet = new HashSet<>();
            for (int userId : userIds) {
                SysUserView uv = CmTag.getUserById(userId);
                String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd")
                        + " 基本情况登记表 " + uv.getRealname() + ".doc";

                // 保证文件名不重复
                if(filenameSet.contains(filename)){
                    filename = uv.getCode() + " " + filename;
                }
                filenameSet.add(filename);

                String filepath = tmpdir + FILE_SEPARATOR + filename;
                FileOutputStream output = new FileOutputStream(new File(filepath));
                OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

                process(userId, osw);

                fileMap.put(filename, new File(filepath));
            }

            String filename = String.format("%s基本情况登记表",
                    CmTag.getSysConfig().getSchoolName());
            DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.zip(fileMap, filename, request, response);
            FileUtils.deleteDir(new File(tmpdir));
        }
    }

    //输出基本情况登记表
    public void process(Integer userId, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMap(userId);
        dataMap.put("fillDate", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));

        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        dataMap.put("schoolEmail", CmTag.getStringProperty("zzb_email"));
        freemarkerService.process("/dp/dpInfoform.ftl", dataMap, out);
    }

    public Map<String, Object> getDataMap(Integer userId) throws IOException, TemplateException {

        DpInfoForm bean = getDpInfoForm(userId);

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
        dataMap.put("hp", bean.getHomePlace());
        dataMap.put("dpPartyName",bean.getDpParty().getName());
        dataMap.put("dpGrowTime", DateUtils.formatDate(bean.getDpGrowTime(), DateUtils.YYYYMM));

        dataMap.put("workTime", DateUtils.formatDate(bean.getWorkTime(), DateUtils.YYYYMM));

        dataMap.put("health", bean.getHealth());
        // 专业技术职务
        dataMap.put("proPost", bean.getProPost());
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
        dataMap.put("partTimeJob", bean.getPartTimeJob());

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        String resumeDesc =StringUtils.trimToEmpty(freemarkerService.genTitleEditorSegment(null,
                bean.getResumeDesc(), true, 440, "/common/oldTitleEditor.ftl"));
        dataMap.put("resumeDesc", StringUtils.trimToNull(resumeDesc));
        dataMap.put("reward", freemarkerService.genTitleEditorSegment(bean.getReward(), false, false, 440));
        dataMap.put("otherReward", bean.getOtherReward());
        dataMap.put("ces", bean.getEva());
        dataMap.put("trainDesc", freemarkerService.genTitleEditorSegment(bean.getTrainDesc(), true, false, 440));
        dataMap.put("otherRewardDesc", freemarkerService.genTitleEditorSegment(bean.getReward() != null ? bean.getReward() + "；": "" + bean.getOtherReward(), false, false, 440));
        dataMap.put("achievements", bean.getAchievements());
        dataMap.put("address", bean.getAddress());
        dataMap.put("postalCode",bean.getPostalCode());
        dataMap.put("mobile", bean.getMobile());
        dataMap.put("phone", bean.getPhone());
        dataMap.put("email", bean.getEmail());
        dataMap.put("fax", bean.getFax());

        {
            String familys = "";
            List<DpFamily> dpFamilies = bean.getDpFamilies();
            int size = dpFamilies.size();
            for (int i = 0; i < 6; i++) {
                if (size <= i)
                    familys += getFamilySeg(null, "/dp/dpFamily.ftl");
                else
                    familys += getFamilySeg(dpFamilies.get(i), "/dp/dpFamily.ftl");
            }
            dataMap.put("familys", familys);
        }

        return dataMap;
    }

    private String getFamilySeg(DpFamily bean, String ftlPath) throws IOException, TemplateException {

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

    //获得基本情况登记表的信息
    public DpInfoForm getDpInfoForm(Integer userId) {

        DpInfoForm bean = new DpInfoForm();
        bean.setUserId(userId);
        SysUserView uv = CmTag.getUserById(userId);
        DpMemberView dpMember = dpMemberService.findByUserId(userId);
        if (dpMember != null){
            bean.setDpPartyId(dpMember.getPartyId());
            bean.setTitle(dpMember.getDpPost());
            bean.setPartTimeJob(dpMember.getPartTimeJob());
            bean.setDpGrowTime(dpMember.getDpGrowTime());
            bean.setAddress(dpMember.getAddress());
            bean.setEmail(dpMember.getEmail());
            bean.setMobile(dpMember.getMobile());
            bean.setTrainDesc(dpMember.getTrainState());
        }
        bean.setPhone(uv.getPhone());

        //bean.setPostalCode();
        //bean.setFax();

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null){
            bean.setProPost(cadre.getProPost());
            bean.setWorkTime(cadre.getWorkTime());
        }

        bean.setCode(uv.getCode());
        bean.setRealname(CmTag.realnameWithEmpty(uv.getRealname()));
        bean.setGender(uv.getGender());
        bean.setBirth(uv.getBirth());
        bean.setAge(DateUtils.intervalYearsUntilNow(uv.getBirth()));

        File avatar = new File(springProps.avatarFolder + uv.getAvatar());
        if (!avatar.exists()) avatar = new File(ConfigUtil.defaultHomePath()
                + FILE_SEPARATOR + "img" + FILE_SEPARATOR + "default.png");
        //头像大小
        bean.setAvatarWidth(143);
        bean.setAvatarHeight(198);
        try {
            BufferedImage _avatar = ImageIO.read(avatar);
            bean.setAvatarWidth(_avatar.getWidth());
            bean.setAvatarHeight(_avatar.getHeight());
        }catch (IOException e){
            logger.error("异常", e);
        }
        String base64 = ImageUtils.encodeImgageToBase64(avatar);
        bean.setAvatar(base64);
        bean.setNation(uv.getNation());
        bean.setNativePlace(uv.getNativePlace());
        bean.setHomePlace(uv.getHomeplace());
        bean.setSpecialty(uv.getSpecialty());
        bean.setHealth(metaTypeService.getName(uv.getHealth()));

        DpFamilyExample example = new DpFamilyExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("sort_order asc");
        List<DpFamily> dpFamilies = dpFamilyMapper.selectByExample(example);
        bean.setDpFamilies(dpFamilies);

        //简历(工作经历)
        String _resume = null;
        if (dpWorkService.list(userId).size() > 0 ){
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("dpWorks", dpWorkService.list(userId));
            _resume = FreemarkerService.freemarker(dataMap, "/dp/dpWork.ftl");
        }
        bean.setResumeDesc(StringUtils.defaultIfBlank(_resume, ""));

        //奖励情况
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("dpRewards", dpRewardService.list(userId));
        String _dpReward = FreemarkerService.freemarker(dataMap, "/dp/dpReward.ftl");
        bean.setReward(StringUtils.defaultIfBlank(_dpReward, ""));
        /*Map<String, Object> dataMapOther = new HashMap<>();
        dataMapOther.put("cadreRewards", dpRewardService.list(userId, metaTypeService.metaTypes("mt_2tinxl")));
        String _otherReward = FreemarkerService.freemarker(dataMap, "/cadre/cadreReward.ftl");
        bean.setOtherReward(StringUtils.defaultIfBlank(_otherReward, ""));*/

        //显示三年的年度考核结果
        Integer evaYears = CmTag.getIntProperty("evaYears");
        if (evaYears == null) evaYears = 3;
        Integer currentYear = DateUtils.getCurrentYear();
        List<Integer> years = new ArrayList<>();
        for (Integer i = 0; i < evaYears; i++){
            years.add(currentYear - evaYears + i);
        }
        String evaResult = StringUtils.join(years, "、") + "年均为合格";//默认
        {
            Map<Integer, String> evaMap = new LinkedHashMap<>();
            DpEvaExample dpEvaExample = new DpEvaExample();
            dpEvaExample.createCriteria().andUserIdEqualTo(userId)
                    .andYearBetween(currentYear - evaYears, currentYear);
            dpEvaExample.setOrderByClause("year desc");
            List<DpEva> dpEvas = dpEvaMapper.selectByExampleWithRowbounds(dpEvaExample,new RowBounds(0, evaYears));
            if (dpEvas.size() > 0){
                for (DpEva dpEva : dpEvas){
                    int year = dpEva.getYear();
                    int type = dpEva.getType();
                    evaMap.put(year, year + "年" + metaTypeService.getName(type));
                }
                ArrayList<String> evaList = new ArrayList<>(evaMap.values());
                Collections.reverse(evaList);
                evaResult = StringUtils.join(evaList, "；");
            }
        }
        bean.setEva(evaResult);


        String _fulltimeEdu = ""; // 全日制最高学历
        String _fulltimeDegree = ""; // 全日制最高学位
        String _onjobEdu = ""; // 在职最高学历
        String _onjobDegree = ""; // 在职最高学位

        MetaType fullltimeType = CmTag.getMetaTypeByCode("mt_fulltime");
        List<DpEdu> fulltimeHighDegrees = dpEduService.getHighDegrees(userId, fullltimeType.getId());

        if (fulltimeHighDegrees.size() > 1) { // 有多个学位的情况（只处理前两个学位）

            DpEdu firstHighDegree = fulltimeHighDegrees.get(0);
            DpEdu secondHighDegree = fulltimeHighDegrees.get(1);

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

            DpEdu fulltimeHighDegree = fulltimeHighDegrees.get(0);
            DpEdu fulltimeHighEdu = dpEduService.getHighEdu(userId, fullltimeType.getId());
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
        }else{ // 没有学位的情况

            DpEdu fulltimeHighEdu = dpEduService.getHighEdu(userId, fullltimeType.getId());
            if (fulltimeHighEdu != null && !isJxxx(fulltimeHighEdu.getEduId())) {
                _fulltimeEdu = CmTag.getEduName(fulltimeHighEdu.getEduId());

                bean.setSameSchool(true);
                bean.setSchoolDepMajor1(StringUtils.trimToEmpty(fulltimeHighEdu.getSchool())
                        + StringUtils.trimToEmpty(fulltimeHighEdu.getDep()));
                bean.setSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(fulltimeHighEdu.getMajor())));
            }
        }

        MetaType onjobType = CmTag.getMetaTypeByCode("mt_onjob");
        List<DpEdu> onjobHighDegrees = dpEduService.getHighDegrees(userId, onjobType.getId());

        if (onjobHighDegrees.size() > 1) { // 有多个学位的情况（只处理前两个学位）

            DpEdu firstHighDegree = onjobHighDegrees.get(0);
            DpEdu secondHighDegree = onjobHighDegrees.get(1);

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

            DpEdu onjobHighDegree = onjobHighDegrees.get(0);
            DpEdu onjobHighEdu = dpEduService.getHighEdu(userId, onjobType.getId());
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
        }else{ // 没有学位的情况

            DpEdu onjobHighEdu = dpEduService.getHighEdu(userId, onjobType.getId());
            if (onjobHighEdu != null && !isJxxx(onjobHighEdu.getEduId())) {
                _onjobEdu = CmTag.getEduName(onjobHighEdu.getEduId());

                bean.setSameInSchool(true);
                bean.setInSchoolDepMajor1(StringUtils.trimToEmpty(onjobHighEdu.getSchool())
                        + StringUtils.trimToEmpty(onjobHighEdu.getDep()));
                bean.setInSchoolDepMajor2(StringUtils.trimToEmpty(CadreUtils.major(onjobHighEdu.getMajor())));
            }
        }

        // 全日制最高学历
        bean.setEdu(_fulltimeEdu);
        bean.setDegree(_fulltimeDegree);

        // 在职最高学历
        bean.setInEdu(_onjobEdu);
        bean.setInDegree(_onjobDegree);

        return bean;
    }

    // 判断是否是进修学习，进修学习不能进入任免审批表
    public boolean isJxxx(Integer eduId) {

        MetaType jxxx = CmTag.getMetaTypeByCode("mt_edu_jxxx");
        return (jxxx != null && eduId != null && jxxx.getId() == eduId);
    }

}
