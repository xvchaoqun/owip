package service.abroad;

import domain.PassportDraw;
import domain.PassportDrawExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class PassportDrawService extends BaseMapper {

    @Transactional
    public int insertSelective(PassportDraw record){

        return passportDrawMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        passportDrawMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportDrawExample example = new PassportDrawExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        passportDrawMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PassportDraw record){
        return passportDrawMapper.updateByPrimaryKeySelective(record);
    }
}
