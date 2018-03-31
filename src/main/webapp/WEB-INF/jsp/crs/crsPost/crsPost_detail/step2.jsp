<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=1'><i
                class="fa fa-list"></i> 应聘名单
                <span class="badge badge-primary">${count[1]}</span>
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=2'><i
                class="fa fa-check-circle"></i> 资格审核（通过）
            <span class="badge badge-primary">${count[2]}</span>
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=3'><i
                class="fa fa-times-circle"></i> 资格审核（未通过）
            <span class="badge badge-primary">${count[3]}</span>
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=4'><i
                class="fa fa-sign-out"></i> 退出
            <span class="badge badge-primary">${count[4]}</span>
        </a>
    </li>
</ul>
<div class="col-xs-12" id="step-item-content">
    <c:import url="${ctx}/crsApplicant?postId=${param.id}&cls=1"/>
</div>
<div style="clear: both"></div>