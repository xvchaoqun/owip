package service.sc.scPassport;

import domain.sc.scPassport.ScPassport;
import domain.sc.scPassport.ScPassportExample;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;

import java.util.Arrays;

@Service
public class ScPassportService extends ScBaseMapper {

    public boolean idDuplicate(Integer id, int handId, int classId){

        ScPassportExample example = new ScPassportExample();
        ScPassportExample.Criteria criteria = example.createCriteria()
                .andHandIdEqualTo(handId)
                .andClassIdEqualTo(classId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scPassportMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScPassport record){

        Assert.isTrue(!idDuplicate(null, record.getHandId(), record.getClassId()), "duplicated");
        scPassportMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScPassportExample example = new ScPassportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scPassportMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScPassport record){

        Assert.isTrue(!idDuplicate(record.getId(), record.getHandId(), record.getClassId()), "duplicated");

        return scPassportMapper.updateByPrimaryKeySelective(record);
    }
}
