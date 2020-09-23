<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>退回</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/oa/oaGridParty_report" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">
        <input type="hidden" name="report" value="${param.report}"/>
        <c:set var="num" value='${fn:length(fn:split(param.ids,","))}'/>
        <c:if test="${not empty param.ids && num==1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">表格名称</label>
                <div class="col-xs-6 label-text">
                    ${oaGridParty.gridName}
                </div>
            </div>
        </c:if>
        <c:if test="${num>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">已选择表格数量</label>
                <div class="col-xs-6 label-text">
                     ${num}个
                </div>
            </div>
        </c:if>
        <c:if test="${param.report==OA_GRID_PARTY_BACK}">
            <div class="form-group">
                <label class="col-xs-4 control-label">退回原因</label>
                <div class="col-xs-6">
                    <textarea class="form-control limited" rows="4" type="text" name="backReason"></textarea>
                </div>
            </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
             data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
             class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
</script>