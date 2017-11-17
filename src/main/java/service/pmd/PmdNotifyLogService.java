package service.pmd;

import domain.pmd.PmdNotifyLog;
import domain.pmd.PmdNotifyLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class PmdNotifyLogService extends BaseMapper {

    @Transactional
    public void insertSelective(PmdNotifyLog record) {

        pmdNotifyLogMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        pmdNotifyLogMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdNotifyLogExample example = new PmdNotifyLogExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdNotifyLogMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdNotifyLog record) {

        return pmdNotifyLogMapper.updateByPrimaryKeySelective(record);
    }
}
