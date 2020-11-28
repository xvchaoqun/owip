<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>函询对象列表</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>序号</td>
            <td>工作证号</td>
            <td>姓名</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${itemList}" var="u" varStatus="vs">
        <tr>
            <td>${vs.index+1}</td>
            <td>${u.code}</td>
            <td>${u.realname}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>