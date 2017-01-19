package servcie;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.analysis.StatCadreService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fafa on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CadreStatExportServiceTest {

    @Autowired
    StatCadreService cadreStatService;

    @Test
    public void stat() throws IOException {

        XSSFWorkbook wb = cadreStatService.toXlsx();
        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test2.xlsx"));

        wb.write(output);
        output.close();
    }

}
