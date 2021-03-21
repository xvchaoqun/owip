package service.ces;

import domain.ces.CesResult;
import domain.ces.CesResultExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CesResultService extends BaseMapper {

    public CesResult get(byte type, Integer unitId, Integer cadreId, int year, String name){

        CesResultExample example = new CesResultExample();
        CesResultExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type)
                .andYearEqualTo(year).andNameEqualTo(name);
        if(type == SystemConstants.CES_RESULT_TYPE_UNIT){
            criteria.andUnitIdEqualTo(unitId);
        }else{
            criteria.andCadreIdEqualTo(cadreId);
        }

        List<CesResult> cesResults = cesResultMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cesResults.size()>0?cesResults.get(0):null;
    }

    @Transactional
    public void insertSelective(CesResult record){

        cesResultMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CesResultExample example = new CesResultExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cesResultMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CesResult record){

        cesResultMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int batchImport(byte type, List<CesResult> records) {

        int addCount = 0;
        for (CesResult record : records) {

            Integer unitId = record.getUnitId();
            Integer cadreId = record.getCadreId();
            int year = record.getYear();
            String name = record.getName();

            CesResult cesResult = get(type, unitId, cadreId, year, name);
            if(cesResult==null) {
                insertSelective(record);
                addCount++;
            }else{
                int id = cesResult.getId();
                record.setId(id);
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
    }

}
