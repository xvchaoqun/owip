package controller.cm;

import org.springframework.beans.factory.annotation.Autowired;
import service.base.MetaClassService;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.cm.CmBaseMapper;
import service.cm.CmMemberService;
import service.ext.ExtService;
import service.party.BranchService;
import service.party.MemberService;
import service.party.MemberTeacherService;
import service.party.PartyService;
import service.pcs.PcsConfigService;
import service.sys.StudentInfoService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import service.sys.UserBeanService;
import sys.HttpResponseMethod;

public class CmBaseController extends CmBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected MemberTeacherService memberTeacherService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected StudentInfoService studentInfoService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected MetaClassService metaClassService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected ExtService extService;
    @Autowired(required = false)
    protected PcsConfigService pcsConfigService;

    @Autowired(required = false)
    protected CmMemberService cmMemberService;
}
