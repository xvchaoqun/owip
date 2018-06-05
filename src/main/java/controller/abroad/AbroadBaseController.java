package controller.abroad;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.abroad.AbroadAdditionalPostService;
import service.abroad.AbroadExportService;
import service.abroad.AbroadShortMsgService;
import service.abroad.ApplicatTypeService;
import service.abroad.ApplySelfService;
import service.abroad.ApprovalLogService;
import service.abroad.ApprovalOrderService;
import service.abroad.ApproverBlackListService;
import service.abroad.ApproverService;
import service.abroad.ApproverTypeService;
import service.abroad.PassportApplyService;
import service.abroad.PassportDrawService;
import service.abroad.PassportService;
import service.abroad.SafeBoxService;
import service.abroad.TaiwanRecordService;

/**
 * Created by lm on 2017/9/20.
 */
public class AbroadBaseController extends BaseController {

    @Autowired
    protected ApplicatTypeService applicatTypeService;
    @Autowired
    protected ApprovalOrderService approvalOrderService;
    @Autowired
    protected ApproverService approverService;
    @Autowired
    protected ApproverBlackListService approverBlackListService;
    @Autowired
    protected ApproverTypeService approverTypeService;

    @Autowired
    protected AbroadAdditionalPostService abroadAdditionalPostService;

    @Autowired
    protected PassportDrawService passportDrawService;
    @Autowired
    protected ApprovalLogService approvalLogService;
    @Autowired
    protected ApplySelfService applySelfService;
    @Autowired
    protected PassportApplyService passportApplyService;
    @Autowired
    protected PassportService passportService;
    @Autowired
    protected SafeBoxService safeBoxService;
    @Autowired
    protected TaiwanRecordService taiwanRecordService;
    @Autowired
    protected AbroadExportService abroadExportService;

    @Autowired
    protected AbroadShortMsgService abroadShortMsgService;
}
