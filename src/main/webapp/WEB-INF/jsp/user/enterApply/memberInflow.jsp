<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div  style="padding-top: 50px;"></div>
<c:if test="${memberInflow.inflowStatus==MEMBER_INFLOW_STATUS_BACK}">
  <div class="alert alert-danger">
    <button type="button" class="close" data-dismiss="alert">
      <i class="ace-icon fa fa-times"></i>
    </button>
    <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if test="${not empty memberInflow.reason}">: ${memberInflow.reason}</c:if>
    <br>
  </div>
</c:if>
    <div class="page-header">
      <h1>
        流入党员申请
      </h1>
    </div>
<form class="form-horizontal" action="${ctx}/user/memberInflow" id="modalForm" method="post">
  <input type="hidden" name="id" value="${memberInflow.id}">
  <div class="row">
    <div class="col-xs-6">
      <div class="form-group">
        <label class="col-sm-4 control-label no-padding-right"> ${(user.type==USER_TYPE_JZG)?"教工号":"学号"}</label>
        <div class="col-sm-6 label-text">
          ${user.code}
        </div>
      </div>

      <div class="form-group">
        <label class="col-xs-4 control-label">分党委</label>
        <div class="col-xs-6">
          <select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                  name="partyId" data-placeholder="请选择">
            <option value="${party.id}">${party.name}</option>
          </select>
        </div>
      </div>
      <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
        <label class="col-xs-4 control-label">党支部</label>
        <div class="col-xs-6">
          <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
                  name="branchId" data-placeholder="请选择">
            <option value="${branch.id}">${branch.name}</option>
          </select>
        </div>
      </div>
      <script>
        register_party_branch_select($("#modalForm"), "branchDiv",
                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}", null,null, true);
      </script>
      <div class="form-group">
        <label class="col-xs-4 control-label">原职业</label>
        <div class="col-xs-6">
          <select required data-rel="select2" name="originalJob" data-placeholder="请选择">
            <option></option>
            <c:import url="/metaTypes?__code=mc_job"/>
          </select>
          <script type="text/javascript">
            $("#modalForm select[name=originalJob]").val("${memberInflow.originalJob}");
          </script>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-4 control-label">流入前所在省份</label>
        <div class="col-xs-6" id="loc_province_container1">
          <select required class="loc_province" name="province" style="width:120px;" data-placeholder="请选择">
          </select>
        </div>
      </div>

      <div class="form-group">
        <label class="col-xs-4 control-label">流入原因</label>
        <div class="col-xs-6">
          <textarea required class="form-control limited" type="text" name="flowReason" rows="5">${memberInflow.flowReason}</textarea>
        </div>
      </div>
    </div>
    <div class="col-xs-6">

      <div class="form-group">
        <label class="col-xs-4 control-label">流入时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input required class="form-control date-picker" name="_flowTime" type="text"
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberInflow.flowTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>

      <div class="form-group">
        <label class="col-xs-4 control-label">入党时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input required class="form-control date-picker" name="_growTime" type="text"
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberInflow.growTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-4 control-label">组织关系所在地</label>
        <div class="col-xs-6">
          <input required class="form-control" type="text" name="orLocation" value="${memberInflow.orLocation}">
        </div>
      </div>

      <div class="form-group">
        <label class="col-xs-8 control-label">是否持有《中国共产党流动党员活动证》</label>
        <div class="col-xs-3">
          <label>
            <input name="hasPapers" ${memberInflow.hasPapers?"checked":""}  type="checkbox" />
            <span class="lbl"></span>
          </label>
        </div>
      </div>
    </div></div>

  <div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
      <button class="btn btn-info" type="submit"id="submitBtn" data-loading-text="提交中..."  data-success-text="您的申请已提交成功" autocomplete="off">
        <i class="ace-icon fa fa-check bigger-110"></i>
        提交
      </button>

      &nbsp; &nbsp; &nbsp;
      <button class="closeView btn btn-default" type="button">
        <i class="ace-icon fa fa-undo bigger-110"></i>
        返回
      </button>
    </div>
  </div>
</form>
<style>
  #modalForm .row{
    width: 1100px;
  }
  #modalForm .input-group{
    width:150px;
  }
  #modalForm input.left-input{
    width: 260px;
  }
  #modalForm .right-div .col-xs-6{
    width:auto;
  }
  #modalForm .help-block{
    white-space: nowrap;
  }
</style>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
      <script>
        $("#modalForm :checkbox").bootstrapSwitch();
        showLocation("${memberInflow.province}",null, null, $("#loc_province_container1"));
        register_date($('.date-picker'), {endDate:'${today}'});
        $('#modalForm [data-rel="select2"]').select2();
        $("form").validate({
          submitHandler: function (form) {

            if(!$("#branchDiv").is(":hidden")){
              if($('select[name=branchId]').val()=='') {
                bootbox.alert("请选择支部。");
                return;
              }
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
              success:function(ret){
                if(ret.success){
                  bootbox.alert("提交成功。",function(){
                      $btn.button("success").addClass("btn-success");
                      location.reload();
                  });
                }else{
                  $btn.button('reset');
                }
              }
            });
          }
        });
      </script>
