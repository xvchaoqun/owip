package service.abroad;

import controller.global.OpException;
import domain.abroad.ApplySelf;
import domain.abroad.ApprovalLog;
import domain.abroad.ApprovalLogExample;
import domain.abroad.ApproverType;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.abroad.common.ApprovalResult;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ApprovalLogService extends AbroadBaseMapper {

    @Autowired
    private ApplySelfService applySelfService;
    @Autowired
    private AbroadShortMsgService abroadShortMsgService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    protected ApproverTypeService approverTypeService;

    // 获取申请记录 初审结果  审批结果: -1不需要审批 0未通过 1通过 null未审批
    public Integer getAdminFirstTrialStatus(int applyId){

        List<ApprovalLog> approvalLogs = findByApplyId(applyId);
        for (ApprovalLog approvalLog : approvalLogs) {
            if(approvalLog.getTypeId()==null && approvalLog.getOdType()==0){
                return approvalLog.getStatus()?1:0;
            }
        }
        // 如果没有组织部初审，则审批记录肯定为空
        //Assert.isTrue(approvalLogs.size() == 0);
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
           criteria.andTypeIdIsNull().andOdTypeEqualTo(AbroadConstants.ABROAD_APPROVER_LOG_OD_TYPE_FIRST);
        }else if(approverTypeId==0){
            criteria.andTypeIdIsNull().andOdTypeEqualTo(AbroadConstants.ABROAD_APPROVER_LOG_OD_TYPE_LAST);
        }else{
            criteria.andTypeIdEqualTo(approverTypeId);
        }
        List<ApprovalLog> approvalLogs = approvalLogMapper.selectByExample(example);
        if(approvalLogs.size()>0) return approvalLogs.get(0);
        return null;
    }
    
    @Transactional
    public synchronized void doApproval(ApprovalLog record){

/*        Integer typeId = record.getTypeId();// 审批人身份ID
        if(typeId==null){
            if(record.getOdType()==AbroadConstants.ABROAD_APPROVER_LOG_OD_TYPE_FIRST){
                typeId = AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST; //初审
            }
            if(record.getOdType()==AbroadConstants.ABROAD_APPROVER_LOG_OD_TYPE_LAST){
                typeId = AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST; // 终审
            }
        }*/
        // 先完成审批记录，再更新申请记录审批字段
        insertSelective(record);
        String _approverType = "";
        if(record.getTypeId()!=null){
            ApproverType approverType = approverTypeMapper.selectByPrimaryKey(record.getTypeId());
            if(approverType!=null) _approverType = approverType.getName();
        }

        ApplySelf _applySelf = applySelfMapper.selectByPrimaryKey(record.getApplyId());
        sysApprovalLogService.add(_applySelf.getId(), _applySelf.getUser().getId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_APPLYSELF,
                _approverType+ "审批", record.getStatus() ? SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS
                        : SystemConstants.SYS_APPROVAL_LOG_STATUS_DENY, record.getRemark());

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
                if(flowNode == AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST)
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
        applySelf.setApprovalRemark(record.getRemark());
        if(!record.getStatus()) // 如果上一个领导未通过，应该下面的领导都不需要审批了，直接转到组织部终审。
            applySelf.setFlowNode(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST);

        applySelf.setFlowNodes(StringUtils.join(flowNodes, ",")); // 已完成审批的 审批身份
        applySelf.setFlowUsers(StringUtils.join(flowUsers, ",")); // 已完成审批（未通过或通过）的 审批人

        if(record.getTypeId()==null && record.getOdType()==AbroadConstants.ABROAD_APPROVER_LOG_OD_TYPE_LAST){
            applySelf.setIsFinish(true); // 终审完成
            applySelf.setIsAgreed(record.getStatus());
        }

        // 立刻更新申请记录的相关审批结果字段（供查询使用）
        applySelfService.doApproval(applySelf);

        // 如果通过审批，且下一个审批身份是管理员，则短信通知管理员
        if(record.getStatus() && nextFlowNode!=null && nextFlowNode==AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST){
            abroadShortMsgService.sendApplySelfPassMsgToCadreAdmin(applyId, IpUtils.getRealIp(ContextHelper.getRequest()));
        }
    }

    // 审批
    @Transactional
    public void approval(int opUserId, // 审批人
                         int applySelfId,
                         int approvalTypeId,
                         boolean pass,
                         Date approvalTime, // 审批时间
                         String remark, String filePath, String fileName){

        Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(applySelfId);
        Integer result = approvalResultMap.get(approvalTypeId).getValue();
        if (result != null && result != -1) {
            throw new OpException("重复审批");
        }
        if (result != null && result == -1) {
            throw new OpException("不需该审批人身份进行审批");
        }
        if (approvalTypeId == -1) { // 管理员初审
            org.springframework.util.Assert.isTrue(result == null, "null");
            ShiroHelper.checkRole(RoleConstants.ROLE_CADREADMIN);
        }
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        if (approvalTypeId > 0) {
            for (Map.Entry<Integer, ApproverType> entry : approverTypeMap.entrySet()) {
                Integer key = entry.getKey();
                if (key != approvalTypeId) {
                    Integer preResult = approvalResultMap.get(key).getValue();
                    if (preResult == null || preResult == 0)
                        throw new OpException(entry.getValue().getName() + "审批未通过，不允许进行当前审批");
                }
                if (key == approvalTypeId) break;
            }
        }
        if (approvalTypeId == 0) {
            // 验证前面的审批均已完成（通过或未通过）
            for (Map.Entry<Integer, ApproverType> entry : approverTypeMap.entrySet()) {
                Integer key = entry.getKey();
                //if (key != 0) {
                Integer preResult = approvalResultMap.get(key).getValue();

                if (preResult != null && preResult == 0) break; // 前面有审批未通过的，则可以直接终审
                if (preResult != null && preResult == -1) continue; // 跳过不需要审批的

                if (preResult == null) // 前面存在 未审批
                    throw new OpException(entry.getValue().getName() + "未完成审批");
                // }
            }
        }

        ApprovalLog record = new ApprovalLog();
        record.setApplyId(applySelfId);
        if (approvalTypeId > 0)
            record.setTypeId(approvalTypeId);
        if (approvalTypeId == -1) {
            record.setOdType(AbroadConstants.ABROAD_APPROVER_LOG_OD_TYPE_FIRST); // 初审
            if (!pass) { // 不通过，退回申请
                ApplySelf applySelf = new ApplySelf();
                applySelf.setId(applySelfId);
                applySelf.setStatus(false); // 退回
                applySelf.setApprovalRemark(remark);

                //如果管理员初审未通过，就不需要领导审批，也不需要管理员再终审一次，直接就退回给干部了。
                // 也就是说只要管理员初审不通过，就相当于此次申请已经完成了审批。那么这条记录应该转移到“已完成审批”中去。
                applySelf.setIsFinish(true);
                applySelf.setFlowNodes("-1");
                applySelf.setFlowUsers(ShiroHelper.getCurrentUserId()+"");

                applySelfService.doApproval(applySelf);
            }
        }
        if (approvalTypeId == 0) {
            record.setOdType(AbroadConstants.ABROAD_APPROVER_LOG_OD_TYPE_LAST); // 终审
        }
        record.setStatus(pass);
        record.setRemark(remark);
        record.setUserId(opUserId);
        record.setCreateTime(approvalTime);
        record.setIp(ContextHelper.getRealIp());
        record.setFilePath(filePath);
        record.setFileName(fileName);

        doApproval(record);
    }

    @Transactional
    public int insertSelective(ApprovalLog record){

        return approvalLogMapper.insertSelective(record);
    }
}
