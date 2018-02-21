<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>生成干部任免审批表</h3>
</div>
<div class="modal-body rownumbers">
    党委常委会
    <select data-rel="select2-ajax"
            data-ajax-url="${ctx}/sc/scCommittee_selects"
            data-width="260" name="committeeId"
            data-placeholder="请选择或输入日期(YYYYMMDD)">
        <option></option>
    </select>

    <div class="space-4"></div>
    <table id="jqGridPopup" class="table-striped"></table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="selectBtn" class="btn btn-primary" value="添加"/>
</div>
<style>
    .rownumbers #jqGridPopup_cb {
        text-align: left !important;
    }
</style>
<script>

    var $gridLoading = $("#jqGridPopup").closest(".ui-jqgrid").find('.loading');
    var $selectCommittee = register_ajax_select($('#modal select[name=committeeId]'), {maximumInputLength: 8})
    $selectCommittee.on("change", function () {
        //var unitType = $(this).select2("data")[0]['type']||'';
        var committeeId = $(this).val();
        //console.log(committeeId)
        $("#jqGridPopup").jqGrid("clearGridData");
        $gridLoading.show();
        $.getJSON("${ctx}/sc/scCommittee_selectCadres", {committeeId: committeeId}, function (data) {
            for (var i = 0; i < data.length; i++) {
                var rowData = data[i];
                $("#jqGridPopup").jqGrid('addRowData', rowData.id, rowData);
            }
            $gridLoading.hide();
        })
    });

    $("#jqGridPopup").jqGrid({
        pager: null,
        rownumbers: true,
        multiboxonly: false,
        height: 400,
        width: 765,
        datatype: "local",
        rowNum: 0,
        data: [],
        colModel: [
            {label: '工作证号', name: 'code'},
            {label: '姓名', name: 'realname'},
            {label: '所在单位及职务', name: 'title', width: 240, align: 'left'}
        ]
    });


    $("#modal #selectBtn").click(function () {

        var committeeId = $selectCommittee.val();
        var cadreIds = $("#jqGridPopup").getGridParam("selarrrow");
        if ($.trim(committeeId) == '' || cadreIds.length == 0) return;

        $.post("${ctx}/sc/scAdArchive_au", {committeeId: committeeId, cadreIds: cadreIds}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                $("#jqGrid").trigger("reloadGrid");
            }
        })
    });
</script>