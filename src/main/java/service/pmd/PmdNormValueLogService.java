package service.pmd;

import domain.pmd.PmdNormValueLog;
import domain.pmd.PmdNormValueLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class PmdNormValueLogService extends PmdBaseMapper {

    @Transactional
    public void insertSelective(PmdNormValueLog record){

        pmdNormValueLogMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pmdNormValueLogMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmdNormValueLogExample example = new PmdNormValueLogExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdNormValueLogMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdNormValueLog record){
        return pmdNormValueLogMapper.updateByPrimaryKeySelective(record);
    }
}
