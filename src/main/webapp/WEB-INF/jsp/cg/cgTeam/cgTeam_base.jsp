<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CgConstants.CG_RULE_TYPE_MAP%>" var="CG_RULE_TYPE_MAP"/>
<c:set value="<%=CgConstants.CG_RULE_TYPE_STAFF%>" var="CG_RULE_TYPE_STAFF"/>
<c:set value="<%=CgConstants.CG_TEAM_RECTOR%>" var="CG_TEAM_RECTOR"/>
<c:set value="<%=CgConstants.CG_TEAM_MANAGING_VICE_PRESIDENT%>" var="CG_TEAM_MANAGING_VICE_PRESIDENT"/>
<c:set value="<%=CgConstants.CG_TEAM_VICE_PRESIDENT%>" var="CG_TEAM_VICE_PRESIDENT"/>
<c:set value="<%=CgConstants.CG_TEAM_MEMBER%>" var="CG_TEAM_MEMBER"/>
<c:set value="<%=CgConstants.CG_TEAM_OFFICE_DIRECTOR%>" var="CG_TEAM_OFFICE_DIRECTOR"/>
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
        ${cgTeam.name}概况
    </span>
        <a href="javascript:;" class="downloadBtn btn btn-primary"
           data-url="${ctx}/cg/cgTeam_download?ids[]=${cgTeam.id}">
            <i class="ace-icon fa fa-download "></i>
            导出
        </a>
</div>
        <table style="border: 1px solid #151515; width: 850px; font-size: 15px">
            <tr>
                <th style="text-align: center">
                    <h1>${cgTeam.name}</h1>
                </th>
            </tr>
<c:forEach items="${cgRuleContentMap}" var="cgRuleContent">

        <tr style="padding-left: 5px">
            <th style="padding: 20px 0px 10px 10px">
                ${CG_TEAM_TYPE_MAP.get(cgTeam.type)}${CG_RULE_TYPE_MAP.get(cgRuleContent.key)}
            </th>
        </tr>
        <tr>
            <td style="padding: 0px 0px 10px 30px">${cgRuleContent.value}</td>
        </tr>

    <c:if test="${cgRuleContent.key == CG_RULE_TYPE_STAFF}">
        <tr style="padding-left: 5px">
            <th style="padding: 20px 0px 10px 10px">
                具体人员组成
            </th>
        </tr>
        <tr>
            <td style="padding: 0px 0px 10px 30px">
                <c:forEach items="${cgMemberTypeMap}" var="record">

                    <p>${cm:getMetaType(record.key).name}:
                        <c:forEach items="${record.value}" var="userId" varStatus="status">
                            ${cm:getUserById(userId).realname}${status.last?"":"、"}
                        </c:forEach>
                    </p>
                </c:forEach>
                <c:if test="${not empty cgLeader}">
                    <p>办公室主任：${cm:getUserById(cgLeader.userId).realname}</p>
                </c:if>
            </td>
        </tr>
    </c:if>
</c:forEach>
        </table>
    </div>
</div>