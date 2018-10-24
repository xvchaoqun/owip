<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>参会人</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2 table-center">
        <thead>
        <tr>
            <th width="50">序号</th>
            <th width="80">工号</th>
            <th width="80">姓名</th>
            <th>所在单位及职务</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList}" var="u" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${u.code}</td>
                <td>${u.realname}</td>
                <td style="text-align: left">${cm:getCadreByUserId(u.userId).title}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>