package service.verify;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.verify.VerifyAgeMapper;
import persistence.verify.VerifyJoinPartyTimeMapper;
import persistence.verify.VerifyWorkTimeMapper;
import service.CoreBaseMapper;
import sys.HttpResponseMethod;

public class VerifyBaseMapper extends CoreBaseMapper implements HttpResponseMethod {

    @Autowired
    protected VerifyAgeMapper verifyAgeMapper;
    @Autowired
    protected VerifyWorkTimeMapper verifyWorkTimeMapper;
    @Autowired
    protected VerifyJoinPartyTimeMapper verifyJoinPartyTimeMapper;

}
