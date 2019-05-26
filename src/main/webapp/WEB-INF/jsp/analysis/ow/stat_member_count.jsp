<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>

<div class="col-sm-4" style="width:350px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-signal"></i>
                党员基本情况统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <div style="min-height: 130px;">
                    <div class="stat-row">党员总数：<span class="count">${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE) + statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)}</span></div>
                    <div class="stat-row">正式党员人数：<span class="count">${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE)}</span><br/>（其中教职工<span class="count">${statPositiveMap.get(MEMBER_TYPE_TEACHER)}</span>人，学生<span class="count">${statPositiveMap.get(MEMBER_TYPE_STUDENT)}</span>人）</div>
                    <div class="stat-row">预备党员人数：<span class="count">${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)}</span><br/>（其中教职工<span class="count">${statGrowMap.get(MEMBER_TYPE_TEACHER)}</span>人，学生<span class="count">${statGrowMap.get(MEMBER_TYPE_STUDENT)}</span>人）</div>
                </div>
            </div>
        </div>
    </div>
</div>