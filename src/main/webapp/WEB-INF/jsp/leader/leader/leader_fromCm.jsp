<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CmConstants.CM_MEMBER_TYPE_CW%>" var="CM_MEMBER_TYPE_CW"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3 style="margin: 0">从党委常委中提取</h3>
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
        width: 770,
        rowNum: 100,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/cmMember_data?callback=?&type=${CM_MEMBER_TYPE_CW}&isQuit=0&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: [
            {label: '届数', name: 'pcsName', width: 160, frozen:true},
            {label: '职务', name: 'post', width: 120, formatter: $.jgrid.formatter.MetaType, frozen:true},
            {label: '姓名', name: 'realname', width: 80},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 370},
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    $("#selectBtn").click(function () {

        var memberIds = $("#jqGrid_popup").getGridParam("selarrrow");
        //console.log(courseIds)
        if(memberIds.length==0){
            SysMsg.info("请选择党委常委。");
            return;
        }
        var $btn = $("#selectBtn").button('loading');
        $.post("${ctx}/leader_fromCm", {memberIds:memberIds},function(ret){
            if(ret.success){
                $("#modal").modal('hide');
                $("#jqGrid").trigger("reloadGrid");
            }
            $btn.button('reset');
        });
    });
</script>