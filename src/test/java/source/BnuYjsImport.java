package source;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.gson.Gson;
import domain.ExtYjs;
import domain.ExtYjsExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.ExtYjsMapper;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BnuYjsImport extends Source {

    @Autowired
    public DruidDataSource bnuDS;
    @Test
    public void excute() throws Exception {

        long startTime=System.currentTimeMillis();
        setConn(bnuDS);
        excute(schema, tableName);
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }

    @Autowired
    public ExtYjsMapper extYjsMapper;
    public String schema = "licdc_zg";
    public String tableName = "v_yjs_yjsxjjbxx";

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        Gson gson = new Gson();
        ExtYjs extYjs = gson.fromJson(JSONUtils.toString(map), ExtYjs.class);
        ExtYjsExample example = new ExtYjsExample();
        example.createCriteria().andXhEqualTo(rs.getString("xh"));
        List<ExtYjs> extYjses = extYjsMapper.selectByExample(example);
        if (extYjses.size() > 0) {
            extYjs.setId(extYjses.get(0).getId());
            //extYjsMapper.updateByExample(extYjs, example);
        } else {
            extYjsMapper.insert(extYjs);
        }
    }

}
