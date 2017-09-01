<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable" style="margin: 10px 20px; width: 1100px">
    <div class="space-4"></div>
    <a href="${ctx}/pcsPrOw_export?file=7&stage=${param.stage}">
        <i class="fa fa-download"></i> 附件7. 全校党员参与推荐代表候选人情况统计表（组织部汇总）</a>
    <div class="space-4"></div>
    <table class="table table-bordered table-striped" data-offset-top="101">
        <thead class="multi">
        <tr>
            <th width="40" rowspan="2">序号</th>
            <th rowspan="2">分党委名称</th>
            <th colspan="2">所有党员总数</th>
            <th colspan="2">应参会党员数</th>
            <th colspan="2">实参会党员数</th>
            <th rowspan="2">参与比率</th>
        </tr>
        <tr>
            <th width="110">党员总数</th>
            <th width="60">正式党员数</th>
            <th width="70">党员数</th>
            <th width="60">正式党员数</th>
            <th width="80">党员数</th>
            <th width="80">正式党员数</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="memberCount" value="0"/>
        <c:set var="positiveCount" value="0"/>
        <c:set var="expectMemberCount" value="0"/>
        <c:set var="expectPositiveMemberCount" value="0"/>
        <c:set var="actualMemberCount" value="0"/>
        <c:set var="actualPositiveMemberCount" value="0"/>
        <c:forEach items="${records}" var="record" varStatus="vs">
            <c:set var="memberCount" value="${memberCount+record.memberCount}"/>
            <c:set var="positiveCount" value="${positiveCount+record.positiveCount}"/>
            <c:set var="expectMemberCount" value="${expectMemberCount+record.expectMemberCount}"/>
            <c:set var="expectPositiveMemberCount" value="${expectPositiveMemberCount+record.expectPositiveMemberCount}"/>
            <c:set var="actualMemberCount" value="${actualMemberCount+record.actualMemberCount}"/>
            <c:set var="actualPositiveMemberCount" value="${actualPositiveMemberCount+record.actualPositiveMemberCount}"/>
            <tr>
                <td>${vs.count}</td>
                <td style="text-align: left">${record.name}</td>
                <td>${record.memberCount}</td>
                <td>${record.positiveCount}</td>
                <td>${record.expectMemberCount}</td>
                <td>${record.expectPositiveMemberCount}</td>
                <td>${record.actualMemberCount}</td>
                <td>${record.actualPositiveMemberCount}</td>
                <td><fmt:formatNumber value="${record.actualMemberCount/record.expectMemberCount}" type="percent"
                                      pattern="#0.00%"/></td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <th colspan="2" style="text-align: right">合计</th>
            <th>${memberCount}</th>
            <th>${positiveCount}</th>
            <th>${expectMemberCount}</th>
            <th>${expectPositiveMemberCount}</th>
            <th>${actualMemberCount}</th>
            <th>${actualPositiveMemberCount}</th>
            <th>
                <c:if test="${expectMemberCount>0}">
                    <fmt:formatNumber value="${actualMemberCount/expectMemberCount}" type="percent"
                                      pattern="#0.00%"/>
                </c:if>
            </th>
        </tr>
        </tfoot>
    </table>
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
<script>
    stickheader();
</script>