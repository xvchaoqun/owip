<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
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
    <form id="payForm" action="${pay_url}" target="_blank" method="post"></form>
</div>
<div class="modal-footer">
    <%--<div id="tip">提示：由于校园卡支付平台出于安全性考虑，只要点了“去支付”按钮，必须支付完成，不可再换人代缴操作，请谨慎操作。</div>--%>
    <div id="submitTip">支付完成前，请不要关闭此支付验证窗口。支付完成后，请点击“查看支付结果”更新支付状态。</div>
    <button id="submitBtn" type="button"
            data-loading-text="支付中，已跳转至支付页面"
            class="btn btn-primary"><i class="fa fa-mail-forward"></i> 去支付</button>
    <input id="finishBtn" style="display: none" type="button" class="btn btn-success" value="查看支付结果">
    <a id="testCallback" target="_blank">test成功</a>
</div>
<style>
    #submitTip, #tip{
        text-indent: 2em;
        text-align: left;
        font-size: 18px;
        font-weight: bolder;
        margin-bottom: 5px;
        color: darkred;
    }
    #submitTip{
        display: none;
    }
</style>
<script type="text/template" id="payFormTpl">
    <input type="hidden" name="paycode" value="{{=order.paycode}}"/>
    <input type="hidden" name="payer" value="{{=order.payer}}"/>
    <input type="hidden" name="payertype" value="{{=order.payertype}}"/>
    <input type="hidden" name="payername" value="{{=order.payername}}"/>
    <input type="hidden" name="sn" value="{{=order.sn}}"/>
    <input type="hidden" name="amt" value="{{=order.amt}}"/>
    <input type="hidden" name="macc" value="{{=order.macc}}"/>
    <input type="hidden" name="commnet" value="{{=order.commnet}}"/>
    <input type="hidden" name="sno_id_name" value="{{=order.snoIdName}}"/>
    <input type="hidden" name="sign" value="{{=order.sign}}"/>
</script>
<script>
    $("#submitBtn").click(function () {
        var $this = $(this);
        $.ajax({
            type : "post",
            url : "${ctx}/user/pmd/payConfirm",
            data:{id:'${pmdMember.id}', isSelfPay:"${param.isSelfPay}"},
            async : false, // 同步方法
            dataType:"json",
            success : function(data){
                if(data.success){
                    $this.button('loading').prop("disabled", true);
                    $("#submitTip,#finishBtn").show();
                    $("#closeBtn").hide();

                    // test
                    $("#testCallback").attr("href", "${ctx}/pmd/pay/callback/campuscard?" + data.ret);

                    $("#payForm").html(_.template($("#payFormTpl").html())({order: data.order}))
                    $("#payForm").submit();
                }
            }
        });
    });
    $("#finishBtn").click(function () {

        $("#modal").modal('hide');
        <c:if test="${param.isMemberHelpPay==1}">
        $.hideView();
        </c:if>
        $("#${param.isSelfPay==0?'jqGrid2':'jqGrid'}").trigger("reloadGrid");
    });
</script>