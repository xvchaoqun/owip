<%@ tag description="数据脱敏" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="src" type="java.lang.String" required="true" %>
<%@ attribute name="type" type="java.lang.String" required="true" %>
<span class="mask" data-src="${cm:base64Encode(src)}">${cm:mask(src, type)}</span>