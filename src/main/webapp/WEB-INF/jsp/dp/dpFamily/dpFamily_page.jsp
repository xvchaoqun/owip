<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/dp/dpColModels.jsp"/>

<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="dpFamily:edit">
        <a class="popupBtn btn btn-success btn-sm" data-width="800"
           data-url="${ctx}/dp/dpFamily_au?userId=${param.userId}"><i class="fa fa-plus"></i>
            添加</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/dp/dpFamily_au"  data-width="800"
           data-grid-id="#jqGrid2"
           data-querystr="&userId=${param.userId}"><i class="fa fa-edit"></i>
            修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="dpFamily:del">
        <a data-url="${ctx}/dp/dpFamily_batchDel"
           data-title="删除"
           data-msg="确定删除这{0}条数据？<div class='bolder text-danger'></div>"
           data-grid-id="#jqGrid2"
           data-querystr="userId=${param.userId}"
           class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </a>
    </shiro:hasPermission>
</div>

<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2"  data-height-reduce="30" ></table>
<div id="jqGridPager_dpFamily"></div></div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager_dpFamily",
        url: '${ctx}/dp/dpFamily_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: dpColModels.dpFamily
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
</script>