<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button id="closeBtn" type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量代缴信息确认</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right" style="width: 200px;">缴费人数</td>
            <td>${fn:length(fn:split(param.ids,","))}人</td>
        </tr>
        <tr>
            <td class="bg-right">总金额</td>
            <td>${duePay}</td>
        </tr>
    </table>
    <div id="payFormDiv"></div>
</div>
<div class="modal-footer">

    <div id="submitTip">支付完成前，请不要关闭此支付验证窗口。支付完成后，请点击“查看支付结果”更新支付状态。</div>
    <button id="submitBtn" type="button"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-mail-forward"></i> 去支付
    </button>
    <input id="finishBtn" style="display: none" type="button" class="btn btn-success" value="查看支付结果">
    <c:if test="${devMode}">
        <a id="testCallback" target="_blank">test成功</a>
    </c:if>
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
    <jsp:include page="/ext/pmd_payForm.jsp"/>
</script>
<script>
    $("#submitBtn").click(function () {

        var $this = $(this);
        $this.button('loading');
        window.setTimeout(function () {
            $.ajax({
                type: "post",
                url: "${ctx}/user/pmd/payConfirm_batch",
                data: {ids: '${param.ids}', isDelay: "${param.isDelay}"},
                async: false, // 同步方法
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        $this.data("loading-text",
                            "<i class='fa fa-spinner fa-spin '></i> 支付中，已跳转至支付页面").button('loading').prop("disabled", true);
                        $("#submitTip,#finishBtn").show();
                        $("#closeBtn").hide();

                        <c:if test="${devMode}">
                        console.log(data.order);
                        // test
                        $("#testCallback").attr("href", "${ctx}/pmd/pay/callback?" + data.ret);
                        </c:if>

                        $("#payFormDiv").html(_.template($("#payFormTpl").html())({order: data.order, formMap:data.formMap}));
                        $("#payFormDiv form").submit();
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