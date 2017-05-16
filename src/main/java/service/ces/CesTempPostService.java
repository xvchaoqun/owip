package service.ces;

import domain.ces.CesTempPost;
import domain.ces.CesTempPostExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CesTempPostService extends BaseMapper {


    @Transactional
    public void insertSelective(CesTempPost record) {

        //record.setSortOrder(getNextSortOrder("ces_temp_post", "1=1"));
        cesTempPostMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cesTempPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CesTempPostExample example = new CesTempPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cesTempPostMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CesTempPost record) {

        return cesTempPostMapper.updateByPrimaryKeySelective(record);
    }
}
