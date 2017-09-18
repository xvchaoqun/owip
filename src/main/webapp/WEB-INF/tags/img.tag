<%@ tag description="带最后修改时间的图片文件引用" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="src" type="java.lang.String" required="true" %>
<img src="${ctx}${src}?_=${cm:lastModified(cm:getAbsolutePath(src))}" />
