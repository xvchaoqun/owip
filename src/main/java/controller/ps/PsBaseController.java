package controller.ps;

import org.springframework.beans.factory.annotation.Autowired;
import service.party.PartyService;
import service.ps.PsBaseMapper;
import service.ps.PsInfoService;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

public class PsBaseController extends PsBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected PartyService partyService;

    @Autowired
    protected PsInfoService psInfoService;
}
