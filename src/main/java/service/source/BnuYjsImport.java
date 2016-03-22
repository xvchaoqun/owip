package service.source;

import com.google.gson.Gson;
import domain.ExtYjs;
import domain.ExtYjsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.ExtYjsMapper;
import sys.utils.JSONUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Service
public class BnuYjsImport extends Source {

    @Autowired
    public ExtYjsMapper extYjsMapper;
    public String schema = "licdc_zg";
    public String tableName = "v_yjs_yjsxjjbxx";

    public void excute() {

        System.out.println("更新研究生账号库");
        long startTime=System.currentTimeMillis();
        excute(schema, tableName);
        long endTime=System.currentTimeMillis();
        System.out.println("更新研究生账号库程序运行时间： "+(endTime-startTime)+"ms");
    }

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
