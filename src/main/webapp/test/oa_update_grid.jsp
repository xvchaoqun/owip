<%@ page import="domain.oa.*" %>
<%@ page import="persistence.oa.OaGridPartyMapper" %>
<%@ page import="service.oa.OaGridPartyDataService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="service.SpringProps" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>重新上传党统个分党委上传的数据</title>
</head>
<body>
<%
    OaGridPartyMapper oaGridPartyMapper = CmTag.getBean(OaGridPartyMapper.class);
    OaGridPartyDataService oaGridPartyDataService  = CmTag.getBean(OaGridPartyDataService.class);
    SpringProps springProps = CmTag.getBean(SpringProps.class);

    OaGridPartyExample example = new OaGridPartyExample();
    example.createCriteria().andExcelFilePathIsNotNull();
    List<OaGridParty> oaGridPartyList = oaGridPartyMapper.selectByExample(example);

    for (OaGridParty oaGridParty : oaGridPartyList) {

        File file = new File(springProps.uploadPath + oaGridParty.getExcelFilePath());
        oaGridPartyDataService.importData(oaGridParty, file);
    }

%>
</body>
</html>
