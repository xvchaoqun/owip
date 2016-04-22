<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
    <div class="page-header">
      <h1>
        留学归国申请恢复组织生活
      </h1>
    </div>
    <form class="form-horizontal" id="modalForm" method="post" action="${ctx}/user/memberReturn">
      <input type="hidden" name="id" value="${memberReturn.id}">
      <div class="row">
        <div class="col-xs-6">
          <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"> ${(user.type==USER_TYPE_JZG)?"教工号":"学号"}</label>
            <div class="col-sm-9">
              <input readonly disabled type="text" value="${user.code}" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"> 请选择组织机构</label>
            <div class="col-sm-9">
              <select required name="classId" data-rel="select2" data-placeholder="请选择">
                <option></option>
                <c:forEach items="${partyClassMap}" var="cls">
                  <option value="${cls.key}">${cls.value.name}</option>
                </c:forEach>
              </select>
              <script>
                $("#modalForm select[name=classId]").val("${party.classId}")
              </script>
            </div>
          </div>
          <div class="form-group"  id="party" style="${empty party?'display: none;':''}" >
            <div class="col-sm-offset-3 col-sm-9">
              <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                      name="partyId" data-placeholder="请选择分党委">
                <option value="${party.id}">${party.name}</option>
              </select>
            </div>
          </div>
          <div class="form-group" id="branch" style="${empty branch?'display: none;':''}" >
            <div class="col-sm-offset-3 col-sm-9">
              <select data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                      name="branchId" data-placeholder="请选择党支部">
                <option value="${branch.id}">${branch.name}</option>
              </select>
            </div>
          </div>
          <script>
            register_class_party_branch_select($("#modalForm"), "party", "branch",
                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', '${party.id}', null,null,null,true)
          </script>
          <div class="form-group">
            <label class="col-xs-3 control-label">政治面貌</label>
            <div class="col-xs-6">
              <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="120">
                <option></option>
                <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
                  <option value="${_status.key}">${_status.value}</option>
                </c:forEach>
              </select>
              <script>
                $("#modalForm select[name=politicalStatus]").val(${memberReturn.politicalStatus});
              </script>
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6" style="width: 300px">
              <textarea class="form-control limited" name="remark" rows="5">${memberReturn.remark}</textarea>
            </div>
          </div>
        </div>
        <div class="col-xs-6">
          <div class="form-group">
            <label class="col-xs-6 control-label">提交恢复组织生活申请时间</label>
            <div class="col-xs-6">
              <div class="input-group">
                <input required class="form-control date-picker" name="_returnApplyTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.returnApplyTime,'yyyy-MM-dd')}" />
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-6 control-label">提交书面申请书时间</label>
            <div class="col-xs-6">
              <div class="input-group">
                <input required class="form-control date-picker" name="_applyTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.applyTime,'yyyy-MM-dd')}" />
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-6 control-label">确定为入党积极分子时间</label>
            <div class="col-xs-6">
              <div class="input-group">
                <input required class="form-control date-picker" name="_activeTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.activeTime,'yyyy-MM-dd')}" />
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-6 control-label">确定为发展对象时间</label>
            <div class="col-xs-6">
              <div class="input-group">
                <input required class="form-control date-picker" name="_candidateTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.candidateTime,'yyyy-MM-dd')}" />
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-6 control-label">入党时间</label>
            <div class="col-xs-6">
              <div class="input-group">
                <input required class="form-control date-picker" name="_growTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.growTime,'yyyy-MM-dd')}" />
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-6 control-label">转正时间</label>
            <div class="col-xs-6">
              <div class="input-group">
                <input required class="form-control date-picker" name="_positiveTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.positiveTime,'yyyy-MM-dd')}" />
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
              </div>
            </div>
          </div>
        </div>
      </div>


      <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
          <button class="btn btn-info" type="submit">
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
      <script>
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
