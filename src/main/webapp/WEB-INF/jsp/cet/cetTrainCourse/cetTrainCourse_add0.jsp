<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加培训课程</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourse_add" id="modalForm" method="post">
        <input type="hidden" name="trainId" value="${param.trainId}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所在培训班</label>
            <div class="col-xs-6 label-text">
                ${cetTrain.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">课程名称</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-width="320"
                        data-ajax-url="${ctx}/cet/cetCourse_selects"
                        name="courseId" data-placeholder="请输入课程名称">
                    <option></option>
                </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="添加"/>
</div>

<script>
    register_ajax_select($('#modal [data-rel="select2-ajax"]'));
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