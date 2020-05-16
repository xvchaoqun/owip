<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" data-target="#user-content">
                <li class="${to=='sysUser_base'?'active':''}">
                    <a href="javascript:;" data-url="${ctx}/sysUser_base?userId=${param.userId}">基本信息</a>
                </li>
                <li class="${to=='sysUser_info'?'active':''}">
                    <a href="javascript:;" data-url="${ctx}/sysUser_info?userId=${param.userId}">账号详情</a>
                </li>
                <c:if test="${uv.casUser}">
                <li class="${to=='sysUser_ext'?'active':''}">
                    <a href="javascript:;" data-url="${ctx}/sysUser_ext?userId=${param.userId}">校园账号信息</a>
                </li>
                </c:if>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8" id="user-content" style="overflow: hidden">
                <c:import url="/${to}"/>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->

<script>
	function _reload(){
		$("ul[data-target=\"#user-content\"] li.active a").click();
	}
</script>

