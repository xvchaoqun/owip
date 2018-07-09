package service.sc.scPassport;

import domain.sc.scPassport.ScPassport;
import domain.sc.scPassport.ScPassportExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScPassportService extends BaseMapper {

    @Transactional
    public void insertSelective(ScPassport record){

        scPassportMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scPassportMapper.deleteByPrimaryKey(id);
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

        return scPassportMapper.updateByPrimaryKeySelective(record);
    }
}
