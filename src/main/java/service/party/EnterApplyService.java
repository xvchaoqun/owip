package service.party;

import domain.EnterApply;
import domain.EnterApplyExample;
import domain.MemberApply;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2015/12/4.
 */
@Service
public class EnterApplyService extends BaseMapper{

    // 查询当前的有效申请
    public EnterApply getCurrentApply(int userId){

        EnterApplyExample example = new EnterApplyExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(SystemConstants.ENTER_APPLY_STATUS_APPLY);

        List<EnterApply> enterApplies = enterApplyMapper.selectByExample(example);
        if(enterApplies.size()>1)
            throw new DBErrorException("系统异常"); // 当前申请状态每个用户只允许一个，且是最新的一条
        if(enterApplies.size()==1) return enterApplies.get(0);

        return null;
    }

    // 申请入党
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public void memberApply(MemberApply record) {
        int userId = record.getUserId();

        EnterApply _enterApply = getCurrentApply(userId);
        if(_enterApply!=null) throw new DBErrorException("重复申请");

        if(memberApplyMapper.selectByPrimaryKey(userId)==null)
            memberApplyMapper.insert(record);
        else
            memberApplyMapper.updateByPrimaryKey(record);

        EnterApply enterApply = new EnterApply();
        enterApply.setUserId(record.getUserId());
        enterApply.setType(SystemConstants.ENTER_APPLY_TYPE_MEMBERAPPLY);
        enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_APPLY);
        enterApply.setCreateTime(new Date());
        enterApplyMapper.insertSelective(enterApply);
    }
    // 申请入党-本人撤回
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void memberApplyBack(int userId) {
        // 状态检查
        EnterApply _enterApply = getCurrentApply(userId);
        if(_enterApply==null || _enterApply.getType()!=SystemConstants.ENTER_APPLY_TYPE_MEMBERAPPLY)
            throw new DBErrorException("系统错误");
        // 状态检查
        MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if(_memberApply==null)
            throw new DBErrorException("系统错误");
        if(_memberApply!=null && _memberApply.getStage()!=SystemConstants.APPLY_STAGE_INIT &&
                _memberApply.getStage() != SystemConstants.APPLY_STAGE_DENY){
            throw new DBErrorException("申请已进入审核阶段，不允许撤回。");
        }

        EnterApply enterApply = new EnterApply();
        enterApply.setId(_enterApply.getId());
        enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_SELF_ABORT);
        enterApply.setBackTime(new Date());
        enterApplyMapper.updateByPrimaryKeySelective(enterApply);

        MemberApply memberApply = new MemberApply();
        memberApply.setUserId(userId);
        memberApply.setStage(SystemConstants.APPLY_STAGE_DENY);
        memberApplyMapper.updateByPrimaryKeySelective(memberApply);
    }
}
