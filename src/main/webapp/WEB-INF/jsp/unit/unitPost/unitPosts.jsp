<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>岗位列表</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>岗位名称</td>
            <td>分管工作</td>
            <td>岗位级别</td>
            <td>职务属性</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${unitPosts}" var="record" varStatus="vs">
            <tr>
                <td>${record.name}</td>
                <td>${record.job}</td>
                <td>${cm:getMetaType(record.adminLevel).name}</td>
                <td>${cm:getMetaType(record.postType).name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>