<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>更新干部联系方式</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreConcat_au?cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="userId" value="${cadre.userId}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${cadre.realname}
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">手机号</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="mobile" value="${cadre.mobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">代收短信的手机号</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="msgMobile" value="${cadre.user.msgMobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否禁止接收短信</label>
				<div class="col-xs-6">
                    <input type="checkbox" class="big" name="notSendMsg" ${cadre.user.notSendMsg?"checked":""}/>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>短信称谓</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="msgTitle" value="${empty cadre.msgTitle?cadre.realname:cadre.msgTitle}">
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">办公电话</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="phone" value="${cadre.phone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">家庭电话</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="homePhone" value="${cadre.homePhone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">电子邮箱</label>
				<div class="col-xs-6">
                        <input class="form-control email" type="text" name="email" value="${cadre.email}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="更新"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $("#modalForm :checkbox").bootstrapSwitch();

</script>