package controller.sc.scAd;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scAd.ScAdArchiveService;
import service.sc.scAd.ScAdArchiveVoteService;
import service.sc.scAd.ScAdUseService;

public class ScAdBaseController extends BaseController {


    @Autowired
    protected ScAdArchiveService scAdArchiveService;
    @Autowired
    protected ScAdArchiveVoteService scAdArchiveVoteService;
    @Autowired
    protected ScAdUseService scAdUseService;
}
