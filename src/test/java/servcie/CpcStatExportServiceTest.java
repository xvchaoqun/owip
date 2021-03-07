package servcie;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.unit.UnitPostAllocationService;
import sys.constants.CadreConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fafa on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CpcStatExportServiceTest {

    @Autowired
    UnitPostAllocationService unitPostAllocationService;

    @Test
    public void stat() throws IOException {

        XSSFWorkbook wb = unitPostAllocationService.cpcInfo_Xlsx(CadreConstants.CADRE_CATEGORY_CJ);
        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test333.xlsx"));

        wb.write(output);
        output.close();
    }

}
