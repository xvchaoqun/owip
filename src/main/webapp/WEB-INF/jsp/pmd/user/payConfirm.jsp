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
    <div id="payFormDiv"></div>
</div>
<div class="modal-footer">

    <div id="submitTip">支付完成前，请不要关闭此支付验证窗口。支付完成后，请点击“查看支付结果”更新支付状态。</div>
    <c:if test="${empty pmdMember.payTip}">
        <button id="submitBtn" type="button"
                data-loading-text="支付中，已跳转至支付页面"
                class="btn btn-primary"><i class="fa fa-mail-forward"></i> 去支付</button>
    </c:if>
    <c:if test="${not empty pmdMember.payTip}">
        ${pmdMember.payTip}
    </c:if>

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
            url : "${ctx}/user/pmd/payConfirm",
            data:{id:'${pmdMember.id}', isSelfPay:"${param.isSelfPay}"},
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
        <c:if test="${param.isMemberHelpPay==1}">
        $.hideView();
        </c:if>
        $("#${param.isSelfPay==0?'jqGrid2':'jqGrid'}").trigger("reloadGrid");
    });
</script>