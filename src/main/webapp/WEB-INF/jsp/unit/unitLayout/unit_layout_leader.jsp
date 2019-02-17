<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:forEach items="${resultMap}" var="result">
    <c:set var="unitType" value="${result.key}"/>
    <c:set var="leaders" value="${result.value}"/>

    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-angle-double-down"></i>
                    ${unitType.name}
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="col-xs-1">工作证号</th>
                        <th>姓名</th>
                        <th>职务</th>
                        <th>行政级别</th>
                        <th width="300">分管工作</th>
                        <th>分管机关部门</th>
                        <th>联系学部、院、系（所）</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${leaders}" var="leader" varStatus="st">
                        <c:set var="cadre" value="${cm:getCadreById(leader.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <c:set var="cLeaderUnits" value="${cLeaderUnitMap.get(leader.id)}"/>
                        <c:set var="mLeaderUnits" value="${mLeaderUnitMap.get(leader.id)}"/>

                        <tr>
                            <td nowrap>${sysUser.code}</td>
                            <td nowrap>
                            <t:cadre cadreId="${cadre.id}" realname="${sysUser.realname}"/>
                            </td>
                            <td nowrap>${cadre.title}</td>
                            <td nowrap>${cm:getMetaType(cadre.adminLevel).name}</td>
                            <td>${leader.job}</td>
                            <td>
                                <ul>
                                    <c:forEach items="${mLeaderUnits}" var="leaderUnit">
                                        <c:set var="unit" value="${unitMap.get(leaderUnit.unitId)}"/>
                                        <li>
                                            <a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
                                                <span class="${unit.status==UNIT_STATUS_HISTORY?'delete':''}">${unit.name}</span>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>

                            </td>
                            <td>
                                <ul>
                                    <c:forEach items="${cLeaderUnits}" var="leaderUnit">
                                        <c:set var="unit" value="${unitMap.get(leaderUnit.unitId)}"/>
                                    <li>
                                        <a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id=${unit.id}">
                                            <span class="${unit.status==UNIT_STATUS_HISTORY?'delete':''}">${unit.name}</span>
                                        </a>
                                    </li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:forEach>
<div class="footer-margin"/>
<style>
    #body-content  .widget-main.no-padding .table{

        border: 1px solid #E5E5E5
    }
    .widget-body .table thead:first-child tr{
        background:#F2F2F2;
    }
</style>