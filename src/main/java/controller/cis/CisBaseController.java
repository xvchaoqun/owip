package controller.cis;

import org.springframework.beans.factory.annotation.Autowired;
import service.cis.*;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

public class CisBaseController extends CisBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    
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
