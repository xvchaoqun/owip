package service.member;

import controller.global.OpException;
import domain.member.ApplySn;
import domain.member.ApplySnExample;
import domain.member.MemberApply;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.utils.DateUtils;

import java.util.List;

@Service
public class ApplySnService extends MemberBaseMapper {

    @Autowired
    private ApplySnRangeService applySnRangeService;

    // 获取即将分配的志愿书编码
    public List<ApplySn> getAssignApplySnList(int count){

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
    public void assign(int userId) {

        List<ApplySn> applySns = getAssignApplySnList(1);

        if(applySns.size()>0){
            ApplySn applySn = applySns.get(0);
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);

            if(memberApply!=null) {

                MemberApply record = new MemberApply();
                record.setUserId(userId);
                record.setApplySnId(applySn.getId());
                record.setApplySn(applySn.getDisplaySn());
                memberApplyMapper.updateByPrimaryKeySelective(record);

                use(applySn.getId(), userId);
            }
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

                commonMapper.excuteSql("update ow_member_apply set apply_sn_id=null, apply_sn=null where user_id="+ userId);
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

        ApplySn record = new ApplySn();
        record.setId(applySnId);
        record.setIsUsed(true);
        record.setUserId(userId);

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

        commonMapper.excuteSql("update ow_apply_sn set is_used = 0, user_id = null where id=" + applySnId);


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
    public void change(ApplySn applySn) {

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
        commonMapper.excuteSql("update ow_member_apply set apply_sn_id=null, apply_sn=null where user_id="+ userId);

        // 分配新编码
        assign(userId);
    }
}
