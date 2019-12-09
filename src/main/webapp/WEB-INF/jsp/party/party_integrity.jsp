<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>
<c:set var="member_needGrowTime" value="${_pMap['member_needGrowTime']=='true'}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>基层党组织信息完整度对照表</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered checkTable">
        <tbody>
        <tr>
            <td class="bg-right">名称</td>
            <td class="bg-left" style="min-width: 80px">${empty partyView.name?"否":"是"}</td>
            <td class="bg-right">成立时间</td>
            <td class="bg-left" style="min-width: 80px">${empty partyView.foundTime?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">所属单位</td>
            <td class="bg-left" style="min-width: 80px">${empty partyView.unitId?"否":"是"}</td>
            <td class="bg-right">党总支类别</td>
            <td class="bg-left" style="min-width: 120px">${empty partyView.classId?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">组织类型</td>
            <td class="bg-left">${empty partyView.typeId?"否":"是"}</td>
            <td class="bg-right">联系电话</td>
            <td class="bg-left">${empty partyView.phone?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">任命时间</td>
            <td class="bg-left">${empty partyView.appointTime?"否":"是"}</td>
            <td class="bg-right">应换届时间</td>
            <td class="bg-left">${empty partyView.tranTime?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">是否标杆院系</td>
            <td class="bg-left">${empty partyView.isBg?"否":"是"}</td>
            <td class="bg-right">
                <c:if test="${partyView.isBg}">评选标杆院系时间</c:if>
            </td>
            <td class="bg-left">
                <c:if test="${partyView.isBg}">${empty partyView.bgDate?"否":"是"}</c:if>
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
    $("td:contains('是否')").removeClass("notExist");
</script>