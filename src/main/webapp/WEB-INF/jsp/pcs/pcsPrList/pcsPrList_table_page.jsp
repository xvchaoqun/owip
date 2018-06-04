<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<c:if test="${empty param.partyId}">
<jsp:include page="menu.jsp"/>
</c:if>
<div class="row">
  <div class="col-xs-12">
    <div class="tabbable" style="margin: 10px 20px; width: 900px">
      <div class="space-4"></div>
      <c:if test="${not empty param.partyId}">
        <a href="${ctx}/pcsPrOw_export?file=4&stage=${PCS_STAGE_THIRD}&partyId=${param.partyId}">
          <i class="fa fa-download"></i> 党代表数据统计表</a>
      </c:if>
<c:if test="${empty param.partyId}">
      <a href="${ctx}/pcsPrParty_export?file=4&stage=${PCS_STAGE_THIRD}">
        <i class="fa fa-download"></i> 党代表数据统计表</a>
  </c:if>
      <div class="space-4"></div>
      <table class="table table-bordered table-striped" data-offset-top="101">
        <thead>
        <tr>
          <th width="40" rowspan="2">序号</th>
          <th rowspan="2" colspan="2">程序</th>
          <th rowspan="2" width="80">代表总数</th>
          <th colspan="3">代表构成</th>
          <th colspan="3">其中</th>
        </tr>
        <tr>
          <th width="110">专业技术人员和干部代表</th>
          <th width="60">学生代表</th>
          <th width="70">离退休代表</th>
          <th width="60">妇女代表</th>
          <th width="80">少数民族代表</th>
          <th width="80">50岁以下代表</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td class="ltitle">1</td>
          <td colspan="2" class="ltitle">应选代表情况</td>
          <c:set var="expectCount"
                 value="${pcsPrAllocate.proCount +pcsPrAllocate.stuCount +pcsPrAllocate.retireCount }"/>
          <td>${expectCount}</td>
          <td>${pcsPrAllocate.proCount}</td>
          <td>${pcsPrAllocate.stuCount}</td>
          <td>${pcsPrAllocate.retireCount}</td>
          <td>${pcsPrAllocate.femaleCount}</td>
          <td>${pcsPrAllocate.minorityCount}</td>
          <td>${pcsPrAllocate.underFiftyCount}</td>
        </tr>
        <tr>
          <td rowspan="3" class="ltitle">2</td>
          <td rowspan="3" class="ltitle">党员大会选举结果</td>
          <td class="ltitle">选举结果</td>
          <c:set var="actualCount" value="${realPcsPrAllocate.proCount +realPcsPrAllocate.stuCount +realPcsPrAllocate.retireCount }"/>
          <td>${actualCount}</td>
          <td>${realPcsPrAllocate.proCount}</td>
          <td>${realPcsPrAllocate.stuCount}</td>
          <td>${realPcsPrAllocate.retireCount}</td>
          <td>${realPcsPrAllocate.femaleCount}</td>
          <td>${realPcsPrAllocate.minorityCount}</td>
          <td>${realPcsPrAllocate.underFiftyCount}</td>
        </tr>
        <tr>
          <td class="ltitle">差额</td>
          <td>${actualCount-expectCount}</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
        </tr>
        <tr>
          <td class="ltitle">差额比率
          </td>
          <td>
            <c:if test="${expectCount>0}">
              <fmt:formatNumber value="${(cm:toInt(actualCount) - expectCount)/expectCount}" type="percent"
                                pattern="#0.00%"/>
            </c:if>
          </td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<style>
  .table th, .table td {
    text-align: center;
  }

  .ltitle {
    text-align: center !important;
    vertical-align: middle !important;
    background-color: #f9f9f9 !important;
  }
</style>