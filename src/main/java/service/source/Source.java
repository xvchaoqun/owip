package service.source;

import bean.ColumnBean;
import domain.sys.SysUserSync;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import persistence.sys.SysUserSyncMapper;
import sys.utils.JSONUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Source {

    //public DruidDataSource bnuDS;
    private Logger logger = LoggerFactory.getLogger(getClass());
    protected static Connection conn;
    @Autowired
    public SysUserSyncMapper sysUserSyncMapper;

    public Connection initConn() {

        if (null == conn) {
            try {
                ApplicationContext ac = new ClassPathXmlApplicationContext(
                        new String[]{"/ext-source.xml"});
                DataSource dataSource = (DataSource) ac.getBean("extDS");
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
    public void excute(String schema, String tableName, Integer syncId) {

        initConn();

        Statement stat = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>();

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
            for (int i = 1; i <= pageNo; i++) {
                logger.info(String.format("总数：%s， 每页%s条， 总%s页， 当前第%s页", count, pageSize, pageNo, i));
                String sql = getLimitString("select * from " + tbl, (i - 1) * pageSize, pageSize);
                //stat = conn.createStatement();
                rs = stat.executeQuery(sql);
                while (rs != null && rs.next()) {

                    map = new HashMap<>();
                    for (ColumnBean columnBean : columnBeans) {
                        String name = columnBean.getName();
                        map.put(name, rs.getString(name));
                    }

                    update(map, rs);
                }

                if(syncId!=null) {
                    SysUserSync _sync = sysUserSyncMapper.selectByPrimaryKey(syncId);
                    if (_sync.getIsStop()) {
                        break; // 强制结束
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("出错：{}", JSONUtils.toString(map), ex);
        } finally {
            try {
                rs.close();
                stat.close();
            } catch (Exception ex) {
                logger.error("关闭失败, {}", JSONUtils.toString(map), ex);
            }
        }
    }

    // 按条件从oracle导入数据到mysql
    public int excute(String schema, String tableName, String searchStr) {

        initConn();

        Statement stat = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        try {
            List<ColumnBean> columnBeans = getTableColumns(tableName);
            String tbl = String.format("%s.%s", schema, tableName);
            String sql = "select * from " + tbl +(StringUtils.isNotBlank(searchStr)?" " + searchStr:"");
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs != null && rs.next()) {

                map = new HashMap<>();
                for (ColumnBean columnBean : columnBeans) {
                    String name = columnBean.getName();
                    map.put(name, rs.getString(name));
                }

                update(map, rs);
                i++;
            }
        } catch (Exception ex) {
            logger.error("出错：{}", JSONUtils.toString(map), ex);
        } finally {
            try {
                rs.close();
                stat.close();
            } catch (Exception ex) {
                logger.error("关闭失败, {}", JSONUtils.toString(map), ex);
            }
        }

        return i;
    }

    // 按条件从oracle导入数据到mysql（分页）
    public int excute(String schema, String tableName, String searchStr, Integer syncId) {

        initConn();

        Statement stat = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>();
        int ret = 0;
        try {
            List<ColumnBean> columnBeans = getTableColumns(tableName);
            String tbl = String.format("%s.%s", schema, tableName);
            searchStr = (StringUtils.isNotBlank(searchStr)?" " + searchStr:"");
            int count = 0;
            String countSql = "select count(*) from " + tbl + searchStr;
            stat = conn.createStatement();
            rs = stat.executeQuery(countSql);
            while (rs != null && rs.next()) {
                count = rs.getInt(1);
            }
            int pageSize = 1000;
            int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);
            logger.info(String.format("总数：%s， 每页%s条， 总%s页", count, pageSize, pageNo));
            for (int i = 1; i <= pageNo; i++) {
                logger.info(String.format("总数：%s， 每页%s条， 总%s页， 当前第%s页", count, pageSize, pageNo, i));
                String sql = getLimitString("select * from " + tbl + searchStr, (i - 1) * pageSize, pageSize);

                rs = stat.executeQuery(sql);
                while (rs != null && rs.next()) {

                    map = new HashMap<>();
                    for (ColumnBean columnBean : columnBeans) {
                        String name = columnBean.getName();
                        map.put(name, rs.getString(name));
                    }

                    update(map, rs);
                    ret++;
                }

                if(syncId!=null) {
                    SysUserSync _sync = sysUserSyncMapper.selectByPrimaryKey(syncId);
                    if (_sync.getIsStop()) {
                        break; // 强制结束
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("出错：{}", JSONUtils.toString(map), ex);
        } finally {
            try {
                rs.close();
                stat.close();
            } catch (Exception ex) {
                logger.error("关闭失败, {}", JSONUtils.toString(map), ex);
            }
        }

        return ret;
    }

    // 读取oracle表的字段信息
    public List<ColumnBean> getTableColumns(String tablename) throws Exception {

        List<ColumnBean> columnBeans = new ArrayList<ColumnBean>();

        Statement stat = null;
        ResultSet rs = null;
        String sql = " select * from all_tab_columns  where Table_Name="
                + " upper('" + tablename + "')";
        stat = conn.createStatement();
        logger.info("sql=" + sql);
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
