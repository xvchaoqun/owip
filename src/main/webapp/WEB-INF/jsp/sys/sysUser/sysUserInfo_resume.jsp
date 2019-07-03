<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="font-size: xx-large; color: darkblue">
    ${ui.realname}简历：
</div>
<br/>
<br/>
<div>
<pre style="font-size: xx-large">${fn:trim(ui.resume)}</pre>
</div>