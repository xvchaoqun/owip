package ext.service;

import bean.SchoolUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/6/22.
 */
@Service
public class ExtUnitService extends Source {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Cacheable(value = "schoolUnits")
    public Map<String, SchoolUnit> getSchoolUnitMap(){

        Map<String, SchoolUnit> unitMap = new LinkedHashMap<>();

        /*Connection conn = getConn();
        Statement stat = null;
        ResultSet rs = null;

        String sql = "select * from HIT_DMB.XJDM_ZZJG";
        logger.info(sql);
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs != null && rs.next()) {
                SchoolUnit record = new SchoolUnit();
                record.setCode(StringUtils.trim(rs.getString("DM")));
                record.setName(rs.getString("MC"));
                record.setType(rs.getString("XSCJ"));
                record.setTop(rs.getString("JGID"));
                record.setRemark(rs.getString("LSJGID"));
                unitMap.put(record.getCode(), record);
            }
        } catch (SQLException e) {
           logger.error("查询学校单位列表异常", e);
        } finally {
            realeaseResource(rs, stat, conn);
        }*/

        return unitMap;
    }

    @CachePut(value = "schoolUnits")
    public Map<String, SchoolUnit> refreshSchoolUnits(){

        return getSchoolUnitMap();
    }

    @Override
    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {}
}