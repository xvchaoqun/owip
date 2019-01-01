<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="back-btn">
  <a href="javascript:;" class="openView" data-open-by="page"
     data-url="${ctx}/m/cadre_compare?cadreIds[]=${param['cadreIds[]']}"><i class="fa fa-reply"></i> 返回</a>
</div>
<div style="overflow:auto;width:100%">
  <table id="fixedTable" class="table table-bordered table-center" width="auto" style="white-space:nowrap">
    <thead>
    <tr>
      <th>序号</th>
      <th>姓名</th>
      <th>单位及职务</th>
      <th>级别</th>
      <th>性别</th>
      <th>民族</th>
      <th>出生时间</th>
      <th>年龄</th>
      <th>最高学历学位</th>
      <th>所学专业</th>
      <th>参加工作时间</th>
      <th>党派</th>
      <th>党派加入时间</th>
      <th>专业技术职务</th>
      <th>现职务始任时间</th>
      <th>现职级年限</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="cadre" items="${cadres}" varStatus="vs">
    <tr>
      <td>${vs.count}</td>
      <td>
        <c:set var="backTo" value="${ctx}/m/cadre_compare_result?cadreIds[]=${param['cadreIds[]']}"/>
        <a href="javascript:;" class="openView" data-open-by="page" data-url="${ctx}/m/cadre_info?backTo=${cm:encodeURI(backTo)}&cadreId=${cadre.id}">
          ${cadre.realname}
        </a>
      </td>
      <td style="text-align: left">${cadre.title}</td>
      <td>${cm:getMetaType(cadre.typeId).name}</td>
      <td>${GENDER_MAP.get(cadre.gender)}</td>
      <td>${cadre.nation}</td>
      <td>${cm:formatDate(cadre.birth,'yyyy-MM-dd')}</td>
      <td>
        <c:if test="${not empty cadre.birth}">
        ${cm:intervalYearsUntilNow (cadre.birth)}岁
        </c:if>
      </td>
      <td>${cm:getMetaType(cadre.eduId).name}</td>
      <td>${cadre.major}</td>
      <td>${cm:formatDate(cadre.workTime, "yyyy.MM")}</td>
      <td>${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('partyName')}</td>
      <td>${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('growTime')}</td>
      <td>${cadre.proPost}</td>
      <td>${cm:formatDate(cadre.npWorkTime,'yyyy-MM-dd')}</td>
      <td>${cadre.adminLevelYear==0?'未满一年':cadre.adminLevelYear}</td>
    </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<%--<button class="closeView btn btn-success btn-block">
  <i class="ace-icon fa fa-backward"></i>
  返回
</button>--%>
<div class="alert alert-block alert-success" style="margin-top: 40px;">
  <i class="ace-icon fa fa-info-circle green"></i>  提示：打开手机的屏幕旋转，横屏后效果更佳
</div>
<style>
  .table>thead>tr>th:last-child{
    border-right-color: inherit;
  }
</style>
<script>
  $("#fixedTable").fixedTable({
    fixedCell:2,
    fixedType:"left",
  });

  window.addEventListener("orientationchange",function() {
    window.setTimeout(function() {
      var w = $("#body-content-view").width() - $("#fixedTable_fixed").width();
      $("#fixedTable").closest("div").width(w);
    }, 200);
  });
</script>
