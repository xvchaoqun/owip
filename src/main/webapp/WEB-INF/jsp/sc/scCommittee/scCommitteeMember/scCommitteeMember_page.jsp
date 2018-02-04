<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.isAbsent==0?'实际参会常委数':'请假常委数'}</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>序号</td>
            <td>工号</td>
            <td>姓名</td>
            <td>职务</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList}" var="u" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${u.code}</td>
                <td>${u.realname}</td>
                <td>${u.post}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>