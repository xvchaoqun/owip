<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.cadre.Cadre" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="static sys.constants.CadreConstants.CADRE_STATUS_LEADER_LEAVE" %>
<%@ page import="static sys.constants.CadreConstants.CADRE_STATUS_CJ_LEAVE" %>
<%@ page import="static sys.constants.CadreConstants.*" %>
<%@ page import="domain.cadre.CadreExample" %>
<%@ page import="persistence.cadre.CadreMapper" %>
<%@ page import="sys.constants.RoleConstants" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
   /* SysRoleMapper sysRoleMapper = CmTag.getBean(SysRoleMapper.class);
    SysRoleExample example = new SysRoleExample();
    example.createCriteria().andTypeEqualTo((byte) 2);
    List<SysRole> sysRoles=sysRoleMapper.selectByExample(example);

    for (SysRole role : sysRoles) {
       role.setResourceIdsMinus(role.getResourceIds());
       role.setmResourceIdsMinus(role.getmResourceIds());
       role.setResourceIds("-1");
       role.setmResourceIds("-1");
       sysRoleMapper.updateByPrimaryKey(role);
    }*/
    List<Byte> states= new ArrayList<>();
    states.add(CADRE_STATUS_LEADER_LEAVE);
    states.add(CADRE_STATUS_CJ_LEAVE);
    states.add(CADRE_STATUS_KJ_LEAVE);

    CadreExample example1 = new CadreExample();
    example1.createCriteria().andStatusIn(states);
    CadreMapper cadreMapper = CmTag.getBean(CadreMapper.class);
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);
    List<Cadre> cadres=cadreMapper.selectByExample(example1);
    for(Cadre cadre:cadres){
        //添加离任干部角色
        sysUserService.addRole(cadre.getUserId(), RoleConstants.ROLE_CADRE_LEAVE);
    }
%>
</body>
</html>
