package view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tpl.ColumnBean;
import tpl.DBParser;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by fafa on 2015/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ViewTest{

    @Autowired
    DataSource dataSource;

    @Test
    public void get() throws Exception {

        DBParser dbParser = new DBParser(dataSource);

        Map<String, ColumnBean> columnBeanMap = dbParser.getTableColumnsMap("ow_student", "owip");
        for (Map.Entry<String, ColumnBean> entry : columnBeanMap.entrySet()) {

            System.out.println("s." + entry.getKey() + ",");
        }
    }
}
