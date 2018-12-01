<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-load-el="#div-content-view"
           data-url="${ctx}/unitTeamPlan?cls=1&unitTeamId=${param.unitTeamId}"><i
                class="fa fa-setting"></i> 干部配置方案</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-load-el="#div-content-view"
           data-url="${ctx}/unitTeamPlan?cls=2&unitTeamId=${param.unitTeamId}">
            <i class="fa fa-history"></i> 班子成员任职信息</a>
    </li>
    <%--<li class="<c:if test="${status==3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?status=3"><i class="fa fa-list"></i> 班子年度工作总结</a>
    </li>
    <li class="<c:if test="${status==4}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?status=4"><i class="fa fa-list"></i> 班子年度考核情况</a>
    </li>--%>
    <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative;margin-top: 3px;">
        <a href="javascript:"
           data-load-el="#div-content" data-hide-el="#div-content-view"
           class="hideView btn btn-xs btn-warning">
            <i class="ace-icon fa fa-reply"></i>
            返回</a>
    </div>
</ul>
