<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="menu.jsp"/>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable" style="margin: 10px 20px; width: 1200px">
            <div class="space-4"></div>

<c:if test="${module==1}">
            <a href="javascript:;" data-url="${ctx}/cadreCompanyList_statExport?cadreStatus=<%=CadreConstants.CADRE_STATUS_LEADER%>"
               style="margin-left: 20px" class="downloadBtn">
                <i class="fa fa-download"></i> ${_school}现任校领导兼职情况统计表</a>

            <a href="javascript:;" data-url="${ctx}/cadreCompanyList_statExport?cadreStatus=<%=CadreConstants.CADRE_STATUS_LEADER_LEAVE%>"
               style="margin-left: 20px" class="downloadBtn">
                <i class="fa fa-download"></i> ${_school}离任校领导兼职情况统计表</a>
</c:if>
<c:if test="${module==2}">
    <a href="javascript:;" data-url="${ctx}/cadreCompanyList_statExport?cadreStatus=<%=CadreConstants.CADRE_STATUS_CJ%>"
       style="margin-left: 20px" class="downloadBtn">
        <i class="fa fa-download"></i> ${_school}现任干部兼职情况统计表</a>
</c:if>
            <div class="space-4"></div>
            <c:set var="typeMap" value="${cm:getMetaTypes('mc_cadre_company_type')}"/>
            <table class="table table-bordered table-striped table-center" data-offset-top="132">
                <thead class="multi">
                <tr>
                    <th width="40" rowspan="2">序号</th>
                    <th rowspan="2" width="80">姓名</th>
                    <th rowspan="2" width="280">所在单位及职务</th>
                    <th rowspan="2" width="100">行政级别</th>
                    <th rowspan="2" width="50">兼职个数</th>
                    <th colspan="${fn:length(typeMap)}">兼职类型</th>
                </tr>
                <tr>
                    <c:forEach items="${typeMap}" var="entity">
                    <th width="100">${entity.value.name}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${statMap}" var="stat" varStatus="vs">
                    <c:set var="cadre" value="${cm:getCadreById(stat.key)}"/>
                <tr>
                    <td>${vs.count}</td>
                    <td>${cadre.realname}</td>
                    <td style="text-align: left">${cadre.title}</td>
                    <td>${cm:getMetaType(cadre.adminLevel).name}</td>
                    <td>${stat.value.totalCount}</td>
                    <c:forEach items="${typeMap}" var="entity">
                        <c:set var="num" value="${stat.value.typeMap.get(entity.key)}"/>
                        <td>${empty num?'-':num}</td>
                    </c:forEach>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="footer-margin"/>
<script>
    stickheader();
</script>