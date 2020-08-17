<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsOw?cls=1&stage=${param.stage}">
            <i class="fa fa-envelope-open"></i> 各${_p_partyName}推荐情况</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsOw?cls=2&stage=${param.stage}">
            <i class="fa fa-area-chart"></i> 两委委员推荐汇总</a>
    </li>
    <li class="<c:if test="${cls==3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsOw?cls=3&stage=${param.stage}">
            <i class="fa fa-line-chart"></i> 党员参与推荐情况</a>
    </li>

    <li class="<c:if test="${cls==4}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsOw?cls=4&stage=${param.stage}">
            <i class="fa fa-level-down"></i>
            <c:if test="${param.stage!=PCS_STAGE_THIRD}">
                “${param.stage==PCS_STAGE_FIRST?"二下":""}
                ${param.stage==PCS_STAGE_SECOND?"三下":""}”名单
            </c:if>
            <c:if test="${param.stage==PCS_STAGE_THIRD}">
                候选人预备人选
            </c:if>
        </a>
    </li>
</ul>