<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>${MEMBER_STAY_TYPE_MAP.get(type)}组织关系暂留申请</h3>
</div>
<div class="modal-body">
  <form  class="form-horizontal">
    <div class="form-group">
      <label class="col-xs-3 control-label">选择用户</label>
      <div class="col-xs-6">
        <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects" data-width="350"
                name="userId" data-placeholder="请选择">
          <option></option>
        </select>
      </div>
    </div>
  </form>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>
</div>
<script>
  register_user_select($('#modal select[name=userId]'));
  $("#modal #submitBtn").click(function(){

    var userId = $("#modal select[name=userId]").val();
    if(userId=="") return;
    $.loadView("${ctx}/user/memberStay?type=${type}&auth=admin&userId=" + userId);
  })
</script>