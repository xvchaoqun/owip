package service.crs;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.crs.*;
import domain.modify.ModifyCadreAuth;
import mixin.MixinUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.cadre.CadreInfoCheckService;
import service.cadre.CadreService;
import service.modify.ModifyCadreAuthService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.CrsConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import java.util.*;

@Service
public class CrsApplicantService extends CrsBaseMapper {

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
    @Autowired
    @Lazy
    private CrsShortMsgService crsShortMsgService;

    // 是否可以补报名
    public boolean canAfterApply(int userId, int postId){

        Set<Integer> canAfterApplyPostIdSet = new HashSet<>(iCrsMapper.canAfterApplyPostIds(userId));
        return canAfterApplyPostIdSet.contains(postId);
    }

    // 获取报名权限 （1：报名已结束， 2： 报名未开始  0： 可以报名）
    public int getPostApplyStatus(CrsPost crsPost, int userId){

        byte switchStatus = crsPost.getSwitchStatus();
        if( switchStatus != CrsConstants.CRS_POST_ENROLL_STATUS_OPEN){
            if(switchStatus == CrsConstants.CRS_POST_ENROLL_STATUS_CLOSED) {
                if(!canAfterApply(userId, crsPost.getId()))
                    return 1;
            }else {
                return 2;
            }
        }

        return 0;
    }

    // 获得报名人员（未退出的）
    public List<CrsApplicantView> getCrsApplicants(int postId){

        CrsApplicantViewExample example = new CrsApplicantViewExample();
        example.createCriteria().andPostIdEqualTo(postId)
                .andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT).andIsQuitEqualTo(false);

        return crsApplicantViewMapper.selectByExample(example);
    }

    // 获取资格审核推荐通过人员列表（不包含退出）
    public List<CrsApplicantView> getPassedCrsApplicants(int postId) {

        CrsApplicantViewExample example = new CrsApplicantViewExample();
        example.createCriteria().andPostIdEqualTo(postId)
                .andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT)
                .andIsRequireCheckPassEqualTo(true).andIsQuitEqualTo(false);

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

    // 保存应聘材料
    @Transactional
    public void apply(Integer applicantId, int postId, byte status, String career, String report, int userId) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        if(crsPost==null ||crsPost.getStatus() ==CrsConstants.CRS_POST_STATUS_FINISH){
            throw new OpException("岗位{0}应聘已经结束。", crsPost==null?"":crsPost.getName());
        }

        int postApplyStatus = getPostApplyStatus(crsPost, userId);
        if(postApplyStatus==1){
            if(status==CrsConstants.CRS_APPLICANT_STATUS_SUBMIT) { // 报名结束后，不可提交报名，但还可以修改报名材料
                throw new OpException("岗位{0}应聘报名已结束。", crsPost == null ? "" : crsPost.getName());
            }
        }else if(postApplyStatus == 2){
            throw new OpException("岗位{0}应聘还未开始。", crsPost == null ? "" : crsPost.getName());
        }

        Date meetingTime = crsPost.getMeetingTime();
        Date reportDeadline = crsPost.getReportDeadline();

        if(reportDeadline!=null
                && DateUtils.compareDate(new Date(), reportDeadline)
                && !canAfterApply(userId, postId)){
            throw new OpException("上传材料截止时间为{0}，现在时间已过，不可退出。",
                    DateUtils.formatDate(reportDeadline, "yyyy年MM月dd日 HH:mm"));
        }

        if(idDuplicate(applicantId, postId, userId)){
            throw new OpException("岗位{0}重复应聘。", crsPost==null?"":crsPost.getName());
        }

        if(applicantId==null) {

            if (!CadreInfoCheckService.perfectCadreInfo(userId)) {
                throw new OpException("未通过干部信息完整性校验。");
            }

            // 检查报名规则
            if(!crsApplyRuleService.canApply(userId, postId)){
                throw new OpException("操作失败：不符合报名规则。");
            }

            CrsApplicantWithBLOBs record = new CrsApplicantWithBLOBs();
            record.setPostId(postId);
            record.setUserId(userId);
            record.setReport(report);
            record.setCareer(career);
            record.setEnrollTime(new Date());
            record.setIsRecommend(false);
            record.setInfoCheckStatus(CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_INIT);
            record.setRequireCheckStatus(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT); //
            record.setIsQuit(false);
            record.setStatus(status);

            crsApplicantMapper.insertSelective(record);
            applicantId = record.getId();

            sysApprovalLogService.add(record.getId(), record.getUserId(),
                    (userId == ShiroHelper.getCurrentUserId()) ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                    "应聘报名", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false));

        }else{

            CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
            Assert.isTrue(crsApplicant != null && crsApplicant.getPostId() == postId
                    && crsApplicant.getUserId() == userId, "数据异常。");

            // 只要报名成功了，上传PPT的时候无需再次检查是否信息都全。只要报名成功了，就可以上传PPT。
            if (crsApplicant.getStatus() == CrsConstants.CRS_APPLICANT_STATUS_SAVE
                    && !CadreInfoCheckService.perfectCadreInfo(userId)) {
                throw new OpException("未通过干部信息完整性校验。");
            }

            CrsApplicantWithBLOBs record = new CrsApplicantWithBLOBs();
            record.setId(applicantId);
            record.setReport(report);
            record.setCareer(career);
            if(status==CrsConstants.CRS_APPLICANT_STATUS_SUBMIT){
                record.setStatus(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);
            }
            crsApplicantMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(crsApplicant.getId(), crsApplicant.getUserId(),
                    (userId == ShiroHelper.getCurrentUserId()) ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                    (status==CrsConstants.CRS_APPLICANT_STATUS_SUBMIT?"提交":"更新")+"应聘材料", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(record, MixinUtils.baseMixins(), false));
        }

        if(status==CrsConstants.CRS_APPLICANT_STATUS_SUBMIT) {
            // 短信通知管理员
            crsShortMsgService.sendApplySubmitMsgToAdmin(applicantId, ContextHelper.getRealIp());

            // 更新补报状态为完成（如果存在）
            CrsApplyUser _record = new CrsApplyUser();
            _record.setStatus(CrsConstants.CRS_APPLY_USER_STATUS_FINISH);
            CrsApplyUserExample example = new CrsApplyUserExample();
            example.createCriteria().andPostIdEqualTo(postId).andUserIdEqualTo(userId);
            crsApplyUserMapper.updateByExampleSelective(_record, example);
        }
    }

    // 返回干部的某个岗位的应聘报名记录（正常状态，包含暂存或已提交的报名）
    public CrsApplicantWithBLOBs getAvaliable(int postId, Integer userId){

        CrsApplicantExample example = new CrsApplicantExample();
        CrsApplicantExample.Criteria criteria = example.createCriteria()
                .andPostIdEqualTo(postId).andUserIdEqualTo(userId)
                .andStatusIn(Arrays.asList(CrsConstants.CRS_APPLICANT_STATUS_SAVE,
                        CrsConstants.CRS_APPLICANT_STATUS_SUBMIT));
        List<CrsApplicantWithBLOBs> crsApplicants = crsApplicantMapper.selectByExampleWithBLOBs(example);
        if(crsApplicants.size()==0 || crsApplicants.size()>1) return null;

        return crsApplicants.get(0);
    }

    // 退出竞聘
    @Transactional
    public void quit(int postId, Integer userId, String quitProof) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        // Date meetingTime = crsPost.getMeetingTime();
        Date quitDeadline = crsPost.getQuitDeadline();

        if(!ShiroHelper.isPermitted("crsPost:list")) {
            if (quitDeadline != null && DateUtils.compareDate(new Date(), quitDeadline)) {
                throw new OpException("退出竞聘截止时间为{0}，现在时间已过，不可退出。",
                        DateUtils.formatDate(quitDeadline, "yyyy年MM月dd日 HH:mm"));
            }
        }

        CrsApplicant crsApplicant = getAvaliable(postId, userId);
        if(crsApplicant==null || crsApplicant.getStatus()!=CrsConstants.CRS_APPLICANT_STATUS_SUBMIT)
            throw new OpException("干部应聘状态异常");

        CrsApplicantWithBLOBs record = new CrsApplicantWithBLOBs();
        record.setId(crsApplicant.getId());
        record.setIsQuit(true);
        record.setQuitProof(StringUtils.trimToNull(quitProof));

        crsApplicantMapper.updateByPrimaryKeySelective(record);

        if(StringUtils.isBlank(quitProof)){
            // 可能之前退出过，所以要清空之前的退出申请文件（如果有的话）
            commonMapper.excuteSql("update crs_applicant set quit_proof=null where id=" + crsApplicant.getId());
        }

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

        if(ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)
            && crsPost.getSwitchStatus() != CrsConstants.CRS_POST_ENROLL_STATUS_OPEN){

            if(crsPost.getSwitchStatus() == CrsConstants.CRS_POST_ENROLL_STATUS_CLOSED) {

                throw new OpException("岗位{0}应聘报名已结束。", crsPost == null ? "" : crsPost.getName());
            }else {
                throw new OpException("岗位{0}应聘还未开始。", crsPost == null ? "" : crsPost.getName());
            }
        }

        CrsApplicant crsApplicant = getAvaliable(postId, userId);
        if(crsApplicant==null || crsApplicant.getStatus()!=CrsConstants.CRS_APPLICANT_STATUS_SUBMIT)
            throw new OpException("干部应聘状态异常");

        CrsApplicantWithBLOBs record = new CrsApplicantWithBLOBs();
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
    public void updateByPrimaryKeySelective(CrsApplicantWithBLOBs record) {

        CrsApplicantWithBLOBs oldRecord = crsApplicantMapper.selectByPrimaryKey(record.getId());

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

        // 如果是 干部、考察对象或优秀年轻干部，则直接返回干部ID
        if(ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CADRE,
                RoleConstants.ROLE_CADREINSPECT, RoleConstants.ROLE_CADRERESERVE)){
            return cv.getId();
        }

        int cadreId;
        if(cv!=null ){
            cadreId = cv.getId();
            //Assert.isTrue(cv.getStatus()==CadreConstants.CADRE_STATUS_RECRUIT, "应聘干部状态异常");
            // 可能是已撤销资格的考察对象，则这里cv.getStatus()=CadreConstants.CADRE_STATUS_INSPECT

            if(cv.getStatus()==CadreConstants.CADRE_STATUS_NOT_CADRE) {

                Cadre record = new Cadre();
                record.setId(cadreId);
                record.setStatus(CadreConstants.CADRE_STATUS_RECRUIT);
                cadreService.updateByPrimaryKeySelective(record);
            }
        }else{
            // 普通教师 第一次访问的情况，需要先初始化 应聘干部
            Cadre record  = new Cadre();
            record.setUserId(userId);
            record.setStatus(CadreConstants.CADRE_STATUS_RECRUIT);
            // 默认是处级干部
            if(record.getType()==null){
                record.setType(CadreConstants.CADRE_TYPE_OTHER);
            }
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

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        CrsApplicant entity = crsApplicantMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer postId = entity.getPostId();
        if((BooleanUtils.isTrue(entity.getSpecialStatus())
                ||entity.getRequireCheckStatus()==1)&&entity.getIsQuit()==false) {
            CrsApplicantViewExample example = new CrsApplicantViewExample();
            if (addNum * orderBy > 0) {

                example.createCriteria().andPostIdEqualTo(postId)
                        .andIsRequireCheckPassEqualTo(true).andIsQuitEqualTo(false).andSortOrderGreaterThan(baseSortOrder);
                example.setOrderByClause("sort_order asc");
            } else {

                example.createCriteria().andPostIdEqualTo(postId)
                        .andIsRequireCheckPassEqualTo(true).andIsQuitEqualTo(false).andSortOrderLessThan(baseSortOrder);
                example.setOrderByClause("sort_order desc");
            }

            List<CrsApplicantView> overEntities = crsApplicantViewMapper.selectByExampleWithRowbounds(example,
                    new RowBounds(0, Math.abs(addNum)));
            if (overEntities.size() > 0) {

                CrsApplicantView targetEntity = overEntities.get(overEntities.size() - 1);

                String whereSql = "post_id=" + postId + " and (special_status=1 or require_check_status=1) and is_quit=0";
                if (addNum * orderBy > 0)
                    commonMapper.downOrder("crs_applicant", whereSql,
                            baseSortOrder, targetEntity.getSortOrder());
                else
                    commonMapper.upOrder("crs_applicant", whereSql,
                            baseSortOrder, targetEntity.getSortOrder());

                CrsApplicantWithBLOBs record = new CrsApplicantWithBLOBs();
                record.setId(id);
                record.setSortOrder(targetEntity.getSortOrder());
                crsApplicantMapper.updateByPrimaryKeySelective(record);
            }
        }
    }
}
