<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>信息审核</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsApplicant_infoCheck" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.applicantId}">

        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${crsApplicant.user.realname}（教工号：${crsApplicant.user.code}）
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">报名岗位</label>
            <div class="col-xs-6 label-text">
                ${crsApplicant.post.name}）
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">审核结果</label>

            <div class="col-xs-6 label-text" style="font-size: 15px;">
                <input type="checkbox" class="big" name="status" checked data-off-text="不通过" data-on-text="通过"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">说明</label>

            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${crsApplicant!=null}">确定</c:if><c:if test="${crsApplicant==null}">添加</c:if>"/>
</div>

<script>
    $("#modal :checkbox").bootstrapSwitch();
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
</script>