<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>撤销党支部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/branch_batchDel" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids" value="${param.ids}">
		<input type="hidden" name="isDeleted" value="1"/>
		<c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>
		<c:if test="${count>1}">
			<div class="form-group">
				<label class="col-xs-4 control-label"> 已选党支部个数</label>
				<div class="col-xs-6 label-text">
						${count} 个
				</div>
			</div>
		</c:if>
		<c:if test="${count==1}">
			<div class="form-group">
				<label class="col-xs-4 control-label">党支部名称</label>
				<div class="col-xs-6 label-text">
						${branch.name}
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 撤销时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 150px">
					<input required class="form-control date-picker" name="abolishTime" type="text"
						   data-date-format="yyyy.mm.dd" value="${_today_dot}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
	<div class="note">注：撤销操作将同时删除其下的支部委员会及相关管理员权限，请谨慎操作！</div>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
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
                        //$("#jqGrid").trigger("reloadGrid");
                        updateCache();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'), {endDate: '${_today}'});
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>