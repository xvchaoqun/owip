package service.cet;

import domain.cet.CetDiscussGroupObj;
import domain.cet.CetDiscussGroupObjExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CetDiscussGroupObjService extends BaseMapper {

    @Transactional
    public void insertSelective(CetDiscussGroupObj record){

        cetDiscussGroupObjMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetDiscussGroupObjMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetDiscussGroupObjMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetDiscussGroupObj record){

        return cetDiscussGroupObjMapper.updateByPrimaryKeySelective(record);
    }
}
