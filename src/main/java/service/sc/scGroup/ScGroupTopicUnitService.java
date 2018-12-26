package service.sc.scGroup;

import domain.sc.scGroup.ScGroupTopicUnit;
import domain.sc.scGroup.ScGroupTopicUnitExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sc.ScBaseMapper;

import java.util.Arrays;

@Service
public class ScGroupTopicUnitService extends ScBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScGroupTopicUnitExample example = new ScGroupTopicUnitExample();
        ScGroupTopicUnitExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scGroupTopicUnitMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScGroupTopicUnit record){

        scGroupTopicUnitMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scGroupTopicUnitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScGroupTopicUnitExample example = new ScGroupTopicUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scGroupTopicUnitMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScGroupTopicUnit record){
        return scGroupTopicUnitMapper.updateByPrimaryKeySelective(record);
    }
}
