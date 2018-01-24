package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.modify.ModifyBaseApplyService;
import service.modify.ModifyBaseItemService;
import service.modify.ModifyCadreAuthService;
import service.modify.ModifyTableApplyService;

public class ModifyBaseController extends BaseController {

    @Autowired
    protected ModifyCadreAuthService modifyCadreAuthService;
    @Autowired
    protected ModifyBaseApplyService modifyBaseApplyService;
    @Autowired
    protected ModifyBaseItemService modifyBaseItemService;
    @Autowired
    protected ModifyTableApplyService modifyTableApplyService;
}
