<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsPost_detail/step3_meeting?id=${param.id}'><i
                class="fa fa-bullhorn"></i> 通知和公告
        </a>
    </li>
    <%--<li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsPost_detail/step3_meeting?id=${param.id}'><i
                class="fa fa-calendar-o"></i> 纪监办函询
        </a>
    </li>--%>
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsPostExpert?id=${param.id}'><i
                class="fa fa-calendar-o"></i> 专家组
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsPost_detail/step3_material?id=${param.id}'><i
                class="fa fa-files-o"></i> 会议材料
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsPostFile?postId=${param.id}'><i
                class="fa fa-tasks"></i> 会议记录
        </a>
    </li>
</ul>
<div class="col-xs-12" id="step-body-content-view">
    <c:import url="${ctx}/crsPostExpert?id=${param.id}"/>
</div>
<div style="clear: both"></div>