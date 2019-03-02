<%@ tag description="单位档案跳转链接" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ attribute name="unit" type="domain.unit.Unit" required="true" %>
<shiro:hasPermission name="unit:view">
    <a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
        <span class="${unit.status==UNIT_STATUS_HISTORY?'delete':''}">${unit.name}</span>
    </a>
</shiro:hasPermission>
<shiro:lacksPermission name="unit:view">
    <span class="${unit.status==UNIT_STATUS_HISTORY?'delete':''}">${unit.name}</span>
</shiro:lacksPermission>