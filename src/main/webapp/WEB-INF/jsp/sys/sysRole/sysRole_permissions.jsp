<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>权限列表</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-center table-unhover2">
        <thead>
        <tr>
            <th width="40">序号</th>
            <th nowrap>权限</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${permissions}" var="permission" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td <%--style="text-align:left;"--%>>
                    <c:set var="isSelect" value="0"/>
                    <c:forEach items="${permission.value}" var="parentId">
                        <span ${parentId.value?'style="color: green"':'style="color: red"'}>${sysResourceMap.get(parentId.key).name}(${sysResourceMap.get(parentId.key).permission})</span>/
                     </c:forEach>
                    <span style="color: green">${sysResourceMap.get(permission.key).name}(${sysResourceMap.get(permission.key).permission})</span>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>