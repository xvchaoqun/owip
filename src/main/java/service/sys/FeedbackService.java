package service.sys;

import domain.sys.Feedback;
import domain.sys.FeedbackExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class FeedbackService extends BaseMapper {


    @Transactional
    public void insertSelective(Feedback record) {

        feedbackMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        feedbackMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        FeedbackExample example = new FeedbackExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        feedbackMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Feedback record) {
        return feedbackMapper.updateByPrimaryKeySelective(record);
    }
}
