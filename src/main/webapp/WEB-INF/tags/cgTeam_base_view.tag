<%@ tag description="干部档案跳转链接" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ tag import="sys.constants.CgConstants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="childTeamBases" type="java.util.List" required="true" %>
<c:set value="<%=CgConstants.CG_CHILD_TEAM_TYPE_MAP%>" var="CG_CHILD_TEAM_TYPE_MAP"/>
<c:set value="<%=CgConstants.CG_RULE_TYPE_MAP%>" var="CG_RULE_TYPE_MAP"/>
<c:set value="<%=CgConstants.CG_RULE_TYPE_STAFF%>" var="CG_RULE_TYPE_STAFF"/>

<c:forEach items="${childTeamBases}" var="cgTeamBase">
    <tr>
        <th style="text-align: center">
            <h3 style="color:red;">${cgTeamBase.cgTeam.name}</h3>
        </th>
    </tr>
    <c:if test="${not empty cgTeamBase.staffContent}">
        <tr style="padding-left: 5px">
            <th style="padding: 20px 0px 10px 10px">
                    ${CG_CHILD_TEAM_TYPE_MAP.get(cgTeamBase.cgTeam.type)}组成规则
            </th>
        </tr>
        <tr>
            <td style="padding: 0px 0px 10px 30px">
                    ${cgTeamBase.staffContent}
            </td>
        </tr>
        <c:if test="${not empty cgTeamBase.postAndNameMap}">
            <tr style="padding-left: 5px">
                <th style="padding: 20px 0px 10px 10px">具体人员组成</th>
            </tr>
            <tr>
                <td style="padding: 0px 0px 10px 30px">
                    <c:forEach items="${cgTeamBase.postAndNameMap}" var="record">
                        <p>${cm:getMetaType(record.key).name}:
                            <c:forEach items="${record.value}" var="userId" varStatus="status">
                                ${cm:getUserById(userId).realname}${status.last?"":"、"}
                            </c:forEach>
                        </p>
                    </c:forEach>
                    <c:if test="${not empty cgTeamBase.cgLeader}">
                        <p>办公室主任：${cm:getUserById(cgTeamBase.cgLeader.userId).realname}</p>
                    </c:if>
                </td>
            </tr>
        </c:if>
    </c:if>
    <c:if test="${not empty cgTeamBase.jobContent}">
        <tr style="padding-left: 5px">
            <th style="padding: 20px 0px 10px 10px">
                    ${CG_CHILD_TEAM_TYPE_MAP.get(cgTeamBase.cgTeam.type)}工作职责
            </th>
        </tr>
        <tr>
            <td style="padding: 0px 0px 10px 30px">
                    ${cgTeamBase.jobContent}
            </td>
        </tr>
    </c:if>
    <c:if test="${not empty cgTeamBase.debateContent}">
        <tr style="padding-left: 5px">
            <th style="padding: 20px 0px 10px 10px">
                    ${CG_CHILD_TEAM_TYPE_MAP.get(cgTeamBase.cgTeam.type)}议事规则
            </th>
        </tr>
        <tr>
            <td style="padding: 0px 0px 10px 30px">
                    ${cgTeamBase.debateContent}
            </td>
        </tr>
    </c:if>
</c:forEach>
