<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-body-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetProjectObj?projectId=${param.projectId}&cls=1'><i
                class="fa fa-users"></i> 培训对象
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-body-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetProjectObj?projectId=${param.projectId}&cls=2'><i
                class="fa fa-power-off"></i> 退出培训人员
        </a>
    </li>
</ul>
<div class="col-xs-12" id="detail-body-content-view">
    <c:import url="${ctx}/cet/cetProjectObj"/>
</div>
<div style="clear: both"></div>