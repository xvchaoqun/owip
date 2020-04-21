import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;
import persistence.dr.common.IDrMapper;
import service.cadre.CadreEduService;
import service.pmd.PmdOrderLogService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class DpTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String path = "D:/pmddoc";
    public static final String openFileStyle = "r";
    @Autowired(required = false)
    private PmdOrderLogService pmdOrderLogService;
    @Autowired
    private IDrMapper iDrMapper;

    @Autowired
    private CadreEduService cadreEduService;
    /*
        批量计算文件夹中.txt中的党费收缴情况
        遇到相同订单号的，先删除，后添加
     */
    @Test
    public void excute() {
        File file = new File(path);
        int count = 0;
        count = checkFile(file, count);
        logger.info("总计：" + count + "条记录");
    }

    public int checkFile(File file, Integer count){

        if (file.isFile()){
            try {
                String name = file.getName();
                count += pmdOrderLogService.loadFile1(file.getAbsolutePath(), openFileStyle, name);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return count;
        }

        if (file.isDirectory()){
            for (File _file : file.listFiles()){
                count = checkFile(_file, count);
            }
        }

        return count;
    }

    //根据code更新领导干部的学习经历中，最高学历最高学位（可以去掉code,直接全部更新）
    @Test
    public void updateEdu(){
        String code = 99038 + "";
        cadreEduService.updateHighEdu(code);
    }

    @Test
    public void result(){
        /*List<Integer> list = new ArrayList<>();
        list.add(650);

        List<DrFinalResult> drFinalResults = iDrMapper.selectInspectorFilter(list, 1);
        for (DrFinalResult drFinalResult : drFinalResults) {
            System.out.println(drFinalResult);
        }*/
       /* List<DrFinalResult> drFinalResults1 = iDrMapper.resultOne(5);
        for (DrFinalResult drFinalResult : drFinalResults1) {
            System.out.println(drFinalResult);
        }*/
       int a = iDrMapper.countResult(null, 5);
       System.out.println(a);
    }

    @Test
    public void exportXssf() throws IOException {


        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/dr/dr_online_template1.xlsx"));

        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(1);
        CellRangeAddress cra = new CellRangeAddress(1,1,0,2);
        sheet.addMergedRegion(cra);

        //.setCellValue("11111");
        String str = null;
        str = cell.getStringCellValue();

        System.out.println(cell.getAddress());
        System.out.println(row.getFirstCellNum() + "-" + row.getLastCellNum());
        System.out.println(str);

    }
}
