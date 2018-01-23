package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scMatter.ScMatterAccessItemService;
import service.sc.scMatter.ScMatterAccessService;
import service.sc.scMatter.ScMatterCheckItemService;
import service.sc.scMatter.ScMatterCheckService;
import service.sc.scMatter.ScMatterItemService;
import service.sc.scMatter.ScMatterService;
import service.sc.scMatter.ScMatterTransferService;

public class ScBaseController extends BaseController {

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

}
