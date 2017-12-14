<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button id="closeBtn" type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.isSelfPay==0?'代缴信息确认':'缴费信息确认'}</h3>
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
        <input type="hidden" name="paycode" value="${payFormBean.paycode}"/>
        <input type="hidden" name="payer" value="${payFormBean.payer}"/>
        <input type="hidden" name="payertype" value="${payFormBean.payertype}"/>
        <input type="hidden" name="payername" value="${payFormBean.payername}"/>
        <input type="hidden" name="sn" value="${payFormBean.sn}"/>
        <input type="hidden" name="amt" value="${payFormBean.amt}"/>
        <input type="hidden" name="macc" value="${payFormBean.macc}"/>
        <input type="hidden" name="commnet" value="${payFormBean.commnet}"/>
        <input type="hidden" name="sno_id_name" value="${payFormBean.sno_id_name}"/>
        <input type="hidden" name="sign" value="${payFormBean.sign}"/>

    </form>
</div>
<div class="modal-footer">
    <div id="submitTip">支付完成前，请不要关闭此支付验证窗口。支付完成后，请点击“查看支付结果”更新支付状态。</div>
    <button id="submitBtn" type="button"
            data-loading-text="支付中，已跳转至支付页面"
            class="btn btn-primary"><i class="fa fa-mail-forward"></i> 去支付</button>
    <input id="finishBtn" style="display: none" type="button" class="btn btn-success" value="查看支付结果">
    <%--<a href="${ctx}/pmd/pay/callback/campuscard?${ret}" target="_blank">test成功</a>--%>
</div>
<style>
    #submitTip{
        text-indent: 2em;
        text-align: left;
        display: none;
        font-size: 18px;
        font-weight: bolder;
        margin-bottom: 5px;
        color: darkred;
    }
</style>
<script>
    $("#submitBtn").click(function () {
        var $this = $(this);
        $.ajax({
            type : "post",
            url : "${ctx}/user/pmd/payConfirm_campuscard",
            data:{id:'${pmdMember.id}', isSelfPay:"${param.isSelfPay}"},
            async : false, // 同步方法
            dataType:"json",
            success : function(data){
                if(data.success){
                    $this.button('loading').prop("disabled", true);
                    $("#submitTip,#finishBtn").show();
                    $("#closeBtn").hide();
                    $("#payForm").submit();
                }
            }
        });
    });
    $("#finishBtn").click(function () {

        $("#modal").modal('hide');

        $("#${param.isSelfPay==0?'jqGrid2':'jqGrid'}").trigger("reloadGrid");
    });
</script>