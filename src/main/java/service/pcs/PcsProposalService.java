package service.pcs;

import domain.pcs.PcsProposal;
import domain.pcs.PcsProposalExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class PcsProposalService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "提案编码为空");

        PcsProposalExample example = new PcsProposalExample();
        PcsProposalExample.Criteria criteria = example.createCriteria()
                .andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pcsProposalMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PcsProposal record){

        Assert.isTrue(!idDuplicate(null, record.getCode()), "提案编码重复");
        pcsProposalMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pcsProposalMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsProposalExample example = new PcsProposalExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsProposalMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PcsProposal record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "提案编码重复");
        return pcsProposalMapper.updateByPrimaryKeySelective(record);
    }
}
