<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${sysMsg!=null?'编辑':'添加'}系统提醒</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sys/sysMsg_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${sysMsg.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 接收人</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-width="272" data-ajax-url="${ctx}/sysUser_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 标题</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="title" value="${sysMsg.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 内容</label>
				<div class="col-xs-6">
                        <textarea required class="form-control" name="content" rows="8">${sysMsg.content}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty sysMsg?'确定':'添加'}</button>
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
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>