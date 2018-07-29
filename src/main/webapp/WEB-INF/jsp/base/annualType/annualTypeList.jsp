<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <c:forEach items="${param.cls}" var="module">
            <c:import url="/annualTypeList_item">
                <c:param name="module" value="${module}"/>
                <c:param name="pageSize" value="15"/>
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
<script>
    function _reload(btn) {
        var module = $(btn).data("module");
        _reloadDiv(module);
    }
    function _reloadDiv(module) {
        var $div = $('div[data-module="'+module+'"]');
        var querystr = $div.data("querystr");
        $.get("${ctx}/annualTypeList_item?module="+module+"&"+querystr, function(html){
            $('div[data-module="'+module+'"]').replaceWith(html);
        });
    }
</script>