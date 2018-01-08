package service.source;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sys.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BnuJzgSalaryImportTest {

    @Autowired
    public ExtJzgSalaryImport extJzgSalaryImport;
    @Test
    public void excute1() throws Exception {

        extJzgSalaryImport.excute("201712");
    }
    @Test
    public void excute() throws Exception {

        Date start = DateUtils.parseDate("201701", "yyyyMM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        for(int i=0; i<12; i++) {
            String date = DateUtils.formatDate(cal.getTime(), "yyyyMM");
            System.out.println("同步月份：" + date);
            extJzgSalaryImport.excute(date);
            cal.add(Calendar.MONTH, 1);
        }
    }
}
