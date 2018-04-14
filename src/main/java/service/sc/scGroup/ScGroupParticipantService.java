package service.sc.scGroup;

import domain.sc.scGroup.ScGroupParticipant;
import domain.sc.scGroup.ScGroupParticipantExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScGroupParticipantService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScGroupParticipantExample example = new ScGroupParticipantExample();
        ScGroupParticipantExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scGroupParticipantMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScGroupParticipant record){

        scGroupParticipantMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scGroupParticipantMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScGroupParticipantExample example = new ScGroupParticipantExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scGroupParticipantMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScGroupParticipant record){
        return scGroupParticipantMapper.updateByPrimaryKeySelective(record);
    }
}
