package service.cis;

import domain.cadre.Cadre;
import domain.cis.*;
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
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CisInspectObjService extends BaseMapper {

    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private CisObjInspectorService cisObjInspectorService;

    // 输出考察报告
    public void process(int objId, Writer out) throws IOException, TemplateException {

        CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
        Cadre cadre = cisInspectObj.getCadre();
        SysUserView uv = cadre.getUser();

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("realname", uv.getRealname());
        dataMap.put("code", uv.getCode());
        dataMap.put("post", cisInspectObj.getPost());

        // 主体
        Byte inspectorType = cisInspectObj.getInspectorType();
        if(inspectorType==SystemConstants.CIS_INSPECTOR_TYPE_OW)
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

        dataMap.put("info", freemarkerService.genSegment2(cisInspectObj.getSummary(), "/common/seg.ftl"));

        CisInspectorView chiefInspector = cisInspectObj.getChiefInspector();
        dataMap.put("chief", chiefInspector.getRealname());
        dataMap.put("remark", cisInspectObj.getRemark());
        //dataMap.put("exportDate", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        freemarkerService.process("/cis/eva.ftl", dataMap, out);
    }

    /*private String genSegment(String title, String content, String ftlPath) throws IOException, TemplateException {

        *//*String conent = "<p>\n" +
                "\t1987.09-1991.07&nbsp;内蒙古大学生物学系植物生态学&nbsp;\n" +
                "</p>\n" +
                "<p>\n" +
                "\t1994.09-1997.07&nbsp;北京师范大学资源与环境学院自然地理学&nbsp;管理学博士\n" +
                "</p>";*//*
        //System.out.println(getStringNoBlank(info));
        List rows = new ArrayList();

        Pattern p = Pattern.compile("<p(.*)>([^/]*)</p>");
        Matcher matcher = p.matcher(content);
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            int type = 0;
            if (StringUtils.contains(matcher.group(1), "2em"))
                type = 1;
            if (StringUtils.contains(matcher.group(1), "5em"))
                type = 2;
            String group = matcher.group(2);
            List cols = new ArrayList();
            cols.add(type);

            for (String col : group.trim().split("&nbsp;")) {
                cols.add(col.trim());
            }
            rows.add(cols);
        }
        if(matchCount==0){
            List cols = new ArrayList();
            cols.add(0);
            cols.add(content);
            rows.add(cols);
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("dataList", rows);

        return freemarkerService.process(ftlPath, dataMap);
    }*/

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

        if(record.getInspectorType()==SystemConstants.CIS_INSPECTOR_TYPE_OTHER){
            updateMapper.excuteSql("delete from cis_obj_inspector where obj_id="+record.getId());
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

        if(unitIds==null || unitIds.length==0) return;

        for (Integer unitId : unitIds) {

            CisObjUnit _record = new CisObjUnit();
            _record.setObjId(objId);
            _record.setUnitId(unitId);
            cisObjUnitMapper.insertSelective(_record);
        }

        cisObjInspectorService.updateInspectIds(objId, inspectorIds);
    }
}
