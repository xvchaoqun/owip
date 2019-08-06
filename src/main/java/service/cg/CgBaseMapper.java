package service.cg;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.cg.*;
import service.CoreBaseMapper;

public class CgBaseMapper extends CoreBaseMapper {

    /**
     * 委员会和领导小组
     */
    @Autowired(required = false)
    protected CgTeamMapper cgTeamMapper;
    @Autowired(required = false)
    protected CgLeaderMapper cgLeaderMapper;
    @Autowired(required = false)
    protected CgRuleMapper cgRuleMapper;
    @Autowired(required = false)
    protected CgMemberMapper cgMemberMapper;
    @Autowired(required = false)
    protected CgUnitMapper cgUnitMapper;
}
