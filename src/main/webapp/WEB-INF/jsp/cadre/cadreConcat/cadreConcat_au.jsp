<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>更新干部联系方式</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreConcat_au?cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="userId" value="${cadre.userId}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${cadre.user.realname}
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">手机号</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="mobile" value="${cadre.user.mobile}">
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">短信称谓</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="msgTitle" value="${empty cadre.user.msgTitle?cadre.user.realname:cadre.user.msgTitle}">
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">办公电话</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="phone" value="${cadre.user.phone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">家庭电话</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="homePhone" value="${cadre.user.homePhone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">电子邮箱</label>
				<div class="col-xs-6">
                        <input class="form-control email" type="text" name="email" value="${cadre.user.email}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
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

</script>