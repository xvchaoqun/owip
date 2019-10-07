package service.cr;

import domain.cr.CrMeeting;
import domain.cr.CrMeetingExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CrMeetingService extends CrBaseMapper {

    @Transactional
    public void insertSelective(CrMeeting record){

        crMeetingMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        crMeetingMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CrMeetingExample example = new CrMeetingExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crMeetingMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CrMeeting record){

        crMeetingMapper.updateByPrimaryKeySelective(record);
    }
}
