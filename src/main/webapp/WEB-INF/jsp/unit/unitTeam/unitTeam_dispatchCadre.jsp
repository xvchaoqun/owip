<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑${param.auType==2?'任职信息':'免职信息'}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitTeam_dispatchCadre" id="modalForm" method="post">
        <input type="hidden" name="unitTeamId" value="${unitTeam.id}">
        <input type="hidden" name="auType" value="${param.auType}">

        <input type="hidden" name="dispatchCadreId" value="${param.auType==2?unitTeam.appointDispatchCadreId
        :unitTeam.deposeDispatchCadreId}">
			<div class="form-group table-single-select">
				<table id="jqGrid_popup" class="table-striped"></table>
				<table id="jqGridPager_popup" class="table-striped"></table>
			</div>
		<div class="form-group">
			<label class="col-xs-2 control-label" style="font-size: 18px; font-weight: bolder;">${param.auType==2?'任职时间':'免职时间'}</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 200px;margin-top: 6px;">
					<input required class="form-control date-picker" name="dispatchCadreDate"
						   type="text" data-date-format="yyyy-mm-dd"
						   value="${cm:formatDate(param.auType==2?unitTeam.appointDate:unitTeam.deposeDate, "yyyy-MM-dd")}"/>
					<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
				</div>
			</div>
		</div>
		<c:if test="${param.auType==2}">
		<div class="form-group">
            <label class="col-xs-2 control-label" style="font-size: 18px; font-weight: bolder;">应换届时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 200px;margin-top: 6px;">
                    <input class="form-control date-picker" name="expectDeposeDate" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(unitTeam.expectDeposeDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
		</c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <c:if test="${unit!=null}">确定</c:if><c:if test="${unit==null}">添加</c:if></button>
</div>
<script>
	var $jqGrid = $("#jqGrid_popup");
	var lastSel ='${param.auType==2?unitTeam.appointDispatchCadreId
        :unitTeam.deposeDispatchCadreId}' ;
    $jqGrid.jqGrid({
		caption:'<span style="font-size: 18px;font-weight: bolder"><i class="ace-icon fa fa-circle-o"></i> 请选择${param.auType==2?'任职文件':'免职文件'}</span>',
		pager: "#jqGridPager_popup",
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
			    $("#modalForm input[name=dispatchCadreId]").val(ids[0])
				var rowData = $(this).getRowData(ids[0]);
				//console.log(rowData['dispatch.workTime'])
				if(rowData['dispatch.workTime']!=undefined)
					$("#modalForm input[name=dispatchCadreDate]").val(rowData['dispatch.workTime'])
			}else{
			    console.log("gc clear")
			   $("#modalForm input[name=dispatchCadreId]").val('')
            }
		},
		ondblClickRow:function(){},
        height: 265,
        width:1090,
        url: '${ctx}/dispatchCadre_data?callback=?&pageSize=20&unitId=${unitTeam.unitId}',
        colModel: [ { label: '年份', name: 'dispatch.year', width: 75,frozen:true },
            { label:'发文号',  name: 'dispatch.dispatchCode', width: 140, align:'left',formatter:function(cellvalue, options, rowObject){

                return $.swfPreview(rowObject.dispatch.file, rowObject.dispatch.fileName,
                    cellvalue, cellvalue, 'url');
            },frozen:true },
            { label: '任免日期',  name: 'dispatch.workTime',frozen:true , formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label:'类别', name: 'type', width: 50, formatter:function(cellvalue, options, rowObject){
                return _cMap.DISPATCH_CADRE_TYPE_MAP[cellvalue];
            },frozen:true },
            { label:'干部类型', name: 'cadreTypeId', width: 80, formatter: $.jgrid.formatter.MetaType},
            { label:'工作证号', name: 'user.code'},
            { label:'姓名', name: 'user.realname', width: 90, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cadre==undefined) return ''
                return $.cadre(rowObject.cadre.id, cellvalue, '_blank');
            }},
            { label:'任免职务', name: 'post', width: 150, cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if(rowObject.unitPostId==undefined)
                        return "class='warning'";
                }, align:'left'},
            { label:'职务属性', name: 'postId', width: 120, align:'left', formatter: $.jgrid.formatter.MetaType},
            { label:'行政级别', name: 'adminLevelId', formatter: $.jgrid.formatter.MetaType}],
        gridComplete:function(){
            //console.log("gc:" +  $("#modalForm input[name=dispatchCadreId]").val())
           $("#jqGrid_popup").jqGrid("setSelection", $("#modalForm input[name=dispatchCadreId]").val(), false);
        }
    });
	$.initNavGrid("jqGrid_popup", "jqGridPager_popup");
    $('textarea.limited').inputlimiter();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                       $("#jqGrid2").trigger("reloadGrid");
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