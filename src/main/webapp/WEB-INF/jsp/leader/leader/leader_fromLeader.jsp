<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CmConstants.CM_MEMBER_TYPE_DW%>" var="CM_MEMBER_TYPE_DW"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3 style="margin: 0">从校领导中提取</h3>
</div>
<div class="modal-body popup-jqgrid">
    <table id="jqGrid_popup" class="table-striped"></table>
    <div id="jqGridPager_popup"></div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="selectBtn" class="btn btn-primary"
           data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">添加</button>
</div>

<script>
    $("#jqGrid_popup").jqGrid({
        height: 390,
        width: 700,
        rowNum: 100,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/cadre_data?callback=?&status=${CADRE_STATUS_LEADER}&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: [
            {label: '姓名', name: 'realname', width: 80},
            {label: '工作证号', name: 'code', width: 110},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType}
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    $("#selectBtn").click(function () {

        var cadreIds = $("#jqGrid_popup").getGridParam("selarrrow");
        //console.log(courseIds)
        if(cadreIds.length==0){
            SysMsg.info("请选择校领导。");
            return;
        }
        var $btn = $("#selectBtn").button('loading');
        $.post("${ctx}/leader_fromLeader", {cadreIds:cadreIds},function(ret){
            if(ret.success){
                $("#modal").modal('hide');
                $("#jqGrid").trigger("reloadGrid");
            }
            $btn.button('reset');
        });
    });
</script>