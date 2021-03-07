<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${module==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unitPostAllocation?module=1&cadreCategory=${cadreCategory}"><i class="fa fa-table"></i> 内设机构${CADRE_CATEGORY_MAP.get(cadreCategory)}配备详情</a>
    </li>
    <li class="<c:if test="${module==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unitPostAllocation?module=2&cadreCategory=${cadreCategory}"><i class="fa fa-bar-chart"></i> 内设机构${CADRE_CATEGORY_MAP.get(cadreCategory)}配备统计</a>
    </li>
    <c:if test="${_p_hasKjCadre}">
        <shiro:lacksPermission name="hide:kj">
    <div class="input-group pull-left" style="left: 60px;padding-top: 6px">
        <c:forEach items="${CADRE_CATEGORY_MAP}" var="entity">
            <div class="checkbox checkbox-inline checkbox-sm checkbox-success checkbox-circle">
                <input required type="radio" name="cadreCategory" id="cadreType${entity.key}"
                    ${cadreCategory==entity.key?"checked":""} value="${entity.key}">
                <label for="cadreType${entity.key}">
                        ${entity.value}
                </label>
            </div>
        </c:forEach>
    </div>
        </shiro:lacksPermission>
</c:if>
</ul>
<script>
    $("input[name=cadreCategory]").click(function(){
        var cadreCategory = $(this).val();
        $.loadPage({url:"${ctx}/unitPostAllocation?module=${module}&cadreCategory="+cadreCategory})
    })
</script>