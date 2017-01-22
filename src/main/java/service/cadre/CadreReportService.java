package service.cadre;

import domain.cadre.CadreReport;
import domain.cadre.CadreReportExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadreReportService extends BaseMapper {

    @Transactional
    public void insertSelective(CadreReport record){

        cadreReportMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cadreReportMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreReportExample example = new CadreReportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreReportMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreReport record){
        return cadreReportMapper.updateByPrimaryKeySelective(record);
    }
}
