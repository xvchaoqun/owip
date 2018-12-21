package service.cla;

import domain.base.ContentTpl;
import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import domain.cadre.CadreLeader;
import domain.cadre.CadreView;
import domain.cla.ClaAdditionalPost;
import domain.cla.ClaAdditionalPostExample;
import domain.cla.ClaApplicatCadre;
import domain.cla.ClaApplicatCadreExample;
import domain.cla.ClaApply;
import domain.cla.ClaApplyExample;
import domain.cla.ClaApplyFile;
import domain.cla.ClaApplyFileExample;
import domain.cla.ClaApplyModify;
import domain.cla.ClaApplyModifyExample;
import domain.cla.ClaApprovalLog;
import domain.cla.ClaApprovalLogExample;
import domain.cla.ClaApprovalOrder;
import domain.cla.ClaApprovalOrderExample;
import domain.cla.ClaApprover;
import domain.cla.ClaApproverBlackList;
import domain.cla.ClaApproverType;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.cla.common.ClaApplySearchBean;
import persistence.cla.common.ClaApprovalResult;
import persistence.cla.common.ClaApprovalTdBean;
import persistence.cla.common.ClaApproverTypeBean;
import service.BaseMapper;
import service.SpringProps;
import service.base.ContentTplService;
import service.base.MetaTypeService;
import service.base.ShortMsgService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.sys.UserBeanService;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.CadreConstants;
import sys.constants.ClaConstants;
import sys.constants.ContentTplConstants;
import sys.spring.DateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
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
public class ClaApplyService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreCommonService cadreCommonService;
    @Autowired
    private ClaAdditionalPostService claAdditionalPostService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    protected ClaApproverService claApproverService;
    @Autowired
    protected ClaApproverBlackListService claApproverBlackListService;
    @Autowired
    protected ClaApproverTypeService claApproverTypeService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected ContentTplService contentTplService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected SpringProps springProps;

    // 查找审批人
    public List<SysUserView> findApprovers(int cadreId/*被审批干部*/, int approvalTypeId) {

        // 审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）
        if (approvalTypeId <= 0) { // 查找干部管理员
            /*List<SysUserView> cadreAdmin = sysUserService.findByRole(RoleConstants.ROLE_CADREADMIN);
            return cadreAdmin;*/
            if (approvalTypeId == -1) { // 组织部初审
                try {
                    ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CLA_APPLY_SUBMIT_INFO);
                    return contentTplService.getShorMsgReceivers(tpl.getId());
                } catch (Exception ex) {
                    logger.error("初审审批人读取异常", ex);
                }
            } else if (approvalTypeId == 0) { // 组织部终审
                try {
                    ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CLA_APPLY_PASS_INFO);
                    return contentTplService.getShorMsgReceivers(tpl.getId());
                } catch (Exception ex) {
                    logger.error("终审审批人读取异常", ex);
                }
            }

            return new ArrayList<SysUserView>();
        } else {

            ClaApproverType mainPostApproverType = claApproverTypeService.getMainPostApproverType();
            ClaApproverType leaderApproverType = claApproverTypeService.getLeaderApproverType();
            Map<Integer, ClaApproverBlackList> mainPostBlackList = claApproverBlackListService.findAll(mainPostApproverType.getId());
            Map<Integer, ClaApproverBlackList> leaderBlackList = claApproverBlackListService.findAll(leaderApproverType.getId());

            CadreView cadre = CmTag.getCadreById(cadreId);
            ClaApproverType approverType = claApproverTypeService.findAll().get(approvalTypeId);
            if (approverType.getType() == ClaConstants.CLA_APPROVER_TYPE_UNIT) { // 查找本单位正职
                List<SysUserView> _users = new ArrayList<SysUserView>();
                List<Cadre> mainPostList = cadreCommonService.findMainPost(cadre.getUnitId());
                for (Cadre _cadre : mainPostList) {
                    if ((_cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                            && (mainPostBlackList.get(_cadre.getId()) == null))  // 排除本单位正职黑名单（不包括兼审单位正职）
                        _users.add(_cadre.getUser());
                }

                List<CadreView> additionalPost = claAdditionalPostService.findAdditionalPost(cadre.getUnitId());
                for (CadreView _cadre : additionalPost) {
                    if (_cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER) {
                        _users.add(_cadre.getUser());
                    }
                }

                return _users;
            } else if (approverType.getType() == ClaConstants.CLA_APPROVER_TYPE_LEADER) { // 查找分管校领导

                List<SysUserView> users = new ArrayList<SysUserView>();
                MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
                List<CadreLeader> managerUnitLeaders = iCadreMapper.getManagerUnitLeaders(cadre.getUnitId(), leaderManagerType.getId());
                for (CadreLeader managerUnitLeader : managerUnitLeaders) {
                    CadreView _cadre = managerUnitLeader.getCadre();
                    if ((_cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                            && leaderBlackList.get(_cadre.getId()) == null)  // 排除黑名单
                        users.add(managerUnitLeader.getUser());
                }
                return users;
            } else { // 查找其他身份下的审批人
                List<SysUserView> users = new ArrayList<SysUserView>();
                List<ClaApprover> approvers = claApproverService.findByType(approvalTypeId);
                for (ClaApprover approver : approvers) {
                    CadreView _cadre = approver.getCadre();
                    if (_cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                        users.add(approver.getUser());
                }
                return users;
            }
        }
    }

    // 查找下一步的审批人员
    public List<SysUserView> findNextApprovers(int applyId) {

        ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
        // 下一个审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）
        Integer flowNode = apply.getFlowNode();

        return findApprovers(apply.getCadreId(), flowNode);
    }

    // 干部管理员 审批列表
    public Map findApplyList(HttpServletResponse response, Integer cadreId,
                             DateRange _applyDate,
                             // 出行时间范围
                             Byte type, Boolean isModify, Boolean isBack,
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

        ClaApplyExample example = new ClaApplyExample();
        ClaApplyExample.Criteria criteria = example.createCriteria();
        if (sort != null && order != null)
            example.setOrderByClause(String.format("%s %s", sort, order));

        criteria.andIsDeletedEqualTo(status == -1);
        criteria.andIsFinishEqualTo(status > 0);
        criteria.andIsAgreedEqualTo(status == 1);

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (_applyDate != null) {
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
        if (isBack != null) {
            criteria.andIsBackEqualTo(isBack);
        }

        if (export == 1) {
            apply_export(example, response);
            return null;
        }

        long count = claApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ClaApply> applys = claApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map map = new HashMap();
        map.put("applys", applys);
        map.put("commonList", new CommonList(count, pageNo, pageSize));
        return map;
    }

    // 干部（非管理员） 审批人员列表
    public Map findApplyList(int userId/*审批人*/, Integer cadreId/*被审批干部*/,
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
        List<ClaApply> applys = null;

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null && (cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)) { // 审批人必须是现任干部才有审批权限

            //==============================================
            Map<Integer, List<Integer>> approverTypeUnitIdListMap = new HashMap<Integer, List<Integer>>();
            Map<Integer, List<Integer>> approverTypeCadreIdListMap = new HashMap<Integer, List<Integer>>();

            ClaApproverType mainPostApproverType = claApproverTypeService.getMainPostApproverType();
            ClaApproverType leaderApproverType = claApproverTypeService.getLeaderApproverType();

            ClaApproverTypeBean approverTypeBean = null;
            ShiroUser shiroUser = ShiroHelper.getShiroUser();
            if (shiroUser != null) {
                approverTypeBean = getApproverTypeBean(ShiroHelper.getCurrentUserId());
            } else {
                // 用于 api 接口调用
                approverTypeBean = getApproverTypeBean(userId);
            }

            if (approverTypeBean != null) {
                if (approverTypeBean.getMainPostUnitIds().size() > 0) {
                    List unitIds = new ArrayList();
                    unitIds.addAll(approverTypeBean.getMainPostUnitIds());
                    approverTypeUnitIdListMap.put(mainPostApproverType.getId(), unitIds);
                }
                if (approverTypeBean.getLeaderUnitIds().size() > 0) {
                    approverTypeUnitIdListMap.put(leaderApproverType.getId(), approverTypeBean.getLeaderUnitIds());
                }

                approverTypeCadreIdListMap = approverTypeBean.getApproverTypeCadreIdListMap();
            }
            if (approverTypeUnitIdListMap != null && approverTypeUnitIdListMap.size() == 0)
                approverTypeUnitIdListMap = null;
            if (approverTypeCadreIdListMap != null && approverTypeCadreIdListMap.size() == 0)
                approverTypeCadreIdListMap = null;
            //==============================================

            ClaApplySearchBean searchBean = new ClaApplySearchBean(cadreId, type,
                    _applyDate == null ? null : _applyDate.getStart(), _applyDate == null ? null : _applyDate.getEnd());

            if (status == 0)
                count = iClaMapper.countNotApproval(searchBean, approverTypeUnitIdListMap, approverTypeCadreIdListMap);
            if (status == 1)
                count = iClaMapper.countHasApproval(searchBean, approverTypeUnitIdListMap, approverTypeCadreIdListMap, userId);

            if (pageNo > 0) {
                pageNo = Math.max(1, pageNo);
                if ((pageNo - 1) * pageSize >= count) {
                    pageNo = Math.max(1, pageNo - 1);
                }
                if (status == 0)
                    applys = iClaMapper.selectNotApprovalList(searchBean, approverTypeUnitIdListMap, approverTypeCadreIdListMap,
                            new RowBounds((pageNo - 1) * pageSize, pageSize));
                if (status == 1)
                    applys = iClaMapper.selectHasApprovalList(searchBean, approverTypeUnitIdListMap, approverTypeCadreIdListMap, userId,
                            new RowBounds((pageNo - 1) * pageSize, pageSize));
            }
        }
        Map map = new HashMap();
        map.put("applys", applys);
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
     * "        data-url=\"%s/apply_view?type=aproval&id=%s&approvalTypeId=%s\">\n" +
     * "        <i class=\"fa fa-edit\"></i> 审批\n" +
     * "        </button></td>";
     * 5: <td>未通过</td>
     * 6: <td>通过</td>
     *
     * @return
     */
    public Map getApprovalTdBeanMap(int applyId) {

        HttpServletRequest request = ContextHelper.getRequest();
        Boolean isView = (Boolean) request.getAttribute("isView");
        if (isView == null) return null; // 如果不需要查看列表审批权限，则不处理;
        // isView= false 只是查看状态； isView=true 返回状态及判断是否有审批权限，用于生成按钮

        Boolean needApproverList = (Boolean) request.getAttribute("needApproverList");
        if (needApproverList == null) needApproverList = false; // 默认不需要读取审批人列表 (读取的话效率太低，访问速度严重下降)

        // <审批人身份id，审批td类型>
        Map<Integer, ClaApprovalTdBean> resultMap = new LinkedHashMap<>();

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        Map<Integer, ClaApprovalResult> approvalResultMap = getApprovalResultMap(applyId);
        int size = approvalResultMap.size();
        if (size == 0) {
            // 干部没任何身份（需要在后台设置身份）
            return resultMap;
        }
        ClaApprovalResult[] vals = approvalResultMap.values().toArray(new ClaApprovalResult[size]);
        Integer[] keys = approvalResultMap.keySet().toArray(new Integer[size]);

        ClaApprovalResult firstVal = vals[0];
        {
            // 初审td
            ClaApprovalTdBean bean = new ClaApprovalTdBean();
            bean.setApplyId(applyId);
            bean.setApprovalTypeId(-1);
            if (firstVal.getValue() == null) {
                if (isView) {
                    bean.setTdType(3);
                } else {
                    boolean canApproval = canApproval(currentUserId, applyId, -1);
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
                ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
                List<SysUserView> approvers = findApprovers(apply.getCadreId(), bean.getApprovalTypeId());
                bean.setApproverList(approvers);
            }

            resultMap.put(-1, bean); // 初审
        }

        int last = 0;
        boolean lastIsUnPass = false;
        {
            boolean goToNext = true;
            for (int i = 1; i < size - 1; i++) {

                ClaApprovalResult val = vals[i];
                ClaApprovalTdBean bean = new ClaApprovalTdBean();
                bean.setApplyId(applyId);
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
                            boolean canApproval = canApproval(currentUserId, applyId, keys[i]);
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
                    ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
                    List<SysUserView> approvers = findApprovers(apply.getCadreId(), bean.getApprovalTypeId());
                    bean.setApproverList(approvers);
                }

                resultMap.put(keys[i], bean);
            }
        }

        {
            // 终审td
            ClaApprovalResult lastVal = vals[size - 1];
            ClaApprovalTdBean bean = new ClaApprovalTdBean();
            bean.setApplyId(applyId);
            bean.setApprovalTypeId(0);
            if (last == size - 2 || lastIsUnPass) { // 前面已经审批完成，或者 前面有一个未通过，直接到组织部终审
                if (lastVal.getValue() == null) {
                    if (isView) {
                        bean.setTdType(3);
                    } else {
                        boolean canApproval = canApproval(currentUserId, applyId, 0);
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
                ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
                List<SysUserView> approvers = findApprovers(apply.getCadreId(), bean.getApprovalTypeId());
                bean.setApproverList(approvers);
            }

            resultMap.put(0, bean); // 终审
        }

        return resultMap;
        /*if(!view && ShiroHelper.hasRole("cadreAdmin")) {
            ClaApplyMapper claApplyMapper = (ClaApplyMapper) wac.getBean("claApplyMapper");
            CadreService cadreService = (CadreService) wac.getBean("cadreService");
            SysUserService sysUserService = (SysUserService) wac.getBean("sysUserService");
            ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(apply.getCadreId());
            SysUser sysUser = sysUserService.findById(cadre.getUserId());

            if((firstVal.getValue()!=null && firstVal.getValue()==0)||(lastVal.getValue()!=null)) { //初审未通过，或者终审完成，需要短信提醒
                td += String.format("<td><button data-id=\"%s\" data-userid=\"%s\" data-status=\"%s\" data-name=\"%s\"" +
                        "        class=\"shortMsgBtn btn btn-primary btn-mini btn-xs\">\n" +
                        "        <i class=\"fa fa-info-circle\"></i> 短信提醒\n" +
                        "        </button></td>", applyId, sysUser.getId(), (lastVal.getValue()!=null && lastVal.getValue()==1), sysUser.getRealname());
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
    public ClaApproverTypeBean getApproverTypeBean(int userId) {

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre == null || (cadre.getStatus() != CadreConstants.CADRE_STATUS_MIDDLE
                && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER)) return null;

        // 本单位正职
        List<Integer> mainPostUnitIds = getMainPostUnitIds(userId);
        // 分管校领导
        List<Integer> leaderUnitIds = getLeaderMangerUnitIds(userId);

        // 其他身份
        Map<Integer, List<Integer>> approverTypeCadreIdListMap = new HashMap<>(); // 本人所属的审批身份及对应的审批的职务属性
        Map<Integer, ClaApproverType> approverTypeMap = claApproverTypeService.findAll();
        for (ClaApproverType approverType : approverTypeMap.values()) {
            Byte type = approverType.getType();
            if (type != ClaConstants.CLA_APPROVER_TYPE_UNIT && type != ClaConstants.CLA_APPROVER_TYPE_LEADER) {
                List<Integer> approvalPostIds = getApprovalPostIds(userId, approverType.getId());
                if (approvalPostIds.size() > 0) {
                    approverTypeCadreIdListMap.put(approverType.getId(), approvalPostIds);
                }
            }
        }

        ClaApproverTypeBean approverTypeBean = new ClaApproverTypeBean();
        approverTypeBean.setCadre(cadre);
        approverTypeBean.setMainPostUnitIds(mainPostUnitIds);
        approverTypeBean.setManagerLeader(leaderUnitIds.size() > 0);
        approverTypeBean.setLeaderUnitIds(leaderUnitIds);
        approverTypeBean.setApprover(!approverTypeCadreIdListMap.isEmpty());
        approverTypeBean.setApproverTypeCadreIdListMap(approverTypeCadreIdListMap);
        approverTypeBean.setApprovalCadreIdSet(findApprovalCadreIdSet(userId));

        return approverTypeBean;
    }

    /**
     * <审批人身份id，审批结果>
     * 审批人身份id: -1 初审  0 终审  >0 (approvalType.id)
     * 审批结果: ApprovalResult.value -1不需要审批 0未通过 1通过 null未审批
     */
    public Map<Integer, ClaApprovalResult> getApprovalResultMap(int applyId) {

        Map<Integer, ClaApprovalResult> approvalResultMap = new LinkedHashMap<>();

        ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
        Integer cadreId = apply.getCadreId();
        //CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        //Integer postId = cadre.getPostId();

        Set<Integer> needApprovalTypeSet = new HashSet<>();

        if(apply.getIsFinish()){

            String flowNodes = apply.getFlowNodes();
            String[] _approverTypeIds = flowNodes.split(",");
            for (String _approverTypeId : _approverTypeIds) {
                int approverTypeId = Integer.valueOf(_approverTypeId);
                if(approverTypeId>0){
                    needApprovalTypeSet.add(approverTypeId);
                }
            }

        }else {
            Integer applicatTypeId = null;
            {   // 查询申请人身份
                ClaApplicatCadreExample example = new ClaApplicatCadreExample();
                example.createCriteria().andCadreIdEqualTo(cadreId);
                List<ClaApplicatCadre> applicatCadres = claApplicatCadreMapper.selectByExample(example);
                if (applicatCadres.size() == 0) {
                    CadreView cv = apply.getCadre();
                    logger.error("请假审批数据异常，干部没有任何身份: {}, {}", cv.getCode(), cv.getRealname());
                    return approvalResultMap;// 异常情况，不允许申请人没有任何身份
                }
                ClaApplicatCadre applicatCadre = applicatCadres.get(0);
                applicatTypeId = applicatCadre.getTypeId();
            }
            List<ClaApprovalOrder> approvalOrders = null;
            {  // 所需要的审批人身份列表
                ClaApprovalOrderExample example = new ClaApprovalOrderExample();
                example.createCriteria().andApplicatTypeIdEqualTo(applicatTypeId);
                example.setOrderByClause("sort_order desc");
                approvalOrders = claApprovalOrderMapper.selectByExample(example);
                for (ClaApprovalOrder approvalOrder : approvalOrders) {
                    needApprovalTypeSet.add(approvalOrder.getApproverTypeId());
                }
            }
        }

        Map<Integer, Integer> resultMap = new HashMap<>();
        Map<Integer, ClaApprovalLog> approvalLogMap = new HashMap<>();
        { // 已审批的记录
            ClaApprovalLogExample example = new ClaApprovalLogExample();
            example.createCriteria().andApplyIdEqualTo(apply.getId());
            List<ClaApprovalLog> approvalLogs = claApprovalLogMapper.selectByExample(example);

            for (ClaApprovalLog approvalLog : approvalLogs) {
                Integer typeId = null;
                Integer value = null;
                if (approvalLog.getTypeId() == null) {
                    if (approvalLog.getOdType() == 0) { // 初审
                        typeId = ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST;
                        value = approvalLog.getStatus() ? 1 : 0;
                        //approvalResult.put(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST, approvalLog.getStatus() ? 1 : 0);
                    }
                    if (approvalLog.getOdType() == 1) { // 终审
                        typeId = ClaConstants.CLA_APPROVER_TYPE_ID_OD_LAST;
                        value = approvalLog.getStatus() ? 1 : 0;
                        //approvalResult.put(ClaConstants.CLA_APPROVER_TYPE_ID_OD_LAST, approvalLog.getStatus() ? 1 : 0);
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


        approvalResultMap.put(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST, new ClaApprovalResult(resultMap.get(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST),
                approvalLogMap.get(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST))); // 初审

        Map<Integer, ClaApproverType> approverTypeMap = claApproverTypeService.findAll();
        for (ClaApproverType approverType : approverTypeMap.values()) {
            if (needApprovalTypeSet.contains(approverType.getId()))
                approvalResultMap.put(approverType.getId(), new ClaApprovalResult(resultMap.get(approverType.getId()), approvalLogMap.get(approverType.getId())));
            else
                approvalResultMap.put(approverType.getId(), new ClaApprovalResult(-1, null));
        }

        approvalResultMap.put(ClaConstants.CLA_APPROVER_TYPE_ID_OD_LAST, new ClaApprovalResult(resultMap.get(ClaConstants.CLA_APPROVER_TYPE_ID_OD_LAST),
                approvalLogMap.get(ClaConstants.CLA_APPROVER_TYPE_ID_OD_LAST))); // 终审

        // value: -1不需要审批 0未通过 1通过 null未审批
        return approvalResultMap;
    }

    // 如果是本单位正职，返回单位ID 列表，包括兼职单位
    public List<Integer> getMainPostUnitIds(int userId) {

        List<Integer> unitIds = new ArrayList<>();
        ClaApproverType mainPostApproverType = claApproverTypeService.getMainPostApproverType();
        if (mainPostApproverType == null) return unitIds;

        Map<Integer, ClaApproverBlackList> blackListMap = claApproverBlackListService.findAll(mainPostApproverType.getId());
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null
                && (cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                && blackListMap.get(cadre.getId()) == null) { // 必须是现任干部，且不在黑名单
            MetaType postType = metaTypeMap.get(cadre.getPostId());
            if (postType != null && postType.getBoolAttr()) {
                unitIds.add(cadre.getUnitId());
            }
            if (postType == null) {
                SysUserView uv = cadre.getUser();
                logger.error(String.format("读取职务属性出错：%s %s postId=%s",
                        uv.getUsername(), uv.getRealname(), cadre.getPostId()));
            }
        }
        {
            // 兼任职务所在单位
            ClaAdditionalPostExample example = new ClaAdditionalPostExample();
            example.createCriteria().andCadreIdEqualTo(cadre.getId());
            List<ClaAdditionalPost> cPosts = claAdditionalPostMapper.selectByExample(example);
            for (ClaAdditionalPost cPost : cPosts) {
                MetaType postType = metaTypeMap.get(cPost.getPostId());
                if (postType.getBoolAttr()) {
                    unitIds.add(cPost.getUnitId());
                }
            }
        }

        return unitIds;
    }

    // 如果是分管校领导，返回分管单位ID列表
    public List<Integer> getLeaderMangerUnitIds(int userId) {

        List<Integer> unitIds = new ArrayList<>();
        ClaApproverType leaderApproverType = claApproverTypeService.getLeaderApproverType();
        if (leaderApproverType == null) return unitIds;

        Map<Integer, ClaApproverBlackList> blackListMap = claApproverBlackListService.findAll(leaderApproverType.getId());

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null && (cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                && blackListMap.get(cadre.getId()) == null) { // 必须是现任干部，且不在黑名单
            MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
            unitIds = iCadreMapper.getLeaderManagerUnitId(cadre.getId(), leaderManagerType.getId());
        }
        return unitIds;
    }

    // 如果是其他审批身份，返回需要审批的职务属性
    public List<Integer> getApprovalPostIds(int userId, int approverTypeId) {

        List<Integer> postIds = new ArrayList<>();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null) {
            postIds = iClaMapper.getApprovalPostIds_approverTypeId(cadre.getId(), approverTypeId);
        }
        return postIds;
    }

    // 查找用户可以审批的干部（非管理员） Set<干部ID>
    public Set<Integer> findApprovalCadreIdSet(int userId) {

        // 待审批的干部
        Set<Integer> cadreIdSet = new HashSet<>();

        // 待审批的干部所在单位
        List<Integer> unitIds = new ArrayList<>();

        // 如果是本单位正职（包括兼任职务所在单位）
        List<Integer> mainPostUnitIds = getMainPostUnitIds(userId);
        if (mainPostUnitIds.size() > 0) unitIds.addAll(mainPostUnitIds);

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

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null) {
            // 其他审批人身份的干部，查找他需要审批的干部
            List<Integer> approvalCadreIds = iClaMapper.getApprovalCadreIds(cadre.getId());
            cadreIdSet.addAll(approvalCadreIds);
        }

        return cadreIdSet;
    }

    // 判断是否有审批权限（非管理员）
    public boolean canApproval(int userId, int applyId, int approvalTypeId) {

        ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
        int targetCadreId = apply.getCadreId(); // 待审批的干部

        List<SysUserView> approvers = findApprovers(targetCadreId, approvalTypeId);
        for (SysUserView approver : approvers) {

            if (approver.getId().intValue() == userId) return true;
        }

        return false;
    }


    /*public boolean canApproval(int userId, int applyId, int approvalTypeId) {

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (approvalTypeId <= 0) {
            return ShiroHelper.hasRole(RoleConstants.ROLE_CADREADMIN);
        } else if (cadre == null || (cadre.getStatus() != CadreConstants.CADRE_STATUS_MIDDLE
                && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER)) {
            return false; // 必须是现任干部才有审批权限
        }

        ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
        int targetCadreId = apply.getCadreId(); // 待审批的干部
        CadreView targetCadre = cadreViewMapper.selectByPrimaryKey(targetCadreId);

        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(approvalTypeId);
        Byte type = approverType.getType();

        if (type == ClaConstants.CLA_APPROVER_TYPE_UNIT) { // 本单位正职审批
           // 待审批的干部所在单位
            Set<Integer> unitIds = new HashSet<>();
            unitIds.addAll(getMainPostUnitIds(userId));
            return unitIds.contains(targetCadre.getUnitId());
        }else if (type == ClaConstants.CLA_APPROVER_TYPE_LEADER) {  // 校领导审批

            //分管校领导
            MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
            List<Integer> unitIdList = iClaMapper.getLeaderManagerUnitId(cadre.getId(), leaderManagerType.getId());
            Set<Integer> unitIds = new HashSet<>();
            unitIds.addAll(unitIdList);

            return unitIds.contains(targetCadre.getUnitId());
        }else{
            Set<Integer> cadreIdSet = new HashSet<>();
            // 其他审批人身份 的所在单位 给定一个干部id，查找他需要审批的干部
            List<Integer> approvalCadreIds = iClaMapper.getApprovalCadreIds_approverTypeId(cadre.getId(), approvalTypeId);
            cadreIdSet.addAll(approvalCadreIds);

            return cadreIdSet.contains(targetCadreId);
        }
    }*/

    public List<ClaApplyFile> getFiles(int applyId) {

        ClaApplyFileExample example = new ClaApplyFileExample();
        example.createCriteria().andApplyIdEqualTo(applyId);
        return claApplyFileMapper.selectByExample(example);
    }

    public ClaApply get(int id) {

        return claApplyMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insertSelective(ClaApply record) {

        record.setIsDeleted(false);
        record.setIsFinish(false);
        record.setIsAgreed(false);
        record.setIsModify(false);
        return claApplyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        claApplyMapper.deleteByPrimaryKey(id);
    }

    // 逻辑删除（只能删除还未通过的记录）
    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ClaApplyExample example = new ClaApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsAgreedEqualTo(false);

        ClaApply record = new ClaApply();
        record.setIsDeleted(true);
        claApplyMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void batchUnDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ClaApplyExample example = new ClaApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        ClaApply record = new ClaApply();
        record.setIsDeleted(false);
        claApplyMapper.updateByExampleSelective(record, example);
    }

    // 真删除（只能删除已经被逻辑删除的记录）
    @Transactional
    public void doBatchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ClaApplyExample example = new ClaApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsDeletedEqualTo(true);

        claApplyMapper.deleteByExample(example);
    }


    @Transactional
    public int updateByPrimaryKeySelective(ClaApply record) {
        return claApplyMapper.updateByPrimaryKeySelective(record);
    }

    // 未标记删除的记录，才可以进行审批操作
    @Transactional
    public int doApproval(ClaApply record) {

        ClaApplyExample example = new ClaApplyExample();
        example.createCriteria().andIdEqualTo(record.getId()).andIsDeletedEqualTo(false);

        return claApplyMapper.updateByExampleSelective(record, example);
    }

    // 变更行程
    @Transactional
    public void modify(ClaApply record, String modifyProof, String modifyProofFileName, String modifyRemark) {

        // 第一次修改，需要保留原纪录
        {
            ClaApplyModifyExample example = new ClaApplyModifyExample();
            example.createCriteria().andApplyIdEqualTo(record.getId());
            if (claApplyModifyMapper.countByExample(example) == 0) {
                addModify(ClaConstants.CLA_APPLY_MODIFY_TYPE_ORIGINAL, record.getId(), null, null, "提交的记录");
            }
        }
        record.setIsModify(true);
        claApplyMapper.updateByPrimaryKeySelective(record);

        addModify(ClaConstants.CLA_APPLY_MODIFY_TYPE_MODIFY, record.getId(), modifyProof, modifyProofFileName, modifyRemark);
    }

    private void addModify(byte modifyType, int applyId, String modifyProof, String modifyProofFileName, String modifyRemark) {
        // 获取修改后的信息
        ClaApply apply = get(applyId);
        ClaApplyModify modify = new ClaApplyModify();
        try {
            BeanUtils.copyProperties(modify, apply);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
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
        if (modifyType == ClaConstants.CLA_APPLY_MODIFY_TYPE_ORIGINAL) {
            modify.setModifyUserId(apply.getUser().getId());
            modify.setIp(apply.getIp());
            modify.setCreateTime(apply.getCreateTime());
        }

        claApplyModifyMapper.insertSelective(modify);
    }

    // 本年度的申请记录（审批通过的申请）
    public List<ClaApply> getAnnualApplyList(int cadreId, int year) {

        ClaApplyExample example = new ClaApplyExample();
        ClaApplyExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        criteria.andIsAgreedEqualTo(true);
        criteria.andApplyDateBetween(DateUtils.parseDate(year + "-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(year + "-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");

        return claApplyMapper.selectByExample(example);
    }

    public void apply_export(ClaApplyExample example, HttpServletResponse response) {

        List<ClaApply> records = claApplyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号|100", "申请日期|100", "工作证号|100", "姓名|50",
                "所在单位及职务|300|left", "出行时间|130", "返校时间|130",
                "出行天数|80", "目的地|200|left", "请假事由|200|left",
                "同行人员|100", "申请时间|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ClaApply record = records.get(i);
            SysUserView uv = record.getUser();
            CadreView cadre = record.getCadre();

            String[] values = {
                    "L" + record.getId(),
                    DateUtils.formatDate(record.getApplyDate(), DateUtils.YYYY_MM_DD),
                    uv.getCode(),
                    uv.getRealname(),
                    cadre.getTitle(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.getDayCountBetweenDate(record.getStartTime(), record.getEndTime()) + "",
                    record.getDestination(),
                    record.getReason() == null ? "" : record.getReason().replaceAll("\\+\\+\\+", ","),
                    record.getPeerStaff() == null ? "" : record.getPeerStaff().replaceAll("\\+\\+\\+", ","),
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }

        String fileName = "干部请假申请_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
