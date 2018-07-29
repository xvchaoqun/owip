<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="approvalAuth_menu.jsp"/>
<%@ include file="../constants.jsp" %>
<div class="row">
  <div class="col-xs-12">
    <div class="tabbable" style="margin: 10px 20px; width: 1200px">
      <div class="space-4"></div>

      <table class="table table-bordered table-striped table-center" data-offset-top="132">
        <thead>
        <tr>
          <th width="50">序号</th>
          <th nowrap>申请人</th>
          <th nowrap>工号</th>
          <th width="100">所在单位</th>
          <th>现任职务</th>
          <c:forEach items="${approverTypeMap}" var="entity">
          <th nowrap>${entity.value.name}</th>
          </c:forEach>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cadreApproverListMap}" var="cadreApproverListMap" varStatus="vs">
          <c:set var="cadre" value="${cm:getCadreById(cadreApproverListMap.key)}"/>
          <tr>
            <td>${vs.count}</td>
            <td>${cadre.realname}</td>
            <td>${cadre.code}</td>
            <td style="text-align: left; white-space: nowrap">${cadre.unit.name}</td>
            <td style="text-align: left">${cadre.title}</td>
            <c:forEach items="${cadreApproverListMap.value}" var="approverListMap">
              <td class="<c:if test="${approverListMap.value!=null && empty approverListMap.value}">empty</c:if>">
                <c:if test="${approverListMap.value==null}">--</c:if>
                <c:forEach items="${approverListMap.value}" var="approver" varStatus="vs">
                  ${approver.realname}
                  ${!vs.last?'、':''}
                </c:forEach>
              </td>
            </c:forEach>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
<style>
  td.empty{
    background-color: red;
  }
  .table tbody tr:hover td.empty{
    background-color: red!important;
  }
</style>
<script>
  stickheader();
</script>