package service.base;

import domain.base.ShortMsgTpl;
import domain.base.ShortMsgTplExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ShortMsgTplService extends BaseMapper {


    @Transactional
    public void insertSelective(ShortMsgTpl record){

        shortMsgTplMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        shortMsgTplMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ShortMsgTplExample example = new ShortMsgTplExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        shortMsgTplMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ShortMsgTpl record){

        return shortMsgTplMapper.updateByPrimaryKeySelective(record);
    }
}
