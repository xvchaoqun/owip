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
            <td class="bg-right">班子成员信息</td>
            <td class="bg-left">是</td>
            <td class="bg-right">支部排序</td>
            <td class="bg-left">是</td>
        </tr>
        <tr>
            <td class="bg-right">所在单位属性</td>
            <td class="bg-left">是</td>
            <td class="bg-right">是否标杆院系</td>
            <td class="bg-left">${empty partyView.isBg?"否":"是"}</td>

        </tr>
        <c:if test="${partyView.isBg}">
            <tr>
                <td class="bg-right">评选标杆院系时间</td>
                <td class="bg-left">
                        ${empty partyView.bgDate?"否":"是"}
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <form action="${ctx}/party_integrity" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input name="partyId" value="${partyView.id}" type="hidden">
    </form>
</div>
<div class="modal-footer">
    <div class="note">注：系统每日自动校验信息完整度，如需查看最新结果，请点击立即刷新按钮。</div>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-refresh"></i> 立即刷新
    </button>
</div>
<style>
    .checkTable tbody td.notExist{
        background-color: #f2dede!important;
    }
</style>
<script>
    $("td:contains('否')").addClass("notExist");
    $("td:contains('是否')").removeClass("notExist");

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>