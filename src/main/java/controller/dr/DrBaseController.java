package controller.dr;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.sc.IScMapper;
import persistence.sc.scRecord.ScRecordViewMapper;
import service.base.ContentTplService;
import service.cadre.CadreService;
import service.dr.*;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

public class DrBaseController extends DrBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected ContentTplService contentTplService;
    @Autowired
    protected CadreService cadreService;
    @Autowired(required = false)
    protected ScRecordViewMapper scRecordViewMapper;
    @Autowired(required = false)
    protected IScMapper iScMapper;
    
    @Autowired
    protected DrMemberService drMemberService;
    @Autowired
    protected DrOfflineService drOfflineService;
    @Autowired
    protected DrVoterTypeTplService drVoterTypeTplService;
    @Autowired
    protected DrVoterTypeService drVoterTypeService;
    @Autowired
    protected DrExportService drExportService;
}
