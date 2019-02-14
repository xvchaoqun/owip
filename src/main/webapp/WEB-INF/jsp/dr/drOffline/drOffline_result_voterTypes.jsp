<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${fn:length(typeMap)>0}">
    <table class="table table-striped table-bordered
        table-condensed table-center table-unhover2">
        <thead>
        <tr>
            <c:forEach items="${typeMap}" var="entity">
                <th>${entity.value.name}</th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <tr>
            <c:forEach items="${typeMap}" var="entity">
            <td class="bg-center">
                <input required class="form-control digits" maxlength="4"
                       data-at="bottom center" data-my="top center"
                       style="width: 50px;margin: 0 auto;" value="${voterMap.get(entity.key)}"
                       type="text" name="type_${entity.key}">
            </td>
            </c:forEach>
        </tr>
        </tbody>
    </table>
</c:if>