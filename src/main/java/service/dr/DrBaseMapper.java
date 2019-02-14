package service.dr;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.dr.*;
import persistence.dr.common.IDrMapper;
import service.CoreBaseMapper;

public class DrBaseMapper extends CoreBaseMapper {

    @Autowired(required = false)
    protected IDrMapper iDrMapper;

    @Autowired(required = false)
    protected DrMemberMapper drMemberMapper;
    @Autowired(required = false)
    protected DrOfflineMapper drOfflineMapper;
    @Autowired(required = false)
    protected DrOfflineViewMapper drOfflineViewMapper;
    @Autowired(required = false)
    protected DrOfflineCandidateMapper drOfflineCandidateMapper;
    @Autowired(required = false)
    protected DrVoterTypeMapper drVoterTypeMapper;
    @Autowired(required = false)
    protected DrVoterTypeTplMapper drVoterTypeTplMapper;
}
