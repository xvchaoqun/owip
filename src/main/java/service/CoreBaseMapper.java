package service;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.base.*;
import persistence.base.common.IBaseMapper;
import persistence.cadre.*;
import persistence.cadre.common.ICadreMapper;
import persistence.cadreInspect.CadreInspectMapper;
import persistence.cadreInspect.CadreInspectViewMapper;
import persistence.cadreReserve.CadreReserveMapper;
import persistence.cadreReserve.CadreReserveOriginMapper;
import persistence.cadreReserve.CadreReserveViewMapper;
import persistence.common.CommonMapper;
import persistence.common.IPropertyMapper;
import persistence.cpc.common.ICpcMapper;
import persistence.dispatch.*;
import persistence.dispatch.common.IDispatchMapper;
import persistence.ext.*;
import persistence.leader.LeaderMapper;
import persistence.leader.LeaderUnitMapper;
import persistence.leader.LeaderUnitViewMapper;
import persistence.leader.LeaderViewMapper;
import persistence.leader.common.ILeaderMapper;
import persistence.member.MemberMapper;
import persistence.member.common.IMemberMapper;
import persistence.member.common.StatMemberMapper;
import persistence.modify.common.IModifyMapper;
import persistence.party.*;
import persistence.party.common.IPartyMapper;
import persistence.sys.*;
import persistence.sys.common.ISysMapper;
import persistence.unit.*;
import persistence.unit.common.IUnitMapper;

import java.io.File;

public class CoreBaseMapper {
    
    @Autowired(required = false)
    protected HistoryUnitMapper historyUnitMapper;
    @Autowired(required = false)
    protected UnitMapper unitMapper;
    @Autowired(required = false)
    protected UnitViewMapper unitViewMapper;
    @Autowired(required = false)
    protected UnitPostMapper unitPostMapper;
    @Autowired(required = false)
    protected UnitPostViewMapper unitPostViewMapper;
    @Autowired(required = false)
    protected UnitPostCountViewMapper unitPostCountViewMapper;
    @Autowired(required = false)
    protected UnitTransferMapper unitTransferMapper;
    @Autowired(required = false)
    protected UnitCadreTransferGroupMapper unitCadreTransferGroupMapper;
    @Autowired(required = false)
    protected UnitCadreTransferMapper unitCadreTransferMapper;
    @Autowired(required = false)
    protected UnitTeamMapper unitTeamMapper;
    @Autowired(required = false)
    protected UnitTeamPlanMapper unitTeamPlanMapper;
    
    /**
     * 组织机构
     */
    @Autowired(required = false)
    protected BranchMemberGroupMapper branchMemberGroupMapper;
    @Autowired(required = false)
    protected BranchMemberGroupViewMapper branchMemberGroupViewMapper;
    @Autowired(required = false)
    protected BranchMemberMapper branchMemberMapper;
    @Autowired(required = false)
    protected BranchMemberViewMapper branchMemberViewMapper;
    @Autowired(required = false)
    protected PartyMapper partyMapper;
    @Autowired(required = false)
    protected PartyViewMapper partyViewMapper;
    @Autowired(required = false)
    protected PartyMemberMapper partyMemberMapper;
    @Autowired(required = false)
    protected PartyMemberViewMapper partyMemberViewMapper;
    @Autowired(required = false)
    protected PartyMemberGroupMapper partyMemberGroupMapper;
    @Autowired(required = false)
    protected PartyMemberGroupViewMapper partyMemberGroupViewMapper;
    @Autowired(required = false)
    protected BranchMapper branchMapper;
    @Autowired(required = false)
    protected BranchTransferLogMapper branchTransferLogMapper;
    @Autowired(required = false)
    protected BranchViewMapper branchViewMapper;
    @Autowired(required = false)
    protected OrgAdminMapper orgAdminMapper;
    
    @Autowired(required = false)
    protected MemberMapper memberMapper;
    
        /**
     * 发文
     */
    @Autowired(required = false)
    protected DispatchMapper dispatchMapper;
    @Autowired(required = false)
    protected DispatchViewMapper dispatchViewMapper;
    @Autowired(required = false)
    protected DispatchTypeMapper dispatchTypeMapper;
    @Autowired(required = false)
    protected DispatchCadreMapper dispatchCadreMapper;
    @Autowired(required = false)
    protected DispatchCadreViewMapper dispatchCadreViewMapper;
    @Autowired(required = false)
    protected DispatchCadreRelateMapper dispatchCadreRelateMapper;
    @Autowired(required = false)
    protected DispatchUnitMapper dispatchUnitMapper;
    @Autowired(required = false)
    protected DispatchUnitViewMapper dispatchUnitViewMapper;
    @Autowired(required = false)
    protected DispatchUnitRelateMapper dispatchUnitRelateMapper;
    @Autowired(required = false)
    protected DispatchWorkFileMapper dispatchWorkFileMapper;
    @Autowired(required = false)
    protected DispatchWorkFileAuthMapper dispatchWorkFileAuthMapper;
    
    /**
     * 干部库
     */
    @Autowired(required = false)
    protected CadreInfoMapper cadreInfoMapper;
    @Autowired(required = false)
    protected CadreInfoCheckMapper cadreInfoCheckMapper;
    @Autowired(required = false)
    protected CadreFamilyAbroadMapper cadreFamilyAbroadMapper;
    @Autowired(required = false)
    protected CadreFamilyMapper cadreFamilyMapper;
    @Autowired(required = false)
    protected CadreCompanyMapper cadreCompanyMapper;
    @Autowired(required = false)
    protected CadreCompanyViewMapper cadreCompanyViewMapper;
    @Autowired(required = false)
    protected CadreCompanyFileMapper cadreCompanyFileMapper;
    @Autowired(required = false)
    protected CadreParttimeMapper cadreParttimeMapper;
    @Autowired(required = false)
    protected CadreTrainMapper cadreTrainMapper;
    @Autowired(required = false)
    protected CadreRewardMapper cadreRewardMapper;
    @Autowired(required = false)
    protected CadrePaperMapper cadrePaperMapper;
    @Autowired(required = false)
    protected CadreResearchMapper cadreResearchMapper;
    @Autowired(required = false)
    protected CadreBookMapper cadreBookMapper;
    @Autowired(required = false)
    protected CadreCourseMapper cadreCourseMapper;
    @Autowired(required = false)
    protected CadrePostMapper cadrePostMapper;
    @Autowired(required = false)
    protected CadrePostProMapper cadrePostProMapper;
    @Autowired(required = false)
    protected CadrePostAdminMapper cadrePostAdminMapper;
    @Autowired(required = false)
    protected CadrePostWorkMapper cadrePostWorkMapper;
    @Autowired(required = false)
    protected CadreAdminLevelMapper cadreAdminLevelMapper;
    @Autowired(required = false)
    protected CadreWorkMapper cadreWorkMapper;
    @Autowired(required = false)
    protected CadreEduMapper cadreEduMapper;
    @Autowired(required = false)
    protected CadreEvaMapper cadreEvaMapper;
    @Autowired(required = false)
    protected CadreUnderEduMapper cadreUnderEduMapper;
    @Autowired(required = false)
    protected CadreTutorMapper cadreTutorMapper;
    @Autowired(required = false)
    protected CadreReportMapper cadreReportMapper;
    
    @Autowired(required = false)
    protected LeaderMapper leaderMapper;
    @Autowired(required = false)
    protected LeaderViewMapper leaderViewMapper;
    @Autowired(required = false)
    protected LeaderUnitMapper leaderUnitMapper;
    @Autowired(required = false)
    protected LeaderUnitViewMapper leaderUnitViewMapper;
    @Autowired(required = false)
    protected CadreMapper cadreMapper;
    @Autowired(required = false)
    protected CadreViewMapper cadreViewMapper;
    @Autowired(required = false)
    protected CadrePartyMapper cadrePartyMapper;
    @Autowired(required = false)
    protected CadreAdLogMapper cadreAdLogMapper;
    @Autowired(required = false)
    protected CadreInspectMapper cadreInspectMapper;
    @Autowired(required = false)
    protected CadreInspectViewMapper cadreInspectViewMapper;
    @Autowired(required = false)
    protected CadreReserveMapper cadreReserveMapper;
    @Autowired(required = false)
    protected CadreReserveViewMapper cadreReserveViewMapper;
    @Autowired(required = false)
    protected CadreReserveOriginMapper cadreReserveOriginMapper;
    @Autowired(required = false)
    protected CadreStatHistoryMapper cadreStatHistoryMapper;
    
    @Autowired(required = false)
    protected StudentInfoMapper studentInfoMapper;
    @Autowired(required = false)
    protected TeacherInfoMapper teacherInfoMapper;
    
    @Autowired(required = false)
    protected ExtYjsMapper extYjsMapper;
    @Autowired(required = false)
    protected ExtBksMapper extBksMapper;
    @Autowired(required = false)
    protected ExtJzgMapper extJzgMapper;
    @Autowired(required = false)
    protected ExtAbroadMapper extAbroadMapper;
    @Autowired(required = false)
    protected ExtRetireSalaryMapper extRetireSalaryMapper;
    @Autowired(required = false)
    protected ExtJzgSalaryMapper extJzgSalaryMapper;

    @Autowired(required = false)
    protected IPropertyMapper iPropertyMapper;
    @Autowired(required = false)
    protected ILeaderMapper iLeaderMapper;
    @Autowired(required = false)
    protected ICadreMapper iCadreMapper;
    @Autowired(required = false)
    protected ICpcMapper iCpcMapper;
    @Autowired(required = false)
    protected IDispatchMapper iDispatchMapper;
    @Autowired(required = false)
    protected IMemberMapper iMemberMapper;
    @Autowired(required = false)
    protected IModifyMapper iModifyMapper;
    @Autowired(required = false)
    protected IPartyMapper iPartyMapper;
    @Autowired(required = false)
    protected ISysMapper iSysMapper;
    @Autowired(required = false)
    protected IUnitMapper iUnitMapper;
    @Autowired(required = false)
    protected StatMemberMapper statMemberMapper;
    
    @Autowired(required = false)
    protected LocationMapper locationMapper;
    @Autowired(required = false)
    protected MetaClassMapper metaClassMapper;
    @Autowired(required = false)
    protected MetaTypeMapper metaTypeMapper;
    @Autowired(required = false)
    protected AnnualTypeMapper annualTypeMapper;
    @Autowired(required = false)
    protected CountryMapper countryMapper;
    @Autowired(required = false)
    protected ShortMsgMapper shortMsgMapper;
    @Autowired(required = false)
    protected ShortMsgReceiverMapper shortMsgReceiverMapper;
    @Autowired(required = false)
    protected ContentTplMapper contentTplMapper;
    @Autowired(required = false)
    protected SitemapMapper sitemapMapper;
    @Autowired(required = false)
    protected SitemapRoleMapper sitemapRoleMapper;
    @Autowired(required = false)
    protected ShortMsgTplMapper shortMsgTplMapper;
    @Autowired(required = false)
    protected IBaseMapper iBaseMapper;
    
    @Autowired(required = false)
    protected SysPropertyMapper sysPropertyMapper;
    @Autowired(required = false)
    protected SysConfigMapper sysConfigMapper;
    @Autowired(required = false)
    protected SysConfigLoginMsgMapper sysConfigLoginMsgMapper;
    @Autowired(required = false)
    protected SysApprovalLogMapper sysApprovalLogMapper;
    @Autowired(required = false)
    protected SysSyncMapper sysSyncMapper;
    @Autowired(required = false)
    protected SysLogMapper sysLogMapper;
    @Autowired(required = false)
    protected SysLoginLogMapper sysLoginLogMapper;
    @Autowired(required = false)
    protected SysUserMapper sysUserMapper;
    @Autowired(required = false)
    protected SysUserInfoMapper sysUserInfoMapper;
    @Autowired(required = false)
    protected SysUserViewMapper sysUserViewMapper;
    @Autowired(required = false)
    protected SysRoleMapper sysRoleMapper;
    @Autowired(required = false)
    protected SysResourceMapper sysResourceMapper;
    @Autowired(required = false)
    protected AttachFileMapper attachFileMapper;
    @Autowired(required = false)
    protected HtmlFragmentMapper htmlFragmentMapper;
    @Autowired(required = false)
    protected SysOnlineStaticMapper sysOnlineStaticMapper;
    @Autowired(required = false)
    protected FeedbackMapper feedbackMapper;
    
    @Autowired
    protected SpringProps springProps;
    @Autowired(required = false)
    protected CommonMapper commonMapper;
    
    // #tomcat版本>=8.0.39 下 win10下url路径中带正斜杠的文件路径读取不了
    protected final static String FILE_SEPARATOR = File.separator;
    
    
    // 排序顺序
    public final static byte ORDER_BY_ASC = -1; // 正序
    public final static byte ORDER_BY_DESC = 1; // 逆序
    
    // 获得表中最大的排序序号
    public int getNextSortOrder(String tableName, String whereSql) {
        
        return getNextSortOrder(tableName, "sort_order", whereSql);
    }
    
    public int getNextSortOrder(String tableName, String sortOrder, String whereSql) {
        
        Integer maxSortOrder = commonMapper.getMaxSortOrder(tableName, sortOrder, whereSql);
        return (maxSortOrder == null ? 1 : maxSortOrder + 1);
    }
}
