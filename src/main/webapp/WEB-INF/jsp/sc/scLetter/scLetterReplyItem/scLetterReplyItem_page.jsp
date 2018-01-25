<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>纪委回复情况</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td width="50">序号</td>
            <td width="100">工号</td>
            <td width="80">姓名</td>
            <td>回复情况</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${itemList}" var="scLetterReplyItem" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${scLetterReplyItem.code}</td>
                <td>${scLetterReplyItem.realname}</td>
                <td>${scLetterReplyItem.content}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>