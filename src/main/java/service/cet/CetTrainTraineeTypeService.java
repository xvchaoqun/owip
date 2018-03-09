package service.cet;

import domain.cet.CetTrainTraineeType;
import domain.cet.CetTrainTraineeTypeExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CetTrainTraineeTypeService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        CetTrainTraineeTypeExample example = new CetTrainTraineeTypeExample();
        CetTrainTraineeTypeExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetTrainTraineeTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetTrainTraineeType record){

        //Assert.isTrue(!idDuplicate(null, record.getCode()));
        cetTrainTraineeTypeMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetTrainTraineeTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainTraineeTypeExample example = new CetTrainTraineeTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTrainTraineeTypeMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTrainTraineeType record){
        return cetTrainTraineeTypeMapper.updateByPrimaryKeySelective(record);
    }
}
