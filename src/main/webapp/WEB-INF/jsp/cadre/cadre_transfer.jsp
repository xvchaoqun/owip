<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>干部库转移</h3>
</div>
<div class="modal-body">
  <form id="modalForm" class="form-horizontal" method="post" action="${ctx}/cadre_transfer">
  <div class="form-group">
      <label class="col-xs-3 control-label">选择干部</label>
      <div class="col-xs-6">
        <select data-rel="select2-ajax"
                data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_MIDDLE},${CADRE_STATUS_MIDDLE_LEAVE},${CADRE_STATUS_LEADER},${CADRE_STATUS_LEADER_LEAVE}"
                data-width="300"
                name="cadreId" data-placeholder="请输入账号或姓名或工号">
          <option></option>
        </select>
      </div>
    </div>
  <div class="form-group">
      <label class="col-xs-3 control-label">转移至</label>
      <div class="col-xs-6">
        <select data-rel="select2" name="status"
                data-width="300"
                data-placeholder="请选择">
          <option></option>
          <option value="${CADRE_STATUS_MIDDLE}">现任干部库</option>
          <option value="${CADRE_STATUS_MIDDLE_LEAVE}">离任干部库</option>
          <option value="${CADRE_STATUS_LEADER}">现任校领导库</option>
          <option value="${CADRE_STATUS_LEADER_LEAVE}">离任校领导库</option>
        </select>
      </div>
    </div>
  </form>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
  <input type="button" id="submitBtn" class="btn btn-primary" value="转移"/>
</div>
<script>
  $.register.user_select($('#modal select[name=cadreId]'));
  $('[data-rel="select2"]').select2();
  $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
  $("#modalForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success:function(ret) {
          if (ret.success) {
            $("#modal").modal('hide');
            $("#jqGrid").trigger("reloadGrid");
            SysMsg.success('转移成功。', '成功');
          }
        }});
    }
  });
</script>