<%@ page import="sys.tags.CmTag" %>
<%@ page import="persistence.sys.SysUserInfoMapper" %>
<%@ page import="domain.sys.SysUserInfoExample" %>
<%@ page import="domain.sys.SysUserInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.ibatis.session.RowBounds" %>
<%@ page import="sys.utils.PatternUtils" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>正则提取简历信息</title>
</head>
<body>

<%
    SysUserInfoMapper sysUserInfoMapper = CmTag.getBean(SysUserInfoMapper.class);
    SysUserInfoExample example = new SysUserInfoExample();
    example.createCriteria().andResumeIsNotNull();
    //List<SysUserInfo> uis = sysUserInfoMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 100));
    List<SysUserInfo> uis = sysUserInfoMapper.selectByExample(example);
    out.write( "<br/>");
    for (SysUserInfo ui : uis) {

        SysUserView u = CmTag.getUserById(ui.getUserId());
        String resume = ui.getResume();
        List<String> edus = PatternUtils.withdrawAll("(\\d{4}\\.\\d{2}获得.*学位)", resume);
        if(edus.size()>0) {
            //out.write(ui.getRealname() + "  " + edus + "<br/>");
            for (String edu : edus) {

                String date = PatternUtils.withdraw("(\\d{4}\\.\\d{2})", edu);
                String degree = PatternUtils.withdraw("获得(.*)学位", edu);


                out.write(u.getCode() + "," + ui.getRealname() + "," + date + "," + degree + "<br/>");
            }

        }
    }

%>

</body>
</html>
