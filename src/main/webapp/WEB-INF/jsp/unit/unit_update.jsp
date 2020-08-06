<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unit_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${unit.id}">
        <input type="hidden" name="dispatchUnitId" value="${unit.dispatchUnitId}">
			<div class="form-group table-single-select">
				<table id="jqGrid_popup" class="table-striped"></table>
			</div>
				<div class="form-group">
				<label class="col-xs-3 control-label">成立时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input  class="form-control date-picker" name="workTime"
                               type="text" data-date-format="yyyy.mm.dd"
                               value="${cm:formatDate(unit.workTime, "yyyy.MM.dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
				</div>
			</div>
		<div class="form-group">
				<label class="col-xs-3 control-label">单位网址</label>
				<div class="col-xs-6">
                        <input class="form-control url"  type="text" name="url" value="${unit.url}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" name="remark">${unit.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <c:if test="${unit!=null}">确定</c:if><c:if test="${unit==null}">添加</c:if></button>
</div>
<jsp:include page="/WEB-INF/jsp/dispatch/dispatchUnit/dispatchUnit_colModel.jsp"/>
<script>
	var dispatchUnits = ${cm:toJSONArray(dispatchUnits)}
	var $jqGrid = $("#jqGrid_popup");
	var lastSel ='${unit.dispatchUnitId}' ;
    $jqGrid.jqGrid({
		caption:'<span style="font-size: 18px;font-weight: bolder"><i class="ace-icon fa fa-circle-o"></i> 请选择成立文件</span>',
		pager: null,
        rownumbers: true,
		multiselect: true,
        multiboxonly: true,
		beforeSelectRow:function(){
			$("#jqGrid_popup").jqGrid('resetSelection');
			return true;
		},
		onSelectRow: function (rowId, status, e) {
        	if (rowId == lastSel) {
            	$(this).jqGrid("resetSelection");
            	lastSel = undefined;
			} else {
				lastSel = rowId;
			}
        	var ids = $(this).getGridParam("selarrrow");
			if (ids.length == 1) {
			    $("#modalForm input[name=dispatchUnitId]").val(ids[0])
				var rowData = $(this).getRowData(ids[0]);
				//console.log(rowData['dispatch.pubTime'])
				if(rowData['dispatch.pubTime']!=undefined)
					$("#modalForm input[name=workTime]").val(rowData['dispatch.pubTime'])
			}else{
			   $("#modalForm input[name=dispatchUnitId]").val('')
            }
		},
		ondblClickRow:function(){},
        height: 200,
        width:1090,
        datatype: "local",
        rowNum: dispatchUnits.length,
        data: dispatchUnits,
        colModel: colModel,
        gridComplete:function(){
           $("#jqGrid_popup").jqGrid("setSelection", '${unit.dispatchUnitId}', false);
        }
    });

    $('textarea.limited').inputlimiter();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $.openView({url:"${ctx}/unit_base?id=${unit.id}&_="+new Date().getTime(),
                        $mask:$("#tab-content"), $show:$("#tab-content")})
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.date($('.date-picker'));
</script>