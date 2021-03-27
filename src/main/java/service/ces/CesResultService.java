package service.ces;

import domain.cadre.CadreView;
import domain.ces.CesResult;
import domain.ces.CesResultExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DownloadUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.User;
import java.util.*;

@Service
public class CesResultService extends BaseMapper {

    public CesResult get(byte type, Integer unitId, Integer cadreId, int year, String name){

        CesResultExample example = new CesResultExample();
        CesResultExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type)
                .andYearEqualTo(year).andNameEqualTo(name);
        if(type == SystemConstants.CES_RESULT_TYPE_UNIT){
            criteria.andUnitIdEqualTo(unitId);
        }else{
            if (cadreId != null) {
                criteria.andCadreIdEqualTo(cadreId);
            }
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

    public void cesResult_export(Integer[] ids, byte type, Integer cadreId, Integer unitId, HttpServletRequest request, HttpServletResponse response) {
        CesResultExample example =new CesResultExample();
        CesResultExample.Criteria criteria =example.createCriteria();
        example.setOrderByClause("year desc");
        criteria.andTypeEqualTo(type);

        if(ids != null && ids.length > 0){
            criteria.andIdIn(Arrays.asList(ids));
        } else {
            if (cadreId != null) {
                criteria.andCadreIdEqualTo(cadreId);
            }
            if (unitId != null) {
                criteria.andUnitIdEqualTo(unitId);
            }
        }
        List<CesResult> records = cesResultMapper.selectByExample(example);
        List<String> titles = new ArrayList(Arrays.asList("年份|80"));
        if (type == 1) {
            titles.add("工作证号|100");
            titles.add("姓名|100");
        }
        titles.add(type == SystemConstants.CES_RESULT_TYPE_CADRE ? "时任单位|250" : "班子名称|250");
        titles.add("时任职务|250");
        titles.add("测评类别|200");
        titles.add("排名|100");
        titles.add(type == SystemConstants.CES_RESULT_TYPE_CADRE ? "总人数|100" : "班子总人数|100");
        titles.add("备注|200");

        String fileName = "年终考核结果";
        fileName = HtmlUtils.htmlUnescape(fileName);
        SXSSFWorkbook wb = new SXSSFWorkbook(500);
        createSheet(null, wb, titles, records, type);
        output(wb, (type == 1 ? "干部" : "班子") + fileName + ".xlsx", request, response);

    }

    public static void output(Workbook wb, String filename, HttpServletRequest request, HttpServletResponse response) {

        filename = HtmlUtils.htmlUnescape(filename);

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            filename = DownloadUtils.encodeFilename(request, filename);
            // jquery.fileDownload.js 用于回调
            DownloadUtils.addFileDownloadCookieHeader(response);
            //response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xlsx");
            response.setHeader("Content-disposition", "attachment; filename=" + filename);
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
//            logger.error("export error.", e);
        }
    }

    public static void createSheet(String sheetName, SXSSFWorkbook wb, List<String> titles, List<CesResult> valuesList, byte type) {

        Sheet sheet = createSafeSheet(wb, sheetName);
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
//                    logger.error("export error.", e);
                }
            }
            if (split.length > 2) {
                aligns[i] = split[2];
            } else {
                aligns[i] = null;
            }
        }
        CellStyle centerCellStyle = createCenterCellStyle(wb);
        int col = 0;
        for (int i = 0; i < valuesList.size(); i++) {
            CesResult cesResult = valuesList.get(i);
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(cesResult.getYear());

            if (type == 1) {
                SysUserView user = cesResult.getCadre().getUser();
                cell = row.createCell(++col);
                cell.setCellStyle(centerCellStyle);
                cell.setCellValue(user.getCode());

                cell = row.createCell(++col);
                cell.setCellStyle(centerCellStyle);
                cell.setCellValue(user.getRealname());
            }

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(cesResult.getUnit().getName());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(cesResult.getTitle());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(cesResult.getName());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(cesResult.getNum());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(cesResult.getRank());

            cell = row.createCell(++col);
            cell.setCellStyle(centerCellStyle);
            cell.setCellValue(cesResult.getRemark());
            col = 0;
        }
    }

    public static CellStyle createCenterCellStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true); // 自动换行
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    // 新建sheet，保证不重名
    public static Sheet createSafeSheet(Workbook wb, String sheetName) {

        Sheet sheet = null;
        if (StringUtils.isNotBlank(sheetName)) {
            // 保证sheetName不重复
            Set<String> sheetNames = new HashSet<>();
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet _sheet = wb.getSheetAt(i);
                sheetNames.add(_sheet.getSheetName());
            }
            int idx = 1;
            String tmp = WorkbookUtil.createSafeSheetName(sheetName);
            while (sheetNames.contains(tmp)) {
                tmp = sheetName + "-" + idx;
                idx++;
            }
            sheetName = tmp;
            sheet = wb.createSheet(sheetName);

        } else {
            sheet = wb.createSheet();
        }
        return sheet;
    }

}
