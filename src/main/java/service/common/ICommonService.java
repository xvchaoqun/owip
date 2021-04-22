package service.common;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadreEdu;
import domain.cadre.CadreEva;
import domain.cadre.CadreEvaExample;
import domain.member.MemberApply;
import domain.sys.*;
import ext.service.ExtService;
import freemarker.EduSuffix;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.cadre.CadreEvaMapper;
import persistence.sys.SysUserInfoMapper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ICommonService {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    protected ExtService extService;
    @Autowired
    protected CadreEvaMapper cadreEvaMapper;

    // 同步用户头像
    public String syncAvatar(Integer userId){
        return null;
    };

    // 党员发展时间节点限制
    public void checkMemberApplyData(MemberApply record) {

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Date birth = uv.getBirth();
        if (birth != null && DateUtils.intervalYearsUntilNow(birth) < 18) {
            throw new OpException("未满18周岁，不能申请入党。");
        }

        Date applyTime = record.getApplyTime();
        if ( applyTime == null) {
            throw new OpException("提交书面申请书时间不允许为空。");
        }

        Date activeTime = record.getActiveTime();
        if (activeTime != null && activeTime.before(applyTime)) {
            throw new OpException("确定为入党积极分子时间不能早于提交书面申请书时间");
        }

        Date candidateTime = record.getCandidateTime();
        if (activeTime != null && candidateTime != null) {
            DateTime dt = new DateTime(activeTime);
            DateTime afterActiveTimeOneYear = dt.plusYears(1);
            if (candidateTime.before(afterActiveTimeOneYear.toDate())) {
                throw new OpException("确定为发展对象时间与成为积极分子的时间间隔必须大于等于1年");
            }
        }

        Date candidateTrainStartTime = record.getCandidateTrainStartTime();
        if (candidateTrainStartTime != null && activeTime != null && candidateTrainStartTime.before(activeTime)) {
            throw new OpException("培训起始时间应该在确定为入党积极分子之后");
        }

        Date candidateTrainEndTime = record.getCandidateTrainEndTime();
        if (candidateTrainEndTime != null && candidateTrainStartTime != null
                && candidateTrainEndTime.before(candidateTrainStartTime)) {
            throw new OpException("培训结束时间应该在培训起始时间之后");
        }

        Date planTime = record.getPlanTime();
        if (candidateTime != null && planTime != null && planTime.before(candidateTime)) {
            throw new OpException("列入发展计划时间应该在确定为发展对象之后");
        }

        Date drawTime = record.getDrawTime();
        if (drawTime != null && planTime != null && drawTime.before(planTime)) {
            throw new OpException("领取志愿书时间应该在列入发展计划之后");
        }
        Date growTime = record.getGrowTime();
        if (!CmTag.getBoolProperty("ignore_plan_and_draw")){
            if (growTime != null && candidateTime != null && growTime.before(candidateTime)) {
                throw new OpException("发展时间应该在确定为发展对象之后");
            }
        }else {
            if (growTime != null && drawTime != null && growTime.before(drawTime)) {
                throw new OpException("发展时间应该在领取志愿书之后");
            }
        }

        Date positiveTime = record.getPositiveTime();
        if (growTime != null && positiveTime!=null && positiveTime.before(growTime)) {
            throw new OpException("转正时间应该在入党时间之后");
        }
    }

     // 同步信息过滤（部分信息只同步第一次）
    public void syncFilter(SysUserInfo ui, TeacherInfo ti, StudentInfo si) {

        SysUser _sysUser = sysUserService.dbFindById(ui.getUserId());
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(ui.getUserId());

        // 系统同步覆盖设置
        SyncStatus sysSync = new SyncStatus(CmTag.getStringProperty("sync"));

        if (sysUserInfo == null) {
            ui.setNativePlace(extService.getExtNativePlace(_sysUser.getSource(), _sysUser.getCode()));
        } else {
            // 个人同步覆盖设置
            SyncStatus userSync = new SyncStatus(sysUserInfo.getSync());
            sysSync.combine(userSync);

            // 姓名不覆盖
            if(sysSync.realname && StringUtils.isNotBlank(sysUserInfo.getRealname())){
                ui.setRealname(null);
            }

            // 性别不覆盖
            if(sysSync.gender && sysUserInfo.getGender()!=null
                && SystemConstants.GENDER_MAP.containsKey(sysUserInfo.getGender())){

                ui.setGender(null);
            }
            // 出生年月
            if(sysSync.birth && sysUserInfo.getBirth()!=null){
                ui.setBirth(null);
            }
            // 民族
            if(sysSync.nation && StringUtils.isNotBlank(sysUserInfo.getNation())){
                ui.setNation(null);
            }

            // 籍贯
            if (StringUtils.isBlank(sysUserInfo.getNativePlace())) {
                ui.setNativePlace(extService.getExtNativePlace(_sysUser.getSource(), _sysUser.getCode()));
            } else if(sysSync.nativePlace) {
                ui.setNativePlace(null);
            }
            // 出生地
            if (sysSync.homeplace && StringUtils.isNotBlank(sysUserInfo.getHomeplace())) {
                ui.setHomeplace(null);
            }
            // 户籍地
            if (sysSync.household && StringUtils.isNotBlank(sysUserInfo.getHousehold())) {
                ui.setHousehold(null);
            }

            if (sysSync.mobile && StringUtils.isNotBlank(sysUserInfo.getMobile())) {
                ui.setMobile(null);
            }

            if (sysSync.email && StringUtils.isNotBlank(sysUserInfo.getEmail())) {
                ui.setEmail(null);
            }

            if (sysSync.phone && StringUtils.isNotBlank(sysUserInfo.getPhone())) {
                ui.setPhone(null);
            }

            if (sysSync.homePhone && StringUtils.isNotBlank(sysUserInfo.getHomePhone())) {
                ui.setHomePhone(null);
            }
            if (sysSync.avatar && StringUtils.isNotBlank(sysUserInfo.getAvatar())) {
                ui.setAvatar(null);
            }
        }

        if(ti!=null){
            // 专业技术职务不覆盖
            if(sysSync.proPost && StringUtils.isNotBlank(ti.getProPost())){
                ti.setProPost(null);
            }
            // 专业技术职务级别不覆盖
            if(sysSync.proPostLevel && StringUtils.isNotBlank(ti.getProPostLevel())){
                ti.setProPostLevel(null);
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("formatNation(\"回族\") = " + formatNation("回族"));
        System.out.println("formatNation(\"回族（区内）\") = " + formatNation("回族（区内）"));
    }

    // 人事库中的民族转换成标准格式
    public static String formatNation(String mz){

        mz = StringUtils.trim(mz);

        // 处理加备注的民族，比如：回族（区内）
        if(mz.contains("族") && !mz.endsWith("族")){
            mz = mz.substring(0, mz.indexOf("族"));
        }

        mz = StringUtils.appendIfMissing(mz.trim(), "族");

        Map<Integer, MetaType> nations = CmTag.getMetaTypes("mc_nation");
        Set<String> nationSet = nations.values().stream().map(MetaType::getName).collect(Collectors.toSet());
        if(nationSet.contains(mz)){
            return mz;
        }

        return null;
    }

    // 人事库中的职级转换成标准格式
    public static String formatProPostLevel(String proPostLevel){

        if(StringUtils.contains(proPostLevel, "正高")){
            return "正高";
        }

        if(StringUtils.contains(proPostLevel, "副高")){
            return "副高";
        }

        if(StringUtils.contains(proPostLevel, "中级")){
            return "中级";
        }

        if(StringUtils.contains(proPostLevel, "初级")){
            return "初级";
        }

        return null;
    }

    // 任免审批表学习经历表述
    public String getResumeOfCadreEdu(CadreEdu cadreEdu, boolean isOnjob) {

        String detail = cadreEdu.getResume();
        String major = StringUtils.trimToNull(cadreEdu.getMajor());
        if (major != null) {
            major = StringUtils.trimToEmpty(StringUtils.appendIfMissing(cadreEdu.getMajor(), "专业"));
        } else {
            major = "";
        }

        String note = StringUtils.isNotBlank(cadreEdu.getNote()) ?
                String.format(BooleanUtils.isTrue(cadreEdu.getNoteBracketsExclude()) ? "%s" : "（%s）",
                        cadreEdu.getNote()) : "";

        if (StringUtils.isBlank(detail)) {
            if (!isOnjob) {
                // 全日制
                detail = String.format("%s%s%s%s%s", StringUtils.trimToEmpty(cadreEdu.getSchool()),
                        StringUtils.trimToEmpty(cadreEdu.getDep()),
                        major,
                        StringUtils.trimToEmpty(EduSuffix.getEduSuffix(cadreEdu.getEduId(), false)),
                        note);
            } else {
                // 在职
                if (CmTag.getBoolProperty("ad_show_onjob")) {
                    detail = String.format("在%s%s%s%s%s学习%s%s%s", StringUtils.trimToEmpty(cadreEdu.getSchool()),
                            StringUtils.trimToEmpty(cadreEdu.getDep()),
                            major,
                            "在职",
                            StringUtils.trimToEmpty(EduSuffix.getEduSuffix(cadreEdu.getEduId(), true)),
                            cadreEdu.getIsGraduated() ? "毕业" : "",
                            (CmTag.getBoolProperty("ad_show_degree") && cadreEdu.getHasDegree()) ?
                                    StringUtils.appendIfMissing(String.format("，获%s", cadreEdu.getDegree()), "学位") : "",
                            note);
                } else {
                    detail = String.format("%s%s%s%s%s", StringUtils.trimToEmpty(cadreEdu.getSchool()),
                            StringUtils.trimToEmpty(cadreEdu.getDep()),
                            major,
                            StringUtils.trimToEmpty(EduSuffix.getEduSuffix(cadreEdu.getEduId(), true)),
                            note);
                }
            }
        }

        return detail;
    }

    //年度考核结果 表述
    public String getEvaResult(int cadreId){

        int evaYears = CmTag.getIntProperty("evaYears", 3);
        Integer currentYear = DateUtils.getCurrentYear();

        String evaResult = CmTag.getStringProperty("defaultEvaResult"); // {0}年均为合格

        if(StringUtils.isNotBlank(evaResult)) {
            List<Integer> years = new ArrayList<>();
            for (Integer i = 0; i < evaYears; i++) {
                years.add(currentYear - evaYears + i);
            }
            evaResult = StringUtils.replace(evaResult, "{0}", StringUtils.join(years, "、"));
        }

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
                    evaMap.put(year, year + "年：" + CmTag.getMetaTypeName(type));
                }
                ArrayList<String> evaList = new ArrayList<>(evaMap.values());
                Collections.reverse(evaList);
                evaResult = StringUtils.join(evaList, "；");
            }
        }

        return evaResult;
    }
}
