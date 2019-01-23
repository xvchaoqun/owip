<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>修改党费应交额</h3>
</div>
<div class="modal-body">
  <form class="form-horizontal" action="${ctx}/user/pmd/pmdMember_setRetireBase" id="modalForm" method="post">
    <input name="pmdMemberId" type="hidden" value="${param.pmdMemberId}">
    <div class="form-group">
      <label class="col-xs-4 control-label">党员</label>
      <div class="col-xs-6 label-text">
        ${pmdConfigMember.user.realname}
      </div>
    </div>
    <div class="form-group">
      <label class="col-xs-4 control-label">离退休人员党费计算基数(¥)</label>
      <div class="col-xs-8 label-text">
        <c:if test="${param.view==1 || param.view==2}">
          ${cm:stripTrailingZeros(pmdConfigMember.retireSalary)}
        </c:if>
        <c:if test="${param.view!=1 && param.view!=2}">
        <input required class="number" data-rule-min="0.01" maxlength="10" style="width: 100px;"
               type="text" name="retireSalary" value="${cm:stripTrailingZeros(pmdConfigMember.retireSalary)}">
        <button type="button" class="btn btn-success" onclick="_syncRetireSalary()"><i class="fa fa-refresh"></i> 同步最新离退休人员党费计算基数</button>
        </c:if>
      </div>
    </div>
    <div class="center" id="msg">

    </div>
  </form>
</div>
<div class="modal-footer">
<c:if test="${param.view==1 || param.view==2}">
  <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</c:if>
<c:if test="${param.view!=1 && param.view!=2}">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <button id="submitBtn" type="button" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</c:if>
</div>
<script>
  function _syncRetireSalary(){

    $("#msg").html('<span class="text-primary">数据同步中...</span>');
    $.getJSON("${ctx}/user/pmd/pmdMember_syncRetireSalary",{pmdMemberId:${param.pmdMemberId}},function(ret){
        if(ret.exist){
          $("#modalForm input[name=retireSalary]").val(ret.base);
          $("#msg").html('<span class="text-success">已读取'+ret.rq+'月份的党费计算基数</span>')
        }else{
          $("#msg").html('<span class="text-danger">还没有该老师的党费计算基数数据</span>')
        }
    });
  }
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