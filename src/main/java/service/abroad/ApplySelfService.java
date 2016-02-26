package service.abroad;

import domain.ApplySelf;
import domain.ApplySelfExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ApplySelfService extends BaseMapper {

    @Transactional
    public int insertSelective(ApplySelf record){

        return applySelfMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        applySelfMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApplySelfExample example = new ApplySelfExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applySelfMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApplySelf record){
        return applySelfMapper.updateByPrimaryKeySelective(record);
    }
}
