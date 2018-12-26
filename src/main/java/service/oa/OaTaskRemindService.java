package service.oa;

import domain.oa.OaTaskRemind;
import domain.oa.OaTaskRemindExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;

@Service
public class OaTaskRemindService extends OaBaseMapper {

    public boolean idDuplicate(Integer id, int taskId, int userId) {

        OaTaskRemindExample example = new OaTaskRemindExample();
        OaTaskRemindExample.Criteria criteria = example.createCriteria()
                .andTaskIdEqualTo(taskId).andUserIdEqualTo(userId).andIsFinishEqualTo(false);
        if (id != null) criteria.andIdNotEqualTo(id);

        return oaTaskRemindMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(OaTaskRemind record) {

        Assert.isTrue(!idDuplicate(null, record.getTaskId(), record.getUserId()), "重复设置提醒");
        oaTaskRemindMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        oaTaskRemindMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        OaTaskRemindExample example = new OaTaskRemindExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        oaTaskRemindMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(OaTaskRemind record) {
        if (record.getTaskId() != null && record.getUserId() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getTaskId(), record.getUserId()), "重复设置提醒");

        record.setTaskId(null);
        record.setUserId(null);
        return oaTaskRemindMapper.updateByPrimaryKeySelective(record);
    }
}
