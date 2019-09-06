package controller.dp;

import ext.service.ExtService;
import org.springframework.beans.factory.annotation.Autowired;
import service.LoginUserService;
import service.base.MetaTypeService;
import service.dp.*;
import service.dp.dpCommon.DpCommonService;
import service.dp.dpCommon.StatDpPartyMemberService;
import service.global.CacheHelper;
import service.global.CacheService;
import service.party.MemberService;
import service.sys.StudentInfoService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import service.unit.UnitService;
import sys.HttpResponseMethod;

public class DpBaseController extends DpBaseMapper implements HttpResponseMethod {

    @Autowired
    protected DpPrCmService dpPrCmService;
    @Autowired
    protected DpNprService dpNprService;
    @Autowired
    protected DpOmService dpOmService;
    @Autowired
    protected DpNpmService dpNpmService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected DpCommonService dpCommonService;
    @Autowired
    protected CacheService cacheService;
    @Autowired
    protected CacheHelper cacheHelper;
    @Autowired
    protected StudentInfoService studentInfoService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected StatDpPartyMemberService statDpPartyMemberService;
    @Autowired
    protected DpOrgAdminService dpOrgAdminService;
    @Autowired
    protected DpPartyService dpPartyService;
    @Autowired
    protected DpPartyMemberService dpPartyMemberService;
    @Autowired
    protected DpPartyMemberAdminService dpPartyMemberAdminService;
    @Autowired
    protected DpPartyMemberGroupService dpPartyMemberGroupService;
    @Autowired
    protected DpMemberService dpMemberService;

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected ExtService extService;
    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected UnitService unitService;


}
