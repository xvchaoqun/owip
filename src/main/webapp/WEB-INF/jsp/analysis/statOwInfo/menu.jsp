<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==0}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/statOwInfo?cls=0">
            <i class="fa fa-signal"></i> ${schoolName}研究生队伍党员信息分析</a>
    </li>
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/statOwInfo?cls=1">
            <i class="fa fa-signal"></i> 各二级党组织研究生队伍党员信息分析</a>
    </li>
    <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
        <shiro:hasPermission name="cadre:export">
            <button class="downloadBtn pull-left btn btn-success btn-sm"
                    data-url="${ctx}/statOwInfo?export=2&cls=${cls}"><i class="fa fa-download"></i>
                导出
            </button>
        </shiro:hasPermission>
    </div>
</ul>
