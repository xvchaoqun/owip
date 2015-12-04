package service.party;

import domain.EnterApply;
import domain.EnterApplyExample;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.DBErrorException;
import sys.constants.SystemConstants;

import java.util.List;

/**
 * Created by fafa on 2015/12/4.
 */
@Service
public class EnterApplyService extends BaseMapper{

    public EnterApply getCurrentApply(int userId){

        EnterApplyExample example = new EnterApplyExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(SystemConstants.ENTER_APPLY_STATUS_APPLY);

        List<EnterApply> enterApplies = enterApplyMapper.selectByExample(example);
        if(enterApplies.size()>1)
            throw new DBErrorException("系统异常"); // 当前申请状态每个用户只允许一个，且是最新的一条
        if(enterApplies.size()==1) return enterApplies.get(0);

        return null;
    }
}
