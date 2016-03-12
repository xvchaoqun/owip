<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/2/28
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div>
  <h3>选择需要的证件并申请办理签注</h3>
</div>
<div class="modal-body">
  <div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
      <h4 class="widget-title lighter">
        <i class="ace-icon fa fa-star orange"></i>
        因私出国（境）行程
      </h4>
      <div class="widget-toolbar">
        <a href="#" data-action="collapse">
          <i class="ace-icon fa fa-chevron-up"></i>
        </a>
      </div>
    </div>
    <div class="widget-body" style="display: block;">
      <div class="widget-main no-padding">
        <table class="table table-striped table-bordered table-hover">
          <thead>
          <tr>
            <th>编号</th>
            <th>申请日期</th>
            <th>出行时间</th>
            <th>出发时间</th>
            <th>返回时间</th>
            <th>出行天数</th>
            <th>前往国家或地区</th>
            <th>事由</th>
            <th>组织部初审</th>
            <th>组织部终审</th>
          </tr>
          </thead>
          <tbody>
          <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
          <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
          <tr>

            <td>S${applySelf.id}</td>
            <td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
            <td>${APPLY_SELF_DATE_TYPE_MAP.get(applySelf.type)}</td>
            <td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
            <td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
            <td></td>
            <td>${applySelf.toCountry}</td>
            <td>${fn:replace(applySelf.reason, '+++', ',')}</td>
            <td></td>
            <td></td>
          </tr>
          </tbody>
        </table>
      </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
  </div>
<div class="well center" style="margin-top: 20px; font-size: 20px">
  <div class="row" style="padding-left: 100px">
    <c:if test="${fn:length(passports)==0}">
      您还没有提交证件
    </c:if>
    <c:forEach items="${passports}" var="passport">
      <c:set var="passportType" value="${cm:getMetaType('mc_passport_type', passport.classId)}"/>
      <div style="float: left; margin-right: 40px;">
        <input type="checkbox" class="big" name="passportId" value="${passport.id}"
               data-sign="${passportType.code != 'mt_passport_normal'}" class="big"> ${passportType.name}
        <c:if test="${passportType.code != 'mt_passport_normal'}">
          <span class="label" style="vertical-align: 4px; margin-left: 10px">未申请办理签注</span>
        </c:if>
      </div>
    </c:forEach>
  </div>
</div>
</div>
<div class="modal-footer center">
<c:if test="${fn:length(passports)>0}">
  <input id="next" class="btn btn-success" value="确认"/>
  <input data-url="${ctx}/user/passportDraw_self" class="openView btn btn-info" value="返回选择行程"/>
  </c:if>
  <input  class="closeView btn btn-default" value="取消申请"/>
</div>
<script>
  $("input[type=checkbox]").click(function(){
    if($(this).prop("checked")){
      $("input[type=checkbox]").not(this).prop("checked", false);
    }
  });
  $("#next").click(function(){
    var $passportId = $('input[name=passportId]:checked');
    var passportId = $passportId.val();
    if(passportId==undefined || passportId==''){
      SysMsg.info("请选择需要的证件");
      return;
    }
    if($passportId.data("sign"))
        $("#item-content").load("${ctx}/user/passportDraw_self_sign?type=new&applyId=${param.applyId}&passportId="+passportId);
    else
      $("#item-content").load("${ctx}/user/passportDraw_self_confirm?applyId=${param.applyId}&passportId="+passportId);
  });
  $('form [data-rel="select2"]').select2();
</script>
