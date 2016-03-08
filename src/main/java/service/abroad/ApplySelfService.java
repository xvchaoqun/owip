package service.abroad;

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

    public Map<Integer, Integer>  getApprovalResultMap(int id){

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
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

        int firstApproverTypeId = -1; // 初审管理员，伪ID
        int lastApproverTypeId= 0; // 终审管理员，伪ID

        Map<Integer, Integer> approvalResult = new HashMap<>();
        { // 已审批的记录
            ApprovalLogExample example = new ApprovalLogExample();
            example.createCriteria().andApplyIdEqualTo(applySelf.getId());
            List<ApprovalLog> approvalLogs = approvalLogMapper.selectByExample(example);

            for (ApprovalLog approvalLog : approvalLogs) {
                if(approvalLog.getTypeId()==null){
                    if(approvalLog.getOdType()==0){ // 初审
                        approvalResult.put(firstApproverTypeId, approvalLog.getStatus()?1:0);
                    }
                    if(approvalLog.getOdType()==1){ // 终审
                        approvalResult.put(lastApproverTypeId, approvalLog.getStatus()?1:0);
                    }
                } else {
                    approvalResult.put(approvalLog.getTypeId(), approvalLog.getStatus()?1:0);
                }
            }
        }

        Map<Integer, Integer> resultMap = new LinkedHashMap<>();
        resultMap.put(firstApproverTypeId, approvalResult.get(firstApproverTypeId)); // 初审
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (ApproverType approverType : approverTypeMap.values()) {
            if(needApprovalTypeSet.contains(approverType.getId()))
                resultMap.put(approverType.getId(), approvalResult.get(approverType.getId()));
            else
                resultMap.put(approverType.getId(), -1);
        }
        resultMap.put(lastApproverTypeId, approvalResult.get(lastApproverTypeId)); // 终审

        // value: -1不需要审批 0未通过 1通过 null未审批
        return resultMap;
    }
    // 查找用户可以审批的干部（非管理员）
    public Set<Integer> findApprovalCadreIdSet(int userId){

        // 待审批的干部
        Set<Integer> cadreIdSet = new HashSet<>();

        // 待审批的干部所在单位
        List<Integer> unitIds = new ArrayList<>();
        // 如果是本单位正职
        Cadre cadre = cadreService.findByUserId(userId);
        int cadreId = cadre.getId();
        MetaType postType = metaTypeService.findAll().get(cadre.getPostId());
        if(postType.getBoolAttr()) unitIds.add(cadre.getUnitId());

        //分管校领导
        MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
        List<Integer> unitIdList = selectMapper.getLeaderManagerUnitId(cadreId, leaderManagerType.getId());
        unitIds.addAll(unitIdList);

        if(!unitIds.isEmpty()) {
            CadreExample example = new CadreExample();
            example.createCriteria().andUnitIdIn(unitIds);
            List<Cadre> cadreIdList = cadreMapper.selectByExample(example);
            for (Cadre record : cadreIdList) {
                cadreIdSet.add(record.getId());
            }
        }

        // 其他审批人身份 的所在单位 给定一个干部id，查找他需要审批的干部
        List<Integer> approvalCadreIds = selectMapper.getApprovalCadreIds(cadreId);
        cadreIdSet.addAll(approvalCadreIds);

        return cadreIdSet;
    }

    // 判断是否有审批权限（非管理员）
    public boolean canApproval(int userId, int applySelfId, int approvalTypeId){

        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre.getStatus() != SystemConstants.CADRE_STATUS_NOW)
            return false; // 必须是现任干部才有审批权限

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        int targetCadreId = applySelf.getCadreId(); // 待审批的干部
        Cadre targetCadre = cadreService.findAll().get(targetCadreId);

        if(approvalTypeId<=0){
            return SecurityUtils.getSubject().hasRole("cadreAdmin");
        }

        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(approvalTypeId);
        Byte type = approverType.getType();

        if(type==SystemConstants.APPROVER_TYPE_UNIT) { // 本单位正职审批
            // 待审批的干部所在单位
            Set<Integer> unitIds = new HashSet<>();
            // 如果是本单位正职
            MetaType postType = metaTypeService.findAll().get(cadre.getPostId());
            if (postType.getBoolAttr()) unitIds.add(cadre.getUnitId());

            return unitIds.contains(targetCadre.getUnitId());
        }

        if(type==SystemConstants.APPROVER_TYPE_LEADER) {  // 校领导审批

            //分管校领导
            MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
            List<Integer> unitIdList = selectMapper.getLeaderManagerUnitId(cadre.getId(), leaderManagerType.getId());
            Set<Integer> unitIds = new HashSet<>();
            unitIds.addAll(unitIdList);

            return unitIds.contains(targetCadre.getUnitId());
        }
        if(type==SystemConstants.APPROVER_TYPE_OTHER) {
            Set<Integer> cadreIdSet = new HashSet<>();
            // 其他审批人身份 的所在单位 给定一个干部id，查找他需要审批的干部
            List<Integer> approvalCadreIds = selectMapper.getApprovalCadreIds_approverTypeId(cadre.getId(), approvalTypeId);
            cadreIdSet.addAll(approvalCadreIds);

            return cadreIdSet.contains(targetCadreId);
        }

        return false;
    }

    public List<ApplySelfFile> getFiles(int applyId){

        ApplySelfFileExample example = new ApplySelfFileExample();
        example.createCriteria().andApplyIdEqualTo(applyId);
        return applySelfFileMapper.selectByExample(example);
    }
    @Transactional
    public int insertSelective(ApplySelf record){

        return applySelfMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        applySelfMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApplySelfExample example = new ApplySelfExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applySelfMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApplySelf record){
        return applySelfMapper.updateByPrimaryKeySelective(record);
    }
}
