package controller.verify;

import org.springframework.beans.factory.annotation.Autowired;
import service.cadre.CadreService;
import service.verify.VerifyAgeService;
import service.verify.VerifyBaseMapper;
import service.verify.VerifyGrowTimeService;
import service.verify.VerifyWorkTimeService;
import sys.HttpResponseMethod;

public class VerifyBaseController extends VerifyBaseMapper implements HttpResponseMethod {

    @Autowired
    protected CadreService cadreService;

    @Autowired
    protected VerifyAgeService verifyAgeService;
    @Autowired
    protected VerifyWorkTimeService verifyWorkTimeService;
    @Autowired
    protected VerifyGrowTimeService verifyGrowTimeService;
}
