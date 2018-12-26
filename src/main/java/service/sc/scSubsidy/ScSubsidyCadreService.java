package service.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidyCadre;
import domain.sc.scSubsidy.ScSubsidyCadreExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.sc.ScBaseMapper;

import java.util.Arrays;

@Service
public class ScSubsidyCadreService extends ScBaseMapper {

    public boolean idDuplicate(Integer id, int subsidyId, int cadreId){

        ScSubsidyCadreExample example = new ScSubsidyCadreExample();
        ScSubsidyCadreExample.Criteria criteria = example.createCriteria()
                .andSubsidyIdEqualTo(subsidyId).andCadreIdEqualTo(cadreId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scSubsidyCadreMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScSubsidyCadre record){

        Assert.isTrue(!idDuplicate(null, record.getSubsidyId(), record.getCadreId()), "duplicate");
        scSubsidyCadreMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scSubsidyCadreMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScSubsidyCadreExample example = new ScSubsidyCadreExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scSubsidyCadreMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScSubsidyCadre record){
        if(record.getSubsidyId()!=null && record.getCadreId()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getSubsidyId(), record.getCadreId()), "duplicate");
        return scSubsidyCadreMapper.updateByPrimaryKeySelective(record);
    }
}
