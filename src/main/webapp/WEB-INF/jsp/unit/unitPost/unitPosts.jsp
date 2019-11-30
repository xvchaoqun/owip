<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.displayEmpty==1?'空缺':''}岗位列表</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-center table-unhover2">
        <thead>
        <tr>
            <th width="40">序号</th>
            <th>岗位名称</th>
            <th nowrap>分管工作</th>
            <th nowrap>岗位级别</th>
            <th nowrap>职务属性</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${unitPosts}" var="record" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td style="text-align: left">${record.name}</td>
                <td style="text-align: left">${record.job}</td>
                <td nowrap>${cm:getMetaType(record.adminLevel).name}</td>
                <td nowrap>${cm:getMetaType(record.postType).name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <c:if test="${param.displayEmpty==1}">
        <div class="note">注：须在干部档案页的任职情况中进行相关岗位的关联之后（如果存在关联），此处才可正确显示空缺岗位</div>
    </c:if>
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>