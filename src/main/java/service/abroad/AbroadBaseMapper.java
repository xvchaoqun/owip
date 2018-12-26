package service.abroad;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.abroad.*;
import persistence.abroad.common.IAbroadMapper;
import service.CoreBaseMapper;

public class AbroadBaseMapper extends CoreBaseMapper {
    /**
     * 因私出国境
     */
    @Autowired(required = false)
    protected IAbroadMapper iAbroadMapper;
    @Autowired(required = false)
    protected ApplicatCadreMapper applicatCadreMapper;
    @Autowired(required = false)
    protected ApplicatTypeMapper applicatTypeMapper;
    @Autowired(required = false)
    protected ApprovalOrderMapper approvalOrderMapper;
    @Autowired(required = false)
    protected ApproverMapper approverMapper;
    @Autowired(required = false)
    protected ApproverBlackListMapper approverBlackListMapper;
    @Autowired(required = false)
    protected ApproverTypeMapper approverTypeMapper;
    @Autowired(required = false)
    protected PassportDrawFileMapper passportDrawFileMapper;
    @Autowired(required = false)
    protected PassportDrawMapper passportDrawMapper;
    @Autowired(required = false)
    protected ApplySelfMapper applySelfMapper;
    @Autowired(required = false)
    protected ApplySelfModifyMapper applySelfModifyMapper;
    @Autowired(required = false)
    protected ApplySelfFileMapper applySelfFileMapper;
    @Autowired(required = false)
    protected ApprovalLogMapper approvalLogMapper;
    @Autowired(required = false)
    protected AbroadAdditionalPostMapper abroadAdditionalPostMapper;
    @Autowired(required = false)
    protected AbroadAdditionalPostViewMapper abroadAdditionalPostViewMapper;
    @Autowired(required = false)
    protected PassportMapper passportMapper;
    @Autowired(required = false)
    protected SafeBoxMapper safeBoxMapper;
    @Autowired(required = false)
    protected PassportApplyMapper passportApplyMapper;
    @Autowired(required = false)
    protected PassportApplyViewMapper passportApplyViewMapper;
    @Autowired(required = false)
    protected TaiwanRecordMapper taiwanRecordMapper;
}
