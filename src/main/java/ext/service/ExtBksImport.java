package ext.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ext.domain.ExtBks;
import ext.domain.ExtBksExample;
import ext.persistence.ExtBksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ExtBksImport extends Source {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ExtBksMapper extBksMapper;
    public String schema = "ICDC_VIEW";
    public String tableName = "v_bks_xjjbsjxx";

    public void byCode(String code) {

        logger.info("更新本科生账号库基本信息:" + code);
        excute(schema, tableName, String.format("where xh='%s'", code));
    }

    public void excute(Integer syncId){

        logger.info("开始更新本科生账号库基本信息");
        long startTime=System.currentTimeMillis();
        excute(schema, tableName, "order by xh", syncId);
        long endTime=System.currentTimeMillis();
        logger.info("本科生账号库更新完成，更新本科生账号库基本信息运行时间： " + (endTime - startTime) + "ms");
    }

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ExtBks extBks = gson.fromJson(JSONUtils.toString(map), ExtBks.class);
        ExtBksExample example = new ExtBksExample();
        example.createCriteria().andXhEqualTo(rs.getString("xh"));
        List<ExtBks> extBkses = extBksMapper.selectByExample(example);
        if (extBkses.size() > 0) {
            extBksMapper.updateByExample(extBks, example);
        } else {
            extBksMapper.insert(extBks);
        }
    }

}
