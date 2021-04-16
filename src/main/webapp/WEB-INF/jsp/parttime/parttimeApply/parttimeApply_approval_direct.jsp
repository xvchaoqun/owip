<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>审批通过</h3>
</div>
<div class="modal-body">
  <form autocomplete="off" disableautocomplete id="modalForm" class="form-horizontal" method="post" action="${ctx}/parttime/parttimeApply_approval">
    <input type="hidden" name="applyId" value="${param.applyId}">
    <input type="hidden" name="approvalTypeId" value="${param.approvalTypeId}">
    <input type="hidden" name="pass" value="1">
    <input type="hidden" name="isAdmin" value="1">
    <div class="form-group">
      <label class="col-xs-3 control-label"><span class="star">*</span>审批人</label>
      <div class="col-xs-6">
        <select required data-rel="select2" name="approvalUserId" data-placeholder="请选择">
          <option></option>
          <c:forEach items="${approvers}" var="approver">
            <option value="${approver.userId}">${approver.realname}(${approver.code})</option>
          </c:forEach>
        </select>
      </div>
    </div>
    <div class="form-group">
      <label class="col-xs-3 control-label">审批日期</label>
      <div class="col-xs-6">
        <div class="input-group">
          <input class="form-control date-picker" name="approvalTime" type="text"
                 data-date-format="yyyy-mm-dd" value="${_today}"/>
          <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
        </div>
      </div>
    </div>
    <div class="form-group">
      <label class="col-xs-3 control-label">审批意见</label>
      <div class="col-xs-6">
        <textarea class="form-control limited" name="remark"></textarea>
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
  $('#modalForm [data-rel="select2"]').select2();
  $("#submitBtn").click(function(){
    $("#modalForm").submit(); return false;
  });
  $("#modalForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            $("#modal").modal('hide');
            SysMsg.success("审批成功。", function(){
              $.openView("${ctx}/parttime/parttimeApply_view?id=${param.applyId}")
            })
          }
        }
      });
    }
  });
</script>