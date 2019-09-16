package controller.cg;

import org.springframework.beans.factory.annotation.Autowired;
import service.base.MetaTypeService;
import service.cg.*;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.HttpResponseMethod;

public class CgBaseController extends CgBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected UnitService unitService;

    @Autowired
    protected CgTeamService cgTeamService;
    @Autowired
    protected CgMemberService cgMemberService;
    @Autowired
    protected CgLeaderService cgLeaderService;
    @Autowired
    protected CgUnitService cgUnitService;
    @Autowired
    protected CgRuleService cgRuleService;

    @Autowired
    protected MetaTypeService metaTypeService;
}
