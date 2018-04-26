package service.crs;

import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantCheck;
import domain.crs.CrsApplicantCheckExample;
import domain.crs.CrsApplicantExample;
import domain.crs.CrsRequireRule;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.CrsConstants;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrsApplicantCheckService extends BaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private CrsRequireRuleService crsRequireRuleService;

    // 获取报名人的所有组合项的审核结果 <RequireRuleId, CrsApplicantCheck>
    public Map<Integer, CrsApplicantCheck> getRuleCheckMap(int applicantId) {

        CrsApplicantCheckExample example = new CrsApplicantCheckExample();
        example.createCriteria().andApplicantIdEqualTo(applicantId);

        List<CrsApplicantCheck> crsApplicantChecks = crsApplicantCheckMapper.selectByExample(example);
        Map<Integer, CrsApplicantCheck> map = new LinkedHashMap<>();
        for (CrsApplicantCheck record : crsApplicantChecks) {
            map.put(record.getRequireRuleId(), record);
        }

        return map;
    }

    // 审核组合项
    public void ruleCheck(int applicantId, int ruleId, boolean status) {

        CrsApplicantCheckExample example = new CrsApplicantCheckExample();
        example.createCriteria().andApplicantIdEqualTo(applicantId)
                .andRequireRuleIdEqualTo(ruleId);
        List<CrsApplicantCheck> crsApplicantChecks = crsApplicantCheckMapper.selectByExample(example);
        if (crsApplicantChecks.size() == 1) {
            CrsApplicantCheck record = new CrsApplicantCheck();
            record.setId(crsApplicantChecks.get(0).getId());
            record.setPass(status);
            crsApplicantCheckMapper.updateByPrimaryKeySelective(record);
        } else {

            CrsApplicantCheck record = new CrsApplicantCheck();
            record.setApplicantId(applicantId);
            record.setRequireRuleId(ruleId);
            record.setPass(status);
            record.setCheckTime(new Date());
            record.setCheckUserId(ShiroHelper.getCurrentUserId());

            crsApplicantCheckMapper.insertSelective(record);
        }

        CrsApplicant oldRecord = crsApplicantMapper.selectByPrimaryKey(applicantId);
        CrsRequireRule crsRequireRule = crsRequireRuleMapper.selectByPrimaryKey(ruleId);
        sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                crsRequireRule.getName() + "-审核", status ? SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS
                        : SystemConstants.SYS_APPROVAL_LOG_STATUS_DENY,
                JSONUtils.toString(oldRecord, MixinUtils.baseMixins(), false));
    }

    // 信息审核
    @Transactional
    public void infoCheck(Integer id, boolean status, String remark) {

        CrsApplicant oldRecord = crsApplicantMapper.selectByPrimaryKey(id);

        CrsApplicant record = new CrsApplicant();
        record.setInfoCheckStatus(status ? CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_PASS :
                CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS);
        record.setInfoCheckRemark(remark);

        if (status) {
            record.setRequireCheckStatus(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT);
        }

        CrsApplicantExample example = new CrsApplicantExample();
        example.createCriteria().andIdEqualTo(id)
                .andInfoCheckStatusEqualTo(CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_INIT);

        crsApplicantMapper.updateByExampleSelective(record, example);

        sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "信息审核", status ? SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS
                        : SystemConstants.SYS_APPROVAL_LOG_STATUS_DENY, remark);
    }

    // 资格审核
    @Transactional
    public void requireCheck(Integer id, boolean status, String remark) {

        CrsApplicant oldRecord = crsApplicantMapper.selectByPrimaryKey(id);

        CrsApplicant record = new CrsApplicant();
        record.setRequireCheckStatus(status ? CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_PASS :
                CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS);
        record.setRequireCheckRemark(remark);
        if(status){
            String whereSql = "post_id=" + oldRecord.getPostId()
                    + " and (special_status=1 or require_check_status=1) and is_quit=0";
            record.setSortOrder(getNextSortOrder("crs_applicant", whereSql));
        }

        CrsApplicantExample example = new CrsApplicantExample();
        example.createCriteria().andIdEqualTo(id)
                .andRequireCheckStatusEqualTo(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT)
                .andInfoCheckStatusEqualTo(CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_PASS); // 信息审核之后才能资格审核?

        crsApplicantMapper.updateByExampleSelective(record, example);

        sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "资格审核", status ? SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS
                        : SystemConstants.SYS_APPROVAL_LOG_STATUS_DENY, remark);
    }

    // 重新资格审核
    @Transactional
    public void requireCheck_back(Integer id) {

        CrsApplicant record = new CrsApplicant();
        record.setRequireCheckStatus(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT);

        CrsApplicantExample example = new CrsApplicantExample();
        example.createCriteria().andIdEqualTo(id)
                .andRequireCheckStatusIn(Arrays.asList(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_PASS,
                        CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS)); // 信息审核之后才能资格审核?

        crsApplicantMapper.updateByExampleSelective(record, example);

        CrsApplicant oldRecord = crsApplicantMapper.selectByPrimaryKey(id);
        sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "重新资格审核",  SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                JSONUtils.toString(oldRecord, MixinUtils.baseMixins(), false));
    }

    // 推荐/自荐
    @Transactional
    public void recommend(CrsApplicant record) {

        CrsApplicant oldRecord = crsApplicantMapper.selectByPrimaryKey(record.getId());

        record.setIsRecommend(BooleanUtils.isTrue(record.getIsRecommend()));

        record.setRecommendOw(StringUtils.trimToNull(record.getRecommendOw()));
        record.setRecommendCadre(StringUtils.trimToNull(record.getRecommendCadre()));
        record.setRecommendCrowd(StringUtils.trimToNull(record.getRecommendCrowd()));

        record.setRecommendPdf(StringUtils.trimToNull(record.getRecommendPdf()));

        if (record.getIsRecommend() == false) {

            record.setRecommendOw(null);
            record.setRecommendCadre(null);
            record.setRecommendCrowd(null);
            record.setRecommendPdf(null);
        }

        iCrsMapper.recommend(record);

        sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                "更新岗位报名自荐/推荐", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                JSONUtils.toString(oldRecord, MixinUtils.baseMixins(), false));
    }

    // 破格操作
    @Transactional
    public void special(int id, boolean specialStatus, String specialPdf, String specialRemark) {

        CrsApplicant oldRecord = crsApplicantMapper.selectByPrimaryKey(id);

        if (specialStatus) {

            CrsApplicant record = new CrsApplicant();
            record.setSpecialStatus(true);
            record.setSpecialPdf(specialPdf);
            record.setSpecialRemark(specialRemark);

            String whereSql = "post_id=" + oldRecord.getPostId()
                    + " and (special_status=1 or require_check_status=1) and is_quit=0";
            record.setSortOrder(getNextSortOrder("crs_applicant", whereSql));

            CrsApplicantExample example = new CrsApplicantExample();
            example.createCriteria().andIdEqualTo(id).andRequireCheckStatusEqualTo(
                    CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS); // 资格审核未通过才可以破格

            crsApplicantMapper.updateByExampleSelective(record, example);

            sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                    "破格通过",  SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, specialRemark);

        } else {
            // 取消破格
            commonMapper.excuteSql("update crs_applicant set special_status=0 where id=" + id);
           /* commonMapper.excuteSql("update crs_applicant set special_status=0" +
                    " special_pdf=null, special_remark=null where id=" + id);*/

            sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT,
                    "取消破格",  SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    JSONUtils.toString(oldRecord, MixinUtils.baseMixins(), false));
        }
    }
}
