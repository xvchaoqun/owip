<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设定本学期起止时间</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitTeam_term" autocomplete="off" disableautocomplete id="modalForm" method="post">

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>起始时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="termStartDate" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(_sysConfig.termStartDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>结束时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="termEndDate" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(_sysConfig.termEndDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定</button>
</div>

<script>
    $.register.date($('.date-picker'));
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $.hashchange()
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>