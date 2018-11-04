<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><span style="font-size:25px;font-weight: bolder">${cadre.realname}</span>离任
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsCommitteeMember_leave" id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <input type="hidden" name="type" value="${param.type}">
        <input type="hidden" name="isQuit" value="1">
        <div class="form-group">
            <label class="col-xs-3 control-label">离任日期</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="quitDate"
                           type="text" data-date-format="yyyy-mm-dd"
                           value="${_today}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">离职原因</label>
            <div class="col-xs-6">
                <textarea class="form-control limited noEnter" rows="8"
                          name="quitReason">${pcsCommitteeMember.quitReason}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
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
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>