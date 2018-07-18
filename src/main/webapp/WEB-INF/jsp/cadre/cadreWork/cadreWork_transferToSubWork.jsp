<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>修改为其间工作经历</h3>
</div>
<div class="modal-body">
  <form class="form-horizontal" action="${ctx}/cadreWork_transferToSubWork" id="modalForm" method="post">
    <input type="hidden" name="id" value="${param.id}">
    <div class="form-group">
      <label class="col-xs-4 control-label">工作经历</label>
      <div class="col-xs-8 label-text">
        ${cm:formatDate(cadreWork.startTime, "yyyy-MM-dd")} ~
        ${cm:formatDate(cadreWork.endTime, "yyyy-MM-dd")}  ${cadreWork.detail}
      </div>
    </div>
    <div class="form-group">
      <label class="col-xs-4 control-label">选择所属主要工作经历</label>
      <div class="col-xs-8">
        <select required data-rel="select2" name="fid" data-placeholder="请选择" data-width="320">
          <option></option>
          <c:forEach items="${topCadreWorks}" var="topCadreWork">
            <option value="${topCadreWork.id}">${cm:formatDate(topCadreWork.startTime, "yyyy-MM-dd")} ~
            ${cm:formatDate(topCadreWork.endTime, "yyyy-MM-dd")}  ${topCadreWork.detail}</option>
          </c:forEach>
        </select>
      </div>
    </div>
  </form>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
  $("#modal form").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            $("#modal").modal("hide");
            $("#jqGrid_cadreWork").trigger("reloadGrid");
          }
        }
      });
    }
  });
  $('[data-rel="select2"]').select2();
</script>