package service.common;

import controller.global.OpException;
import domain.member.MemberApply;
import domain.sys.SysUserView;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import service.sys.SysUserService;
import sys.utils.DateUtils;

import java.util.Date;

public abstract class ICommonService {

    @Autowired
    private SysUserService sysUserService;

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
            DateTime afterActiveTimeTwoYear = dt.plusYears(2);
            if (candidateTime.before(afterActiveTimeOneYear.toDate())
                    || candidateTime.after(afterActiveTimeTwoYear.toDate())) {
                throw new OpException("确定为发展对象时间与成为积极分子的时间间隔必须大于等于1年，且小于等于2年");
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
}
