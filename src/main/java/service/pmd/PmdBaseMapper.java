package service.pmd;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.pmd.*;
import persistence.pmd.common.IPmdMapper;
import service.CoreBaseMapper;

public class PmdBaseMapper extends CoreBaseMapper {

    /**
     * 党费
     */
    @Autowired(required = false)
    protected PmdPayPartyMapper pmdPayPartyMapper;
    @Autowired(required = false)
    protected PmdPayBranchMapper pmdPayBranchMapper;
    @Autowired(required = false)
    protected PmdPayPartyViewMapper pmdPayPartyViewMapper;
    @Autowired(required = false)
    protected PmdPayBranchViewMapper pmdPayBranchViewMapper;
    @Autowired(required = false)
    protected PmdMonthMapper pmdMonthMapper;
    @Autowired(required = false)
    protected IPmdMapper iPmdMapper;
    @Autowired(required = false)
    protected PmdMemberMapper pmdMemberMapper;
    @Autowired(required = false)
    protected PmdMemberPayMapper pmdMemberPayMapper;
    @Autowired(required = false)
    protected PmdMemberPayViewMapper pmdMemberPayViewMapper;
    @Autowired(required = false)
    protected PmdOrderMapper pmdOrderMapper;
    @Autowired(required = false)
    protected PmdOrderItemMapper pmdOrderItemMapper;
    @Autowired(required = false)
    protected PmdNotifyMapper pmdNotifyMapper;
    @Autowired(required = false)
    protected PmdPartyMapper pmdPartyMapper;
    @Autowired(required = false)
    protected PmdPartyViewMapper pmdPartyViewMapper;
    @Autowired(required = false)
    protected PmdBranchMapper pmdBranchMapper;
    @Autowired(required = false)
    protected PmdBranchViewMapper pmdBranchViewMapper;
    @Autowired(required = false)
    protected PmdPartyAdminMapper pmdPartyAdminMapper;
    @Autowired(required = false)
    protected PmdBranchAdminMapper pmdBranchAdminMapper;
    @Autowired(required = false)
    protected PmdNormMapper pmdNormMapper;
    @Autowired(required = false)
    protected PmdNormValueMapper pmdNormValueMapper;
    @Autowired(required = false)
    protected PmdNormValueLogMapper pmdNormValueLogMapper;
    @Autowired(required = false)
    protected PmdSpecialUserMapper pmdSpecialUserMapper;
    @Autowired(required = false)
    protected PmdConfigMemberTypeMapper pmdConfigMemberTypeMapper;
    @Autowired(required = false)
    protected PmdConfigMemberMapper pmdConfigMemberMapper;
    @Autowired(required = false)
    protected PmdConfigMemberViewMapper pmdConfigMemberViewMapper;
    @Autowired(required = false)
    protected PmdConfigResetMapper pmdConfigResetMapper;

    @Autowired(required = false)
    protected PmdFeeMapper pmdFeeMapper;
}
