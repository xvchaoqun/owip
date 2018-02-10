package service.cis;

import domain.cadre.CadreView;
import domain.cis.CisInspectObj;
import domain.cis.CisInspectObjExample;
import domain.cis.CisInspectorView;
import domain.cis.CisObjInspector;
import domain.cis.CisObjInspectorExample;
import domain.cis.CisObjUnit;
import domain.cis.CisObjUnitExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.common.FreemarkerService;
import sys.constants.CisConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CisInspectObjService extends BaseMapper {

    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private CisObjInspectorService cisObjInspectorService;

    // 输出考察报告
    public void process(int objId, Writer out) throws IOException, TemplateException {

        CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
        CadreView cadre = cisInspectObj.getCadre();
        SysUserView uv = cadre.getUser();

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("realname", uv.getRealname());
        dataMap.put("code", uv.getCode());
        dataMap.put("post", cisInspectObj.getPost());

        // 主体
        Byte inspectorType = cisInspectObj.getInspectorType();
        if(inspectorType== CisConstants.CIS_INSPECTOR_TYPE_OW)
            dataMap.put("inspectorType", "党委组织部");
        else
            dataMap.put("inspectorType", cisInspectObj.getOtherInspectorType());

        dataMap.put("inspectDate", DateUtils.formatDate(cisInspectObj.getInspectDate(), DateUtils.YYYY_MM_DD_CHINA));
        List<String> names = new ArrayList<>();
        List<CisInspectorView> inspectors = cisInspectObj.getInspectors();
        if(inspectors!=null && inspectors.size()>0) {
            for (CisInspectorView inspector : inspectors) {
                names.add(inspector.getRealname());
            }
            dataMap.put("inspectors", StringUtils.join(names, "，"));
        }

        dataMap.put("info", freemarkerService.genEditorSegment(cisInspectObj.getSummary()));

        CisInspectorView chiefInspector = cisInspectObj.getChiefInspector();
        dataMap.put("chief", chiefInspector.getRealname());
        dataMap.put("remark", cisInspectObj.getRemark());
        //dataMap.put("exportDate", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());
        freemarkerService.process("/cis/evaReport.ftl", dataMap, out);
    }

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

        if(record.getInspectorType()==CisConstants.CIS_INSPECTOR_TYPE_OTHER){
            commonMapper.excuteSql("delete from cis_obj_inspector where obj_id="+record.getId());
        }
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

        Collections.sort(inspectors, new Comparator<CisInspectorView>() {
            @Override
            public int compare(CisInspectorView o1, CisInspectorView o2) {
                return o2.getSortOrder().compareTo(o1.getSortOrder());
            }
        });

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
    public void updateSummary(Integer[] unitIds, Integer[] inspectorIds, CisInspectObj record) {

        cisInspectObjMapper.updateByPrimaryKeySelective(record);
        int objId = record.getId();
        CisObjUnitExample example = new CisObjUnitExample();
        example.createCriteria().andObjIdEqualTo(objId);
        cisObjUnitMapper.deleteByExample(example);

        if(unitIds!=null && unitIds.length>0) {
            for (Integer unitId : unitIds) {

                CisObjUnit _record = new CisObjUnit();
                _record.setObjId(objId);
                _record.setUnitId(unitId);
                cisObjUnitMapper.insertSelective(_record);
            }
        }

        cisObjInspectorService.updateInspectIds(objId, inspectorIds);
    }
}
