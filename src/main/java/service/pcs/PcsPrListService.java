package service.pcs;

import controller.global.OpException;
import controller.pcs.pr.PcsPrCandidateFormBean;
import domain.pcs.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.PcsConstants;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import java.util.List;

@Service
public class PcsPrListService extends PcsBaseMapper {
    @Autowired
    private PcsPrPartyService pcsPrPartyService;

    // 检查是否保存过姓名笔画顺序
    public boolean hasSort(int configId, int partyId) {

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, PcsConstants.PCS_STAGE_SECOND, partyId);
        if(pcsPrRecommend==null) return false;

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        example.createCriteria().andRecommendIdEqualTo(pcsPrRecommend.getId()).andRealnameSortOrderIsNull();

        return pcsPrCandidateMapper.countByExample(example) == 0;
    }

    // 先按代表类型，同一类型下，再按姓氏笔画。
    public List<PcsPrCandidateView> getList2(int configId, Integer partyId, Boolean isChosen) {

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        PcsPrCandidateViewExample.Criteria criteria = example.createCriteria()
                .andConfigIdEqualTo(configId).andStageEqualTo(PcsConstants.PCS_STAGE_SECOND);

        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (isChosen != null) {
            criteria.andIsChosenEqualTo(isChosen);
        }
        example.setOrderByClause("party_sort_order desc, type asc, realname_sort_order asc");

        return pcsPrCandidateViewMapper.selectByExample(example);
    }

    // 获取党代表名单 （预先按姓笔画排序）
    public List<PcsPrCandidateView> getList(int configId, int partyId, Boolean isChosen) {

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        PcsPrCandidateViewExample.Criteria criteria = example.createCriteria()
                .andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId)
                .andStageEqualTo(PcsConstants.PCS_STAGE_SECOND);

        if (isChosen != null) {
            criteria.andIsChosenEqualTo(isChosen);
        }

        example.setOrderByClause("party_sort_order desc, realname_sort_order asc, type asc, leader_sort_order desc, sort_order asc");

        return pcsPrCandidateViewMapper.selectByExample(example);
    }


    // 三下三上， 提交党代表名单
    @Transactional
    public void submit(int configId, int partyId, List<PcsPrCandidateFormBean> beans) {

        if (!pcsPrPartyService.allowModify(partyId, configId, PcsConstants.PCS_STAGE_THIRD)) {
            throw new OpException("数据已报送，不可修改。");
        }

        // 阶段三共用阶段二的候选人名单
        PcsPrRecommend pcsPrRecommend2 = pcsPrPartyService.getPcsPrRecommend
                (configId, PcsConstants.PCS_STAGE_SECOND, partyId);
        int recommendId = pcsPrRecommend2.getId();

        {
            // 先重置代表勾选状态
            PcsPrCandidateExample example = new PcsPrCandidateExample();
            example.createCriteria().andRecommendIdEqualTo(recommendId);
            PcsPrCandidate record = new PcsPrCandidate();
            record.setIsChosen(false);
            pcsPrCandidateMapper.updateByExampleSelective(record, example);
        }

        for (PcsPrCandidateFormBean bean : beans) {

            int userId = bean.getUserId();

            PcsPrCandidate _candidate = new PcsPrCandidate();
            String mobile = bean.getMobile();
            if (StringUtils.isBlank(mobile) || !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)) {
                throw new OpException("手机号码有误：" + mobile);
            }
            _candidate.setMobile(mobile);
            _candidate.setEmail(bean.getEmail());
            _candidate.setIsChosen(true);

            PcsPrCandidateExample example = new PcsPrCandidateExample();
            example.createCriteria().andRecommendIdEqualTo(recommendId).andUserIdEqualTo(userId);
            int ret = pcsPrCandidateMapper.updateByExampleSelective(_candidate, example);

            if (ret != 1) {
                throw new OpException("数据有误。");
            }
        }

    }

    // 按姓氏笔画排序（三下三上排序）
    @Transactional
    public void sort(int configId, int partyId, Integer[] userIds) {

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, PcsConstants.PCS_STAGE_SECOND, partyId);
        int recommendId = pcsPrRecommend.getId();

        commonMapper.excuteSql("update pcs_pr_candidate set realname_sort_order = null where recommend_id=" + recommendId);

        for (Integer userId : userIds) {

            PcsPrCandidate record = new PcsPrCandidate();
            record.setRealnameSortOrder(getNextSortOrder("pcs_pr_candidate",
                    "realname_sort_order", "recommend_id=" + recommendId));

            PcsPrCandidateExample example = new PcsPrCandidateExample();
            example.createCriteria().andRecommendIdEqualTo(recommendId).andUserIdEqualTo(userId);
            pcsPrCandidateMapper.updateByExampleSelective(record, example);
        }
    }
}
