package service.dr;

import bean.TempResult;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import domain.dr.DrOnline;
import domain.dr.DrOnlineCandidate;
import domain.dr.DrOnlinePostView;
import domain.dr.DrOnlineResultView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public TempResult getTempResult(String tempData){

        TempResult tempResult = null;

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", TempResult.class);

        if (StringUtils.isNotBlank(tempData)){
            tempResult = (TempResult) xStream.fromXML(tempData);
        }

        tempResult = (tempResult == null) ? new TempResult() : tempResult;

        return tempResult;
    }

    //todo 导出线上民主推荐结果，按照各个岗位导出，模板中需要多预留一行
    public void exportOnlineResult(Integer onlineId, Integer postId, HttpServletResponse response) throws IOException {

        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
        List<Integer> postIds = drOnlineResultService.getPostId(onlineId);
        Map<Integer ,List<DrOnlineCandidate>> candidateMap = drOnlineResultService.findCandidate(onlineId);

        InputStream is = new FileInputStream(ResourceUtils
                .getFile("classpath:xlsx/dr/dr_online_template.xlsx"));
        /*XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = null;*/


        if (postIds.size() == 0){
            XSSFWorkbook wb1 = new XSSFWorkbook(is);
            XSSFSheet sheet1 = wb1.getSheetAt(0);
            XSSFRow row1 = sheet1.getRow(0);
            XSSFCell cell1 = row1.getCell(0);
            String str1 = null;

            row1 = sheet1.getRow(1);
            cell1 = row1.getCell(0);
            str1 = cell1.getStringCellValue()
                    .replace("post", "无")
                    .replace("headcount", "0");
            cell1.setCellValue(str1);

            row1 = sheet1.getRow(2);
            cell1 = row1.getCell(0);
            str1 = cell1.getStringCellValue()
                    .replace("pubcount", "0")
                    .replace("finishcount", "0");
            cell1.setCellValue(str1);

            String fileName = String.format("线上民主推荐（%s）", drOnline.getCode());
            ExportHelper.output(wb1, fileName + ".xlsx", response);
            return;
        }
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row = sheet.getRow(0);
            XSSFCell cell = row.getCell(0);
            String str = null;

            sheet = wb.getSheetAt(0);
            DrOnlinePostView postView = drOnlinePostService.getPost(postId);
            List<DrOnlineCandidate> candidates = candidateMap.get(postId);

            row = sheet.getRow(1);
            cell = row.getCell(0);
            str = cell.getStringCellValue()
                    .replace("post", postView.getName())
                    .replace("headcount", postView.getCompetitiveNum() + "");
            cell.setCellValue(str);

            if (candidateMap.size() == 0){
                row = sheet.getRow(2);
                cell = row.getCell(0);
                str = cell.getStringCellValue()
                        .replace("pubcount", "0")
                        .replace("finishcount", "0");
                cell.setCellValue(str);
            }else {
                DrOnlineResultView _view = drOnlineResultService.findCount(onlineId, postView.getId(), null);
                row = sheet.getRow(2);
                cell = row.getCell(0);
                str = cell.getStringCellValue()
                        .replace("pubcount", _view.getPubCounts()+"")
                        .replace("finishcount", _view.getFinishCounts()+"");
                cell.setCellValue(str);

                int startRow = 4;
                int rowCount =candidates.size();
                ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
                int i = 0;

                if (candidates.size() != 0) {
                    for (DrOnlineCandidate record : candidates) {
                        DrOnlineResultView _result = drOnlineResultService.findCount(onlineId, postView.getId(), record.getId());
                        DecimalFormat df = new DecimalFormat("0.00");
                        Double optionSum = Double.valueOf(_result.getOptionSum());
                        Double finishCounts = Double.valueOf(_result.getFinishCounts());
                        String rate = df.format(optionSum/finishCounts*100) + "%";

                        row = sheet.getRow(startRow++);
                        int column = 0;
                        // 序号
                        cell = row.getCell(column++);
                        cell.setCellValue(++i);

                        // 推荐人选
                        cell = row.getCell(column++);
                        cell.setCellValue(record.getUser().getRealname());

                        // 票数
                        cell = row.getCell(column++);
                        cell.setCellValue(_result.getOptionSum());

                        //得票比率
                        cell = row.getCell(column++);
                        cell.setCellValue(rate);
                    }
                }
            }
            String fileName = String.format("线上民主推荐（%s）", drOnline.getCode());
            ExportHelper.output(wb, fileName + ".xlsx", response);

    }

}
