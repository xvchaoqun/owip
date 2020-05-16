package service;

import bean.ColumnBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by fafa on 2016/1/15.
 */
@Service
public class DBServcie {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringProps springProps;

    @Cacheable(value="TableColumns", key = "#tablename")
    public Set<String> getTableColumns(String tablename){

        Map<String, ColumnBean> columnBeanMap = getColumnBeanMap(tablename);
        return new HashSet<>(columnBeanMap.keySet());
    }

    @Cacheable(value="ColumnBeans", key = "#tablename")
    public Map<String, ColumnBean> getColumnBeanMap(String tablename){

        Map<String, ColumnBean> columnBeansMap = new LinkedHashMap<>();
        
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();

            String sql = " select column_name, data_type, character_maximum_length as data_length, column_comment as comments from information_schema.columns "
                    + "where table_name='" + tablename + "' and table_schema='" + springProps.schema + "'";

            stat = conn.createStatement();
            //System.out.println("sql=" + sql);
            rs = stat.executeQuery(sql);
            while (rs != null && rs.next()) {
                
                String columnName = StringUtils.lowerCase(rs.getString("column_name"));
				String dataType = rs.getString("data_type");
				long length = rs.getLong("data_length");
				String comments = rs.getString("comments");

				if(StringUtils.isNotBlank(comments))
					comments = comments.split("，")[0];
                else
					comments = columnName;
				
                columnBeansMap.put(columnName, new ColumnBean(columnName, dataType, length,  comments));
            }
        } catch (SQLException e) {
            logger.error("异常", e);
            //throw new Exception("连接数据库失败,请检查数据库连接。");
        } finally {
            //close(conn, stat, rs);
            try {
                if(null!=stat) stat.close();
                if(null!=conn) conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                logger.error("异常", e);
            }
        }

        return columnBeansMap;
    }
}
