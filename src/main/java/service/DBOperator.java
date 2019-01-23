package service;

import bean.ColumnBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DBOperator {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DataSource dataSource;

	public List<String> getTableNameList(String schema) throws SQLException {

		Connection conn = dataSource.getConnection();
		Statement stat = null;
		ResultSet rs = null;

		List<String> list = new ArrayList<>();
		try {
			String sql = "select table_name from information_schema.tables "
					+ "where table_schema='" + schema + "'";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);

			while (rs != null && rs.next()) {
				String table_name = rs.getString("table_name");
				list.add(table_name);
			}
		}catch(SQLException ex){

			logger.error("异常", ex);
		}finally{

			try {
				stat.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("异常", e);
			}
		}

		return list;

	}
	
	public String getTableComments(String tablename, String schema) throws SQLException{
		
		Connection conn = dataSource.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			String sql = "select table_comment from information_schema.tables "
					+ "where table_name='"+ tablename +"' and table_schema='" + schema + "'";
			stat = conn.createStatement();
			System.out.println("sql=" + sql);
			rs = stat.executeQuery(sql);
			
			while (rs != null && rs.next()) {

				String table_comment = rs.getString("table_comment");
				if(StringUtils.isNotBlank(table_comment))
					return table_comment.split("，")[0];
			}
		}catch(SQLException ex){
			
			logger.error("异常", ex);
		}finally{
			
			try {
				stat.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("异常", e);
			}
		}
		
		return null;
	}
	// 读取mysql表的部分字段信息（过滤）
	public List<ColumnBean> getTableColumns(String tablename, String schema, String columns, boolean include) throws Exception {
		
		List<ColumnBean> columnBeans = new ArrayList<ColumnBean>();
		
		Connection conn = dataSource.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			
			String sql = " select column_name, data_type, character_maximum_length as data_length, column_comment as comments from information_schema.columns "
					+ "where table_name='"+ tablename +"' and table_schema='" + schema + "'";
			stat = conn.createStatement();
			System.out.println("sql=" + sql);
			rs = stat.executeQuery(sql);
			while (rs != null && rs.next()) {
				String columnName = StringUtils.lowerCase(rs.getString("column_name"));
				String dataType = rs.getString("data_type");
				long length = rs.getLong("data_length");
				String comments = rs.getString("comments");

				if(StringUtils.isNotBlank(comments))
					comments = comments.split("，")[0];

				if(null != columns){
					
					if((include && (("," +columns+ ",").indexOf("," + columnName + ",") == -1))
							|| (!include && (("," +columns+ ",").indexOf("," + columnName + ",") > -1))){
						
						continue;
					}
				}
				
				if(StringUtils.isBlank(comments))
					comments = columnName;
				
				// 排除fk
				boolean isFk = false;
				/*for(String tableName :fatherMap.keySet()){
					ForeignKeyBean fkBean = fatherMap.get(tableName);
					if(StringUtils.equals(tablename, tableName) && 
							(StringUtils.equals(fkBean.getColumnName(), columnName)
									||StringUtils.equals(fkBean.getrColumnName(), columnName))){
						isFk = true;
						break;
					}
				}*/
				if(!isFk)
				columnBeans.add(new ColumnBean(columnName, dataType, length,  comments));
			}			
		} catch (SQLException e) {
			logger.error("异常", e);
			//throw new Exception("连接数据库失败,请检查数据库连接。");			
		} finally {
			//close(conn, stat, rs);
			try {
				stat.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("异常", e);
			}
		}
		
		return columnBeans;
	}
	
	// 读取mysql表的全部字段信息
	public Map<String, ColumnBean> getTableColumnsMap(String tablename, String schema) throws Exception {
		
		Map<String, ColumnBean> columnBeansMap = new HashMap<String, ColumnBean>();
		
		Connection conn = dataSource.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			
			String sql = " select column_name, data_type, character_maximum_length as data_length, column_comment as comments from information_schema.columns "
					+ "where table_name='"+ tablename +"' and table_schema='" + schema + "'";
			
			stat = conn.createStatement();
			System.out.println("sql=" + sql);
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
				stat.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("异常", e);
			}
		}
		
		return columnBeansMap;
	}
	
}
