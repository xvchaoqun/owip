<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
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
        <c:if test="${commonList.recNum>0}">
          <table class="table table-actived table-striped table-bordered table-hover">
            <thead>
            <tr>
              <th></th>
              <th>编号</th>
              <th>申请日期</th>
              <th>出行时间</th>
              <th>出发时间</th>
              <th>返回时间</th>
              <th>出行天数</th>
              <th>前往国家或地区</th>
              <th>事由</th>
              <th>审批情况</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${applySelfs}" var="applySelf" varStatus="st">
              <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
              <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
              <tr>
                <td class="center">
                  <label class="pos-rel">
                    <input type="checkbox" value="${applySelf.id}" class="ace">
                    <span class="lbl"></span>
                  </label>
                </td>
                <td>S${applySelf.id}</td>
                <td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
                <td>${APPLY_SELF_DATE_TYPE_MAP.get(applySelf.type)}</td>
                <td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
                <td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
                <td>${cm:getDayCountBetweenDate(applySelf.startDate,applySelf.endDate)}</td>
                <td>${applySelf.toCountry}</td>
                <td>${fn:replace(applySelf.reason, '+++', ',')}</td>
                <td>
                ${applySelf.isFinish?(cm:getMapValue(0, applySelf.approvalTdBeanMap).tdType==6?"通过":"未通过"):"未完成审批"}
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
          <c:if test="${!empty commonList && commonList.pageNum>1 }">
            <div class="row my_paginate_row">
              <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
              <div class="col-xs-6">
                <div class="my_paginate">
                  <ul class="pagination">
                    <wo:page commonList="${commonList}" uri="${ctx}/applySelf_page" target="#page-content" pageNum="5"
                             model="3"/>
                  </ul>
                </div>
              </div>
            </div>
          </c:if>
        </c:if>
        <c:if test="${commonList.recNum==0}">
          <div class="well well-lg center">
            <h4 class="green lighter">没有未出行的因私出国申请<c:if test="${param.auth!='admin'}">，<a href="${ctx}/user/applySelf">现在去申请</a></c:if></h4>
          </div>
        </c:if>
    </div><!-- /.widget-main -->
  </div><!-- /.widget-body -->
</div>
<div class="modal-footer center" style="margin-top: 20px">
  <input id="apply" class="btn btn-success" value="申请使用证件"/>
  <c:if test="${param.auth=='admin'}">
    <input type="button" class="closeView btn btn-default" value="返回"/>
  </c:if>
  <c:if test="${param.auth!='admin'}">
    <input data-url="${ctx}/user/passportDraw_select" class="openView  btn btn-default" value="返回"/>
  </c:if>

</div>
<script>
  $("input[type=checkbox]").click(function(){
      if($(this).prop("checked")){
        $("input[type=checkbox]").not(this).prop("checked", false);
      }
  });
$("#apply").click(function(){
    var $applyId = $("input[type=checkbox]:checked");
    var applyId = $applyId.val();
    if(applyId==undefined || applyId==''){
      SysMsg.info("请选择一个行程");
      return;
    }
    $("#item-content").load("${ctx}/user/passportDraw_self_select?auth=${param.auth}&cadreId=${param.cadreId}&applyId="+applyId);
});
</script>