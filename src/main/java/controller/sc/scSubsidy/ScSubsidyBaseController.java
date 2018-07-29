package controller.sc.scSubsidy;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scSubsidy.ScSubsidyCadreService;
import service.sc.scSubsidy.ScSubsidyDcService;
import service.sc.scSubsidy.ScSubsidyDispatchService;
import service.sc.scSubsidy.ScSubsidyService;

public class ScSubsidyBaseController extends BaseController {

    @Autowired
    protected ScSubsidyService scSubsidyService;
    @Autowired
    protected ScSubsidyCadreService scSubsidyCadreService;
    @Autowired
    protected ScSubsidyDcService scSubsidyDcService;
    @Autowired
    protected ScSubsidyDispatchService scSubsidyDispatchService;

}
