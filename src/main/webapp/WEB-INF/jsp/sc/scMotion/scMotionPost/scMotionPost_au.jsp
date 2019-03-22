<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scMotionPost!=null}">编辑</c:if><c:if test="${scMotionPost==null}">添加</c:if>动议所包含的岗位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMotionPost_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scMotionPost.id}">
        <input type="hidden" name="motionId" value="${motion.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">岗位</label>
            <div class="col-xs-6">
                <select name="unitPostId" data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects?unitId=${motion.unitId }"
                        data-placeholder="请选择">
                    <option value="${unitPost.id}" title="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.name}</option>
                </select>
                <script>
                    $.register.del_select($("#modalForm select[name=unitPostId]"), 273)
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark">${scMotionPost.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if
            test="${scMotionPost!=null}">确定</c:if><c:if test="${scMotionPost==null}">添加</c:if></button>
</div>
<script>
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
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>