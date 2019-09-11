<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cgUnit!=null?'编辑':'添加'}挂靠单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cg/cgUnit_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cgUnit.id}">
		<input type="hidden" name="teamId" value="${teamId}">
		<input type="hidden" name="isCurrent" value="${isCurrent}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 单位</label>
			<div class="col-xs-6">
				<select required data-rel="select2-ajax" data-width="270"
						data-ajax-url="${ctx}/unit_selects"
						name="unitId" data-placeholder="请选择单位">
					<option value="${cgUnit.unitId}">${unit.name}</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 确定时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 200px">
					<input class="form-control date-picker" name="confirmDate" type="text"
					   data-date-format="yyyy.mm.dd"
					   value="${cm:formatDate(cgUnit.confirmDate,'yyyy.MM.dd')}"/>
					<span class="input-group-addon">
						<i class="fa fa-calendar bigger-110"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="remark" value="${cgUnit.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cgUnit?'确定':'添加'}</button>
</div>
<script>
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
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
</script>