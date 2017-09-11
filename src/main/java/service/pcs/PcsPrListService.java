package service.pcs;

import controller.global.OpException;
import controller.pcs.pr.PcsPrCandidateFormBean;
import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsPrCandidateExample;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrCandidateViewExample;
import domain.pcs.PcsPrRecommend;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import java.util.List;

@Service
public class PcsPrListService extends BaseMapper {
    @Autowired
    private PcsPrPartyService pcsPrPartyService;

    // 获取党代表名单
    public List<PcsPrCandidateView> getList(int configId, Integer partyId){

        PcsPrCandidateViewExample example = new PcsPrCandidateViewExample();
        PcsPrCandidateViewExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId)
                .andIsChosenEqualTo(true);
        if(partyId!=null){
            criteria.andPartyIdEqualTo(partyId);
        }

        example.setOrderByClause("type asc, leader_sort_order desc, vote3 desc, sort_order asc");

        return pcsPrCandidateViewMapper.selectByExample(example);
    }


    // 三下三上， 提交党代表名单
    @Transactional
    public void submit(int configId, int partyId, List<PcsPrCandidateFormBean> beans) {

        if(!pcsPrPartyService.allowModify(partyId, configId, SystemConstants.PCS_STAGE_THIRD)){
            throw new OpException("数据已报送，不可修改。");
        }

        // 阶段三共用阶段二的候选人名单
        PcsPrRecommend pcsPrRecommend2 = pcsPrPartyService.getPcsPrRecommend
                (configId, SystemConstants.PCS_STAGE_SECOND, partyId);
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
            if(StringUtils.isBlank(mobile) || !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
                throw new OpException("手机号码有误：" + mobile);
            }
            _candidate.setMobile(mobile);
            _candidate.setEmail(bean.getEmail());
            _candidate.setIsChosen(true);

            PcsPrCandidateExample example = new PcsPrCandidateExample();
            example.createCriteria().andRecommendIdEqualTo(recommendId).andUserIdEqualTo(userId);
            int ret = pcsPrCandidateMapper.updateByExampleSelective(_candidate, example);

            if(ret!=1){
                throw new OpException("数据有误。");
            }
        }

    }
}
