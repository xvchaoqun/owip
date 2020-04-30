<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CgConstants.CG_RULE_TYPE_MAP%>" var="CG_RULE_TYPE_MAP"/>
<c:set value="<%=CgConstants.CG_RULE_TYPE_STAFF%>" var="CG_RULE_TYPE_STAFF"/>
<c:set value="<%=CgConstants.CG_TEAM_TYPE_MAP%>" var="CG_TEAM_TYPE_MAP"/>

<div class="modal-body">
    <div class="widget-box transparent" id="view-box">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i> 返回
                </a>
            </h4>
            <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                ${cgTeamBase.cgTeam.name}概况
            </span>
            <a href="javascript:;" class="downloadBtn btn btn-primary" 
               data-url="${ctx}/cg/cgTeam_download?ids[]=${cgTeamBase.cgTeam.id}">
                <i class="ace-icon fa fa-download "></i>导出</a>
        </div>
        <table style="border: 1px solid #151515; font-size: 16px;width: 800px">
            <t:cgTeam_base_view cgTeamBase="${cgTeamBase}" />
            <c:set value="${fn:length(cgTeamBase.cgRuleList)}" var="ruleListSize"/>
            <c:if test="${not empty cgBranchList}">
                <tr>
                    <th class="tableSubtitle" style="color: red">
                            ${cm:toHanStr(ruleListSize+1)}、分委会
                    </th>
                </tr>
                <c:forEach items="${cgBranchList}" var="cgBranch" varStatus="vs">
                    <t:cgTeam_base_view cgTeamBase="${cgBranch}" index="${vs.index+1}" />
                </c:forEach>
            </c:if>
            <c:if test="${not empty cgWorkgroupList}">
                <tr>
                    <th class="tableSubtitle" style="color:red;">
                        <c:if test="${not empty cgBranchList}">
                            ${cm:toHanStr(ruleListSize+2)}、
                        </c:if>
                        <c:if test="${empty cgBranchList}">
                            ${cm:toHanStr(ruleListSize+1)}、
                        </c:if>工作小组
                    </th>
                </tr>
                <c:forEach items="${cgWorkgroupList}" var="cgWorkgroup" varStatus="vs">
                    <t:cgTeam_base_view cgTeamBase="${cgWorkgroup}" index="${vs.index+1}" />
                </c:forEach>
            </c:if>
        </table>
    </div>
</div>