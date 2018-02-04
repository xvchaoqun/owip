package service.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeTopic;
import domain.sc.scCommittee.ScCommitteeTopicExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScCommitteeTopicService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        ScCommitteeTopicExample example = new ScCommitteeTopicExample();
        ScCommitteeTopicExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scCommitteeTopicMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScCommitteeTopic record){

        record.setIsDeleted(false);
        scCommitteeTopicMapper.insertSelective(record);
    }


    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScCommitteeTopicExample example = new ScCommitteeTopicExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScCommitteeTopic record = new ScCommitteeTopic();
        record.setIsDeleted(true);
        scCommitteeTopicMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScCommitteeTopic record){
        return scCommitteeTopicMapper.updateByPrimaryKeySelective(record);
    }
}
