<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${recruitPost!=null}">编辑</c:if><c:if test="${recruitPost==null}">添加</c:if>岗位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/recruitPost_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${recruitPost.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">年度</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" placeholder="请选择年份" name="year"
							   type="text"
							   data-date-format="yyyy" data-date-min-view-mode="2" value="${_thisYear}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">招聘岗位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${recruitPost.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政级别</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="adminLevel" data-placeholder="请选择行政级别">
						<option></option>
						<jsp:include page="/metaTypes?__code=mc_admin_level"/>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=adminLevel]").val(${recruitPost.adminLevel});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属单位</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" required data-ajax-url="${ctx}/unit_selects?status=1"
							name="unitId" data-placeholder="请选择单位">
						<option value="${unit.id}">${unit.name}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">部门属性</label>
				<div class="col-xs-6">
					<input type="text" disabled name="unitType" value="${unit.unitType.name}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark">${recruitPost.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${recruitPost!=null}">确定</c:if><c:if test="${recruitPost==null}">添加</c:if>"/>
</div>

<script>
	register_date($('.date-picker'));
	$('textarea.limited').inputlimiter();
	var $selectUnit = register_ajax_select($('#modalForm select[name=unitId]'));
	$selectUnit.on("change",function(){
		var unitType = $(this).select2("data")[0]['type']||'';
		$('#modalForm input[name=unitType]').val(unitType);
	});

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>