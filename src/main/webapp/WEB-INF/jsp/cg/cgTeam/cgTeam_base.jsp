<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CgConstants.CG_RULE_TYPE_MAP%>" var="CG_RULE_TYPE_MAP"/>
<c:set value="<%=CgConstants.CG_RULE_TYPE_STAFF%>" var="CG_RULE_TYPE_STAFF"/>
<c:set value="<%=CgConstants.CG_TEAM_TYPE_MAP%>" var="CG_TEAM_TYPE_MAP"/>
<c:set value="<%=CgConstants.CG_CHILD_TEAM_TYPE_MAP%>" var="CG_CHILD_TEAM_TYPE_MAP"/>

<div class="modal-body">
    <div class="widget-box transparent" id="view-box">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i> 返回
                </a>
            </h4>
            <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                ${cgTeamBases.cgTeam.name}概况
            </span>
            <a href="javascript:;" class="downloadBtn btn btn-primary"
               data-url="${ctx}/cg/cgTeam_download?ids[]=${cgTeamBases.cgTeam.id}">
                <i class="ace-icon fa fa-download "></i>
                导出</a>
        </div>
        <table style="border: 1px solid #151515; font-size: 15px;width: 800px">

                <tr>
                    <th style="text-align: center">
                        <h1>${cgTeamBases.cgTeam.name}</h1>
                    </th>
                </tr>
                <c:if test="${not empty cgTeamBases.staffContent}">
                    <tr style="padding-left: 5px">
                        <th style="padding: 20px 0px 10px 10px">
                                ${CG_TEAM_TYPE_MAP.get(cgTeamBases.cgTeam.type)}组成规则
                        </th>
                    </tr>
                    <tr>
                        <td style="padding: 0px 0px 10px 30px">
                            ${cgTeamBases.staffContent}
                        </td>
                    </tr>
                    <c:if test="${not empty cgTeamBases.postAndNameMap}">
                        <tr style="padding-left: 5px">
                            <th style="padding: 20px 0px 10px 10px">具体人员组成</th>
                        </tr>
                        <tr>
                            <td style="padding: 0px 0px 10px 30px">
                                <c:forEach items="${cgTeamBases.postAndNameMap}" var="record">
                                    <p>${cm:getMetaType(record.key).name}:
                                        <c:forEach items="${record.value}" var="userId" varStatus="status">
                                            ${cm:getUserById(userId).realname}${status.last?"":"、"}
                                        </c:forEach>
                                    </p>
                                </c:forEach>
                                <%--<c:forEach items="${cgTeamBase.postAndNameList}" var="postAndName">
                                    <p style="line-height: 28pt">${postAndName}</p>
                                </c:forEach>--%>
                                <c:if test="${not empty cgTeamBases.cgLeader}">
                                    <p>办公室主任：${cm:getUserById(cgTeamBases.cgLeader.userId).realname}</p>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:if>
                <c:if test="${not empty cgTeamBases.jobContent}">
                    <tr style="padding-left: 5px">
                        <th style="padding: 20px 0px 10px 10px">
                                ${CG_TEAM_TYPE_MAP.get(cgTeamBases.cgTeam.type)}工作职责
                        </th>
                    </tr>
                    <tr>
                        <td style="padding: 0px 0px 10px 30px">
                                ${cgTeamBases.jobContent}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty cgTeamBases.debateContent}">
                    <tr style="padding-left: 5px">
                        <th style="padding: 20px 0px 10px 10px">
                                ${CG_TEAM_TYPE_MAP.get(cgTeamBases.cgTeam.type)}议事规则
                        </th>
                    </tr>
                    <tr>
                        <td style="padding: 0px 0px 10px 30px">
                                ${cgTeamBases.debateContent}
                        </td>
                    </tr>
                </c:if>
            <c:if test="${not empty cgBranchList}">
                <tr>
                    <th>
                        <h1 style="color: red">分委会</h1>
                    </th>
                </tr>
                <t:cgTeam_base_view childTeamBases="${cgBranchList}"></t:cgTeam_base_view>
            </c:if>

            <c:if test="${not empty cgWorkgroupList}">
                <tr>
                    <th>
                        <h1 style="color: red">工作小组</h1>
                    </th>
                </tr>
                <t:cgTeam_base_view childTeamBases="${cgWorkgroupList}"></t:cgTeam_base_view>
            </c:if>
        </table>
    </div>
</div>