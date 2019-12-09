<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>
<c:set var="member_needGrowTime" value="${_pMap['member_needGrowTime']=='true'}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党支部信息完整度对照表</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered checkTable">
        <tbody>
        <tr>
            <td class="bg-right">所属二级单位党组织</td>
            <td class="bg-left" style="min-width: 80px">${empty branchView.partyId?"否":"是"}</td>
            <td class="bg-right">支部类型</td>
            <td class="bg-left" style="min-width: 80px">${empty branchView.types?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">是否是教工党支部</td>
            <td class="bg-left" style="min-width: 80px">${empty branchView.isStaff?"否":"是"}</td>
            <td class="bg-right">是否一线教学科研党支部</td>
            <td class="bg-left" style="min-width: 120px">${empty branchView.isPrefessional?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">是否建立在团队</td>
            <td class="bg-left">${empty branchView.isBaseTeam?"否":"是"}</td>
            <td class="bg-right">成立时间</td>
            <td class="bg-left">${empty branchView.foundTime?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">联系电话</td>
            <td class="bg-left">${empty branchView.phone?"否":"是"}</td>
            <td class="bg-right">任命时间</td>
            <td class="bg-left">${empty branchView.appointTime?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">应换届时间</td>
            <td class="bg-left">${empty branchView.tranTime?"否":"是"}</td>
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
    $("td:contains('是否')").removeClass("notExist");
</script>