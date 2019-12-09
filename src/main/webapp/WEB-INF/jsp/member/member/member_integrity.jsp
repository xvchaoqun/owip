<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>
<c:set var="member_needGrowTime" value="${_pMap['member_needGrowTime']=='true'}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党员信息完整度对照表</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered checkTable">
        <tbody>
        <tr>
            <td class="bg-right">性别</td>
            <td class="bg-left" style="min-width: 80px">${empty memberView.gender?"否":"是"}</td>
            <td class="bg-right">出生日期</td>
            <td class="bg-left" style="min-width: 80px">${empty memberView.birth?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">民族</td>
            <td class="bg-left" style="min-width: 80px">${empty memberView.nation?"否":"是"}</td>
            <td class="bg-right">政治面貌</td>
            <td class="bg-left" style="min-width: 120px">${empty memberView.politicalStatus?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">所属二级单位党组织</td>
            <td class="bg-left">${empty memberView.partyId?"否":"是"}</td>
            <td class="bg-right">所在党支部</td>
            <td class="bg-left">${empty memberView.branchId?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">入党时间</td>
            <td class="bg-left">${empty memberView.growTime?"否":"是"}</td>
            <td class="bg-right">
                <c:set value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>" var="POSITIVE"/>
                <c:if test="${memberView.politicalStatus==POSITIVE}">
                    转正时间
                </c:if>
            </td>
            <td class="bg-left">
                <c:if test="${memberView.politicalStatus==POSITIVE}">
                    ${empty memberView.positiveTime?"否":"是"}
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<style>
    .checkTable tbody td.notExist{
        background-color: #f2dede!important;
    }
</style>
<script>
    $("td:contains('否')").addClass("notExist");
</script>