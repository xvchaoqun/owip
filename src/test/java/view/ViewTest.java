package view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import bean.ColumnBean;
import service.DBOperator;
import service.SpringProps;

import java.util.Map;

/**
 * Created by fafa on 2015/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ViewTest{

    @Autowired
    DBOperator dbOperator;
    @Autowired
    private SpringProps springProps;

    @Test
    public void get() throws Exception {

        Map<String, ColumnBean> columnBeanMap = dbOperator.getTableColumnsMap("ow_student", springProps.schema);
        for (Map.Entry<String, ColumnBean> entry : columnBeanMap.entrySet()) {

            System.out.println("s." + entry.getKey() + ",");
        }
    }
}
