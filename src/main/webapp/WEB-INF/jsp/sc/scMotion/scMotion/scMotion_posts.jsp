<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${scMotion.code}-拟调整岗位</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-center">
        <thead>
        <tr>
            <th width="120">岗位编号</th>
            <th width="150">岗位名称</th>
            <th>分管工作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scMotionPosts}" var="scMotionPost">
            <c:set var="unitPost" value="${scMotionPost.unitPost}"/>
            <tr>
                <td>${unitPost.code}</td>
                <td>${unitPost.name}</td>
                <td>${unitPost.job}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>