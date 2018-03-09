package service.pcs;

import controller.global.OpException;
import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsPrCandidateExample;
import domain.pcs.PcsProposal;
import domain.pcs.PcsProposalExample;
import domain.pcs.PcsProposalView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.PcsConstants;
import sys.utils.DateUtils;

import java.util.Date;
import java.util.List;

@Service
public class PcsProposalOwService extends BaseMapper {

    public static final String TABLE_NAME = "pcs_pr_candidate";

    // 升序排列
    @Transactional
    public void changeOrder(int candidateId, int addNum) {

        if (addNum == 0) return;
        byte orderBy = ORDER_BY_ASC;
        PcsPrCandidate entity = pcsPrCandidateMapper.selectByPrimaryKey(candidateId);
        Integer baseSortOrder = entity.getProposalSortOrder();
        Assert.isTrue(entity.getIsProposal(), "不在党代表名单中");

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andIsProposalEqualTo(true)
                    .andProposalSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("proposal_sort_order asc");
        } else {

            example.createCriteria().andIsProposalEqualTo(true)
                    .andProposalSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("proposal_sort_order desc");
        }

        List<PcsPrCandidate> overEntities =
                pcsPrCandidateMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PcsPrCandidate targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder2(TABLE_NAME, "proposal_sort_order",
                        "is_proposal=1", baseSortOrder, targetEntity.getProposalSortOrder());
            else
                commonMapper.upOrder2(TABLE_NAME, "proposal_sort_order",
                        "is_proposal=1", baseSortOrder, targetEntity.getProposalSortOrder());

            PcsPrCandidate record = new PcsPrCandidate();
            record.setId(candidateId);
            record.setProposalSortOrder(targetEntity.getProposalSortOrder());
            pcsPrCandidateMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 审核通过之后，自动给这个提案赋予一个编号，规则是“TA2017001”,按照顺序往下排。
    public String genCode(){

        int year = DateUtils.getYear(new Date());

        int seq ;
        String prefix = "TA"+year;
        PcsProposalExample example = new PcsProposalExample();
        example.createCriteria().andCodeLike( prefix + "%");
        example.setOrderByClause("code desc");
        List<PcsProposal> records = pcsProposalMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(records.size()>0){
            String code = records.get(0).getCode();
            String str = code.substring(prefix.length());
            seq =  Integer.parseInt(str) + 1;
        }else{
            seq = 1;
        }

        // 系统只能处理999及以下的编号
        Assert.isTrue(seq<1000, "编号异常");

        return prefix + String.format("%03d", seq);
    }

    // 审核提案
    @Transactional
    public int check(int id, boolean status) {

        PcsProposalView pcsProposal = pcsProposalViewMapper.selectByPrimaryKey(id);
        if(pcsProposal.getStatus() != PcsConstants.PCS_PROPOSAL_STATUS_INIT)
            throw new OpException("状态异常");

        PcsProposal record = new PcsProposal();
        record.setId(pcsProposal.getId());
        record.setStatus(status?PcsConstants.PCS_PROPOSAL_STATUS_PASS: PcsConstants.PCS_PROPOSAL_STATUS_DENY);
        if(status && StringUtils.isBlank(pcsProposal.getCode())){
            record.setCode(genCode());
        }

        record.setCheckTime(new Date());
        return pcsProposalMapper.updateByPrimaryKeySelective(record);
    }
}
