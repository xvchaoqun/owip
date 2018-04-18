package servcie;

import domain.pmd.PmdBranch;
import domain.pmd.PmdBranchExample;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNorm;
import domain.pmd.PmdParty;
import domain.pmd.PmdPartyExample;
import org.apache.commons.lang.math.RandomUtils;
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
import service.pmd.PmdBranchService;
import service.pmd.PmdExportService;
import service.pmd.PmdMonthService;
import service.pmd.PmdPartyService;
import service.pmd.PmdPayService;
import service.pmd.PmdPayWszfService;
import sys.utils.DateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
    PmdPayWszfService pmdWszfService;
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
