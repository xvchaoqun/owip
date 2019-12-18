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
            <td class="bg-left" style="min-width: 120px">${empty branchView.isBaseTeam?"否":"是"}</td>
            <td class="bg-right">所在单位属性</td>
            <td class="bg-left" style="min-width: 120px">是</td>

        </tr>
        <tr>
            <td class="bg-right">成立时间</td>
            <td class="bg-left">${empty branchView.foundTime?"否":"是"}</td>
            <td class="bg-right">联系电话</td>
            <td class="bg-left">${empty branchView.phone?"否":"是"}</td>

        </tr>
        <tr>
            <td class="bg-right">支部委员信息</td>
            <td class="bg-left" style="min-width: 120px">是</td>
            <td class="bg-right">任命时间</td>
            <td class="bg-left">${empty branchView.appointTime?"否":"是"}</td>
        </tr>
        <tr>
            <td class="bg-right">应换届时间</td>
            <td class="bg-left">${empty branchView.tranTime?"否":"是"}</td>
        </tr>
        </tbody>
    </table>
    <form action="${ctx}/branch_integrity" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input name="branchId" value="${branchView.id}" type="hidden">
        <p style="color: red;font-size: 16px">
            信息完整度会每天进行一次校验更新，如果查看最新信息完整度，请点击更新
        </p>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-refresh"></i> 更新
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