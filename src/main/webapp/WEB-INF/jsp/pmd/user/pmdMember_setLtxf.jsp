<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>修改党费应交额</h3>
</div>
<div class="modal-body">
  <form class="form-horizontal" action="${ctx}/user/pmd/pmdMember_setLtxf" id="modalForm" method="post">
    <input name="pmdMemberId" type="hidden" value="${param.pmdMemberId}">
    <div class="form-group">
      <label class="col-xs-4 control-label">党员</label>
      <div class="col-xs-6 label-text">
        ${pmdConfigMember.user.realname}
      </div>
    </div>
    <div class="form-group">
      <label class="col-xs-4 control-label">离退休费</label>
      <div class="col-xs-6">
        <input required class="number" data-rule-min="0.01" maxlength="10"
               type="text" name="retireSalary" value="${cm:stripTrailingZeros(pmdConfigMember.retireSalary)}">
      </div>
    </div>
  </form>
</div>
<div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>
<script>

  $("#submitBtn").click(function () {
    $("#modalForm").submit();
    return false;
  });
  $("#modalForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            $("#modal").modal('hide');
            <c:if test="${param.isBranchAdmin==1}">
            $("#jqGrid2").trigger("reloadGrid");
            </c:if>
            <c:if test="${param.isBranchAdmin!=1}">
            $("#jqGrid").trigger("reloadGrid");
            </c:if>
          }
        }
      });
    }
  });
</script>