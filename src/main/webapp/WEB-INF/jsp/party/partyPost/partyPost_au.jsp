<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3><c:if test="${partyReward!=null}">修改</c:if><c:if test="${partyReward==null}">添加</c:if>党内任职经历</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/party/partyPost_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${partyPost.id}">
		<c:if test="${list!=1}">
			<input type="hidden" name="userId" value="${user.id}">
		</c:if>
		<c:if test="${list==1}">
		<div class="form-group">
			<c:if test="${user!=null}">
				<label class="col-xs-3 control-label">党员姓名</label>
				<div class="col-xs-6 label-text">

						${user.realname}
				</div>
			</c:if>
			<c:if test="${user==null}">
				<label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
				<div class="col-xs-6">
					<select required class="form-control" data-rel="select2-ajax"
							data-ajax-url="${ctx}/member_selects"
							name="userId" data-width="270"
							data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</c:if>
		</div>
		</c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 开始日期</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy.mm.dd">
						<input required class="form-control date-picker" name="startDate" type="text" data-width="270"
							   placeholder="格式：yyyy.mm.dd"
							   value="${cm:formatDate(partyPost.startDate,'yyyy.MM.dd')}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束日期</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy.mm.dd">
						<input class="form-control date-picker" name="endDate" type="text" data-width="270"
							   placeholder="格式：yyyy.mm.dd"
							   value="${cm:formatDate(partyPost.endDate,'yyyy.MM.dd')}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 工作单位及担任职务</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="detail">${partyPost.detail}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="remark">${partyPost.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty partyPost?'确定':'添加'}</button>
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
                        $("#jqGrid_post").trigger("reloadGrid");
						$("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
	$.register.date($('.input-group.date'));
</script>