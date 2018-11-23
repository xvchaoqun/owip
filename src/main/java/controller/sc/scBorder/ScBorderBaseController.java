package controller.sc.scBorder;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scBorder.ScBorderItemService;
import service.sc.scBorder.ScBorderService;

public class ScBorderBaseController extends BaseController {

    @Autowired(required = false)
    protected ScBorderService scBorderService;
    @Autowired(required = false)
    protected ScBorderItemService scBorderItemService;

}
