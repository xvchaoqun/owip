<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党员转移至历史党员库</h3>
</div>
<div class="modal-body overflow-visible">
    <form class="form-horizontal" action="${ctx}/transferToHistory" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids" value="${param.ids}">
		<c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>
		<c:if test="${count>=1}">
			<div class="form-group">
				<label class="col-xs-4 control-label">转移党员数</label>

				<div class="col-xs-6 label-text">
						${count} 个
				</div>
			</div>
		</c:if>
		<div class="form-group ">
			<label class="col-xs-4 control-label">标签</label>
			<div class="col-xs-6 input-group" style="padding-left: 12px">
				<select class="multiselect" multiple="" name="lable" data-width="252">
					<c:import url="/metaTypes?__code=mc_mh_lable"/>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label">转至历史党员库详细原因</label>
			<div class="col-xs-6">
						<textarea style="width: 252px" class="form-control limited noEnter" type="text" maxlength="100"
								  name="detailReason"></textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-info"><i class="fa fa-random"></i> 转移</button>
</div>
<script>
	$('textarea.limited').inputlimiter();

	$.register.multiselect($('#modalForm select[name=lable]'), {
		enableClickableOptGroups: true,
		enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
	});

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
	$('textarea.limited').inputlimiter();
    //$("#modalForm :checkbox").bootstrapSwitch();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'), {endDate: '${_today}'});
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>