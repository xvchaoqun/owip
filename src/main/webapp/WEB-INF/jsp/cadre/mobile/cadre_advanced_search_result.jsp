<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="back-btn">
  <a href="javascript:;" class="openView" data-open-by="page"
     data-url="${ctx}/m/cadre_advanced_search?${commonList.searchStr}"><i class="fa fa-reply"></i> 返回</a>
</div>
<div style="overflow:auto;width:100%">
  <table id="fixedTable" class="table table-bordered table-center" width="auto" style="white-space:nowrap">
    <thead>
    <tr>
      <th>工号</th>
      <th>姓名</th>
      <th>单位及职务</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="cadre" items="${cadres}" varStatus="vs">
    <tr>
      <td>${cadre.code}</td>
      <td>
        <c:set var="backTo" value="${ctx}/m/cadre_advanced_search_result?${commonList.searchStr}"/>
        <a href="javascript:;" class="openView" data-open-by="page" data-url="${ctx}/m/cadre_info?backTo=${cm:encodeURI(backTo)}&cadreId=${cadre.id}">
          ${cadre.realname}
        </a>
      </td>
      <td style="text-align: left">${cadre.title}</td>
    </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<div class="message-footer clearfix">
  <wo:page commonList="${commonList}" uri="${ctx}/m/cadre_advanced_search_result"
           target="#body-content-view" model="4"/>
</div>
<style>
  .table>thead>tr>th:last-child{
    border-right-color: inherit;
  }
</style>
