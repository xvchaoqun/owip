package service.parttime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.base.MetaTypeService;
import service.common.FreemarkerService;
import service.sys.SysUserService;

@Service
public class ParttimeExportService extends ParttimeBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ParttimeApplyService parttimeApplyService;
    @Autowired
    private ParttimeApproverTypeService parttimeApproverTypeService;
    @Autowired
    private FreemarkerService freemarkerService;

}
