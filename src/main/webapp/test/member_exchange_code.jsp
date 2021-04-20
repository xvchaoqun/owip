<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.List" %>
<%@ page import="sys.utils.PropertiesUtils" %>
<%@ page import="java.sql.*" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="service.party.MemberService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="domain.sys.SysUser" %>
<%@ page import="persistence.sys.SysUserMapper" %>
<%@ page import="service.global.CacheHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
   // 与excel中不一致的学工号进行替换

    CacheHelper cacheHelper = CmTag.getBean(CacheHelper.class);
    SysUserMapper sysUserMapper = CmTag.getBean(SysUserMapper.class);

    Class.forName(PropertiesUtils.getString("jdbc_driverClassName"));
        Connection conn = DriverManager.getConnection(PropertiesUtils.getString("jdbc_url").replace("${db.schema}",
                PropertiesUtils.getString("db.schema")),
                PropertiesUtils.getString("jdbc_user"), PropertiesUtils.getString("jdbc_password"));;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();

            String sql = "select m.user_id, m.code , m.realname, a.code as excel from ow_member_view m " +
                    "left join tmp_member_apply a on m.idcard=a.idcard " +
                    "where m.code!=a.code and a.code is not null and a.code !='' and a.code  in (select code from sys_user)";
            rs = stat.executeQuery(sql);

            int success = 0;
            List<String> errors = new ArrayList<>();
            while (rs != null && rs.next()) {
               int userId = rs.getInt("user_id");
               String code = rs.getString("code");
               String excel = rs.getString("excel");

                // excel 替换成 code_$
                SysUserView excelUser = CmTag.getUserByCode(excel);
                SysUser record = new SysUser();
                record.setId(excelUser.getId());
                record.setUsername(code +"_$");
                record.setCode(code +"_$");
                sysUserMapper.updateByPrimaryKeySelective(record);

                cacheHelper.clearUserCache(record);

                // code 替换成 excel
                SysUser record2 = new SysUser();
                record2.setId(userId);
                record2.setUsername(excel);
                record2.setCode(excel);
                sysUserMapper.updateByPrimaryKeySelective(record2);

                cacheHelper.clearUserCache(record2);
                // code_$换成code
                SysUser record3 = new SysUser();
                record3.setId(excelUser.getId());
                record3.setUsername(code);
                record3.setCode(code);
                sysUserMapper.updateByPrimaryKeySelective(record3);

                cacheHelper.clearUserCache(record3);

                success++;
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
