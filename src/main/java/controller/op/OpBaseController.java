package controller.op;

import org.springframework.beans.factory.annotation.Autowired;
import service.SpringProps;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.op.OpAttatchService;
import service.op.OpBaseMapper;
import service.op.OpRecordService;
import service.op.OpReportService;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

@SuppressWarnings("unchecked")
public class OpBaseController extends OpBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected OpReportService opReportService;
    @Autowired
    protected OpRecordService opRecordService;
    @Autowired
    protected OpAttatchService opAttatchService;
    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected MetaTypeService metaTypeService;
}
