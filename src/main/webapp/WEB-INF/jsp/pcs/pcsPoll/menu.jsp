<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPoll?cls=1"><i class="fa fa-list"></i> 党支部${_member_need_vote?'投票':''}列表</a>
    </li>
    <li class="<c:if test="${cls==5}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPoll?cls=5"><i class="fa fa-trash-o"></i> 已作废<c:if test="${cancelCount>0}">（${cancelCount}）</c:if></a>
    </li>
    <shiro:hasPermission name="pcsPollResult:list">
        <li class="<c:if test="${cls==2}">active</c:if>">
            <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPoll?cls=2&stage=1">
                <i class="fa fa-drivers-license"></i> 候选人推荐人选汇总表（一下）</a>
        </li>
        <li class="<c:if test="${cls==3}">active</c:if>">
            <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPoll?cls=3&stage=2">
                <i class="fa fa-drivers-license"></i> 候选人推荐人选汇总表（二下）</a>
        </li>
        <li class="<c:if test="${cls==4}">active</c:if>">
            <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPoll?cls=4&stage=3">
                <i class="fa fa-drivers-license"></i> 候选人推荐人选汇总表（三下）</a>
        </li>
    </shiro:hasPermission>
</ul>
