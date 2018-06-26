package service.ext;

import bean.SchoolUnit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/6/22.
 */
@Service
public class ExtUnitService extends Source {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Map<String, SchoolUnit> getSchoolUnitMap(){

        Map<String, SchoolUnit> unitMap = new LinkedHashMap<>();

        initConn();
        Statement stat = null;
        ResultSet rs = null;

        String sql = "select * from ZZB_DATA.UNIT";
        logger.info(sql);
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs != null && rs.next()) {
                SchoolUnit record = new SchoolUnit();
                record.setCode(StringUtils.trim(rs.getString("UNIT_ID")));
                record.setName(rs.getString("UNIT_NAME"));
                record.setTop(rs.getString("LSDWH"));
                unitMap.put(record.getCode(), record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stat.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return unitMap;
    }

    @Override
    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {}
}