package service.parttime;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.parttime.*;
import persistence.parttime.common.IParttimeMapper;
import service.CoreBaseMapper;

public class ParttimeBaseMapper extends CoreBaseMapper {

    @Autowired(required = false)
    protected IParttimeMapper iParttimeMapper;

    @Autowired
    protected ParttimeApplicatTypeMapper parttimeApplicatTypeMapper;
    @Autowired
    protected ParttimeApplyMapper parttimeApplyMapper;
    @Autowired
    protected ParttimeApprovalLogMapper parttimeApprovalLogMapper;
    @Autowired
    protected ParttimeApplyFileMapper parttimeApplyFileMapper;
    @Autowired
    protected ParttimeApplyModifyMapper parttimeApplyModifyMapper;
    @Autowired
    protected ParttimeApproverTypeMapper parttimeApproverTypeMapper;
}
