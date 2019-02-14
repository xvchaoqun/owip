package service.cis;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cis.*;
import domain.sys.SysUserView;
import domain.unit.Unit;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.HtmlUtils;
import service.common.FreemarkerService;
import sys.constants.CisConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.*;

@Service
public class CisInspectObjService extends CisBaseMapper {

    @Autowired
    private FreemarkerService freemarkerService;

    // 输出考察报告
    public Map<String, Object> getDataMap(CisInspectObj cisInspectObj) throws IOException, TemplateException {

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
        List<CisInspector> inspectors = cisInspectObj.getInspectors();
        if(inspectors!=null && inspectors.size()>0) {
            for (CisInspector inspector : inspectors) {
                names.add(inspector.getUser().getRealname());
            }
            dataMap.put("inspectors", StringUtils.join(names, "，"));
        }

        String content = HtmlUtils.htmlUnescape(cisInspectObj.getSummary());
        dataMap.put("content", content);
        dataMap.put("info", freemarkerService.genTextareaSegment(content, "/common/editor.ftl"));

        CisInspector chiefInspector = cisInspectObj.getChiefInspector();
        dataMap.put("chief", chiefInspector.getUser().getRealname());
        dataMap.put("remark", cisInspectObj.getRemark());
        //dataMap.put("exportDate", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        dataMap.put("schoolName", CmTag.getSysConfig().getSchoolName());

        return dataMap;
    }

    public void process(Map<String, Object> dataMap, Writer out) throws IOException, TemplateException {

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

        {
            CisInspectObjViewExample example = new CisInspectObjViewExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andArchiveIdIsNotNull();
            if(cisInspectObjViewMapper.countByExample(example)>0){

                throw new OpException("不可删除已归档的记录");
            }
        }
        {
            CisInspectObjExample example = new CisInspectObjExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cisInspectObjMapper.deleteByExample(example);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(CisInspectObj record) {

        if(record.getInspectorType()==CisConstants.CIS_INSPECTOR_TYPE_OTHER){
            commonMapper.excuteSql("delete from cis_obj_inspector where obj_id="+record.getId());
        }
        return cisInspectObjMapper.updateByPrimaryKeySelective(record);
    }

    public List<CisInspector> getInspectors(Integer id) {

        List<CisInspector> inspectors = new ArrayList<>();
        CisObjInspectorExample example = new CisObjInspectorExample();
        example.createCriteria().andObjIdEqualTo(id);

        List<CisObjInspector> cisObjInspectors = cisObjInspectorMapper.selectByExample(example);
        for (CisObjInspector cisObjInspector : cisObjInspectors) {
            Integer inspectorId = cisObjInspector.getInspectorId();
            CisInspector cisInspector = cisInspectorMapper.selectByPrimaryKey(inspectorId);
            inspectors.add(cisInspector);
        }

        Collections.sort(inspectors, new Comparator<CisInspector>() {
            @Override
            public int compare(CisInspector o1, CisInspector o2) {
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
    public void updateSummary(Integer[] unitIds, CisInspectObj record) {

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
    }

    // 导出工作安排
    public void export(CisInspectObjViewExample example, HttpServletResponse response) throws IOException {

        example.setOrderByClause("inspect_date asc, id asc");
        List<CisInspectObjView> records = cisInspectObjViewMapper.selectByExample(example);
        int size = records.size();
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cis/cis_inspector_obj.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFSheet templateSheet = wb.getSheetAt(1);
        {
            CisInspectObjView record = records.get(0);
            renderObj(sheet, record, 0);
        }

        int i = 0;
        for (; i < size - 1; i++) {

            ExcelUtils.copyRows(1, 5, 5 * (i + 1), templateSheet, sheet);

            XSSFRow indexRow = sheet.getRow(1 + 5 * (i + 1));
            indexRow.getCell(0).setCellValue(i + 2);

            CisInspectObjView record = records.get(i + 1);
            renderObj(sheet, record, 5 * (i + 1));
        }

        wb.removeSheetAt(1);// 移除模板
        ExportHelper.output(wb, "干部考察工作安排.xlsx", response);
    }

    private void renderObj(XSSFSheet sheet, CisInspectObjView record, int rowNum) {

        CadreView cadre = record.getCadre();

        XSSFRow row = sheet.getRow(rowNum++);
        // 考察日期
        XSSFCell cel = row.getCell(2);
        cel.setCellValue(DateUtils.formatDate(record.getInspectDate(), DateUtils.YYYY_MM_DD_CHINA));

        // 考察对象
        cel = row.getCell(4);
        cel.setCellValue(cadre.getRealname());

        // 所在单位及职务
        row = sheet.getRow(rowNum++);
        cel = row.getCell(2);
        cel.setCellValue(record.getPost());

        // 拟任职务
        row = sheet.getRow(rowNum++);
        cel = row.getCell(2);
        cel.setCellValue(record.getAssignPost());

        // 考察主体
        row = sheet.getRow(rowNum++);
        cel = row.getCell(2);
        String _inspectorType = "党委组织部";
        Byte inspectorType = record.getInspectorType();
        if(inspectorType!= CisConstants.CIS_INSPECTOR_TYPE_OW)
            _inspectorType = record.getOtherInspectorType();
        cel.setCellValue(_inspectorType);

        // 考察组负责人
        cel = row.getCell(4);
        CisInspector chiefInspector = record.getChiefInspector();
        cel.setCellValue(chiefInspector==null?"":chiefInspector.getUser().getRealname());

        // 考察组成员
        row = sheet.getRow(rowNum++);
        cel = row.getCell(2);
        String _inspectors = "";
        List<String> names = new ArrayList<>();
        List<CisInspector> inspectors = record.getInspectors();
        if(inspectors!=null && inspectors.size()>0) {
            for (CisInspector inspector : inspectors) {
                names.add(inspector.getUser().getRealname());
            }
            _inspectors = StringUtils.join(names, "，");
        }
        cel.setCellValue(_inspectors);
    }
}
