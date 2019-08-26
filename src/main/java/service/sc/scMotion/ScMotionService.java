package service.sc.scMotion;

import domain.sc.scMotion.ScMotion;
import domain.sc.scMotion.ScMotionExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;
import sys.constants.ScConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ScMotionService extends ScBaseMapper {

    public String getNextSeq(Date holdDate) {

        ScMotionExample example = new ScMotionExample();
        ScMotionExample.Criteria criteria = example.createCriteria()
                .andHoldDateEqualTo(holdDate);
        example.setOrderByClause("seq desc");

        List<ScMotion> scMotions = scMotionMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        if(scMotions.size()==0) return DateUtils.formatDate(holdDate, "yyyyMMdd0101");

        ScMotion scMotion = scMotions.get(0);
        String seq = scMotion.getSeq();

        return DateUtils.formatDate(holdDate, "yyyyMMdd01")
                + String.format("%02d", Integer.valueOf(seq.substring(10, 12))+1);
    }

    @Transactional
    public void insertSelective(ScMotion record) {

        record.setSeq(getNextSeq(record.getHoldDate()));

        scMotionMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        scMotionMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ScMotionExample example = new ScMotionExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMotionMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScMotion record) {

        scMotionMapper.updateByPrimaryKeySelective(record);

        if(record.getWay()!=null && record.getWay()== ScConstants.SC_MOTION_WAY_OTHER){
            commonMapper.excuteSql("update sc_motion set committee_topic_id=null, group_topic_id=null where id="+record.getId());
        }
    }
}
