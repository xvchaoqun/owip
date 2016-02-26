package service.abroad;

import domain.PassportApply;
import domain.PassportApplyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class PassportApplyService extends BaseMapper {

    @Transactional
    public int insertSelective(PassportApply record){

        return passportApplyMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        passportApplyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        passportApplyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PassportApply record){

        return passportApplyMapper.updateByPrimaryKeySelective(record);
    }
}
