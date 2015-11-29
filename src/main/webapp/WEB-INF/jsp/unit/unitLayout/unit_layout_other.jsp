<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-angle-double-down"></i>
                    正在运转单位
            </h4>

            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">
                <table class="table table-striped table-hover table-condensed">
                    <tbody>

                    <tr>
                        <c:forEach items="${runUnits}" var="unit" varStatus="vs">
                     <td>

                    <c:set var="pUnitAdminCadres" value="${pUnitAdminCadreMap.get(unit.id)}"/>
                    <c:set var="npUnitAdminCadres" value="${npUnitAdminCadreMap.get(unit.id)}"/>
                         <a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
                    ${unit.name}
                        </a><c:if test="${fn:length(pUnitAdminCadres)>0 || fn:length(npUnitAdminCadres)>0}">
                         ： </c:if>
                    <c:forEach items="${pUnitAdminCadres}" var="pUnitAdminCadre">
                        <c:set var="cadre" value="${cadreMap.get(pUnitAdminCadre.cadreId)}"/>
                         <a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${cadre.id}">
                         ${cm:getUserById(cadre.userId).realname}
                             </a>
                    </c:forEach>
                         <c:if test="${fn:length(pUnitAdminCadres)>0 && fn:length(npUnitAdminCadres)>0}">
                    | </c:if>
                    <c:forEach items="${npUnitAdminCadres}" var="npUnitAdminCadre">
                        <c:set var="cadre2" value="${cadreMap.get(npUnitAdminCadre.cadreId)}"/>
                         <a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${cadre2.id}">
                        ${cm:getUserById(cadre2.userId).realname}
                             </a>
                    </c:forEach>
                    <c:if test="${vs.count%2==0}">
                        </td></tr>
                        ${vs.last?"":"<tr>"}
                    </c:if>
                    </td>
                        </c:forEach>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-angle-double-down"></i>
            历史单位
        </h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <c:forEach items="${historyUnits}" var="unit">
                ${unit.name}
            </c:forEach>
        </div>
    </div>
</div>