<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><span style="font-size:25px;font-weight: bolder">${cadre.realname}</span>提任校领导
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadre_promote" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadre.id}">

        <div class="form-group">
            <label class="col-xs-4 control-label">提任后所在单位及职务</label>
            <div class="col-xs-7">
                <input  class="form-control" type="text" name="title" value="${cadre.title}">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <div style="font-size: 16pt;font-weight: bolder;color: darkred;text-align: left;padding: 12px 0;">
        注：提任后该干部将转入校级领导信息库，请谨慎操作。</div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $.loadView('${ctx}/cadre_view?cadreId=${cadre.id}&to=cadrePost_page')
                    }
                }
            });
        }
    });
</script>