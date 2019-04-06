package service.sys;

import domain.sys.Feedback;
import domain.sys.FeedbackExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;

import java.util.Arrays;

@Service
public class FeedbackService extends BaseMapper {

    @Transactional
    public void insertSelective(Feedback record) {

        Integer fid = record.getFid();

        record.setReplyCount(0);
        if (fid == null) {
            record.setSelfCanEdit(true);
        } else {
            Feedback feedback = feedbackMapper.selectByPrimaryKey(fid);
            if (feedback.getUserId().intValue() == ShiroHelper.getCurrentUserId()) {
                record.setSelfCanEdit(true);
            }else {
                FeedbackExample example = new FeedbackExample();
                example.or().andIdEqualTo(fid);
                example.or().andFidEqualTo(fid);
                Feedback _update = new Feedback();
                _update.setSelfCanEdit(false);
                feedbackMapper.updateByExampleSelective(_update, example);
            }
        }

        feedbackMapper.insertSelective(record);

        if (fid != null) {
            FeedbackExample example = new FeedbackExample();
            example.createCriteria().andFidEqualTo(fid);
            long count = feedbackMapper.countByExample(example);
            commonMapper.excuteSql("update sys_feedback set reply_count = " + count + " where id=" + fid);
        }
    }

    @Transactional
    public void del(Integer id) {

        Feedback feedback = feedbackMapper.selectByPrimaryKey(id);
        feedbackMapper.deleteByPrimaryKey(id);

        Integer fid = feedback.getFid();
        if (fid != null) {
            FeedbackExample example = new FeedbackExample();
            example.createCriteria().andFidEqualTo(fid);
            long count = feedbackMapper.countByExample(example);
            commonMapper.excuteSql("update sys_feedback set reply_count = " + count + " where id=" + fid);
        }
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
