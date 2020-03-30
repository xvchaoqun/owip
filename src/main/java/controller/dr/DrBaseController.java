package controller.dr;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.sc.IScMapper;
import persistence.sc.scRecord.ScRecordViewMapper;
import service.base.ContentTplService;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.dr.*;
import service.sys.SysLoginLogService;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.HttpResponseMethod;

public class DrBaseController extends DrBaseMapper implements HttpResponseMethod {

    //线上民主推荐
    @Autowired
    protected DrCommonService drCommonService;
    @Autowired
    protected DrOnlineInspectorService drOnlineInspectorService;
    @Autowired
    protected DrOnlineInspectorLogService drOnlineInspectorLogService;
    @Autowired
    protected DrOnlineNoticeService drOnlineNoticeService;
    @Autowired
    protected DrOnlineInspectorTypeService drOnlineInspectorTypeService;
    @Autowired
    protected DrOnlineResultService drOnlineResultService;
    @Autowired
    protected DrOnlineCandidateService drOnlineCandidateService;
    @Autowired
    protected DrOnlinePostService drOnlinePostService;
    @Autowired
    protected DrOnlineService drOnlineService;
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected SysLoginLogService sysLoginLogService;

    @Autowired
    protected UnitService unitService;
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
