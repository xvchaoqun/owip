<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_APPLY_STAGE_DENY" value="<%=OwConstants.OW_APPLY_STAGE_DENY%>"/>

<div  style="padding-top: 50px;"></div>
<c:if test="${memberApply.stage==OW_APPLY_STAGE_DENY}">
  <div class="alert alert-danger">
    <button type="button" class="close" data-dismiss="alert">
      <i class="ace-icon fa fa-times"></i>
    </button>
    <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if test="${not empty memberApply.reason}">: ${memberApply.reason}</c:if>
    <br>
  </div>
</c:if>
    <div class="page-header">
      <h1>
        申请入党
      </h1>
    </div>
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" method="post" action="${ctx}/user/memberApply">
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> ${(_user.type==USER_TYPE_JZG)?"教工号":"学号"}</label>
        <div class="col-sm-9 label-text">
          ${_user.code}
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>提交申请书时间</label>
        <div class="col-sm-2 col-xs-4">
          <div class="input-group" style="width: 150px">
            <input required class="form-control date-picker" name="_applyTime" type="text"
                   data-date-format="yyyy-mm-dd"
                   data-date-end-date="${_today}"
                   value="${cm:formatDate(memberApply.applyTime,'yyyy-MM-dd')}"/>
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>请选择组织机构</label>
        <div class="col-sm-9">
          <select required name="classId" data-rel="select2" data-placeholder="请选择" data-width="150">
            <option></option>
            <c:import url="/metaTypes?__code=mc_party_class"/>
          </select>
          <script>
            $("#modalForm select[name=classId]").val("${party.classId}")
          </script>
          </div>
        </div>
        <div class="form-group"  id="party" style="${empty party?'display: none;':''}" >
            <div class="col-sm-offset-3 col-sm-9">
          <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                  name="partyId" data-placeholder="请选择${_p_partyName}">
            <option value="${party.id}">${party.name}</option>
          </select>
          </div>
         </div>
          <div class="form-group" id="branch" style="${empty branch?'display: none;':''}" >
            <div class="col-sm-offset-3 col-sm-9">
          <select data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
                  name="branchId" data-placeholder="请选择党支部">
            <option value="${branch.id}">${branch.name}</option>
          </select>
              </div>
            </div>

      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> 备注</label>
        <div class="col-sm-9">
          <textarea name="remark"  class="col-xs-6 col-sm-3" rows="5">${memberApply.remark}</textarea>
        </div>
      </div>

      <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
          <button class="btn btn-info" type="submit" id="submitBtn" data-loading-text="提交中..."  data-success-text="您的申请已提交成功" autocomplete="off">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
          </button>

          &nbsp; &nbsp; &nbsp;
          <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            返回
          </button>
        </div>
      </div>
      </form>
      <script>
        $.register.class_party_branch_select($("#modalForm"), "party", "branch",
                '${cm:getMetaTypeByCode("mt_direct_branch").id}', '${party.id}');
        $.register.date($('.date-picker'));

        $("form").validate({
          submitHandler: function (form) {
            if(!$("#party").is(":hidden")){
              if($('select[name=partyId]').val()=='') {
                SysMsg.success("请选择${_p_partyName}。");
                return;
              }
            }
            if(!$("#branch").is(":hidden")){
              if(!($('select[name=branchId]').val()>0)) {
                SysMsg.success("请选择支部。");
                return;
              }
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
              success:function(ret){
                if(ret.success){
                  SysMsg.success("提交成功。",function(){
                      $btn.button("success").addClass("btn-success");
                      $.hashchange();
                  });
                }else{
                  $btn.button('reset');
                }
              }
            });
          }
        });
      </script>
