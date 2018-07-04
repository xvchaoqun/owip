package service.ext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.ext.ExtAbroad;
import domain.ext.ExtAbroadExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.ext.ExtAbroadMapper;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ExtAbroadImport extends Source {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public ExtAbroadMapper extAbroadMapper;
    public String schema = "ICDC_VIEW";
    public String tableName = "v_rs_jzg_cgjxx";

    public void excute(Integer syncId){
        logger.info("更新教职工党员出国信息库");
        long startTime=System.currentTimeMillis();
        excute(schema, tableName, syncId);
        long endTime=System.currentTimeMillis();
        logger.info("更新教职工党员出国信息库程序运行时间： " + (endTime - startTime) + "ms");
    }

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ExtAbroad record = gson.fromJson(JSONUtils.toString(map), ExtAbroad.class);
        ExtAbroadExample example = new ExtAbroadExample();
        example.createCriteria().andLshEqualTo(rs.getString("lsh"));
        List<ExtAbroad> records = extAbroadMapper.selectByExample(example);
        if (records.size() > 0) {
            record.setId(records.get(0).getId());
            extAbroadMapper.updateByExample(record, example);
        } else {
            extAbroadMapper.insert(record);
        }
    }

}
