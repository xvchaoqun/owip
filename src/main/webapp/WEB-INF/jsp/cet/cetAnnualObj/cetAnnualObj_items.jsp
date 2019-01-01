<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<c:if test="${param.isValid==1}">
    <button class="downloadBtn btn btn-success btn-xs"
            data-url="${ctx}/cet/cetAnnualObj_exportDetails?objId=${param.objId}">
        <i class="fa fa-download"></i>
        导出学习培训明细表</button>
</c:if>
<table class="table table-bordered table-striped table-center">
    <thead>
    <tr>
        <th width="200">培训时间</th>
        <th>培训班名称</th>
        <th>培训类型</th>
        <th>培训班主办方</th>
        <th width="100">完成学时数</th>
        <th width="100">是否已结业</th>
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
                ${record.isGraduate?(cm:trimToZero(record.period)):0}
            </td>
            <td>
                ${record.isGraduate?"已结业":"未结业"}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

