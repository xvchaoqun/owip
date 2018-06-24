package service.ext;

import bean.ColumnBean;
import org.junit.Test;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/6/22.
 */
public class SourceTest extends Source {

    @Test
    public void getTableColumns() throws Exception {

        initConn();

        List<ColumnBean> tableColumns = getTableColumns("ICDC_VIEW", "V_JZG_RS_JZG_JBXX_NEW");
        for (ColumnBean tableColumn : tableColumns) {
            System.out.println(tableColumn);
        }
    }

    @Test
    public void excute(){
        excute(null);
    }

    @Override
    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        System.out.println(JSONUtils.toString(map, false));
        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        //ExtJzg extJzg = gson.fromJson(JSONUtils.toString(map), ExtJzg.class);

    }

    public void excute(Integer syncId){

        long startTime=System.currentTimeMillis();
        excute("ICDC_VIEW", "V_JZG_RS_JZG_JBXX_NEW", "order by zgh", syncId);
        long endTime=System.currentTimeMillis();
    }
}
