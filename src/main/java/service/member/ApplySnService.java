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
import org.springframework.cache.annotation.Caching;
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
    public List<ApplySn> getAssignApplySnList(int count) {

        if (count == 0) return new ArrayList<>();

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

        if (applySn == null || applySn.getYear().intValue()
                != DateUtils.getCurrentYear() || applySn.getIsUsed() || applySn.getIsAbolished()) {
            throw new OpException("编码{0}不可用。", applySn.getDisplaySn());
        }

        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);

        if (memberApply != null) {

            MemberApply record = new MemberApply();
            record.setUserId(userId);
            record.setApplySnId(applySn.getId());
            record.setApplySn(applySn.getDisplaySn());
            memberApplyMapper.updateByPrimaryKeySelective(record);

            use(applySn.getId(), userId);

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
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
        if (memberApply != null) {

            Integer applySnId = memberApply.getApplySnId();
            if (applySnId != null) {

                commonMapper.excuteSql("update ow_member_apply set apply_sn_id=null, " +
                        "apply_sn=null where user_id=" + userId);
                clearUse(applySnId);
            }
        }
    }

    // 更新为已使用
    @Transactional
    public void use(int applySnId, int userId) {

        ApplySn applySn = applySnMapper.selectByPrimaryKey(applySnId);
        if (applySn == null) {
            throw new OpException("编码不存在。");
        } else if (BooleanUtils.isTrue(applySn.getIsUsed())) {
            throw new OpException("编码{0}已被使用。", applySn.getDisplaySn());
        } else if (BooleanUtils.isTrue(applySn.getIsAbolished())) {
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
    public void clearUse(int applySnId) {

        ApplySn applySn = applySnMapper.selectByPrimaryKey(applySnId);
        if (applySn == null) {
            throw new OpException("编码不存在。");
        } else if (BooleanUtils.isNotTrue(applySn.getIsUsed())) {
            throw new OpException("编码{0}没有被使用。", applySn.getDisplaySn());
        } else if (BooleanUtils.isTrue(applySn.getIsAbolished())) {
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
    public void change(ApplySn applySn, ApplySn newApplySn, byte opType) {

        if (BooleanUtils.isNotTrue(applySn.getIsUsed())) {
            throw new OpException("原编码{0}没有被使用。", applySn.getDisplaySn());
        } else if (BooleanUtils.isTrue(applySn.getIsAbolished())) {
            throw new OpException("原编码{0}已作废。", applySn.getDisplaySn());
        }

        int userId = applySn.getUserId();
        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if (memberApply == null) {
            throw new OpException("党员发展记录不存在。");
        }
        Integer applySnId = memberApply.getApplySnId();
        if (applySnId == null || applySnId.intValue() != applySn.getId()) {
            throw new OpException("党员发展记录编码有误[{0}]。", memberApply.getApplySn());
        }

        if (opType == 1) { // 作废原编码，不需要更新已使用的编码数量

            ApplySn record = new ApplySn();
            record.setId(applySn.getId());
            record.setIsAbolished(true);
            applySnMapper.updateByPrimaryKeySelective(record);

        } else if (opType == 2) { // 原编码返回使用, 需要更新已使用的编码数量

            commonMapper.excuteSql("update ow_apply_sn set is_used = 0, is_abolished=0, " +
                    "user_id = null, assign_time=null, party_id=null, branch_id=null where id=" + applySnId);

            // 更新编码段已使用数量
            Integer rangeId = applySn.getRangeId();
            updateUseCount(rangeId);

        } else {
            throw new IllegalArgumentException();
        }

        // 清除原申请记录的分配的志愿书（其实可不做这步，因为后面的分配新编码是覆盖操作）
        commonMapper.excuteSql("update ow_member_apply set apply_sn_id=null, " +
                "apply_sn=null where user_id=" + userId);

        // 分配新编码
        assign(userId, newApplySn);
    }


    // 调换志愿书编码
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "MemberApply", key = "#applySn.userId"),
            @CacheEvict(value = "MemberApply", key = "#applySn2.userId")
    })
    public void exchange(ApplySn applySn, ApplySn applySn2) {

        if (applySn.getId().intValue() == applySn2.getId()) {
            throw new OpException("请选择不同的志愿书编码进行调换。");
        }

        if (BooleanUtils.isNotTrue(applySn.getIsUsed())) {
            throw new OpException("原编码{0}没有被使用。", applySn.getDisplaySn());
        } else if (BooleanUtils.isTrue(applySn.getIsAbolished())) {
            throw new OpException("原编码{0}已作废。", applySn.getDisplaySn());
        }

        if (BooleanUtils.isNotTrue(applySn2.getIsUsed())) {
            throw new OpException("原编码{0}没有被使用。", applySn2.getDisplaySn());
        } else if (BooleanUtils.isTrue(applySn2.getIsAbolished())) {
            throw new OpException("原编码{0}已作废。", applySn2.getDisplaySn());
        }

        int userId = applySn.getUserId();
        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if (memberApply == null) {
            throw new OpException("党员发展记录不存在。");
        }
        Integer applySnId = memberApply.getApplySnId();
        if (applySnId == null || applySnId.intValue() != applySn.getId()) {
            throw new OpException("党员发展记录编码有误[{0}]。", memberApply.getApplySn());
        }

        int userId2 = applySn2.getUserId();
        MemberApply memberApply2 = memberApplyMapper.selectByPrimaryKey(userId2);
        if (memberApply2 == null) {
            throw new OpException("调换编码对应的党员发展记录不存在。");
        }

        Integer applySnId2 = memberApply2.getApplySnId();
        if (applySnId2 == null || applySnId2.intValue() != applySn2.getId()) {
            throw new OpException("调换编码对应的党员发展记录编码有误[{0}]。", memberApply2.getApplySn());
        }

        {
            ApplySn record = new ApplySn();
            record.setId(applySnId);
            record.setUserId(userId2);
            record.setPartyId(memberApply2.getPartyId());
            record.setBranchId(memberApply2.getBranchId());
            record.setAssignTime(new Date());
            applySnMapper.updateByPrimaryKeySelective(record);

            record = new ApplySn();
            record.setId(applySnId2);
            record.setUserId(userId);
            record.setPartyId(memberApply.getPartyId());
            record.setBranchId(memberApply.getBranchId());
            record.setAssignTime(new Date());
            applySnMapper.updateByPrimaryKeySelective(record);
        }

        {
            MemberApply record = new MemberApply();
            record.setUserId(userId);
            record.setApplySnId(applySn2.getId());
            record.setApplySn(applySn2.getDisplaySn());
            memberApplyMapper.updateByPrimaryKeySelective(record);

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    null,
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "调换志愿书编码：" + applySn2.getDisplaySn());

            record = new MemberApply();
            record.setUserId(userId2);
            record.setApplySnId(applySn.getId());
            record.setApplySn(applySn.getDisplaySn());
            memberApplyMapper.updateByPrimaryKeySelective(record);

            applyApprovalLogService.add(userId2,
                    memberApply2.getPartyId(), memberApply2.getBranchId(), userId2,
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    null,
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "调换志愿书编码：" + applySn.getDisplaySn());
        }
    }

    // isAbolish = 0: 恢复已作废的编码  1： 作废未使用的编码
    @Transactional
    public void abolish(Integer[] applySnIds, boolean isAbolish) {

        for (int applySnId : applySnIds) {

            ApplySn applySn = applySnMapper.selectByPrimaryKey(applySnId);
            if (applySn == null) {
                throw new OpException("编码{0}不存在。", applySnId);
            } else if (!isAbolish && BooleanUtils.isNotTrue(applySn.getIsAbolished())) {
                // 恢复已作废的编码，要求编码没有作废
                throw new OpException("编码{0}没有作废。", applySn.getDisplaySn());
            } else if (isAbolish) {
                // 作废未使用的编码，要求编码未使用
                if (BooleanUtils.isTrue(applySn.getIsUsed()))
                    throw new OpException("编码{0}已使用，不可作废。", applySn.getDisplaySn());

                if (BooleanUtils.isTrue(applySn.getIsAbolished()))
                    throw new OpException("编码{0}已作废。", applySn.getDisplaySn());
            }

            commonMapper.excuteSql("update ow_apply_sn set is_used = 0, is_abolished=" + isAbolish + ", " +
                    "user_id = null, assign_time=null, party_id=null, branch_id=null where id=" + applySnId);

            // 更新编码段已使用数量
            Integer rangeId = applySn.getRangeId();
            updateUseCount(rangeId);

            SysUserView uv = applySn.getUser();
            logger.info(log(LogConstants.LOG_MEMBER,
                    isAbolish ?"作废编码{0}"
                            : "恢复已作废的编码<{0}>重新使用，原使用人：{1}",
                            applySn.getDisplaySn(), uv==null?"无":(uv.getRealname()+"-"+uv.getCode())));
        }
    }

    // 更新已使用数量：包含已使用和已作废之和
    private void updateUseCount(int rangeId) {

        ApplySnExample example = new ApplySnExample();
        example.or().andRangeIdEqualTo(rangeId)
                .andIsUsedEqualTo(true);
        example.or().andRangeIdEqualTo(rangeId)
                .andIsAbolishedEqualTo(true);

        int useCount = (int) applySnMapper.countByExample(example);

        applySnRangeService.updateUseCount(rangeId, useCount);
    }
}
