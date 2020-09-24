package service.cet;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cet.*;
import domain.member.MemberApply;
import domain.member.MemberApplyExample;
import domain.party.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.member.MemberApplyMapper;
import persistence.party.OrganizerGroupMapper;
import persistence.party.OrganizerGroupUserMapper;
import persistence.party.OrganizerMapper;
import service.cadre.CadreService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CetProjectObjService extends CetBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CetTrainCourseService cetTrainCourseService;
    @Autowired
    private CetTrainObjService cetTrainObjService;
    @Autowired
    private CetProjectPlanService cetProjectPlanService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;

    @Autowired(required = false)
    protected OrganizerMapper organizerMapper;
    @Autowired(required = false)
    protected OrganizerGroupUserMapper organizerGroupUserMapper;
    @Autowired(required = false)
    protected OrganizerGroupMapper organizerGroupMapper;
    @Autowired(required = false)
    protected MemberApplyMapper memberApplyMapper;


    public CetProjectObj get(int userId, int projectId) {

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andUserIdEqualTo(userId).andProjectIdEqualTo(projectId);
        List<CetProjectObj> records = cetProjectObjMapper.selectByExample(example);

        return records.size() > 0 ? records.get(0) : null;
    }

    // 删除心得体会
    @Transactional
    public void clearWrite(Integer[] ids) {
        for (Integer id : ids) {
            commonMapper.excuteSql("update cet_project_obj set word_write=null, write_file_path=null where id=" + id);
        }
    }

    @Transactional
    public void batchDel(int projectId, Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andIdIn(Arrays.asList(ids));
        cetProjectObjMapper.deleteByExample(example);

        iCetMapper.refreshObjCount(projectId);
        iCetMapper.refreshQuitCount(projectId);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetProjectObj record) {

        record.setIsQuit(null); // 不改变退出状态
        return cetProjectObjMapper.updateByPrimaryKeySelective(record);
    }

    // 培训对象列表
    public List<CetProjectObj> cetProjectObjs(int projectId, Integer traineeTypeId) {

        CetProjectObjExample example = new CetProjectObjExample();
        CetProjectObjExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId).andIsQuitEqualTo(false);
        if (traineeTypeId != null) {
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
        }
        example.setOrderByClause("id asc");

        return cetProjectObjMapper.selectByExample(example);
    }

    public Set<Integer> getSelectedProjectObjUserIdSet(int projectId, int traineeTypeId) {

        Set<Integer> userIdSet = new HashSet<>();

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andProjectIdEqualTo(projectId)
                .andTraineeTypeIdEqualTo(traineeTypeId);
        List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);

        for (CetProjectObj cetProjectObj : cetProjectObjs) {
            userIdSet.add(cetProjectObj.getUserId());
        }

        return userIdSet;
    }

    // 同步参训学员的特定信息
    private void appendTraineeInfo(String typeCode, int userId, CetProjectObj record) {

        SysUserView uv = sysUserService.findById(userId);
        record.setMobile(uv.getMobile());
        record.setEmail(uv.getEmail());

        if (StringUtils.isBlank(typeCode)) return;
        switch (typeCode) {
            // 干部、优秀年轻干部
            case "t_leader":
            case "t_cadre":
            case "t_cadre_kj":
            case "t_reserve":
                // 同步干部信息
                CadreView cv = cadreService.dbFindByUserId(userId);
                if (cv != null) {
                    record.setCadreId(cv.getId());
                    // 如果时任单位及职务、职务属性没有，则同步系统里的最新数据
                    if (StringUtils.isBlank(record.getTitle())) {
                        record.setTitle(cv.getTitle());
                    }
                    record.setAdminLevel(cv.getAdminLevel());
                    if (record.getPostType() == null) {
                        record.setPostType(cv.getPostType());
                    }
                    record.setIsOw(cv.getIsOw());
                    record.setOwGrowTime(cv.getOwGrowTime());
                    record.setDpGrowTime(cv.getDpGrowTime());
                    record.setDpTypeId(cv.getDpTypeId());
                    record.setProPost(cv.getProPost());
                    record.setLpWorkTime(cv.getLpWorkTime());
                    record.setMobile(cv.getMobile());
                    record.setEmail(cv.getEmail());
                    record.setCadreStatus(cv.getStatus());
                    record.setCadreSortOrder(cv.getSortOrder());
                }
                break;
            case "t_party_member":
                PartyMemberViewExample example = new PartyMemberViewExample();
                example.createCriteria().andUserIdEqualTo(userId)
                        .andIsDeletedEqualTo(false);
                List<PartyMemberView> partyMemberViews =
                        partyMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
                if (partyMemberViews.size() > 0) {
                    PartyMemberView partyMemberView = partyMemberViews.get(0);
                    record.setPartyId(partyMemberView.getPartyId());
                    record.setBranchId(partyMemberView.getBranchId());
                    record.setPartyTypeIds(partyMemberView.getTypeIds());
                    record.setPostId(partyMemberView.getPostId());
                    record.setAssignDate(partyMemberView.getAssignDate());
                }
                break;
            case "t_branch_member":
                BranchMemberViewExample example2 = new BranchMemberViewExample();
                example2.createCriteria().andUserIdEqualTo(userId)
                        .andIsDeletedEqualTo(false);
                List<BranchMemberView> branchMemberViews =
                        branchMemberViewMapper.selectByExampleWithRowbounds(example2, new RowBounds(0, 1));
                if (branchMemberViews.size() > 0) {
                    BranchMemberView branchMemberView = branchMemberViews.get(0);
                    record.setPartyId(branchMemberView.getPartyId());
                    record.setBranchId(branchMemberView.getBranchId());
                    /*record.setBranchTypeId(branchMemberView.getTypeId());*/
                    record.setAssignDate(branchMemberView.getAssignDate());
                }
                break;
            case "t_organizer": // 	组织员
                OrganizerExample example3 = new OrganizerExample();
                example3.createCriteria()
                        .andStatusEqualTo(OwConstants.OW_ORGANIZER_STATUS_NOW)
                        .andUserIdEqualTo(userId);
                List<Organizer> organizers = organizerMapper.selectByExampleWithRowbounds(example3, new RowBounds(0, 1));
                if (organizers.size() > 0) {
                    Organizer organizer = organizers.get(0);
                    record.setOrganizerType(organizer.getType());
                    record.setOrganizerPartyId(organizer.getPartyId());

                    OrganizerGroupUserExample example4 = new OrganizerGroupUserExample();
                    example4.createCriteria()
                            .andUserIdEqualTo(userId);
                    List<OrganizerGroupUser> organizerGroupUsers =
                            organizerGroupUserMapper.selectByExampleWithRowbounds(example4, new RowBounds(0, 1));
                    if (organizerGroupUsers.size() > 0) {
                        Integer groupId = organizerGroupUsers.get(0).getGroupId();
                        OrganizerGroup organizerGroup = organizerGroupMapper.selectByPrimaryKey(groupId);
                        if (organizerGroup != null) {
                            record.setOrganizerUnits(organizerGroup.getUnits());
                        }
                    }

                    record.setAssignDate(organizer.getAppointDate());
                }
                break;
            case "t_activist": // 	入党积极分子
            case "t_candidate": // 发展对象
            case "t_grow": // 预备党员
                MemberApplyExample example5 = new MemberApplyExample();
                example5.createCriteria()
                        .andIsRemoveEqualTo(false)
                        .andStageEqualTo(OwConstants.OW_APPLY_STAGE_ACTIVE)
                        .andUserIdEqualTo(userId);
                List<MemberApply> records = memberApplyMapper.selectByExampleWithRowbounds(example5, new RowBounds(0, 1));
                if (records.size() > 0) {
                    MemberApply memberApply = records.get(0);
                    record.setPartyId(memberApply.getPartyId());
                    record.setBranchId(memberApply.getBranchId());
                    record.setActiveTime(memberApply.getActiveTime());
                    record.setCandidateTime(memberApply.getCandidateTime());
                    record.setOwGrowTime(memberApply.getGrowTime());
                }
                break;
        }
    }

    // 更新同步参训学员特定信息
    @Transactional
    public void syncTraineeInfo(int projectId, int traineeTypeId) {

        Map<Integer, CetTraineeType> cetTraineeTypeMap = cetTraineeTypeService.findAll();
        CetTraineeType cetTraineeType = cetTraineeTypeMap.get(traineeTypeId);

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andProjectIdEqualTo(projectId)
                .andTraineeTypeIdEqualTo(traineeTypeId);

        List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);

        for (CetProjectObj cetProjectObj : cetProjectObjs) {

            CetProjectObj record = new CetProjectObj();
            record.setId(cetProjectObj.getId());
            appendTraineeInfo(cetTraineeType.getCode(), cetProjectObj.getUserId(), record);

            updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void addOrUpdate(CetProjectObj record, Integer[] identities) {

        int projectId = record.getProjectId();
        int userId = record.getUserId();
        Integer traineeTypeId = record.getTraineeTypeId();

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);

        Map<Integer, CetTraineeType> cetTraineeTypeMap = cetTraineeTypeService.findAll();

        CetTraineeType cetTraineeType = new CetTraineeType();
        if (traineeTypeId != null && traineeTypeId == 0) {
            cetTraineeType.setId(traineeTypeId);
            cetTraineeType.setName(cetProject.getOtherTraineeType());
        } else {

            cetTraineeType = cetTraineeTypeMap.get(traineeTypeId);
        }

        CetProjectObj cetProjectObj = get(userId, projectId);

        // 检查别的参选人类型中是否已经选择参训对象
        if (cetProjectObj != null && cetProjectObj.getTraineeTypeId().intValue() != traineeTypeId) {
            SysUserView uv = cetProjectObj.getUser();
            int _traineeTypeId = cetProjectObj.getTraineeTypeId();

            if(record.getId()==null || record.getId().intValue()!=cetProjectObj.getId()) {

                String _cetTraineeType = "";
                if(_traineeTypeId==0){
                    _cetTraineeType = cetProjectObj.getOtherTraineeType();
                }else{
                    _cetTraineeType = cetTraineeTypeMap.get(_traineeTypeId).getName();
                }

                throw new OpException("参训人{0}（工号：{1}）已经是培训对象（类型：{2}）", uv.getRealname(), uv.getCode(),
                        _cetTraineeType);
            }
        }

        record.setOtherTraineeType(cetTraineeType.getName());
        record.setIdentity(StringUtils.trimToNull(StringUtils.join(identities, ",")) == null
                ? "" : "," + StringUtils.join(identities, ",") + ",");

        appendTraineeInfo(cetTraineeType.getCode(), userId, record);

        if (cetProjectObj != null || record.getId()!=null) {
            record.setId(cetProjectObj.getId());
            cetProjectObjMapper.updateByPrimaryKeySelective(record);
        } else {
            cetProjectObjMapper.insertSelective(record);
        }

        sysApprovalLogService.add(record.getId(), record.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                (cetProjectObj==null?"添加":"更新") + "培训对象", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                "新建");

        iCetMapper.refreshObjCount(projectId);
    }

    // 退出
    @Transactional
    public void quit(int projectId, boolean isQuit, Integer[] ids) {

        CetProjectObj record = new CetProjectObj();
        record.setIsQuit(isQuit);

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andProjectIdEqualTo(projectId)
                .andIdIn(Arrays.asList(ids)).andIsQuitEqualTo(!isQuit);
        cetProjectObjMapper.updateByExampleSelective(record, example);

        iCetMapper.refreshQuitCount(projectId);
    }

    // 设置为必选学员/退课
    // opType  1：设置为必选 2：设置为可选 3: 选课  4：退课
    public void apply(int projectId, int traineeTypeId, Integer[] objIds, byte opType, int trainCourseId) {

        List<CetProjectObj> cetProjectObjs = null;
        if (objIds == null || objIds.length == 0) {
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andProjectIdEqualTo(projectId)
                    .andTraineeTypeIdEqualTo(traineeTypeId).andIsQuitEqualTo(false);
            cetProjectObjs = cetProjectObjMapper.selectByExample(example);
        } else {
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andTraineeTypeIdEqualTo(traineeTypeId)
                    .andIdIn(Arrays.asList(objIds));
            cetProjectObjs = cetProjectObjMapper.selectByExample(example);
        }

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        // 已选课学员
        Map<Integer, CetTrainObjView> trainees = cetTrainCourseService.findTrainees(trainCourseId);

        for (CetProjectObj cetProjectObj : cetProjectObjs) {

            int userId = cetProjectObj.getUserId();
            int objId = cetProjectObj.getId();

            CetTrainObjView ctov = trainees.get(userId);
            if (ctov == null) { // 目前还从未选课?
                if (opType == 1) {
                    cetTrainObjService.applyItem(userId, trainCourseId, true, true, false, "设为必选[管理员]");
                } else if (opType == 3) {
                    cetTrainObjService.applyItem(userId, trainCourseId, true, true, true, "选课[管理员]");
                }
            } else {
                /*if (ctc.getIsFinished()) {
                    continue;
                }*/

                if (!ctov.getCanQuit()) { // 目前是必选

                    if (opType == 2) { // 必选->设为可选

                        CetTrainObj record = new CetTrainObj();
                        record.setId(ctov.getId());
                        record.setCanQuit(true);
                        cetTrainObjMapper.updateByPrimaryKeySelective(record);

                        sysApprovalLogService.add(objId, userId,
                                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                                "改为可选[管理员]("+cetTrainCourse.getName()+")",
                                SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                                cetTrainCourse.getName());
                    } else if (opType == 4) { // 必选->退课

                        cetTrainObjService.applyItem(userId, trainCourseId, false, true, true, "设为可选[管理员]");
                    }
                } else { // 目前是可选

                    if (opType == 4) { // 可选->退课
                        // 什么情况下都要退课？
                        cetTrainObjService.applyItem(userId, trainCourseId, false, true, true, "退课[管理员]");
                    } else if (opType == 3) { // 可选->选课

                        if (ctov.getChooseUserId() == null) { // 未选课的情况下才选课
                            cetTrainObjService.applyItem(userId, trainCourseId, true, true, true, "选课[管理员]");
                        }
                    } else if (opType == 1) {  // 可选->设为必选

                        CetTrainObj record = new CetTrainObj();
                        record.setId(ctov.getId());
                        record.setCanQuit(false);
                        record.setChooseTime(new Date());
                        record.setChooseUserId(ShiroHelper.getCurrentUserId());
                        cetTrainObjMapper.updateByPrimaryKeySelective(record);

                        sysApprovalLogService.add(objId, userId,
                                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                                "改为必选[管理员]("+cetTrainCourse.getName()+")", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                                cetTrainCourse.getName());
                    }
                }
            }
        }
    }

    // 导入选课情况
    //@Transactional 此处不可使用事务
    public Map<String, Object> imports(List<Map<Integer, String>> xlsRows, int projectId, int trainCourseId) {

        int success = 0;
        List<Map<Integer, String>> failedXlsRows = new ArrayList<>();
        for (Map<Integer, String> xlsRow : xlsRows) {

            String code = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(code)) continue;
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {
                failedXlsRows.add(xlsRow);
                continue;
            }
            int userId = uv.getId();
            CetProjectObj cetProjectObj = get(userId, projectId);
            if (cetProjectObj == null || BooleanUtils.isTrue(cetProjectObj.getIsQuit())) {
                failedXlsRows.add(xlsRow);
                continue;
            }
            boolean isQuit = StringUtils.equals(xlsRow.get(2), "退课"); // 留空默认选课
            boolean canQuit = StringUtils.equals(xlsRow.get(3), "是"); // 留空默认不允许退课，即必选

            try {
                if (isQuit) {
                    cetTrainObjService.applyItem(userId, trainCourseId, false, true, true, "导入退课[管理员]");
                } else {
                    cetTrainObjService.applyItem(userId, trainCourseId, true, true, canQuit, "导入选课[管理员]");
                }
                success++;
            } catch (Exception ex) {
                logger.warn("导入选课情况出错：" + ex.getMessage());
                failedXlsRows.add(xlsRow);
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("failedXlsRows", failedXlsRows);
        return resultMap;
    }

    // 获取培训对象在一个培训方案中已完成的学时（非实时）
    public BigDecimal getPlanFinishPeriod(int planId, int objId) {

        CetProjectObjPlanExample example = new CetProjectObjPlanExample();
        example.createCriteria().andPlanIdEqualTo(planId).andObjIdEqualTo(objId);
        List<CetProjectObjPlan> cetProjectObjPlans = cetProjectObjPlanMapper
                .selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (cetProjectObjPlans.size() == 1) ? cetProjectObjPlans.get(0).getFinishPeriod() : null;
    }

    // 获取培训对象在一个培训方案中已完成的学时（实时统计）
    public BigDecimal getRealObjPlanFinishPeriod(int planId, byte planType, int objId) {

        switch (planType) {
            case CetConstants.CET_PROJECT_PLAN_TYPE_OFFLINE: // 线下培训
            case CetConstants.CET_PROJECT_PLAN_TYPE_ONLINE: // 线上培训
            case CetConstants.CET_PROJECT_PLAN_TYPE_PRACTICE: // 实践教学
                return iCetMapper.getTrainObjFinishPeriod(planId, objId);

            case CetConstants.CET_PROJECT_PLAN_TYPE_SELF: // 自主学习
                return iCetMapper.getSelfFinishPeriod(planId, objId);

            case CetConstants.CET_PROJECT_PLAN_TYPE_SPECIAL: // 上级网上专题班
                return iCetMapper.getSpecialPlanFinishPeriod(planId, objId);

            case CetConstants.CET_PROJECT_PLAN_TYPE_GROUP: // 分组研讨
                return iCetMapper.getGroupFinishPeriod(planId, objId);

            case CetConstants.CET_PROJECT_PLAN_TYPE_WRITE: // 撰写心得体会
                return iCetMapper.getWriteFinishPeriod(planId, objId);
        }

        return null;
    }

    // 获取培训对象的已完成学时(实时）
    // <planId, BigDecimal>  planId=0是汇总
    public Map<Integer, BigDecimal> getRealObjFinishPeriodMap(int projectId, int objId) {

        Map<Integer, BigDecimal> periodMap = new LinkedHashMap<>();

        Map<Integer, CetProjectPlan> cetProjectPlanMap = cetProjectPlanService.findAll(projectId);
        BigDecimal finishPeriod = BigDecimal.ZERO;
        for (CetProjectPlan cetProjectPlan : cetProjectPlanMap.values()) {

            int planId = cetProjectPlan.getId();
            byte type = cetProjectPlan.getType();
            BigDecimal planFinishPeriod = getRealObjPlanFinishPeriod(planId, type, objId);
            periodMap.put(cetProjectPlan.getId(), NumberUtils.trimToZero(planFinishPeriod));
            if (planFinishPeriod != null) {
                finishPeriod = finishPeriod.add(planFinishPeriod);
            }
        }

        if(cetProjectPlanMap.size()==0) { // 无培训方案的总学时，针对二级党委培训
            finishPeriod = iCetMapper.getTrainObjFinishPeriod(null, objId);
        }
        periodMap.put(0, NumberUtils.trimToZero(finishPeriod)); // 汇总

        return periodMap;
    }

    // 归档某个人的已完成学时 汇总数据
    public void archiveProjectObj(int projectId, int objId) {

        Map<Integer, BigDecimal> periodMap = getRealObjFinishPeriodMap(projectId, objId);

        {
            CetProjectObjPlanExample example = new CetProjectObjPlanExample();
            example.createCriteria().andObjIdEqualTo(objId);
            cetProjectObjPlanMapper.deleteByExample(example);
        }

        for (Map.Entry<Integer, BigDecimal> entry : periodMap.entrySet()) {
            int planId = entry.getKey();
            BigDecimal finishPeriod = entry.getValue();

            if (planId == 0) {
                // 总学时同时保存进入培训对象
                CetProjectObj record = new CetProjectObj();
                record.setId(objId);
                record.setFinishPeriod(finishPeriod);
                cetProjectObjMapper.updateByPrimaryKeySelective(record);
            }

            CetProjectObjPlan record = new CetProjectObjPlan();
            record.setPlanId(planId);
            record.setObjId(objId);
            record.setFinishPeriod(finishPeriod);

            cetProjectObjPlanMapper.insertSelective(record);
        }
    }

    // 归档培训班所有培训对象的已完成学时数
    public void archiveProject(int projectId) {

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);
        for (CetProjectObj cetProjectObj : cetProjectObjs) {

            archiveProjectObj(cetProjectObj.getProjectId(), cetProjectObj.getId());
        }

        CetProject record = new CetProject();
        record.setId(projectId);
        record.setHasArchive(true);
        cetProjectMapper.updateByPrimaryKeySelective(record);
    }

    // 获取培训对象的已完成学时分项 Map<planId, period>
    public Map<Integer, BigDecimal> getObjPlanFinishPeriodMap(int objId) {

        CetProjectObjPlanExample example = new CetProjectObjPlanExample();
        example.createCriteria().andObjIdEqualTo(objId);

        List<CetProjectObjPlan> cetProjectObjPlans = cetProjectObjPlanMapper.selectByExample(example);
        Map<Integer, BigDecimal> periodMap = new HashMap<>();

        for (CetProjectObjPlan cetProjectObjPlan : cetProjectObjPlans) {

            periodMap.put(cetProjectObjPlan.getPlanId(), cetProjectObjPlan.getFinishPeriod());
        }

        return periodMap;
    }

    // 设置应完成学时
    @Transactional
    public void setShouldFinishPeriod(int projectId, Integer[] ids, BigDecimal shouldFinishPeriod) {

        if (ids == null || ids.length == 0) {
            List<Integer> objIds = iCetMapper.getCetProjectObjIds(projectId);
            ids = objIds.toArray(new Integer[]{});
        }

        if (ids == null || ids.length == 0) return;

        if (shouldFinishPeriod != null) {

            CetProjectObj record = new CetProjectObj();
            record.setShouldFinishPeriod(shouldFinishPeriod);

            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cetProjectObjMapper.updateByExampleSelective(record, example);
        } else {
            commonMapper.excuteSql("update cet_project_obj set should_finish_period=null where id in("
                    + StringUtils.join(ids, ",") + ")");
        }
    }

    // 自动结业
    @Transactional
    public void autoGraduate(int projectId) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        BigDecimal requirePeriod = cetProject.getRequirePeriod();

        List<Integer> finishIds = new ArrayList<>();
        {
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andProjectIdEqualTo(projectId).andIsQuitEqualTo(false);
            List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);
            for (CetProjectObj cetProjectObj : cetProjectObjs) {

                BigDecimal finishPeriod = cetProjectObj.getFinishPeriod();

                if (requirePeriod != null && finishPeriod != null && requirePeriod.compareTo(finishPeriod) <= 0) {
                    finishIds.add(cetProjectObj.getId());
                }
            }
        }
        if (finishIds.size() > 0) {
            CetProjectObj record = new CetProjectObj();
            record.setIsGraduate(true);
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andIdIn(finishIds);
            cetProjectObjMapper.updateByExampleSelective(record, example);
        }
    }

    // 手动结业
    @Transactional
    public void forceGraduate(Integer[] ids, boolean isGraduate, String remark) {

        if (ids == null || ids.length == 0) return;

        CetProjectObj record = new CetProjectObj();
        record.setIsGraduate(isGraduate);

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectObjMapper.updateByExampleSelective(record, example);

        for (Integer id : ids) {

            CetProjectObj obj = cetProjectObjMapper.selectByPrimaryKey(id);
            sysApprovalLogService.add(obj.getId(), obj.getUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                        "更新结业状态："+ (isGraduate?"结业":"未结业"), SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        remark);
        }
    }

    //批量导入CetProjectObj培训对象
    @Transactional
    public int importCetProjectObj(int projectId, List<CetProjectObj> records) {

        int addCount = 0;
        for (CetProjectObj _record : records) {

            Integer userId = _record.getUserId();
            Integer traineeTypeId = _record.getTraineeTypeId();
            CetProjectObj _cetProjectObj = get(userId, projectId, traineeTypeId);

            if (_cetProjectObj == null) {

                addCount++;
                CetTraineeType cetTraineeType = new CetTraineeType();
                if (traineeTypeId != null && traineeTypeId == 0) {
                    cetTraineeType.setId(traineeTypeId);
                    cetTraineeType.setName(cetProjectMapper.selectByPrimaryKey(projectId).getOtherTraineeType());
                } else {
                    Map<Integer, CetTraineeType> cetTraineeTypeMap = cetTraineeTypeService.findAll();
                    cetTraineeType = cetTraineeTypeMap.get(traineeTypeId);
                }

                // 检查别的参选人类型中是否已经选择参训对象
                {
                    CetProjectObjExample example = new CetProjectObjExample();
                    example.createCriteria().andProjectIdEqualTo(projectId)
                            .andTraineeTypeIdNotEqualTo(traineeTypeId)
                            .andUserIdEqualTo(userId);

                    List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
                    if (cetProjectObjs.size() > 0) {
                        CetProjectObj cetProjectObj = cetProjectObjs.get(0);
                        SysUserView uv = cetProjectObj.getUser();

                        throw new OpException("参训人{0}（工号：{1}）已经是培训对象（{2}）", uv.getRealname(), uv.getCode(),
                                cetTraineeType.getName());
                    }
                }

                appendTraineeInfo(cetTraineeType.getCode(), userId, _record);
                cetProjectObjMapper.insertSelective(_record);

                sysApprovalLogService.add(_record.getId(), _record.getUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                        "添加培训对象", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        "批量导入");
            } else {

                _record.setId(_cetProjectObj.getId());
                cetProjectObjMapper.updateByPrimaryKeySelective(_record);

                sysApprovalLogService.add(_record.getId(), _record.getUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                        "更新培训对象", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                        "批量导入");
            }
        }

        iCetMapper.refreshObjCount(projectId);

        return addCount;
    }

    //得到唯一的培训对象CetProjectObj
    public CetProjectObj get(int userId, int projectId, int traineeTypeId) {

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andUserIdEqualTo(userId).andProjectIdEqualTo(projectId).andTraineeTypeIdEqualTo(traineeTypeId);
        List<CetProjectObj> cetTrainees = cetProjectObjMapper.selectByExample(example);

        return cetTrainees.size() > 0 ? cetTrainees.get(0) : null;
    }
}
