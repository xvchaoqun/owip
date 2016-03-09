package service.abroad;

import domain.ApprovalLog;
import domain.ApprovalLogExample;
import org.eclipse.jdt.internal.core.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

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
        // 如果没有组织部初审，则审批记录肯定为空
        Assert.isTrue(approvalLogs.size() == 0);
        return false;
    }

    public List<ApprovalLog> findByApplyId(int applySefId){

        ApprovalLogExample example = new ApprovalLogExample();
        example.createCriteria().andApplyIdEqualTo(applySefId);
        example.setOrderByClause("create_time asc");

        return approvalLogMapper.selectByExample(example);
    }

    public ApprovalLog getApprovalLog(int applySefId, int approverTypeId) {

        ApprovalLogExample example = new ApprovalLogExample();
        ApprovalLogExample.Criteria criteria = example.createCriteria().andApplyIdEqualTo(applySefId);
        if(approverTypeId==-1){
           criteria.andTypeIdIsNull().andOdTypeEqualTo(SystemConstants.APPROVER_LOG_OD_TYPE_FIRST);
        }else if(approverTypeId==0){
            criteria.andTypeIdIsNull().andOdTypeEqualTo(SystemConstants.APPROVER_LOG_OD_TYPE_LAST);
        }else{
            criteria.andTypeIdEqualTo(approverTypeId);
        }
        List<ApprovalLog> approvalLogs = approvalLogMapper.selectByExample(example);
        if(approvalLogs.size()>0) return approvalLogs.get(0);
        return null;
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
