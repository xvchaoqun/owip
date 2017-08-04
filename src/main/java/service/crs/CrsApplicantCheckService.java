package service.crs;

import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantCheck;
import domain.crs.CrsApplicantCheckExample;
import domain.crs.CrsApplicantExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrsApplicantCheckService extends BaseMapper {

    // 获取报名人的每一项的审核结果 <RequireRuleId, CrsApplicantCheck>
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

    // 审核一项
    public void ruleCheck(int applicantId, int ruleId, boolean status) {

        CrsApplicantCheckExample example = new CrsApplicantCheckExample();
        example.createCriteria().andApplicantIdEqualTo(applicantId)
                .andRequireRuleIdEqualTo(ruleId);
        List<CrsApplicantCheck> crsApplicantChecks = crsApplicantCheckMapper.selectByExample(example);
        if(crsApplicantChecks.size() == 1){
            CrsApplicantCheck record = new CrsApplicantCheck();
            record.setId(crsApplicantChecks.get(0).getId());
            record.setPass(status);
            crsApplicantCheckMapper.updateByPrimaryKeySelective(record);
        }else{

            CrsApplicantCheck record = new CrsApplicantCheck();
            record.setApplicantId(applicantId);
            record.setRequireRuleId(ruleId);
            record.setPass(status);
            record.setCheckTime(new Date());
            record.setCheckUserId(ShiroHelper.getCurrentUserId());

            crsApplicantCheckMapper.insertSelective(record);
        }
    }

    // 信息审核
    @Transactional
    public void infoCheck(Integer id, boolean status, String remark) {

        CrsApplicant record = new CrsApplicant();
        record.setInfoCheckStatus(status ? SystemConstants.CRS_APPLICANT_INFO_CHECK_STATUS_PASS :
                SystemConstants.CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS);
        record.setInfoCheckRemark(remark);

        if (status) {
            record.setRequireCheckStatus(SystemConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT);
        }

        CrsApplicantExample example = new CrsApplicantExample();
        example.createCriteria().andIdEqualTo(id)
                .andInfoCheckStatusEqualTo(SystemConstants.CRS_APPLICANT_INFO_CHECK_STATUS_INIT);

        crsApplicantMapper.updateByExampleSelective(record, example);
    }

    // 资格审核
    @Transactional
    public void requireCheck(Integer id, boolean status, String remark) {

        CrsApplicant record = new CrsApplicant();
        record.setRequireCheckStatus(status ? SystemConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_PASS :
                SystemConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS);
        record.setRequireCheckRemark(remark);

        CrsApplicantExample example = new CrsApplicantExample();
        example.createCriteria().andIdEqualTo(id)
                .andRequireCheckStatusEqualTo(SystemConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT)
                .andInfoCheckStatusEqualTo(SystemConstants.CRS_APPLICANT_INFO_CHECK_STATUS_PASS); // 信息审核之后才能资格审核?

        crsApplicantMapper.updateByExampleSelective(record, example);
    }

    // 推荐/自荐
    @Transactional
    public int recommend(CrsApplicant record) {

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

        return iCrsMapper.recommend(record);
    }

    // 破格操作
    @Transactional
    public void special(int id, boolean specialStatus, String specialPdf, String specialRemark) {

        if (specialStatus) {

            CrsApplicant record = new CrsApplicant();
            record.setSpecialStatus(true);
            record.setSpecialPdf(specialPdf);
            record.setSpecialRemark(specialRemark);

            CrsApplicantExample example = new CrsApplicantExample();
            example.createCriteria().andIdEqualTo(id).andRequireCheckStatusEqualTo(
                    SystemConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS); // 资格审核未通过才可以破格

            crsApplicantMapper.updateByExampleSelective(record, example);

        } else {
            // 取消破格
            commonMapper.excuteSql("update crs_applicant set special_status=0 where id=" + id);
           /* commonMapper.excuteSql("update crs_applicant set special_status=0" +
                    " special_pdf=null, special_remark=null where id=" + id);*/
        }
    }
}
