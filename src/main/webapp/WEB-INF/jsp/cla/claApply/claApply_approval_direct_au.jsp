<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>修改审批意见</h3>
</div>
<div class="modal-body">
  <form autocomplete="off" disableautocomplete id="modalForm" class="form-horizontal" method="post" action="${ctx}/cla/claApply_approval_direct_au">
    <input type="hidden" name="approvalLogId" value="${approvalLog.id}">
    <input type="hidden" name="applyId" value="${param.applyId}">
    <div class="form-group">
      <label class="col-xs-3 control-label">审批日期</label>
      <div class="col-xs-6">
        <div class="input-group">
          <input class="form-control date-picker" name="approvalTime" type="text"
                 data-date-format="yyyy-mm-dd" value="${cm:formatDate(approvalLog.createTime, "yyyy-MM-dd")}"/>
          <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
        </div>
      </div>
    </div>
    <div class="form-group">
      <label class="col-xs-3 control-label">审批意见</label>
      <div class="col-xs-6">
        <textarea class="form-control limited" name="remark">${approvalLog.remark}</textarea>
      </div>
    </div>
  </form>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>
</div>
<script>
  $.register.date($('.date-picker'))
  $("#submitBtn").click(function(){
    $("#modalForm").submit(); return false;
  });
  $("#modalForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            $("#modal").modal('hide');
            SysMsg.success("修改成功。", function(){
              $.loadView("${ctx}/cla/claApply_view?id=${param.applyId}&type=${param.type}&approvalTypeId=${param.approvalTypeId}")
            })
          }
        }
      });
    }
  });
</script>