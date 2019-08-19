<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="space-4"></div>
<div class="widget-box" style="width: 500px">
  <div class="widget-header">
    <h4 class="widget-title">
      招聘会人数要求
    </h4>
  </div>
  <div class="widget-body">
    <div class="widget-main" id="requirement-content">
      <form class="form-horizontal" action="${ctx}/crsPost_detail/step1_meetingApplyCount" autocomplete="off" disableautocomplete id="modalForm"
            method="post">
        <input type="hidden" name="id" value="${crsPost.id}">
        <div class="form-group">
          <label class="col-xs-4 control-label"><span class="star">*</span>招聘会人数要求</label>

          <div class="col-xs-6">
            <input required class="form-control digits" type="text" name="meetingApplyCount"
                   value="${crsPost.meetingApplyCount}">
          </div>
        </div>
        <div class="modal-footer center">
          <input type="submit" class="btn btn-primary" value="更新"/>
        </div>
      </form>
    </div>
  </div>
</div>
<script>
  $("#modalForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            SysMsg.success("更新成功。")
          }
        }
      });
    }
  });
</script>