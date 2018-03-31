<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetTrainCourse?trainId=${param.trainId}&cls=3'><i
                class="fa fa-table"></i> 评估表
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetTrain_evaNote?trainId=${param.trainId}&detail=1'><i
                class="fa fa-bullhorn"></i> 评课说明
        </a>
    </li>
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetTrain_detail_eva/inspectors?trainId=${param.trainId}'><i
                class="fa fa-vcard"></i> 评课账号
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetTrain_evaCloseTime?trainId=${param.trainId}&detail=1'><i
                class="fa fa-clock-o"></i> 评课时间管理
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetTrainStat?trainId=${param.trainId}&detail=1'><i
                class="fa fa-bar-chart"></i> 测评结果
        </a>
    </li>
</ul>
<div class="col-xs-12" id="detail-item-content">
    <c:import url="${ctx}/cet/cetTrain_detail_eva/inspectors?trainId=${param.trainId}"/>
</div>
<div style="clear: both"></div>