<%@ page import="java.sql.*" %>
<%@ page import="sys.utils.PropertiesUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="sys.utils.JSONUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
        Class.forName("com.mysql.jdbc.Driver");
    String schema = PropertiesUtils.getString("db.schema");
    Connection conn = DriverManager.getConnection(PropertiesUtils.getString("jdbc_url").replace("${db.schema}", schema),
                PropertiesUtils.getString("jdbc_user"), PropertiesUtils.getString("jdbc_password"));;
        Statement stat = null;
        Statement stat2 = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            stat2=conn.createStatement();
            String sql = "select * from pmd_config_member";
            rs = stat.executeQuery(sql);
            while (rs != null && rs.next()) {
                int userId = rs.getInt("user_id");

                Map<String, BigDecimal> salaryMap = new HashMap<>();
                salaryMap.put("gwgz", rs.getBigDecimal("gwgz"));
                salaryMap.put("xjgz", rs.getBigDecimal("xjgz"));
                salaryMap.put("gwjt", rs.getBigDecimal("gwjt"));
                salaryMap.put("zwbt", rs.getBigDecimal("zwbt"));
                salaryMap.put("zwbt1", rs.getBigDecimal("zwbt1"));
                salaryMap.put("shbt", rs.getBigDecimal("shbt"));
                salaryMap.put("sbf", rs.getBigDecimal("sbf"));
                salaryMap.put("xlf", rs.getBigDecimal("xlf"));
                salaryMap.put("gzcx", rs.getBigDecimal("gzcx"));
                salaryMap.put("shiyebx", rs.getBigDecimal("shiyebx"));
                salaryMap.put("yanglaobx", rs.getBigDecimal("yanglaobx"));
                salaryMap.put("yiliaobx", rs.getBigDecimal("yiliaobx"));
                salaryMap.put("zynj", rs.getBigDecimal("zynj"));
                salaryMap.put("gjj", rs.getBigDecimal("gjj"));

                if(salaryMap.size()>0) {
                    String salaryJSON = JSONUtils.toString(salaryMap, false);
                    sql = "update pmd_config_member set salary='" + salaryJSON + "' where user_id=" + userId;
                    //System.out.println("sql = " + sql);
                    stat2.execute(sql);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //throw new Exception("连接数据库失败,请检查数据库连接。");
        } finally {
            //close(conn, stat, rs);
            try {
                stat.close();
                stat2.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
%>
</body>
</html>
