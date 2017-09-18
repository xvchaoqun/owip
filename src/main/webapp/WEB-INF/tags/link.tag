<%@ tag description="带最后修改时间的CSS文件引用" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="href" type="java.lang.String" required="true" %>
<link rel="stylesheet" href="${ctx}${href}?_=${cm:lastModified(cm:getAbsolutePath(href))}"/>