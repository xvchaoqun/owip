package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scPublic.ScPublicService;
import service.sc.scPublic.ScPublicUserService;

public class ScPublicBaseController extends BaseController {

    @Autowired
    protected ScPublicService scPublicService;
    @Autowired
    protected ScPublicUserService scPublicUserService;

}
