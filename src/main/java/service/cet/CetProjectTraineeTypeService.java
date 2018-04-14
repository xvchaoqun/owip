package service.cet;

import domain.cet.CetProjectTraineeType;
import domain.cet.CetProjectTraineeTypeExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CetProjectTraineeTypeService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        CetProjectTraineeTypeExample example = new CetProjectTraineeTypeExample();
        CetProjectTraineeTypeExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetProjectTraineeTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetProjectTraineeType record){

        //Assert.isTrue(!idDuplicate(null, record.getCode()));
        cetProjectTraineeTypeMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetProjectTraineeTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectTraineeTypeExample example = new CetProjectTraineeTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectTraineeTypeMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetProjectTraineeType record){
        return cetProjectTraineeTypeMapper.updateByPrimaryKeySelective(record);
    }
}
