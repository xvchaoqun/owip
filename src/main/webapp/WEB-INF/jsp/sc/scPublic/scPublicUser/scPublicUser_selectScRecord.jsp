<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>对应的选任纪实</h3>
</div>
<div class="modal-body">
<table id="jqGrid_popup" class="jqGrid table-striped"></table>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" onclick="_selectScRecord()" class="btn btn-primary">
        确定
    </button>
</div>
<script>
    function _selectScRecord(){

        var ids = $("#jqGrid_popup").getGridParam("selarrrow");
        if(ids.length>1){
            $.tip({
                $target: $("#modal #submitBtn"),
                at: 'left center', my: 'right center', type: 'info',
                msg: "请选择一条纪实记录"
            });
            return;
        }
        var recordId = (ids.length==1)?ids[0]:'';
        $.post("${ctx}/sc/scPublicUser_selectScRecord",{id:'${param.id}', recordId:recordId},function(ret){
            if(ret.success){
                 $("#jqGrid2").trigger("reloadGrid");
                 $("#modal").modal('hide');
            }
        })
    }

    $("#jqGrid_popup").jqGrid({
        pager:null,
        rownumbers: true,
        loadui:'disabled',
        width:1020,
        height:270,
        url: '${ctx}/sc/scRecords?userId=${scPublicUser.cadre.userId}&year=${scPublicUser.year}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen: true},
            { label:'工作证号', name: '_code',formatter: function (){return '${scPublicUser.cadre.code}'}},
            { label:'考察对象', name: '_realname',formatter: function (){return '${scPublicUser.cadre.realname}'}},
            {label: '所属纪实', name: 'code', width: 200, frozen: true},
            {label: '选任岗位', name: 'postName', align: 'left', width: 200, frozen: true,
                formatter: function (cellvalue, options, rowObject){
                return '[{0}]{1}'.format(rowObject.postCode, rowObject.postName)
            }},
            {label: '分管工作', align: 'left', name: 'job', width: 240},
            {label: '选任启动日期', name: 'holdDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}}
        ],
        gridComplete:function(){
            $(this).jqGrid("setSelection", '${scPublicUser.recordId}', true);
        }
    });
</script>