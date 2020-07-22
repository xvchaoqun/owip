package service.dr;

import domain.dr.DrOnline;
import domain.dr.DrOnlineExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.DrConstants;

import java.util.*;

@Service
public class DrOnlineService extends DrBaseMapper {

    @Transactional
    public void insertSelective(DrOnline record){

        record.setStatus(DrConstants.DR_ONLINE_INIT);
        record.setSeq(getNextSeq(record.getRecommendDate()));
        drOnlineMapper.insertSelective(record);
    }

    //假删除
    @Transactional
    public void fakeDel(Integer[] ids, boolean isDeleted){

        if(ids==null || ids.length==0) return;

        DrOnline record = new DrOnline();
        record.setIsDeleted(isDeleted);

        DrOnlineExample example = new DrOnlineExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineMapper.updateByExampleSelective(record, example);
    }

    // 批量删除岗位（相关信息全部级联删除）
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DrOnlineExample example = new DrOnlineExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOnline record){

        drOnlineMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DrOnline> findAll() {

        DrOnlineExample example = new DrOnlineExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DrOnline> records = drOnlineMapper.selectByExample(example);
        Map<Integer, DrOnline> map = new LinkedHashMap<>();
        for (DrOnline record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    public int getNextSeq(Date recommendDate) {

        DrOnlineExample example = new DrOnlineExample();
        example.createCriteria().andRecommendDateEqualTo(recommendDate);
        example.setOrderByClause("seq desc");

        List<DrOnline> records = drOnlineMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        int seq = 1;
        if(records.size()>0) {
            DrOnline record = records.get(0);
            seq = record.getSeq() + 1;
        }

        return seq;
    }

    @Transactional
    public void changeStatus(Integer[] ids, Byte status){

        if(ids==null || ids.length==0) return;

        DrOnline record = new DrOnline();
        record.setStatus(status);
        record.setIsDeleted(false);

        DrOnlineExample example = new DrOnlineExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineMapper.updateByExampleSelective(record, example);
    }
}
