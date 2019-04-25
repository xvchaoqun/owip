<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>转移</h3>
</div>
<div class="modal-body">
  <form autocomplete="off" disableautocomplete id="modalForm" class="form-horizontal" method="post" action="${ctx}/cadreReserve_transfer">
  <div class="form-group">
      <label class="col-xs-3 control-label"><span class="star">*</span>选择转移对象</label>
      <div class="col-xs-6">
        <select required data-rel="select2-ajax"
                data-ajax-url="${ctx}/cadreReserve_selects?reserveStatus=<%=CadreConstants.CADRE_RESERVE_STATUS_NORMAL%>"
                data-width="300"
                name="cadreId" data-placeholder="请输入账号或姓名或工号">
          <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
        </select>
      </div>
    </div>
  <div class="form-group">
      <label class="col-xs-3 control-label"><span class="star">*</span>转移至</label>
      <div class="col-xs-6">
        <select required data-rel="select2" name="type"
                data-width="300"
                data-placeholder="请选择">
          <option></option>
         <c:forEach var="_type" items="${cm:getMetaTypes('mc_cadre_reserve_type')}">
            <option value="${_type.key}"> ${_type.value.name}</option>
         </c:forEach>
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