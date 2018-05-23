<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>关联订单信息</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td width="180">订单号</td>
            <td width="80">缴费账号</td>
            <td width="80">缴费人</td>
            <td width="50">金额</td>
            <td width="100">支付状态</td>
            <td width="100">订单状态</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pmdOrderCampuscards}" var="o" varStatus="vs">
            <tr>
                <td>${o.sn}</td>
                <td>${o.payer}</td>
                <td>${o.payername}</td>
                <td>${o.amt}</td>
                <td>${o.isSuccess?'已支付':'未支付'}</td>
                <td>${o.isClosed?'已关闭':'正常'}</td>
                <td>
                    <c:if test="${!o.isSuccess && !o.isClosed}">
                        <button class="btn btn-danger btn-xs" onclick="_closeTrade('${o.sn}')">关闭订单</button>
                    </c:if>
                    <c:if test="${o.isBatch}">
                        (批量代缴)
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<script>
    function _closeTrade(sn){
        $.post("${ctx}/pmd/pmdOrderCampuscard_closeTrade", {sn:sn},function(data){
            if(data.success){
                $.loadModal("${ctx}/pmd/pmdOrderCampuscard_orders?memberId=${param.memberId}", "850");
            }
        });
    }
</script>