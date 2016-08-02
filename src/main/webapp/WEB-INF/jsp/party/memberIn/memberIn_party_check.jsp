<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审核通过</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberIn_party_check" id="modalForm" method="post">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <c:set var="len" value="${fn:length(fn:split(param['ids[]'],','))}"/>
        <c:if test="${len>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">申请记录</label>
                <div class="col-xs-6 label-text">
                        ${len} 条
                </div>
            </div>
        </c:if>
        <c:if test="${len==1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">申请人</label>
                <div class="col-xs-6 label-text">
                        ${cm:getUserById(memberIn.userId).realname}
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否有回执</label>
            <div class="col-xs-6">
                    <label>
                        <input name="hasReceipt" type="checkbox" />
                        <span class="lbl"></span>
                    </label>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $('[data-rel="select2"]').select2({allowClear:false});
    $("#modalForm :checkbox").bootstrapSwitch();
    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        SysMsg.success('操作成功。', '成功', function () {
                            goto_next(${param.goToNext==1});
                        });
                    }
                }
            });
        }
    });
</script>