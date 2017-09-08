<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="menu.jsp"/>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable" style="margin: 10px 20px; width: 900px">
            <div class="space-4"></div>
            <a class="pull-right" style="margin-bottom: 10px" href="${ctx}/pcsOw_export?file=6&stage=${param.stage}" >
                <i class="fa fa-download"></i> 参加两委委员候选人推荐提名情况汇总表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</a>
        <table class="table table-bordered table-striped" data-offset-top="101">
            <thead>
            <tr>
                <th width="50">序号</th>
                <th>分党委名称</th>
                <th width="20" style="white-space: nowrap">党员数</th>
                <th width="120" style="white-space: nowrap">应参会党员数</th>
                <th width="120" style="white-space: nowrap">实参会党员数</th>
                <th width="120" style="white-space: nowrap">参会比率</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="memberCount" value="0"/>
            <c:set var="expectMemberCount" value="0"/>
            <c:set var="actualMemberCount" value="0"/>
            <c:forEach items="${records}" var="record" varStatus="vs">
                <c:set var="_memberCount" value="${partyMemberCountMap.get(record.id)}"/>
                <c:set var="memberCount" value="${memberCount+_memberCount}"/>
                <c:set var="expectMemberCount" value="${expectMemberCount+record.expectMemberCount}"/>
                <c:set var="actualMemberCount" value="${actualMemberCount+record.actualMemberCount}"/>
                <tr>
                    <td>${vs.count}</td>
                    <td style="text-align: left;white-space: nowrap;">${record.name}</td>
                    <td>${_memberCount}</td>
                    <td>${record.expectMemberCount}</td>
                    <td>${record.actualMemberCount}</td>
                    <td><fmt:formatNumber value="${record.actualMemberCount/record.expectMemberCount}" type="percent"
                                          pattern="#0.00%"/></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th colspan="2" style="text-align: right">汇总</th>
                <th>${memberCount}</th>
                <th>${expectMemberCount}</th>
                <th>${actualMemberCount}</th>
                <th><c:if test="${expectMemberCount>0}">
                    <fmt:formatNumber value="${actualMemberCount/expectMemberCount}" type="percent"
                                      pattern="#0.00%"/>
                </c:if></th>
            </tr>
            </tfoot>
        </table>
            </div>
    </div>
</div>
<style>
    .table tr th,
    .table tbody tr td{
        text-align: center;
    }
</style>
<script>
    stickheader();
</script>