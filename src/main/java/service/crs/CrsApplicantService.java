package service.crs;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantExample;
import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import domain.crs.CrsPost;
import domain.modify.ModifyCadreAuth;
import mixin.MixinUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.cadre.CadreInfoCheckService;
import service.cadre.CadreService;
import service.modify.ModifyCadreAuthService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CrsConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CrsApplicantService extends BaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ModifyCadreAuthService modifyCadreAuthService;
    @Autowired
    private CrsApplyRuleService crsApplyRuleService;

    // 获得报名人员
    public List<CrsApplicantView> getCrsApplicants(int postId){

        CrsApplicantViewExample example = new CrsApplicantViewExample();
        example.createCriteria().andPostIdEqualTo(postId)
                .andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);

        return crsApplicantViewMapper.selectByExample(example);
    }

    // 获取资格审核推荐通过人员列表
    public List<CrsApplicantView> getPassedCrsApplicants(int postId) {

        CrsApplicantViewExample example = new CrsApplicantViewExample();
        example.createCriteria().andPostIdEqualTo(postId)
                .andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT)
                .andIsRequireCheckPassEqualTo(true);

        return crsApplicantViewMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int postId, int userId) {

        CrsApplicantExample example = new CrsApplicantExample();
        CrsApplicantExample.Criteria criteria = example.createCriteria()
                .andPostIdEqualTo(postId).andUserIdEqualTo(userId)
                .andStatusIn(Arrays.asList(CrsConstants.CRS_APPLICANT_STATUS_SAVE,
                        CrsConstants.CRS_APPLICANT_STATUS_SUBMIT));

        if (id != null) criteria.andIdNotEqualTo(id);

        return crsApplicantMapper.countByExample(example) > 0;
    }

    // 应聘报名
    @Transactional
    public void apply(Integer id, int postId, byte status, String report, int userId) {

        if(!CadreInfoCheckService.perfectCadreInfo(userId)){
            throw new OpException("未通过干部信息完整性校验。");
        }

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        if(crsPost==null ||crsPost.getStatus() ==CrsConstants.CRS_POST_STATUS_FINISH){
            throw new OpException("岗位{0}应聘已经结束。", crsPost==null?"":crsPost.getName());
        }
        if(crsPost.getSwitchStatus() != CrsConstants.CRS_POST_ENROLL_STATUS_OPEN){
            throw new OpException("岗位{0}应聘还未开始。", crsPost==null?"":crsPost.getName());
        }

        Date meetingTime = crsPost.getMeetingTime();
        Date reportDeadline = crsPost.getReportDeadline();

        if(reportDeadline!=null && DateUtils.compareDate(new Date(), reportDeadline)){
            throw new OpException("招聘会于{0}召开，{1}之后不可修改应聘材料。",
                    DateUtils.formatDate(meetingTime, "yyyy年MM月dd日 HH点"),
                    DateUtils.formatDate(reportDeadline, "yyyy年MM月dd日 HH点"));
        }

        if(idDuplicate(id, postId, userId)){
            throw new OpException("岗位{0}重复应聘。", crsPost==null?"":crsPost.getName());
        }

        if(status!=CrsConstants.CRS_APPLICANT_STATUS_SAVE
                && status!=CrsConstants.CRS_APPLICANT_STATUS_SUBMIT){
            throw new OpException("状态异常。");
        }

        if(id==null) {

            // 检查报名规则
            if(!crsApplyRuleService.canApply(userId, postId)){
                throw new OpException("操作失败：不符合报名规则。");
            }

            CrsApplicant record = new CrsApplicant();
            record.setPostId(postId);
            record.setUserId(userId);
            record.setReport(report);
            record.setEnrollTime(new Date());
            record.setIsRecommend(false);
            record.setInfoCheckStatus(CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_INIT);
            record.setRequireCheckStatus(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT); //
            record.setIsQuit(false);
            record.setStatus(status);
            crsApplicantMapper.insertSelective(record);

            sysApprovalLogService.add(record.getId(), record.getUserId(),
                    (userId == ShiroHelper.getCurrentUserId()) ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                    "应聘报名", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false));
        }else{

            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(id);
            Assert.isTrue(crsApplicant!=null && crsApplicant.getPostId()==postId
                    && crsApplicant.getUserId()==userId, "数据异常。");

            CrsApplicant record = new CrsApplicant();
            record.setId(id);
            record.setReport(report);
            if(status==CrsConstants.CRS_APPLICANT_STATUS_SUBMIT)
                record.setStatus(status);

            crsApplicantMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(crsApplicant.getId(), crsApplicant.getUserId(),
                    (userId == ShiroHelper.getCurrentUserId()) ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                    "更新工作设想和预期目标", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false));
        }
    }

    // 返回干部的某个岗位的应聘报名记录（正常状态，包含暂存或已提交的报名）
    public CrsApplicant getAvaliable(int postId, Integer userId){

        CrsApplicantExample example = new CrsApplicantExample();
        CrsApplicantExample.Criteria criteria = example.createCriteria()
                .andPostIdEqualTo(postId).andUserIdEqualTo(userId)
                .andStatusIn(Arrays.asList(CrsConstants.CRS_APPLICANT_STATUS_SAVE,
                        CrsConstants.CRS_APPLICANT_STATUS_SUBMIT));
        List<CrsApplicant> crsApplicants = crsApplicantMapper.selectByExample(example);
        if(crsApplicants.size()==0 || crsApplicants.size()>1) return null;

        return crsApplicants.get(0);
    }

    // 退出竞聘
    @Transactional
    public void quit(int postId, Integer userId) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        Date meetingTime = crsPost.getMeetingTime();
        Date quitDeadline = crsPost.getQuitDeadline();

        if(quitDeadline!=null && DateUtils.compareDate(new Date(), quitDeadline)){
            throw new OpException("招聘会于{0}召开，{1}之后不可退出。",
                    DateUtils.formatDate(meetingTime, "yyyy年MM月dd日 HH点"),
                    DateUtils.formatDate(quitDeadline, "yyyy年MM月dd日 HH点"));
        }

        CrsApplicant crsApplicant = getAvaliable(postId, userId);
        if(crsApplicant==null || crsApplicant.getStatus()!=CrsConstants.CRS_APPLICANT_STATUS_SUBMIT)
            throw new OpException("干部应聘状态异常");

        CrsApplicant record = new CrsApplicant();
        record.setId(crsApplicant.getId());
        record.setIsQuit(true);

        crsApplicantMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(record.getId(), userId,
                (userId.intValue()==ShiroHelper.getCurrentUserId())?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF
                        :SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "退出竞聘", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 重新报名
    @Transactional
    public void reApply(int postId, Integer userId) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        if(crsPost.getSwitchStatus() != CrsConstants.CRS_POST_ENROLL_STATUS_OPEN){
            throw new OpException("岗位{0}应聘未开始。", crsPost==null?"":crsPost.getName());
        }

        CrsApplicant crsApplicant = getAvaliable(postId, userId);
        if(crsApplicant==null || crsApplicant.getStatus()!=CrsConstants.CRS_APPLICANT_STATUS_SUBMIT)
            throw new OpException("干部应聘状态异常");

        CrsApplicant record = new CrsApplicant();
        record.setId(crsApplicant.getId());
        record.setIsQuit(false);

        crsApplicantMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(record.getId(), userId,
                (userId.intValue()==ShiroHelper.getCurrentUserId())?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF
                        :SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "重新报名", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 调整岗位
    /*@Transactional
    public void adjust(Integer[] applyPostIds, Integer[] selectablePostIds, Integer confirmUserId) {

        if(applyPostIds == null || applyPostIds.length==0)  throw new OpException("请选择已报名岗位。");
        if(selectablePostIds == null || selectablePostIds.length==0)  throw new OpException("请选择调整后的岗位。");

        // 删除已报名岗位
        for (Integer applyPostId : applyPostIds) {

            CrsApplicant avaliable = getAvaliable(applyPostId, confirmUserId);
            Assert.isTrue(avaliable!=null && avaliable.getStatus()==CrsConstants.CRS_APPLICANT_STATUS_SUBMIT
                    && avaliable.getUserId().intValue() == confirmUserId, "参数错误");

            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(applyPostId);
            Assert.isTrue(crsPost!=null && avaliable.getUserId().intValue() == confirmUserId, "参数错误");

            CrsApplicant record = new CrsApplicant();
            record.setId(avaliable.getId());
            record.setStatus(CrsConstants.CRS_APPLICANT_STATUS_DELETE);
            crsApplicantMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(applyPostId, confirmUserId,
                    (confirmUserId.intValue()==ShiroHelper.getCurrentUserId())?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF
                            :SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                    "调整岗位(调整前的岗位)", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }

        // 添加岗位
        for (Integer selectablePostId : selectablePostIds) {

            // 。。。待修改
            apply(selectablePostId, null, confirmUserId);

            sysApprovalLogService.add(selectablePostId, confirmUserId,
                    (confirmUserId.intValue()==ShiroHelper.getCurrentUserId())?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF
                            :SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                    "调整岗位(调整后的岗位)", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }

        CrsApplicantAdjust adjust = new CrsApplicantAdjust();
        adjust.setUserId(confirmUserId);
        adjust.setAdjustTime(new Date());
        adjust.setPrePostIds(StringUtils.join(applyPostIds, ","));
        adjust.setAfterPostIds(StringUtils.join(selectablePostIds, ","));
        adjust.setOpUserId(ShiroHelper.getCurrentUserId());
        adjust.setIp(ContextHelper.getRealIp());
        crsApplicantAdjustMapper.insertSelective(adjust);
    }*/

    @Transactional
    public void updateByPrimaryKeySelective(CrsApplicant record) {

        CrsApplicant oldRecord = crsApplicantMapper.selectByPrimaryKey(record.getId());

        //Assert.isTrue(!idDuplicate(record.getId(), record.getPostId(), record.getUserId()), "报名重复");
        record.setPostId(null);
        record.setUserId(null);
        crsApplicantMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(record.getId(), record.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "更新报名人员", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                JSONUtils.toString(oldRecord, MixinUtils.baseMixins(), false));
    }

    // 开始采集信息
    @Transactional
    public int start() {

        int userId = ShiroHelper.getCurrentUserId();
        CadreView cv = cadreService.dbFindByUserId(userId);

        // 如果是 干部、考察对象或后备干部，则直接返回干部ID
        if(ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CADRE,
                RoleConstants.ROLE_CADREINSPECT, RoleConstants.ROLE_CADRERESERVE)){
            return cv.getId();
        }

        int cadreId;
        if(cv!=null ){
            cadreId = cv.getId();
            Assert.isTrue(cv.getStatus()==SystemConstants.CADRE_STATUS_RECRUIT, "应聘干部状态异常");
        }else{
            // 普通教师 第一次访问的情况，需要先初始化 应聘干部
            Cadre record  = new Cadre();
            record.setUserId(userId);
            record.setStatus(SystemConstants.CADRE_STATUS_RECRUIT);
            cadreService.insertSelective(record);

            cadreId = record.getId();
        }

        if(ShiroHelper.lackRole(RoleConstants.ROLE_CADRERECRUIT)) {

            // 为普通教师添加应聘干部角色
            sysUserService.addRole(userId, RoleConstants.ROLE_CADRERECRUIT);
            ShiroHelper.refreshRoles();
        }

        List<ModifyCadreAuth> modifyCadreAuths = modifyCadreAuthService.findAll(cadreId);
        if(modifyCadreAuths!=null && modifyCadreAuths.size()>0){

            ModifyCadreAuth modifyCadreAuth = modifyCadreAuths.get(0);
            ModifyCadreAuth record = new ModifyCadreAuth();
            record.setId(modifyCadreAuth.getId());
            record.setCadreId(cadreId);
            record.setIsUnlimited(true);
            modifyCadreAuthService.updateByPrimaryKeySelective(record);
        }else{
            ModifyCadreAuth record = new ModifyCadreAuth();
            record.setCadreId(cadreId);
            record.setIsUnlimited(true);
            record.setAddTime(new Date());
            record.setAddUserId(userId);
            record.setAddIp(ContextHelper.getRealIp());

            modifyCadreAuthService.insertSelective(record);
        }

        return cadreId;
    }
}
