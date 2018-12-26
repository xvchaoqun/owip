package service.oa;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.oa.*;
import service.CoreBaseMapper;

public class OaBaseMapper extends CoreBaseMapper {

    /**
     * 协同办公
     */
    @Autowired(required = false)
    protected OaTaskMapper oaTaskMapper;
    @Autowired(required = false)
    protected OaTaskViewMapper oaTaskViewMapper;
    @Autowired(required = false)
    protected OaTaskFileMapper oaTaskFileMapper;
    @Autowired(required = false)
    protected OaTaskMsgMapper oaTaskMsgMapper;
    @Autowired(required = false)
    protected OaTaskRemindMapper oaTaskRemindMapper;
    @Autowired(required = false)
    protected OaTaskUserMapper oaTaskUserMapper;
    @Autowired(required = false)
    protected OaTaskUserViewMapper oaTaskUserViewMapper;
    @Autowired(required = false)
    protected OaTaskUserFileMapper oaTaskUserFileMapper;

}
