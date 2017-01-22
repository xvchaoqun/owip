package service.cis;

import domain.cis.*;
import domain.unit.Unit;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CisInspectObjService extends BaseMapper {

    // 生成编号
    public int genSeq(int typeId, int year){

        int seq ;
        CisInspectObjExample example = new CisInspectObjExample();
        example.createCriteria().andYearEqualTo(year).andTypeIdEqualTo(typeId);
        example.setOrderByClause("seq desc");
        List<CisInspectObj> records = cisInspectObjMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(records.size()>0){
            seq = records.get(0).getSeq() + 1;
        }else{
            seq = 1;
        }

        return seq;
    }

    @Transactional
    public void insertSelective(CisInspectObj record) {

        cisInspectObjMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cisInspectObjMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CisInspectObjExample example = new CisInspectObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cisInspectObjMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CisInspectObj record) {

        return cisInspectObjMapper.updateByPrimaryKeySelective(record);
    }

    public List<CisInspectorView> getInspectors(Integer id) {

        List<CisInspectorView> inspectors = new ArrayList<>();
        CisObjInspectorExample example = new CisObjInspectorExample();
        example.createCriteria().andObjIdEqualTo(id);
        List<CisObjInspector> cisObjInspectors = cisObjInspectorMapper.selectByExample(example);
        for (CisObjInspector cisObjInspector : cisObjInspectors) {
            Integer inspectorId = cisObjInspector.getInspectorId();
            CisInspectorView cisInspector = cisInspectorViewMapper.selectByPrimaryKey(inspectorId);
            inspectors.add(cisInspector);
        }

        return inspectors;
    }

    public List<Unit> getUnits(Integer id) {

        List<Unit> units = new ArrayList<>();
        CisObjUnitExample example = new CisObjUnitExample();
        example.createCriteria().andObjIdEqualTo(id);
        List<CisObjUnit> cisObjUnits = cisObjUnitMapper.selectByExample(example);
        for (CisObjUnit cisObjUnit : cisObjUnits) {
            Integer unitId = cisObjUnit.getUnitId();
            Unit unit = unitMapper.selectByPrimaryKey(unitId);
            units.add(unit);
        }

        return units;
    }

    @Transactional
    public void updateSummary(int objId, String summary, Integer[] unitIds) {

        {
            CisInspectObj record = new CisInspectObj();
            record.setId(objId);
            record.setSummary(summary);
            cisInspectObjMapper.updateByPrimaryKeySelective(record);
        }

        CisObjUnitExample example = new CisObjUnitExample();
        example.createCriteria().andObjIdEqualTo(objId);
        cisObjUnitMapper.deleteByExample(example);

        if(unitIds==null || unitIds.length==0) return;

        for (Integer unitId : unitIds) {

            CisObjUnit record = new CisObjUnit();
            record.setObjId(objId);
            record.setUnitId(unitId);
            cisObjUnitMapper.insertSelective(record);
        }

    }
}
