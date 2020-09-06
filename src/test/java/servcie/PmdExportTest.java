package servcie;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.CommonMapper;
import persistence.pmd.PmdBranchMapper;
import persistence.pmd.PmdMemberMapper;
import persistence.pmd.PmdNormMapper;
import persistence.pmd.PmdPartyMapper;
import persistence.pmd.common.IPmdMapper;
import service.pmd.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lm on 2018/1/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PmdExportTest {

    @Autowired
    PmdMonthService pmdMonthService;
    @Autowired
    PmdBranchService pmdBranchService;
    @Autowired
    PmdBranchMapper pmdBranchMapper;
    @Autowired
    PmdPartyService pmdPartyService;
    @Autowired
    PmdExportService pmdExportService;
    @Autowired
    PmdPartyMapper pmdPartyMapper;
    @Autowired
    PmdPayService pmdPayService;
    @Autowired
    CommonMapper commonMapper;
    @Autowired
    PmdMemberMapper pmdMemberMapper;
    @Autowired
    PmdNormMapper pmdNormMapper;
    @Autowired
    IPmdMapper iPmdMapper;

    // 设定额度
    @Test
    public void step0() throws IOException {

        XSSFWorkbook wb = pmdExportService.reportParty(1);

        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test222.xlsx"));
        wb.write(output);
        output.close();
    }
}
