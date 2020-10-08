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
                <table class="table table-striped table-center table-bordered table-hover">
                    <thead>
                    <tr>
                        <th width="120">工作证号</th>
                        <th width="100">姓名</th>
                        <th width="150">职务</th>
                        <th width="80">行政级别</th>
                        <th>分管工作</th>
                        <th style="min-width: 200px">分管机关部门</th>
                        <th style="min-width: 200px">联系学部、院、系（所）</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${leaders}" var="leader" varStatus="st">
                        <c:set var="cadre" value="${leader.cadre}"/>
                        <c:set var="sysUser" value="${leader.user}"/>
                        <c:set var="cLeaderUnits" value="${cLeaderUnitMap.get(leader.id)}"/>
                        <c:set var="mLeaderUnits" value="${mLeaderUnitMap.get(leader.id)}"/>

                        <tr>
                            <td nowrap>${sysUser.code}</td>
                            <td nowrap>
                            <t:cadre cadreId="${cadre.id}" realname="${sysUser.realname}"/>
                            </td>
                            <td nowrap style="text-align: left">${cadre.title}</td>
                            <td nowrap>${cm:getMetaType(cadre.adminLevel).name}</td>
                            <td style="text-align: left">${leader.job}</td>
                            <td style="text-align: left">
                                <ul>
                                    <c:forEach items="${mLeaderUnits}" var="leaderUnit">
                                        <li>
                                            <t:unit unit="${unitMap.get(leaderUnit.unitId)}"/>
                                        </li>
                                    </c:forEach>
                                </ul>

                            </td>
                            <td style="text-align: left">
                                <ul>
                                    <c:forEach items="${cLeaderUnits}" var="leaderUnit">
                                    <li>
                                        <t:unit unit="${unitMap.get(leaderUnit.unitId)}"/>
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
<style>
    #body-content  .widget-main.no-padding .table{

        border: 1px solid #E5E5E5
    }
    .widget-body .table thead:first-child tr{
        background:#F2F2F2;
    }
</style>