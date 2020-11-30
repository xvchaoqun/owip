package service.common;

import controller.global.OpException;
import domain.base.MetaType;
import domain.member.MemberApply;
import domain.sys.*;
import ext.service.ExtService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.sys.SysUserInfoMapper;
import service.sys.SysUserService;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ICommonService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    private ExtService extService;

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
        if (growTime != null && drawTime != null && growTime.before(drawTime)) {
            throw new OpException("发展时间应该在领取志愿书之后");
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
            if(sysSync.realname && sysUserInfo.getRealname()!=null){
                ui.setRealname(null);
            }

            // 性别不覆盖
            if(sysSync.gender && sysUserInfo.getGender()!=null){
                ui.setGender(null);
            }
            // 出生年月
            if(sysSync.birth && sysUserInfo.getBirth()!=null){
                ui.setBirth(null);
            }
            // 民族
            if(sysSync.nation && sysUserInfo.getNation()!=null){
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

        if(StringUtils.isBlank(mz)
                || StringUtils.equalsAnyIgnoreCase(mz.trim(),
                "无", "空", "null", "未知", "其他", "其它")
        ) return null;

        mz = mz.trim();
        if(mz.contains("族")){

            if(!mz.endsWith("族")){
                mz = mz.substring(0, mz.indexOf("族")+1);
            }
            return mz;
        }

        Map<Integer, MetaType> nations = CmTag.getMetaTypes("mc_nation");
        Set<String> nationSet = nations.values().stream().map(MetaType::getName).collect(Collectors.toSet());
        if(nationSet.contains(mz+"族")){
            return mz+"族";
        }
        return mz;
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
}
