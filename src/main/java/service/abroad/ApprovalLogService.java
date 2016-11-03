package service.abroad;

import bean.ApprovalResult;
import domain.abroad.ApplySelf;
import domain.abroad.ApprovalLog;
import domain.abroad.ApprovalLogExample;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.internal.core.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.helper.ContextHelper;
import service.sys.ShortMsgService;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApprovalLogService extends BaseMapper {

    @Autowired
    private ApplySelfService applySelfService;
    @Autowired
    private ShortMsgService shortMsgService;

    // 获取申请记录 初审结果  审批结果: -1不需要审批 0未通过 1通过 null未审批
    public Integer getAdminFirstTrialStatus(int applyId){

        List<ApprovalLog> approvalLogs = findByApplyId(applyId);
        for (ApprovalLog approvalLog : approvalLogs) {
            if(approvalLog.getTypeId()==null && approvalLog.getOdType()==0){
                return approvalLog.getStatus()?1:0;
            }
        }
        // 如果没有组织部初审，则审批记录肯定为空
        Assert.isTrue(approvalLogs.size() == 0);
        return null;
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
    public synchronized void add(ApprovalLog record){

/*        Integer typeId = record.getTypeId();// 审批人身份ID
        if(typeId==null){
            if(record.getOdType()==SystemConstants.APPROVER_LOG_OD_TYPE_FIRST){
                typeId = SystemConstants.APPROVER_TYPE_ID_OD_FIRST; //初审
            }
            if(record.getOdType()==SystemConstants.APPROVER_LOG_OD_TYPE_LAST){
                typeId = SystemConstants.APPROVER_TYPE_ID_OD_LAST; // 终审
            }
        }*/
        // 先完成审批记录，再更新申请记录审批字段
        insertSelective(record);

        Integer applyId = record.getApplyId();
        Integer nextFlowNode = null; // 下一个审批身份
        List<Integer> flowNodes = new ArrayList<>(); // 已审批身份类型,（按顺序排序，逗号分隔）
        List<Integer> flowUsers = new ArrayList<>(); // 已审批的审批人ID，（按顺序排序，逗号分隔）
        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(applyId);
        for (Map.Entry<Integer, ApprovalResult> entry : approvalResultMap.entrySet()) {
            Integer flowNode = entry.getKey();
            ApprovalResult approvalResult = entry.getValue();
            if(approvalResult.getValue()==null){ // 未审批
                if(nextFlowNode == null){
                    nextFlowNode = flowNode;
                }
                if(flowNode == SystemConstants.APPROVER_TYPE_ID_OD_FIRST)
                    break; // 还没经过组织部初审
                else
                    continue;
            }
            if(approvalResult.getValue()==-1) continue; //不需要审批

            // 已审批（未通过或通过）
            flowNodes.add(flowNode);
            flowUsers.add(approvalResult.getApprovalLog().getUserId());
        }
        ApplySelf applySelf = new ApplySelf();
        applySelf.setId(applyId);
        applySelf.setFlowNode(nextFlowNode); // 下一个审批身份

        if(!record.getStatus()) // 如果上一个领导未通过，应该下面的领导都不需要审批了，直接转到组织部终审。
            applySelf.setFlowNode(SystemConstants.APPROVER_TYPE_ID_OD_LAST);

        applySelf.setFlowNodes(StringUtils.join(flowNodes, ",")); // 已完成审批的 审批身份
        applySelf.setFlowUsers(StringUtils.join(flowUsers, ",")); // 已完成审批（未通过或通过）的 审批人

        if(record.getTypeId()==null && record.getOdType()==SystemConstants.APPROVER_LOG_OD_TYPE_LAST){
            applySelf.setIsFinish(true); // 终审完成
            applySelf.setIsAgreed(record.getStatus());
        }

        // 立刻更新申请记录的相关审批结果字段（供查询使用）
        applySelfService.approval(applySelf);

        // 如果通过审批，且下一个审批身份是管理员，则短信通知管理员
        if(record.getStatus() && nextFlowNode!=null && nextFlowNode==SystemConstants.APPROVER_TYPE_ID_OD_LAST){
            shortMsgService.sendApplySelfPassMsgToCadreAdmin(applyId, IpUtils.getRealIp(ContextHelper.getRequest()));
        }
    }

    @Transactional
    public int insertSelective(ApprovalLog record){

        return approvalLogMapper.insertSelective(record);
    }
    /*@Transactional
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
    }*/
}
