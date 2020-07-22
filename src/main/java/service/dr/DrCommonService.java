package service.dr;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import domain.dr.DrOnline;
import domain.dr.DrOnlinePostView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.dr.common.DrFinalResult;
import persistence.dr.common.DrTempResult;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
public class DrCommonService extends DrBaseMapper{

    @Autowired
    private DrOnlineResultService drOnlineResultService;
    @Autowired
    private DrOnlinePostService drOnlinePostService;

    //转换暂存票数
    public DrTempResult getTempResult(String tempData){

        DrTempResult tempResult = null;

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", DrTempResult.class);

        if (StringUtils.isNotBlank(tempData)){
            tempResult = (DrTempResult) xStream.fromXML(tempData);
        }

        tempResult = (tempResult == null) ? new DrTempResult() : tempResult;

        return tempResult;
    }

    public String getStringTemp(DrTempResult record){

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", DrTempResult.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(record, new CompactWriter(sw));
        return sw.toString();
    }

    //todo 导出线上民主推荐结果，按照各个岗位导出，模板中需要多预留一行
    public void exportOnlineResult(List<Integer> typeIds, Integer onlineId, HttpServletResponse response) throws IOException {

        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
        List<Integer> postIds = drOnlineResultService.getPostId(typeIds, onlineId);
        //Map<Integer ,List<DrOnlineCandidate>> candidateMap = drOnlineResultService.findCandidate(onlineId);
        Map<Integer, List<String>> candidateMap = drOnlineResultService.findCandidate(typeIds, onlineId);

        InputStream is = new FileInputStream(ResourceUtils
                .getFile("classpath:xlsx/dr/dr_online_template.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = null;

        if (postIds.size() == 0){

            row = sheet.getRow(1);
            cell = row.getCell(0);
            str = cell.getStringCellValue()
                    .replace("post", "无")
                    .replace("headcount", "0");
            cell.setCellValue(str);

            row = sheet.getRow(2);
            cell = row.getCell(0);
            str = cell.getStringCellValue()
                    .replace("pubcount", "0")
                    .replace("finishcount", "0");
            cell.setCellValue(str);

            String fileName = String.format("线上民主推荐（%s）", drOnline.getCode());
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return;
        }

        //sheet = wb.getSheetAt(0);

        //进行row扩充
        int startRow = 4;
        int rowInsert = iDrMapper.countResult(typeIds, null, onlineId, null, null) + (postIds.size() - 1) * 4;
        ExcelUtils.insertRow(wb, sheet, startRow, rowInsert - 1);
        String[] tableHead = {"序号", "推荐人选", "票数", "得票比率"};
        //获得单元格样式
        XSSFCellStyle cellStyle = null;
        XSSFRow _row = sheet.getRow(3);
        XSSFCellStyle cellStyle1 = _row.getCell(0).getCellStyle();

        int rowCount = 1;//记录行数
        for (Integer postId : postIds){
            //设置模板中每一个职务的第一行
            DrOnlinePostView postView = drOnlinePostService.getPost(postId);
            //List<DrOnlineCandidate> candidates = candidateMap.get(postId);
            List<String> candidates = candidateMap.get(postId);
            row = sheet.getRow(rowCount++);//1
            cell = row.getCell(0);
            if (rowCount <= 2) {
                cellStyle = cell.getCellStyle();
                str = cell.getStringCellValue()
                        .replace("post", postView.getName())
                        .replace("headcount", postView.getCompetitiveNum() + "");
            }else {
                //合并单元格
                CellRangeAddress cra = new CellRangeAddress(rowCount - 1,rowCount - 1,0,2);
                sheet.addMergedRegion(cra);
                for (int i = 3; i >= 0; i--){
                    cell = row.getCell(i);
                    cell.setCellStyle(cellStyle);
                }
                str = "推荐职务：" + postView.getName() + "（" + postView.getCompetitiveNum() + "名）";
            }
            cell.setCellValue(str);
            //设置每一个岗位的第二行
            if (candidates.size() == 0) {//参评人为空
                row = sheet.getRow(rowCount++);//2
                cell = row.getCell(0);
                str = cell.getStringCellValue()
                        .replace("pubcount", "0")
                        .replace("finishcount", "0");
                cell.setCellValue(str);

                //插入表头
                row = sheet.getRow(rowCount++);
                for(int i = 0; i < tableHead.length; i++){
                    cell = row.getCell(i);
                    cell.setCellStyle(cellStyle1);
                    cell.setCellValue(tableHead[i]);
                }
            }else {
                DrFinalResult _view = drOnlineResultService.findCount(onlineId, postId, null, typeIds);
                row = sheet.getRow(rowCount++);//2
                cell = row.getCell(0);
                if (rowCount == 3) {
                    str = cell.getStringCellValue()
                            /*.replace("pubcount", _view.getPubCounts() + "")
                            .replace("finishcount", _view.getFinishCounts() + "")*/;
                }else {
                    CellRangeAddress cra = new CellRangeAddress(rowCount - 1,rowCount - 1,0,3);
                    sheet.addMergedRegion(cra);
                    for (int i = 3; i >= 0; i--){
                        cell = row.getCell(i);
                        cell.setCellStyle(cellStyle);
                    }
                    //str = "共发出推荐票" +  _view.getPubCounts() + "张，参加推荐共" + _view.getFinishCounts() + "人";
                }
                cell.setCellValue(str);

                row = sheet.getRow(rowCount++);
                for(int i = 0; i < tableHead.length; i++){
                    cell = row.getCell(i);
                    cell.setCellStyle(cellStyle1);
                    cell.setCellValue(tableHead[i]);
                }

                //处理得票
                int i = 0;
                for (String record : candidates) {
                    DrFinalResult _result = drOnlineResultService.findCount(onlineId, postId, record, typeIds);
                    DecimalFormat df = new DecimalFormat("0.00");
                    /*Double options = Double.valueOf(_result.getOptions());
                    Double finishCounts = Double.valueOf(_result.getFinishCounts());
                    String rate = df.format(options/finishCounts*100) + "%";*/

                    row = sheet.getRow(rowCount++);
                    int column = 0;
                    // 序号
                    cell = row.getCell(column++);
                    cell.setCellValue(++i);

                    // 推荐人选
                    cell = row.getCell(column++);
                    cell.setCellValue(record);

                    // 票数
                    cell = row.getCell(column++);
                    //cell.setCellValue(_result.getOptions());

                    //得票比率
                    cell = row.getCell(column);
                    //cell.setCellValue(rate);
                }
            }
            rowCount++;
        }
        String fileName = String.format("线上民主推荐（%s）", drOnline.getCode());
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

}
