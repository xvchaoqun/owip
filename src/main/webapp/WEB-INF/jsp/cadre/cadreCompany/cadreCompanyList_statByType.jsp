<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="menu.jsp"/>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable" style="margin: 10px 20px; width: 1100px">
            <div class="space-4"></div>
            <a href="javascript:;" data-url="${ctx}/cadreCompanyList_statByTypeExport"
               style="margin-left: 20px" class="downloadBtn">
                <i class="fa fa-download"></i> ${_school}现任干部兼职情况统计表（按类别统计）</a>
            <div class="space-4"></div>
            <c:set var="typeMap" value="${cm:getMetaTypes('mc_cadre_company_type')}"/>
            <table class="table table-bordered table-unhover2 table-center" data-offset-top="132">
                <thead class="multi">
                <tr>
                    <th width="40" rowspan="3" colspan="2">类别</th>
                    <th colspan="2">合计</th>
                    <th colspan="${fn:length(typeMap) * 2}">兼职类型</th>
                </tr>
                <tr>
                    <th rowspan="2">兼职干部人数</th>
                    <th rowspan="2">兼职个数</th>
                    <c:forEach items="${typeMap}" var="entity">
                        <th colspan="2">${entity.value.name}</th>
                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach items="${typeMap}" var="entity">
                        <th width="58">人数</th>
                        <th width="58">个数</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td colspan="2" class="ltitle">总数</td>
                    <td>-</td>
                    <td>-</td>
                    <c:forEach items="${typeMap}" var="entity">
                        <td>-</td>
                        <td>-</td>
                    </c:forEach>
                </tr>
                <tr>
                    <td rowspan="3" class="ltitle" width="110">按行政级别</td>
                    <td class="ltitle">正处级</td>
                    <td>-</td>
                    <td>-</td>
                    <c:forEach items="${typeMap}" var="entity">
                        <td>-</td>
                        <td>-</td>
                    </c:forEach>
                </tr>
                <tr>
                    <td class="ltitle">副处级</td>
                    <td>-</td>
                    <td>-</td>
                    <c:forEach items="${typeMap}" var="entity">
                        <td>-</td>
                        <td>-</td>
                    </c:forEach>
                </tr>
                <tr>
                    <td class="ltitle">聘任制<br/>（无级别）</td>
                    <td>-</td>
                    <td>-</td>
                    <c:forEach items="${typeMap}" var="entity">
                        <td>-</td>
                        <td>-</td>
                    </c:forEach>
                </tr>
                <tr>
                    <td rowspan="2" class="ltitle">按干部类别</td>
                    <td class="ltitle">双肩挑</td>
                    <td>-</td>
                    <td>-</td>
                    <c:forEach items="${typeMap}" var="entity">
                        <td>-</td>
                        <td>-</td>
                    </c:forEach>
                </tr>
                <tr>
                    <td class="ltitle">专  职</td>
                    <td>-</td>
                    <td>-</td>
                    <c:forEach items="${typeMap}" var="entity">
                        <td>-</td>
                        <td>-</td>
                    </c:forEach>
                </tr>
                <c:forEach items="${unitTypeGroupMap}" var="entity" varStatus="vs">
                    <tr>
                        <c:if test="${vs.first}">
                        <td rowspan="${fn:length(unitTypeGroupMap)}" class="ltitle">按单位类型</td>
                        </c:if>
                        <td class="ltitle">${entity.value.name}</td>
                        <td>-</td>
                        <td>-</td>
                        <c:forEach items="${typeMap}" var="entity">
                            <td>-</td>
                            <td>-</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<style>
    .ltitle {
        text-align: center !important;
        vertical-align: middle !important;
        background-color: #f9f9f9 !important;
    }
</style>
<script>
    var rowDataMap = ${cm:toJSONArray(rowDataMap)};
    $.each(rowDataMap, function(i, rowData){
        //console.log(rowData)
        $.each(rowData, function(j, data){
            //console.log(i+ ":" + j + "=" +data + ":" + $.inArray(parseInt(i), [1,4,6]))
            $(".table tbody tr:eq("+i +") td:eq("+ (parseInt(j)-($.inArray(i, [0,2,3,5,7,8])>=0?1:0)) +")").html(data);
        })
    })
</script>