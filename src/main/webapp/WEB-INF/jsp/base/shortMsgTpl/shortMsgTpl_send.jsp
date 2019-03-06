<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>发送短信</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/shortMsgTpl_send" id="modalForm" method="post">
            <input type="hidden" name="tplId" value="${param.id}">
			<div class="form-group">
				<label class="col-xs-2 control-label">选择用户</label>
				<div class="col-xs-5">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                            name="receiverId" data-placeholder="请输入账号或姓名或学工号">
                        <option></option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><span class="star">*</span>手机号码</label>
				<div class="col-xs-5" style="width: 225px;">
                    <input required class="form-control" type="text" name="mobile">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">模板名称</label>
				<div class="col-xs-5 label-text">
					${shortMsgTpl.name}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><span class="star">*</span>短信内容</label>
				<div class="col-xs-9 label-text">
                   <textarea required class="form-control limited" type="text"
                             name="content" rows="10" maxlength="500">${shortMsgTpl.content}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="发送"/>
</div>

<script>
    var $selectCadre = $.register.user_select($('#modalForm select[name=receiverId]'));
    $selectCadre.on("change",function(){
        var ret = $(this).select2("data")[0];
        $('#modalForm input[name=mobile]').val(ret.msgMobile || ret.mobile);
    });

    $('textarea.limited').inputlimiter();
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
</script>