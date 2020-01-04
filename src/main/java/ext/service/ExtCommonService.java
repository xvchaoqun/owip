package ext.service;

import controller.global.OpException;
import domain.member.MemberApply;
import domain.sys.SysUserView;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.common.ICommonService;
import service.sys.SysUserService;
import sys.utils.DateUtils;

import java.util.Date;

@Service
public class ExtCommonService extends ICommonService {

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
        if (applyTime == null) {
            throw new OpException("提交书面申请书时间不允许为空。");
        }

        Date activeTime = record.getActiveTime();
        if (activeTime != null) {
            DateTime dt = new DateTime(applyTime);
            DateTime afterApplyTime15Days = dt.plusDays(15);
            DateTime afterApplyTime13Months = dt.plusMonths(13);

            if (activeTime.before(afterApplyTime15Days.toDate())
                    || activeTime.after(afterApplyTime13Months.toDate())) {
                throw new OpException("确定为入党积极分子时间与提交申请书时间的间隔必须大于15天，且小于13个月");
            }
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
        if (candidateTime != null && planTime != null) {

            DateTime dt = new DateTime(candidateTime);
            DateTime afterCandidateTime45Days = dt.plusDays(45);
            if (planTime.before(afterCandidateTime45Days.toDate())) {
                throw new OpException("列入发展计划时间与成为发展对象的时间间隔必须大于等于45天");
            }
        }

        Date drawTime = record.getDrawTime();
        if (drawTime != null && planTime != null) {
            DateTime dt = new DateTime(planTime);
            DateTime afterPlanTime8Days = dt.plusDays(8);
            if (drawTime.before(afterPlanTime8Days.toDate())) {
                throw new OpException("领取志愿书时间与列入发展计划的时间间隔必须大于等于8天");
            }
        }
        Date growTime = record.getGrowTime();
        if (growTime != null && drawTime != null) {

            DateTime dt = new DateTime(drawTime);
            DateTime afterDrawTime1Months = dt.plusMonths(1);
            if (growTime.before(drawTime) || growTime.after(afterDrawTime1Months.toDate())) {
                throw new OpException("成为预备党员的时间与领取志愿书的时间间隔须小于等于1个月");
            }
        }

        Date positiveTime = record.getPositiveTime();
        if (growTime != null && positiveTime != null) {

            DateTime dt = new DateTime(growTime);
            DateTime afterGrowTimeOneYear = dt.plusYears(1);
            DateTime afterGrowTimeTwoYear = dt.plusYears(2);
            if (positiveTime.before(afterGrowTimeOneYear.toDate())
                    || positiveTime.after(afterGrowTimeTwoYear.toDate())) {
                throw new OpException("转正时间与成为预备党员的时间间隔必须大于等于1年，且小于等于2年");
            }
        }
    }
}
