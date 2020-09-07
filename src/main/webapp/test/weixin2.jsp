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
<%@ page import="java.io.IOException" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="sys.entity.SendMsgResult" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.google.gson.JsonParser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>wechat</title>
</head>
<%!
    public static String GetResponse(String Info) throws IOException {
        String path = "https://weixin.bnu.edu.cn/api/sendmsg.php?id=2";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestMethod("POST");

        conn.setDoOutput(true);
        conn.setDoInput(true);
        OutputStream os = conn.getOutputStream();
        os.write(Info.getBytes("utf-8"));
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        StringBuffer strBuffer = new StringBuffer("");
        String strLine = null;
        while ((strLine = br.readLine()) != null) {

            strBuffer.append(strLine);
        }
        String str = new String(strBuffer);
        return str;
    }

    public static String encode_base64(String sData) {

        String sBase64 = Base64.getEncoder().encodeToString(sData.getBytes(StandardCharsets.UTF_8));
        return sBase64.replace("+/", "-_");
    }

%>
<%
    String _codes = request.getParameter("codes");
    String content = request.getParameter("content");
    if (StringUtils.isNotBlank(_codes) && StringUtils.isNotBlank(content)) {
        String[] codes = _codes.split(",");


        SendMsgResult sendMsgResult = new SendMsgResult();

        String str = "{" +
                "\"username\"" + ":\"" + StringUtils.join(codes, "|") + "\"," +
                "\"content\"" + ":\"" + content + "\"" +
                "}";

        //str = encode_base64(str);
        str = GetResponse(str);

        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        JsonElement errcode = jsonObject.get("errcode");

        sendMsgResult.setSuccess((errcode.getAsInt() == 0));
        sendMsgResult.setMsg(str);

        out.write(JSONUtils.toString(sendMsgResult, false));
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
