<%@ page import="sys.tags.CmTag" %>
<%@ page import="domain.pcs.PcsPrCandidate" %>
<%@ page import="persistence.pcs.PcsPrCandidateMapper" %>
<%@ page import="domain.pcs.PcsPrCandidateExample" %>
<%@ page import="java.util.List" %>
<%@ page import="service.SpringProps" %>
<%@ page import="sys.service.ApplicationContextSupport" %>
<%@ page import="sys.utils.PropertiesUtils" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="sys.constants.RoleConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);

    Class.forName(PropertiesUtils.getString("jdbc_driverClassName"));
    Connection conn = DriverManager.getConnection(PropertiesUtils.getString("jdbc_url").replace("${db.schema}",
            PropertiesUtils.getString("db.schema")),
            PropertiesUtils.getString("jdbc_user"), PropertiesUtils.getString("jdbc_password"));
    Statement stat = null;
    ResultSet rs = null;
    try {
        stat = conn.createStatement();
        String sql = "select u.id from sys_user u, ow_member m where m.user_id=u.id and m.`status`=1 and u.role_ids not like '%,8,%'";
        rs = stat.executeQuery(sql);

        while (rs != null && rs.next()) {
            int userId = rs.getInt("id");
            sysUserService.addRole(userId, RoleConstants.ROLE_MEMBER);
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

%>

</body>
</html>
