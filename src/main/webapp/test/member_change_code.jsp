<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.List" %>
<%@ page import="sys.utils.PropertiesUtils" %>
<%@ page import="java.sql.*" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="service.party.MemberService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%

    MemberService memberService = CmTag.getBean(MemberService.class);

    Class.forName(PropertiesUtils.getString("jdbc_driverClassName"));
        Connection conn = DriverManager.getConnection(PropertiesUtils.getString("jdbc_url").replace("${db.schema}",
                PropertiesUtils.getString("db.schema")),
                PropertiesUtils.getString("jdbc_user"), PropertiesUtils.getString("jdbc_password"));;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();

            String sql = "select tm.username, tm.code, tm.zgh from tmp_member tm, sys_user u, ow_member om where tm.username=u.username and u.id=om.user_id and om.status=1";
            rs = stat.executeQuery(sql);

            int success = 0;
            List<String> errors = new ArrayList<>();
            while (rs != null && rs.next()) {
               String username = rs.getString("username");
               String code = rs.getString("code");
               String zgh = rs.getString("zgh");

                SysUserView oldUser = CmTag.getUserByUsername(username);
                SysUserView newUser = CmTag.getUserByUsername(zgh);

                try {
                    memberService.exchangeMemberCode(oldUser.getUserId(), newUser.getUserId(), "更换账号，原账号：" + username + ", " + code);

                    success++;
                }catch (Exception ex){
                    errors.add(ex.getMessage());
                }
            }

            for (String error : errors) {
                out.write(error + "<br/>");
            }

            out.write("总共更换账号数量："  + success);

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
