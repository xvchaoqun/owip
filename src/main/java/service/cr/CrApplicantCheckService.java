package service.cr;

import domain.cr.*;
import mixin.MixinUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.CrConstants;
import sys.constants.SystemConstants;
import sys.utils.JSONUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrApplicantCheckService extends CrBaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private CrRequireRuleService crRequireRuleService;

    // 获取报名人的所有组合项的审核结果 <RequireRuleId, CrApplicantCheck>
    public Map<Integer, CrApplicantCheck> getRuleCheckMap(int applicantId, boolean isFirstPost) {

        CrApplicantCheckExample example = new CrApplicantCheckExample();
        example.createCriteria().andApplicantIdEqualTo(applicantId)
                .andIsFirstPostEqualTo(isFirstPost);

        List<CrApplicantCheck> crApplicantChecks = crApplicantCheckMapper.selectByExample(example);
        Map<Integer, CrApplicantCheck> map = new LinkedHashMap<>();
        for (CrApplicantCheck record : crApplicantChecks) {
            map.put(record.getRequireRuleId(), record);
        }

        return map;
    }

    // 审核组合项
    public void ruleCheck(int applicantId, boolean isFirstPost, int ruleId, boolean status) {

        CrApplicantCheckExample example = new CrApplicantCheckExample();
        example.createCriteria().andApplicantIdEqualTo(applicantId)
                .andIsFirstPostEqualTo(isFirstPost)
                .andRequireRuleIdEqualTo(ruleId);
        List<CrApplicantCheck> crApplicantChecks = crApplicantCheckMapper.selectByExample(example);
        if (crApplicantChecks.size() == 1) {
            CrApplicantCheck record = new CrApplicantCheck();
            record.setId(crApplicantChecks.get(0).getId());
            record.setPass(status);
            crApplicantCheckMapper.updateByPrimaryKeySelective(record);
        } else {

            CrApplicantCheck record = new CrApplicantCheck();
            record.setApplicantId(applicantId);
            record.setIsFirstPost(isFirstPost);
            record.setRequireRuleId(ruleId);
            record.setPass(status);
            record.setCheckTime(new Date());
            record.setCheckUserId(ShiroHelper.getCurrentUserId());

            crApplicantCheckMapper.insertSelective(record);
        }

        CrApplicant oldRecord = crApplicantMapper.selectByPrimaryKey(applicantId);
        CrRequireRule crRequireRule = crRequireRuleMapper.selectByPrimaryKey(ruleId);
        sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CR1,
                crRequireRule.getName() + "-审核"+(isFirstPost?"(第一志愿)":"(第二志愿)"), status ? SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS
                        : SystemConstants.SYS_APPROVAL_LOG_STATUS_DENY,
                JSONUtils.toString(oldRecord, MixinUtils.baseMixins(), false));
    }

    // 资格审核
    @Transactional
    public void requireCheck(Integer id, int postId, boolean status, String remark) {

        CrApplicant oldRecord = crApplicantMapper.selectByPrimaryKey(id);

        CrApplicant record = new CrApplicant();
        record.setId(id);
        boolean isFirstPost = (oldRecord.getFirstPostId() == postId);
        if(isFirstPost) {
            record.setFirstCheckStatus(status ? CrConstants.CR_REQUIRE_CHECK_STATUS_PASS :
                    CrConstants.CR_REQUIRE_CHECK_STATUS_UNPASS);
            record.setFirstCheckRemark(remark);
        }else{
            record.setSecondCheckStatus(status ? CrConstants.CR_REQUIRE_CHECK_STATUS_PASS :
                    CrConstants.CR_REQUIRE_CHECK_STATUS_UNPASS);
            record.setSecondCheckRemark(remark);
        }

        crApplicantMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CR1,
                "资格审核" + (isFirstPost?"(第一志愿)":"(第二志愿)"), status ? SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS
                        : SystemConstants.SYS_APPROVAL_LOG_STATUS_DENY, remark);
    }
}
