package service.crs;

import domain.crs.CrsPostFile;
import domain.crs.CrsPostFileExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CrsPostFileService extends BaseMapper {

    @Transactional
    public void insertSelective(CrsPostFile record) {

        crsPostFileMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        crsPostFileMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsPostFileExample example = new CrsPostFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crsPostFileMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsPostFile record) {
        return crsPostFileMapper.updateByPrimaryKeySelective(record);
    }
}
