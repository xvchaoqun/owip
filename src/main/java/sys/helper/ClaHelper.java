package sys.helper;

import domain.cla.ClaApply;
import domain.cla.ClaApprovalLog;
import persistence.cla.common.ClaApplyModifyBean;
import persistence.cla.common.ClaApproverTypeBean;
import persistence.cla.common.IClaMapper;
import service.cla.ClaApplyService;
import service.cla.ClaApprovalLogService;
import sys.tags.CmTag;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/6/8.
 */
public class ClaHelper {

    /**
     * 干部请假审批
     */

    public static ClaApproverTypeBean getClaApproverTypeBean(Integer userId) {

        ClaApplyService claApplyService = CmTag.getBean(ClaApplyService.class);
        if(claApplyService==null) return null;

        return claApplyService.getApproverTypeBean(userId);
    }

    public static ClaApply getClaApply(Integer applyId) {

        ClaApplyService claApplyService = CmTag.getBean(ClaApplyService.class);
        if(claApplyService==null) return null;
        return claApplyService.get(applyId);
    }

    public static List<ClaApplyModifyBean> getClaApplyModifyList(Integer applyId) {

        IClaMapper iClaMapper =  CmTag.getBean(IClaMapper.class);
        return iClaMapper.getApplyModifyList(applyId);
    }

    // 获取初审结果
    public static Integer getClaAdminFirstTrialStatus(Integer applyId) {

        ClaApprovalLogService claApprovalLogService = CmTag.getBean(ClaApprovalLogService.class);
        return claApprovalLogService.getAdminFirstTrialStatus(applyId);
    }

    public static Map getClaApprovalTdBeanMap(Integer applySelfId) {

        ClaApplyService claApplyService = CmTag.getBean(ClaApplyService.class);
        if(claApplyService==null) return null;
        return claApplyService.getApprovalTdBeanMap(applySelfId);
    }

    public static ClaApprovalLog getClaApprovalLog(Integer applySelfId, Integer approverTypeId) {

        ClaApprovalLogService claApprovalLogService = CmTag.getBean(ClaApprovalLogService.class);
        return claApprovalLogService.getApprovalLog(applySelfId, approverTypeId);
    }

    // 获取审批log
    public static List<ClaApprovalLog> findClaApprovalLogs(Integer applyId) {

        ClaApprovalLogService claApprovalLogService = CmTag.getBean(ClaApprovalLogService.class);
        return claApprovalLogService.findByApplyId(applyId);
    }
}
