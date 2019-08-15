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
    protected IDpPartyMapper iDpPartyMapper;
    @Autowired
    protected DpPartyViewMapper dpPartyViewMapper;
    @Autowired
    protected DpMemberOutMapper dpMemberOutMapper;
    @Autowired
    protected DpMemberViewMapper dpMemberViewMapper;
    @Autowired
    protected IDpPropertyMapper iDpPropertyMapper;
    @Autowired
    protected IDpMemberMapper iDpMemberMapper;
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

}
