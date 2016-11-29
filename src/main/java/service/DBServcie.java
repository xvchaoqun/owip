package service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fafa on 2016/1/15.
 */
@Service
public class DBServcie {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringProps springProps;

    @Cacheable(value="TableColumns", key = "#tablename")
    public Set<String> getTableColumns(String tablename) throws Exception {

        Set<String> tableColumns = new HashSet<>();
        Connection conn = dataSource.getConnection();
        Statement stat = null;
        ResultSet rs = null;
        try {

            String sql = " select column_name, data_type, character_maximum_length as data_length, column_comment as comments from information_schema.columns "
                    + "where table_name='" + tablename + "' and table_schema='" + springProps.schema + "'";

            stat = conn.createStatement();
            //System.out.println("sql=" + sql);
            rs = stat.executeQuery(sql);
            while (rs != null && rs.next()) {
                String columnName = StringUtils.lowerCase(rs.getString("column_name"));
                tableColumns.add(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new Exception("连接数据库失败,请检查数据库连接。");
        } finally {
            //close(conn, stat, rs);
            try {
                stat.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return tableColumns;
    }
}
