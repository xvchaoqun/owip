package service.cr;

import domain.cr.CrInfo;
import domain.cr.CrInfoExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

@Service
public class CrInfoService extends CrBaseMapper {

    @Transactional
    public void insertSelective(CrInfo record) {

        record.setCreateTime(new Date());

        crInfoMapper.insertSelective(record);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrInfo record) {

        return crInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrInfoExample example = new CrInfoExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        crInfoMapper.deleteByExample(example);
    }
}
