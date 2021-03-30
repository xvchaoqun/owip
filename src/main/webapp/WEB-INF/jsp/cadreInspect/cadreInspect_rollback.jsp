<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>通过任命</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreInspect_rollback" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="inspectId" value="${id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">是否现任干部</label>
            <div class="col-xs-6">
                <label>
                    <input name="isCadre" type="checkbox"/>
                    <span class="lbl"></span>
                </label>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-success" value="确认"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
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
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $("#modal :checkbox").bootstrapSwitch();

    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>