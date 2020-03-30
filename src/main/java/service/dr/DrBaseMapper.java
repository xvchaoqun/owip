package service.dr;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.dr.*;
import persistence.dr.common.IDrMapper;
import service.CoreBaseMapper;

public class DrBaseMapper extends CoreBaseMapper {

    //线上民主推荐
    @Autowired(required = false)
    protected DrOnlineInspectorMapper drOnlineInspectorMapper;
    @Autowired(required = false)
    protected DrOnlineInspectorLogMapper drOnlineInspectorLogMapper;
    @Autowired(required = false)
    protected DrOnlineNoticeMapper drOnlineNoticeMapper;
    @Autowired(required = false)
    protected DrOnlineInspectorTypeMapper drOnlineInspectorTypeMapper;
    @Autowired(required = false)
    protected DrOnlineResultViewMapper drOnlineResultViewMapper;
    @Autowired(required = false)
    protected DrOnlineResultMapper drOnlineResultMapper;
    @Autowired(required = false)
    protected DrOnlineCandidateMapper drOnlineCandidateMapper;
    @Autowired(required = false)
    protected DrOnlinePostViewMapper drOnlinePostViewMapper;
    @Autowired(required = false)
    protected DrOnlinePostMapper drOnlinePostMapper;
    @Autowired(required = false)
    protected DrOnlineMapper drOnlineMapper;

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
