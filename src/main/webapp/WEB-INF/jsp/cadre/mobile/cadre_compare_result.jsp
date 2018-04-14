<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div style="overflow:auto;width:100%">
  <table  class="table table-bordered" width="auto" style="white-space:nowrap">
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
      <td>${cadre.realname}</td>
      <td>${cadre.title}</td>
      <td>${adminLevelMap[cadre.typeId].name}</td>
      <td>${GENDER_MAP.get(cadre.gender)}</td>
      <td>${cadre.nation}</td>
      <td>${cm:formatDate(cadre.birth,'yyyy-MM-dd')}</td>
      <td>
        <c:if test="${not empty cadre.birth}">
        ${cm:intervalYearsUntilNow (cadre.birth)}岁
        </c:if>
      </td>
      <td>${eduTypeMap.get(cadre.eduId).name}</td>
      <td>${cadre.major}</td>
      <td>${cm:formatDate(cadre.workTime, "yyyy-MM-dd")}</td>
      <td>${cadre.cadreDpType>0?democraticPartyMap.get(cadre.dpTypeId).name:(cadre.cadreDpType==0)?'中共党员':''}</td>
      <td>${cm:formatDate(cadre.cadreGrowTime,'yyyy-MM-dd')}</td>
      <td>${cadre.proPost}</td>
      <td>${cm:formatDate(cadre.npWorkTime,'yyyy-MM-dd')}</td>
      <td>${cadre.adminLevelYear==0?'未满一年':cadre.adminLevelYear}</td>
    </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<button class="closeView btn btn-success btn-block">
  <i class="ace-icon fa fa-backward"></i>
  返回
</button>
