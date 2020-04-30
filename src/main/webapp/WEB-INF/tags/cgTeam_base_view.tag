<%@ tag description="干部档案跳转链接" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ tag import="sys.constants.CgConstants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="cgTeamBase" type="java.util.Map" required="true" %>
<%@ attribute name="index" type="java.lang.Integer" required="false" %>
<c:set value="<%=CgConstants.CG_RULE_TYPE_MAP%>" var="CG_RULE_TYPE_MAP"/>
<c:set value="<%=CgConstants.CG_RULE_TYPE_STAFF%>" var="CG_RULE_TYPE_STAFF"/>
<c:set value="<%=CgConstants.CG_TEAM_TYPE_MAP%>" var="CG_TEAM_TYPE_MAP"/>
<tr>
    <th class="${not empty index?'childTitle':'tableTitle'}">
        <c:if test="${not empty index}">（${cm:toHanStr(index)}）</c:if>
        ${cgTeamBase.cgTeam.name}
    </th>
</tr>
<c:if test="${not empty cgTeamBase.cgRuleList}">
    <c:set var="cgRuleList" value="${cgTeamBase.cgRuleList}"/>
    <c:forEach items="${cgRuleList}" var="cgRule" varStatus="vs">
        <tr>
            <th class="tableSubtitle">
                ${empty index?cm:toHanStr(vs.index+1):vs.index+1}、
                ${CG_TEAM_TYPE_MAP.get(cgTeamBase.cgTeam.type)}${CG_RULE_TYPE_MAP.get(cgRule.type)}
            </th>
        </tr>
        <tr>
            <td style="padding: 0px 20px;line-height: 28pt;">${cgRule.content}</td>
        </tr>
        <c:if test="${CG_RULE_TYPE_STAFF == cgRule.type}">
            <c:if test="${not empty cgTeamBase.cgMemberList}">
                <tr>
                    <th class="tableSubtitle">具体人员组成：</th>
                </tr>
                <c:forEach items="${cgTeamBase.cgMemberList}" var="cgMember">
                    <tr>
                        <td class="tableContent">${cgMember}</td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${not empty cgTeamBase.cgLeader}">
                <tr>
                    <td class="tableContent">
                           办公室主任：${cgTeamBase.cgLeader.user.realname}
                    </td>
                </tr>
            </c:if>
        </c:if>
    </c:forEach>
</c:if>
<style>
    .tableTitle{
        text-align: center;
        font-size: 32px;
        padding: 20px 0px;
    }
    .childTitle{
        text-align: left;
        font-size: 18px;
        padding: 10px 20px;
        color: red;
    }
    .tableSubtitle{
        text-align: left;
        font-size: 18px;
        font-weight: bold;
        padding: 0px 20px;
    }
    .tableContent{
        font-size: 16px;
        padding: 0px 40px;
        line-height: 28pt;
    }
</style>