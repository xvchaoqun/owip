<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="userInfo">
    <div class="profile-user-info profile-user-info-striped">
        <c:forEach items="${columnBeanMap}" var="cbEntity">
                <div class="profile-info-row">
                    <div class="profile-info-name"> ${cbEntity.value.comments}</div>

                    <div class="profile-info-value">
                        <span class="editable">${valuesMap.get(cbEntity.key)}</span>
                    </div>
                </div>
        </c:forEach>
    </div>
</div>