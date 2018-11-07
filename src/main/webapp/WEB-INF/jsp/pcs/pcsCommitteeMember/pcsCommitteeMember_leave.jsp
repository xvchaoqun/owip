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
            <label class="col-xs-3 control-label">离任文件</label>

            <div class="col-xs-6">
                <input class="form-control" type="file" name="_quitFilePath"/>
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
    <button type="button" class="btn btn-primary" id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口" >确定</button>
</div>
<script>
    $.fileInput($('#modalForm input[type=file]'),{
        no_file:'请上传pdf文件',
        allowExt: ['pdf']
    })
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>