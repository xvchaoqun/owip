<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<div class="row">
  <div class="col-xs-12">
    <!-- PAGE CONTENT BEGINS -->
    <div class="page-header">
      <h1>
        ${empty memberApply?"申请入党":"当前状态"}
 </h1>
    </div>
    <c:if test="${empty memberApply}">
    <form class="form-horizontal" method="post" action="${ctx}/apply">
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> ${(user.type==USER_TYPE_JZG)?"教工号":"学号"}</label>
        <div class="col-sm-9">
          <input readonly disabled type="text" value="${user.code}" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> 提交申请书时间</label>
        <div class="col-sm-2 col-xs-4">
          <div class="input-group">
            <input required class="form-control date-picker" name="_applyTime" type="text"
                   data-date-format="yyyy-mm-dd"/>
            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> 选择党支部</label>
        <div class="col-sm-9">
          <select required name="classId" data-rel="select2" data-placeholder="请选择">
            <option></option>
            <c:forEach items="${partyClassMap}" var="cls">
              <option value="${cls.key}">${cls.value.name}</option>
            </c:forEach>
          </select>
          </div>
        </div>

        <div class="form-group"  id="party" style="display: none;" >
            <div class="col-sm-offset-3 col-sm-9">
          <select data-rel="select2-ajax" data-ajax--url="${ctx}/party_selects"
                  name="partyId" data-placeholder="请输入分党委名称">
            <option></option>
          </select>
          </div>
         </div>
          <div class="form-group" id="branch" style="display: none;" >
            <div class="col-sm-offset-3 col-sm-9">
          <select data-rel="select2-ajax" data-ajax--url="${ctx}/branch_selects"
                  name="branchId" data-placeholder="请输入支部名称">
            <option></option>
          </select>
              </div>
            </div>

      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> 备注</label>
        <div class="col-sm-9">
          <textarea name="remark"  class="col-xs-10 col-sm-5" rows="10"></textarea>
        </div>
      </div>

      <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
          <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
          </button>

          &nbsp; &nbsp; &nbsp;
          <button class="btn" type="reset">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            重置
          </button>
        </div>
      </div>
      </form>
      <script>
        $('[data-rel="select2"]').select2({width:200}).on("change", function () {
            if($(this).val()>0){
              $("#party").show();
            }else{
              $('select[name=partyId]').val(null).trigger("change");
              $('select[name=branchId]').val(null).trigger("change");
              $("#party, #branch").hide();
            }
        });

        $('[data-rel="select2-ajax"]').select2({
          width:400,
          ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
              return {
                searchStr: params.term,
                pageSize: 10,
                pageNo: params.page,
                classId: $('select[name=classId]').val()
              };
            },
            processResults: function (data, params) {
              params.page = params.page || 1;
              return {results: data.options,  pagination: {
                more: (params.page * 10) < data.totalCount
              }};
            },
            cache: true
          }
        });

        $('select[name=partyId]').on("change", function () {

          if($(this).val()>0 && $('select[name=classId]').val()!='${cm:getMetaTypeByCode("mt_direct_branch").id}'){
            $("#branch").show();
          }else{
            $('select[name=branchId]').val(null).trigger("change");
            $("#branch").hide();
          }
        });

        $('.date-picker').datepicker({
          language:"zh-CN",
          autoclose: true,
          todayHighlight: true
        })
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
    </c:if>
  <c:if test="${not empty memberApply}">
    <ul class="steps">
      <li data-step="1" class="active">
        <span class="step">0</span>
        <span class="title">申请已提交</span>
      </li>
      <c:if test="${memberApply.stage==-1}">
      <li data-step="2" class="active">
        <span class="step">1</span>
        <span class="title">未通过申请</span>
      </li>
      </c:if>

      <li data-step="1" <c:if test="${memberApply.stage>0}">class="active"</c:if>>
        <span class="step">1</span>
        <span class="title">申请已通过</span>
      </li>
      <li data-step="2" <c:if test="${memberApply.stage>1}">class="active"</c:if>>
        <span class="step">2</span>
        <span class="title">入党积极分子</span>
      </li>

      <li data-step="3" <c:if test="${memberApply.stage>2}">class="active"</c:if>>
        <span class="step">3</span>
        <span class="title">成为发展对象</span>
      </li>

      <li data-step="4" <c:if test="${memberApply.stage>3}">class="active"</c:if>>
        <span class="step">4</span>
        <span class="title">例入发展计划</span>
      </li>
      <li data-step="5" <c:if test="${memberApply.stage>4}">class="active"</c:if>>
        <span class="step">5</span>
        <span class="title">领取志愿书</span>
      </li>
      <li data-step="6" <c:if test="${memberApply.stage>5}">class="active"</c:if>>
        <span class="step">6</span>
        <span class="title">预备党员</span>
      </li>
      </c:if>
    </ul>

    </div>
  </div>
