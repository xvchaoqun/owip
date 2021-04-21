package service.parttime;

import bean.ShortMsgBean;
import domain.base.ContentTpl;
import domain.base.MetaType;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.leader.LeaderUnitView;
import domain.parttime.*;
import domain.sys.SysUserView;
import ext.service.ShortMsgService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.parttime.common.ParttimeApplySearchBean;
import persistence.parttime.common.ParttimeApprovalResult;
import persistence.parttime.common.ParttimeApprovalTdBean;
import persistence.parttime.common.ParttimeApproverTypeBean;
import service.base.ContentTplService;
import service.base.MetaTypeService;
import service.cadre.CadreCommonService;
import service.cadre.CadrePostService;
import service.cadre.CadreService;
import service.sys.UserBeanService;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.CadreConstants;
import sys.constants.ContentTplConstants;
import sys.constants.ParttimeConstants;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.spring.DateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.*;

@Service
public class ParttimeApplyService extends ParttimeBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CadreService cadreService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private ContentTplService contentTplService;
    @Autowired
    private UserBeanService userBeanService;
    @Autowired
    private CadreCommonService cadreCommonService;
    @Autowired
    private ParttimeApproverTypeService parttimeApproverTypeService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private CadrePostService cadrePostService;

    public Map findApplyList(ParttimeApply parttimeApply, String applyTime, String parttime,
                             byte status,
                            Integer pageNo, Integer pageSize, Integer[] ids, int export, HttpServletResponse response) {
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ParttimeApplyExample example = new ParttimeApplyExample();
        ParttimeApplyExample.Criteria criteria = example.createCriteria();
        //已提交
        if (status == 0) {
            criteria.andStatusEqualTo(true);
            criteria.andIsDeletedEqualTo(false);
            criteria.andIsFinishEqualTo(false);
            criteria.andIsAgreedIsNull();
        }
        //同意申请
        if (status == 1) {
            criteria.andIsAgreedEqualTo(true);
            criteria.andIsDeletedEqualTo(false);
            criteria.andIsFinishEqualTo(true);
        }
        //不同意申请
        if (status == 2) {
            criteria.andIsAgreedEqualTo(false);
            criteria.andIsDeletedEqualTo(false);
            criteria.andIsFinishEqualTo(true);
        }
        //已删除
        if (status == 3) {
            criteria.andIsDeletedEqualTo(true);
        }
        if (parttimeApply.getCadreId() != null) {
            criteria.andCadreIdEqualTo(parttimeApply.getCadreId());
        }
        if (parttimeApply.getIsFirst() != null) {
            criteria.andIsFirstEqualTo(parttimeApply.getIsFirst());
        }
        if (parttimeApply.getBackground() != null) {
            criteria.andBackgroundEqualTo(parttimeApply.getBackground());
        }
        if (parttimeApply.getHasPay() != null) {
            criteria.andHasPayEqualTo(parttimeApply.getHasPay());
        }
        if (StringUtils.isNotBlank(applyTime)) {
            String[] split = applyTime.split("至");
            if (split.length > 0) {
                String startApplyTime = split[0].trim();
                String endApplyTime = split[1].trim();
                criteria.andApplyTimeBetween(DateUtils.parseDate(startApplyTime), DateUtils.parseDate(endApplyTime));
            }
        }
        if (StringUtils.isNotBlank(parttime)) {
            String[] split = parttime.split("至");
            if (split.length > 0) {
                String startTime = split[0].trim();
                String endTime = split[1].trim();
                criteria.andStartTimeGreaterThanOrEqualTo(DateUtils.parseDate(startTime));
                criteria.andEndTimeLessThanOrEqualTo(DateUtils.parseDate(endTime));
            }
        }

        if (export == 1) {
            if (ids != null && ids.length > 0) {
                criteria.andIdIn(Arrays.asList(ids));
            }
            parttime_export(example, response);
            return null;
        }

        long count = parttimeApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ParttimeApply> applys = parttimeApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map map = new HashMap();
        map.put("applys", applys);
        map.put("commonList", new CommonList(count, pageNo, pageSize));
        return map;
    }

    @Async
    public void sendApplySubmitMsgToCadreAdmin(int applyId, String ip){

        ParttimeApplyService parttimeApplyService = ApplicationContextSupport.getContext().getBean(ParttimeApplyService.class);
        ParttimeApply parttimeApply = get(applyId);
        SysUserView applyUser = parttimeApply.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PARTTIME_APPLY_SUBMIT_INFO);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        cadreTitle, applyUser.getRealname());

                ShortMsgBean bean = new ShortMsgBean();
                shortMsgService.initShortMsgBeanParams(bean, tpl);

                bean.setSender(applyUser.getId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setTypeStr(tpl.getName());

                shortMsgService.send(bean, ip);
            }catch (Exception ex){
                logger.error("异常", ex);
                logger.error("干部提交兼职申报，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {},{}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    public ParttimeApply get(int id) {
        return parttimeApplyMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ParttimeApply record) {
        return parttimeApplyMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void modify(ParttimeApply record, String modifyProof, String modifyProofFileName, String modifyRemark) {

        // 第一次修改，需要保留原纪录
        {
            ParttimeApplyModifyExample example = new ParttimeApplyModifyExample();
            example.createCriteria().andApplyIdEqualTo(record.getId());
            if (parttimeApplyModifyMapper.countByExample(example) == 0) {
                addModify(ParttimeConstants.PARTTIME_APPLY_MODIFY_TYPE_MODIFY, record, null, null, "提交的记录");
            }
        }
        record.setIsModify(true);
        parttimeApplyMapper.updateByPrimaryKeySelective(record);

        addModify(ParttimeConstants.PARTTIME_APPLY_MODIFY_TYPE_MODIFY, record, modifyProof, modifyProofFileName, modifyRemark);
    }

    private void addModify(byte modifyType, ParttimeApply record, String modifyProof, String modifyProofFileName, String modifyRemark) {
        // 获取修改后的信息
        ParttimeApply apply = get(record.getId());
        ParttimeApplyModify modify = new ParttimeApplyModify();
        try {
            PropertyUtils.copyProperties(modify, apply);
        } catch (Exception e) {
            logger.error("异常", e);
        }

        modify.setId(null);
        modify.setType(record.getType());
        modify.setModifyType(modifyType);
        modify.setApplyId(record.getId());
        modify.setModifyProof(modifyProof);
        modify.setModifyProofFileName(modifyProofFileName);
        modify.setRemark(modifyRemark);
        modify.setIp(IpUtils.getRealIp(ContextHelper.getRequest()));
        modify.setModifyUserId(ShiroHelper.getCurrentUserId());
        modify.setCreateTime(new Date());
        modify.setTitle(record.getTitle());
        modify.setStartTime(record.getStartTime());
        modify.setEndTime(record.getEndTime());

        // 第一条记录标记为本人提交
        if (modifyType == ParttimeConstants.PARTTIME_APPLY_MODIFY_TYPE_ORIGINAL) {
            modify.setModifyUserId(apply.getUser().getId());
            modify.setIp(apply.getIp());
            modify.setCreateTime(apply.getCreateTime());
        }
        parttimeApplyModifyMapper.insertSelective(modify);
    }

    public void parttime_export(ParttimeApplyExample example, HttpServletResponse response) {

        List<ParttimeApply> records = parttimeApplyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"编号|100", "申请日期|100", "工作证号|100", "姓名|50",
                "兼职单位及职务|300|left", "兼职开始时间|130", "兼职结束时间|130",
                "首次/连任|80", "是否有国境外背景|200|left", "是否取酬|200|left",
                "取酬金额|100", "申请理由|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ParttimeApply record = records.get(i);
            SysUserView uv = record.getUser();
            CadreView cadre = record.getCadre();

            String[] values = {
                    "L" + record.getId(),
                    DateUtils.formatDate(record.getApplyTime(), DateUtils.YYYY_MM_DD),
                    uv.getCode(),
                    uv.getRealname(),
                    cadre.getTitle(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    record.getIsFirst() ? "是" : "否",
                    record.getBackground() ? "是" : "否",
                    record.getHasPay() ? "是" : "否",
                    String.valueOf(record.getBalance()),
                    record.getReason()
            };
            valuesList.add(values);
        }

        String fileName = "兼职申报_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @Transactional
    public void batchDel(Integer[] ids) {
        if (ids == null || ids.length == 0) return;
        ParttimeApplyExample example = new ParttimeApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
//        List<ParttimeApply> list = parttimeApplyMapper.selectByExample(example);
        for (int i = 0; i < ids.length;i++) {
            ParttimeApply record = new ParttimeApply();
            record.setIsDeleted(true);
            parttimeApplyMapper.updateByExampleSelective(record, example);
        }
    }

    public List<SysUserView> findApprovers(int cadreId/*被审批干部*/, int approvalTypeId) {

        // 审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）
        if (approvalTypeId <= 0) { // 查找干部管理员
            if (approvalTypeId == -1) { // 组织部初审
                try {
                    ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PARTTIME_APPLY_SUBMIT_INFO);
                    return contentTplService.getShorMsgReceivers(tpl.getId());
                } catch (Exception ex) {
                    logger.error("初审审批人读取异常", ex);
                }
            } else if (approvalTypeId == 0) { // 组织部终审
                try {
                    ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PARTTIME_APPLY_PASS_INFO);
                    return contentTplService.getShorMsgReceivers(tpl.getId());
                } catch (Exception ex) {
                    logger.error("终审审批人读取异常", ex);
                }
            }

            return new ArrayList<SysUserView>();
        } else {

            ParttimeApproverType mainPostApproverType = parttimeApproverTypeService.getMainPostApproverType();
            ParttimeApproverType leaderApproverType = parttimeApproverTypeService.getLeaderApproverType();
            Map<Integer, ParttimeApproverBlackList> mainPostBlackList = new HashMap<>();
            //获取黑名单中的记录
            if(mainPostApproverType!=null) {
                mainPostBlackList = findAll(mainPostApproverType.getId());
            }
            Map<Integer, ParttimeApproverBlackList> leaderBlackList = new HashMap<>();
            if(leaderApproverType!=null) {
                leaderBlackList = findAll(leaderApproverType.getId());
            }
            //获取申请人信息
            CadreView cadre = CmTag.getCadreById(cadreId);
            Integer unitId = cadre.getUnitId();
            //获取审批人分类
            ParttimeApproverType approverType = parttimeApproverTypeService.findAll().get(approvalTypeId);
            if (approverType.getType() == ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT) { // 查找院系党组织
                List<SysUserView> _users = new ArrayList<SysUserView>();
                if(unitId!=null) {
                    List<CadreView> mainPostList = cadreCommonService.findMainPost(unitId);
                    for (CadreView _cadre : mainPostList) {
                        if ((_cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                                || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                                && (mainPostBlackList.get(_cadre.getId()) == null))  // 排除本单位正职黑名单（不包括兼审单位正职）
                            _users.add(_cadre.getUser());
                    }
                }

                return _users;
            } else if (approverType.getType() == ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER) { // 查找分管校领导

                List<SysUserView> users = new ArrayList<SysUserView>();
                if(unitId!=null) {
                    MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
                    List<LeaderUnitView> managerUnitLeaders = iLeaderMapper.getManagerUnitLeaders(unitId, leaderManagerType.getId());
                    if (managerUnitLeaders.size() > 0) {
                        for (LeaderUnitView managerUnitLeader : managerUnitLeaders) {
                            CadreView _cadre = managerUnitLeader.getCadre();
                            if ((_cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                                    || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                                    && leaderBlackList.get(_cadre.getId()) == null)  // 排除黑名单
                                users.add(_cadre.getUser());
                        }
                    }
                }
                return users;
            } else { // 查找其他身份下的审批人
                List<SysUserView> users = new ArrayList<SysUserView>();
                List<ParttimeApprover> approvers = findByType(approvalTypeId);
                for (ParttimeApprover approver : approvers) {
                    CadreView _cadre = approver.getCadre();
                    if (_cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                            || _cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                        users.add(approver.getUser());
                }
                return users;
            }
        }
    }

    public ParttimeApprovalLog getApprovalLog(int applySefId, int approverTypeId) {

        ParttimeApprovalLogExample example = new ParttimeApprovalLogExample();
        ParttimeApprovalLogExample.Criteria criteria = example.createCriteria().andApplyIdEqualTo(applySefId);
        if(approverTypeId==-1){
            criteria.andTypeIdIsNull().andOdTypeEqualTo(ParttimeConstants.PARTTIME_APPROVER_LOG_OD_TYPE_FIRST);
        }else if(approverTypeId==0){
            criteria.andTypeIdIsNull().andOdTypeEqualTo(ParttimeConstants.PARTTIME_APPROVER_LOG_OD_TYPE_LAST);
        }else{
            criteria.andTypeIdEqualTo(approverTypeId);
        }
        List<ParttimeApprovalLog> approvalLogs = parttimeApprovalLogMapper.selectByExample(example);
        if(approvalLogs.size()>0) return approvalLogs.get(0);
        return null;
    }

    public ParttimeApproverType getMainPostApproverType(){
        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        example.createCriteria().andTypeEqualTo(ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT);
        List<ParttimeApproverType> approverTypes = parttimeApproverTypeMapper.selectByExample(example);
        if(approverTypes.size()>0) return approverTypes.get(0);
        return null;
    }

    public ParttimeApproverType getLeaderApproverType(){

        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        example.createCriteria().andTypeEqualTo(ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER);
        List<ParttimeApproverType> approverTypes = parttimeApproverTypeMapper.selectByExample(example);
        if(approverTypes.size()>0) return approverTypes.get(0);
        return null;
    }

    @Cacheable(value="ParttimeApproverBlackList", key = "#approverTypeId")
    public Map<Integer, ParttimeApproverBlackList> findAll(int approverTypeId) {

        ParttimeApproverBlackListExample example = new ParttimeApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        List<ParttimeApproverBlackList> records = parttimeApproverBlackListMapper.selectByExample(example);
        Map<Integer, ParttimeApproverBlackList> map = new LinkedHashMap<>();
        for (ParttimeApproverBlackList record : records) {
            map.put(record.getCadreId(), record);
        }
        return map;
    }

    @Cacheable(value="parttimeApproverType:ALL")
    public Map<Integer, ParttimeApproverType> findAll() {
        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        example.setOrderByClause("sort_order desc");
        List<ParttimeApproverType> parttimeApproverTypees = parttimeApproverTypeMapper.selectByExample(example);
        Map<Integer, ParttimeApproverType> map = new LinkedHashMap<>();
        for (ParttimeApproverType parttimeApproverType : parttimeApproverTypees) {
            map.put(parttimeApproverType.getId(), parttimeApproverType);
        }
        return map;
    }

    public List<ParttimeApprover> findByType(int typeId){
        ParttimeApproverExample example = new ParttimeApproverExample();
        example.createCriteria().andTypeIdEqualTo(typeId);

        return parttimeApproverMapper.selectByExample(example);
    }

    public List<ParttimeApplyFile> getFiles(int applyId) {

        ParttimeApplyFileExample example = new ParttimeApplyFileExample();
        example.createCriteria().andApplyIdEqualTo(applyId);
        return parttimeApplyFileMapper.selectByExample(example);
    }

    /**
     * <审批人身份id，审批结果>
     * 审批人身份id: -1 初审  0 终审  >0 (approvalType.id)
     * 审批结果: ApprovalResult.value -1不需要审批 0未通过 1通过 null未审批
     */
    public Map<Integer, ParttimeApprovalResult> getApprovalResultMap(int applyId) {

        Map<Integer, ParttimeApprovalResult> approvalResultMap = new LinkedHashMap<>();

        ParttimeApply apply = parttimeApplyMapper.selectByPrimaryKey(applyId);
        Integer cadreId = apply.getCadreId();
        //CadreView cadre = iCadreMapper.getCadre(cadreId);
        //Integer postId = cadre.getPostId();

        Set<Integer> needApprovalTypeSet = new HashSet<>();

        if(apply.getIsFinish()){
            String flowNodes = apply.getFlowNodes();
            if (StringUtils.isNotBlank(flowNodes)) {
                String[] _approverTypeIds = flowNodes.split(",");
                if (_approverTypeIds.length > 0) {
                    for (String _approverTypeId : _approverTypeIds) {
                        if (StringUtils.isBlank(_approverTypeId)) continue;
                        int approverTypeId = Integer.valueOf(_approverTypeId);
                        if (approverTypeId > 0) {
                            needApprovalTypeSet.add(approverTypeId);
                        }
                    }
                }
            }
        }else {
            Integer applicatTypeId = null;
            {   // 查询申请人身份
                ParttimeApplicatCadreExample example = new ParttimeApplicatCadreExample();
                example.createCriteria().andCadreIdEqualTo(cadreId);
                List<ParttimeApplicatCadre> applicatCadres = parttimeApplicatCadreMapper.selectByExample(example);
                if (applicatCadres.size() == 0) {
                    CadreView cv = apply.getCadre();
                    logger.error("数据错误，干部没有任何身份: {}, {}", cv.getCode(), cv.getRealname());
                    return approvalResultMap;// 异常情况，不允许申请人没有任何身份
                }
                ParttimeApplicatCadre applicatCadre = applicatCadres.get(0);
                applicatTypeId = applicatCadre.getTypeId();
            }
            List<ParttimeApprovalOrder> approvalOrders = null;
            {  // 所需要的审批人身份列表
                ParttimeApprovalOrderExample example = new ParttimeApprovalOrderExample();
                example.createCriteria().andApplicateTypeIdEqualTo(applicatTypeId);
                example.setOrderByClause("sort_order desc");
                approvalOrders = parttimeApprovalOrderMapper.selectByExample(example);
                //选择国境外背景走外事部门流程，否则跳过
                Map approverTypeMap = parttimeApproverTypeService.findAll();
                for (ParttimeApprovalOrder approvalOrder : approvalOrders) {
                    ParttimeApproverType parttimeApproverType = (ParttimeApproverType) approverTypeMap.get(approvalOrder.getApproverTypeId());
                    if (parttimeApproverType.getType() == ParttimeConstants.PARTTIME_APPROVER_TYPE_FOREIGN) {
                        if (!apply.getBackground()) continue;
                    }
                    needApprovalTypeSet.add(approvalOrder.getApproverTypeId());
                }
            }
        }

        Map<Integer, Integer> resultMap = new HashMap<>();
        Map<Integer, ParttimeApprovalLog> approvalLogMap = new HashMap<>();
        { // 已审批的记录
            ParttimeApprovalLogExample example = new ParttimeApprovalLogExample();
            example.createCriteria().andApplyIdEqualTo(apply.getId());
            List<ParttimeApprovalLog> approvalLogs = parttimeApprovalLogMapper.selectByExample(example);

            for (ParttimeApprovalLog approvalLog : approvalLogs) {
                Integer typeId = null;
                Integer value = null;
                if (approvalLog.getTypeId() == null) {
                    if (approvalLog.getOdType() == 0) { // 初审
                        typeId = ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST;
                        value = approvalLog.getStatus() ? 1 : 0;
                        //approvalResult.put(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST, approvalLog.getStatus() ? 1 : 0);
                    }
                    if (approvalLog.getOdType() == 1) { // 终审
                        typeId = ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_LAST;
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


        approvalResultMap.put(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST, new ParttimeApprovalResult(resultMap.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST),
                approvalLogMap.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_FIRST))); // 初审

        Map<Integer, ParttimeApproverType> approverTypeMap = parttimeApproverTypeService.findAll();

        CadrePost cadrePost = cadrePostService.getFirstMainCadrePost(cadreId);
        boolean isPrincipal = cadrePost.getIsPrincipal();
        for (ParttimeApproverType approverType : approverTypeMap.values()) {
            ParttimeApproverType parttimeApproverType = approverTypeMap.get(approverType.getId());
            //没选择国境外背景跳过
            if (parttimeApproverType.getType() == ParttimeConstants.PARTTIME_APPROVER_TYPE_FOREIGN && !apply.getBackground()) {
                continue;
            }
            //正职和副职
            if ((isPrincipal && parttimeApproverType.getType() == ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT) ||
               (!isPrincipal && parttimeApproverType.getType() == ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER)) {
                continue;
            }
            if (needApprovalTypeSet.contains(approverType.getId()))
                approvalResultMap.put(approverType.getId(), new ParttimeApprovalResult(resultMap.get(approverType.getId()), approvalLogMap.get(approverType.getId())));
            else
                approvalResultMap.put(approverType.getId(), new ParttimeApprovalResult(-1, null));
        }

        approvalResultMap.put(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_LAST, new ParttimeApprovalResult(resultMap.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_LAST),
                approvalLogMap.get(ParttimeConstants.PARTTIME_APPROVER_TYPE_ID_OD_LAST))); // 终审

        // value: -1不需要审批 0未通过 1通过 null未审批
        return approvalResultMap;
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
            CadreViewExample example = new CadreViewExample();
            example.createCriteria().andUnitIdIn(unitIds);
            List<CadreView> cadreIdList = cadreViewMapper.selectByExample(example);
            for (CadreView record : cadreIdList) {
                cadreIdSet.add(record.getId());
            }
        }

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null) {
            // 其他审批人身份的干部，查找他需要审批的干部
            List<Integer> approvalCadreIds = iParttimeMapper.getApprovalCadreIds(cadre.getId());
            cadreIdSet.addAll(approvalCadreIds);
        }

        return cadreIdSet;
    }

    // 如果是本单位正职，返回单位ID 列表，包括兼职单位
    public List<Integer> getMainPostUnitIds(int userId) {

        List<Integer> unitIds = new ArrayList<>();
        ParttimeApproverType mainPostApproverType = parttimeApproverTypeService.getMainPostApproverType();
        if (mainPostApproverType == null) return unitIds;

        Map<Integer, ParttimeApproverBlackList> blackListMap = findAll(mainPostApproverType.getId());
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null
                && (cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                && blackListMap.get(cadre.getId()) == null) { // 必须是现任干部，且不在黑名单

            if (BooleanUtils.isTrue(cadre.getIsPrincipal())) {
                unitIds.add(cadre.getUnitId());
            }
        }

        return unitIds;
    }

    // 如果是分管校领导，返回分管单位ID列表
    public List<Integer> getLeaderMangerUnitIds(int userId) {

        List<Integer> unitIds = new ArrayList<>();
        ParttimeApproverType leaderApproverType = parttimeApproverTypeService.getLeaderApproverType();
        if (leaderApproverType == null) return unitIds;

        Map<Integer, ParttimeApproverBlackList> blackListMap = findAll(leaderApproverType.getId());

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null && (cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                && blackListMap.get(cadre.getId()) == null) { // 必须是现任干部，且不在黑名单
            MetaType leaderManagerType = CmTag.getMetaTypeByCode("mt_leader_manager");
            unitIds = iLeaderMapper.getLeaderManagerUnitId(cadre.getUserId(), leaderManagerType.getId());
        }
        return unitIds;
    }

    public ParttimeApproverTypeBean getApproverTypeBean(int userId) {

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre == null || (cadre.getStatus() != CadreConstants.CADRE_STATUS_CJ
                && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER)) return null;

        // 本单位正职
        List<Integer> mainPostUnitIds = getMainPostUnitIds(userId);
        // 分管校领导
        List<Integer> leaderUnitIds = getLeaderMangerUnitIds(userId);

        // 其他身份
        Map<Integer, List<Integer>> approverTypeCadreIdListMap = new HashMap<>(); // 本人所属的审批身份及对应的审批的职务属性
        Map<Integer, ParttimeApproverType> approverTypeMap = parttimeApproverTypeService.findAll();
        for (ParttimeApproverType approverType : approverTypeMap.values()) {
            Byte type = approverType.getType();
            if (type != ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT && type != ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER) {
                List<Integer> approvalPostIds = getApprovalPostIds(userId, approverType.getId());
                if (approvalPostIds.size() > 0) {
                    approverTypeCadreIdListMap.put(approverType.getId(), approvalPostIds);
                }
            }
        }

        ParttimeApproverTypeBean approverTypeBean = new ParttimeApproverTypeBean();
        approverTypeBean.setCadre(cadre);
        approverTypeBean.setMainPostUnitIds(mainPostUnitIds);
        approverTypeBean.setManagerLeader(leaderUnitIds.size() > 0);
        approverTypeBean.setLeaderUnitIds(leaderUnitIds);
        approverTypeBean.setApprover(!approverTypeCadreIdListMap.isEmpty());
        approverTypeBean.setApproverTypeCadreIdListMap(approverTypeCadreIdListMap);
        approverTypeBean.setApprovalCadreIdSet(findApprovalCadreIdSet(userId));

        return approverTypeBean;
    }

    // 如果是其他审批身份，返回需要审批的职务属性
    public List<Integer> getApprovalPostIds(int userId, int approverTypeId) {
        List<Integer> postIds = new ArrayList<>();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null) {
            postIds = iParttimeMapper.getApprovalPostIds_approverTypeId(cadre.getId(), approverTypeId);
        }
        return postIds;
    }

    // 判断是否有审批权限（非管理员）
    public boolean canApproval(int userId, int applyId, int approvalTypeId) {

        ParttimeApply apply = parttimeApplyMapper.selectByPrimaryKey(applyId);
        int targetCadreId = apply.getCadreId(); // 待审批的干部

        List<SysUserView> approvers = findApprovers(targetCadreId, approvalTypeId);
        for (SysUserView approver : approvers) {

            if (approver.getId().intValue() == userId) return true;
        }

        return false;
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
        Map<Integer, ParttimeApprovalTdBean> resultMap = new LinkedHashMap<>();

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        Map<Integer, ParttimeApprovalResult> approvalResultMap = getApprovalResultMap(applyId);
        int size = approvalResultMap.size();
        if (size == 0) {
            // 干部没任何身份（需要在后台设置身份）
            return resultMap;
        }
        ParttimeApprovalResult[] vals = approvalResultMap.values().toArray(new ParttimeApprovalResult[size]);
        Integer[] keys = approvalResultMap.keySet().toArray(new Integer[size]);

        ParttimeApprovalResult firstVal = vals[0];
        {
            // 初审td
            ParttimeApprovalTdBean bean = new ParttimeApprovalTdBean();
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
                ParttimeApply apply = parttimeApplyMapper.selectByPrimaryKey(applyId);
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

                ParttimeApprovalResult val = vals[i];
                ParttimeApprovalTdBean bean = new ParttimeApprovalTdBean();
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
                    ParttimeApply apply = parttimeApplyMapper.selectByPrimaryKey(applyId);
                    List<SysUserView> approvers = findApprovers(apply.getCadreId(), bean.getApprovalTypeId());
                    bean.setApproverList(approvers);
                }

                resultMap.put(keys[i], bean);
            }
        }

        {
            // 终审td
            ParttimeApprovalResult lastVal = vals[size - 1];
            ParttimeApprovalTdBean bean = new ParttimeApprovalTdBean();
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
                ParttimeApply apply = parttimeApplyMapper.selectByPrimaryKey(applyId);
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
            CadreView cadre = iCadreMapper.getCadre(apply.getCadreId());
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

    @Transactional
    public void batchUnDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ParttimeApplyExample example = new ParttimeApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        ParttimeApply record = new ParttimeApply();
        record.setIsDeleted(false);
        parttimeApplyMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void doBatchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ParttimeApplyExample example = new ParttimeApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsDeletedEqualTo(true);

        parttimeApplyMapper.deleteByExample(example);
    }

    // 未标记删除的记录，才可以进行审批操作
    @Transactional
    public int doApproval(ParttimeApply record) {

        ParttimeApplyExample example = new ParttimeApplyExample();
        example.createCriteria().andIdEqualTo(record.getId()).andIsDeletedEqualTo(false);

        return parttimeApplyMapper.updateByExampleSelective(record, example);
    }

    // 本年度的申请记录（审批通过的申请）
    public List<ParttimeApply> getAnnualApplyList(int cadreId, int year) {

        ParttimeApplyExample example = new ParttimeApplyExample();
        ParttimeApplyExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        criteria.andIsAgreedEqualTo(true);
        criteria.andApplyTimeBetween(DateUtils.parseDate(year + "-01-01 00:00:00", DateUtils.YYYY_MM_DD),
                DateUtils.parseDate(year + "-12-30 23:59:59", DateUtils.YYYY_MM_DD));
        example.setOrderByClause("create_time desc");

        return parttimeApplyMapper.selectByExample(example);
    }

    // 干部管理员 审批列表
    public Map findApplyList(HttpServletResponse response, Integer cadreId,
                             DateRange _applyDate,
                             // 出行时间范围
                             Byte type, Boolean isModify, Boolean background,
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

        ParttimeApplyExample example = new ParttimeApplyExample();
        ParttimeApplyExample.Criteria criteria = example.createCriteria();
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
                criteria.andApplyTimeGreaterThanOrEqualTo(_applyDate.getStart());
            }

            if (_applyDate.getEnd() != null) {
                criteria.andApplyTimeLessThanOrEqualTo(_applyDate.getEnd());
            }
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (isModify != null) {
            criteria.andIsModifyEqualTo(isModify);
        }
        if (background != null) {
            criteria.andBackgroundEqualTo(background);
        }

        if (export == 1) {
//            apply_export(example, response);
            return null;
        }

        long count = parttimeApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ParttimeApply> applys = parttimeApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

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
        List<ParttimeApply> applys = null;

        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (cadre != null && (cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)) { // 审批人必须是现任干部才有审批权限

            //==============================================
            Map<Integer, List<Integer>> approverTypeUnitIdListMap = new HashMap<Integer, List<Integer>>();
            Map<Integer, List<Integer>> approverTypeCadreIdListMap = new HashMap<Integer, List<Integer>>();

            ParttimeApproverType mainPostApproverType = parttimeApproverTypeService.getMainPostApproverType();
            ParttimeApproverType leaderApproverType = parttimeApproverTypeService.getLeaderApproverType();

            ParttimeApproverTypeBean approverTypeBean = null;
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

            ParttimeApplySearchBean searchBean = new ParttimeApplySearchBean(cadreId, type,
                    _applyDate == null ? null : _applyDate.getStart(), _applyDate == null ? null : _applyDate.getEnd());

            if (status == 0)
                count = iParttimeMapper.countNotApproval(searchBean, approverTypeUnitIdListMap, approverTypeCadreIdListMap);
            if (status == 1)
                count = iParttimeMapper.countHasApproval(searchBean, approverTypeUnitIdListMap, approverTypeCadreIdListMap, userId);

            if (pageNo > 0) {
                pageNo = Math.max(1, pageNo);
                if ((pageNo - 1) * pageSize >= count) {
                    pageNo = Math.max(1, pageNo - 1);
                }
                if (status == 0)
                    applys = iParttimeMapper.selectNotApprovalList(searchBean, approverTypeUnitIdListMap, approverTypeCadreIdListMap,
                            new RowBounds((pageNo - 1) * pageSize, pageSize));
                if (status == 1)
                    applys = iParttimeMapper.selectHasApprovalList(searchBean, approverTypeUnitIdListMap, approverTypeCadreIdListMap, userId,
                            new RowBounds((pageNo - 1) * pageSize, pageSize));
            }
        }
        Map map = new HashMap();
        map.put("applys", applys);
        map.put("commonList", new CommonList(count, pageNo, pageSize));
        return map;
    }
}
