package service.parttime;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.parttime.*;
import persistence.parttime.common.IParttimeMapper;
import service.CoreBaseMapper;

public class ParttimeBaseMapper extends CoreBaseMapper {

    @Autowired(required = false)
    protected IParttimeMapper iParttimeMapper;

     @Autowired
    protected ParttimeApplyMapper parttimeApplyMapper;
    @Autowired
    protected ParttimeApplicatTypeMapper parttimeApplicatTypeMapper;
    @Autowired
    protected ParttimeApprovalLogMapper parttimeApprovalLogMapper;
    @Autowired
    protected ParttimeApplyFileMapper parttimeApplyFileMapper;
    @Autowired
    protected ParttimeApplyModifyMapper parttimeApplyModifyMapper;
    @Autowired
    protected ParttimeApproverTypeMapper parttimeApproverTypeMapper;
    @Autowired
    protected ParttimeApproverBlackListMapper parttimeApproverBlackListMapper;
    @Autowired
    protected ParttimeApproverMapper parttimeApproverMapper;
    @Autowired
    protected ParttimeApplicatCadreMapper parttimeApplicatCadreMapper;
    @Autowired
    protected ParttimeApprovalOrderMapper parttimeApprovalOrderMapper;
}
