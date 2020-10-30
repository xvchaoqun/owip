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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%

    Class.forName(PropertiesUtils.getString("jdbc_driverClassName"));
        Connection conn = DriverManager.getConnection(PropertiesUtils.getString("jdbc_url").replace("${db.schema}",
                PropertiesUtils.getString("db.schema")),
                PropertiesUtils.getString("jdbc_user"), PropertiesUtils.getString("jdbc_password"));;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();

            PcsPrCandidateMapper pcsPrCandidateMapper = CmTag.getBean(PcsPrCandidateMapper.class);
            PcsPrCandidateExample example = new PcsPrCandidateExample();
            example.createCriteria().andDegreeIsNull();
            List<PcsPrCandidate> pcsPrCandidates = pcsPrCandidateMapper.selectByExample(example);
            for (PcsPrCandidate pcsPrCandidate : pcsPrCandidates) {

                String code = pcsPrCandidate.getCode();

                String sql = "select id, code, realname, degree, 学科 from tmp_teacher_edu where code='"+code+"' and high_degree='是' order by start desc, end desc limit 1";
                rs = stat.executeQuery(sql);

                String degree = null;
                while (rs != null && rs.next()) {
                    degree = rs.getString("学科");
                    if(StringUtils.isBlank(degree))
                        degree = rs.getString("degree");
                }

                if(StringUtils.isNotBlank(degree)) {
                    PcsPrCandidate record = new PcsPrCandidate();
                    record.setId(pcsPrCandidate.getId());
                    record.setDegree(degree);
                    pcsPrCandidateMapper.updateByPrimaryKeySelective(record);

                    out.write("<br/>code:"+code + " degree:" + degree);
                }
            }

            example = new PcsPrCandidateExample();
            example.createCriteria().andEducationIsNull();
            pcsPrCandidates = pcsPrCandidateMapper.selectByExample(example);
            for (PcsPrCandidate pcsPrCandidate : pcsPrCandidates) {

                String code = pcsPrCandidate.getCode();

                String sql = "select id, code, realname, edu from tmp_teacher_edu where code='"+code+"' and high_edu='是' order by start desc, end desc limit 1";
                rs = stat.executeQuery(sql);

                String edu = null;
                while (rs != null && rs.next()) {
                    edu = rs.getString("edu");
                }

                if(StringUtils.isNotBlank(edu)) {
                    PcsPrCandidate record = new PcsPrCandidate();
                    record.setId(pcsPrCandidate.getId());
                    record.setEducation(edu);
                    pcsPrCandidateMapper.updateByPrimaryKeySelective(record);

                    out.write("<br/>code:"+code + " edu:" + edu);
                }
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
