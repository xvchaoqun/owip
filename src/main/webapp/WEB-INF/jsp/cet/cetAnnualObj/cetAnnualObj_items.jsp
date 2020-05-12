<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<shiro:hasPermission name="cetAnnualObj:list">
<c:if test="${param.isValid==1}">
    <button class="downloadBtn btn btn-success btn-xs"
            data-url="${ctx}/cet/cetAnnualObj_exportDetails?objId=${param.objId}">
        <i class="fa fa-download"></i>
        导出学习培训明细表</button>
    <div class="space-4"></div>
</c:if>
</shiro:hasPermission>
<table class="table table-bordered table-striped table-center">
    <thead>
    <tr>
        <th width="200">培训时间</th>
        <th>培训班名称</th>
        <th>培训类型</th>
        <th>培训班主办方</th>
        <th width="100">完成学时数</th>
        <th width="100">是否已结业</th>
        <th width="100">完成百分比</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${trainRecords}" var="record">
        <tr>
            <td>
                ${cm:formatDate(record.startDate, "yyyy-MM-dd")}
                ~
                ${cm:formatDate(record.endDate, "yyyy-MM-dd")}
            </td>
            <td style="text-align: left">
                ${record.name}
            </td>
            <td>
                ${CET_TYPE_MAP.get(record.type)}
            </td>
            <td style="text-align: left">
                ${record.organizer}
            </td>
            <td>
                ${cm:trimToZero(record.period)}
            </td>
            <td>
                ${record.isGraduate?"已结业":"未结业"}
            </td>
            <td>
                <c:if test="${empty record.shouldFinishPeriod}">--</c:if>
                <c:if test="${not empty record.shouldFinishPeriod}">
                    <fmt:parseNumber value="${record.period}" var="period" />
                    <fmt:parseNumber value="${record.shouldFinishPeriod}" var="shouldFinishPeriod" />
                    <fmt:formatNumber value="${(period>shouldFinishPeriod?shouldFinishPeriod:period)/shouldFinishPeriod}" type="percent"
                                          pattern="#0.00%" var="progress"/>
                    <div class="progress progress-striped pos-rel" data-percent="${progress}">
                        <div class="progress-bar progress-bar-success" style="width:${progress};"></div>
                    </div>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

