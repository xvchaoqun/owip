<%@ page import="domain.sys.SysUserView" %>
<%@ page import="service.party.BranchAdminService" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="sys.constants.RoleConstants" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>支部管理员</title>
</head>
<body>
<%
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);
    BranchAdminService branchAdminService = CmTag.getBean(BranchAdminService.class);
    List<SysUserView> sysUsers = sysUserService.findByRole(RoleConstants.ROLE_BRANCHADMIN);
    for (SysUserView sysUser : sysUsers) {

        int userId = sysUser.getId();
        List<Integer> adminBranchIdList = branchAdminService.adminBranchIdList(userId);
        if(adminBranchIdList.size()==0){

            out.write(sysUser.getRealname() + " " + sysUser.getCode() + " <br/>");

            sysUserService.delRole(userId, RoleConstants.ROLE_BRANCHADMIN);
        }
    }
%>
</body>
</html>
