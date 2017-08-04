<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="_menuSelected"
           data-url='${ctx}/crsPost_detail/step2_time?id=${param.id}'><i
                class="fa fa-clock-o"></i> 报名时间管理
        </a>
    </li>
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="_menuSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=1'><i
                class="fa fa-info-circle"></i> 信息审核（待审核）
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="_menuSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=2'><i
                class="fa fa-check-circle"></i> 信息审核（通过）
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="_menuSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=3'><i
                class="fa fa-times-circle"></i> 信息审核（未通过）
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="_menuSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=4'><i
                class="fa fa-hourglass-1"></i> 资格审核（待审核）
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="_menuSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=5'><i
                class="fa fa-check-circle"></i> 资格审核（通过）
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#step-item-content" data-callback="_menuSelected"
           data-url='${ctx}/crsApplicant?postId=${param.id}&cls=6'><i
                class="fa fa-times-circle"></i> 资格审核（未通过）
        </a>
    </li>
</ul>
<div class="col-xs-12" id="step-item-content">
    <c:import url="${ctx}/crsApplicant?postId=${param.id}&cls=1"/>
</div>