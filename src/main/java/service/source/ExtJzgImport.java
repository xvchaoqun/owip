package service.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.ext.ExtJzg;
import domain.ext.ExtJzgExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.ext.ExtJzgMapper;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Service
public class ExtJzgImport extends Source {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public ExtJzgMapper extJzgMapper;
    public String schema = "licdc_zg";
    public String tableName = "v_jzg_rs_jzg_jbxx";

    public void excute(String code) {

        logger.info("更新教职工账号库基本信息:" + code);
        excute(schema, tableName, String.format("where zgh='%s'", code));
    }

    public void excute(){
        logger.info("更新教职工账号库基本信息");
        long startTime=System.currentTimeMillis();
        excute(schema, tableName);
        long endTime=System.currentTimeMillis();
        logger.info("更新教职工账号库基本信息程序运行时间： " + (endTime - startTime) + "ms");
    }

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ExtJzg extJzg = gson.fromJson(JSONUtils.toString(map), ExtJzg.class);
        ExtJzgExample example = new ExtJzgExample();
        example.createCriteria().andZghEqualTo(rs.getString("zgh"));
        List<ExtJzg> extJzges = extJzgMapper.selectByExample(example);
        if (extJzges.size() > 0) {
            extJzg.setId(extJzges.get(0).getId());
            extJzgMapper.updateByExample(extJzg, example);
        } else {
           extJzgMapper.insert(extJzg);
           //logger.info("插入=========" + rs.getString("zgh"));
        }
    }

}
