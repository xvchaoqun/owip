package tpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fafa on 2015/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class TableTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void execute() throws Exception {

        DBParser dbParser = new DBParser(dataSource);

        List<String> tableNameList = dbParser.getTableNameList("owip");
        for (String s : tableNameList) {

            System.out.println(("truncate " + s + ";"));
        }
    }
}
