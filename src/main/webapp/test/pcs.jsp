<%@ page import="sys.tags.CmTag" %>
<%@ page import="domain.base.MetaType" %>
<%@ page import="persistence.common.CommonMapper" %>
<%@ page import="persistence.pcs.PcsPrAllocateMapper" %>
<%@ page import="domain.pcs.PcsPrAllocateExample" %>
<%@ page import="domain.pcs.PcsPrAllocate" %>
<%@ page import="java.util.List" %>
<%@ page import="sys.utils.PropertiesUtils" %>
<%@ page import="sys.utils.JSONUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    MetaType proType = CmTag.getMetaTypeByName("mc_pcs_pr_type", "专业技术人员和干部代表");
    MetaType stuType = CmTag.getMetaTypeByName("mc_pcs_pr_type", "学生代表");
    MetaType retireType = CmTag.getMetaTypeByName("mc_pcs_pr_type", "离退休代表");
    CommonMapper commonMapper = CmTag.getBean(CommonMapper.class);
    commonMapper.excuteSql("update pcs_pr_candidate set type=" + proType.getId() + " where type=1");
    commonMapper.excuteSql("update pcs_pr_candidate set type=" + stuType.getId() + " where type=2");
    commonMapper.excuteSql("update pcs_pr_candidate set type=" + retireType.getId() + " where type=3");

    Class.forName("com.mysql.jdbc.Driver");
    String schema = PropertiesUtils.getString("db.schema");
    Connection conn = DriverManager.getConnection(PropertiesUtils.getString("jdbc_url").replace("${db.schema}", schema),
            PropertiesUtils.getString("jdbc_user"), PropertiesUtils.getString("jdbc_password"));
    Statement stat = null;
    Statement stat2 = null;
    ResultSet rs = null;
    try {
        stat = conn.createStatement();
        stat2=conn.createStatement();
        String sql = "select * from pcs_pr_allocate";
        rs = stat.executeQuery(sql);
        while (rs != null && rs.next()) {
            Map<Integer, Integer> prCountMap = new HashMap<>();
            prCountMap.put(proType.getId(), rs.getInt("pro_count"));
            prCountMap.put(stuType.getId(), rs.getInt("stu_count"));
            prCountMap.put(retireType.getId(), rs.getInt("retire_count"));

            out.write("<br/>" + rs.getInt("id"));
            sql = "update pcs_pr_allocate set pr_count='" + JSONUtils.toString(prCountMap) + "' where id=" + rs.getInt("id");
            stat2.execute(sql);
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

    out.write("done." + System.currentTimeMillis());
%>
</body>
</html>
