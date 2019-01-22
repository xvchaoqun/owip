package controller.pmd;

import org.springframework.beans.factory.annotation.Autowired;
import service.base.ShortMsgService;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import service.pmd.*;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

public class PmdBaseController extends PmdBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected ShortMsgService shortMsgService;
    
    @Autowired
    protected PmdMonthService pmdMonthService;
    @Autowired
    protected PmdMemberService pmdMemberService;
    @Autowired
    protected PmdMemberPayService pmdMemberPayService;
    @Autowired
    protected PmdPartyService pmdPartyService;
    @Autowired
    protected PmdBranchService pmdBranchService;
    @Autowired
    protected PmdPayPartyService pmdPayPartyService;
    @Autowired
    protected PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    protected PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    protected PmdPayService pmdPayService;

    @Autowired
    protected PmdOrderService pmdOrderService;
    @Autowired
    protected PmdSpecialUserService pmdSpecialUserService;
    @Autowired
    protected PmdNormService pmdNormService;
    @Autowired
    protected PmdNormValueService pmdNormValueService;
    @Autowired
    protected PmdNormValueLogService pmdNormValueLogService;
    @Autowired
    protected PmdConfigMemberTypeService pmdConfigMemberTypeService;
    @Autowired
    protected PmdConfigMemberService pmdConfigMemberService;
    @Autowired
    protected PmdConfigResetService pmdConfigResetService;
    @Autowired
    protected PmdExportService pmdExportService;
    @Autowired
    protected PmdExtService pmdExtService;
    @Autowired
    protected PmdSendMsgService pmdSendMsgService;
}
