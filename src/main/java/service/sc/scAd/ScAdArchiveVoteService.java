package service.sc.scAd;

import domain.sc.scAd.ScAdArchiveVote;
import domain.sc.scAd.ScAdArchiveVoteExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScAdArchiveVoteService extends BaseMapper {

    public boolean idDuplicate(Integer id, int voteId) {

        ScAdArchiveVoteExample example = new ScAdArchiveVoteExample();
        ScAdArchiveVoteExample.Criteria criteria = example.createCriteria().andVoteIdEqualTo(voteId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return scAdArchiveVoteMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScAdArchiveVote record) {

        Assert.isTrue(!idDuplicate(null, record.getVoteId()), "重复");
        scAdArchiveVoteMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        scAdArchiveVoteMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ScAdArchiveVoteExample example = new ScAdArchiveVoteExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scAdArchiveVoteMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScAdArchiveVote record) {
        Assert.isTrue(!idDuplicate(record.getId(), record.getVoteId()), "重复");
        return scAdArchiveVoteMapper.updateByPrimaryKeySelective(record);
    }
}
