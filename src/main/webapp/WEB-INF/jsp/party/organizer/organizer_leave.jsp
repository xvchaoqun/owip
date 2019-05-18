<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_ORGANIZER_TYPE_MAP" value="<%=OwConstants.OW_ORGANIZER_TYPE_MAP%>"/>
<c:set var="OW_ORGANIZER_STATUS_MAP" value="<%=OwConstants.OW_ORGANIZER_STATUS_MAP%>"/>
<c:set var="OW_ORGANIZER_STATUS_NOW" value="<%=OwConstants.OW_ORGANIZER_STATUS_NOW%>"/>
<c:set var="OW_ORGANIZER_STATUS_LEAVE" value="<%=OwConstants.OW_ORGANIZER_STATUS_LEAVE%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${OW_ORGANIZER_TYPE_MAP.get(type)}${param.status==OW_ORGANIZER_STATUS_NOW?'重新任用':'离任'}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/organizer_leave" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${organizer.id}">
        <input type="hidden" name="status" value="${param.status}">

			<div class="form-group">
				<label class="col-xs-4 control-label">姓名</label>
				<div class="col-xs-6 label-text">
                     ${sysUser.realname}
				</div>
			</div>
			<c:if test="${param.status==OW_ORGANIZER_STATUS_LEAVE}">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span> 离任时间</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
						<input required class="form-control" name="dismissDate" type="text" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${param.status==OW_ORGANIZER_STATUS_NOW}">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span> 任职时间</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
						<input required class="form-control" name="appointDate" type="text" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			</c:if>
    </form>
</div>
<div class="modal-footer">
	<c:if test="${param.status==OW_ORGANIZER_STATUS_LEAVE}">
    <div class="note">
            注：确认后，该组织员将出现在离任库中，并且同步至历史任职情况库。
    </div>
	</c:if>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty organizer?'确定':'添加'}</button>
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
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.input-group.date'));
</script>