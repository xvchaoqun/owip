<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${module==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unitPostAllocation?module=1&cadreType=${cadreType}"><i class="fa fa-table"></i> 内设机构${CADRE_TYPE_MAP.get(cadreType)}配备详情</a>
    </li>
    <li class="<c:if test="${module==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unitPostAllocation?module=2&cadreType=${cadreType}"><i class="fa fa-bar-chart"></i> 内设机构${CADRE_TYPE_MAP.get(cadreType)}配备统计</a>
    </li>
    <c:if test="${_p_hasKjCadre}">
    <div class="input-group pull-right" style="right: 60px;padding-top: 6px">
        <c:forEach items="${CADRE_TYPE_MAP}" var="entity">
            <div class="checkbox checkbox-inline checkbox-sm checkbox-success checkbox-circle">
                <input required type="radio" name="cadreType" id="cadreType${entity.key}"
                    ${cadreType==entity.key?"checked":""} value="${entity.key}">
                <label for="cadreType${entity.key}">
                        ${entity.value}
                </label>
            </div>
        </c:forEach>
    </div>
</c:if>
</ul>
<script>
    $("input[name=cadreType]").click(function(){
        var cadreType = $(this).val();
        $.loadPage({url:"${ctx}/unitPostAllocation?module=${module}&cadreType="+cadreType})
    })
</script>