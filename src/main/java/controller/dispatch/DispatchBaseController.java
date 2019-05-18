package controller.dispatch;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.dispatch.*;

public class DispatchBaseController extends BaseController {

    @Autowired
    protected DispatchService dispatchService;
    @Autowired
    protected DispatchTypeService dispatchTypeService;
    @Autowired
    protected DispatchCadreService dispatchCadreService;
    @Autowired
    protected DispatchCadreRelateService dispatchCadreRelateService;
    @Autowired
    protected DispatchUnitRelateService dispatchUnitRelateService;
    @Autowired
    protected DispatchUnitService dispatchUnitService;
    @Autowired
    protected DispatchWorkFileService dispatchWorkFileService;
}
