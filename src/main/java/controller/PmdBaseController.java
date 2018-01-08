package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.pmd.PmdBranchAdminService;
import service.pmd.PmdBranchService;
import service.pmd.PmdConfigMemberService;
import service.pmd.PmdConfigMemberTypeService;
import service.pmd.PmdConfigResetService;
import service.pmd.PmdExtService;
import service.pmd.PmdMemberPayService;
import service.pmd.PmdMemberService;
import service.pmd.PmdMonthService;
import service.pmd.PmdNormService;
import service.pmd.PmdNormValueLogService;
import service.pmd.PmdNormValueService;
import service.pmd.PmdPartyAdminService;
import service.pmd.PmdPartyService;
import service.pmd.PmdPayCampusCardService;
import service.pmd.PmdPayService;
import service.pmd.PmdPayWszfService;
import service.pmd.PmdSendMsgService;
import service.pmd.PmdSpecialUserService;

public class PmdBaseController extends BaseController {

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
    protected PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    protected PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    protected PmdPayService pmdPayService;
    @Autowired
    protected PmdPayWszfService pmdPayWszfService;
    @Autowired
    protected PmdPayCampusCardService pmdPayCampusCardService;
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
    protected PmdExtService pmdExtService;
    @Autowired
    protected PmdSendMsgService pmdSendMsgService;
}
