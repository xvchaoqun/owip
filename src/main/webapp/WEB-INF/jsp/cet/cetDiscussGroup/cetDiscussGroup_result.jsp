<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>上传开会信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetDiscussGroup_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetDiscussGroup.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">研讨主题</label>
            <c:if test="${not empty cetDiscussGroup.subject}">
                <div class="col-xs-6 label-text">
                        ${cetDiscussGroup.subject}
                </div>
            </c:if>
            <c:if test="${empty cetDiscussGroup.subject}">
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="subject" value="${cetDiscussGroup.subject}">
                </div>
            </c:if>
        </div>
        <div class="form-group" id="_startTime">
            <label class="col-xs-3 control-label">召开时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" required type="text" name="discussTime"
                           value="${cm:formatDate(cetDiscussGroup.discussTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">召开地点</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="discussAddress"
                       value="${cetDiscussGroup.discussAddress}">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
    $.register.datetime($('.datetime-picker'));
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
</script>