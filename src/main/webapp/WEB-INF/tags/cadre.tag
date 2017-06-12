<%@ tag description="干部档案跳转链接" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="cadreId" type="java.lang.Integer" required="true" %>
<%@ attribute name="realname" type="java.lang.String" required="true" %>
<a href="javascript:" class="openView" data-url="${ctx}/cadre_view?cadreId=${cadreId}">
    ${realname}
</a>