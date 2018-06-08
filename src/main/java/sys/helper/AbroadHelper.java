package sys.helper;

import domain.abroad.ApplySelf;
import domain.abroad.ApprovalLog;
import domain.abroad.Passport;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawFile;
import domain.abroad.SafeBox;
import persistence.abroad.PassportMapper;
import persistence.abroad.common.ApplySelfModifyBean;
import persistence.abroad.common.ApproverTypeBean;
import persistence.abroad.common.IAbroadMapper;
import service.abroad.ApplySelfService;
import service.abroad.ApprovalLogService;
import service.abroad.PassportDrawService;
import service.abroad.SafeBoxService;
import sys.tags.CmTag;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/6/8.
 */
public class AbroadHelper {

    /**
     * 因私出国境
     */
    public static ApproverTypeBean getApproverTypeBean(Integer userId) {

        ApplySelfService applySelfService = CmTag.getBean(ApplySelfService.class);
        if(applySelfService==null) return null;

        return applySelfService.getApproverTypeBean(userId);
    }

    public static ApplySelf getApplySelf(Integer applyId) {

        ApplySelfService applySelfService = CmTag.getBean(ApplySelfService.class);
        if(applySelfService==null) return null;
        return applySelfService.get(applyId);
    }

    public static List<ApplySelfModifyBean> getApplySelfModifyList(Integer applyId) {

        IAbroadMapper iAbroadMapper =  CmTag.getBean(IAbroadMapper.class);
        return iAbroadMapper.getApplySelfModifyList(applyId);
    }

    // 获取因私出国申请记录 初审 结果
    public static Integer getAdminFirstTrialStatus(Integer applyId) {

        ApprovalLogService approvalLogService = CmTag.getBean(ApprovalLogService.class);
        return approvalLogService.getAdminFirstTrialStatus(applyId);
    }

    public static Map getApprovalTdBeanMap(Integer applySelfId) {

        ApplySelfService applySelfService = CmTag.getBean(ApplySelfService.class);
        if(applySelfService==null) return null;
        return applySelfService.getApprovalTdBeanMap(applySelfId);
    }

    public static ApprovalLog getApprovalLog(Integer applySelfId, Integer approverTypeId) {

        ApprovalLogService approvalLogService = CmTag.getBean(ApprovalLogService.class);
        return approvalLogService.getApprovalLog(applySelfId, approverTypeId);
    }

    // 获取因私出国申请记录 的评审log
    public static List<ApprovalLog> findApprovalLogs(Integer applyId) {

        ApprovalLogService approvalLogService = CmTag.getBean(ApprovalLogService.class);
        return approvalLogService.findByApplyId(applyId);
    }

    public static Map<Integer, SafeBox> getSafeBoxMap() {

        SafeBoxService safeBoxService = CmTag.getBean(SafeBoxService.class);
        return safeBoxService.findAll();
    }

    // 证件
    public static Passport getPassport(Integer id) {

        PassportMapper passportMapper = CmTag.getBean(PassportMapper.class);
        return passportMapper.selectByPrimaryKey(id);
    }

    // 拒绝归还证件借出记录
    public static PassportDraw getRefuseReturnPassportDraw(Integer passportId) {

        PassportDrawService passportDrawService = CmTag.getBean(PassportDrawService.class);
        return passportDrawService.getRefuseReturnPassportDraw(passportId);
    }

    public static List<PassportDrawFile> getPassportDrawFiles(Integer id) {

        PassportDrawService passportDrawService = CmTag.getBean(PassportDrawService.class);
        return passportDrawService.getPassportDrawFiles(id);
    }
}
