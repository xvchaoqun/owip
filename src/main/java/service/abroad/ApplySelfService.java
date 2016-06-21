package service.abroad;

import bean.ApplySelfSearchBean;
import bean.ApprovalResult;
import bean.ApprovalTdBean;
import bean.ApproverTypeBean;
import domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.cadre.CadreService;
import service.helper.ContextHelper;
import service.helper.ExportHelper;
import service.sys.MetaTypeService;
import service.sys.SysUserService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class ApplySelfService extends BaseMapper {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    protected ApproverService approverService;
    @Autowired
    protected ApproverTypeService approverTypeService;
    @Autowired
    protected SpringProps springProps;

    // 查找审批人
    public List<SysUser> findApprovers(int cadreId/*被审批干部*/, int approvalTypeId) {

        // 审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）
        if (approvalTypeId <= 0) { // 查找干部管理员
            List<SysUser> cadreAdmin = sysUserService.findByRole("cadreAdmin");
            return cadreAdmin;
        } else {
            Map<Integer, Cadre> cadreMap = cadreService.findAll();
            Cadre cadre = cadreMap.get(cadreId);
            ApproverType approverType = approverTypeService.findAll().get(approvalTypeId);
            if (approverType.getType() == SystemConstants.APPROVER_TYPE_UNIT) { // 查找本单位正职
                List<SysUser> _users = new ArrayList<>();
                List<Cadre> mainPostList = cadreService.findMainPost(cadre.getUnitId());
                for (Cadre _cadre : mainPostList) {
                    if(_cadre.getStatus() == SystemConstants.CADRE_STATUS_NOW)
                        _users.add(_cadre.getUser());
                }
                return _users;
            } else if (approverType.getType() == SystemConstants.APPROVER_TYPE_LEADER) { // 查找分管校领导

                List<SysUser> users = new ArrayList<>();
                MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
                List<Leader> managerUnitLeaders = selectMapper.getManagerUnitLeaders(cadre.getUnitId(), leaderManagerType.getId());
                for (Leader managerUnitLeader : managerUnitLeaders) {
                    Cadre _cadre = managerUnitLeader.getCadre();
                    if(_cadre.getStatus() == SystemConstants.CADRE_STATUS_NOW)
                        users.add(managerUnitLeader.getUser());
                }
                return users;
            } else { // 查找其他身份下的审批人
                List<SysUser> users = new ArrayList<>();
                List<Approver> approvers = approverService.findByType(approvalTypeId);
                for (Approver approver : approvers) {
                    Cadre _cadre = approver.getCadre();
                    if(_cadre.getStatus() == SystemConstants.CADRE_STATUS_NOW)
                        users.add(approver.getUser());
                }
                return users;
            }
        }
    }

    // 查找下一步的审批人员
    public List<SysUser> findNextApprovers(int applySelfId) {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        // 下一个审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）
        Integer flowNode = applySelf.getFlowNode();

        return findApprovers(applySelf.getCadreId(), flowNode);
    }

    // 干部管理员 审批列表
    public Map findApplySelfList(HttpServletResponse response, Integer cadreId,
                                 String _applyDate,
                                 // 出行时间范围
                                 Byte type, int status, String sort, String order, Integer pageNo, Integer pageSize, int export) {
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

        criteria.andIsFinishEqualTo(status != 0);
        criteria.andIsAgreedEqualTo(status == 1);

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (StringUtils.isNotBlank(_applyDate)) {
            String applyDateStart = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String applyDateEnd = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(applyDateStart)) {
                criteria.andApplyDateGreaterThanOrEqualTo(DateUtils.parseDate(applyDateStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(applyDateEnd)) {
                criteria.andApplyDateLessThanOrEqualTo(DateUtils.parseDate(applyDateEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            applySelf_export(example, response);
            return null;
        }

        int count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map map = new HashMap();
        map.put("applySelfs", applySelfs);
        map.put("commonList", new CommonList(count, pageNo, pageSize));
        return map;
    }

    // 干部 审批人员列表
    public Map findApplySelfList(int userId/*审批人*/, Integer cadreId/*被审批干部*/,
                                 String _applyDate,
                                 // 出行时间范围
                                 Byte type, int status, Integer pageNo, Integer pageSize) {

        if (null == pageSize) {
            pageSize = springProps.mPageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = 0;
        List<ApplySelf> applySelfs = null;

        Cadre cadre = cadreService.findByUserId(userId);
        if (cadre != null && cadre.getStatus() == SystemConstants.CADRE_STATUS_NOW) { // 审批人必须是现任干部才有审批权限

            //==============================================
            Map<Integer, List<Integer>> approverTypeUnitIdListMap = new HashMap<>();
            Map<Integer, List<Integer>> approverTypePostIdListMap = new HashMap<>();

            ApproverType mainPostApproverType = approverTypeService.getMainPostApproverType();
            ApproverType leaderApproverType = approverTypeService.getLeaderApproverType();

            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();

            if(approverTypeBean!=null) {
                if (approverTypeBean.getMainPostUnitId() != null) {
                    List unitIds = new ArrayList();
                    unitIds.add(approverTypeBean.getMainPostUnitId());
                    approverTypeUnitIdListMap.put(mainPostApproverType.getId(), unitIds);
                }
                if (approverTypeBean.getLeaderUnitIds().size() > 0) {
                    approverTypeUnitIdListMap.put(leaderApproverType.getId(), approverTypeBean.getLeaderUnitIds());
                }

                approverTypePostIdListMap = approverTypeBean.getApproverTypePostIdListMap();
            }
            if (approverTypeUnitIdListMap!=null && approverTypeUnitIdListMap.size() == 0) approverTypeUnitIdListMap = null;
            if (approverTypePostIdListMap!=null && approverTypePostIdListMap.size() == 0) approverTypePostIdListMap = null;
            //==============================================

            String applyDateStart = null;
            String applyDateEnd = null;
            if (StringUtils.isNotBlank(_applyDate)) {
                applyDateStart = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
                applyDateEnd = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            }
            ApplySelfSearchBean searchBean = new ApplySelfSearchBean(cadreId, type, applyDateStart, applyDateEnd);

            if (status == 0)
                count = selectMapper.countNotApproval(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap);
            if (status == 1)
                count = selectMapper.countHasApproval(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap, userId);

            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }

            if (status == 0)
                applySelfs = selectMapper.selectNotApprovalList(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap,
                        new RowBounds((pageNo - 1) * pageSize, pageSize));
            if (status == 1)
                applySelfs = selectMapper.selectHasApprovalList(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap, userId,
                        new RowBounds((pageNo - 1) * pageSize, pageSize));

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

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Map<Integer, ApprovalResult> approvalResultMap = getApprovalResultMap(applySelfId);
        int size = approvalResultMap.size();
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
                    boolean canApproval = canApproval(shiroUser.getId(), applySelfId, -1);
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
                List<SysUser> approvers = findApprovers(applySelf.getCadreId(), bean.getApprovalTypeId());
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
                            boolean canApproval = canApproval(shiroUser.getId(), applySelfId, keys[i]);
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
                    List<SysUser> approvers = findApprovers(applySelf.getCadreId(), bean.getApprovalTypeId());
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
                        boolean canApproval = canApproval(shiroUser.getId(), applySelfId, 0);
                        bean.setCanApproval(canApproval);
                        bean.setTdType(4);
                    }
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
                List<SysUser> approvers = findApprovers(applySelf.getCadreId(), bean.getApprovalTypeId());
                bean.setApproverList(approvers);
            }

            resultMap.put(0, bean); // 终审
        }

        return resultMap;
        /*if(!view && SecurityUtils.getSubject().hasRole("cadreAdmin")) {
            ApplySelfMapper applySelfMapper = (ApplySelfMapper) wac.getBean("applySelfMapper");
            CadreService cadreService = (CadreService) wac.getBean("cadreService");
            SysUserService sysUserService = (SysUserService) wac.getBean("sysUserService");
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
            Cadre cadre = cadreService.findAll().get(applySelf.getCadreId());
            SysUser sysUser = sysUserService.findById(cadre.getUserId());

            if((firstVal.getValue()!=null && firstVal.getValue()==0)||(lastVal.getValue()!=null)) { //初审未通过，或者终审完成，需要短信提醒
                td += String.format("<td><button data-id=\"%s\" data-userid=\"%s\" data-status=\"%s\" data-name=\"%s\"" +
                        "        class=\"shortMsgBtn btn btn-primary btn-mini btn-xs\">\n" +
                        "        <i class=\"fa fa-info-circle\"></i> 短信提醒\n" +
                        "        </button></td>", applySelfId, sysUser.getId(), (lastVal.getValue()!=null && lastVal.getValue()==1), sysUser.getRealname());
            }else{
                td +="<td></td>";
            }
        }*/
    }

    /**
     * 登录时调用一次，后写入ShiroUser
     *
     * @param userId
     * @return
     */
    public ApproverTypeBean getApproverTypeBean(int userId) {

        Cadre cadre = cadreService.findByUserId(userId);
        if (cadre == null || cadre.getStatus()!= SystemConstants.CADRE_STATUS_NOW) return null;

        // 本单位正职
        Integer mainPostUnitId = getMainPostUnitId(userId);
        // 分管校领导
        List<Integer> leaderUnitIds = getLeaderMangerUnitIds(userId);

        // 其他身份
        Map<Integer, List<Integer>> approverTypePostIdListMap = new HashMap<>(); // 本人所属的审批身份及对应的审批的职务属性
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (ApproverType approverType : approverTypeMap.values()) {
            if (approverType.getType() == SystemConstants.APPROVER_TYPE_OTHER) {
                List<Integer> approvalPostIds = getApprovalPostIds(userId, approverType.getId());
                if (approvalPostIds.size() > 0) {
                    approverTypePostIdListMap.put(approverType.getId(), approvalPostIds);
                }
            }
        }

        ApproverTypeBean approverTypeBean = new ApproverTypeBean();
        approverTypeBean.setCadre(cadre);
        approverTypeBean.setMainPost(mainPostUnitId != null);
        approverTypeBean.setMainPostUnitId(mainPostUnitId);
        approverTypeBean.setManagerLeader(leaderUnitIds.size() > 0);
        approverTypeBean.setLeaderUnitIds(leaderUnitIds);
        approverTypeBean.setApprover(!approverTypePostIdListMap.isEmpty());
        approverTypeBean.setApproverTypePostIdListMap(approverTypePostIdListMap);
        approverTypeBean.setApprovalCadreIdSet(findApprovalCadreIdSet(userId));

        return approverTypeBean;
    }

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
    public Integer getMainPostUnitId(int userId) {

        Cadre cadre = cadreService.findByUserId(userId);
        if (cadre != null && cadre.getStatus()== SystemConstants.CADRE_STATUS_NOW ) { // 必须是现任干部
            MetaType postType = metaTypeService.findAll().get(cadre.getPostId());
            if (postType!=null && postType.getBoolAttr()) {
                return cadre.getUnitId();
            }
            if(postType==null){
                SysUser sysUser = cadre.getUser();
                logger.error(String.format("读取职务属性出错：%s %s postId=%s",
                        sysUser.getUsername(), sysUser.getRealname(), cadre.getPostId()));
            }
        }
        return null;
    }

    // 如果是分管校领导，返回分管单位ID列表
    public List<Integer> getLeaderMangerUnitIds(int userId) {

        List<Integer> unitIds = new ArrayList<>();
        Cadre cadre = cadreService.findByUserId(userId);
        if (cadre != null && cadre.getStatus()== SystemConstants.CADRE_STATUS_NOW ) { // 必须是现任干部
            MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
            unitIds = selectMapper.getLeaderManagerUnitId(cadre.getId(), leaderManagerType.getId());
        }
        return unitIds;
    }

    // 如果是其他审批身份，返回需要审批的职务属性
    public List<Integer> getApprovalPostIds(int userId, int approverTypeId) {

        List<Integer> postIds = new ArrayList<>();
        Cadre cadre = cadreService.findByUserId(userId);
        if (cadre != null) {
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
        Integer mainPostUnitId = getMainPostUnitId(userId);
        if (mainPostUnitId != null) unitIds.add(mainPostUnitId);

        //分管校领导
        unitIds.addAll(getLeaderMangerUnitIds(userId));

        if (!unitIds.isEmpty()) {
            CadreExample example = new CadreExample();
            example.createCriteria().andUnitIdIn(unitIds);
            List<Cadre> cadreIdList = cadreMapper.selectByExample(example);
            for (Cadre record : cadreIdList) {
                cadreIdSet.add(record.getId());
            }
        }

        Cadre cadre = cadreService.findByUserId(userId);
        if (cadre != null) {
            // 其他审批人身份的干部，查找他需要审批的干部
            List<Integer> approvalCadreIds = selectMapper.getApprovalCadreIds(cadre.getId());
            cadreIdSet.addAll(approvalCadreIds);
        }

        return cadreIdSet;
    }

    // 判断是否有审批权限（非管理员）
    public boolean canApproval(int userId, int applySelfId, int approvalTypeId) {

        Cadre cadre = cadreService.findByUserId(userId);
        if (approvalTypeId <= 0) {
            return SecurityUtils.getSubject().hasRole("cadreAdmin");
        } else if (cadre == null || cadre.getStatus() != SystemConstants.CADRE_STATUS_NOW) {
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

    public ApplySelf get(int id) {

        return applySelfMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insertSelective(ApplySelf record) {
        record.setIsFinish(false);
        record.setIsAgreed(false);
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

    public void applySelf_export(ApplySelfExample example, HttpServletResponse response) {

        List<ApplySelf> records = applySelfMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号", "申请日期", "工作证号", "姓名", "所在单位及职务", "出行时间", "回国时间", /*"出行时间范围",*/
                "出行天数", "前往国家或地区", "因私出国（境）事由", "同行人员", "费用来源", "所需证件", "申请时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ApplySelf record = records.get(i);
            SysUser sysUser = record.getUser();
            Cadre cadre = record.getCadre();

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
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    cadre.getTitle(),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.getDayCountBetweenDate(record.getStartDate(), record.getEndDate()) + "",
                    /*SystemConstants.APPLY_SELF_DATE_TYPE_MAP.get(record.getType()),*/
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
