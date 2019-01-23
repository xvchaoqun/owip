package service.ext;

import bean.ColumnBean;
import domain.sys.SysSync;
import org.apache.commons.lang.StringUtils;
import org.mybatis.extend.interceptor.Dialect;
import org.mybatis.extend.interceptor.MySQLDialect;
import org.mybatis.extend.interceptor.OracleDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import persistence.sys.SysSyncMapper;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

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
    private static Logger logger = LoggerFactory.getLogger(Source.class);
    private static DataSource ds;
    @Autowired
    public SysSyncMapper sysSyncMapper;
    protected static Dialect dialect = null;

    public Connection getConn() {

        Dialect.Type databaseType = null;
        try {
            databaseType = Dialect.Type.valueOf(PropertiesUtils.getString("ext_dialect").toUpperCase());
        } catch (Exception e) {
            // ignore
        }

        switch (databaseType) {
            case ORACLE:
                dialect = new OracleDialect();
                break;
            case MYSQL:
                dialect = new MySQLDialect();
                break;
        }

        if (null == ds) {
            ApplicationContext ac = new ClassPathXmlApplicationContext(
                    new String[]{"/ext-source.xml"});
            ds = (DataSource) ac.getBean("extDS");
        }
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            logger.error("异常", e);
        }

        return null;
    }

    //释放资源
    public static void realeaseResource(ResultSet rs, Statement stat,Connection conn){

        if(null != rs){
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("异常", e);
            }
        }

        if(null != stat){
            try {
                stat.close();
            } catch (SQLException e) {
                logger.error("异常", e);
            }
        }

        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("异常", e);
        }
    }

    public abstract void update(Map<String, Object> map, ResultSet rs) throws SQLException;

    // 从oracle导入数据到mysql
    public void excute(String schema, String tableName, Integer syncId) {

        Connection conn = getConn();

        Statement stat = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>();

        try {
            List<ColumnBean> columnBeans = getTableColumns(schema, tableName);
            if(columnBeans.size()==0){
                logger.error("出错：无法读取数据表字段。{}.{}", schema, tableName);
                return ;
            }
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
                String sql = dialect.getLimitString("select * from " + tbl, (i - 1) * pageSize, pageSize);
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
                    SysSync _sync = sysSyncMapper.selectByPrimaryKey(syncId);
                    if (_sync.getIsStop()) {
                        break; // 强制结束
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("出错：{}", JSONUtils.toString(map, false));
            logger.error("异常", ex);
        } finally {
            realeaseResource(rs, stat, conn);
        }
    }

    // 按条件从oracle导入数据到mysql
    public int excute(String schema, String tableName, String searchStr) {

        Connection conn = getConn();

        Statement stat = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        try {
            List<ColumnBean> columnBeans = getTableColumns(schema, tableName);
            if(columnBeans.size()==0){
                logger.error("出错：无法读取数据表字段。{}.{}", schema, tableName);
                return -1;
            }
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
            logger.error("出错：{}", JSONUtils.toString(map, false));
            logger.error("异常", ex);
        } finally {
            realeaseResource(rs, stat, conn);
        }

        return i;
    }

    // 按条件从oracle导入数据到mysql（分页）
    public int excute(String schema, String tableName, String searchStr, Integer syncId) {

        Connection conn = getConn();

        Statement stat = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<>();
        int ret = 0;
        try {
            List<ColumnBean> columnBeans = getTableColumns(schema, tableName);
            if(columnBeans.size()==0){
                logger.error("出错：无法读取数据表字段。{}.{}", schema, tableName);
                return -1;
            }
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
                String sql = dialect.getLimitString("select * from " + tbl + searchStr, (i - 1) * pageSize, pageSize);

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
                    SysSync _sync = sysSyncMapper.selectByPrimaryKey(syncId);
                    if (_sync.getIsStop()) {
                        break; // 强制结束
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("出错：{}", JSONUtils.toString(map,false));
            logger.error("异常", ex);
        } finally {
            realeaseResource(rs, stat, conn);
        }

        return ret;
    }

    // 读取oracle表的字段信息
    public List<ColumnBean> getTableColumns(String schema, String tablename) throws Exception {

        Connection conn = getConn();

        List<ColumnBean> columnBeans = new ArrayList<ColumnBean>();

        Statement stat = null;
        ResultSet rs = null;
        try {

            String sql = dialect.getTableColumnSql(schema, tablename);
            stat = conn.createStatement();
            logger.info("sql=" + sql);
            rs = stat.executeQuery(sql);
            while (rs != null && rs.next()) {
                String columnName = rs.getString("column_name");
                String dataType = rs.getString("data_type");
                long length = rs.getLong("data_length");

                if (StringUtils.equalsIgnoreCase(columnName, "ID")
                        || StringUtils.equalsIgnoreCase(columnName, "status")) continue;

                columnBeans.add(new ColumnBean(StringUtils.lowerCase(columnName), dataType, length, null));
            }
        } finally {
            realeaseResource(rs, stat, conn);
        }

        return columnBeans;
    }
}
