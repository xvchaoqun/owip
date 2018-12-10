<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button id="closeBtn" type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量代缴信息确认</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right" style="width: 200px;">缴费人数</td>
            <td>${fn:length(fn:split(param['ids[]'],","))}人</td>
        </tr>
        <tr>
            <td class="bg-right">总金额</td>
            <td>${duePay}</td>
        </tr>
    </table>
    <form id="payForm" action="${pay_url}" target="_blank" method="post"></form>
</div>
<div class="modal-footer">
    <%--<div id="tip">提示：由于校园卡支付平台出于安全性考虑，只要点了“去支付”按钮，必须支付完成，不可再换人代缴操作，请谨慎操作。</div>--%>
    <div id="submitTip">支付完成前，请不要关闭此支付验证窗口。支付完成后，请点击“查看支付结果”更新支付状态。</div>
    <button id="submitBtn" type="button"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-mail-forward"></i> 去支付
    </button>
    <input id="finishBtn" style="display: none" type="button" class="btn btn-success" value="查看支付结果">
    <%--<a id="testCallback" target="_blank">test成功</a>--%>
</div>
<style>
    #submitTip, #tip {
        text-indent: 2em;
        text-align: left;
        font-size: 18px;
        font-weight: bolder;
        margin-bottom: 5px;
        color: darkred;
    }

    #submitTip {
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
        $this.button('loading');
        window.setTimeout(function () {
            $.ajax({
                type: "post",
                url: "${ctx}/user/pmd/payConfirm_campuscard_batch",
                data: {'ids[]': '${param['ids[]']}', isDelay: "${param.isDelay}"},
                async: false, // 同步方法
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        $this.data("loading-text",
                            "<i class='fa fa-spinner fa-spin '></i> 支付中，已跳转至支付页面").button('loading').prop("disabled", true);
                        $("#submitTip,#finishBtn").show();
                        $("#closeBtn").hide();

                        // test
                        // $("#testCallback").attr("href", "${ctx}/pmd/pay/callback/campuscard?" + data.ret);

                        $("#payForm").html(_.template($("#payFormTpl").html())({order: data.order}))
                        $("#payForm").submit();
                    }
                }
            });
        }, 200);
    });
    $("#finishBtn").click(function () {
        $("#modal").modal('hide');
        $("#jqGrid2").trigger("reloadGrid");
    });
</script>