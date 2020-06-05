<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>干部信息</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-center table-unhover2">
        <thead>
        <tr>
            <th nowrap>序号</th>
            <th>工作证号</th>
            <th>姓名</th>
            <th nowrap>所在单位及职务</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cadreViews}" var="cadreView" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td>${cadreView.code}</td>
                <td style="text-align: left">
                    <a href="/#/cadre_view?cadreId=${cadreView.id}&hideBack=1" target="_blank"
                    data-tooltip="tooltip" data-container="body" data-html="true">
                            ${cadreView.realname}</a>
                </td>
                <td style="text-align: left;white-space: inherit">${cadreView.title}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>