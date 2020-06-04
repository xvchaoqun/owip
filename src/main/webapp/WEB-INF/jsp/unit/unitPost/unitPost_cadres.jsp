<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DISPATCH_CADRE_TYPE_APPOINT" value="<%=DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${not empty unitPostGroup?unitPostGroup.name:unitPost.name}（历史任免干部信息）</h3>
</div>
<div class="modal-body popup-jqgrid">
    <form class="form-inline search-form" id="searchForm_popup">
        <input type="hidden" name="unitPostId" value="${param.unitPostId}">
        <div class="form-group">
            <label>姓名</label>
            <select data-rel="select2-ajax"
                    data-ajax-url="${ctx}/cadre_selects"
                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
            </select>
        </div>
        <div class="form-group">
            <label>类型</label>
            <select data-rel="select2" data-placeholder="请选择" data-width="80"
                    name="type">
                <option></option>
                <c:forEach items="<%=DispatchConstants.DISPATCH_CADRE_TYPE_MAP%>"
                           var="_type">
                    <option value="${_type.key}">${_type.value}</option>
                </c:forEach>
                <option value="-1">全部</option>
            </select>
            <script>
                $("#searchForm_popup select[name=type]").val('${type}');
            </script>
        </div>
        <c:if test="${not empty param.groupId}">
            <input type="hidden" name="groupId" value="${param.groupId}">
            <input type="hidden" name="displayType" value="0">
        </c:if>
        <c:if test="${empty param.groupId}">
        <div class="form-group">
            <label>显示范围</label>
            <select data-rel="select2" data-placeholder="请选择" data-width="130"
                    name="displayType">
                <c:if test="${unitPost.groupId>0}">
                <option value="0">按岗位分组</option>
                </c:if>
                <option value="1">按岗位名称</option>
                <option value="2">当前岗位</option>

            </select>
            <script>
                $("#searchForm_popup select[name=displayType]").val('${displayType}');
            </script>
        </div>
        </c:if>
        <c:set var="_query" value="${not empty param.cadreId || (not empty param.type && cm:toByte(param.type) != DISPATCH_CADRE_TYPE_APPOINT)}"/>
        <div  class="form-group">
            <button type="button" data-url="${ctx}/unitPost_cadres"
                    data-target="#modal .modal-content" data-form="#searchForm_popup"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/unitPost_cadres"
                        data-querystr="unitPostId=${param.unitPostId}&groupId=${param.groupId}"
                        data-target="#modal .modal-content"
                        class="reloadBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>

            <button type="button" class="downloadBtn btn btn-info btn-sm tooltip-success"
                    data-grid-id="#jqGrid_popup"
                    data-url="${ctx}/dispatchCadre_data?unitPostIds=${param.unitPostId}&type=${type}&displayType=${displayType}&asc=1&export=2"
               data-rel="tooltip" data-placement="top" title="导出该岗位历史任职信息">
                <i class="fa fa-download"></i> 导出</button>
        </div>
    </form>
    <table id="jqGrid_popup" class="table-striped"> </table>
    <div id="jqGridPager_popup"> </div>
</div>
<jsp:include page="/WEB-INF/jsp/dispatch/dispatchCadre/dispatchCadre_columns.jsp"/>
<script>
    $("#jqGrid_popup").jqGrid({
        multiselect:false,
        height:390,
        width:965,
        rowNum:10,
        ondblClickRow:function(){},
        pager:"jqGridPager_popup",
        url: "${ctx}/dispatchCadre_data?callback=?&unitPostIds=${param.unitPostId}&type=${type}&displayType=${displayType}&asc=1&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel:colModel
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
    $('#searchForm_popup select[name=type]').select2({allowClear:false});
    $('#searchForm_popup select[name=displayType]').select2({allowClear:false});
    $.register.user_select($('#searchForm_popup select[name=cadreId]'));
</script>