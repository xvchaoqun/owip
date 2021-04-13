<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<jsp:include page="menu.jsp"/>

<div class="row">
    <div class="col-xs-12">
        <div class="tabbable" style="margin: 10px 20px; width: 900px">
            <div class="space-4"></div>

            <a href="${ctx}/pcs/pcsPrParty_export?file=4&stage=${param.stage}">
                <i class="fa fa-download"></i> ${_p_partyName}酝酿党员代表大会代表候选人<%--${param.stage==PCS_STAGE_FIRST?'初步':'预备'}--%>推荐人选统计表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</a>
            <div class="space-4"></div>
            <table class="table table-bordered table-striped" data-offset-top="132">
                <thead>
                <tr>
                    <th width="40" rowspan="2">序号</th>
                    <th rowspan="2" colspan="2">程序</th>
                    <th rowspan="2" width="80">代表总数</th>
                    <th colspan="${prTypeNum}">代表构成</th>
                    <th colspan="3">其中</th>
                </tr>
                <tr>
                    <c:forEach items="${prTypes}" var="prType">
                        <th width="70">${prType.value.name}</th>
                    </c:forEach>
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
                           value="${pcsPrAllocate.totalPrCount}"/>
                    <td>${expectCount}</td>
                      <c:forEach items="${prTypes}" var="prType">
                          <td>
                              ${pcsPrAllocate.prCountMap.get(prType.key)}
                          </td>
                      </c:forEach>

                    <td>${pcsPrAllocate.femaleCount}</td>
                    <td>${pcsPrAllocate.minorityCount}</td>
                    <td>${pcsPrAllocate.underFiftyCount}</td>
                </tr>
                <tr>
                    <td rowspan="3" class="ltitle">2</td>
                    <td rowspan="3" class="ltitle">“一上”推荐</td>
                    <td class="ltitle">推荐结果</td>
                    <c:set var="actualCount" value="${realPcsPrAllocate.totalPrCount}"/>
                    <td>${actualCount}</td>
                      <c:forEach items="${prTypes}" var="prType">
                          <td>
                              ${realPcsPrAllocate.prCountMap.get(prType.key)}
                          </td>
                      </c:forEach>

                    <td>${realPcsPrAllocate.femaleCount}</td>
                    <td>${realPcsPrAllocate.minorityCount}</td>
                    <td>${realPcsPrAllocate.underFiftyCount}</td>
                </tr>
                <tr>
                    <td class="ltitle">差额</td>
                    <td>${actualCount-expectCount}</td>
                    <c:forEach items="${prTypes}" var="prType">
                          <td>-</td>
                      </c:forEach>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                </tr>
                <tr>
                    <td class="ltitle">差额
                        比率
                    </td>
                    <td>
                        <c:if test="${expectCount>0}">
                            <fmt:formatNumber value="${(cm:toInt(actualCount) - expectCount)/expectCount}" type="percent"
                                              pattern="#0.0%"/>
                        </c:if>
                    </td>
                    <c:forEach items="${prTypes}" var="prType">
                    <td>-</td>
                  </c:forEach>
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