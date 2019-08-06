package controller.ps;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.ps.common.IPsMapper;
import service.party.PartyService;
import service.ps.*;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

public class PsBaseController extends PsBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected PartyService partyService;

    @Autowired
    protected PsInfoService psInfoService;
    @Autowired
    protected PsTaskService psTaskService;
    @Autowired
    protected PsAdminService psAdminService;
    @Autowired
    protected PsAdminPartyService psAdminPartyService;
    @Autowired
    protected PsMemberService psMemberService;
    @Autowired
    protected PsPartyService psPartyService;
    @Autowired
    protected IPsMapper iPsMapper;
}
