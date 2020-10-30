<%@ page import="domain.oa.*" %>
<%@ page import="persistence.oa.OaGridPartyMapper" %>
<%@ page import="service.oa.OaGridPartyDataService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="service.SpringProps" %>
<%@ page import="org.springframework.util.StringUtils" %>
<%@ page import="controller.global.OpException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>重新上传党统个分党委上传的数据</title>
</head>
<body>
<%

    OaGridPartyMapper oaGridPartyMapper = CmTag.getBean(OaGridPartyMapper.class);
    OaGridPartyDataService oaGridPartyDataService = CmTag.getBean(OaGridPartyDataService.class);
    SpringProps springProps = CmTag.getBean(SpringProps.class);

    Integer id = null;//分党委id
    try {
        id = Integer.valueOf(request.getParameter("id"));
    }catch (Exception e){
        throw new OpException("参数异常");
    }

    if (id == null) {

        OaGridPartyExample example = new OaGridPartyExample();
        example.createCriteria().andExcelFilePathIsNotNull();
        List<OaGridParty> oaGridPartyList = oaGridPartyMapper.selectByExample(example);

        for (OaGridParty oaGridParty : oaGridPartyList) {

            File file = new File(springProps.uploadPath + oaGridParty.getExcelFilePath());
            oaGridPartyDataService.importData(oaGridParty, file);
        }
    }else {
        OaGridParty oaGridParty = oaGridPartyMapper.selectByPrimaryKey(id);
        if (oaGridParty != null && oaGridParty.getExcelFilePath() != null){
            File file = new File(springProps.uploadPath + oaGridParty.getExcelFilePath());
            oaGridPartyDataService.importData(oaGridParty, file);
        }

    }

%>
</body>
</html>
