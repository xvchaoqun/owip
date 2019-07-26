package ext.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ext.domain.ExtRetireSalary;
import ext.domain.ExtRetireSalaryExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ext.persistence.ExtRetireSalaryMapper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExtRetireSalaryImport extends Source {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public ExtRetireSalaryMapper extRetireSalaryMapper;
    public String schema = "ICDC_VIEW";
    public String tableName = "v_cjc_ltxf";

    public void byCode(String code) {

        String rq = DateUtils.formatDate(new Date(), "yyyyMM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        String lastRq = DateUtils.formatDate(cal.getTime(), "yyyyMM");

        logger.info("同步最新两个月的离退休人员党费计算基数信息:" + code);
        excute(schema, tableName, String.format("where zgh='%s' and (rq='%s' or rq='%s')", code, lastRq, rq));
    }

    // 同步某个月的工资
    public int excute(String rq){
        System.out.println(String.format("同步%s月离退休信息", rq));
        long startTime=System.currentTimeMillis();
        int ret = excute(schema, tableName, String.format("where rq='%s'", rq), null);
        long endTime=System.currentTimeMillis();
        System.out.println(String.format("同步%s月离退休信息运行时间： " + (endTime - startTime) + "ms", rq));
        return ret;
    }

    public int excute(Integer syncId){
        String rq = DateUtils.formatDate(new Date(), "yyyyMM");
        logger.info("同步{}月离退休信息", rq);
        long startTime=System.currentTimeMillis();
        int ret = excute(schema, tableName, String.format("where rq='%s'", rq), syncId);
        long endTime=System.currentTimeMillis();
        logger.info("同步{}月离退休信息运行时间： " + (endTime - startTime) + "ms", rq);
        return ret;
    }

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ExtRetireSalary record = gson.fromJson(JSONUtils.toString(map), ExtRetireSalary.class);
        ExtRetireSalaryExample example = new ExtRetireSalaryExample();
        example.createCriteria().andZghEqualTo(rs.getString("zgh")).andRqEqualTo(rs.getString("rq"));

        record.setBase(rs.getBigDecimal("yljffje"));

        BigDecimal base = record.getBase();
        if(base==null || base.compareTo(BigDecimal.valueOf(0)) <= 0) return;

        List<ExtRetireSalary> records = extRetireSalaryMapper.selectByExample(example);
        if (records.size() > 0) {
            record.setId(records.get(0).getId());
            extRetireSalaryMapper.updateByExample(record, example);
        } else {
            extRetireSalaryMapper.insert(record);
        }
    }

}
