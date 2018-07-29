package service.sc.scMotion;

import domain.sc.scMotion.ScMotion;
import domain.sc.scMotion.ScMotionPost;
import domain.sc.scMotion.ScMotionPostExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

@Service
public class ScMotionPostService extends BaseMapper {

    private void updatePostCount(int motionId){

        ScMotionPostExample example = new ScMotionPostExample();
        example.createCriteria().andMotionIdEqualTo(motionId);
        int postCount = (int) scMotionPostMapper.countByExample(example);

        ScMotion record = new ScMotion();
        record.setId(motionId);
        record.setPostCount(postCount);

        scMotionMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void insertSelective(ScMotionPost record){

        scMotionPostMapper.insertSelective(record);

        updatePostCount(record.getMotionId());
    }

    @Transactional
    public void del(Integer id){

        ScMotionPost scMotionPost = scMotionPostMapper.selectByPrimaryKey(id);
        scMotionPostMapper.deleteByPrimaryKey(id);

        updatePostCount(scMotionPost.getMotionId());
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            del(id);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScMotionPost record){

        record.setMotionId(null);
        scMotionPostMapper.updateByPrimaryKeySelective(record);
    }
}
