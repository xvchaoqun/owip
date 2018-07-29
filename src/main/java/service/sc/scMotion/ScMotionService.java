package service.sc.scMotion;

import domain.sc.scMotion.ScMotion;
import domain.sc.scMotion.ScMotionExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ScMotionService extends BaseMapper {

    public boolean idDuplicate(Integer id, short year, int num) {

        ScMotionExample example = new ScMotionExample();
        ScMotionExample.Criteria criteria = example.createCriteria()
                .andYearEqualTo(year).andNumEqualTo(num);
        if (id != null) criteria.andIdNotEqualTo(id);

        return scMotionMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScMotion record) {

        Assert.isTrue(!idDuplicate(null, record.getYear(), record.getNum()), "duplicate");
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
    public int updateByPrimaryKeySelective(ScMotion record) {
        Assert.isTrue(!idDuplicate(record.getId(), record.getYear(), record.getNum()), "duplicate");
        return scMotionMapper.updateByPrimaryKeySelective(record);
    }
}
