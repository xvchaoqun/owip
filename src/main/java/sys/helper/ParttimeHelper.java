package sys.helper;

import domain.parttime.ParttimeApply;
import domain.parttime.ParttimeApprovalLog;
import persistence.parttime.common.ParttimeApplyModifyBean;
import persistence.parttime.common.ParttimeApproverTypeBean;
import persistence.parttime.common.IParttimeMapper;
import service.parttime.ParttimeApplyService;
import service.parttime.ParttimeApprovalLogService;
import sys.tags.CmTag;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/6/8.
 */
public class ParttimeHelper {

    /**
     * 兼职审批
     */

    public static ParttimeApproverTypeBean getParttimeApproverTypeBean(Integer userId) {

        ParttimeApplyService parttimeApplyService = CmTag.getBean(ParttimeApplyService.class);
        if(parttimeApplyService==null) return null;

        return parttimeApplyService.getApproverTypeBean(userId);
    }

    public static ParttimeApply getParttimeApply(Integer applyId) {

        ParttimeApplyService parttimeApplyService = CmTag.getBean(ParttimeApplyService.class);
        if(parttimeApplyService==null) return null;
        return parttimeApplyService.get(applyId);
    }

    public static List<ParttimeApplyModifyBean> getParttimeApplyModifyList(Integer applyId) {

        IParttimeMapper iParttimeMapper =  CmTag.getBean(IParttimeMapper.class);
        return iParttimeMapper.getApplyModifyList(applyId);
    }

    // 获取初审结果
    public static Integer getParttimeAdminFirstTrialStatus(Integer applyId) {

        ParttimeApprovalLogService parttimeApprovalLogService = CmTag.getBean(ParttimeApprovalLogService.class);
        return parttimeApprovalLogService.getAdminFirstTrialStatus(applyId);
    }

    public static Map getParttimeApprovalTdBeanMap(Integer applyId) {

        ParttimeApplyService parttimeApplyService = CmTag.getBean(ParttimeApplyService.class);
        if(parttimeApplyService==null) return null;
        return parttimeApplyService.getApprovalTdBeanMap(applyId);
    }

    public static ParttimeApprovalLog getParttimeApprovalLog(Integer applySelfId, Integer approverTypeId) {

        ParttimeApprovalLogService parttimeApprovalLogService = CmTag.getBean(ParttimeApprovalLogService.class);
        return parttimeApprovalLogService.getApprovalLog(applySelfId, approverTypeId);
    }

    // 获取审批log
    public static List<ParttimeApprovalLog> findParttimeApprovalLogs(Integer applyId) {

        ParttimeApprovalLogService parttimeApprovalLogService = CmTag.getBean(ParttimeApprovalLogService.class);
        return parttimeApprovalLogService.findByApplyId(applyId);
    }
}
