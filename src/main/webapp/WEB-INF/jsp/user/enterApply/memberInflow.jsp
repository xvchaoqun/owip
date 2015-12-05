<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>

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
        <label class="col-xs-4 control-label">用户</label>
        <div class="col-xs-6">
          <select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
                  name="userId" data-placeholder="请输入账号或姓名或学工号">
            <option value="${sysUser.id}">${sysUser.realname}</option>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-4 control-label">类别</label>
        <div class="col-xs-6">
          <select required data-rel="select2" name="type" data-placeholder="请选择类别">
            <option></option>
            <c:forEach items="${MEMBER_TYPE_MAP}" var="_type">
              <option value="${_type.key}">${_type.value}</option>
            </c:forEach>
          </select>
          <script>
            $("#modalForm select[name=type]").val(${memberInflow.type});
          </script>
        </div>
      </div>

      <div class="form-group">
        <label class="col-xs-4 control-label">分党委</label>
        <div class="col-xs-6">
          <select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                  name="partyId" data-placeholder="请选择">
            <option value="${party.id}">${party.name}</option>
          </select>
        </div>
      </div>
      <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
        <label class="col-xs-4 control-label">党支部</label>
        <div class="col-xs-6">
          <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                  name="branchId" data-placeholder="请选择">
            <option value="${branch.id}">${branch.name}</option>
          </select>
        </div>
      </div>
      <script>
        register_party_branch_select($("#modalForm"), "branchDiv",
                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
      </script>
      <div class="form-group">
        <label class="col-xs-4 control-label">原职业</label>
        <div class="col-xs-6">
          <select required data-rel="select2" name="originalJob" data-placeholder="请选择">
            <option></option>
            <c:import url="/metaTypes?__code=mc_job"/>
          </select>
          <script type="text/javascript">
            $("#modal form select[name=originalJob]").val(${memberInflow.originalJob});
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
          <textarea required class="form-control limited" type="text" name="reason" rows="5">${memberInflow.reason}</textarea>
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
        <label class="col-xs-4 control-label">转出单位</label>
        <div class="col-xs-6">
          <input required class="form-control" type="text" name="outflowUnit" value="${memberInflow.outflowUnit}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-4 control-label">转出地</label>
        <div class="col-xs-6" id="loc_province_container2">
          <select required class="loc_province" name="outflowLocation" style="width:120px;" data-placeholder="请选择">
          </select>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-4 control-label">转出时间</label>
        <div class="col-xs-6">
          <div class="input-group">
            <input required class="form-control date-picker" name="_outflowTime" type="text"
                   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberInflow.outflowTime,'yyyy-MM-dd')}" />
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-4 control-label">是否持有《中国共产党流动党员活动证》</label>
        <div class="col-xs-6">
          <label>
            <input name="hasPapers" ${memberInflow.hasPapers?"checked":""} class="ace ace-switch ace-switch-5" type="checkbox" />
            <span class="lbl"></span>
          </label>
        </div>
      </div>
    </div></div>

  <div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
      <button class="btn btn-info" type="submit">
        <i class="ace-icon fa fa-check bigger-110"></i>
        提交
      </button>

      &nbsp; &nbsp; &nbsp;
      <button class="closeView btn" type="button">
        <i class="ace-icon fa fa-undo bigger-110"></i>
        返回
      </button>
    </div>
  </div>
</form>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
      <script>
        showLocation("${memberInflow.province}",null, null, $("#loc_province_container1"));
        showLocation("${memberInflow.outflowLocation}",null, null, $("#loc_province_container2"));
        register_user_select($('select[name=userId]'));
        register_date($('.date-picker'));
        $('#modalForm [data-rel="select2"]').select2();
        $("form").validate({
          submitHandler: function (form) {
            if(!$("#party").is(":hidden")){
              if($('select[name=partyId]').val()=='') {
                bootbox.alert("请选择分党委。");
                return;
              }
            }
            if(!$("#branch").is(":hidden")){
              if($('select[name=branchId]').val()=='') {
                bootbox.alert("请选择支部。");
                return;
              }
            }
            $(form).ajaxSubmit({
              success:function(ret){
                if(ret.success){
                  bootbox.alert("提交成功。",function(){
                      location.reload();
                  });
                }
              }
            });
          }
        });
      </script>
