package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.pmd.PmdBranchAdminService;
import service.pmd.PmdBranchService;
import service.pmd.PmdMemberPayService;
import service.pmd.PmdMemberService;
import service.pmd.PmdMonthService;
import service.pmd.PmdNormService;
import service.pmd.PmdNormValueLogService;
import service.pmd.PmdNormValueService;
import service.pmd.PmdNotifyLogService;
import service.pmd.PmdPartyAdminService;
import service.pmd.PmdPartyService;
import service.pmd.PmdPayService;
import service.pmd.PmdSpecialUserService;

public class PmdBaseController extends BaseController {

    @Autowired
    protected PmdMonthService pmdMonthService;
    @Autowired
    protected PmdMemberService pmdMemberService;
    @Autowired
    protected PmdMemberPayService pmdMemberPayService;
    @Autowired
    protected PmdNotifyLogService pmdNotifyLogService;
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
    protected PmdSpecialUserService pmdSpecialUserService;
    @Autowired
    protected PmdNormService pmdNormService;
    @Autowired
    protected PmdNormValueService pmdNormValueService;
    @Autowired
    protected PmdNormValueLogService pmdNormValueLogService;
}
