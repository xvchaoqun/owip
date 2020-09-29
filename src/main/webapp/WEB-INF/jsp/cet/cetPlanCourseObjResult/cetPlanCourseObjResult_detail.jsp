<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>学习详情（${sysUser.realname}）</h3>
</div>
<div class="modal-body">
<table class="table table-bordered table-unhover2 table-center">
    <thead>
    <tr>
        <th width="60">序号</th>
        <th>专题班名称</th>
        <th width="90">完成课程数</th>
        <th width="90">完成学时数</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cetCourseItemMap}" var="entity" varStatus="vs">
        <c:set var="cetPlanCourseObjResult" value="${resultMap.get(entity.key)}"/>
    <tr>
        <td>${vs.count}</td>
        <td style="text-align: left">${entity.value.name}</td>
        <td>${cetPlanCourseObjResult.courseNum}</td>
        <td>${cm:stripTrailingZeros(cetPlanCourseObjResult.period)}/${cm:stripTrailingZeros(entity.value.period)}</td>
    </tr>
    </c:forEach>
    <tr>
        <td colspan="2">上传学习心得数</td>
        <td colspan="2">${cetPlanCourseObj.num}</td>
    </tr>
    <c:if test="${_p_cetSupportCert}">
    <tr>
        <td colspan="2">是否结业</td>
        <td colspan="2">${(cetPlanCourseObj.isFinished)?"是":"否"}</td>
    </tr>
    </c:if>
    </tbody>
</table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>