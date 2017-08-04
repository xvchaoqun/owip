<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div style="width: 500px;">
  <div class="modal-header">
    <h3>设定招聘会时间和地点</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsPost_detail/step3_meeting" id="modalForm" method="post">
      <input type="hidden" name="id" value="${crsPost.id}">

      <div class="form-group">
        <label class="col-xs-3 control-label">招聘会时间</label>

        <div class="col-xs-6">
          <div class="input-group">
            <input class="form-control datetime-picker" required type="text" name="meetingTime"
                   value="${cm:formatDate(crsPost.meetingTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-3 control-label">地点</label>

        <div class="col-xs-6">
          <input required class="form-control" type="text" name="meetingAddress" value="${crsPost.meetingAddress}">
        </div>
      </div>
      <div class="modal-footer center">
        <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
        <input type="submit" class="btn btn-primary" value="确定"/>
      </div>
    </form>
  </div>
</div>
<script>
  register_datetime($('.datetime-picker'));

  $("#modalForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            //$("#modal").modal('hide');
            $("#step-content li.active .loadPage").click()
          }
        }
      });
    }
  });
  $("#modalForm :checkbox").bootstrapSwitch();
  $('#modalForm [data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();
</script>