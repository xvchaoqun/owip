<%@ page import="domain.cadre.CadreView" %>
<%@ page import="service.cadre.CadreService" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="sys.constants.CadreConstants" %>
<%@ page import="sys.constants.RoleConstants" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.Map" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.cadre.Cadre" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    int notCadreCount = 0 , cadreCount = 0;
    CadreService cadreService = CmTag.getBean(CadreService.class);
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);

    // 删除非干部
    List<SysUserView> sysUsers = sysUserService.findByRole(RoleConstants.ROLE_CADRE_CJ);
    for (SysUserView sysUser : sysUsers) {
        Cadre c = cadreService.getByUserId(sysUser.getUserId());
        if(c==null || !CadreConstants.CADRE_STATUS_SET.contains(c.getStatus())){
            sysUserService.delRole(sysUser.getUserId(), RoleConstants.ROLE_CADRE_CJ);
            notCadreCount++;
        }
    }

    // 给所有的干部加上干部身份
    Map<Integer, CadreView> cadreMap = cadreService.findAll();
    for (CadreView cadre : cadreMap.values()) {

        if (CadreConstants.CADRE_STATUS_SET.contains(cadre.getStatus())) {
            // 添加干部身份
            sysUserService.addRole(cadre.getUserId(), RoleConstants.ROLE_CADRE_CJ);
            cadreCount++;
        }
    }

    out.write("notCadreCount="+notCadreCount + ",cadreCount="+cadreCount);
%>
</body>
</html>
