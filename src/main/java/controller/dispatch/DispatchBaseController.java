package controller.dispatch;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.dispatch.DispatchCadreRelateService;
import service.dispatch.DispatchCadreService;
import service.dispatch.DispatchService;
import service.dispatch.DispatchTypeService;
import service.dispatch.DispatchUnitRelateService;
import service.dispatch.DispatchUnitService;
import service.dispatch.DispatchWorkFileService;

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
