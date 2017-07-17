package service.abroad;

import bean.ApplySelfSearchBean;
import bean.ApprovalResult;
import bean.ApprovalTdBean;
import bean.ApproverTypeBean;
import bean.ShortMsgBean;
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
import domain.cadre.CadreAdditionalPost;
import domain.cadre.CadreAdditionalPostExample;
import domain.cadre.CadreExample;
import domain.cadre.CadreLeader;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.BeanUtils;
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
import service.base.ContentTplService;
import service.base.MetaTypeService;
import service.base.ShortMsgService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.sys.UserBeanService;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
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
public class ApplySelfService extends BaseMapper {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreCommonService cadreCommonService;
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

    // 查找审批人
    public List<SysUserView> findApprovers(int cadreId/*被审批干部*/, int approvalTypeId) {

        // 审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）
        if (approvalTypeId <= 0) { // 查找干部管理员
            /*List<SysUserView> cadreAdmin = sysUserService.findByRole(SystemConstants.ROLE_CADREADMIN);
            return cadreAdmin;*/
            if(approvalTypeId==-1) { // 组织部初审
                ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_APPLYSELF_SUBMIT_INFO);
                return contentTplService.getShorMsgReceivers(tpl.getId());
            }else if(approvalTypeId==0){ // 组织部终审
                ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_APPLYSELF_PASS_INFO);
                return contentTplService.getShorMsgReceivers(tpl.getId());
            }

            return new ArrayList<SysUserView>();
        } else {

            ApproverType mainPostApproverType = approverTypeService.getMainPostApproverType();
            ApproverType leaderApproverType = approverTypeService.getLeaderApproverType();
            Map<Integer, ApproverBlackList> mainPostBlackList = approverBlackListService.findAll(mainPostApproverType.getId());
            Map<Integer, ApproverBlackList> leaderBlackList = approverBlackListService.findAll(leaderApproverType.getId());

            Map<Integer, CadreView> cadreMap = cadreService.findAll();
            CadreView cadre = cadreMap.get(cadreId);
            ApproverType approverType = approverTypeService.findAll().get(approvalTypeId);
            if (approverType.getType() == SystemConstants.APPROVER_TYPE_UNIT) { // 查找本单位正职
                List<SysUserView> _users = new ArrayList<SysUserView>();
                List<Cadre> mainPostList = cadreCommonService.findMainPost(cadre.getUnitId());
                for (Cadre _cadre : mainPostList) {
                    if ((_cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER)
                            && (mainPostBlackList.get(_cadre.getId())==null))  // 排除本单位正职黑名单（不包括兼审单位正职）
                        _users.add(_cadre.getUser());
                }

                List<CadreView> additionalPost = cadreCommonService.findAdditionalPost(cadre.getUnitId());
                for (CadreView _cadre : additionalPost) {
                    if (_cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER){
                        _users.add(_cadre.getUser());
                    }
                }

                return _users;
            } else if (approverType.getType() == SystemConstants.APPROVER_TYPE_LEADER) { // 查找分管校领导

                List<SysUserView> users = new ArrayList<SysUserView>();
                MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
                List<CadreLeader> managerUnitLeaders = iCadreMapper.getManagerUnitLeaders(cadre.getUnitId(), leaderManagerType.getId());
                for (CadreLeader managerUnitLeader : managerUnitLeaders) {
                    CadreView _cadre = managerUnitLeader.getCadre();
                    if ((_cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER)
                            && leaderBlackList.get(_cadre.getId())==null)  // 排除黑名单
                        users.add(managerUnitLeader.getUser());
                }
                return users;
            } else { // 查找其他身份下的审批人
                List<SysUserView> users = new ArrayList<SysUserView>();
                List<Approver> approvers = approverService.findByType(approvalTypeId);
                for (Approver approver : approvers) {
                    CadreView _cadre = approver.getCadre();
                    if (_cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                            || _cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER)
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

    /**
     * 仅用于定时任务，给需要审批的人员发短信
     *
     * 如果领导没有审批，那么从第二天开始，每天早上8点发送一次
      */
    public void sendApprovalMsg(){

        logger.debug("====因私审批短信通知...start====");
        int success = 0, total = 0; // 成功条数，总条数
        //Date today = new Date();

        ApplySelfExample example = new ApplySelfExample();
        example.createCriteria().andIsDeletedEqualTo(false) // 没有删除
                .andStatusEqualTo(true) // 已经提交的
                .andIsFinishEqualTo(false) // 还未完成审批的
                .andFlowNodeGreaterThan(0); // 当前不是组织部审批的
        List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);

        for (ApplySelf applySelf : applySelfs) {

            Map<String, Integer> resultMap = sendApprovalMsg(applySelf.getId());
            success += resultMap.get("success");
            total += resultMap.get("total");
        }

        logger.debug(String.format("====因私审批短信通知，发送成功%s/%s条...end====", success, total));
    }

    /**
     * 给一条申请记录的下一步审批人发短信
     * @param applySelfId
     * @return 发送短信的数目
     */
    public Map<String, Integer> sendApprovalMsg(int applySelfId){

        int success = 0, total = 0; // 成功条数，总条数
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        resultMap.put("id", applySelfId);
        resultMap.put("success", success);
        resultMap.put("total", total);

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        if(applySelf.getIsDeleted() || !applySelf.getStatus()
                || applySelf.getIsFinish() || applySelf.getFlowNode()<=0){
            return resultMap;
        }

        SysUserView applyUser = applySelf.getUser();
        Integer flowNode = applySelf.getFlowNode();
        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(flowNode);
        Byte type = approverType.getType();
        List<SysUserView> approvers = findApprovers(applySelf.getCadreId(), flowNode);
        int size = approvers.size();
        String key = null; // 短信模板代码
        if(size>0) {
            if (type == SystemConstants.APPROVER_TYPE_UNIT) { // 本单位正职审批

                if (size > 1) { // 多个正职审批
                    key = SystemConstants.CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_2;
                } else{ // 单个正职审批
                    key = SystemConstants.CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_1;
                }
            } else if (type == SystemConstants.APPROVER_TYPE_LEADER) {  // 校领导审批

                key = SystemConstants.CONTENT_TPL_APPLYSELF_APPROVAL_LEADER;
            } else if (type == SystemConstants.APPROVER_TYPE_SECRETARY) { // 书记审批

                key = SystemConstants.CONTENT_TPL_APPLYSELF_APPROVAL_SECRETARY;
            } else if (type == SystemConstants.APPROVER_TYPE_MASTER) { // 校长审批

                key = SystemConstants.APPLYSELF_APPROVAL_MASTER;
            }

            // 校验用，以防万一
            if(size>1 && !StringUtils.equals(key, SystemConstants.CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_2)){
                logger.error("因私审批系统发送短信异常："
                        + JSONUtils.toString(applySelf, false));
                return resultMap;
            }
        }
        if(key != null){
            for (SysUserView approver : approvers) {

                int userId = approver.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);
                ShortMsgBean bean = new ShortMsgBean();
                bean.setSender(null);
                bean.setReceiver(userId);
                ContentTpl tpl = shortMsgService.getShortMsgTpl(key);
                String msgTpl = tpl.getContent();
                bean.setType(tpl.getName());
                String msg = null;
                switch (key){
                    case SystemConstants.CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_1:
                    case SystemConstants.CONTENT_TPL_APPLYSELF_APPROVAL_UNIT_2:
                        msg = MessageFormat.format(msgTpl, msgTitle, applyUser.getRealname());
                        break;
                    default:
                        CadreView applyCadre = cadreService.dbFindByUserId(applyUser.getId());
                        msg = MessageFormat.format(msgTpl, msgTitle, applyCadre.getTitle(), applyUser.getRealname());
                        break;
                }
                bean.setContent(msg);
                bean.setMobile(mobile);
                try {
                    total++;
                    boolean ret = shortMsgService.send(bean, "127.0.0.1");
                    logger.info(String.format("系统发送短信[%s]：%s", ret ? "成功" : "失败", bean.getContent()));

                    if(ret) success++;
                }catch (Exception ex){
                    logger.error("因私审批系统发送短信失败", ex);
                }
            }
        }

        resultMap.put("success", success);
        resultMap.put("total", total);

        return resultMap;
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

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null && (cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                || cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER)) { // 审批人必须是现任干部才有审批权限

            //==============================================
            Map<Integer, List<Integer>> approverTypeUnitIdListMap = new HashMap<Integer, List<Integer>>();
            Map<Integer, List<Integer>> approverTypePostIdListMap = new HashMap<Integer, List<Integer>>();

            ApproverType mainPostApproverType = approverTypeService.getMainPostApproverType();
            ApproverType leaderApproverType = approverTypeService.getLeaderApproverType();

            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();

            if (approverTypeBean != null) {
                if (approverTypeBean.getMainPostUnitIds().size()>0) {
                    List unitIds = new ArrayList();
                    unitIds.addAll(approverTypeBean.getMainPostUnitIds());
                    approverTypeUnitIdListMap.put(mainPostApproverType.getId(), unitIds);
                }
                if (approverTypeBean.getLeaderUnitIds().size() > 0) {
                    approverTypeUnitIdListMap.put(leaderApproverType.getId(), approverTypeBean.getLeaderUnitIds());
                }

                approverTypePostIdListMap = approverTypeBean.getApproverTypePostIdListMap();
            }
            if (approverTypeUnitIdListMap != null && approverTypeUnitIdListMap.size() == 0)
                approverTypeUnitIdListMap = null;
            if (approverTypePostIdListMap != null && approverTypePostIdListMap.size() == 0)
                approverTypePostIdListMap = null;
            //==============================================

            ApplySelfSearchBean searchBean = new ApplySelfSearchBean(cadreId, type,
                    _applyDate==null?null:_applyDate.getStart(), _applyDate==null?null:_applyDate.getEnd());

            if (status == 0)
                count = iAbroadMapper.countNotApproval(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap);
            if (status == 1)
                count = iAbroadMapper.countHasApproval(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap, userId);

            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }

            if (status == 0)
                applySelfs = iAbroadMapper.selectNotApprovalList(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap,
                        new RowBounds((pageNo - 1) * pageSize, pageSize));
            if (status == 1)
                applySelfs = iAbroadMapper.selectHasApprovalList(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap, userId,
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
                List<SysUserView> approvers = findApprovers(applySelf.getCadreId(), bean.getApprovalTypeId());
                bean.setApproverList(approvers);
            }

            resultMap.put(0, bean); // 终审
        }

        return resultMap;
        /*if(!view && ShiroHelper.hasRole("cadreAdmin")) {
            ApplySelfMapper applySelfMapper = (ApplySelfMapper) wac.getBean("applySelfMapper");
            CadreService cadreService = (CadreService) wac.getBean("cadreService");
            SysUserService sysUserService = (SysUserService) wac.getBean("sysUserService");
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
            CadreView cadre = cadreService.findAll().get(applySelf.getCadreId());
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

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre == null || (cadre.getStatus() != SystemConstants.CADRE_STATUS_MIDDLE
                && cadre.getStatus() != SystemConstants.CADRE_STATUS_LEADER)) return null;

        // 本单位正职
        List<Integer> mainPostUnitIds = getMainPostUnitIds(userId);
        // 分管校领导
        List<Integer> leaderUnitIds = getLeaderMangerUnitIds(userId);

        // 其他身份
        Map<Integer, List<Integer>> approverTypePostIdListMap = new HashMap<>(); // 本人所属的审批身份及对应的审批的职务属性
        Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
        for (ApproverType approverType : approverTypeMap.values()) {
            Byte type = approverType.getType();
            if (type != SystemConstants.APPROVER_TYPE_UNIT && type != SystemConstants.APPROVER_TYPE_LEADER) {
                List<Integer> approvalPostIds = getApprovalPostIds(userId, approverType.getId());
                if (approvalPostIds.size() > 0) {
                    approverTypePostIdListMap.put(approverType.getId(), approvalPostIds);
                }
            }
        }

        ApproverTypeBean approverTypeBean = new ApproverTypeBean();
        approverTypeBean.setCadre(cadre);
        approverTypeBean.setMainPostUnitIds(mainPostUnitIds);
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

        Map<Integer, ApprovalResult> approvalResultMap = new LinkedHashMap<>();

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applyId);
        Integer cadreId = applySelf.getCadreId();
        //CadreView cadre = cadreService.findAll().get(cadreId);
        //Integer postId = cadre.getPostId();

        Integer applicatTypeId = null;
        {   // 查询申请人身份
            ApplicatCadreExample example = new ApplicatCadreExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            List<ApplicatCadre> applicatCadres = applicatCadreMapper.selectByExample(example);
            if(applicatCadres.size()==0){
                logger.error("===========数据异常，干部没有任何身份: cadreId="+cadreId);
                return approvalResultMap;// 异常情况，不允许申请人没有任何身份
            }
            ApplicatCadre applicatCadre = applicatCadres.get(0);
            applicatTypeId = applicatCadre.getTypeId();
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

    // 如果是本单位正职，返回单位ID 列表，包括兼职单位
    public List<Integer> getMainPostUnitIds(int userId) {

        List<Integer> unitIds = new ArrayList<>();
        ApproverType mainPostApproverType = approverTypeService.getMainPostApproverType();
        Map<Integer, ApproverBlackList> blackListMap = approverBlackListService.findAll(mainPostApproverType.getId());
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null
                && (cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                || cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER)
                && blackListMap.get(cadre.getId())==null) { // 必须是现任干部，且不在黑名单
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
            CadreAdditionalPostExample example = new CadreAdditionalPostExample();
            example.createCriteria().andCadreIdEqualTo(cadre.getId());
            List<CadreAdditionalPost> cPosts = cadreAdditionalPostMapper.selectByExample(example);
            for (CadreAdditionalPost cPost : cPosts) {
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

        ApproverType leaderApproverType = approverTypeService.getLeaderApproverType();
        Map<Integer, ApproverBlackList> blackListMap = approverBlackListService.findAll(leaderApproverType.getId());

        List<Integer> unitIds = new ArrayList<>();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null && (cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                || cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER)
                && blackListMap.get(cadre.getId())==null) { // 必须是现任干部，且不在黑名单
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
            postIds = iAbroadMapper.getApprovalPostIds_approverTypeId(cadre.getId(), approverTypeId);
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
        if (mainPostUnitIds.size()>0) unitIds.addAll(mainPostUnitIds);

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
            List<Integer> approvalCadreIds = iAbroadMapper.getApprovalCadreIds(cadre.getId());
            cadreIdSet.addAll(approvalCadreIds);
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
    /*public boolean canApproval(int userId, int applySelfId, int approvalTypeId) {

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (approvalTypeId <= 0) {
            return ShiroHelper.hasRole(SystemConstants.ROLE_CADREADMIN);
        } else if (cadre == null || (cadre.getStatus() != SystemConstants.CADRE_STATUS_MIDDLE
                && cadre.getStatus() != SystemConstants.CADRE_STATUS_LEADER)) {
            return false; // 必须是现任干部才有审批权限
        }

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        int targetCadreId = applySelf.getCadreId(); // 待审批的干部
        CadreView targetCadre = cadreService.findAll().get(targetCadreId);

        ApproverType approverType = approverTypeMapper.selectByPrimaryKey(approvalTypeId);
        Byte type = approverType.getType();

        if (type == SystemConstants.APPROVER_TYPE_UNIT) { // 本单位正职审批
           // 待审批的干部所在单位
            Set<Integer> unitIds = new HashSet<>();
            unitIds.addAll(getMainPostUnitIds(userId));
            return unitIds.contains(targetCadre.getUnitId());
        }else if (type == SystemConstants.APPROVER_TYPE_LEADER) {  // 校领导审批

            //分管校领导
            MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
            List<Integer> unitIdList = iAbroadMapper.getLeaderManagerUnitId(cadre.getId(), leaderManagerType.getId());
            Set<Integer> unitIds = new HashSet<>();
            unitIds.addAll(unitIdList);

            return unitIds.contains(targetCadre.getUnitId());
        }else{
            Set<Integer> cadreIdSet = new HashSet<>();
            // 其他审批人身份 的所在单位 给定一个干部id，查找他需要审批的干部
            List<Integer> approvalCadreIds = iAbroadMapper.getApprovalCadreIds_approverTypeId(cadre.getId(), approvalTypeId);
            cadreIdSet.addAll(approvalCadreIds);

            return cadreIdSet.contains(targetCadreId);
        }
    }*/

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
                addModify(SystemConstants.APPLYSELF_MODIFY_TYPE_ORIGINAL, record.getId(), null, null, "提交的记录");
            }
        }
        record.setIsModify(true);
        applySelfMapper.updateByPrimaryKeySelective(record);

        addModify(SystemConstants.APPLYSELF_MODIFY_TYPE_MODIFY, record.getId(), modifyProof, modifyProofFileName, modifyRemark);
    }

    private void addModify(byte modifyType, int applyId, String modifyProof, String modifyProofFileName, String modifyRemark) {
        // 获取修改后的信息
        ApplySelf applySelf = get(applyId);
        ApplySelfModify modify = new ApplySelfModify();
        try {
            BeanUtils.copyProperties(modify, applySelf);
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
        if (modifyType == SystemConstants.APPLYSELF_MODIFY_TYPE_ORIGINAL) {
            modify.setModifyUserId(applySelf.getUser().getId());
            modify.setIp(applySelf.getIp());
            modify.setCreateTime(applySelf.getCreateTime());
        }

        applySelfModifyMapper.insertSelective(modify);
    }

    public void applySelf_export(ApplySelfExample example, HttpServletResponse response) {

        List<ApplySelf> records = applySelfMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号", "申请日期", "工作证号", "姓名", "所在单位及职务", "出行时间", "回国时间", /*"出行时间范围",*/
                "出行天数", "前往国家或地区", "因私出国（境）事由", "同行人员", "费用来源", "所需证件", "申请时间"};
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
