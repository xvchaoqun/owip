<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-circle-o-notch fa-spin"></i>
                    正在运转单位
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">
                <table class="table table-actived table-striped table-hover">
                    <tbody>

                    <tr>
                        <c:forEach items="${runUnits}" var="unit" varStatus="vs">
                     <td width="50%">

                    <c:set var="pUnitAdminCadres" value="${pUnitAdminCadreMap.get(unit.id)}"/>
                    <c:set var="npUnitAdminCadres" value="${npUnitAdminCadreMap.get(unit.id)}"/>
                         <a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
                    ${unit.name}
                        </a><c:if test="${fn:length(pUnitAdminCadres)>0 || fn:length(npUnitAdminCadres)>0}">
                         ： </c:if>
                    <c:forEach items="${pUnitAdminCadres}" var="pUnitAdminCadre">
                        <c:set var="cadre" value="${cadreMap.get(pUnitAdminCadre.cadreId)}"/>
                        <t:cadre cadreId="${cadre.id}" realname="${cm:getUserById(cadre.userId).realname}"/>
                    </c:forEach>
                         <c:if test="${fn:length(pUnitAdminCadres)>0 && fn:length(npUnitAdminCadres)>0}">
                    | </c:if>
                    <c:forEach items="${npUnitAdminCadres}" var="npUnitAdminCadre">
                        <c:set var="cadre2" value="${cadreMap.get(npUnitAdminCadre.cadreId)}"/>
                        <t:cadre cadreId="${cadre2.id}" realname="${cm:getUserById(cadre2.userId).realname}"/>
                    </c:forEach>
                    <c:if test="${vs.count%2==0}">
                        </td></tr>
                        ${vs.last?"":"<tr>"}
                    </c:if>
                    </td>
                        </c:forEach>
                    <c:if test="${fn:length(runUnits)%2==1}">
                        <td></td>
                    </c:if>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-history"></i>
            历史单位
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <table class="table table-actived table-striped table-hover">
                <tbody>
                <tr>
                    <c:forEach items="${historyUnits}" var="unit" varStatus="vs">
                    <td width="50%">
                        <a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
                                ${unit.name}
                        </a>
                        <c:if test="${vs.count%2==0}">
                    </td></tr>
                    ${vs.last?"":"<tr>"}
                </c:if>
                </td>
                </c:forEach>

                <c:if test="${fn:length(historyUnits)%2==1}">
                    <td></td>
                </c:if>
                </tr>
                </tbody>
            </table>

        </div>
    </div>
</div>