<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var yearPeriodMap = ${cm:toJSONObject(yearPeriodMap)}
</script>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetTrainee?trainId=${param.trainId}&cls=1'><i
                class="fa fa-users"></i> 可选课人员
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetTrainee?trainId=${param.trainId}&cls=2'><i
                class="fa fa-check-circle"></i> 已选课人员
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-item-content" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetTrainee?trainId=${param.trainId}&cls=3'><i
                class="fa fa-power-off"></i> 退班人员
        </a>
    </li>
</ul>
<div class="col-xs-12" id="detail-item-content">
    <c:import url="${ctx}/cet/cetTrainee?trainId=${param.trainId}&cls=1"/>
</div>
<div style="clear: both"></div>