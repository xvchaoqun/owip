package controller.sc.scPassport;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scPassport.ScPassportHandService;
import service.sc.scPassport.ScPassportService;

public class ScPassportBaseController extends BaseController {

    @Autowired
    protected ScPassportService scPassportService;
    @Autowired
    protected ScPassportHandService scPassportHandService;

}
