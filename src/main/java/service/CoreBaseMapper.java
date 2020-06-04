package service;

import ext.persistence.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.base.*;
import persistence.base.common.IBaseMapper;
import persistence.cadre.*;
import persistence.cadre.common.CadreSearchBean;
import persistence.cadre.common.ICadreMapper;
import persistence.cadreInspect.CadreInspectMapper;
import persistence.cadreInspect.CadreInspectViewMapper;
import persistence.cadreReserve.CadreReserveMapper;
import persistence.cadreReserve.CadreReserveOriginMapper;
import persistence.cadreReserve.CadreReserveViewMapper;
import persistence.common.CommonMapper;
import persistence.common.IPropertyMapper;
import persistence.dispatch.*;
import persistence.dispatch.common.IDispatchMapper;
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
import sys.constants.CadreConstants;

import java.io.File;
import java.util.List;

public class CoreBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    protected HistoryUnitMapper historyUnitMapper;
    @Autowired(required = false)
    protected UnitMapper unitMapper;
    @Autowired(required = false)
    protected UnitViewMapper unitViewMapper;
    @Autowired(required = false)
    protected UnitFunctionMapper unitFunctionMapper;
    @Autowired(required = false)
    protected UnitPostMapper unitPostMapper;
    @Autowired(required = false)
    protected UnitPostGroupMapper unitPostGroupMapper;
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
    protected OrgAdminViewMapper orgAdminViewMapper;
    
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
    protected CadrePunishMapper cadrePunishMapper;
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
    protected CadreReportMapper cadreReportMapper;
    @Autowired(required = false)
    protected CadrePositionReportMapper cadrePositionReportMapper;
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
    protected CadrePartyViewMapper cadrePartyViewMapper;
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
    protected MetaTypeViewMapper metaTypeViewMapper;
    @Autowired(required = false)
    protected LayerTypeMapper layerTypeMapper;
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

    // 调整错误的sort_order
    public void adjustSortOrder(String tableName, String whereSql){

        adjustSortOrder(tableName, "id", whereSql);
    }

    public void adjustSortOrder(String tableName, String idName, String whereSql){

        List<Integer> ids = commonMapper.getWrongSortOrderRecordIds(tableName, idName, whereSql);
        if(ids.size()>0) {

            logger.error("adjustSortOrder, {}, {}, {}, {}", tableName, idName, whereSql, StringUtils.join(ids, ","));
            for (Integer id : ids) {

                int nextSortOrder = getNextSortOrder(tableName, whereSql);
                commonMapper.excuteSql("update " + tableName + " set sort_order = " + nextSortOrder + " where " + idName + "=" + id);
            }
        }
    }

    public void changeOrder(String tableName, String whereSql, byte orderBy,
                            Integer id, int addNum) {

        changeOrder(tableName, "id", whereSql, orderBy, id, addNum);
    }

    /**
     * 表排序，要求表的主键为int类型，排序字段名为sort_order，且为int类型
     *
     * @param tableName 表名
     * @param idName  主键字段名
     * @param whereSql 排序范围查询条件
     * @param orderBy 正序/逆序
     * @param id 主键值
     * @param addNum 调整步长，id=null 或 addNum=0时，仅进行校正sort_order操作
     */
    public void changeOrder(String tableName, String idName, String whereSql, byte orderBy,
                            Integer id, int addNum){

        // 校正sort_order
        adjustSortOrder(tableName, idName, whereSql);

        if(addNum==0 || id==null) return;

        Integer baseSortOrder = commonMapper.getSortOrder(tableName, idName, id);
        if (baseSortOrder == null) return;

        String targetSql = StringUtils.trimToEmpty(whereSql) + (StringUtils.isBlank(whereSql)?"":" and ");
        if (addNum * orderBy > 0) {
            int count = commonMapper.count(tableName, targetSql + "sort_order > " + baseSortOrder);
            addNum = Math.min(Math.abs(addNum), count)*(addNum>0?1:-1);
            targetSql += "sort_order > " + baseSortOrder + " order by sort_order asc limit " + (Math.abs(addNum) - 1) + ",1";
        } else {
            int count = commonMapper.count(tableName, targetSql + "sort_order < " + baseSortOrder);
            addNum = Math.min(Math.abs(addNum), count)*(addNum>0?1:-1);
            targetSql += "sort_order < " + baseSortOrder + " order by sort_order desc limit " + (Math.abs(addNum) - 1) + ",1";
        }

        if(Math.abs(addNum)==0) return;

        Integer targetSortOrder = commonMapper.getTargetSortOrder(tableName, targetSql);
        if (targetSortOrder != null) {

            if (addNum * orderBy > 0) {
                commonMapper.downOrder(tableName, whereSql, baseSortOrder, targetSortOrder);
            } else {
                commonMapper.upOrder(tableName, whereSql, baseSortOrder, targetSortOrder);
            }

            commonMapper.excuteSql("update " + tableName + " set sort_order=" + targetSortOrder + " where "+ idName +"=" + id);
        }
    }

    public String getMainAdminLevelCode(CadreSearchBean searchBean){
        return (searchBean.cadreType== CadreConstants.CADRE_TYPE_CJ)?"mt_admin_level_main":"mt_admin_level_main_kj";
    }
    public String getViceAdminLevelCode(CadreSearchBean searchBean){
        return (searchBean.cadreType== CadreConstants.CADRE_TYPE_CJ)?"mt_admin_level_vice":"mt_admin_level_vice_kj";
    }
}
