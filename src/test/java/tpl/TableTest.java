package tpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.DBOperator;
import service.SpringProps;

import java.util.List;

/**
 * Created by fafa on 2015/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class TableTest {

    @Autowired
    DBOperator dbOperator;
    @Autowired
    private SpringProps springProps;

    @Test
    public void execute() throws Exception {

        List<String> tableNameList = dbOperator.getTableNameList(springProps.schema);
        for (String s : tableNameList) {

            System.out.println(("truncate " + s + ";"));
        }
    }
}
