package service.cadre;

import domain.cadre.CadreEva;
import domain.cadre.CadreEvaExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadreEvaService extends BaseMapper {

    public boolean idDuplicate(Integer id, int cadreId, int year){

        CadreEvaExample example = new CadreEvaExample();
        CadreEvaExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId).andYearEqualTo(year);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreEvaMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CadreEva record){

        Assert.isTrue(!idDuplicate(null, record.getCadreId(), record.getYear()), "duplicate");
        cadreEvaMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cadreEvaMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreEvaExample example = new CadreEvaExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreEvaMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreEva record){
        if(record.getCadreId()!=null && record.getType()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getCadreId(), record.getYear()), "duplicate");

        return cadreEvaMapper.updateByPrimaryKeySelective(record);
    }
}
