package service.dp;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.dp.*;
import persistence.dp.common.IDpMemberMapper;
import persistence.dp.common.IDpPartyMapper;
import persistence.dp.common.IDpPropertyMapper;
import service.CoreBaseMapper;

public class DpBaseMapper extends CoreBaseMapper {

    /*
     * 民主党派
     * */
    @Autowired
    protected DpRewardMapper dpRewardMapper;
    @Autowired
    protected DpEduMapper dpEduMapper;
    @Autowired
    protected DpWorkMapper dpWorkMapper;
    @Autowired
    protected DpFamilyMapper dpFamilyMapper;
    @Autowired
    protected DpEvaMapper dpEvaMapper;
    @Autowired
    protected DpPrCmMapper dpPrCmMapper;
    @Autowired
    protected DpPrCmViewMapper dpPrCmViewMapper;
    @Autowired
    protected DpNpmViewMapper dpNpmViewMapper;
    @Autowired
    protected DpOmViewMapper dpOmViewMapper;
    @Autowired
    protected DpNprViewMapper dpNprViewMapper;
    @Autowired
    protected DpNprMapper dpNprMapper;
    @Autowired
    protected DpOmMapper dpOmMapper;
    @Autowired
    protected DpNpmMapper dpNpmMapper;
    @Autowired
    protected DpPartyMemberViewMapper dpPartyMemberViewMapper;
    @Autowired
    protected DpPartyViewMapper dpPartyViewMapper;
    @Autowired
    protected DpMemberViewMapper dpMemberViewMapper;
    @Autowired
    protected DpOrgAdminMapper dpOrgAdminMapper;
    @Autowired
    protected DpMemberMapper dpMemberMapper;
    @Autowired
    protected DpPartyMapper dpPartyMapper;
    @Autowired
    protected DpPartyMemberMapper dpPartyMemberMapper;
    @Autowired
    protected DpPartyMemberGroupMapper dpPartyMemberGroupMapper;


    @Autowired
    protected IDpPartyMapper iDpPartyMapper;
    @Autowired
    protected IDpPropertyMapper iDpPropertyMapper;
    @Autowired
    protected IDpMemberMapper iDpMemberMapper;

}
