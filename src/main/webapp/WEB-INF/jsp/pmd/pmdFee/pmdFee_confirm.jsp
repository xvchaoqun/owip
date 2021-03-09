<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button id="closeBtn" type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>缴费信息确认</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right">工作证号</td>
            <td>${pmdFee.user.code}</td>
        </tr>
        <tr>
            <td class="bg-right">姓名</td>
            <td>${pmdFee.user.realname}</td>
        </tr>
        <tr>
            <td class="bg-right">所属党委</td>
            <td>${partyMap.get(pmdFee.partyId).name}</td>
        </tr>
        <c:if test="${not empty pmdFee.branchId}">
            <tr>
                <td class="bg-right">所属党支部</td>
                <td>${branchMap.get(pmdFee.branchId).name}</td>
            </tr>
        </c:if>
        <tr>
            <td class="bg-right">缴费月份</td>
            <td>${cm:formatDate(pmdFee.payMonth, "yyyy年MM月")}</td>
        </tr>
        <tr>
            <td class="bg-right">缴费类别</td>
            <td>${cm:getMetaType(pmdFee.type).name}</td>
        </tr>
        <tr>
            <td class="bg-right">缴费金额</td>
            <td>${pmdFee.amt}</td>
        </tr>
    </table>
    <div id="payFormDiv"></div>
</div>
<div class="modal-footer">

    <div id="submitTip">支付完成前，请不要关闭此支付验证窗口。支付完成后，请点击“查看支付结果”更新支付状态。</div>
    <button id="submitBtn" type="button"
            data-loading-text="支付中，已跳转至支付页面"
            class="btn btn-primary"><i class="fa fa-mail-forward"></i> 去支付</button>
    <input id="finishBtn" style="display: none" type="button" class="btn btn-success" value="查看支付结果">
        <c:if test="${_p_payTest}">
    <a id="testCallback" target="_blank">test成功</a>
            </c:if>
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
    <jsp:include page="/ext/pmd_payForm.jsp"/>
</script>
<script>
    $("#submitBtn").click(function () {

        var $this = $(this);
        $.ajax({
            type : "post",
            url : "${ctx}/pmd/pmdFee_confirm",
            data:{id:'${pmdFee.id}', isSelfPay:"${param.isSelfPay}"},
            async : false, // 同步方法
            dataType:"json",
            success : function(data){
                if(data.success){
                    $this.button('loading').prop("disabled", true);
                    $("#submitTip,#finishBtn").show();
                    $("#closeBtn").hide();

                    <c:if test="${_p_payTest}">
                    console.log(data.order);
                    // for test
                    $("#testCallback").attr("href", "${ctx}/pmd/pay/callback?" + data.ret);
                    </c:if>
                    $("#payFormDiv").html(_.template($("#payFormTpl").html())({order: data.order, formMap:data.formMap}));
                    $("#payFormDiv form").submit();
                }
            }
        });
    });
    $("#finishBtn").click(function () {

        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    });
</script>