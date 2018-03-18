<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="_menuSelected"
           data-url='${ctx}/cet/cetTrain_detail_msg?trainId=${param.trainId}'><i
                class="fa fa-bullhorn"></i> 通知
        </a>
    </li>
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="_menuSelected"
           data-url='${ctx}/cet/cetTrainCourse?trainId=${param.trainId}&cls=2'><i
                class="fa fa-sign-in"></i> 签到
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="_menuSelected"
           data-url='${ctx}/cet/cetTrain_detail_stat?trainId=${param.trainId}'><i
                class="fa fa-bar-chart"></i> 统计
        </a>
    </li>
</ul>
<div class="col-xs-12" id="detail-item-content">
    <c:import url="${ctx}/cet/cetTrainCourse?trainId=${param.trainId}&cls=2"/>
</div>
<div style="clear: both"></div>