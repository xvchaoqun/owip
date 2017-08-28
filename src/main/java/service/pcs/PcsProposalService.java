package service.pcs;

import domain.pcs.PcsProposal;
import domain.pcs.PcsProposalExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class PcsProposalService extends BaseMapper {

    public boolean idDuplicate(Integer id, int userId, byte type, int configId) {

        PcsProposalExample example = new PcsProposalExample();
        PcsProposalExample.Criteria criteria = example.createCriteria()
                .andConfigIdEqualTo(configId).andTypeEqualTo(type).andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pcsProposalMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PcsProposal record) {

        pcsProposalMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        pcsProposalMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PcsProposalExample example = new PcsProposalExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsProposalMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PcsProposal record) {
        return pcsProposalMapper.updateByPrimaryKeySelective(record);
    }
}
