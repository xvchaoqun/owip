<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3><c:if test="${partyMember!=null}">修改</c:if><c:if test="${partyMember==null}">添加</c:if>委员</h3>
</div>
<div class="modal-body overflow-visible">
<form class="form-horizontal no-footer" action="${ctx}/partyMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
  <input type="hidden" name="groupId" value="${partyMemberGroup.id}">
  <input type="hidden" name="id" value="${partyMember.id}">
  <div class="form-group">
    <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
        <c:if test="${partyMember==null}">
          <div class="col-xs-6">
              <select required ${partyMember!=null?"disabled":""} data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects" data-width="260"
                      name="userId" data-placeholder="请输入账号或姓名或学工号">
                <option value="${uv.id}">${uv.realname}-${uv.code}</option>
              </select>
          </div>
        </c:if>

        <c:if test="${partyMember!=null}">
          <div class="col-xs-6 label-text">
              <input type="hidden" name="userId" value="${uv.id}">
              ${uv.realname}(${uv.code})
          </div>
        </c:if>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label"><span class="star">*</span>职务</label>
    <div class="col-xs-6">
      <select required data-rel="select2" name="postId" data-placeholder="请选择"  data-width="260">
        <option></option>
        <jsp:include page="/metaTypes?__code=mc_party_member_post"/>
      </select>
      <script>
        $("#modal select[name=postId]").val('${partyMember.postId}');
      </script>
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">分工</label>
    <div class="col-xs-6">
      <select name="_typeIds" class="multiselect" multiple="" data-placeholder="请选择" style="position: relative"> 
          <c:import url="/metaTypes?__code=mc_party_member_type"/>
      </select> 
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">任职时间</label>
    <div class="col-xs-6">
      <div class="input-group">
        <input class="form-control date-picker" name="assignDate" type="text"
               data-date-min-view-mode="1" placeholder="yyyy.mm"
               data-date-format="yyyy.mm" value="${cm:formatDate(partyMember.assignDate,'yyyy.MM')}" />
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  <c:if test="${partyMember.isHistory}">
  <div class="form-group">
    <label class="col-xs-3 control-label">离任时间</label>
    <div class="col-xs-6">
      <div class="input-group">
        <input class="form-control date-picker" name="dismissDate" type="text"
               data-date-min-view-mode="1" placeholder="yyyy.mm"
               data-date-format="yyyy.mm" value="${cm:formatDate(partyMember.dismissDate,'yyyy.MM')}" />
        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
      </div>
    </div>
  </div>
  </c:if>
  <div class="form-group">
    <label class="col-xs-3 control-label">办公电话</label>

    <div class="col-xs-6">
      <input class="form-control" type="text" name="officePhone" value="${partyMember.officePhone}">
    </div>
  </div>
  <div class="form-group">
    <label class="col-xs-3 control-label">手机号</label>

    <div class="col-xs-6">
      <input class="form-control mobile" type="text" name="mobile" value="${partyMember.mobile}">
    </div>
  </div>
</form>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="button" id="submitBtn" class="btn btn-primary"
         value="<c:if test="${partyMember!=null}">确定</c:if><c:if test="${partyMember==null}">添加</c:if>"/>
</div>
<script>
  $.register.multiselect($('#modal select[name=_typeIds]'), [${partyMember.typeIds}]);
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