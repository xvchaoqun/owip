package source;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.gson.Gson;
import domain.ExtJzg;
import domain.ExtJzgExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.ExtJzgMapper;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BnuJzgImport extends Source {

    @Autowired
    public DruidDataSource bnuDS;
    @Test
    public void excute(){
        long startTime=System.currentTimeMillis();
        setConn(bnuDS);
        excute(schema, tableName);
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }

    @Autowired
    public ExtJzgMapper extJzgMapper;
    public String schema = "licdc_zg";
    public String tableName = "v_jzg_rs_jzg_jbxx";

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        Gson gson = new Gson();
        ExtJzg extJzg = gson.fromJson(JSONUtils.toString(map), ExtJzg.class);
        ExtJzgExample example = new ExtJzgExample();
        example.createCriteria().andZghEqualTo(rs.getString("zgh"));
        List<ExtJzg> extJzges = extJzgMapper.selectByExample(example);
        if (extJzges.size() > 0) {
            extJzg.setId(extJzges.get(0).getId());
            extJzgMapper.updateByExample(extJzg, example);
        } else {
           extJzgMapper.insert(extJzg);
           //System.out.println("插入=========" + rs.getString("zgh"));
        }
    }

}
