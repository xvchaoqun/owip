<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>短信提醒</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetProjectObj_uploadWriteMsg" id="modalForm" method="post">
        <input type="hidden" name="objIds[]" value="${param["objIds[]"]}">
        <input type="hidden" name="projectId" value="${param.projectId}">
        <div class="form-group">
            <label class="col-xs-3 control-label">发送人数</label>
            <div class="col-xs-8 label-text">
                <c:set value="${fn:length(userIds)}" var="num"/>
                ${num} 人
<c:if test="${num>0}">
                (
                <c:forEach items="${userIds}" var="userId" varStatus="vs" end="2">
                    ${cm:getUserById(userId).realname}
                    <c:if test="${!vs.last}">、</c:if>
                </c:forEach>
                <c:if test="${num>3}">
                    等
                </c:if>
                )
    </c:if>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号码</label>
            <div class="col-xs-8">
                <input class="form-control mobile" type="text" name="mobile">
                <span class="help-block">* 发送给指定手机号码，留空则发给全部未上传心得体会的学员</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">短信内容</label>
            <div class="col-xs-8">
                <textarea required class="form-control" type="text" name="msg" rows="8"></textarea>
                <span class="help-block">* 短信内容可修改</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">是否添加后缀</label>
            <div class="col-xs-8">
                <input type="checkbox" class="big" name="addSuffix" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">后缀</label>
            <div class="col-xs-8">
                <input class="form-control" type="text" name="suffix" value="（系统短信，请勿直接回复）">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
           class="btn btn-primary" ${num==0?"disabled":""}>
        确定发送
    </button>
</div>

<script>
    $("#modalForm :checkbox").bootstrapSwitch();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");
                        SysMsg.info("共发送{0}条短信，其中发送成功{1}条".format(ret.totalCount, ret.successCount))
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$('textarea.limited').inputlimiter();
</script>