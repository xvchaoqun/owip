package controller.cla;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.cla.ClaAdditionalPostService;
import service.cla.ClaApplicatTypeService;
import service.cla.ClaApplyService;
import service.cla.ClaApprovalLogService;
import service.cla.ClaApprovalOrderService;
import service.cla.ClaApproverBlackListService;
import service.cla.ClaApproverService;
import service.cla.ClaApproverTypeService;
import service.cla.ClaExportService;
import service.cla.ClaShortMsgService;

public class ClaBaseController extends BaseController {

    @Autowired
    protected ClaApplicatTypeService claApplicatTypeService;
    @Autowired
    protected ClaApprovalOrderService claApprovalOrderService;
    @Autowired
    protected ClaApproverService claApproverService;
    @Autowired
    protected ClaApproverBlackListService claApproverBlackListService;
    @Autowired
    protected ClaApproverTypeService claApproverTypeService;

    @Autowired
    protected ClaAdditionalPostService claAdditionalPostService;

    @Autowired
    protected ClaApprovalLogService claApprovalLogService;
    @Autowired
    protected ClaApplyService claApplyService;
    @Autowired
    protected ClaExportService claExportService;

    @Autowired
    protected ClaShortMsgService claShortMsgService;
}
