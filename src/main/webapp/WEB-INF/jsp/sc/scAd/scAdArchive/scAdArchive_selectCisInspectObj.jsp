<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>选择干部考察材料</h3>
</div>
<div class="modal-body rownumbers">
    <table id="jqGridPopup" class="table-striped"></table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="selectBtn" class="btn btn-primary" value="生成"/>
</div>
<style>
    .rownumbers #jqGridPopup_cb {
        text-align: left !important;
    }
</style>
<script>
    var cisInspectObjs = ${cm:toJSONArray(cisInspectObjs)};
    $("#jqGridPopup").jqGrid({
        pager: null,
        rownumbers: true,
        multiboxonly: false,
        height: 400,
        width:770,
        datatype: "local",
        rowNum: cisInspectObjs.length,
        data: cisInspectObjs,
        colModel: [
            {
                label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {

                return ('<a href="javascript:;" class="linkBtn"'
                +'data-url="${ctx}#/cisInspectObj?typeId={0}&year={1}&seq={2}"'
                +'data-target="_blank">{3}</a>')
                        .format(rowObject.typeId, rowObject.year, rowObject.seq, rowObject.sn)

            }, width: 180, frozen: true
            },
            {label: '考察日期', name: 'inspectDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            {label: '工作证号', name: 'cadre.code', frozen: true},
            {label: '考察对象', name: 'cadre.realname', frozen: true},
            {label: '所在单位及职务', name: 'post', align: 'left', width: 300},
            {label: '拟任职务', name: 'assignPost', align: 'left', width: 300},
            {hidden: true, key: true, name: 'id'}
        ],
        gridComplete:function(){
            <c:if test="${selectedObjId>0}">
            $("#jqGridPopup").jqGrid("setSelection", ${selectedObjId});
            </c:if>
        }
    });

    $("#modal #selectBtn").click(function(){

        var ids = $("#jqGridPopup").getGridParam("selarrrow");
        if(ids.length==0 || ids.length>1){
            $.tip({
                $target: $(this),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择一条考察信息。"
            });
            return;
        }

        $.openView("${ctx}/sc/scAdArchive_cisPreview?archiveId=${param.archiveId}&objId=" + ids[0]);
    });
</script>