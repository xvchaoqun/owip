package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.pmd.PmdMonthService;
import service.sc.ScMatterAccessItemService;
import service.sc.ScMatterAccessService;
import service.sc.ScMatterCheckItemService;
import service.sc.ScMatterCheckService;
import service.sc.ScMatterItemService;
import service.sc.ScMatterService;
import service.sc.ScMatterTransferItemService;
import service.sc.ScMatterTransferService;

public class ScBaseController extends BaseController {

    @Autowired
    protected PmdMonthService pmdMonthService;

    @Autowired
    protected ScMatterService scMatterService;
    @Autowired
    protected ScMatterItemService scMatterItemService;
    @Autowired
    protected ScMatterAccessService scMatterAccessService;
    @Autowired
    protected ScMatterAccessItemService scMatterAccessItemService;
    @Autowired
    protected ScMatterCheckService scMatterCheckService;
    @Autowired
    protected ScMatterCheckItemService scMatterCheckItemService;
    @Autowired
    protected ScMatterTransferService scMatterTransferService;
    @Autowired
    protected ScMatterTransferItemService scMatterTransferItemService;

}
