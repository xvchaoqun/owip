package service.source;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.gson.Gson;
import domain.ExtBks;
import domain.ExtBksExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.ExtBksMapper;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Service
public class BnuBksImport extends Source {

    @Autowired
    public DruidDataSource bnuDS;

    public void excute(){

        System.out.println("更新本科生账号库");
        long startTime=System.currentTimeMillis();
        setConn(bnuDS);
        excute(schema, tableName);
        long endTime=System.currentTimeMillis();
        System.out.println("更新本科生账号库运行时间： " + (endTime - startTime) + "ms");
    }

    @Autowired
    public ExtBksMapper extBksMapper;
    public String schema = "licdc_zg";
    public String tableName = "v_bks_xjjbsjxx";

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        Gson gson = new Gson();
        ExtBks extBks = gson.fromJson(JSONUtils.toString(map), ExtBks.class);
        ExtBksExample example = new ExtBksExample();
        example.createCriteria().andXhEqualTo(rs.getString("xh"));
        List<ExtBks> extBkses = extBksMapper.selectByExample(example);
        if (extBkses.size() > 0) {
            extBks.setId(extBkses.get(0).getId());
            extBksMapper.updateByExample(extBks, example);
        } else {
            extBksMapper.insert(extBks);
        }
    }

}
