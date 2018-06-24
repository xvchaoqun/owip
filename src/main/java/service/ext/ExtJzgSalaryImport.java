package service.ext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.ext.ExtJzgSalary;
import domain.ext.ExtJzgSalaryExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.ext.ExtJzgSalaryMapper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExtJzgSalaryImport extends Source {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public ExtJzgSalaryMapper extJzgSalaryMapper;
    public String schema = "licdc_zg";
    public String tableName = "v_zzry_gz_dydf";

    public int excute(Integer syncId){

        String rq = DateUtils.formatDate(new Date(), "yyyyMM");
        logger.info("同步({})在职工资信息", rq);
        long startTime=System.currentTimeMillis();
        int ret = excute(schema, tableName, String.format("where rq='%s'", rq), syncId);
        long endTime=System.currentTimeMillis();
        logger.info("同步({})在职工资信息运行时间： " + (endTime - startTime) + "ms", rq);
        return ret;
    }

    public int excute(String rq){

        logger.info("同步({})在职工资信息", rq);
        long startTime=System.currentTimeMillis();
        int ret = excute(schema, tableName, String.format("where rq='%s'", rq));
        long endTime=System.currentTimeMillis();
        logger.info("同步({})在职工资信息运行时间： " + (endTime - startTime) + "ms", rq);
        return ret;
    }

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ExtJzgSalary record = gson.fromJson(JSONUtils.toString(map), ExtJzgSalary.class);
        ExtJzgSalaryExample example = new ExtJzgSalaryExample();
        example.createCriteria().andZghEqualTo(rs.getString("zgh")).andRqEqualTo(rs.getString("rq"));
        List<ExtJzgSalary> records = extJzgSalaryMapper.selectByExample(example);
        if (records.size() > 0) {
            extJzgSalaryMapper.updateByExample(record, example);
        } else {
            extJzgSalaryMapper.insert(record);
        }
    }

}
