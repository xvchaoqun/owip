<%@ page import="domain.oa.*" %>
<%@ page import="persistence.oa.OaGridPartyMapper" %>
<%@ page import="service.oa.OaGridPartyDataService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="service.SpringProps" %>
<%@ page import="org.springframework.util.StringUtils" %>
<%@ page import="controller.global.OpException" %>
<%@ page import="persistence.oa.OaTaskMapper" %>
<%@ page import="persistence.oa.common.IOaTaskMapper" %>
<%@ page import="sys.utils.NumberUtils" %>
<%@ page import="java.util.Set" %>
<%@ page import="sys.constants.RoleConstants" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新共享任务角色</title>
</head>
<body>
<%
    OaTaskMapper oaTaskMapper = CmTag.getBean(OaTaskMapper.class);
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);

    OaTaskExample example = new OaTaskExample();
    example.createCriteria().andUserIdsIsNotNull().andUserIdsNotEqualTo("");
    List<OaTask> oaTasks = oaTaskMapper.selectByExample(example);

    for (OaTask oaTask : oaTasks) {
        String userIds = oaTask.getUserIds();
        Set<Integer> userIdSet = NumberUtils.toIntSet(userIds, ",");
        for (Integer userId : userIdSet) {
            sysUserService.addRole(userId, RoleConstants.ROLE_OA_SHARE);
        }
    }
%>
</body>
</html>
