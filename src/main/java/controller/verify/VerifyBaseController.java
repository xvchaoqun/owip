package controller.verify;

import org.springframework.beans.factory.annotation.Autowired;
import service.verify.VerifyAgeService;
import service.verify.VerifyBaseMapper;
import service.verify.VerifyJoinPartyTimeService;
import service.verify.VerifyWorkTimeService;
import sys.HttpResponseMethod;

public class VerifyBaseController extends VerifyBaseMapper implements HttpResponseMethod {

    @Autowired
    protected VerifyAgeService verifyAgeService;
    @Autowired
    protected VerifyWorkTimeService verifyWorkTimeService;
    @Autowired
    protected VerifyJoinPartyTimeService verifyJoinPartyTimeService;
}
