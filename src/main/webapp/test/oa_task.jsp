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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新任务相关统计数量</title>
</head>
<body>
<%

    OaTaskMapper oaTaskMapper = CmTag.getBean(OaTaskMapper.class);
    IOaTaskMapper iOaTaskMapper = CmTag.getBean(IOaTaskMapper.class);

    List<OaTask> oaTasks = oaTaskMapper.selectByExample(new OaTaskExample());

    for (OaTask oaTask : oaTasks) {
        iOaTaskMapper.refreshCount(oaTask.getId());
    }

%>
</body>
</html>
