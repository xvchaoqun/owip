package service.abroad;

import bean.ApprovalResult;
import domain.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.cadre.CadreService;
import service.sys.MetaTypeService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class ApplySelfService extends BaseMapper {

    @Autowired
    private CadreService cadreService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    protected ApproverTypeService approverTypeService;

    /**
     * <审批人身份id，审批结果>
     * 审批人身份id: -1 初审  0 终审  >0 (approvalType.id)
     * 审批结果: ApprovalResult.value -1不需要审批 0未通过 1通过 null未审批
     */
    public Map<Integer, ApprovalResult> getApprovalResultMap(int applyId) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        Integer cadreId = applySelf.getCadreId();
        Cadre cadre = cadreService.findAll().get(cadreId);
        Integer postId = cadre.getPostId();

        Integer applicatTypeId = null;
        {   // 查询申请人身份
            ApplicatPostExample example = new ApplicatPostExample();
            example.createCriteria().andPostIdEqualTo(postId);
            List<ApplicatPost> applicatPosts = applicatPostMapper.selectByExample(example);
            ApplicatPost applicatPost = applicatPosts.get(0);
            applicatTypeId = applicatPost.getTypeId();
        }
        Set<Integer> needApprovalTypeSet = new HashSet<>();
        List<ApprovalOrder> approvalOrders = null;
        {  // 所需要的审批人身份列表
            ApprovalOrderExample example = new ApprovalOrderExample();
            example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId);
            example.setOrderByClause("sort_order desc");
            approvalOrders = approvalOrderMapper.selectByExample(example);
            for (ApprovalOrder approvalOrder : approvalOrders) {
                needApprovalTypeSet.add(approvalOrder.getApproverTypeId());
            }
        }

        Map<Integer, Integer> resultMap = new HashMap<>();
        Map<Integer, ApprovalLog> approvalLogMap = new HashMap<>();
        { // 已审批的记录
            ApprovalLogExample example = new ApprovalLogExample();
            example.createCriteria().andApplyIdEqualTo(applySelf.getId());
            List<ApprovalLog> approvalLogs = approvalLogMapper.selectByExample(example);

            for (ApprovalLog approvalLog : approvalLogs) {
                Integer typeId = null;
                Integer value = null;
                if (approvalLog.getTypeId() == null) {
                    if (approvalLog.getOdType() == 0) { // 初审
                        typeId = SystemConstants.APPROVER_TYPE_ID_OD_FIRST;
                        value = approvalLog.getStatus() ? 1 : 0;
                        //approvalResult.put(SystemConstants.APPROVER_TYPE_ID_OD_FIRST, approvalLog.getStatus() ? 1 : 0);
                    }
                    if (approvalLog.getOdType() == 1) { // 终审
                        typeId = SystemConstants.APPROVER_TYPE_ID_OD_LAST;
                        value = approvalLog.getStatus() ? 1 : 0;
                        //approvalResult.put(SystemConstants.APPROVER_TYPE_ID_OD_LAST, approvalLog.getStatus() ? 1 : 0);
                    }
                } else {
                    typeId = approvalLog.getTypeId();
                    value = approvalLog.getStatus() ? 1 : 0;
                    //approvalResult.put(approvalLog.getTypeId(), approvalLog.getStatus() ? 1 : 0);
                }
                resultMap.put(typeId, value);
                approvalLogMap.put(typeId, approvalLog);
            }
        }

        Map<Integer, ApprovalResult> approvalResultMap = new LinkedHashMap<>();
        approvalResultMap.put(SystemConstants.APPROVER_TYPE_ID_OD_FIRST, new ApprovalResult(resultMap.get(SystemConstants.APPROVER_TYPE_ID_OD_FIRST),
                approvalLogMap.get(SystemConstants.APPROVER_TYPE_ID_OD_FIRST))); // 初审

        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (ApproverType approverType : approverTypeMap.values()) {
            if (needApprovalTypeSet.contains(approverType.getId()))
                approvalResultMap.put(approverType.getId(), new ApprovalResult(resultMap.get(approverType.getId()), approvalLogMap.get(approverType.getId())));
            else
                approvalResultMap.put(approverType.getId(), new ApprovalResult(-1, null));
        }

        approvalResultMap.put(SystemConstants.APPROVER_TYPE_ID_OD_LAST, new ApprovalResult(resultMap.get(SystemConstants.APPROVER_TYPE_ID_OD_LAST),
                approvalLogMap.get(SystemConstants.APPROVER_TYPE_ID_OD_LAST))); // 终审

        // value: -1不需要审批 0未通过 1通过 null未审批
        return approvalResultMap;
    }

    // 如果是本单位正职，返回单位ID
    public Integer getMainPostUnitId(int userId){

        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre!=null) {
            MetaType postType = metaTypeService.findAll().get(cadre.getPostId());
            if (postType.getBoolAttr()){
                cadre.getUnitId();
            }
        }
        return null;
    }
    // 如果是分管校领导，返回分管单位ID列表
    public List<Integer> getLeaderUnitIds(int userId){

        List<Integer> unitIds = new ArrayList<>();
        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre!=null) {
            MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
            unitIds = selectMapper.getLeaderManagerUnitId(cadre.getId(), leaderManagerType.getId());
        }
        return unitIds;
    }

    // 如果是其他审批身份，返回需要审批的职务属性
    public List<Integer> getApprovalPostIds(int userId, int approverTypeId){

        List<Integer> postIds = new ArrayList<>();
        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre!=null) {
            postIds = selectMapper.getApprovalPostIds_approverTypeId(cadre.getId(), approverTypeId);
        }
        return postIds;
    }

    // 查找用户可以审批的干部（非管理员） Set<干部ID>
    public Set<Integer> findApprovalCadreIdSet(int userId) {

        // 待审批的干部
        Set<Integer> cadreIdSet = new HashSet<>();

        // 待审批的干部所在单位
        List<Integer> unitIds = new ArrayList<>();
        // 如果是本单位正职
        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre==null) return cadreIdSet; // 不可能出现的情况
        int cadreId = cadre.getId();
        MetaType postType = metaTypeService.findAll().get(cadre.getPostId());
        if (postType.getBoolAttr()) unitIds.add(cadre.getUnitId());

        //分管校领导
        MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
        List<Integer> unitIdList = selectMapper.getLeaderManagerUnitId(cadreId, leaderManagerType.getId());
        unitIds.addAll(unitIdList);

        if (!unitIds.isEmpty()) {
            CadreExample example = new CadreExample();
            example.createCriteria().andUnitIdIn(unitIds);
            List<Cadre> cadreIdList = cadreMapper.selectByExample(example);
            for (Cadre record : cadreIdList) {
                cadreIdSet.add(record.getId());
            }
        }

        // 其他审批人身份的干部，查找他需要审批的干部
        List<Integer> approvalCadreIds = selectMapper.getApprovalCadreIds(cadreId);
        cadreIdSet.addAll(approvalCadreIds);

        return cadreIdSet;
    }

    // 判断是否有审批权限（非管理员）
    public boolean canApproval(int userId, int applySelfId, int approvalTypeId) {

        Cadre cadre = cadreService.findByUserId(userId);
        if (approvalTypeId <= 0) {
            return SecurityUtils.getSubject().hasRole("cadreAdmin");
        }else if (cadre==null || cadre.getStatus() != SystemConstants.CADRE_STATUS_NOW) {
            return false; // 必须是现任干部才有审批权限
        }

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        int targetCadreId = applySelf.getCadreId(); // 待审批的干部
        Cadre targetCadre = cadreService.findAll().get(targetCadreId);

        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(approvalTypeId);
        Byte type = approverType.getType();

        if (type == SystemConstants.APPROVER_TYPE_UNIT) { // 本单位正职审批
            // 待审批的干部所在单位
            Set<Integer> unitIds = new HashSet<>();
            // 如果是本单位正职
            MetaType postType = metaTypeService.findAll().get(cadre.getPostId());
            if (postType.getBoolAttr()) unitIds.add(cadre.getUnitId());

            return unitIds.contains(targetCadre.getUnitId());
        }

        if (type == SystemConstants.APPROVER_TYPE_LEADER) {  // 校领导审批

            //分管校领导
            MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
            List<Integer> unitIdList = selectMapper.getLeaderManagerUnitId(cadre.getId(), leaderManagerType.getId());
            Set<Integer> unitIds = new HashSet<>();
            unitIds.addAll(unitIdList);

            return unitIds.contains(targetCadre.getUnitId());
        }
        if (type == SystemConstants.APPROVER_TYPE_OTHER) {
            Set<Integer> cadreIdSet = new HashSet<>();
            // 其他审批人身份 的所在单位 给定一个干部id，查找他需要审批的干部
            List<Integer> approvalCadreIds = selectMapper.getApprovalCadreIds_approverTypeId(cadre.getId(), approvalTypeId);
            cadreIdSet.addAll(approvalCadreIds);

            return cadreIdSet.contains(targetCadreId);
        }

        return false;
    }

    public List<ApplySelfFile> getFiles(int applyId) {

        ApplySelfFileExample example = new ApplySelfFileExample();
        example.createCriteria().andApplyIdEqualTo(applyId);
        return applySelfFileMapper.selectByExample(example);
    }

    @Transactional
    public int insertSelective(ApplySelf record) {

        return applySelfMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        applySelfMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ApplySelfExample example = new ApplySelfExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applySelfMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApplySelf record) {
        return applySelfMapper.updateByPrimaryKeySelective(record);
    }
}
