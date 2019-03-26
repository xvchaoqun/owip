package service.unit;

import domain.unit.UnitFunction;
import domain.unit.UnitFunctionExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class UnitFunctionService extends BaseMapper {

    @Transactional
    public void insertSelective(UnitFunction record) {

        unitFunctionMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        UnitFunctionExample example = new UnitFunctionExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitFunctionMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(UnitFunction record) {
        return unitFunctionMapper.updateByPrimaryKeySelective(record);
    }

    public List<UnitFunction> getUnitFunctions(int unitId) {

        UnitFunctionExample example = new UnitFunctionExample();
        example.createCriteria().andUnitIdEqualTo(unitId);
        example.setOrderByClause("is_current desc, confirm_time desc");
        return unitFunctionMapper.selectByExample(example);
    }
}
