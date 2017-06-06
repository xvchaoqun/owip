package servcie;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.analysis.StatCadreService;
import service.member.MemberStayExportService;
import service.party.PartyExportService;
import sys.constants.SystemConstants;
import sys.utils.ExportHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fafa on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ExportServiceTest {

    @Autowired
    StatCadreService cadreStatService;

    @Autowired
    MemberStayExportService memberStayExportService;

    @Autowired
    PartyExportService partyExportService;

    @Test
    public void stat() throws IOException {

        XSSFWorkbook wb = cadreStatService.toXlsx();
        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test2.xlsx"));

        wb.write(output);
        output.close();
    }
    @Test
    public void partyExportService() throws IOException {

        XSSFWorkbook wb = partyExportService.toXlsx();
        ExportHelper.save(wb, "D:/tmp/test23.xlsx");
    }
    @Test
    public void memberStayExportService() throws IOException {

        SXSSFWorkbook wb = memberStayExportService.toXlsx(SystemConstants.MEMBER_STAY_TYPE_ABROAD);
        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test222.xlsx"));

        wb.write(output);
        output.close();

        wb = memberStayExportService.toXlsx(SystemConstants.MEMBER_STAY_TYPE_INTERNAL);
        output = new FileOutputStream(new File("D:/tmp/test222.xlsx"));

        wb.write(output);
        output.close();
    }

}
