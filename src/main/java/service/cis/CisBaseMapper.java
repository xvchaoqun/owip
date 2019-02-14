package service.cis;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.cis.*;
import persistence.cis.common.ICisMapper;
import service.CoreBaseMapper;

public class CisBaseMapper extends CoreBaseMapper {

    /**
     * 干部考察系统
     */
    @Autowired(required = false)
    protected CisEvaluateMapper cisEvaluateMapper;
    @Autowired(required = false)
    protected CisInspectObjMapper cisInspectObjMapper;
    @Autowired(required = false)
    protected CisInspectObjViewMapper cisInspectObjViewMapper;
    @Autowired(required = false)
    protected CisInspectorMapper cisInspectorMapper;
    @Autowired(required = false)
    protected CisObjInspectorMapper cisObjInspectorMapper;
    @Autowired(required = false)
    protected CisObjUnitMapper cisObjUnitMapper;
    @Autowired(required = false)
    protected ICisMapper iCisMapper;

}
