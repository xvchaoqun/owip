<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑选课/退课状态</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourse_applyStatus" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrainCourse.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">课程名称</label>
            <div class="col-xs-8 label-text">
                ${cetTrainCourse.cetCourse.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">选课/退课状态</label>
            <div class="col-xs-8">
                <select name="applyStatus" data-rel="select2" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${CET_TRAIN_COURSE_APPLY_STATUS_MAP}" var="applyStatus">
                    <option value="${applyStatus.key}">${applyStatus.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=applyStatus]").val('${cetTrainCourse.applyStatus}')
                </script>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $('#modalForm [data-rel="select2"]').select2();
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