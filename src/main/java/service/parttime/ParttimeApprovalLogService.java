package service.parttime;

import controller.global.OpException;
import domain.parttime.ParttimeApply;
import domain.parttime.ParttimeApprovalLog;
import domain.parttime.ParttimeApprovalLogExample;
import domain.parttime.ParttimeApproverType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.parttime.ParttimeApplyMapper;
import persistence.parttime.ParttimeApprovalLogMapper;
import persistence.parttime.ParttimeApproverTypeMapper;
import persistence.parttime.common.ParttimeApprovalResult;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.ClaConstants;
import sys.constants.ParttimeConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;
import java.util.*;

@Service
public class ParttimeApprovalLogService {
    @Autowired
    private ParttimeApprovalLogMapper parttimeApprovalLogMapper;
    @Autowired
    private ParttimeApplyService parttimeApplyService;
    @Autowired
    private ParttimeApproverTypeMapper parttimeApproverTypeMapper;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private ParttimeApplyMapper parttimeApplyMapper;
    @Autowired
    private ParttimeShortMsgService parttimeShortMsgService;

    // 获取申请记录 初审结果  审批结果: -1不需要审批 0未通过 1通过 null未审批
    public Integer getAdminFirstTrialStatus(int applyId){
        List<ParttimeApprovalLog> approvalLogs = findByApplyId(applyId);
        for (ParttimeApprovalLog approvalLog : approvalLogs) {
            if(approvalLog.getTypeId()==null && approvalLog.getOdType()==0){
                return approvalLog.getStatus()?1:0;
            }
        }
        // 如果没有组织部初审，则审批记录肯定为空
        //Assert.isTrue(approvalLogs.size() == 0);
        return null;
    }

    public List<ParttimeApprovalLog> findByApplyId(int applySefId){

        ParttimeApprovalLogExample example = new ParttimeApprovalLogExample();
        example.createCriteria().andApplyIdEqualTo(applySefId);
        example.setOrderByClause("create_time asc");

        return parttimeApprovalLogMapper.selectByExample(example);
    }

    public ParttimeApprovalLog getApprovalLog(int applySefId, int approverTypeId) {

        ParttimeApprovalLogExample example = new ParttimeApprovalLogExample();
        ParttimeApprovalLogExample.Criteria criteria = example.createCriteria().andApplyIdEqualTo(applySefId);
        if(approverTypeId==-1){
            criteria.andTypeIdIsNull().andOdTypeEqualTo(ClaConstants.CLA_APPROVER_LOG_OD_TYPE_FIRST);
        }else if(approverTypeId==0){
            criteria.andTypeIdIsNull().andOdTypeEqualTo(ClaConstants.CLA_APPROVER_LOG_OD_TYPE_LAST);
        }else{
            criteria.andTypeIdEqualTo(approverTypeId);
        }
        List<ParttimeApprovalLog> approvalLogs = parttimeApprovalLogMapper.selectByExample(example);
        if(approvalLogs.size()>0) return approvalLogs.get(0);
        return null;
    }

    // 审批
    @Transactional
    public void approval(int opUserId, // 审批人
                         int applyId,
                         int approvalTypeId,
                         boolean pass,
                         Date approvalTime, // 审批时间
                         String remark) {

        Map<Integer, ParttimeApprovalResult> approvalResultMap = parttimeApplyService.getApprovalResultMap(applyId);
        Integer result = approvalResultMap.get(approvalTypeId).getValue();
        if (result != null && result != -1) {
            throw new OpException("重复审批");
        }
        if (result != null && result == -1) {
            throw new OpException("不需该审批人身份进行审批");
        }
        if (approvalTypeId == -1) { // 管理员初审
            org.springframework.util.Assert.isTrue(result == null, "null");
            ShiroHelper.checkPermission(RoleConstants.PERMISSION_COMPANYAPPLY);
        }
        Map<Integer, ParttimeApproverType> approverTypeMap = parttimeApplyService.findAll();
        if (approvalTypeId > 0) {
            for (Map.Entry<Integer, ParttimeApproverType> entry : approverTypeMap.entrySet()) {
                Integer key = entry.getKey();
                if (key != approvalTypeId) {
                    if (approvalResultMap.get(key) != null) {
                        Integer preResult = approvalResultMap.get(key).getValue();
                        if (preResult == null || preResult == 0)
                            throw new OpException(entry.getValue().getName() + "审批未通过，不允许进行当前审批");
                    }
                }
                if (key == approvalTypeId) break;
            }
        }
        if (approvalTypeId == 0) {
            // 验证前面的审批均已完成（通过或未通过）
            for (Map.Entry<Integer, ParttimeApproverType> entry : approverTypeMap.entrySet()) {
                Integer key = entry.getKey();
                if (approvalResultMap.get(key) != null) {
                    Integer preResult = approvalResultMap.get(key).getValue();

                    if (preResult != null && preResult == 0) break; // 前面有审批未通过的，则可以直接终审
                    if (preResult != null && preResult == -1) continue; // 跳过不需要审批的

                    if (preResult == null) // 前面存在 未审批
                        throw new OpException(entry.getValue().getName() + "未完成审批");
                 }
            }
        }

        ParttimeApprovalLog record = new ParttimeApprovalLog();
        record.setApplyId(applyId);
        if (approvalTypeId > 0)
            record.setTypeId(approvalTypeId);
        if (approvalTypeId == -1) {
            record.setOdType(ClaConstants.CLA_APPROVER_LOG_OD_TYPE_FIRST); // 初审
            if (!pass) { // 不通过，退回申请
                ParttimeApply apply = new ParttimeApply();
                apply.setId(applyId);
                apply.setStatus(false); // 退回
                apply.setApprovalRemark(remark);
                //如果管理员初审未通过，就不需要领导审批，也不需要管理员再终审一次，直接就退回给干部了。
                // 也就是说只要管理员初审不通过，就相当于此次申请已经完成了审批。那么这条记录应该转移到“已完成审批”中去。
                apply.setIsFinish(true);
                apply.setIsAgreed(pass);
                parttimeApplyService.doApproval(apply);
            }
        }
        if (approvalTypeId == 0) {
            record.setOdType(ClaConstants.CLA_APPROVER_LOG_OD_TYPE_LAST); // 终审
        }
        record.setStatus(pass);
        record.setRemark(remark);
        record.setUserId(opUserId);
        record.setCreateTime(approvalTime);
        record.setIp(ContextHelper.getRealIp());
        doApproval(record);
    }

    @Transactional
    public synchronized void doApproval(ParttimeApprovalLog record){
/*        Integer typeId = record.getTypeId();// 审批人身份ID
        if(typeId==null){
            if(record.getOdType()==ClaConstants.CLA_APPROVER_LOG_OD_TYPE_FIRST){
                typeId = ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST; //初审
            }
            if(record.getOdType()==ClaConstants.CLA_APPROVER_LOG_OD_TYPE_LAST){
                typeId = ClaConstants.CLA_APPROVER_TYPE_ID_OD_LAST; // 终审
            }
        }*/
        // 先完成审批记录，再更新申请记录审批字段
        insertSelective(record);
        String _approverType = "";
        if(record.getTypeId()!=null){
            ParttimeApproverType approverType = parttimeApproverTypeMapper.selectByPrimaryKey(record.getTypeId());
            if(approverType!=null) _approverType = approverType.getName();
        }

        ParttimeApply _apply = parttimeApplyMapper.selectByPrimaryKey(record.getApplyId());
        sysApprovalLogService.add(_apply.getId(), _apply.getUser().getId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CLA_APPLY,
                _approverType+ "审批", record.getStatus() ? SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS
                        : SystemConstants.SYS_APPROVAL_LOG_STATUS_DENY, record.getRemark());
        Integer applyId = record.getApplyId();
        Integer nextFlowNode = null; // 下一个审批身份
        List<Integer> flowNodes = new ArrayList<>(); // 已审批身份类型,（按顺序排序，逗号分隔）
        List<Integer> flowUsers = new ArrayList<>(); // 已审批的审批人ID，（按顺序排序，逗号分隔）
        Map<Integer, ParttimeApprovalResult> approvalResultMap = parttimeApplyService.getApprovalResultMap(applyId);
        for (Map.Entry<Integer, ParttimeApprovalResult> entry : approvalResultMap.entrySet()) {
            Integer flowNode = entry.getKey();
            ParttimeApprovalResult approvalResult = entry.getValue();
            if(approvalResult.getValue()==null){ // 未审批
                if(nextFlowNode == null){
                    nextFlowNode = flowNode;
                }
                if(flowNode == ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST)
                    break; // 还没经过组织部初审
                else
                    continue;
            }
            if(approvalResult.getValue()==-1) continue; //不需要审批

            // 已审批（未通过或通过）
            flowNodes.add(flowNode);
            flowUsers.add(approvalResult.getApprovalLog().getUserId());
        }
        ParttimeApply apply = new ParttimeApply();
        apply.setId(applyId);
        apply.setFlowNode(nextFlowNode); // 下一个审批身份
        apply.setApprovalRemark(record.getRemark());
        if(!record.getStatus()) // 如果上一个领导未通过，应该下面的领导都不需要审批了，直接转到组织部终审。
            apply.setFlowNode(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_LAST);

        apply.setFlowNodes(StringUtils.join(flowNodes, ",")); // 已完成审批的 审批身份
        apply.setFlowUsers(StringUtils.join(flowUsers, ",")); // 已完成审批（未通过或通过）的 审批人

        if(record.getTypeId()==null && record.getOdType()==ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_LAST){
            apply.setIsFinish(true); // 终审完成
            apply.setIsAgreed(record.getStatus());
        }
        // 立刻更新申请记录的相关审批结果字段（供查询使用）
        parttimeApplyService.doApproval(apply);

        // 如果通过审批，且下一个审批身份是管理员，则短信通知管理员
        if(record.getStatus() && nextFlowNode!=null && nextFlowNode==ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_LAST){
            parttimeShortMsgService.sendClaApplyPassMsgToCadreAdmin(applyId, IpUtils.getRealIp(ContextHelper.getRequest()));
        }
    }

    @Transactional
    public int insertSelective(ParttimeApprovalLog record){

        return parttimeApprovalLogMapper.insertSelective(record);
    }
}
