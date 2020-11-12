<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审核</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pm/pm3Meeting_check" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">
        <c:set var="num" value='${fn:length(fn:split(param.ids,","))}'/>
        <c:if test="${not empty param.ids && num==1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">组织生活</label>
                <div class="col-xs-6 label-text">
                    ${pm3Meeting.name}
                </div>
            </div>
        </c:if>
        <c:if test="${num>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">已选择活动数量</label>
                <div class="col-xs-6 label-text">
                     ${num}个
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label"style="padding: 10px">是否通过</label>
            <div class="col-xs-8" style="padding: 10px">
                <input type="checkbox" class="big" name="check" checked/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">审核意见</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" rows="4" type="text" name="checkOpinoin"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
             data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
             class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>
<script>
    $("#modalForm :checkbox").bootstrapSwitch();
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        pm3_reload();
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
</script>