package service.abroad;

import domain.ApprovalLog;
import domain.ApprovalLogExample;
import org.eclipse.jdt.internal.core.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ApprovalLogService extends BaseMapper {


    // 判断申请记录 是否经过了初审
    public boolean hasAdminFirstTrial(int applyId){

        List<ApprovalLog> approvalLogs = findByApplyId(applyId);
        for (ApprovalLog approvalLog : approvalLogs) {
            if(approvalLog.getTypeId()==null && approvalLog.getOdType()==0){
                return true;
            }
        }
        Assert.isTrue(approvalLogs.size() == 0);
        return false;
    }

    public List<ApprovalLog> findByApplyId(int applyId){

        ApprovalLogExample example = new ApprovalLogExample();
        example.createCriteria().andApplyIdEqualTo(applyId);
        example.setOrderByClause("create_time asc");

        return approvalLogMapper.selectByExample(example);
    }

    @Transactional
    public int insertSelective(ApprovalLog record){

        return approvalLogMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        approvalLogMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApprovalLogExample example = new ApprovalLogExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        approvalLogMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApprovalLog record){
        return approvalLogMapper.updateByPrimaryKeySelective(record);
    }
}
