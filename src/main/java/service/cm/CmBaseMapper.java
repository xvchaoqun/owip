package service.cm;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.cm.CmMemberMapper;
import persistence.cm.CmMemberViewMapper;
import service.CoreBaseMapper;

public class CmBaseMapper extends CoreBaseMapper {

    /**
     * 两委委员
     */
    @Autowired(required = false)
    protected CmMemberMapper cmMemberMapper;
    @Autowired(required = false)
    protected CmMemberViewMapper cmMemberViewMapper;
}
