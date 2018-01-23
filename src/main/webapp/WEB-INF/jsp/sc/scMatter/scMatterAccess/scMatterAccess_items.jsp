<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>调阅对象及调阅明细</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>序号</td>
            <td>姓名</td>
            <td>工号</td>
            <td>年度</td>
            <td>填报类型</td>
            <td>封面填表日期</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${itemList}" var="item" varStatus="vs">
        <tr>
            <td>${vs.index+1}</td>
            <td>${item.realname}</td>
            <td>${item.code}</td>
            <td>${item.year}</td>
            <td>${item.type?'年度集中填报':'个别填报'}</td>
            <td>${cm:formatDate(item.fillTime, "yyyy-MM-dd")}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>