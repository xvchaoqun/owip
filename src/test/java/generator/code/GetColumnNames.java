package generator.code;

import bean.ColumnBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.DBOperator;

import java.util.Map;

/**
 * Created by fafa on 2016/12/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class GetColumnNames {

    @Autowired
    DBOperator dbOperator;

    @Test
    public void get() throws Exception {

        Map<String, ColumnBean> tableColumnsMap = dbOperator.getTableColumnsMap("ow_member", "owip20160919");

        String tmp = "";
        for (ColumnBean columnBean : tableColumnsMap.values()) {

            tmp += ",t1." +columnBean.getName() + "=t2."+columnBean.getName();
        }
        System.out.println(tmp);
    }

    @Test
    public void getColumnList() throws Exception {

        Map<String, ColumnBean> tableColumnsMap = dbOperator.getTableColumnsMap("train_course", "owip");

        String tmp = "";
        for (ColumnBean columnBean : tableColumnsMap.values()) {

            tmp += "," +columnBean.getName();
        }
        System.out.println(tmp);
    }
}
