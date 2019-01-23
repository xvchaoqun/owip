package service.abroad;

import domain.abroad.AbroadAdditionalPost;
import domain.abroad.ApplicatCadre;
import domain.abroad.ApplicatCadreExample;
import domain.abroad.ApplySelf;
import domain.abroad.ApplySelfExample;
import domain.abroad.ApplySelfFile;
import domain.abroad.ApplySelfFileExample;
import domain.abroad.ApplySelfModify;
import domain.abroad.ApplySelfModifyExample;
import domain.abroad.ApprovalLog;
import domain.abroad.ApprovalLogExample;
import domain.abroad.ApprovalOrder;
import domain.abroad.ApprovalOrderExample;
import domain.abroad.Approver;
import domain.abroad.ApproverBlackList;
import domain.abroad.ApproverType;
import domain.abroad.Passport;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawExample;
import domain.base.ContentTpl;
import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreLeader;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.abroad.common.ApplySelfSearchBean;
import persistence.abroad.common.ApprovalResult;
import persistence.abroad.common.ApprovalTdBean;
import persistence.abroad.common.ApproverTypeBean;
import service.BaseMapper;
import service.SpringProps;
import service.base.ContentTplService;
import service.base.MetaTypeService;
import service.base.ShortMsgService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.sys.SysApprovalLogService;
import service.sys.UserBeanService;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.CadreConstants;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.ContentUtils;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ApplySelfService extends AbroadBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreCommonService cadreCommonService;
    @Autowired
    private AbroadAdditionalPostService abroadAdditionalPostService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    protected ApproverService approverService;
    @Autowired
    protected ApproverBlackListService approverBlackListService;
    @Autowired
    protected ApproverTypeService approverTypeService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected ContentTplService contentTplService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;

    // 查找审批人
    public List<SysUserView> findApprovers(int cadreId/*被审批干部*/, int approverTypeId) {

        // 审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）
        if (approverTypeId <= 0) { // 查找干部管理员
            /*List<SysUserView> cadreAdmin = sysUserService.findByRole(RoleConstants.ROLE_CADREADMIN);
            return cadreAdmin;*/

            if(approverTypeId==-1) { // 组织部初审
                try {
                    ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_APPLYSELF_SUBMIT_INFO);
                    return contentTplService.getShorMsgReceivers(tpl.getId());
                }catch (Exception ex){
                    logger.error("初审审批人读取异常", ex);
                }
            }else if(approverTypeId==0){ // 组织部终审
                try {
                    ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_APPLYSELF_PASS_INFO);
                    return contentTplService.getShorMsgReceivers(tpl.getId());
                }catch (Exception ex){
                    logger.error("终审审批人读取异常", ex);
                }
            }

            return new ArrayList<SysUserView>();
        } else {

            Map<String, ApproverBlackList> approverBlackListMap = approverBlackListService.findAll(approverTypeId);

            CadreView cadre = CmTag.getCadreById(cadreId);
            ApproverType approverType = approverTypeService.findAll().get(approverTypeId);
            if (approverType.getType() == AbroadConstants.ABROAD_APPROVER_TYPE_UNIT_PRINCIPAL) { // 查找本单位正职
                List<SysUserView> _users = new ArrayList<SysUserView>();
                List<Cadre> mainPostList = cadreCommonService.findMainPost(cadre.getUnitId());
                for (Cadre _cadre : mainPostList) {
                    if ((_cadre.getStatus()== CadreConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus()== CadreConstants.CADRE_STATUS_LEADER)
                            && (approverBlackListMap.get(_cadre.getId()+"_" + _cadre.getUnitId())==null))  // 排除本单位正职黑名单（不包括兼审单位正职）
                        _users.add(_cadre.getUser());
                }

                List<CadreView> additionalPrincipals = abroadAdditionalPostService.findAdditionalPrincipals(cadre.getUnitId());
                for (CadreView _cadre : additionalPrincipals) {
                    if ((_cadre.getStatus()== CadreConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus()== CadreConstants.CADRE_STATUS_LEADER)
                            && (approverBlackListMap.get(_cadre.getId()+"_" + cadre.getUnitId())==null)){
                        _users.add(_cadre.getUser());
                    }
                }

                return _users;
            }else if (approverType.getType() == AbroadConstants.ABROAD_APPROVER_TYPE_UNIT) { // 查找本单位人员（包含兼审单位）

                List<SysUserView> _users = new ArrayList<SysUserView>();
                List<Approver> approvers = iAbroadMapper.findApprovarByTypeAndUnit(approverTypeId, cadre.getUnitId());
                for (Approver approver : approvers) {
                    CadreView _cadre = approver.getCadre();
                    if (_cadre.getStatus()== CadreConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus()== CadreConstants.CADRE_STATUS_LEADER)
                        _users.add(approver.getUser());
                }

                return _users;
            } else if (approverType.getType() == AbroadConstants.ABROAD_APPROVER_TYPE_LEADER) { // 查找分管校领导

                List<SysUserView> users = new ArrayList<SysUserView>();

                String auth = approverType.getAuth();
                if(ContentUtils.contains(auth, "1")) { // 分管部门
                    MetaType leaderType = CmTag.getMetaTypeByCode("mt_leader_manager");
                    List<CadreLeader> managerUnitLeaders = iCadreMapper.getManagerUnitLeaders(cadre.getUnitId(), leaderType.getId());
                    for (CadreLeader managerUnitLeader : managerUnitLeaders) {
                        CadreView _cadre = managerUnitLeader.getCadre();
                        if ((_cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                                || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                                && approverBlackListMap.get(_cadre.getId()) == null)  // 排除黑名单
                            users.add(managerUnitLeader.getUser());
                    }
                }
                if(ContentUtils.contains(auth, "2")) { // 联系学院
                    MetaType leaderType = CmTag.getMetaTypeByCode("mt_leader_contact");
                    List<CadreLeader> managerUnitLeaders = iCadreMapper.getManagerUnitLeaders(cadre.getUnitId(), leaderType.getId());
                    for (CadreLeader managerUnitLeader : managerUnitLeaders) {
                        CadreView _cadre = managerUnitLeader.getCadre();
                        if ((_cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                                || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                                && approverBlackListMap.get(_cadre.getId()) == null)  // 排除黑名单
                            users.add(managerUnitLeader.getUser());
                    }
                }

                return users;
            } else { // 查找其他身份下的审批人
                List<SysUserView> users = new ArrayList<SysUserView>();
                List<Approver> approvers = approverService.findByType(approverTypeId);
                for (Approver approver : approvers) {
                    CadreView _cadre = approver.getCadre();
                    if (_cadre.getStatus()== CadreConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus()== CadreConstants.CADRE_STATUS_LEADER)
                        users.add(approver.getUser());
                }
                return users;
            }
        }
    }

    // 查找下一步的审批人员
    public List<SysUserView> findNextApprovers(int applySelfId) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        // 下一个审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）
        Integer flowNode = applySelf.getFlowNode();

        return findApprovers(applySelf.getCadreId(), flowNode);
    }

    // 干部管理员 审批列表
    public Map findApplySelfList(HttpServletResponse response, Integer cadreId,
                                 DateRange _applyDate,
                                 // 出行时间范围
                                 Byte type, Boolean isModify,
                                 // 1：已完成审批(同意申请) 2 已完成审批(不同意申请) 或0：未完成审批 -1: 已删除的记录
                                 int status,
                                 String sort, String order, Integer pageNo, Integer pageSize, int export) {
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfExample example = new ApplySelfExample();
        ApplySelfExample.Criteria criteria = example.createCriteria();
        if (sort != null && order != null)
            example.setOrderByClause(String.format("%s %s", sort, order));

        criteria.andIsDeletedEqualTo(status==-1);
        criteria.andIsFinishEqualTo(status > 0);
        criteria.andIsAgreedEqualTo(status == 1);

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if(_applyDate!=null) {
            if (_applyDate.getStart() != null) {
                criteria.andApplyDateGreaterThanOrEqualTo(_applyDate.getStart());
            }

            if (_applyDate.getEnd() != null) {
                criteria.andApplyDateLessThanOrEqualTo(_applyDate.getEnd());
            }
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (isModify != null) {
            criteria.andIsModifyEqualTo(isModify);
        }

        if (export == 1) {
            applySelf_export(example, response);
            return null;
        }

        long count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map map = new HashMap();
        map.put("applySelfs", applySelfs);
        map.put("commonList", new CommonList(count, pageNo, pageSize));
        return map;
    }

    // 干部（非管理员） 审批人员列表
    public Map findApplySelfList(int userId/*审批人*/, Integer cadreId/*被审批干部*/,
                                 DateRange _applyDate,
                                 // 出行时间范围
                                 Byte type, int status,
                                 // pageNo<0时，只统计数量，不读取分页数据
                                 Integer pageNo, Integer pageSize) {

        if (null == pageSize) {
            pageSize = springProps.mPageSize;
        }
        if (null == pageNo) pageNo = 1;
        int count = 0;
        List<ApplySelf> applySelfs = null;

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null && (cadre.getStatus()== CadreConstants.CADRE_STATUS_MIDDLE
                || cadre.getStatus()== CadreConstants.CADRE_STATUS_LEADER)) { // 审批人必须是现任干部才有审批权限

            //==============================================
            Map<Integer, List<Integer>> approverTypeCadreIdListMap = new HashMap<Integer, List<Integer>>();

            ApproverTypeBean approverTypeBean = getApproverTypeBean(userId);
            /*ShiroUser shiroUser = ShiroHelper.getShiroUser();
            if(shiroUser!=null) {
                approverTypeBean = shiroUser.getApproverTypeBean();
            }else {
                // 用于 api 接口调用
                approverTypeBean = getApproverTypeBean(userId);
            }*/

            if (approverTypeBean != null) {
                approverTypeCadreIdListMap = approverTypeBean.getApproverTypeCadreIdListMap();
            }

            if (approverTypeCadreIdListMap != null && approverTypeCadreIdListMap.size() == 0)
                approverTypeCadreIdListMap = null;
            //==============================================

            ApplySelfSearchBean searchBean = new ApplySelfSearchBean(cadreId, type,
                    _applyDate==null?null:_applyDate.getStart(), _applyDate==null?null:_applyDate.getEnd());

            if (status == 0)
                count = iAbroadMapper.countNotApproval(searchBean, approverTypeCadreIdListMap);
            if (status == 1)
                count = iAbroadMapper.countHasApproval(searchBean, approverTypeCadreIdListMap, userId);

            if(pageNo>0){
                pageNo = Math.max(1, pageNo);
                if ((pageNo - 1) * pageSize >= count) {
                    pageNo = Math.max(1, pageNo - 1);
                }
                if (status == 0)
                    applySelfs = iAbroadMapper.selectNotApprovalList(searchBean, approverTypeCadreIdListMap,
                            new RowBounds((pageNo - 1) * pageSize, pageSize));
                if (status == 1)
                    applySelfs = iAbroadMapper.selectHasApprovalList(searchBean, approverTypeCadreIdListMap, userId,
                            new RowBounds((pageNo - 1) * pageSize, pageSize));
            }
        }
        Map map = new HashMap();
        map.put("applySelfs", applySelfs);
        map.put("commonList", new CommonList(count, pageNo, pageSize));
        return map;
    }

    /**
     * type:
     * 1: <td>-</td>
     * 2: <td class='not_approval'></td>
     * 3: <td>未审批</td>
     * 4: <td><button/></td>
     * canApproval?"":"disabled";
     * canApproval?"btn-success":"btn-default";
     * String btnTd = "<td><button %s class=\"approvalBtn btn %s btn-mini  btn-xs\"\n" +
     * "        data-id=\"%s\" data-approvaltypeid=\"%s\">\n" +
     * "        <i class=\"fa fa-edit\"></i> 审批\n" +
     * "        </button></td>";
     * String _btnTd = "<td><button %s class=\"openView btn %s btn-mini  btn-xs\"\n" +
     * "        data-url=\"%s/applySelf_view?type=aproval&id=%s&approvalTypeId=%s\">\n" +
     * "        <i class=\"fa fa-edit\"></i> 审批\n" +
     * "        </button></td>";
     * 5: <td>未通过</td>
     * 6: <td>通过</td>
     *
     * @return
     */
    public Map getApprovalTdBeanMap(int applySelfId) {

        HttpServletRequest request = ContextHelper.getRequest();
        Boolean isView = (Boolean) request.getAttribute("isView");
        if (isView == null) return null; // 如果不需要查看列表审批权限，则不处理;
        // isView= false 只是查看状态； isView=true 返回状态及判断是否有审批权限，用于生成按钮

        Boolean needApproverList = (Boolean) request.getAttribute("needApproverList");
        if (needApproverList == null) needApproverList = false; // 默认不需要读取审批人列表 (读取的话效率太低，访问速度严重下降)

        // <审批人身份id，审批td类型>
        Map<Integer, ApprovalTdBean> resultMap = new LinkedHashMap<>();

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        Map<Integer, ApprovalResult> approvalResultMap = getApprovalResultMap(applySelfId);
        int size = approvalResultMap.size();
        if(size==0){
            // 干部没任何身份（需要在后台设置身份）
            return resultMap;
        }
        ApprovalResult[] vals = approvalResultMap.values().toArray(new ApprovalResult[size]);
        Integer[] keys = approvalResultMap.keySet().toArray(new Integer[size]);

        ApprovalResult firstVal = vals[0];
        {
            // 初审td
            ApprovalTdBean bean = new ApprovalTdBean();
            bean.setApplySelfId(applySelfId);
            bean.setApprovalTypeId(-1);
            if (firstVal.getValue() == null) {
                if (isView) {
                    bean.setTdType(3);
                } else {
                    boolean canApproval = canApproval(currentUserId, applySelfId, -1);
                    bean.setCanApproval(canApproval);
                    bean.setTdType(4);
                }
            } else if (firstVal.getValue() == 0) {
                bean.setTdType(5);
            } else {
                bean.setTdType(6);
            }

            if (needApproverList) {
                // 读取所有审批人
                ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
                List<SysUserView> approvers = findApprovers(applySelf.getCadreId(), bean.getApprovalTypeId());
                bean.setApproverList(approvers);
            }

            resultMap.put(-1, bean); // 初审
        }

        int last = 0;
        boolean lastIsUnPass = false;
        {
            boolean goToNext = true;
            for (int i = 1; i < size - 1; i++) {

                ApprovalResult val = vals[i];
                ApprovalTdBean bean = new ApprovalTdBean();
                bean.setApplySelfId(applySelfId);
                bean.setApprovalTypeId(keys[i]);

                if (val.getValue() != null && val.getValue() == -1) {
                    bean.setTdType(1);
                    last++;
                } else if (firstVal.getValue() == null || firstVal.getValue() == 0 || goToNext == false) {
                    bean.setTdType(2);
                    goToNext = false;
                } else {
                    if (val.getValue() == null) {
                        if (isView) {
                            bean.setTdType(3);
                        } else {
                            boolean canApproval = canApproval(currentUserId, applySelfId, keys[i]);
                            bean.setCanApproval(canApproval);
                            bean.setTdType(4);
                        }
                        goToNext = false;
                    } else if (val.getValue() == 0) {
                        bean.setTdType(5);
                        goToNext = false;
                        lastIsUnPass = true; // 未通过，直接到组织部终审
                        last++;
                    } else if (val.getValue() == 1) {
                        bean.setTdType(6);
                        last++;
                    }
                }

                if (needApproverList && bean.getTdType() != 1) {
                    // 读取所有审批人
                    ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
                    List<SysUserView> approvers = findApprovers(applySelf.getCadreId(), bean.getApprovalTypeId());
                    bean.setApproverList(approvers);
                }

                resultMap.put(keys[i], bean);
            }
        }

        {
            // 终审td
            ApprovalResult lastVal = vals[size - 1];
            ApprovalTdBean bean = new ApprovalTdBean();
            bean.setApplySelfId(applySelfId);
            bean.setApprovalTypeId(0);
            if (last == size - 2 || lastIsUnPass) { // 前面已经审批完成，或者 前面有一个未通过，直接到组织部终审
                if (lastVal.getValue() == null) {
                    if (isView) {
                        bean.setTdType(3);
                    } else {
                        boolean canApproval = canApproval(currentUserId, applySelfId, 0);
                        bean.setCanApproval(canApproval);
                        bean.setTdType(4);
                    }
                }else if(lastVal.getValue() == -1){ // 初审未通过
                    bean.setTdType(1);
                } else if (lastVal.getValue() == 0) {
                    bean.setTdType(5);
                } else if (lastVal.getValue() == 1) {
                    bean.setTdType(6);
                }
            } else {
                bean.setTdType(2);
            }

            if (needApproverList) {
                // 读取所有审批人
                ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
                List<SysUserView> approvers = findApprovers(applySelf.getCadreId(), bean.getApprovalTypeId());
                bean.setApproverList(approvers);
            }

            resultMap.put(0, bean); // 终审
        }

        return resultMap;
    }

    /**
     * 登录时调用一次，后写入ShiroUser????
     *
     * @param userId
     * @return
     */
    public ApproverTypeBean getApproverTypeBean(int userId) {

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre == null || (cadre.getStatus() != CadreConstants.CADRE_STATUS_MIDDLE
                && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER)) return null;

        int cadreId = cadre.getId();

        // 需要审批的所有干部
        Set<Integer> approvalCadreIdSet = new HashSet<>();
        // 审批人所属的审批身份及对应可审批的申请人
        Map<Integer, List<Integer>> approverTypeCadreIdListMap = new HashMap<>();
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (ApproverType approverType : approverTypeMap.values()) {

            int approverTypeId = approverType.getId();
            List<Integer> approvalCadreIds = getApprovalCadreIds(cadreId, approverTypeId);
            if(approvalCadreIds.size()>0) {
                approverTypeCadreIdListMap.put(approverTypeId, approvalCadreIds);
                approvalCadreIdSet.addAll(approvalCadreIds);
            }
        }

        ApproverTypeBean approverTypeBean = new ApproverTypeBean();
        approverTypeBean.setCadre(cadre);
        approverTypeBean.setApprover(!approverTypeCadreIdListMap.isEmpty());
        approverTypeBean.setApproverTypeCadreIdListMap(approverTypeCadreIdListMap);
        approverTypeBean.setApprovalCadreIdSet(approvalCadreIdSet);

        return approverTypeBean;
    }

    /**
     * <审批人身份id，审批结果>
     * 审批人身份id: -1 初审  0 终审  >0 (approvalType.id)
     * 审批结果: ApprovalResult.value -1不需要审批 0未通过 1通过 null未审批
     */
    public Map<Integer, ApprovalResult> getApprovalResultMap(int applyId) {

        Map<Integer, ApprovalResult> approvalResultMap = new LinkedHashMap<>();

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        Integer cadreId = applySelf.getCadreId();
        //CadreView cadre = iCadreMapper.getCadre(cadreId);
        //Integer postId = cadre.getPostId();
        Set<Integer> needApprovalTypeSet = new HashSet<>();

        if (applySelf.getIsFinish()){

            String flowNodes = applySelf.getFlowNodes();
            String[] _approverTypeIds = flowNodes.split(",");
            for (String _approverTypeId : _approverTypeIds) {
                int approverTypeId = Integer.parseInt(_approverTypeId);
                if(approverTypeId>0){
                    needApprovalTypeSet.add(approverTypeId);
                }
            }

        }else {
            Integer applicatTypeId = null;
            {   // 查询申请人身份
                ApplicatCadreExample example = new ApplicatCadreExample();
                example.createCriteria().andCadreIdEqualTo(cadreId);
                List<ApplicatCadre> applicatCadres = applicatCadreMapper.selectByExample(example);
                if (applicatCadres.size() == 0) {
                    CadreView cv = applySelf.getCadre();
                    logger.error("因私审批数据异常，干部没有任何身份: {}, {}", cv.getCode(), cv.getRealname());
                    return approvalResultMap;// 异常情况，不允许申请人没有任何身份
                }
                ApplicatCadre applicatCadre = applicatCadres.get(0);
                applicatTypeId = applicatCadre.getTypeId();
            }
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
                        typeId = AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST;
                        value = approvalLog.getStatus() ? 1 : 0;
                        //approvalResult.put(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST, approvalLog.getStatus() ? 1 : 0);
                    }
                    if (approvalLog.getOdType() == 1) { // 终审
                        typeId = AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST;
                        value = approvalLog.getStatus() ? 1 : 0;
                        //approvalResult.put(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST, approvalLog.getStatus() ? 1 : 0);
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

        Integer firstResult = resultMap.get(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST);
        approvalResultMap.put(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST, new ApprovalResult(firstResult,
                approvalLogMap.get(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST))); // 初审

        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (ApproverType approverType : approverTypeMap.values()) {
            if (needApprovalTypeSet.contains(approverType.getId()))
                approvalResultMap.put(approverType.getId(), new ApprovalResult(resultMap.get(approverType.getId()), approvalLogMap.get(approverType.getId())));
            else
                approvalResultMap.put(approverType.getId(), new ApprovalResult(-1, null));
        }

        if (firstResult==null || firstResult==1)
            approvalResultMap.put(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST, new ApprovalResult(resultMap.get(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST),
                approvalLogMap.get(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST))); // 终审
        else // 初审未通过的情况
            approvalResultMap.put(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST, new ApprovalResult(-1, null));

        // value: -1不需要审批 0未通过 1通过 null未审批
        return approvalResultMap;
    }

    // 假定干部是某种审批身份，查找他可以审批的干部
    public List<Integer> getApprovalCadreIds(int cadreId, int approverTypeId){

        List<Integer> cadreIds = new ArrayList<>();
        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(approverTypeId);
        if(approverType==null) return  cadreIds;
        byte type = approverType.getType();

        if(type==AbroadConstants.ABROAD_APPROVER_TYPE_UNIT_PRINCIPAL){
            // 审批类型为本单位正职

            Map<String, ApproverBlackList> approverBlackListMap = approverBlackListService.findAll(approverTypeId);

            // 1、读取所在的全部单位（该干部在这些单位中是正职）
            List<Integer> unitIds = new ArrayList<>();
            CadreView cv = cadreService.findAll().get(cadreId);
            if(BooleanUtils.isTrue(CmTag.getMetaType(cv.getPostId()).getBoolAttr())){
                if(!approverBlackListMap.containsKey(cadreId + "_" + cv.getUnitId())) // 2、确定拥有该审批身份
                    unitIds.add(cv.getUnitId());
            }
            List<AbroadAdditionalPost> additionalPosts = abroadAdditionalPostService.findCadrePosts(cadreId);
            for (AbroadAdditionalPost additionalPost : additionalPosts) {
                if(BooleanUtils.isTrue(CmTag.getMetaType(additionalPost.getPostId()).getBoolAttr())){
                    if(!approverBlackListMap.containsKey(cadreId + "_" + additionalPost.getUnitId())) // 2、确定拥有该审批身份
                        unitIds.add(additionalPost.getUnitId());
                }
            }
            if(unitIds.size()==0) return cadreIds;

            // 3、找出审批类型对应的审批干部（申请人）
            cadreIds = iAbroadMapper.getApprovalCadreIds_approverTypeIdInUnits(approverTypeId, StringUtils.join(unitIds, ","));

        }else if(type==AbroadConstants.ABROAD_APPROVER_TYPE_UNIT){
            // 审批类型为本单位人员
            cadreIds = iAbroadMapper.getApprovalCadreIds_approverTypeIdOfUnit(cadreId, approverTypeId);

        }else if(type==AbroadConstants.ABROAD_APPROVER_TYPE_LEADER){
            // 审批类型为校领导
            CadreView cv = cadreService.findAll().get(cadreId);
            Map<String, ApproverBlackList> approverBlackListMap = approverBlackListService.findAll(approverTypeId);
            // 1、找出分管的单位（确定是校领导且找出分管单位）
            List<Integer> unitIds = new ArrayList<>();
            String auth = approverType.getAuth();
            if(ContentUtils.contains(auth, "1")) { // 分管部门
                MetaType leaderType = CmTag.getMetaTypeByCode("mt_leader_manager");
                if(!approverBlackListMap.containsKey(cadreId + "_" + cv.getUnitId())) // 2、确定拥有该审批身份
                    unitIds.addAll(iCadreMapper.getLeaderManagerUnitId(cadreId, leaderType.getId()));
            }
            if(ContentUtils.contains(auth, "2")) { // 联系学院
                MetaType leaderType = CmTag.getMetaTypeByCode("mt_leader_contact");
                if(!approverBlackListMap.containsKey(cadreId + "_" + cv.getUnitId())) // 2、确定拥有该审批身份
                    unitIds.addAll(iCadreMapper.getLeaderManagerUnitId(cadreId, leaderType.getId()));
            }
            if(unitIds.size()==0) return cadreIds;

            // 3、找出审批类型对应的审批干部（申请人）
            cadreIds = iAbroadMapper.getApprovalCadreIds_approverTypeIdInUnits(approverTypeId, StringUtils.join(unitIds, ","));

        }else{
            // 其他
            cadreIds = iAbroadMapper.getApprovalCadreIds_approverTypeId(cadreId, approverTypeId);
        }

        return cadreIds;
    }

    // 查找用户可以审批的干部（非管理员） Set<干部ID>
    public Set<Integer> findApprovalCadreIdSet(int cadreId) {

        // 待审批的干部
        Set<Integer> cadreIdSet = new HashSet<>();
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (ApproverType approverType : approverTypeMap.values()) {
            cadreIdSet.addAll(getApprovalCadreIds(cadreId, approverType.getId()));
        }

        return cadreIdSet;
    }

    // 判断是否有审批权限（非管理员）
    public boolean canApproval(int userId, int applySelfId, int approvalTypeId) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        int targetCadreId = applySelf.getCadreId(); // 待审批的干部

        List<SysUserView> approvers = findApprovers(targetCadreId, approvalTypeId);
        for (SysUserView approver : approvers) {

            if(approver.getId().intValue() == userId) return true;
        }

        return false;
    }

    // 清除审批记录
    @Transactional
    public void clearApproval(int applySelfId){

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);

        ApprovalLogExample example = new ApprovalLogExample();
        example.createCriteria().andApplyIdEqualTo(applySelfId);
        approvalLogMapper.deleteByExample(example);

        commonMapper.excuteSql("update abroad_apply_self set status=1, is_finish=0, flow_node=-1, " +
                "flow_nodes=null where id=" + applySelfId);

        sysApprovalLogService.add(applySelfId, applySelf.getUser().getId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_APPLYSELF,
                "清除审批记录", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    public List<ApplySelfFile> getFiles(int applyId) {

        ApplySelfFileExample example = new ApplySelfFileExample();
        example.createCriteria().andApplyIdEqualTo(applyId);
        return applySelfFileMapper.selectByExample(example);
    }

    public ApplySelf get(int id) {

        return applySelfMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insertSelective(ApplySelf record) {

        record.setIsDeleted(false);
        record.setIsFinish(false);
        record.setIsAgreed(false);
        record.setIsModify(false);
        return applySelfMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        applySelfMapper.deleteByPrimaryKey(id);
    }

    // 逻辑删除（只能删除还未通过的记录）
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ApplySelfExample example = new ApplySelfExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsAgreedEqualTo(false);

        ApplySelf record = new ApplySelf();
        record.setIsDeleted(true);
        applySelfMapper.updateByExampleSelective(record, example);
    }
    @Transactional
    public void batchUnDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ApplySelfExample example = new ApplySelfExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        ApplySelf record = new ApplySelf();
        record.setIsDeleted(false);
        applySelfMapper.updateByExampleSelective(record, example);
    }

    // 真删除（只能删除已经被逻辑删除的记录）
    @Transactional
    public void doBatchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        {
            // 由于是级联删除，如果被关联了领取证件记录，且该记录关联的证件已经是“借出”状态，则需要重置该证件的状态为未借出
            PassportDrawExample example = new PassportDrawExample();
            example.createCriteria().andApplyIdIn(Arrays.asList(ids));
            List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example); // 即将被级联删除
            for (PassportDraw passportDraw : passportDraws) {
                Passport passport = passportDraw.getPassport();

                Passport _record = new Passport();
                _record.setId(passport.getId());
                _record.setIsLent(false);
                passportMapper.updateByPrimaryKeySelective(_record);
            }
        }

        {
            ApplySelfExample example = new ApplySelfExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andIsDeletedEqualTo(true);

            applySelfMapper.deleteByExample(example);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApplySelf record) {
        return applySelfMapper.updateByPrimaryKeySelective(record);
    }

    // 未标记删除的记录，才可以进行审批操作
    @Transactional
    public int doApproval(ApplySelf record) {

        ApplySelfExample example = new ApplySelfExample();
        example.createCriteria().andIdEqualTo(record.getId()).andIsDeletedEqualTo(false);

        return  applySelfMapper.updateByExampleSelective(record, example);
    }

    // 变更行程
    @Transactional
    public void modify(ApplySelf record, String modifyProof, String modifyProofFileName, String modifyRemark) {

        // 第一次修改，需要保留原纪录
        {
            ApplySelfModifyExample example = new ApplySelfModifyExample();
            example.createCriteria().andApplyIdEqualTo(record.getId());
            if (applySelfModifyMapper.countByExample(example) == 0) {
                addModify(AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_ORIGINAL, record.getId(), null, null, "提交的记录");
            }
        }
        record.setIsModify(true);
        applySelfMapper.updateByPrimaryKeySelective(record);

        addModify(AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY, record.getId(), modifyProof, modifyProofFileName, modifyRemark);
    }

    private void addModify(byte modifyType, int applyId, String modifyProof, String modifyProofFileName, String modifyRemark) {
        // 获取修改后的信息
        ApplySelf applySelf = get(applyId);
        ApplySelfModify modify = new ApplySelfModify();
        try {
            BeanUtils.copyProperties(modify, applySelf);
        } catch (IllegalAccessException e) {
            logger.error("异常", e);
        } catch (InvocationTargetException e) {
            logger.error("异常", e);
        }

        modify.setId(null);
        modify.setModifyType(modifyType);
        modify.setApplyId(applyId);
        modify.setModifyProof(modifyProof);
        modify.setModifyProofFileName(modifyProofFileName);
        modify.setRemark(modifyRemark);
        modify.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));
        modify.setModifyUserId(ShiroHelper.getCurrentUserId());
        modify.setCreateTime(new Date());

        // 第一条记录标记为本人提交
        if (modifyType == AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_ORIGINAL) {
            modify.setModifyUserId(applySelf.getUser().getId());
            modify.setIp(applySelf.getIp());
            modify.setCreateTime(applySelf.getCreateTime());
        }

        applySelfModifyMapper.insertSelective(modify);
    }

    // 本年度的申请记录（审批通过的申请）
    public  List<ApplySelf> getAnnualApplyList(int cadreId, int year){

        ApplySelfExample example = new ApplySelfExample();
        ApplySelfExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        criteria.andIsAgreedEqualTo(true);
        criteria.andApplyDateBetween(DateUtils.parseDate(year + "-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(year + "-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");

        return applySelfMapper.selectByExample(example);
    }

    public void applySelf_export(ApplySelfExample example, HttpServletResponse response) {

        List<ApplySelf> records = applySelfMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号|100", "申请日期|100", "工作证号|100", "姓名|50",
                "所在单位及职务|300|left", "出行时间|100", "回国时间|100", /*"出行时间范围",*/
                "出行天数|80", "前往国家或地区|200|left", "因私出国（境）事由|200|left",
                "同行人员|100", "费用来源|100", "所需证件|150", "申请时间|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ApplySelf record = records.get(i);
            SysUserView uv = record.getUser();
            CadreView cadre = record.getCadre();

            List<String> passportList = new ArrayList<>();
            String needPassports = record.getNeedPassports();
            if (needPassports != null) {
                String[] passportIds = needPassports.split(",");
                for (String passportIdStr : passportIds) {
                    int passportId = Integer.parseInt(passportIdStr);
                    String name = metaTypeService.getName(passportId);
                    passportList.add(name);
                }
            }

            String[] values = {
                    "S" + record.getId(),
                    DateUtils.formatDate(record.getApplyDate(), DateUtils.YYYY_MM_DD),
                    uv.getCode(),
                    uv.getRealname(),
                    cadre.getTitle(),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.getDayCountBetweenDate(record.getStartDate(), record.getEndDate()) + "",
                    /*AbroadConstants.ABROAD_APPLY_SELF_DATE_TYPE_MAP.get(record.getType()),*/
                    record.getToCountry(),
                    record.getReason() == null ? "" : record.getReason().replaceAll("\\+\\+\\+", ","),
                    record.getPeerStaff() == null ? "" : record.getPeerStaff().replaceAll("\\+\\+\\+", ","),
                    record.getCostSource(),
                    StringUtils.join(passportList, ","),
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "因私出国申请_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
