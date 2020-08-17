<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审核情况通知</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsAdmin_msg3" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input  type="hidden" name="partyId" value="${param.partyId}">
        <input  type="hidden" name="pass" value="${param.pass}">
        <div class="form-group">
            <label class="col-xs-3 control-label">${_p_partyName}</label>
            <div class="col-xs-8 label-text">
               ${party.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号码</label>
            <div class="col-xs-8">
                <input class="form-control" type="text" name="mobile" value="${uv.mobile}">
                <span class="help-block"><span class="star">*</span> 发送给指定手机号码，留空则发给全部管理员</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>短信内容</label>
            <div class="col-xs-8">
                <c:if test="${param.pass==0}">
                    <c:set var="msgTpl" value="ct_pcs_pr_unpass_msg"/>
                </c:if>
                <c:if test="${param.pass==1}">
                    <c:set var="msgTpl" value="ct_pcs_pr_pass_msg"/>
                </c:if>
                <textarea required class="form-control" type="text" name="msg" rows="8">${cm:getContentTpl(msgTpl).content}</textarea>
                <span class="help-block"><span class="star">*</span> 短信内容可修改</span>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定发送"/>
</div>
<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");
                        SysMsg.info("共发送{0}条短信，其中发送成功{1}条".format(ret.totalCount, ret.successCount))
                    }
                }
            });
        }
    });
    //$('textarea.limited').inputlimiter();
</script>