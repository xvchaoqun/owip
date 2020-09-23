package service.oa;

import controller.global.OpException;
import domain.oa.OaGridParty;
import domain.oa.OaGridPartyData;
import domain.oa.OaGridPartyDataExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sys.utils.ExcelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OaGridPartyDataService extends OaBaseMapper {

    @Transactional
    public void insertSelective(OaGridPartyData record){

        oaGridPartyDataMapper.insertSelective(record);
    }

    @Transactional
    public void delByGridPartyId(Integer gridPartyId){

        if(gridPartyId == null) return;

        OaGridPartyDataExample example = new OaGridPartyDataExample();
        example.createCriteria().andGridPartyIdEqualTo(gridPartyId);
        oaGridPartyDataMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(OaGridPartyData record){

        oaGridPartyDataMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, OaGridPartyData> findAll() {

        OaGridPartyDataExample example = new OaGridPartyDataExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<OaGridPartyData> records = oaGridPartyDataMapper.selectByExample(example);
        Map<Integer, OaGridPartyData> map = new LinkedHashMap<>();
        for (OaGridPartyData record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void importData(OaGridParty record, MultipartFile _excelFilePath) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(_excelFilePath.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Integer gridPartyId = record.getId();
        String readOnlyPos = record.getGrid().getReadonlyPos();
        String startPos = record.getGrid().getStartPos();
        String endPos = record.getGrid().getEndPos();
        int startRow = ExcelUtils.getRowIndex(startPos) - 1;
        int startCol = ExcelUtils.getColIndex(startPos) - 1;
        int endRow = ExcelUtils.getRowIndex(endPos) - 1;
        int endCol = ExcelUtils.getColIndex(endPos) - 1;

        int row = startRow;
        List<OaGridPartyData> records = new ArrayList<>();

        while (row <= endRow){
            Row dataRow = sheet.getRow(row);
            if (dataRow != null) {
                row++;
                int col = startCol;
                while (col <= endCol){
                    OaGridPartyData data = new OaGridPartyData();
                    data.setGridPartyId(gridPartyId);
                    data.setCellLabel(ExcelUtils.toColLabel(col+1) + row);
                    Cell num = dataRow.getCell(col++);
                    try {
                        //为空或者只读的表格略过
                        if (num == null || StringUtils.isBlank(getValue(num)) || (readOnlyPos != null &&
                                ExcelUtils.inCellArea(ExcelUtils.toColLabel(col) + row, readOnlyPos))){
                            continue;
                        }
                        data.setNum(Double.valueOf(getValue(num)).intValue());
                    }catch (Exception e){
                        throw new OpException("报送表格格式或数据有误，请严格按表格模板填写后提交");
                    }

                    records.add(data);
                }

                if (row > endRow){
                    break;
                }
            }else {
                throw new OpException("报送表格格式或数据有误，请严格按表格模板填写后提交");
            }
        }

        batchimport(records);

    }

    public String getValue(Cell cell) {
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA){
            return null;
        }else{
            return String.valueOf(cell.getStringCellValue());
        }
    }

    @Transactional
    public  void batchimport(List<OaGridPartyData> records) {

        if (records == null || records.size() == 0) return;

        delByGridPartyId(records.get(0).getGridPartyId());

        for (OaGridPartyData record : records) {
            oaGridPartyDataMapper.insertSelective(record);
        }

    }
}
