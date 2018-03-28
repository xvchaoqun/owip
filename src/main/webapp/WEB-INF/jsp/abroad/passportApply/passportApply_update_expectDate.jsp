<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改应交日期</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/abroad/passportApply_update_expectDate" id="modalForm" method="post">
        <input type="hidden" name="id" value="${passportApply.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label">应交日期</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control date-picker" type="text"  name="expectDate"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(passportApply.expectDate, "yyyy-MM-dd")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
    $.register.date($('.date-picker'))
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