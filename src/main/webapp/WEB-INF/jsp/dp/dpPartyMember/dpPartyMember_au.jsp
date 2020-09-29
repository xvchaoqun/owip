<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3><c:if test="${dpPartyMember!=null}">修改</c:if><c:if test="${dpPartyMember==null}">添加</c:if>委员</h3>
</div>
<div class="modal-body">
<form class="form-horizontal no-footer" action="${ctx}/dp/dpPartyMember_au?groupId=${dpPartyMemberGroup.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
  <input type="hidden" name="groupId" value="${dpPartyMemberGroup.id}">
  <input type="hidden" name="id" value="${dpPartyMember.id}">
  <div class="form-group">
    <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
    <div class="col-xs-6">
      <select required data-rel="select2-ajax" data-ajax-url="${ctx}/dp/teacher_select" data-width="260"
              name="userId" data-placeholder="请输入账号或姓名或工作证号">
        <option value="${uv.id}">${uv.realname}-${uv.code}</option>
      </select>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label"><span class="star">*</span>职务</label>
    <div class="col-xs-6">
      <select required data-rel="select2" name="postId" data-placeholder="请选择"  data-width="260">
        <option></option>
        <jsp:include page="/metaTypes?__id=${cm:getMetaClassByCode('mc_dp_party_member_post').id}"/>
      </select>
      <script>
        $("#modal select[name=postId]").val('${dpPartyMember.postId}');
      </script>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">分工</label>
    <div class="col-xs-6">
      <select name="_typeIds" class="multiselect" multiple="" data-placeholder="请选择" style="position: relative"> 
          <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_dp_party_member_type').id}"/>
      </select> 
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">任职时间</label>
    <div class="col-xs-6">
      <div class="input-group">
        <input class="form-control date-picker" name="assignDate" type="text"
               data-date-min-view-mode="1" placeholder="yyyy.mm"
               data-date-format="yyyy.mm" value="${cm:formatDate(dpPartyMember.assignDate,'yyyy.MM')}" />
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">办公电话</label>

    <div class="col-xs-6">
      <input class="form-control" type="text" name="officePhone" value="${dpPartyMember.officePhone}">
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">备注</label>
    <div class="col-xs-6" style="width: 296px">
						<textarea class="form-control limited noEnter" type="text"
                                  name="remark" rows="3">${dpPartyMember.remark}</textarea>
    </div>
  </div>
</form>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="button" id="submitBtn" class="btn btn-primary"
         value="<c:if test="${dpPartyMember!=null}">确定</c:if><c:if test="${dpPartyMember==null}">添加</c:if>"/>
</div>
<script>
  $.register.multiselect($('#modal select[name=_typeIds]'), [${dpPartyMember.typeIds}]);
  $.register.date($('.date-picker'));

  $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
  $("#modalForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            $("#modal").modal("hide")
            var jqGridId = '${param.gridId}'||'jqGrid';
            $("#"+jqGridId).trigger("reloadGrid");
          }
        }
      });
    }
  });
  $.register.user_select($('#modal select[name=userId]'));
  $('[data-rel="select2"]').select2();
</script>