package controller.sc.scDispatch;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scDispatch.ScDispatchCommitteeService;
import service.sc.scDispatch.ScDispatchService;
import service.sc.scDispatch.ScDispatchUserService;

public class ScDispatchBaseController extends BaseController {


    @Autowired
    protected ScDispatchService scDispatchService;
    @Autowired
    protected ScDispatchUserService scDispatchUserService;
    @Autowired
    protected ScDispatchCommitteeService scDispatchCommitteeService;
}
