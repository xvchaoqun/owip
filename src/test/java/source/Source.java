package source;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sys.utils.JSONUtils;
import tpl.ColumnBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Source {

    private Logger logger = LoggerFactory.getLogger(getClass());
    protected Connection conn;

    public Connection setConn(DruidDataSource dataSource) {

        if (null == conn) {
            try {
                conn = dataSource.getConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return conn;
    }

    public abstract void update(Map<String, Object> map, ResultSet rs) throws SQLException;

    // 从oracle导入数据到mysql
    public void excute(String schema, String tableName){

        Statement stat = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>() ;

        try {
            List<ColumnBean> columnBeans = getTableColumns(tableName);

            String tbl = String.format("%s.%s", schema, tableName);
            int count = 0;
            String countSql = "select count(*) from " + tbl;
            stat = conn.createStatement();
            rs = stat.executeQuery(countSql);
            while (rs != null && rs.next()) {
                count = rs.getInt(1);
            }
            int pageSize = 1000;
            int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);
            logger.info(String.format("总数：%s， 每页%s条， 总%s页", count, pageSize, pageNo));
            for (int i=0; i <= pageNo; i++) {
                String sql = getLimitString("select * from " + tbl, (i - 1) * pageSize, pageSize);
                stat = conn.createStatement();
                rs = stat.executeQuery(sql);
                while (rs != null && rs.next()) {

                    map = new HashMap<>();
                    for (ColumnBean columnBean : columnBeans) {
                        String name = columnBean.getName();
                        map.put(name, rs.getString(name));
                    }

                    update(map, rs);
                }
            }
        }catch (Exception ex){
            logger.error("出错：{}", JSONUtils.toString(map),  ex);
        }finally {
            try {
                rs.close();
                stat.close();
            }catch (Exception ex){
                logger.error("关闭失败, {}", JSONUtils.toString(map),  ex);
            }
        }
    }
    // 读取oracle表的字段信息
    public List<ColumnBean> getTableColumns(String tablename) throws Exception {

        List<ColumnBean> columnBeans = new ArrayList<ColumnBean>();

        Statement stat = null;
        ResultSet rs = null;
        String sql = " select * from all_tab_columns  where Table_Name="
                + " upper('" + tablename + "')";
        stat = conn.createStatement();
        System.out.println("sql=" + sql);
        rs = stat.executeQuery(sql);
        while (rs != null && rs.next()) {
            String columnName = rs.getString("column_name");
            String dataType = rs.getString("data_type");
            int length = rs.getInt("data_length");

            if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(columnName, "ID")
                    || org.apache.commons.lang3.StringUtils.equalsIgnoreCase(columnName, "status")) continue;

            columnBeans.add(new ColumnBean(org.apache.commons.lang3.StringUtils.lowerCase(columnName), dataType, length, null));
        }

        return columnBeans;
    }

    public String getLimitString(String sql, int offset, int limit) {

        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

        pagingSelect.append(sql);

        pagingSelect.append(" ) row_ ) where rownum_ > ").append(offset).append(" and rownum_ <= ").append(offset + limit);

        return pagingSelect.toString();
    }
}
