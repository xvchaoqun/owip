package service.oa;

import controller.global.OpException;
import domain.oa.OaGridParty;
import domain.oa.OaGridPartyData;
import domain.oa.OaGridPartyDataExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.RecordFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sys.utils.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OaGridPartyDataService extends OaBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

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

        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(_excelFilePath.getInputStream());
        }catch (RecordFormatException ex){
            throw new OpException("Excel版本过低，请使用高版本的Office Excel打开此文件并保存后重新上传。");
        }

        importData(record, workbook);
    }

    @Transactional
    public void importData(OaGridParty record, File file) throws IOException, InvalidFormatException {

        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        }catch (RecordFormatException ex){
            throw new OpException("Excel版本过低，请使用高版本的Office Excel打开此文件并保存后重新上传。");
        }

        importData(record, workbook);
    }

    public void importData(OaGridParty record, Workbook workbook) throws IOException, InvalidFormatException {


        Sheet sheet = workbook.getSheetAt(0);

        Integer gridPartyId = record.getId();
        String readOnlyPos = record.getOaGrid().getReadonlyPos();
        String startPos = record.getOaGrid().getStartPos();
        String endPos = record.getOaGrid().getEndPos();
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
                    Cell cell = dataRow.getCell(col++);
                    try {

                        String val = ExcelUtils.getCellValue(cell);
                        //为空或者只读的表格略过
                        if (cell == null || StringUtils.isBlank(val) || (readOnlyPos != null &&
                                ExcelUtils.inCellArea(ExcelUtils.toColLabel(col) + row, readOnlyPos))){
                            continue;
                        }
                        data.setNum(Double.valueOf(val).intValue());
                    }catch (Exception ex){

                        logger.info("党统表格读取数据异常：", ex);
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

    @Transactional
    public  void batchimport(List<OaGridPartyData> records) {

        if (records == null || records.size() == 0) return;

        delByGridPartyId(records.get(0).getGridPartyId());

        for (OaGridPartyData record : records) {
            oaGridPartyDataMapper.insertSelective(record);
        }

    }
}
