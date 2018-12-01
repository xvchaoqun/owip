<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.type==0?'行政正职':'行政副职'}</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-center table-condensed table-unhover2">
        <thead>
        <tr>
            <td>序号</td>
            <td>岗位编号</td>
            <td>岗位名称</td>
            <td>岗位级别</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${unitPosts}" var="item" varStatus="vs">
        <tr>
            <td>${vs.count}</td>
            <td>${item.code}</td>
            <td style="text-align: left">${item.name}</td>
            <td>${cm:getMetaType(item.adminLevel).name}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>