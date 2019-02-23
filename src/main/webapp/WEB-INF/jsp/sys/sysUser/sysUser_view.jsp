<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body">

    <div class="widget-box transparent" id="view-box">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
            </h4>
            <div class="widget-toolbar no-border">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="javascript:;" data-url="${ctx}/sysUser_info?userId=${param.userId}">账号详情</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-url="${ctx}/sysUser_ext?userId=${param.userId}">账号同步信息</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main padding-4">
                <div class="tab-content padding-8" id="body-content-view">
                    <c:import url="/sysUser_info"/>
                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
</div>
<script>
	function _reload(){
		$("#view-box .nav-tabs li.active a").click();
	}
</script>

