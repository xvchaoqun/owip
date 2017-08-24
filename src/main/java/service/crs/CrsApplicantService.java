package service.crs;

import controller.global.OpException;
import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantAdjust;
import domain.crs.CrsApplicantExample;
import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import domain.crs.CrsPost;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import java.util.Date;
import java.util.List;

@Service
public class CrsApplicantService extends BaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    // 获取资格审核推荐通过人员列表
    public List<CrsApplicantView> getPassedCrsApplicants(int postId) {

        CrsApplicantViewExample example = new CrsApplicantViewExample();
        example.createCriteria().andPostIdEqualTo(postId)
                .andIsRequireCheckPassEqualTo(true);

        return crsApplicantViewMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int postId, int userId) {

        CrsApplicantExample example = new CrsApplicantExample();
        CrsApplicantExample.Criteria criteria = example.createCriteria()
                .andPostIdEqualTo(postId).andUserIdEqualTo(userId)
                .andStatusEqualTo(SystemConstants.AVAILABLE);

        if (id != null) criteria.andIdNotEqualTo(id);

        return crsApplicantMapper.countByExample(example) > 0;
    }

    // 应聘报名
    @Transactional
    public CrsApplicant apply(int postId, int userId) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        if(crsPost==null || crsPost.getStatus() !=SystemConstants.CRS_POST_STATUS_NORMAL
                || crsPost.getSwitchStatus() != SystemConstants.CRS_POST_ENROLL_STATUS_OPEN){
            throw new OpException("岗位{0}应聘未开始。", crsPost==null?"":crsPost.getName());
        }

        if(idDuplicate(null, postId, userId)){
            throw new OpException("岗位{0}重复应聘。", crsPost==null?"":crsPost.getName());
        }

        CrsApplicant record = new CrsApplicant();
        record.setPostId(postId);
        record.setUserId(userId);
        record.setEnrollTime(new Date());
        record.setIsRecommend(false);
        record.setInfoCheckStatus(SystemConstants.CRS_APPLICANT_INFO_CHECK_STATUS_INIT);
        record.setIsQuit(false);
        record.setStatus(SystemConstants.AVAILABLE);
        crsApplicantMapper.insertSelective(record);

        sysApprovalLogService.add(record.getId(), record.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "应聘报名", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                JSONUtils.toString(record, MixinUtils.baseMixins(), false));

        return record;
    }

    // 返回干部的某个岗位的应聘报名记录（正常状态，包含退出或未退出的报名）
    public CrsApplicant getAvaliable(int postId, Integer userId){

        CrsApplicantExample example = new CrsApplicantExample();
        CrsApplicantExample.Criteria criteria = example.createCriteria()
                .andPostIdEqualTo(postId).andUserIdEqualTo(userId)
                .andStatusEqualTo(SystemConstants.AVAILABLE);
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
            throw new OpException("招聘会于{0}召开，{0}之后不可退出。",
                    DateUtils.formatDate(meetingTime, "yyyy年MM月dd日 HH点"),
                    DateUtils.formatDate(quitDeadline, "yyyy年MM月dd日 HH点"));
        }

        CrsApplicant crsApplicant = getAvaliable(postId, userId);
        if(crsApplicant==null) throw new OpException("干部应聘状态异常");

        CrsApplicant record = new CrsApplicant();
        record.setId(crsApplicant.getId());
        record.setIsQuit(true);

        crsApplicantMapper.updateByPrimaryKeySelective(record);
    }

    // 重新报名
    @Transactional
    public void reApply(int postId, Integer userId) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        if(crsPost.getSwitchStatus() != SystemConstants.CRS_POST_ENROLL_STATUS_OPEN){
            throw new OpException("岗位{0}应聘未开始。", crsPost==null?"":crsPost.getName());
        }

        CrsApplicant crsApplicant = getAvaliable(postId, userId);
        if(crsApplicant==null) throw new OpException("干部应聘状态异常");

        CrsApplicant record = new CrsApplicant();
        record.setId(crsApplicant.getId());
        record.setIsQuit(false);

        crsApplicantMapper.updateByPrimaryKeySelective(record);
    }

    // 调整岗位
    @Transactional
    public void adjust(Integer[] applyPostIds, Integer[] selectablePostIds, Integer confirmUserId) {

        if(applyPostIds == null || applyPostIds.length==0)  throw new OpException("请选择已报名岗位。");
        if(selectablePostIds == null || selectablePostIds.length==0)  throw new OpException("请选择调整后的岗位。");

        // 删除已报名岗位
        for (Integer applyPostId : applyPostIds) {

            CrsApplicant avaliable = getAvaliable(applyPostId, confirmUserId);
            Assert.isTrue(avaliable!=null && avaliable.getStatus()==SystemConstants.AVAILABLE
                    && avaliable.getUserId().intValue() == confirmUserId, "参数错误");

            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(applyPostId);
            Assert.isTrue(crsPost!=null && avaliable.getUserId().intValue() == confirmUserId, "参数错误");

            CrsApplicant record = new CrsApplicant();
            record.setId(avaliable.getId());
            record.setStatus(SystemConstants.UNAVAILABLE);
            crsApplicantMapper.updateByPrimaryKeySelective(record);
        }

        // 添加岗位
        for (Integer selectablePostId : selectablePostIds) {

            apply(selectablePostId, confirmUserId);
        }

        CrsApplicantAdjust adjust = new CrsApplicantAdjust();
        adjust.setUserId(confirmUserId);
        adjust.setAdjustTime(new Date());
        adjust.setPrePostIds(StringUtils.join(applyPostIds, ","));
        adjust.setAfterPostIds(StringUtils.join(selectablePostIds, ","));
        adjust.setOpUserId(ShiroHelper.getCurrentUserId());
        adjust.setIp(ContextHelper.getRealIp());
        crsApplicantAdjustMapper.insertSelective(adjust);
    }

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
}
