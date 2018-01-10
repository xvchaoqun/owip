<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent">
  <div class="widget-header">
    <h4 class="widget-title lighter smaller">
      <a href="javascript:;" class="hideView btn btn-xs btn-success">
        <i class="ace-icon fa fa-backward"></i>
        返回</a>
    </h4>
    <div class="widget-toolbar no-border">
      <ul class="nav nav-tabs">
        <li class="active">
          <a>党费代缴</a>
        </li>
      </ul>
    </div>
  </div>
  <div class="widget-body">
    <div class="widget-main padding-4">
      <div class="tab-content padding-8">
        代缴账号：
        <select id="payUserId"  class="form-control" data-rel="select2-ajax" data-width="350"
                data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${member.partyId}&branchId=${member.branchId}&status=${MEMBER_STATUS_NORMAL}"
                data-placeholder="请输入账号或姓名或学工号">
          <option></option>
        </select>
        <button id="searchBtn" type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i> 查询
        </button>
        <div class="space-4"></div>
        <div id="payList" style="width: 600px;" class="center">

        </div>
      </div>
    </div>
  </div>
</div>
<style>
  #payList table th, #payList table td{
    text-align: center;
  }
</style>
<script>
  register_user_select($('#payUserId'));

  $("#searchBtn").click(function(){
    var userId = $.trim($("#payUserId").val());
    if(userId==''){
      $.tip({$target:$("#payUserId"), msg:"请选择"});
      return;
    }
    $.post("${ctx}/pmdMemberHelpPay_records",{userId:userId},function(ret){
      $("#payList").html(ret);
    })
  })
</script>