<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>查询订单</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right" width="130">订单号</td>
            <td>${param.sn}</td>
        </tr>
        <tr>
            <td class="bg-right">缴费账号</td>
            <td>${param.code}</td>
        </tr>
        <tr>
            <td class="bg-right">接口返回</td>
            <td style="word-break: break-all">${ret}</td>
        </tr>
        <c:if test="${not empty ret && fn:trim(ret) !='' && !order.isSuccess}">
            <tr>
                <td class="bg-right">主动同步结果</td>
                <td>
                    <button onclick="_syncResult()" class="btn btn-info btn-xs"><i class="fa fa-refresh"></i> 同步</button>
                    (用于没有收到支付平台通知的情况)
                </td>
            </tr>
            <script src="${ctx}/js/jquery.md5.js"></script>
            <script>
                function _syncResult() {
                    var ret = ${ret};
                    if ($.isJson(ret) && ret.paid) {
                        ret.paidtime = $.date(eval('new ' + (ret.paidtime.replace(/\//g, ''))), "yyyy-MM-dd hh:mm:ss")
                        //console.log("ret.paidtime=" + ret.paidtime)
                        var signStr = '${keys}' + ret.paycode + ret.sn + ret.amt + ret.payer + ret.paid + ret.paidtime + '${yek}';
                        var sign = $.md5(signStr)
                        var params = "paycode={0}&payitem={1}&payer={2}&payertype={3}&sn={4}&amt={5}&paid={6}&paidtime={7}&sign={8}"
                                .format(ret.paycode, $.trim(ret.payitem), ret.payer, ret.payertype, ret.sn, ret.amt, ret.paid, ret.paidtime, sign.toUpperCase());
                        //console.log(signStr)
                        //console.log(params)
                        //return;
                        $.post("${ctx}/pmd/pay/callback/campuscard?" + params, function (ret) {
                            if (ret == 'success') {
                                SysMsg.success("同步支付通知成功。");

                                $("#modal").modal('hide');
                                $("#jqGrid2").trigger("reloadGrid");
                            } else {
                                SysMsg.info("同步失败：" + ret)
                            }
                        });
                    } else {
                        SysMsg.warning("没有支付成功，无需同步");
                    }
                }
            </script>
        </c:if>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>


