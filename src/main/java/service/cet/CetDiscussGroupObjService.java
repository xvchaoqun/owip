package service.cet;

import domain.cet.CetDiscussGroupObj;
import domain.cet.CetDiscussGroupObjExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetDiscussGroupObjService extends BaseMapper {

    public CetDiscussGroupObj getByDiscussId(Integer objId, int discussId) {

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        example.createCriteria().andObjIdEqualTo(objId).andDiscussIdEqualTo(discussId);
        List<CetDiscussGroupObj> cetDiscussGroupObjs =
                cetDiscussGroupObjMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetDiscussGroupObjs.size()==1?cetDiscussGroupObjs.get(0):null;
    }

    public CetDiscussGroupObj getByDiscussGroupId(Integer objId, int discussGroupId) {

        CetDiscussGroupObjExample example = new CetDiscussGroupObjExample();
        example.createCriteria().andObjIdEqualTo(objId).andDiscussGroupIdEqualTo(discussGroupId);
        List<CetDiscussGroupObj> cetDiscussGroupObjs =
                cetDiscussGroupObjMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetDiscussGroupObjs.size()==1?cetDiscussGroupObjs.get(0):null;
    }

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
