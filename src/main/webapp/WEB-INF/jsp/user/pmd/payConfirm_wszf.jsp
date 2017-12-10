<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>缴费信息确认</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right">月份</td>
            <td>${cm:formatDate(pmdMember.payMonth, "yyyy年MM月")}</td>
        </tr>
        <tr>
            <td class="bg-right">工作证号</td>
            <td>${pmdMember.user.code}</td>
        </tr>
        <tr>
            <td class="bg-right">姓名</td>
            <td>${pmdMember.user.realname}</td>
        </tr>
        <tr>
            <td class="bg-right">所属党委</td>
            <td>${partyMap.get(pmdMember.partyId).name}</td>
        </tr>
        <c:if test="${not empty pmdMember.branchId}">
            <tr>
                <td class="bg-right">所属党支部</td>
                <td>${branchMap.get(pmdMember.branchId).name}</td>
            </tr>
        </c:if>
        <tr>
            <td class="bg-right">党员类别</td>
            <td>${PMD_MEMBER_TYPE_MAP.get(pmdMember.type)}</td>
        </tr>
        <tr>
            <td class="bg-right">应交金额</td>
            <td>${pmdMember.duePay}</td>
        </tr>
    </table>
    <form id="payForm" action="${pay_url}" target="_blank" method="post">
        <input type="hidden" name="orderDate" value="${payFormBean.orderDate}"/>
        <input type="hidden" name="orderNo" value="${payFormBean.orderNo}"/>
        <input type="hidden" name="amount" value="${payFormBean.amount}"/>
        <input type="hidden" name="xmpch" value="${payFormBean.xmpch}"/>
        <input type="hidden" name="return_url" value="${payFormBean.return_url}"/>
        <input type="hidden" name="notify_url" value="${payFormBean.notify_url}"/>
        <input type="hidden" name="sign" value="${payFormBean.sign}"/>

    </form>
</div>
<div class="modal-footer">
    <button id="submitBtn" type="button"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 支付中，已跳转至支付页面"
            class="btn btn-primary"><i class="fa fa-mail-forward"></i> 去支付</button>
    <input id="finishBtn" style="display: none" type="button" class="btn btn-success" value="支付完成？">
    <a href="${ctx}/pmd/pay/returnPage?${ret}" target="_blank">test成功</a>
</div>

<script>
    $("#submitBtn").click(function () {
        var $this = $(this);
        $.ajax({
            type : "post",
            url : "${ctx}/user/pmd/payConfirm_wszf",
            data:{monthId:'${pmdMember.monthId}'},
            async : false, // 同步方法
            dataType:"json",
            success : function(data){
                if(data.success){
                    $this.button('loading').prop("disabled", true);
                    $("#finishBtn").show();
                    $("#payForm").submit();
                }
            }
        });
    });
    $("#finishBtn").click(function () {

        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    });
</script>