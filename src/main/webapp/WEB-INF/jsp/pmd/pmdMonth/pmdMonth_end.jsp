<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>结算</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right">党费收缴月份</td>
            <td>${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}</td>
        </tr>
        <tr>
            <td class="bg-right">启动日期</td>
            <td>${cm:formatDate(pmdMonth.startTime, "yyyy年MM月dd日")}</td>
        </tr>
        <tr>
            <td class="bg-right">结算日期</td>
            <td>${cm:formatDate(now, "yyyy年MM月dd日")}</td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-success"><i class="fa fa-rmb"></i> 确定结算</button>
</div>
<script>
    $("#submitBtn").click(function () {

        $.post("${ctx}/pmd/pmdMonth_end", {monthId: '${pmdMonth.id}'}, function (ret) {
            if (ret.success) {
                $.hashchange();
            }
        });
    });
</script>