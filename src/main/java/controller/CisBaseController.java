package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.cis.CisEvaluateService;
import service.cis.CisInspectObjService;
import service.cis.CisInspectorService;
import service.cis.CisObjInspectorService;
import service.cis.CisObjUnitService;

public class CisBaseController extends BaseController {

    @Autowired
    protected CisEvaluateService cisEvaluateService;
    @Autowired
    protected CisInspectObjService cisInspectObjService;
    @Autowired
    protected CisInspectorService cisInspectorService;
    @Autowired
    protected CisObjInspectorService cisObjInspectorService;
    @Autowired
    protected CisObjUnitService cisObjUnitService;
}
