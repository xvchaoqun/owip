<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="STUDENT" value="<%=MemberConstants.MEMBER_TYPE_STUDENT%>" />
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
            <c:if test="${memberView.politicalStatus==POSITIVE || isOverTime}">
                <td class="bg-right">转正时间</td>
                <td class="bg-left">${empty memberView.positiveTime?"否":"是"}</td>
            </c:if>
            <c:if test="${memberView.politicalStatus!=POSITIVE && !isOverTime}">
                <td class="bg-right">是否出国留学</td>
                <td class="bg-left">是</td>
            </c:if>
        </tr>
        <c:if test="${(memberView.politicalStatus==POSITIVE || isOverTime) && memberView.type == STUDENT}">
            <tr>
                <td class="bg-right">是否出国留学</td>
                <td class="bg-left">是</td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <form action="${ctx}/member_integrity" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input name="memberId" value="${memberView.userId}" type="hidden">
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