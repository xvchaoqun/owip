package service.cadre;

import domain.cadre.CadreConcat;
import domain.cadre.CadreConcatExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CadreConcatService extends BaseMapper {

    public String getCadreMobileByCadreId(int cadreId) {

        CadreConcat cadreConcat = cadreConcatMapper.selectByPrimaryKey(cadreId);
        return (cadreConcat == null) ? null : StringUtils.trimToNull(cadreConcat.getMobile());
    }

    @Transactional
    public void del(Integer cadreId) {

        cadreConcatMapper.deleteByPrimaryKey(cadreId);
    }

    @Transactional
    public void batchDel(Integer[] cadreIds) {

        if (cadreIds == null || cadreIds.length == 0) return;

        CadreConcatExample example = new CadreConcatExample();
        example.createCriteria().andCadreIdIn(Arrays.asList(cadreIds));
        cadreConcatMapper.deleteByExample(example);
    }

    @Transactional
    public void insertOrUpdate(CadreConcat record) {

        Integer cadreId = record.getCadreId();
        CadreConcat cadreConcat = cadreConcatMapper.selectByPrimaryKey(cadreId);
        if (cadreConcat == null) {
            cadreConcatMapper.insertSelective(record);
        } else {
            cadreConcatMapper.updateByPrimaryKeySelective(record);
        }
    }
}
