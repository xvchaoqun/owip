package servcie;

import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadreView;
import domain.unit.Unit;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.cadre.CadreViewMapper;
import persistence.cadre.common.ICadreMapper;
import persistence.dispatch.common.IDispatchMapper;
import service.cadre.CadreService;
import service.unit.UnitService;
import sys.constants.CadreConstants;
import sys.constants.SystemConstants;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/3/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ExcelTest {

    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreViewMapper cadreViewMapper;
    @Autowired
    private ICadreMapper iCadreMapper;
    @Autowired
    private IDispatchMapper iDispatchMapper;
    @Autowired
    private UnitService unitService;

    XSSFComment cellComment3;
    XSSFComment cellComment5;

    @Test
    public void cadres() throws IOException {

        /*XSSFDrawing p = sheet.createDrawingPatriarch();
        // 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
        XSSFComment comment3 = p.createCellComment(new XSSFClientAnchor(0, 0, 0,
                0, 1, 2, 5, 5));
        // 输入批注信息
        comment3.setString(cellComment3.getString());
        // 添加作者,选中B5单元格,看状态栏
        //comment.setAuthor("toad");
        XSSFComment comment5 = p.createCellComment(new XSSFClientAnchor(0, 0, 0,
                0, 3, 3, 5, 5));
        // 输入批注信息
        comment5.setString(cellComment5.getString());*/

        Map<Integer, CadreView> cadreMap = cadreService.findAll();
        Map<Integer, Unit> unitMap = unitService.findAll();
        int leaveCount = 0;
        for (Unit unit : unitMap.values()) {
            //if(unit.getId().intValue()!=106) continue;
            //if(unit.getId().intValue()!=177) continue;
            //if (unit.getId().intValue() != 170) continue;
            //if (unit.getId().intValue() != 180) continue;
            // 外国语文学学院
            //if (unit.getId().intValue() != 158) continue;

            InputStream is = new FileInputStream("D:/tmp/家庭成员调查表.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);

            XSSFRow row5 = sheet.getRow(5);
            cellComment3 = row5.getCell(3).getCellComment();
            cellComment5 = row5.getCell(5).getCellComment();

            // 2013年至今*****主要领导家庭成员一览表
            XSSFRow row1 = sheet.getRow(0);
            row1.getCell(0).setCellValue(String.format("2013年至今%s主要领导家庭成员一览表", unit.getName()));

            XSSFRow row2 = sheet.getRow(1);
            row2.getCell(0).setCellValue(String.format("填表日期：%s", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA)));

           /* // 创建绘图对象
            XSSFDrawing p = sheet.createDrawingPatriarch();
            // 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
            XSSFComment comment = p.createCellComment(new XSSFClientAnchor(0, 0, 0,
                    0, 3, 3, 5, 6));
            // 输入批注信息
            comment.setString(new XSSFRichTextString("这是批注内容!"));
            // 添加作者,选中B5单元格,看状态栏
            comment.setAuthor("toad");
            // 将批注添加到单元格对象中
            row2.getCell(0).setCellComment(comment);*/

            CadreViewExample example = new CadreViewExample();
            example.createCriteria().andUnitIdEqualTo(unit.getId()).andStatusEqualTo(CadreConstants.CADRE_STATUS_CJ);
            example.setOrderByClause("sort_order parseResumeRow");
            List<CadreView> cadres = cadreViewMapper.selectByExample(example);

            int startRowNum = 2;
            if (cadres.size() > 0)
                startRowNum = renderNowCadres(sheet, cadres, unit.getName());

            List<DispatchCadreView> leaveCadres = iDispatchMapper.leaveDispatchCadres(unit.getId());
            List<DispatchCadreView> filterLeaveCadres = new ArrayList<>();
            for (DispatchCadreView leaveCadre : leaveCadres) {
                CadreView cadre = cadreMap.get(leaveCadre.getCadreId());
                if(cadre.getStatus()!= CadreConstants.CADRE_STATUS_CJ){
                    filterLeaveCadres.add(leaveCadre);
                }
            }
            leaveCount += filterLeaveCadres.size();
            if (filterLeaveCadres.size() > 0)
                startRowNum = renderLeaveCadres(startRowNum, cadres.size(), sheet, filterLeaveCadres);
            else
                startRowNum += 2;

            if (cadres.size() == 0 && filterLeaveCadres.size() == 0) {
                System.out.println("++++++++++++" + unit.getName());
                continue;
            }


            if(cadres.size() + filterLeaveCadres.size()>1)
            for (int i = 0; i < cadres.size() + filterLeaveCadres.size(); i++) {

                if (i == 1) continue;

                XSSFDrawing p = sheet.createDrawingPatriarch();
                // 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
                XSSFComment comment3 = p.createCellComment(new XSSFClientAnchor(0, 0, 0,
                        0, 1, 2, 5, 5));
                // 输入批注信息
                comment3.setString(cellComment3.getString());
                // 添加作者,选中B5单元格,看状态栏
                //comment.setAuthor("toad");
                XSSFComment comment5 = p.createCellComment(new XSSFClientAnchor(0, 0, 0,
                        0, 3, 3, 5, 5));
                // 输入批注信息
                comment5.setString(cellComment5.getString());

                XSSFRow _row5 = sheet.getRow(5 + i * 10);
                if(i==1){
                    _row5.getCell(3).removeCellComment();
                    _row5.getCell(5).removeCellComment();
                }
                if(_row5.getCell(3).getCellComment()==null)
                    _row5.getCell(3).setCellComment(comment3);
                if(_row5.getCell(5).getCellComment()==null)
                    _row5.getCell(5).setCellComment(comment5);
            }

            startRowNum = (startRowNum == 2) ? (startRowNum + 12) : startRowNum;

            XSSFRow row = sheet.createRow(startRowNum);
            //row.setHeightInPoints(heightInPoints);
            row.setHeight((short) 528);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("备注:此表含在2013年至2017年间担任过学院或部门副处级以上职务的所有人员");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(startRowNum, 0, startRowNum, 6));


            FileOutputStream output = new FileOutputStream(new File("D:/tmp/" +(unit.getStatus()==SystemConstants.UNIT_STATUS_HISTORY?"历史单位_":"") + unit.getName().replace("/", "、") + ".xlsx"));  //读取的文件路径
            wb.write(output);
            output.close();
        }

        System.out.println("leaveCount="+leaveCount);
    }

    public void renderNowCadreInfo(XSSFSheet sheet, CadreView cadre, int rowNum) {

        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cel = row.getCell(2);
        cel.setCellValue(cadre.getRealname());
        cel = row.getCell(4);
        cel.setCellValue(cadre.getTitle());
        cel = row.getCell(6);
        cel.setCellValue(DateUtils.formatDate(cadre.getNpWorkTime(), DateUtils.YYYY_MM_DD));

        row = sheet.getRow(rowNum + 1);
        cel = row.getCell(2);
        cel.setCellValue(DateUtils.formatDate(cadre.getBirth(), DateUtils.YYYY_MM_DD));
        cel = row.getCell(4);
        cel.setCellValue(cadre.getIdcard());
        cel = row.getCell(6);
        cel.setCellValue("是");

    }

    public int renderNowCadres(XSSFSheet sheet, List<CadreView> cadres, String unitName) throws IOException {


        {
            CadreView cadre = cadres.get(0);
            renderNowCadreInfo(sheet, cadre, 2);
        }

        int size = cadres.size();
        int i = 0;
        for (; i < size - 1; i++) {

            ExcelUtils.copyRows(3, 12, 2 + 10 * (i+1), sheet);

            XSSFRow indexRow = sheet.getRow(3 + 10 * (i + 1));
            indexRow.getCell(0).setCellValue(i + 2);

            CadreView cadre = cadres.get(i + 1);
            renderNowCadreInfo(sheet, cadre, 2 + 10 * (i+1));

            String[] datas = {"夫妻", "父子", "父女", "母子", "母女", "兄弟", "兄妹", "姐弟", "姐妹"};
            //中共党员,共青团员,民革会员,民盟盟员,民建会员,民进会员,农工党党员,致公党党员,九三学社社员,台盟盟员,群众
            String[] data2 = {"中共党员", "共青团员", "民革会员", "民盟盟员", "民建会员", "民进会员",
                    "农工党党员", "致公党党员", "九三学社社员", "台盟盟员", "群众"};
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
            {
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                        .createExplicitListConstraint(datas);
                CellRangeAddressList addressList = new CellRangeAddressList(6 + 10 * (i + 1), 11 + 10 * (i + 1), 2, 2);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(
                        dvConstraint, addressList);
                sheet.addValidationData(validation);
            }

            {
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                        .createExplicitListConstraint(data2);
                CellRangeAddressList addressList = new CellRangeAddressList(6 + 10 * (i + 1), 11 + 10 * (i + 1), 6, 6);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(
                        dvConstraint, addressList);
                sheet.addValidationData(validation);
            }

            /*填写要求：
            1. 填写全称，不可简写。
            2. 在校学生要具体到所在班级。
            3. 如果已退休，填写原工作单位，并在后面添加“（已退休）”。
            4. 如果已去世，填写原工作单位，并在后面添加“（已去世）”。*/

        }

        return 12 + 10 * i;
        /*int rowNum = 12 + 10 *i + 2;
        XSSFRow row = sheet.createRow(rowNum);
        //row.setHeightInPoints(heightInPoints);
        row.setHeight((short)528);
        XSSFCell cell = row.createCell(0);

        cell.setCellValue("备注:此表含在2013年至2017年间担任过学院或部门副处级以上职务的所有人员");
        sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 6));*/

    }


    public void renderLeaveCadreInfo(XSSFSheet sheet, DispatchCadreView dc, int rowNum) {

        CadreView cadre = iCadreMapper.getCadre(dc.getCadreId());
        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cel = row.getCell(2);
        cel.setCellValue(cadre.getRealname());
        cel = row.getCell(3);
        cel.setCellValue("原职务");
        cel = row.getCell(4);
        cel.setCellValue(dc.getPost());
        cel = row.getCell(5);
        cel.setCellValue("离任时间");
        cel = row.getCell(6);
        Dispatch dispatch = dc.getDispatch();
        if (dispatch != null)
            cel.setCellValue(DateUtils.formatDate(dispatch.getWorkTime(), DateUtils.YYYY_MM_DD));

        row = sheet.getRow(rowNum + 1);
        cel = row.getCell(2);
        cel.setCellValue(DateUtils.formatDate(cadre.getBirth(), DateUtils.YYYY_MM_DD));
        cel = row.getCell(4);
        cel.setCellValue(cadre.getIdcard());
        cel = row.getCell(6);
        cel.setCellValue("否");
    }

    public int renderLeaveCadres(int startRowNum, int indexNum, XSSFSheet sheet, List<DispatchCadreView> dispatchCadreViews) throws IOException {

        boolean noNowCadres = (startRowNum == 2);

        if (noNowCadres) { // 没有现任干部的情况
            DispatchCadreView dc = dispatchCadreViews.get(0);
            renderLeaveCadreInfo(sheet, dc, 2);
            indexNum++;
            startRowNum = 12;
        }

        int size = dispatchCadreViews.size();
        int i = 0;
        for (; i < size - (noNowCadres ? 1 : 0); i++) {

            ExcelUtils.copyRows(3, 12, startRowNum + 10 * i, sheet);

            XSSFRow indexRow = sheet.getRow(startRowNum + 1 + 10 * i);
            indexRow.getCell(0).setCellValue(++indexNum);

            DispatchCadreView dc = dispatchCadreViews.get(i + (noNowCadres ? 1 : 0));

            renderLeaveCadreInfo(sheet, dc, startRowNum + 10 * i);

            String[] datas = {"夫妻", "父子", "父女", "母子", "母女", "兄弟", "兄妹", "姐弟", "姐妹"};
            //中共党员,共青团员,民革会员,民盟盟员,民建会员,民进会员,农工党党员,致公党党员,九三学社社员,台盟盟员,群众
            String[] data2 = {"中共党员", "共青团员", "民革会员", "民盟盟员", "民建会员", "民进会员",
                    "农工党党员", "致公党党员", "九三学社社员", "台盟盟员", "群众"};
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
            {
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                        .createExplicitListConstraint(datas);
                CellRangeAddressList addressList = new CellRangeAddressList(startRowNum + 4 + 10 * i, startRowNum + 9 + 10 * i, 2, 2);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(
                        dvConstraint, addressList);
                sheet.addValidationData(validation);
            }

            {
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                        .createExplicitListConstraint(data2);
                CellRangeAddressList addressList = new CellRangeAddressList(startRowNum + 4 + 10 * i, startRowNum + 9 + 10 * i, 6, 6);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(
                        dvConstraint, addressList);
                sheet.addValidationData(validation);
            }

            /*填写要求：
            1. 填写全称，不可简写。
            2. 在校学生要具体到所在班级。
            3. 如果已退休，填写原工作单位，并在后面添加“（已退休）”。
            4. 如果已去世，填写原工作单位，并在后面添加“（已去世）”。*/

        }

        return startRowNum + 10 * i + 2;
    }
}
