<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/statOwInfo?cls=1">
            <i class="fa fa-th"></i> ${schoolName}研究生队伍党员信息分析</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/statOwInfo?cls=2">
            <i class="fa fa-th"></i> 各二级党组织研究生队伍党员信息分析</a>
    </li>
    <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
        <button  class="downloadBtn btn btn-success btn-sm"
                data-url="${ctx}/statOwInfo?export=2&cls=${cls}"><i class="fa fa-download"></i>
            导出
        </button>
        <button class="runBtn btn btn-warning btn-sm" data-url="${ctx}/flushStatOwInfoCache" data-title="刷新获取最新统计数据"
                data-callback="flushReload"
                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 刷新中，请稍后">
            <i class="fa fa-refresh"></i> 刷新数据
        </button>
        （数据统计时间：${cacheTime}）
    </div>
</ul>
<script>
    function flushReload(){
        $("#contentDiv li.active .loadPage").click();
    }
</script>
