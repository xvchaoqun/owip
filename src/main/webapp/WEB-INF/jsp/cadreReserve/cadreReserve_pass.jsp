<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>列为考察对象</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreReserve_pass" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="reserveId" value="${cadreReserve.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">账号</label>
            <div class="col-xs-6 label-text">
                ${cadre.realname}-${cadre.code}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任职单位及职务</label>
            <div class="col-xs-6">
                <textarea class="form-control" rows="3" name="title">${cadre.title}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="reserveRemark" rows="5">${cadreReserve.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-success" value="列为考察对象"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $.hashchange('', '${ctx}/cadreInspect');
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>