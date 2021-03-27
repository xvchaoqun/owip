package service.cadre;

import domain.cadre.CadreEva;
import domain.cadre.CadreEvaExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.util.HtmlUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.ces.CesResultService;
import sys.constants.CadreConstants;
import sys.tags.CmTag;
import sys.utils.ExportHelper;
import sys.utils.MSUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class CadreEvaService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean idDuplicate(Integer id, Integer cadreId, Integer year){

        CadreEvaExample example = new CadreEvaExample();
        CadreEvaExample.Criteria criteria = example.createCriteria();
        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
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

                /*if(StringUtils.isBlank(record.getTitle())){
                    commonMapper.excuteSql("update cadre_eva set title = null where id="+id);
                }*/
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

    // 导出年度考核结果
    public void export(int startYear, int endYear, Integer[] ids, Byte status, int exportType,
                       Integer reserveType, HttpServletResponse response) {

        List<CadreEva> cadreEvas = new ArrayList<>();
        String preStr = "";
        if (exportType == 0){
            cadreEvas = iCadreMapper.getCadreEvas(startYear, endYear, ids, status);//现任干部
        }else {
            preStr = metaTypeService.getName(reserveType);
            cadreEvas = iCadreMapper.getCadreReserveEvas(startYear, endYear, ids, reserveType, CadreConstants.CADRE_RESERVE_STATUS_NORMAL);
        }

        // <cadreId, <year, cadreEva>>
        Map<Integer, Map<Integer, CadreEva>> resultMap = new LinkedHashMap<>();

        for (CadreEva cadreEva : cadreEvas) {

            int cadreId = cadreEva.getCadreId();
            Map<Integer, CadreEva> cadreEvaMap = resultMap.get(cadreId);
            if(cadreEvaMap==null){
                cadreEvaMap = new HashMap<>();
                resultMap.put(cadreId, cadreEvaMap);
            }

            cadreEvaMap.put(cadreEva.getYear(), cadreEva);
        }

        List<String> titles = new ArrayList(Arrays.asList("工作证号|100", "姓名|80", "所在单位及职务|250|left"));
        for (int i = endYear; i >= startYear ; i--) {
            titles.add(i + "年|80");
        }

        List<List<String>> valuesList = new ArrayList<>();

        for (Map.Entry<Integer, Map<Integer, CadreEva>> entry : resultMap.entrySet()) {

            int cadreId = entry.getKey();
            Map<Integer, CadreEva> cadreEvaMap = entry.getValue();

            CadreView cadre = CmTag.getCadreById(cadreId);
            List<String> values = new ArrayList(Arrays.asList(cadre.getCode(), cadre.getRealname(), cadre.getTitle()));
            for (int i = endYear; i >= startYear ; i--) {

                String eva = null;
                CadreEva cadreEva = cadreEvaMap.get(i);
                if(cadreEva!=null){
                    eva = CmTag.getMetaTypeName(cadreEva.getType());
                }

                values.add(StringUtils.trimToEmpty(eva));
            }

            valuesList.add(values);
        }

        String fileName = preStr + "年度考核结果(" + startYear +"-" + endYear + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    public void cadreEva_export(Integer[] ids, Integer cadreId, HttpServletRequest request, HttpServletResponse response) {
        CadreEvaExample example =new CadreEvaExample();
        CadreEvaExample.Criteria criteria =example.createCriteria();
        example.setOrderByClause("year desc");
        if(ids != null && ids.length > 0){
            criteria.andIdIn(Arrays.asList(ids));
        } else {
            if (cadreId != null) {
                criteria.andCadreIdEqualTo(cadreId);
            }
        }
        List<CadreEva> records = cadreEvaMapper.selectByExample(example);
        List<String> titles = new ArrayList(Arrays.asList("年份|80"));
        titles.add("工作证号|100");
        titles.add("姓名|100");
        titles.add("考核情况|100");
        titles.add("时任职务|250");
        titles.add("备注|200");

        String fileName = "干部年度考核记录";
        fileName = HtmlUtils.htmlUnescape(fileName);
        SXSSFWorkbook wb = new SXSSFWorkbook(500);
        createSheet(null, wb, titles, records);
        CesResultService.output(wb, fileName + ".xlsx", request, response);

    }

    public void createSheet(String sheetName, SXSSFWorkbook wb, List<String> titles, List<CadreEva> valuesList) {
        Sheet sheet = CesResultService.createSafeSheet(wb, sheetName);
        sheet.setDefaultRowHeightInPoints(30);
        Row firstRow = sheet.createRow(0);

        CellStyle headStyle2 = MSUtils.getHeadStyle2(wb);

        String[] aligns = new String[titles.size()];
        int width;
        for (int i = 0; i < titles.size(); i++) {

            String _title = titles.get(i);
            String[] split = _title.split("\\|");
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(split[0]);
            cell.setCellStyle(headStyle2);
            if (split.length > 1) {
                try {
                    width = Integer.valueOf(split[1]);
                    sheet.setColumnWidth(i, (short) (35.7 * width));
                } catch (Exception e) {
                    logger.error("export error.", e);
                }
            }
            if (split.length > 2) {
                aligns[i] = split[2];
            } else {
                aligns[i] = null;
            }
        }
        CellStyle centerCellStyle = CesResultService.createCenterCellStyle(wb);
        int col = 0;
        for (int i = 0; i < valuesList.size(); i++) {
            CadreEva record = valuesList.get(i);
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(record.getYear());

            SysUserView user = record.getCadre().getUser();
            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(user.getCode());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(user.getRealname());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(metaTypeMapper.selectByPrimaryKey(record.getType()).getName());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(record.getTitle());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(record.getRemark());
            col = 0;
        }
    }

}
