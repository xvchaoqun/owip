package controller.verify;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.verify.VerifyAgeService;
import service.verify.VerifyWorkTimeService;

public class VerifyBaseController extends BaseController {

    @Autowired
    protected VerifyAgeService verifyAgeService;
    @Autowired
    protected VerifyWorkTimeService verifyWorkTimeService;
}
