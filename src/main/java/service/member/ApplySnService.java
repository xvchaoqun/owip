package service.member;

import controller.global.OpException;
import domain.member.ApplySn;
import domain.member.ApplySnExample;
import domain.member.MemberApply;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.HttpResponseMethod;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApplySnService extends MemberBaseMapper implements HttpResponseMethod {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ApplySnRangeService applySnRangeService;
    @Autowired
    private ApplyApprovalLogService applyApprovalLogService;

    // 获取即将分配的志愿书编码
    public List<ApplySn> getAssignApplySnList(int count){

        if(count==0) return new ArrayList<>();

        int year = DateUtils.getCurrentYear();
        ApplySnExample example = new ApplySnExample();
        example.createCriteria().andYearEqualTo(year)
                .andIsUsedEqualTo(false)
                .andIsAbolishedEqualTo(false);
        example.setOrderByClause("sn asc");
        return applySnMapper.selectByExampleWithRowbounds(example, new RowBounds(0, count));
    }

    // 组织部审批通过领取志愿书，自动分配志愿书编码
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void assign(int userId, ApplySn applySn) {

        if(applySn==null || applySn.getYear().intValue()
                !=DateUtils.getCurrentYear() || applySn.getIsUsed() || applySn.getIsAbolished()){
            throw new OpException("编码{0}不可用。", applySn.getDisplaySn());
        }

        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);

        if(memberApply!=null) {

            MemberApply record = new MemberApply();
            record.setUserId(userId);
            record.setApplySnId(applySn.getId());
            record.setApplySn(applySn.getDisplaySn());
            memberApplyMapper.updateByPrimaryKeySelective(record);

            use(applySn.getId(), userId);

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    ShiroHelper.getCurrentUserId(),  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_GROW),
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                    "领取志愿书编码：" + applySn.getDisplaySn());
        }

    }

    // 组织部打回领取志愿书，或从领取志愿书中进行移除操作，则清除已分配的志愿书编码
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void clearAssign(int userId) {

        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if(memberApply!=null) {

            Integer applySnId = memberApply.getApplySnId();
            if(applySnId!=null){

                commonMapper.excuteSql("update ow_member_apply set apply_sn_id=null, " +
                        "apply_sn=null where user_id="+ userId);
                clearUse(applySnId);
            }
        }
    }

    // 更新为已使用
    @Transactional
    public void use(int applySnId, int userId){

        ApplySn applySn = applySnMapper.selectByPrimaryKey(applySnId);
        if(applySn == null){
            throw new OpException("编码不存在。");
        }else if(BooleanUtils.isTrue(applySn.getIsUsed())){
            throw new OpException("编码{0}已被使用。", applySn.getDisplaySn());
        }else if(BooleanUtils.isTrue(applySn.getIsAbolished())){
            throw new OpException("编码{0}已作废。", applySn.getDisplaySn());
        }

        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);

        ApplySn record = new ApplySn();
        record.setId(applySnId);
        record.setIsUsed(true);
        record.setUserId(userId);
        record.setPartyId(memberApply.getPartyId());
        record.setBranchId(memberApply.getBranchId());
        record.setAssignTime(new Date());
        applySnMapper.updateByPrimaryKeySelective(record);

        // 更新编码段已使用数量
        Integer rangeId = applySn.getRangeId();
        ApplySnExample example = new ApplySnExample();
        example.createCriteria().andRangeIdEqualTo(rangeId).andIsUsedEqualTo(true);
        int useCount = (int) applySnMapper.countByExample(example);

        applySnRangeService.updateUseCount(rangeId, useCount);
    }

    // 更新为未使用
    @Transactional
    public void clearUse(int applySnId){

        ApplySn applySn = applySnMapper.selectByPrimaryKey(applySnId);
        if(applySn == null){
            throw new OpException("编码不存在。");
        }else if(BooleanUtils.isNotTrue(applySn.getIsUsed())){
            throw new OpException("编码{0}没有被使用。", applySn.getDisplaySn());
        }else if(BooleanUtils.isTrue(applySn.getIsAbolished())){
            throw new OpException("编码{0}已作废。", applySn.getDisplaySn());
        }

        commonMapper.excuteSql("update ow_apply_sn set is_used = 0, user_id = null, " +
                "assign_time=null, party_id=null, branch_id=null where id=" + applySnId);

        // 更新编码段已使用数量
        Integer rangeId = applySn.getRangeId();
        ApplySnExample example = new ApplySnExample();
        example.createCriteria().andRangeIdEqualTo(rangeId).andIsUsedEqualTo(true);
        int useCount = (int) applySnMapper.countByExample(example);

        applySnRangeService.updateUseCount(rangeId, useCount);
    }

    // 换领志愿书
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#applySn.userId")
    public void change(ApplySn applySn, ApplySn newApplySn) {

        if(BooleanUtils.isNotTrue(applySn.getIsUsed())){
            throw new OpException("原编码{0}没有被使用。", applySn.getDisplaySn());
        }else if(BooleanUtils.isTrue(applySn.getIsAbolished())){
            throw new OpException("原编码{0}已作废。", applySn.getDisplaySn());
        }

        int userId = applySn.getUserId();
        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if(memberApply==null){
            throw new OpException("党员发展记录不存在。");
        }
        Integer applySnId = memberApply.getApplySnId();
        if(applySnId==null || applySnId.intValue()!=applySn.getId()){
            throw new OpException("党员发展记录编码有误[{0}]。", memberApply.getApplySn());
        }

        // 直接作废编码，不需要更新已使用的编码数量
        ApplySn record = new ApplySn();
        record.setId(applySn.getId());
        record.setIsAbolished(true);
        applySnMapper.updateByPrimaryKeySelective(record);

        // 清除原申请记录的分配的志愿书（其实可不做这步，因为后面的分配新编码是覆盖操作）
        commonMapper.excuteSql("update ow_member_apply set apply_sn_id=null, " +
                "apply_sn=null where user_id="+ userId);

        // 分配新编码
        assign(userId, newApplySn);
    }

    // 恢复已作废的编码重新使用
    @Transactional
    public void reuse(int applySnId) {

        ApplySn applySn = applySnMapper.selectByPrimaryKey(applySnId);
        if(applySn == null){
            throw new OpException("编码不存在。");
        }else if(BooleanUtils.isNotTrue(applySn.getIsUsed())){
            throw new OpException("编码{0}没有被使用。", applySn.getDisplaySn());
        }else if(BooleanUtils.isNotTrue(applySn.getIsAbolished())){
            throw new OpException("编码{0}没有作废。", applySn.getDisplaySn());
        }

        commonMapper.excuteSql("update ow_apply_sn set is_used = 0, is_abolished=0, " +
                "user_id = null, assign_time=null, party_id=null, branch_id=null where id=" + applySnId);

        // 更新编码段已使用数量
        Integer rangeId = applySn.getRangeId();
        ApplySnExample example = new ApplySnExample();
        example.createCriteria().andRangeIdEqualTo(rangeId).andIsUsedEqualTo(true);
        int useCount = (int) applySnMapper.countByExample(example);

        applySnRangeService.updateUseCount(rangeId, useCount);

        SysUserView uv = applySn.getUser();
        logger.info(log(LogConstants.LOG_MEMBER,
                "恢复已作废的编码<{0}>重新使用，原使用人：{1}，{2}",
                applySn.getDisplaySn(), uv.getRealname(), uv.getCode()));
    }
}
