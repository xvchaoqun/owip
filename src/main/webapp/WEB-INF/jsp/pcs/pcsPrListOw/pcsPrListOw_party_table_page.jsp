<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="menu.jsp"/>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable" style="margin: 10px 20px; width: 900px">
            <div class="space-4"></div>
            <a class="pull-right" style="margin-bottom: 10px" href="${ctx}/pcsPrListOw_export?file=5" >
                <i class="fa fa-download"></i> 党员参与情况表</a>
            <table class="table table-bordered table-striped" data-offset-top="132">
                <thead>
                <tr>
                    <th width="50">序号</th>
                    <th>${_p_partyName}名称</th>
                    <th width="80">所有正式党员总数</th>
                    <th width="80">应参会正式党员数</th>
                    <th width="80">实参会正式党员数</th>
                    <th width="80" style="white-space: nowrap">参会比率</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="positiveCount" value="0"/>
                <c:set var="expectPositiveMemberCount" value="0"/>
                <c:set var="actualPositiveMemberCount" value="0"/>
                <c:forEach items="${records}" var="record" varStatus="vs">
                    <c:set var="positiveCount" value="${positiveCount+record.positiveCount}"/>
                    <c:set var="expectPositiveMemberCount" value="${expectPositiveMemberCount+record.expectPositiveMemberCount}"/>
                    <c:set var="actualPositiveMemberCount" value="${actualPositiveMemberCount+record.actualPositiveMemberCount}"/>
                    <tr>
                        <td>${vs.count}</td>
                        <td style="text-align: left;white-space: nowrap;">${record.name}</td>
                        <td>${record.positiveCount}</td>
                        <td>${record.expectPositiveMemberCount}</td>
                        <td>${record.actualPositiveMemberCount}</td>
                        <td><fmt:formatNumber value="${record.actualPositiveMemberCount/record.expectPositiveMemberCount}" type="percent"
                                              pattern="#0.00%"/></td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="2" style="text-align: right">汇总</th>
                    <th>${positiveCount}</th>
                    <th>${expectPositiveMemberCount}</th>
                    <th>${actualPositiveMemberCount}</th>
                    <th><c:if test="${expectPositiveMemberCount>0}">
                        <fmt:formatNumber value="${actualPositiveMemberCount/expectPositiveMemberCount}" type="percent"
                                          pattern="#0.00%"/>
                    </c:if></th>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>
<style>
    .table th,
    .table td{
        text-align: center;
    }
</style>
<script>
    stickheader();
</script>