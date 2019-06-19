package service.ps;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.ps.*;
import service.CoreBaseMapper;

public class PsBaseMapper extends CoreBaseMapper {

    /**
     * 二级党校
     */
    @Autowired(required = false)
    protected PsAdminMapper psAdminMapper;
    @Autowired(required = false)
    protected PsAdminPartyMapper psAdminPartyMapper;
    @Autowired(required = false)
    protected PsInfoMapper psInfoMapper;
    @Autowired(required = false)
    protected PsMemberMapper psMemberMapper;
    @Autowired(required = false)
    protected PsPartyMapper psPartyMapper;
    @Autowired(required = false)
    protected PsTaskMapper psTaskMapper;
}
