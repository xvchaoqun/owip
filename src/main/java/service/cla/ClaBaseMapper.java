package service.cla;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.cla.*;
import persistence.cla.common.IClaMapper;
import service.CoreBaseMapper;

public class ClaBaseMapper extends CoreBaseMapper {

    /**
     * 干部请假审批
     */
    @Autowired(required = false)
    protected IClaMapper iClaMapper;
    
    @Autowired(required = false)
    protected ClaApplicatCadreMapper claApplicatCadreMapper;
    @Autowired(required = false)
    protected ClaApplicatTypeMapper claApplicatTypeMapper;
    @Autowired(required = false)
    protected ClaApprovalOrderMapper claApprovalOrderMapper;
    @Autowired(required = false)
    protected ClaApproverMapper claApproverMapper;
    @Autowired(required = false)
    protected ClaApproverBlackListMapper claApproverBlackListMapper;
    @Autowired(required = false)
    protected ClaApproverTypeMapper claApproverTypeMapper;
    @Autowired(required = false)
    protected ClaApplyMapper claApplyMapper;
    @Autowired(required = false)
    protected ClaApplyModifyMapper claApplyModifyMapper;
    @Autowired(required = false)
    protected ClaApplyFileMapper claApplyFileMapper;
    @Autowired(required = false)
    protected ClaApprovalLogMapper claApprovalLogMapper;
    @Autowired(required = false)
    protected ClaAdditionalPostMapper claAdditionalPostMapper;
    @Autowired(required = false)
    protected ClaAdditionalPostViewMapper claAdditionalPostViewMapper;
}
