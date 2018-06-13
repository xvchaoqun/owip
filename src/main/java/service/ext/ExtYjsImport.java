package service.ext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.ext.ExtYjs;
import domain.ext.ExtYjsExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.ext.ExtYjsMapper;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Service
public class ExtYjsImport extends Source {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public ExtYjsMapper extYjsMapper;
    public String schema = "licdc_zg";
    public String tableName = "v_yjs_yjsxjjbxx";

    public void byCode(String code) {

        logger.info("更新研究生账号基本信息:" + code);
        excute(schema, tableName, String.format("where xh='%s'", code));
    }

    public void excute(Integer syncId) {

        logger.info("更新研究生账号库基本信息");
        long startTime=System.currentTimeMillis();
        excute(schema, tableName, "order by xh", syncId);
        long endTime=System.currentTimeMillis();
        logger.info("更新研究生账号库基本信息程序运行时间： " + (endTime - startTime) + "ms");
    }

    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ExtYjs extYjs = gson.fromJson(JSONUtils.toString(map), ExtYjs.class);
        ExtYjsExample example = new ExtYjsExample();
        example.createCriteria().andXhEqualTo(rs.getString("xh"));
        List<ExtYjs> extYjses = extYjsMapper.selectByExample(example);
        if (extYjses.size() > 0) {
            extYjs.setId(extYjses.get(0).getId());
            extYjsMapper.updateByExample(extYjs, example);
        } else {
            extYjsMapper.insert(extYjs);
        }
    }

}
