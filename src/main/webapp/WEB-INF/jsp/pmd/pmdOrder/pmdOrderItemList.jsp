<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量代缴记录</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-condensed table-center">
        <thead>
        <tr>
            <th colspan="3">订单号：${param.sn}</th>
        </tr>
        <tr>
            <th>姓名</th>
            <th>学工号</th>
            <th>支付金额</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="totalDuePay" value="0"/>
        <c:forEach items="${orderItems}" var="item">
            <c:set var="totalDuePay" value="${totalDuePay+item.duePay}"/>
            <tr>
            <td>${item.realname}</td>
            <td>${item.code}</td>
            <td>${item.duePay}</td>
        </tr>
        </c:forEach>
        <tr>
            <td colspan="2" class="bg-right">总额</td>
            <td style="background-color: #f9f9f9 !important;font-weight: bolder">${totalDuePay}</td>
        </tr>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>