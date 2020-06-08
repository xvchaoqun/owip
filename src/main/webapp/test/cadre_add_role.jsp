<%@ page import="domain.cadre.CadreView" %>
<%@ page import="service.cadre.CadreService" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="sys.constants.CadreConstants" %>
<%@ page import="sys.constants.RoleConstants" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    // for test 给所有的干部加上干部身份

    CadreService cadreService = CmTag.getBean(CadreService.class);
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);

    Map<Integer, CadreView> cadreMap = cadreService.findAll();
    for (CadreView cadre : cadreMap.values()) {

        if (CadreConstants.CADRE_STATUS_SET.contains(cadre.getStatus())) {
            // 添加干部身份
            sysUserService.addRole(cadre.getUserId(), RoleConstants.ROLE_CADRE_CJ);
        }
    }
%>
</body>
</html>
