<%@ page import="jixiantech.api.msg.OneSendResult" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.springframework.core.io.Resource" %>
<%@ page import="org.springframework.core.io.UrlResource" %>
<%@ page import="org.codehaus.xfire.client.Client" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="sys.utils.JSONUtils" %>
<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="sys.gson.GsonUtils" %>
<%@ page import="com.google.gson.JsonElement" %>
<%@ page import="org.apache.commons.lang3.BooleanUtils" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>wechat</title>
</head>
<%
    String _codes = request.getParameter("codes");
    String content = request.getParameter("content");
    if(StringUtils.isNotBlank(_codes) && StringUtils.isNotBlank(content)) {
        String[] codes = _codes.split(",");


        ObjectMapper mapper = new ObjectMapper();
        String sendWechatUrl = "http://onesend.bnu.edu.cn/tp_mp/service/WechatService?wsdl";

        OneSendResult oneSendResult = new OneSendResult();
        //if (codes == null || codes.length == 0 || StringUtils.isBlank(content)) return oneSendResult;
        oneSendResult.setType("微信");

        Resource resource = new UrlResource(sendWechatUrl);
        Client client = new Client(resource.getInputStream(), null);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("person_info", StringUtils.join(codes, "^@^"));
        map.put("tp_name", "onesendbnuwebservicesimple");
        map.put("wechat_info", content);

        String json = mapper.writeValueAsString(map);
        Object[] results = client.invoke("saveWechatInfo", new Object[]{json});
        System.out.println(JSONUtils.toString(results, false));
        client.close();

        oneSendResult.setRet((String) results[0]);

        // {"result":true,"msg_id":"13006982860800","msg":"发布成功"}
        JsonObject jsonObject = GsonUtils.toJsonObject(oneSendResult.getRet());
        JsonElement result = jsonObject.get("result");

        oneSendResult.setSuccess(BooleanUtils.isTrue(result.getAsBoolean()));

        out.write(JSONUtils.toString(oneSendResult, false));
    }
%>
<body>
<form method="post">
    codes:<input type="text" name="codes">
    <br/>
    <br/>
    content:<textarea name="content" rows="10"></textarea>
    <br/>
    <input type="submit" value="发送">
</form>
</body>
</html>
