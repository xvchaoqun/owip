<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>请假原因</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainObj_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" value="${cetTrainObjId}" name="id">
        <input type="hidden" value="${CET_FINISHED_STATUS_REST}" name="status">
        <div class="form-group" id="_teacher">
            <label class="col-xs-3 control-label"><span class="star">*</span> 请假原因</label>
            <div class="col-xs-6">
                <textarea required class="noEnter" rows="8" name="remark"
                          style="width: 100%"></textarea>
            </div>
        </div>
        <div class="modal-footer">
            <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
            <input type="submit" class="btn btn-primary" value="确定"/>
        </div>
    </form>
</div>


<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal2").modal('hide');
                        $("#jqGrid_popup").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>