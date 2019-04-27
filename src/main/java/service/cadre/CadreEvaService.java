package service.cadre;

import domain.cadre.CadreEva;
import domain.cadre.CadreEvaExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CadreEvaService extends BaseMapper {

    public boolean idDuplicate(Integer id, int cadreId, int year){

        CadreEvaExample example = new CadreEvaExample();
        CadreEvaExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId).andYearEqualTo(year);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreEvaMapper.countByExample(example) > 0;
    }
    public CadreEva get(int cadreId, int year){

        CadreEvaExample example = new CadreEvaExample();
        CadreEvaExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId).andYearEqualTo(year);

        List<CadreEva> cadreEvas = cadreEvaMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cadreEvas.size()>0?cadreEvas.get(0):null;
    }

    @Transactional
    public void insertSelective(CadreEva record){

        Assert.isTrue(!idDuplicate(null, record.getCadreId(), record.getYear()), "duplicate");
        cadreEvaMapper.insertSelective(record);
    }

    @Transactional
    public int batchImport(List<CadreEva> records) {

        int addCount = 0;
        for (CadreEva record : records) {

            int cadreId = record.getCadreId();
            int year = record.getYear();
            CadreEva cadreEva = get(cadreId, year);
            if(cadreEva==null) {
                insertSelective(record);
                addCount++;
            }else{
                int id = cadreEva.getId();
                record.setId(id);
                updateByPrimaryKeySelective(record);

                if(StringUtils.isBlank(record.getTitle())){
                    commonMapper.excuteSql("update cadre_eva set title = null where id="+id);
                }
            }
        }

        return addCount;
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
