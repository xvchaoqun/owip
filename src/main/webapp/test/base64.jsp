<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="sys.security.Base64Utils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
<script src="/assets/js/jquery.js"></script>
<script src="/extend/js/jquery.base64.js"></script>
</head>
<body>

<%

    String str = request.getParameter("str");
    if(StringUtils.isNotBlank(str)){
       out.write(Base64Utils.encode(str));
    }


%>
<div>
    <form method="post">
        <textarea id="str" name="str" rows="8" style="width: 1000px"></textarea>
        <br/>
        <button type="submit">base64Encode</button>
    </form>

<%

    String destr1 = request.getParameter("destr1");
    if(StringUtils.isNotBlank(destr1)){
       out.write(Base64Utils.decodeStr(destr1));
    }


%>
    <form method="post">
        <textarea id="destr1" name="destr1" rows="8" style="width: 1000px"></textarea><br/>
        <button type="submit">base64Decode1</button>
    </form>
<%

    String destr2 = request.getParameter("destr2");
    if(StringUtils.isNotBlank(destr2)){
       out.write(Base64Utils.decodeStr(destr2));
    }

%>
    <form method="post">
        <textarea id="destr2" name="destr2" rows="8"  style="width: 1000px"></textarea><br/>
        <button type="submit">base64Decode1</button>
    </form>
</div>
<script>
    $.base64.utf8encode = true;
  /*  var str = "1234";
    $("#base1").html(new Base64().encode(str));
    $("#base2").html($.base64.encode(str));
*/
    var items = [{"userId":8710,"content":"十八大以来，曾收到信访举报，正在调查。"},{"userId":8669,"content":"未收到信访举报，未见违规违纪问题。"},{"userId":8667,"content":"未收到信访举报，未见违规违纪问题。"}];
    var str = JSON.stringify(items);
    console.log("str="+str)
    $("#str").val(str)


    $("#destr2").val($.base64.encode(str))

    function _encode(){

        $.post()
    }
</script>
</body>
</html>
