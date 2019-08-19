<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <c:forEach items="${param.cls}" var="code">
            <c:import url="/htmlFragment_list_item">
                <c:param name="code" value="${code}"/>
            </c:import>
        </c:forEach>
    </div>
</div>
<style>
    .table tr th, .table tr td {
        white-space: nowrap;
        text-align: center;
    }
</style>

<%--
<script>

    function _reload(btn) {
        var cls = $(btn).data("cls");
        $.get("${ctx}/metaClass_type_list_item",{code:cls},function(html){
            $('div[data-cls="'+cls+'"]').replaceWith(html);
        });
    }
</script>--%>
